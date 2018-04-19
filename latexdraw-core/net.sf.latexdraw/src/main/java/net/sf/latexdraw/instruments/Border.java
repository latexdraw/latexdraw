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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
import net.sf.latexdraw.commands.shape.ModifyShapeProperty;
import net.sf.latexdraw.commands.shape.MoveCtrlPoint;
import net.sf.latexdraw.commands.shape.MovePointShape;
import net.sf.latexdraw.commands.shape.RotateShapes;
import net.sf.latexdraw.commands.shape.ScaleShapes;
import net.sf.latexdraw.commands.shape.ShapeProperties;
import net.sf.latexdraw.handlers.ArcAngleHandler;
import net.sf.latexdraw.handlers.CtrlPointHandler;
import net.sf.latexdraw.handlers.Handler;
import net.sf.latexdraw.handlers.MovePtHandler;
import net.sf.latexdraw.handlers.RotationHandler;
import net.sf.latexdraw.handlers.ScaleHandler;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.Position;
import net.sf.latexdraw.util.Inject;
import org.malai.command.Command;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.interaction.library.DnD;
import org.malai.javafx.interaction.library.SrcTgtPointsData;

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

	@Inject private MetaShapeCustomiser metaCustomiser;

	public Border() {
		super();
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
			(ListChangeListener.Change<? extends IShape> evt) -> setActivated(!canvas.getDrawing().getSelection().isEmpty()));

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

	@Override
	public void interimFeedback() {
		canvas.setCursor(Cursor.DEFAULT);
	}

	@Override
	public void onCmdDone(final Command cmd) {
		if(cmd instanceof MoveCtrlPoint || cmd instanceof MovePointShape || cmd instanceof ScaleShapes) {
			metaCustomiser.dimPosCustomiser.update();
		}
	}

	private void updatePointsHandlers() {
		final IGroup selection = canvas.getDrawing().getSelection();

		if(selection.size() == 1) {
			final IShape sh = selection.getShapeAt(0);
			updateMvPtHandlers(sh);
			updateCtrlPtHandlers(sh);
			updateArcHandlers(sh);
		}
	}

	private void updateArcHandlers(final IShape selectedShape) {
		if(selectedShape instanceof IArc) {
			final IArc arc = (IArc) selectedShape;
			arcHandlerStart.setCurrentArc(arc);
			arcHandlerEnd.setCurrentArc(arc);
			arcHandlerStart.setVisible(true);
			arcHandlerEnd.setVisible(true);
		}else {
			arcHandlerStart.setVisible(false);
			arcHandlerEnd.setVisible(false);
		}
	}

	private void updateMvPtHandlers(final IShape selectedShape) {
		if(selectedShape instanceof IModifiablePointsShape) {
			initialisePointHandler(mvPtHandlers, pt -> new MovePtHandler(pt), selectedShape.getPoints());
		}
	}

	private void updateCtrlPtHandlers(final IShape selectedShape) {
		if(selectedShape instanceof IBezierCurve) {
			final IBezierCurve pts = (IBezierCurve) selectedShape;
			initialisePointHandler(ctrlPt1Handlers, pt -> new CtrlPointHandler(pt), pts.getFirstCtrlPts());
			initialisePointHandler(ctrlPt2Handlers, pt -> new CtrlPointHandler(pt), pts.getSecondCtrlPts());
		}
	}

	private <T extends Node & Handler> void initialisePointHandler(final List<T> handlers, final Function<IPoint, T> supplier, final List<IPoint> pts) {
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
		nodeBinder(new DnD(), MovePointShape.class).
			on(mvPtHandlers).
			first((i, c) -> i.getSrcObject().filter(o -> o instanceof MovePtHandler).map(o -> (MovePtHandler) o).ifPresent(handler -> {
				final IGroup group = canvas.getDrawing().getSelection();
				if(group.size() == 1 && group.getShapeAt(0) instanceof IModifiablePointsShape) {
					c.setPoint(handler.getPoint());
					c.setShape((IModifiablePointsShape) group.getShapeAt(0));
				}
			})).
			then((i, c) -> i.getSrcObject().ifPresent(node -> {
				final Point3D startPt = node.localToParent(i.getSrcLocalPoint());
				final Point3D endPt = node.localToParent(i.getTgtLocalPoint());
				final IPoint ptToMove = ((MovePtHandler) node).getPoint();
				final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
				final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
				c.setNewCoord(grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
			})).
			exec().
			when(i -> i.getSrcLocalPoint() != null && i.getTgtLocalPoint() != null && i.getSrcObject().orElse(null) instanceof MovePtHandler).
			bind();
	}

	@Override
	protected void configureBindings() {
		addBinding(new DnD2Scale(this));
		configureMovePointBinding();
		nodeBinder(new DnD(), MoveCtrlPoint.class).
			on(ctrlPt1Handlers).
			on(ctrlPt2Handlers).
			first((i, c) -> {
				final IGroup group = canvas.getDrawing().getSelection();
				if(group.size() == 1 && group.getShapeAt(0) instanceof IControlPointShape) {
					c.setPoint(i.getSrcObject().map(h -> ((CtrlPointHandler) h).getPoint()).orElse(null));
					c.setShape((IControlPointShape) group.getShapeAt(0));
					c.setIsFirstCtrlPt(ctrlPt1Handlers.contains(i.getSrcObject().orElse(null)));
				}
			}).
			then((i, c) -> {
				final Point3D startPt = i.getSrcObject().map(n -> n.localToParent(i.getSrcLocalPoint())).orElse(new Point3D(0d, 0d, 0d));
				final Point3D endPt = i.getSrcObject().map(n -> n.localToParent(i.getTgtLocalPoint())).orElse(new Point3D(0d, 0d, 0d));
				final IPoint ptToMove = i.getSrcObject().map(n -> ((CtrlPointHandler) n).getPoint()).orElse(ShapeFactory.INST.createPoint());
				final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
				final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
				c.setNewCoord(grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
			}).
			exec().bind();

		nodeBinder(new DnD(), RotateShapes.class).
			on(rotHandler).
			first((i, c) -> {
				final IDrawing drawing = canvas.getDrawing();
				c.setGravityCentre(drawing.getSelection().getGravityCentre());
				c.getGc().translate(canvas.getOrigin().getX(), canvas.getOrigin().getY());
				c.setShape(drawing.getSelection().duplicateDeep(false));
			}).
			then((i, c) -> c.setRotationAngle(c.getGc().computeRotationAngle(
				ShapeFactory.INST.createPoint(canvas.sceneToLocal(i.getSrcScenePoint())),
				ShapeFactory.INST.createPoint(canvas.sceneToLocal(i.getTgtScenePoint()))))).
			exec().bind();

		addBinding(new DnD2ArcAngle(this));
	}

	private static class DnD2ArcAngle extends JfXWidgetBinding<ModifyShapeProperty, DnD, Border, SrcTgtPointsData> {
		/** The gravity centre used for the rotation. */
		private IPoint gc;
		/** Defines whether the current handled shape is rotated. */
		private boolean isRotated;
		/** The current handled shape. */
		private final IPoint gap;

		DnD2ArcAngle(final Border ins) {
			super(ins, true, new DnD(), ModifyShapeProperty.class, Arrays.asList(ins.arcHandlerStart, ins.arcHandlerEnd), false, null);
			gap = ShapeFactory.INST.createPoint();
			isRotated = false;
		}

		@Override
		protected ModifyShapeProperty map() {
			final IDrawing drawing = instrument.canvas.getDrawing();
			ShapeProperties prop = null;

			if(drawing.getSelection().size() == 1) {
				final IArc shape = (IArc) drawing.getSelection().getShapeAt(0);
				final double rotAngle = shape.getRotationAngle();
				IPoint pt = ShapeFactory.INST.createPoint(interaction.getSrcObject().map(n -> n.localToParent(interaction.getSrcLocalPoint())).orElse(null));
				gc = shape.getGravityCentre();
				IPoint pCentre;

				if(interaction.getSrcObject().orElse(null) == instrument.arcHandlerStart) {
					prop = ShapeProperties.ARC_START_ANGLE;
					pCentre = shape.getStartPoint();
				}else {
					prop = ShapeProperties.ARC_END_ANGLE;
					pCentre = shape.getEndPoint();
				}

				if(MathUtils.INST.equalsDouble(rotAngle, 0d)) {
					isRotated = false;
				}else {
					pt = pt.rotatePoint(gc, -rotAngle);
					pCentre = pCentre.rotatePoint(gc, -rotAngle);
					isRotated = true;
				}

				gap.setPoint(pt.getX() - pCentre.getX(), pt.getY() - pCentre.getY());
			}

			return new ModifyShapeProperty(prop, drawing.getSelection().duplicateDeep(false), null);
		}

		@Override
		public void first() {
			// Nothing to do.
		}

		@Override
		public void then() {
			IPoint pt = ShapeFactory.INST.createPoint(interaction.getSrcObject().map(n -> n.localToParent(interaction.getTgtLocalPoint())).orElse(null));

			if(isRotated) {
				pt = pt.rotatePoint(gc, -cmd.getShapes().getRotationAngle());
			}

			cmd.setValue(computeAngle(ShapeFactory.INST.createPoint(pt.getX() - gap.getX(), pt.getY() - gap.getY())));
		}


		private double computeAngle(final IPoint position) {
			final double angle = Math.acos((position.getX() - gc.getX()) / position.distance(gc));

			return position.getY() > gc.getY() ? 2d * Math.PI - angle : angle;
		}


		@Override
		public boolean when() {
			return interaction.getSrcObject().isPresent() && interaction.getSrcLocalPoint() != null && interaction.getTgtLocalPoint() != null;
		}
	}

	private static class DnD2Scale extends JfXWidgetBinding<ScaleShapes, DnD, Border, SrcTgtPointsData> {
		/** The point corresponding to the 'press' position. */
		private IPoint p1;
		/** The x gap (gap between the pressed position and the targeted position) of the X-scaling. */
		private double xGap;
		/** The y gap (gap between the pressed position and the targeted position) of the Y-scaling. */
		private double yGap;

		DnD2Scale(final Border ins) {
			super(ins, true, new DnD(), ScaleShapes.class, ins.scaleHandlers.stream().map(h -> (Node) h).collect(Collectors.toList()), false, null);
		}

		private void setXGap(final Position refPosition, final IPoint tl, final IPoint br) {
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

		private void setYGap(final Position refPosition, final IPoint tl, final IPoint br) {
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
			final IDrawing drawing = instrument.canvas.getDrawing();
			final Position refPosition = interaction.getSrcObject().map(h -> ((ScaleHandler) h).getPosition().getOpposite()).orElse(Position.NE);
			final IPoint br = drawing.getSelection().getBottomRightPoint();
			final IPoint tl = drawing.getSelection().getTopLeftPoint();

			p1 = ShapeFactory.INST.createPoint(interaction.getSrcObject().map(n -> n.localToParent(interaction.getSrcLocalPoint())).orElse(null));

			setXGap(refPosition, tl, br);
			setYGap(refPosition, tl, br);
			cmd.setDrawing(drawing);
			cmd.setShape(drawing.getSelection().duplicateDeep(false));
			cmd.setRefPosition(refPosition);
		}


		@Override
		public void then() {
			final IPoint pt = ShapeFactory.INST.createPoint(interaction.getSrcObject().map(n -> n.localToParent(interaction.getTgtLocalPoint())).orElse(null));
			final Position refPosition = cmd.getRefPosition().orElse(Position.NE);

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
		public boolean when() {
			return interaction.getSrcObject().isPresent() && interaction.getSrcLocalPoint() != null && interaction.getTgtLocalPoint() != null;
		}

		@Override
		public void feedback() {
			super.feedback();
			cmd.getRefPosition().ifPresent(pos -> {
				switch(pos) {
					case EAST:
						instrument.canvas.setCursor(Cursor.W_RESIZE);
						break;
					case NE:
						instrument.canvas.setCursor(Cursor.SW_RESIZE);
						break;
					case NORTH:
						instrument.canvas.setCursor(Cursor.S_RESIZE);
						break;
					case NW:
						instrument.canvas.setCursor(Cursor.SE_RESIZE);
						break;
					case SE:
						instrument.canvas.setCursor(Cursor.NW_RESIZE);
						break;
					case SOUTH:
						instrument.canvas.setCursor(Cursor.N_RESIZE);
						break;
					case SW:
						instrument.canvas.setCursor(Cursor.NE_RESIZE);
						break;
					case WEST:
						instrument.canvas.setCursor(Cursor.E_RESIZE);
						break;
				}
			});
		}
	}
}
