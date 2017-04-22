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
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import net.sf.latexdraw.actions.shape.MoveCtrlPoint;
import net.sf.latexdraw.actions.shape.MovePointShape;
import net.sf.latexdraw.actions.shape.ScaleShapes;
import net.sf.latexdraw.handlers.ArcAngleHandler;
import net.sf.latexdraw.handlers.CtrlPointHandler;
import net.sf.latexdraw.handlers.Handler;
import net.sf.latexdraw.handlers.MovePtHandler;
import net.sf.latexdraw.handlers.RotationHandler;
import net.sf.latexdraw.handlers.ScaleHandler;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.Position;
import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInteractor;
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

//	@Inject private MetaShapeCustomiser metaCustomiser;

	Border() {
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
	public void reinit() {
		// selection.clear();
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
//			metaCustomiser.dimPosCustomiser.update();
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
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		movePointInteractor = new DnD2MovePoint(this);
		// addInteractor(new DnD2Scale(this))
		addInteractor(movePointInteractor);
		// addInteractor(new DnD2MoveCtrlPoint(this))
		// addInteractor(new DnD2Rotate(this))
		// addInteractor(new DnD2ArcAngle(this))
	}


	private static class DnD2MovePoint extends JfxInteractor<MovePointShape, DnD, Border> {
		DnD2MovePoint(final Border ins) throws IllegalAccessException, InstantiationException {
			super(ins, true, MovePointShape.class, DnD.class);
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
			super.updateAction();
			final Node node = interaction.getSrcObject().get();
			final Point3D startPt = node.localToParent(interaction.getSrcPoint().get());
			final Point3D endPt = node.localToParent(interaction.getEndPt().get());
			final IPoint ptToMove = ((MovePtHandler) node).getPoint();
			final double x = ptToMove.getX() + endPt.getX() - startPt.getX();
			final double y = ptToMove.getY() + endPt.getY() - startPt.getY();
			action.setNewCoord(instrument.grid.getTransformedPointToGrid(new Point3D(x, y, 0d)));
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getSrcPoint().isPresent() && interaction.getEndPt().isPresent() && interaction.getSrcObject().isPresent() &&
				interaction.getSrcObject().get() instanceof MovePtHandler;
		}
	}
}

// /** Maps a DnD interaction to an action that changes the arc angles. */
// private sealed class DnD2ArcAngle(ins : Border) extends
// InteractorImpl[ModifyShapeProperty, DnD, Border](ins, true,
// classOf[ModifyShapeProperty], classOf[DnD]) {
// /** The gravity centre used for the rotation. */
// var gc : IPoint = _
//
// /** Defines whether the current handled shape is rotated. */
// var isRotated = false
//
// /** The current handled shape. */
// var shape : IShape = _
//
// var gap : IPoint = ShapeFactory.INST.createPoint
//
//
// def initAction() {
// val drawing = instrument.canvas.getDrawing
//
// if(drawing.getSelection.size==1) {
// shape = drawing.getSelection.getShapeAt(0)
// val rotAngle = shape.getRotationAngle
// var pCentre = interaction.getStartObject.asInstanceOf[IHandler[_]].getCentre
// var pt : IPoint = interaction.getStartPt
// gc = instrument.getAdaptedOriginPoint(shape.getGravityCentre)
//
// if(LNumber.equalsDouble(rotAngle, 0.0))
// isRotated = false
// else {
// pt = pt.rotatePoint(gc, -rotAngle)
// pCentre = pCentre.rotatePoint(gc, -rotAngle)
// isRotated = true
// }
//
// gap.setPoint(pt.getX-pCentre.getX, pt.getY-pCentre.getY)
//
// if(interaction.getStartObject==instrument.arcHandlerStart)
// action.setProperty(ShapeProperties.ARC_START_ANGLE)
// else
// action.setProperty(ShapeProperties.ARC_END_ANGLE)
//
// action.setGroup(drawing.getSelection.duplicateDeep(false))
// }
// }
//
//
// override def updateAction() {
// var pt : IPoint = instrument.getAdaptedOriginPoint(interaction.getEndPt)
//
// if(isRotated)
// pt = pt.rotatePoint(gc, -shape.getRotationAngle)
//
// action.setValue(computeAngle(ShapeFactory.INST.createPoint(pt.getX-gap.getX,
// pt.getY-gap.getY)))
// }
//
//
// private def computeAngle(position : IPoint) : Double = {
// val angle = math.acos((position.getX-gc.getX)/position.distance(gc))
//
// if(position.getY>gc.getY)
// 2*math.Pi - angle
// else angle
// }
//
//
// override def isConditionRespected =
// interaction.getStartObject==instrument.arcHandlerEnd ||
// interaction.getStartObject==instrument.arcHandlerStart
// }
//
//
//
// /**
// * This link maps a DnD interaction on a rotation handler to an action that
// rotates the selected shapes.
// */
// private sealed class DnD2Rotate(ins : Border) extends
// InteractorImpl[RotateShapes, DnD, Border](ins, true, classOf[RotateShapes],
// classOf[DnD]) {
// /** The point corresponding to the 'press' position. */
// var p1 : IPoint = _
//
// /** The gravity centre used for the rotation. */
// var gc : IPoint = _
//
//
// def initAction() {
// val drawing = instrument.canvas.getDrawing
// p1 = instrument.getAdaptedOriginPoint(interaction.getStartPt)
// gc = drawing.getSelection.getGravityCentre
// action.setGravityCentre(gc)
// action.setShape(drawing.getSelection.duplicateDeep(false))
// }
//
//
// override def updateAction() {
// action.setRotationAngle(gc.computeRotationAngle(p1,
// instrument.getAdaptedOriginPoint(interaction.getEndPt)))
// }
//
// override def isConditionRespected =
// interaction.getStartObject==instrument.rotHandler
// }
//
//
//
// /**
// * This link maps a DnD interaction on a move control point handler to an
// action that moves the selected control point.
// */
// private sealed class DnD2MoveCtrlPoint(ins : Border) extends
// InteractorImpl[MoveCtrlPoint, DnD, Border](ins, true, classOf[MoveCtrlPoint],
// classOf[DnD]) {
// /** The original coordinates of the moved point. */
// var sourcePt : IPoint = _
//
//
// override def initAction() {
// val group = instrument.canvas.getDrawing.getSelection
//
// if(group.size==1 && group.getShapeAt(0).isInstanceOf[IControlPointShape]) {
// val handler = ctrlPtHandler.get
// sourcePt = ShapeFactory.INST.createPoint(handler.getCentre)
// action.setIndexPt(handler.getIndexPt)
// action.setShape(group.getShapeAt(0).asInstanceOf[IControlPointShape])
// action.setIsFirstCtrlPt(instrument.ctrlPt1Handlers.contains(interaction.getStartObject))
// }
// }
//
//
// override def updateAction() {
// super.updateAction
// val startPt = interaction.getStartPt
// val endPt = interaction.getEndPt
// val x = sourcePt.getX + endPt.getX-startPt.getX
// val y = sourcePt.getY + endPt.getY-startPt.getY
// action.setNewCoord(instrument.getAdaptedGridPoint(ShapeFactory.INST.createPoint(x,
// y)))
// }
//
//
// override def isConditionRespected = ctrlPtHandler.isDefined
//
//
// /**
// * @return The selected move control point handler or null.
// * @since 3.0
// */
// private def ctrlPtHandler : Option[CtrlPointHandler] = {
// val obj = interaction.getStartObject
//
// obj.isInstanceOf[CtrlPointHandler] &&
// (instrument.ctrlPt1Handlers.contains(obj) ||
// instrument.ctrlPt2Handlers.contains(obj)) match {
// case true => Some(obj.asInstanceOf[CtrlPointHandler])
// case false => None
// }
// }
// }

// /**
// * This link maps a DnD interaction on a scale handler to an action that
// scales the selection.
// */
// private sealed class DnD2Scale(ins : Border) extends
// InteractorImpl[ScaleShapes, DnD, Border](ins, true, classOf[ScaleShapes],
// classOf[DnD]) {
// /** The point corresponding to the 'press' position. */
// var p1 : IPoint = _
//
// /** The x gap (gap between the pressed position and the targeted position) of
// the X-scaling. */
// var xGap : Double = _
//
// /** The y gap (gap between the pressed position and the targeted position) of
// the Y-scaling. */
// var yGap : Double = _
//
//
// private def setXGap(refPosition : Position, tl : IPoint, br : IPoint) {
// refPosition match {
// case Position.NW | Position.SW | Position.WEST => xGap = p1.getX - br.getX
// case Position.NE | Position.SE | Position.EAST => xGap = tl.getX - p1.getX
// case _ => xGap = 0.0
// }
// }
//
// private def setYGap(refPosition : Position, tl : IPoint, br : IPoint) {
// refPosition match {
// case Position.NW | Position.NE | Position.NORTH => yGap = p1.getY - br.getY
// case Position.SW | Position.SE | Position.SOUTH => yGap = tl.getY - p1.getY
// case _ => yGap = 0.0
// }
// }
//
//
// override def initAction() {
// val drawing = instrument.canvas.getDrawing
// val refPosition = scaleHandler.get.getPosition.getOpposite
// val br = drawing.getSelection.getBottomRightPoint
// val tl = drawing.getSelection.getTopLeftPoint
//
// // p1 =
// instrument.canvas.getMagneticGrid.getTransformedPointToGrid(instrument.canvas.getZoomedPoint(interaction.getStartPt))
//
// setXGap(refPosition, tl, br)
// setYGap(refPosition, tl, br)
// action.setDrawing(drawing)
// action.setShape(drawing.getSelection.duplicateDeep(false))
// action.refPosition = refPosition
// }
//
//
// override def updateAction() {
// super.updateAction
//
// val pt = instrument.getAdaptedGridPoint(interaction.getEndPt)
// val refPosition = action.refPosition.get
//
// if(refPosition.isSouth)
// action.newY = pt.getY + yGap
// else if(refPosition.isNorth)
// action.newY = pt.getY - yGap
//
// if(refPosition.isWest)
// action.newX = pt.getX - xGap
// else if(refPosition.isEast)
// action.newX = pt.getX + xGap
// }
//
//
// override def isConditionRespected = scaleHandler.isDefined
//
//
// override def interimFeedback() {
// super.interimFeedback
// action.refPosition.get match {
// case Position.EAST =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR))
// case Position.NE =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR))
// case Position.NORTH =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR))
// case Position.NW =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR))
// case Position.SE =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR))
// case Position.SOUTH =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR))
// case Position.SW =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR))
// case Position.WEST =>
// instrument.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR))
// }
// }
//
//
// private def scaleHandler : Option[ScaleHandler] = {
// val obj = interaction.getStartObject
//
// obj.isInstanceOf[ScaleHandler] && instrument.scaleHandlers.contains(obj)
// match {
// case true => Some(obj.asInstanceOf[ScaleHandler])
// case false => None
// }
// }
// }
