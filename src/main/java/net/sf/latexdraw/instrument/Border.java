/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.binding.JfXWidgetBinding;
import io.github.interacto.jfx.interaction.library.DnD;
import io.github.interacto.jfx.interaction.library.SrcTgtPointsData;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import net.sf.latexdraw.command.shape.ModifyShapeProperty;
import net.sf.latexdraw.command.shape.MoveCtrlPoint;
import net.sf.latexdraw.command.shape.MovePointShape;
import net.sf.latexdraw.command.shape.RotateShapes;
import net.sf.latexdraw.command.shape.ScaleShapes;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.handler.ArcAngleHandler;
import net.sf.latexdraw.handler.CtrlPointHandler;
import net.sf.latexdraw.handler.Handler;
import net.sf.latexdraw.handler.MovePtHandler;
import net.sf.latexdraw.handler.RotationHandler;
import net.sf.latexdraw.handler.ScaleHandler;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arc;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.ControlPointShape;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.ModifiablePointsShape;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Position;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.jetbrains.annotations.NotNull;

/**
 * This instrument manages the selected views.
 * @author Arnaud BLOUIN
 */
public class Border extends CanvasInstrument implements Initializable {
	/** The handlers that scale shapes. */
	final ObservableList<ScaleHandler> scaleHandlers;
	/** The handlers that move points. */
	final ObservableList<MovePtHandler> mvPtHandlers;
	/** The handlers that move first control points. */
	final ObservableList<CtrlPointHandler> ctrlPt1Handlers;
	/** The handlers that move second control points. */
	final ObservableList<CtrlPointHandler> ctrlPt2Handlers;
	/** The handler that sets the start angle of an arc. */
	final ArcAngleHandler arcHandlerStart;
	/** The handler that sets the end angle of an arc. */
	final ArcAngleHandler arcHandlerEnd;
	/** The handler that rotates shapes. */
	RotationHandler rotHandler;

	private final @NotNull MetaShapeCustomiser metaCustomiser;

	@Inject
	public Border(final Canvas canvas, final MagneticGrid grid, final MetaShapeCustomiser metaCustomiser) {
		super(canvas, grid);
		this.metaCustomiser = Objects.requireNonNull(metaCustomiser);
		mvPtHandlers = FXCollections.observableArrayList();
		ctrlPt1Handlers = FXCollections.observableArrayList();
		ctrlPt2Handlers = FXCollections.observableArrayList();
		arcHandlerStart = new ArcAngleHandler(true);
		arcHandlerEnd = new ArcAngleHandler(false);
		scaleHandlers = FXCollections.observableArrayList();
	}


	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		scaleHandlers.add(new ScaleHandler(Position.NW, canvas.getSelectionBorder()));
		scaleHandlers.add(new ScaleHandler(Position.NORTH, canvas.getSelectionBorder()));
		scaleHandlers.add(new ScaleHandler(Position.NE, canvas.getSelectionBorder()));
		scaleHandlers.add(new ScaleHandler(Position.WEST, canvas.getSelectionBorder()));
		scaleHandlers.add(new ScaleHandler(Position.EAST, canvas.getSelectionBorder()));
		scaleHandlers.add(new ScaleHandler(Position.SW, canvas.getSelectionBorder()));
		scaleHandlers.add(new ScaleHandler(Position.SOUTH, canvas.getSelectionBorder()));
		scaleHandlers.add(new ScaleHandler(Position.SE, canvas.getSelectionBorder()));

		rotHandler = new RotationHandler(canvas.getSelectionBorder());

		scaleHandlers.forEach(handler -> canvas.addToWidgetLayer(handler));
		canvas.addToWidgetLayer(rotHandler);
		canvas.addToWidgetLayer(arcHandlerStart);
		canvas.addToWidgetLayer(arcHandlerEnd);

		canvas.getDrawing().getSelection().getShapes().addListener(
			(ListChangeListener.Change<? extends Shape> evt) -> setActivated(!canvas.getDrawing().getSelection().isEmpty()));

		setActivated(false);
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		scaleHandlers.forEach(handler -> handler.setVisible(activated));
		rotHandler.setVisible(activated);

		if(activated) {
			updatePointsHandlers();
		}else {
			mvPtHandlers.forEach(handler -> handler.setVisible(false));
			ctrlPt1Handlers.forEach(handler -> handler.setVisible(false));
			ctrlPt2Handlers.forEach(handler -> handler.setVisible(false));
			arcHandlerStart.setVisible(false);
			arcHandlerEnd.setVisible(false);
		}
	}


	private void updatePointsHandlers() {
		final Group selection = canvas.getDrawing().getSelection();

		if(selection.size() == 1) {
			selection.getShapeAt(0).ifPresent(sh -> {
				updateMvPtHandlers(sh);
				updateCtrlPtHandlers(sh);
				updateArcHandlers(sh);
			});
		}
	}

	private void updateArcHandlers(final Shape selectedShape) {
		if(selectedShape instanceof Arc) {
			final Arc arc = (Arc) selectedShape;
			arcHandlerStart.setCurrentArc(arc);
			arcHandlerEnd.setCurrentArc(arc);
			arcHandlerStart.setVisible(true);
			arcHandlerEnd.setVisible(true);
		}else {
			arcHandlerStart.setVisible(false);
			arcHandlerEnd.setVisible(false);
		}
	}

	private void updateMvPtHandlers(final Shape selectedShape) {
		if(selectedShape instanceof ModifiablePointsShape) {
			initialisePointHandler(mvPtHandlers, pt -> new MovePtHandler(pt), selectedShape.getPoints());
		}
	}

	private void updateCtrlPtHandlers(final Shape selectedShape) {
		if(selectedShape instanceof BezierCurve) {
			final BezierCurve pts = (BezierCurve) selectedShape;
			initialisePointHandler(ctrlPt1Handlers, pt -> new CtrlPointHandler(pt), pts.getFirstCtrlPts());
			initialisePointHandler(ctrlPt2Handlers, pt -> new CtrlPointHandler(pt), pts.getSecondCtrlPts());
		}
	}

	private <T extends Node & Handler> void initialisePointHandler(final List<T> handlers, final Function<Point, T> supplier, final List<Point> pts) {
		handlers.forEach(handler -> {
			canvas.removeFromWidgetLayer(handler);
			handler.flush();
		});
		handlers.clear();

		pts.forEach(pt -> {
			final T handler = supplier.apply(pt);
			canvas.addToWidgetLayer(handler);
			handlers.add(handler);
		});
	}

	private void configureMovePointBinding() {
		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(i -> new MovePointShape((ModifiablePointsShape) canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow(),
				i.getSrcObject().filter(o -> o instanceof MovePtHandler).map(o -> ((MovePtHandler) o).getPoint()).orElseThrow()))
			.on(mvPtHandlers)
			.then((i, c) -> i.getSrcObject().ifPresent(node -> {
				final Point3D startPt = node.localToParent(i.getSrcLocalPoint());
				final Point3D endPt = node.localToParent(i.getTgtLocalPoint());
				final Point ptToMove = ((MovePtHandler) node).getPoint();
				final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
				final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
				c.setNewCoord(grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
			}))
			.continuousExecution()
			.when(i -> i.getSrcLocalPoint() != null && i.getTgtLocalPoint() != null && i.getSrcObject().orElse(null) instanceof MovePtHandler &&
				canvas.getDrawing().getSelection().size() == 1 && canvas.getDrawing().getSelection().getShapeAt(0).filter(s -> s instanceof ModifiablePointsShape).isPresent())
			.end(i -> metaCustomiser.dimPosCustomiser.update())
			.bind();
	}

	@Override
	protected void configureBindings() {
		addBinding(new DnD2Scale());

		configureMovePointBinding();

		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(i -> new MoveCtrlPoint((ControlPointShape) canvas.getDrawing().getSelection().getShapeAt(0).orElseThrow(),
				i.getSrcObject().map(h -> ((CtrlPointHandler) h).getPoint()).orElseThrow(), ctrlPt1Handlers.contains(i.getSrcObject().orElseThrow())))
			.on(ctrlPt1Handlers)
			.on(ctrlPt2Handlers)
			.then((i, c) -> {
				final Point3D startPt = i.getSrcObject().map(n -> n.localToParent(i.getSrcLocalPoint())).orElseGet(() -> new Point3D(0d, 0d, 0d));
				final Point3D endPt = i.getSrcObject().map(n -> n.localToParent(i.getTgtLocalPoint())).orElseGet(() -> new Point3D(0d, 0d, 0d));
				final Point ptToMove = i.getSrcObject().map(n -> ((CtrlPointHandler) n).getPoint()).orElseGet(() -> ShapeFactory.INST.createPoint());
				final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
				final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
				c.setNewCoord(grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
			})
			.when(i -> canvas.getDrawing().getSelection().size() == 1 && canvas.getDrawing().getSelection().getShapeAt(0).filter(s -> s instanceof ControlPointShape).isPresent())
			.continuousExecution()
			.end(i -> metaCustomiser.dimPosCustomiser.update())
			.bind();

		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(() -> new RotateShapes(canvas.getDrawing().getSelection().getGravityCentre().add(canvas.getOrigin()),
				canvas.getDrawing().getSelection().duplicateDeep(false), 0d))
			.on(rotHandler)
			.then((i, c) -> c.setRotationAngle(c.getGc().computeRotationAngle(
				ShapeFactory.INST.createPoint(canvas.sceneToLocal(i.getSrcScenePoint())),
				ShapeFactory.INST.createPoint(canvas.sceneToLocal(i.getTgtScenePoint())))))
			.continuousExecution()
			.bind();

		bindArcHandler();
	}

	private void bindArcHandler() {
		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(i -> new ModifyShapeProperty<>(i.getSrcObject().orElse(null) == arcHandlerStart ?
				ShapeProperties.ARC_START_ANGLE : ShapeProperties.ARC_END_ANGLE, canvas.getDrawing().getSelection().duplicateDeep(false), null))
			.on(arcHandlerStart, arcHandlerEnd)
			.then((i, c) -> canvas.getDrawing().getSelection().getShapeAt(0).map(s -> (Arc) s)
				.ifPresent(shape -> {
					final Point gc = c.getShapes().getGravityCentre();
					final Point gap = ShapeFactory.INST.createPoint(i.getSrcObject().map(n -> n.localToParent(i.getSrcLocalPoint())).orElse(null)).
						rotatePoint(shape.getGravityCentre(), -shape.getRotationAngle()).
						substract(i.getSrcObject().orElse(null) == arcHandlerStart ? shape.getStartPoint() : shape.getEndPoint());
					final Point position = ShapeFactory.INST.createPoint(i.getSrcObject().map(n -> n.localToParent(i.getTgtLocalPoint())).orElse(null)).
						rotatePoint(c.getShapes().getGravityCentre(), -c.getShapes().getRotationAngle()).
						substract(gap);
					final double angle = Math.acos((position.getX() - gc.getX()) / position.distance(gc));
					c.setValue(position.getY() > gc.getY() ? 2d * Math.PI - angle : angle);
				})
			)
			.when(i -> i.getSrcObject().isPresent() && i.getSrcLocalPoint() != null && i.getTgtLocalPoint() != null && canvas.getDrawing().getSelection().size() == 1)
			.continuousExecution()
			.bind();
	}


	private class DnD2Scale extends JfXWidgetBinding<ScaleShapes, DnD, SrcTgtPointsData> {
		/** The point corresponding to the 'press' position. */
		private Point p1;
		/** The x gap (gap between the pressed position and the targeted position) of the X-scaling. */
		private double xGap;
		/** The y gap (gap between the pressed position and the targeted position) of the Y-scaling. */
		private double yGap;

		DnD2Scale() {
			super(true, new DnD(),
				i -> new ScaleShapes(canvas.getDrawing().getSelection().duplicateDeep(false), canvas.getDrawing(),
				i.getSrcObject().map(h -> ((ScaleHandler) h).getPosition().getOpposite()).orElse(Position.SW)),
				scaleHandlers.stream().map(h -> (Node) h).collect(Collectors.toList()), false, null);
		}

		private void setXGap(final Position refPosition, final Point tl, final Point br) {
			switch(refPosition) {
				case NW:
				case SW:
				case WEST:
					xGap = p1.getX() - br.getX();
					break;
				case NE:
				case SE:
				case EAST:
					xGap = tl.getX() - p1.getX();
					break;
				default:
					xGap = 0d;
			}
		}

		private void setYGap(final Position refPosition, final Point tl, final Point br) {
			switch(refPosition) {
				case NW:
				case NE:
				case NORTH:
					yGap = p1.getY() - br.getY();
					break;
				case SW:
				case SE:
				case SOUTH:
					yGap = tl.getY() - p1.getY();
					break;
				default:
					yGap = 0d;
			}
		}

		@Override
		public void first() {
			final Drawing drawing = canvas.getDrawing();
			final Point br = drawing.getSelection().getBottomRightPoint();
			final Point tl = drawing.getSelection().getTopLeftPoint();

			p1 = ShapeFactory.INST.createPoint(interaction.getSrcObject().map(n -> n.localToParent(interaction.getSrcLocalPoint())).orElse(null));

			setXGap(cmd.getRefPosition(), tl, br);
			setYGap(cmd.getRefPosition(), tl, br);

			switch(cmd.getRefPosition()) {
				case EAST:
					canvas.setCursor(Cursor.W_RESIZE);
					break;
				case NE:
					canvas.setCursor(Cursor.SW_RESIZE);
					break;
				case NORTH:
					canvas.setCursor(Cursor.S_RESIZE);
					break;
				case NW:
					canvas.setCursor(Cursor.SE_RESIZE);
					break;
				case SE:
					canvas.setCursor(Cursor.NW_RESIZE);
					break;
				case SOUTH:
					canvas.setCursor(Cursor.N_RESIZE);
					break;
				case SW:
					canvas.setCursor(Cursor.NE_RESIZE);
					break;
				case WEST:
					canvas.setCursor(Cursor.E_RESIZE);
					break;
			}
		}


		@Override
		public void then() {
			final Point pt = ShapeFactory.INST.createPoint(interaction.getSrcObject().map(n -> n.localToParent(interaction.getTgtLocalPoint())).orElse(null));
			final Position refPosition = cmd.getRefPosition();

			if(refPosition.isSouth()) {
				cmd.setNewY(pt.getY() + yGap);
			}else {
				if(refPosition.isNorth()) {
					cmd.setNewY(pt.getY() - yGap);
				}
			}

			if(refPosition.isWest()) {
				cmd.setNewX(pt.getX() - xGap);
			}else {
				if(refPosition.isEast()) {
					cmd.setNewX(pt.getX() + xGap);
				}
			}
		}

		@Override
		public void endOrCancel() {
			canvas.setCursor(Cursor.DEFAULT);
		}

		@Override
		public void end() {
			metaCustomiser.dimPosCustomiser.update();
		}

		@Override
		public boolean when() {
			return interaction.getSrcObject().isPresent() && interaction.getSrcLocalPoint() != null && interaction.getTgtLocalPoint() != null;
		}
	}
}
