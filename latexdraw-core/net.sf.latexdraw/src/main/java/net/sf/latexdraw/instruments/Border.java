/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.MoveCtrlPoint;
import net.sf.latexdraw.actions.shape.MovePointShape;
import net.sf.latexdraw.actions.shape.RotateShapes;
import net.sf.latexdraw.actions.shape.ScaleShapes;
import net.sf.latexdraw.actions.shape.ShapeProperties;
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
import org.malai.action.Action;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.interaction.library.DnD;

/**
 * This instrument manages the selected views.
 * @author Arnaud BLOUIN
 */
public class Border extends CanvasInstrument implements Initializable {
	/** The handlers that scale shapes. */
	private final List<ScaleHandler> scaleHandlers;
	/** The handlers that move points. */
	private final List<MovePtHandler> mvPtHandlers;
	/** The handlers that move first control points. */
	private final List<CtrlPointHandler> ctrlPt1Handlers;
	/** The handlers that move second control points. */
	private final List<CtrlPointHandler> ctrlPt2Handlers;
	/** The handler that sets the start angle of an arc. */
	private final ArcAngleHandler arcHandlerStart;
	/** The handler that sets the end angle of an arc. */
	private final ArcAngleHandler arcHandlerEnd;
	/** The handler that rotates shapes. */
	private RotationHandler rotHandler;
	private DnD2MovePoint movePointInteractor;
	private DnD2MoveCtrlPoint moveCtrlPtInteractor;
	@Inject private MetaShapeCustomiser metaCustomiser;

	public Border() {
		super();
		mvPtHandlers = new ArrayList<>();
		ctrlPt1Handlers = new ArrayList<>();
		ctrlPt2Handlers = new ArrayList<>();
		arcHandlerStart = new ArcAngleHandler(true);
		arcHandlerEnd = new ArcAngleHandler(false);
		scaleHandlers = new ArrayList<>(8);
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
	public void onActionDone(final Action action) {
		if(action instanceof MoveCtrlPoint || action instanceof MovePointShape || action instanceof ScaleShapes) {
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
			movePointInteractor.getInteraction().registerToNodes(mvPtHandlers.stream().map(h -> (Node)h).collect(Collectors.toList()));
		}
	}

	private void updateCtrlPtHandlers(final IShape selectedShape) {
		if(selectedShape instanceof IBezierCurve) {
			final IBezierCurve pts = (IBezierCurve) selectedShape;
			initialisePointHandler(ctrlPt1Handlers, pt -> new CtrlPointHandler(pt), pts.getFirstCtrlPts());
			initialisePointHandler(ctrlPt2Handlers, pt -> new CtrlPointHandler(pt), pts.getSecondCtrlPts());
			moveCtrlPtInteractor.getInteraction().registerToNodes(
				Stream.concat(ctrlPt1Handlers.stream(), ctrlPt2Handlers.stream()).map(h -> (Node)h).collect(Collectors.toList()));
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

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		movePointInteractor = new DnD2MovePoint(this);
		moveCtrlPtInteractor = new DnD2MoveCtrlPoint(this);
		addBinding(new DnD2Scale(this));
		addBinding(movePointInteractor);
		addBinding(moveCtrlPtInteractor);
		addBinding(new DnD2Rotate(this));
		addBinding(new DnD2ArcAngle(this));
	}


	private static class DnD2MovePoint extends JfXWidgetBinding<MovePointShape, DnD, Border> {
		DnD2MovePoint(final Border ins) throws IllegalAccessException, InstantiationException {
			super(ins, true, MovePointShape.class, new DnD());
		}

		@Override
		public void initAction() {
			final IGroup group = instrument.canvas.getDrawing().getSelection();

			if(group.size() == 1 && group.getShapeAt(0) instanceof IModifiablePointsShape) {
				final MovePtHandler handler = (MovePtHandler) interaction.getSrcObject().get();
				action.setPoint(handler.getPoint());
				action.setShape((IModifiablePointsShape) group.getShapeAt(0));
			}
		}

		@Override
		public void updateAction() {
			final Node node = interaction.getSrcObject().get();
			final Point3D startPt = node.localToParent(interaction.getSrcPoint());
			final Point3D endPt = node.localToParent(interaction.getEndPt());
			final IPoint ptToMove = ((MovePtHandler) node).getPoint();
			final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
			final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
			action.setNewCoord(instrument.grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getSrcPoint() != null && interaction.getEndPt() != null && interaction.getSrcObject().isPresent() &&
				interaction.getSrcObject().get() instanceof MovePtHandler;
		}
	}


	private static class DnD2MoveCtrlPoint extends JfXWidgetBinding<MoveCtrlPoint, DnD, Border> {
		DnD2MoveCtrlPoint(final Border ins) throws IllegalAccessException, InstantiationException {
			super(ins, true, MoveCtrlPoint.class, new DnD());
		}

		@Override
		public void initAction() {
			final IGroup group = instrument.canvas.getDrawing().getSelection();

			if(group.size() == 1 && group.getShapeAt(0) instanceof IControlPointShape) {
				final CtrlPointHandler handler = (CtrlPointHandler) interaction.getSrcObject().get();
				action.setPoint(handler.getPoint());
				action.setShape((IControlPointShape) group.getShapeAt(0));
				action.setIsFirstCtrlPt(instrument.ctrlPt1Handlers.contains(interaction.getSrcObject().get()));
			}
		}

		@Override
		public void updateAction() {
			final Node node = interaction.getSrcObject().get();
			final Point3D startPt = node.localToParent(interaction.getSrcPoint());
			final Point3D endPt = node.localToParent(interaction.getEndPt());
			final IPoint ptToMove = ((CtrlPointHandler) node).getPoint();
			final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
			final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
			action.setNewCoord(instrument.grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getSrcPoint() != null && interaction.getEndPt() != null && interaction.getSrcObject().isPresent()
				&& interaction.getSrcObject().get() instanceof CtrlPointHandler;
		}
	}

	private static class DnD2ArcAngle extends JfXWidgetBinding<ModifyShapeProperty, DnD, Border> {
		/** The gravity centre used for the rotation. */
		private IPoint gc;
		/** Defines whether the current handled shape is rotated. */
		private boolean isRotated;
		/** The current handled shape. */
		private IArc shape;
		private IPoint gap;

		DnD2ArcAngle(final Border ins) throws IllegalAccessException, InstantiationException {
			super(ins, true, ModifyShapeProperty.class, new DnD(), ins.arcHandlerStart, ins.arcHandlerEnd);
			gap = ShapeFactory.INST.createPoint();
			isRotated = false;
		}

		@Override
		protected ModifyShapeProperty createAction() {
			final IDrawing drawing = instrument.canvas.getDrawing();
			ShapeProperties prop = null;

			if(drawing.getSelection().size() == 1) {
				shape = (IArc) drawing.getSelection().getShapeAt(0);
				final double rotAngle = shape.getRotationAngle();
				IPoint pt = ShapeFactory.INST.createPoint(interaction.getSrcObject().get().localToParent(interaction.getSrcPoint()));
				gc = shape.getGravityCentre();
				IPoint pCentre;

				if(interaction.getSrcObject().get() == instrument.arcHandlerStart) {
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
		public void initAction() {
			// Nothing to do.
		}

		@Override
		public void updateAction() {
			IPoint pt = ShapeFactory.INST.createPoint(interaction.getSrcObject().get().localToParent(interaction.getEndPt()));

			if(isRotated) {
				pt = pt.rotatePoint(gc, -shape.getRotationAngle());
			}

			gap = ShapeFactory.INST.createPoint();
			action.setValue(computeAngle(ShapeFactory.INST.createPoint(pt.getX() - gap.getX(), pt.getY() - gap.getY())));
		}


		private double computeAngle(final IPoint position) {
			final double angle = Math.acos((position.getX() - gc.getX()) / position.distance(gc));

			return position.getY() > gc.getY() ? 2d * Math.PI - angle : angle;
		}


		@Override
		public boolean isConditionRespected() {
			return interaction.getSrcObject().isPresent() && interaction.getSrcPoint() != null && interaction.getEndPt() != null;
		}
	}

	private static class DnD2Scale extends JfXWidgetBinding<ScaleShapes, DnD, Border> {
		/** The point corresponding to the 'press' position. */
		private IPoint p1;
		/** The x gap (gap between the pressed position and the targeted position) of the X-scaling. */
		private double xGap;
		/** The y gap (gap between the pressed position and the targeted position) of the Y-scaling. */
		private double yGap;

		DnD2Scale(final Border ins) throws IllegalAccessException, InstantiationException {
			super(ins, true, ScaleShapes.class, new DnD(), ins.scaleHandlers.stream().map(h -> (Node)h).collect(Collectors.toList()));
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
		public void initAction() {
			final IDrawing drawing = instrument.canvas.getDrawing();
			final ScaleHandler handler = (ScaleHandler) getInteraction().getSrcObject().get();
			final Position refPosition = handler.getPosition().getOpposite();
			final IPoint br = drawing.getSelection().getBottomRightPoint();
			final IPoint tl = drawing.getSelection().getTopLeftPoint();

			p1 = ShapeFactory.INST.createPoint(interaction.getSrcObject().get().localToParent(interaction.getSrcPoint()));

			setXGap(refPosition, tl, br);
			setYGap(refPosition, tl, br);
			action.setDrawing(drawing);
			action.setShape(drawing.getSelection().duplicateDeep(false));
			action.setRefPosition(refPosition);
		}


		@Override
		public void updateAction() {
			final IPoint pt = ShapeFactory.INST.createPoint(interaction.getSrcObject().get().localToParent(interaction.getEndPt()));
			final Position refPosition = action.getRefPosition().get();

			if(refPosition.isSouth()) {
				action.setNewY(pt.getY() + yGap);
			}else {
				if(refPosition.isNorth()) {
					action.setNewY(pt.getY() - yGap);
				}
			}

			if(refPosition.isWest()) {
				action.setNewX(pt.getX() - xGap);
			}else {
				if(refPosition.isEast()) {
					action.setNewX(pt.getX() + xGap);
				}
			}
		}


		@Override
		public boolean isConditionRespected() {
			return interaction.getSrcObject().isPresent() && interaction.getSrcPoint() != null && interaction.getEndPt() != null;
		}


		@Override
		public void interimFeedback() {
			super.interimFeedback();
			action.getRefPosition().ifPresent(pos -> {
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

	private static class DnD2Rotate extends JfXWidgetBinding<RotateShapes, DnD, Border> {
		/** The point corresponding to the 'press' position. */
		private IPoint p1;
		/** The gravity centre used for the rotation. */
		private IPoint gc;

		DnD2Rotate(final Border ins) throws IllegalAccessException, InstantiationException {
			super(ins, true, RotateShapes.class, new DnD(), ins.rotHandler);
		}

		@Override
		public void initAction() {
			final IDrawing drawing = instrument.canvas.getDrawing();
			p1 = ShapeFactory.INST.createPoint(interaction.getSrcObject().get().localToParent(interaction.getSrcPoint()));
			gc = drawing.getSelection().getGravityCentre();
			action.setGravityCentre(gc);
			action.setShape(drawing.getSelection().duplicateDeep(false));
		}


		@Override
		public void updateAction() {
			action.setRotationAngle(gc.computeRotationAngle(p1,
				ShapeFactory.INST.createPoint(interaction.getSrcObject().get().localToParent(interaction.getEndPt()))));

		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getSrcObject().isPresent() && interaction.getSrcPoint() != null && interaction.getEndPt() != null;
		}
	}
}
