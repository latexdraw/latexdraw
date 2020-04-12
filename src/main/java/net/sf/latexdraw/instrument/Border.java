/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.interaction.library.DnD;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
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
	final List<ScaleHandler> scaleHandlers;
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

	private final @NotNull ShapeCoordDimCustomiser coordDimCustomiser;

	@Inject
	public Border(final Canvas canvas, final MagneticGrid grid, final ShapeCoordDimCustomiser coordDimCustomiser) {
		super(canvas, grid);
		this.coordDimCustomiser = Objects.requireNonNull(coordDimCustomiser);
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
			.then((i, c) -> {
				i.getSrcObject().ifPresent(node -> {
					final Point3D startPt = node.localToParent(i.getSrcLocalPoint());
					final Point3D endPt = node.localToParent(i.getTgtLocalPoint());
					final Point ptToMove = ((MovePtHandler) node).getPoint();
					final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
					final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
					c.setNewCoord(grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
				});
				canvas.update();
			})
			.continuousExecution()
			.when(i -> i.getSrcLocalPoint() != null && i.getTgtLocalPoint() != null && i.getSrcObject().orElse(null) instanceof MovePtHandler &&
				canvas.getDrawing().getSelection().size() == 1 && canvas.getDrawing().getSelection().getShapeAt(0).filter(s -> s instanceof ModifiablePointsShape).isPresent())
			.end(() -> coordDimCustomiser.update())
			.bind();
	}

	@Override
	protected void configureBindings() {
		configureDnD2ScaleBinding();

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
			.when(() -> canvas.getDrawing().getSelection().size() == 1 && canvas.getDrawing().getSelection().getShapeAt(0).filter(s -> s instanceof ControlPointShape).isPresent())
			.continuousExecution()
			.end(() -> coordDimCustomiser.update())
			.bind();

		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(() -> new RotateShapes(canvas.getDrawing().getSelection().getGravityCentre().add(canvas.getOrigin()),
				canvas.getDrawing().getSelection().duplicateDeep(false), 0d))
			.on(rotHandler)
			.then((i, c) -> {
				c.setRotationAngle(c.getGc().computeRotationAngle(
					ShapeFactory.INST.createPoint(canvas.sceneToLocal(i.getSrcScenePoint())),
					ShapeFactory.INST.createPoint(canvas.sceneToLocal(i.getTgtScenePoint()))));
				canvas.update();
			})
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
			.then((i, c) -> {
				canvas.getDrawing().getSelection().getShapeAt(0).map(s -> (Arc) s).ifPresent(shape -> {
					final Point gc = c.getShapes().getGravityCentre();
					final Point gap = ShapeFactory.INST.createPoint(i.getSrcObject().map(n -> n.localToParent(i.getSrcLocalPoint())).orElse(null)).
						rotatePoint(shape.getGravityCentre(), -shape.getRotationAngle()).
						substract(i.getSrcObject().orElse(null) == arcHandlerStart ? shape.getStartPoint() : shape.getEndPoint());
					final Point position = ShapeFactory.INST.createPoint(i.getSrcObject().map(n -> n.localToParent(i.getTgtLocalPoint())).orElse(null)).
						rotatePoint(c.getShapes().getGravityCentre(), -c.getShapes().getRotationAngle()).
						substract(gap);
					final double angle = Math.acos((position.getX() - gc.getX()) / position.distance(gc));
					c.setValue(position.getY() > gc.getY() ? 2d * Math.PI - angle : angle);
				});
				canvas.update();
			})
			.when(i -> i.getSrcObject().isPresent() && i.getSrcLocalPoint() != null && i.getTgtLocalPoint() != null && canvas.getDrawing().getSelection().size() == 1)
			.continuousExecution()
			.bind();
	}


	private void configureDnD2ScaleBinding() {
		final AtomicInteger xgap = new AtomicInteger();
		final AtomicInteger ygap = new AtomicInteger();

		nodeBinder()
			.usingInteraction(DnD::new)
			.toProduce(i -> new ScaleShapes(canvas.getDrawing().getSelection().duplicateDeep(false), canvas.getDrawing(),
					i.getSrcObject().map(h -> ((ScaleHandler) h).getPosition().getOpposite()).orElse(Position.SW)))
			.on(scaleHandlers.toArray(new Node[0]))
			.continuousExecution()
			.first((i, c) -> {
				final Drawing drawing = canvas.getDrawing();
				final Point br = drawing.getSelection().getBottomRightPoint();
				final Point tl = drawing.getSelection().getTopLeftPoint();
				final Point srcPt = ShapeFactory.INST.createPoint(i.getSrcObject()
					.map(n -> n.localToParent(i.getSrcLocalPoint())).orElse(null));

				switch(c.getRefPosition()) {
					case EAST -> {
						xgap.set((int) (tl.getX() - srcPt.getX()));
						canvas.setCursor(Cursor.W_RESIZE);
					}
					case NE -> {
						xgap.set((int) (tl.getX() - srcPt.getX()));
						ygap.set((int) (srcPt.getY() - br.getY()));
						canvas.setCursor(Cursor.SW_RESIZE);
					}
					case NORTH -> {
						ygap.set((int) (srcPt.getY() - br.getY()));
						canvas.setCursor(Cursor.S_RESIZE);
					}
					case NW -> {
						xgap.set((int) (srcPt.getX() - br.getX()));
						ygap.set((int) (srcPt.getY() - br.getY()));
						canvas.setCursor(Cursor.SE_RESIZE);
					}
					case SE -> {
						xgap.set((int) (tl.getX() - srcPt.getX()));
						ygap.set((int) (tl.getY() - srcPt.getY()));
						canvas.setCursor(Cursor.NW_RESIZE);
					}
					case SOUTH -> {
						ygap.set((int) (tl.getY() - srcPt.getY()));
						canvas.setCursor(Cursor.N_RESIZE);
					}
					case SW -> {
						xgap.set((int) (srcPt.getX() - br.getX()));
						ygap.set((int) (tl.getY() - srcPt.getY()));
						canvas.setCursor(Cursor.NE_RESIZE);
					}
					case WEST -> {
						xgap.set((int) (srcPt.getX() - br.getX()));
						canvas.setCursor(Cursor.E_RESIZE);
					}
				}
			})
			.then((i, c) -> {
				final Point pt = ShapeFactory.INST.createPoint(i.getSrcObject()
					.map(n -> n.localToParent(i.getTgtLocalPoint())).orElse(null));
				final Position refPosition = c.getRefPosition();

				if(refPosition.isSouth()) {
					c.setNewY(grid.getTransformedPointToGrid(new Point3D(0, pt.getY() + ygap.get(), 0)).getY());
				}else {
					if(refPosition.isNorth()) {
						c.setNewY(grid.getTransformedPointToGrid(new Point3D(0, pt.getY() - ygap.get(), 0)).getY());
					}
				}

				if(refPosition.isWest()) {
					c.setNewX(grid.getTransformedPointToGrid(new Point3D(pt.getX() - xgap.get(), 0, 0)).getX());
				}else {
					if(refPosition.isEast()) {
						c.setNewX(grid.getTransformedPointToGrid(new Point3D(pt.getX() + xgap.get(), 0, 0)).getX());
					}
				}
				canvas.update();
			})
			.when(i -> i.getSrcObject().isPresent() && i.getSrcLocalPoint() != null && i.getTgtLocalPoint() != null)
			.endOrCancel(i -> canvas.setCursor(Cursor.DEFAULT))
			.end(() -> coordDimCustomiser.update())
			.bind();
	}
}
