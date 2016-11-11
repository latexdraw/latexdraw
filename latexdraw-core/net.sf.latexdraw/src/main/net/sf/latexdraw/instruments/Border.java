/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Cursor;
import net.sf.latexdraw.handlers.ArcAngleHandler;
import net.sf.latexdraw.handlers.CtrlPointHandler;
import net.sf.latexdraw.handlers.IHandler;
import net.sf.latexdraw.handlers.MovePtHandler;
import net.sf.latexdraw.handlers.RotationHandler;
import net.sf.latexdraw.handlers.ScaleHandler;
import net.sf.latexdraw.models.interfaces.shape.Position;
import org.malai.picking.Pickable;

/**
 * This instrument manages the selected views.
 * @author Arnaud BLOUIN
 */
public class Border extends CanvasInstrument {
	/** The handlers that scale shapes. */
	protected final List<ScaleHandler> scaleHandlers;

	/** The handlers that move points. */
	protected final List<MovePtHandler> mvPtHandlers;

	/** The handlers that move first control points. */
	protected final List<CtrlPointHandler> ctrlPt1Handlers;

	/** The handlers that move second control points. */
	protected final List<CtrlPointHandler> ctrlPt2Handlers;

	// /** The handler that sets the arc frame. */
	// protected lazy val frameArcHandler : FrameArcHandler = new FrameArcHandler()

	/** The handler that sets the start angle of an arc. */
	protected final ArcAngleHandler arcHandlerStart;

	/** The handler that sets the end angle of an arc. */
	protected final ArcAngleHandler arcHandlerEnd;

	/** The handler that rotates shapes. */
	protected final RotationHandler rotHandler;

	// protected @Inject MetaShapeCustomiser metaCustomiser;

	public Border() {
		super();
		scaleHandlers = new ArrayList<>();
		mvPtHandlers = new ArrayList<>();
		ctrlPt1Handlers = new ArrayList<>();
		ctrlPt2Handlers = new ArrayList<>();
		arcHandlerStart = new ArcAngleHandler(true);
		arcHandlerEnd = new ArcAngleHandler(false);
		rotHandler = new RotationHandler();
		scaleHandlers.add(new ScaleHandler(Position.NW));
		scaleHandlers.add(new ScaleHandler(Position.NORTH));
		scaleHandlers.add(new ScaleHandler(Position.NE));
		scaleHandlers.add(new ScaleHandler(Position.WEST));
		scaleHandlers.add(new ScaleHandler(Position.EAST));
		scaleHandlers.add(new ScaleHandler(Position.SW));
		scaleHandlers.add(new ScaleHandler(Position.SOUTH));
		scaleHandlers.add(new ScaleHandler(Position.SE));
	}

	@Override
	public void reinit() {
		// selection.clear();
	}

	@Override
	public void interimFeedback() {
		canvas.setCursor(Cursor.DEFAULT);
	}

	// public void onActionDone(final Action action) {
	// if(action instanceof MoveCtrlPoint || action instanceof MovePointShape ||
	// action instanceof ScaleShapes) {
	// _metaCustomiser.dimPosCustomiser.update();
	// }
	// }

	/**
	 * Updates the bounding rectangle using the selected views.
	 * 
	 * @since 3.0
	 */
	public void update() {
		if(!isActivated())
			return;
		// if(selection.isEmpty())
		// border.setFrame(0, 0, 1, 1);
		// else {
		// selection.stream().map(IViewShape::getBorder).reduce((r1, r2) ->
		// r1.createUnion(r2)).ifPresent(rec -> {
		// final double zoomLevel = canvas.getZoom();
		// border.setFrame(rec.getMinX()*zoomLevel, rec.getMinY()*zoomLevel,
		// rec.getWidth()*zoomLevel, rec.getHeight()*zoomLevel);
		// updateHandlersPosition();
		// });
		// }
	}

	// private void updateHandlersPosition() {
	// scaleHandlers.forEach(h -> h.updateFromShape(border));
	// rotHandler.setPoint(border.getMaxX(), border.getMinY());
	//
	// updateArcHandlers();
	// updateMvHandlers();
	// updateCtrlMvHandlers();
	// }

	// private void updateArcHandlers() {
	// if(isArcHandlerShowable()) {
	// IShape sh = selection.get(0).getShape();
	// if(sh instanceof IArc) {
	// IArc arc = (IArc)sh;
	// arcHandlerStart.update(arc, canvas.getZoom());
	// arcHandlerEnd.update(arc, canvas.getZoom());
	// }
	// }
	// }

	// private void updateCtrlMvHandlers() {
	// if(isCtrlPtMvHandlersShowable()) {
	// IShape sh = selection.get(0).getShape();
	// if(sh instanceof IControlPointShape) {
	// initialiseCtrlMvHandlers((IControlPointShape)sh);
	// }
	// }
	// }

	// private void initialiseCtrlMvHandlers(final IControlPointShape cps) {
	// final double zoom = canvas.getZoom();
	// final int nbPts = cps.getNbPoints();
	//
	// // Adding missing handlers.
	// if(ctrlPt1Handlers.size()<nbPts) {
	// for(int i = ctrlPt1Handlers.size(); i<nbPts; i++) {
	// ctrlPt1Handlers.add(new CtrlPointHandler(i));
	// ctrlPt2Handlers.add(new CtrlPointHandler(i));
	// }
	// }
	// // Removing extra handlers.
	// else {
	// while(ctrlPt1Handlers.size()>nbPts) {
	// ctrlPt1Handlers.remove(ctrlPt1Handlers.size()-1);
	// ctrlPt2Handlers.remove(ctrlPt2Handlers.size()-1);
	// }
	// }
	//
	// // Updating handlers.
	// for(int i = 0, size = ctrlPt1Handlers.size(); i<size; i++) {
	// IPoint pt1 = cps.getFirstCtrlPtAt(i);
	// ctrlPt1Handlers.get(i).setPoint(pt1.getX()*zoom, pt1.getY()*zoom);
	// IPoint pt2 = cps.getSecondCtrlPtAt(i);
	// ctrlPt2Handlers.get(i).setPoint(pt2.getX()*zoom, pt2.getY()*zoom);
	// }
	// }

	// private void updateMvHandlers() {
	// if(isPtMvHandlersShowable()) {
	// IShape sh = selection.get(0).getShape();
	//
	// if(sh instanceof IModifiablePointsShape) {
	// IModifiablePointsShape pts = (IModifiablePointsShape)sh;
	// final int nbPts = pts.getNbPoints();
	// final double zoom = canvas.getZoom();
	//
	// if(mvPtHandlers.size()<nbPts) {
	// for(int i = mvPtHandlers.size(); i<nbPts; i++)
	// mvPtHandlers.add(new MovePtHandler(i));
	// }else {
	// while(mvPtHandlers.size()>nbPts)
	// mvPtHandlers.remove(mvPtHandlers.size()-1);
	// }
	//
	// for(int i = 0, size = mvPtHandlers.size(); i<size; i++) {
	// IPoint pt = pts.getPtAt(i);
	// mvPtHandlers.get(i).setPoint(pt.getX()*zoom, pt.getY()*zoom);
	// }
	// }
	// }
	// }


	// protected boolean isCtrlPtMvHandlersShowable() {
	// return selection.size()==1&&selection.get(0) instanceof IViewBezierCurve;
	// }
	//
	// protected boolean isPtMvHandlersShowable() {
	// return selection.size()==1&&selection.get(0) instanceof IViewModifiablePtsShape;
	// }
	//
	// /** @return True if the arc handlers can be painted. */
	// protected boolean isArcHandlerShowable() {
	// return selection.size()==1&&selection.get(0) instanceof IViewArc;
	// }

	// /**
	// * Adds the given shape to the selection. If the instrument is activated and
	// * the addition is performed, the instrument is updated.
	// *
	// * @param view
	// * The view to add. If null, nothing is done.
	// * @since 3.0
	// */
	// public void add(final IViewShape view) {
	// if(view!=null) {
	// selection.add(view);
	// if(isActivated()) {
	// // The border is updated only if the view has been added and
	// // the border is activated.
	// update();
	// MappingRegistry.REGISTRY.addMapping(new
	// Shape2BorderMapping(MappingRegistry.REGISTRY.getSourceFromTarget(view, IShape.class), this));
	// }
	// }
	// }
	//
	// /**
	// * Removes the given view from the selection. If the instrument is activated
	// * and the removal is performed, the instrument is updated.
	// *
	// * @param view
	// * The view to remove. If null or it is not already in the
	// * selection, nothing is performed.
	// * @since 3.0
	// */
	// public void remove(final IViewShape view) {
	// if(view!=null) {
	// selection.remove(view);
	// MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view,
	// IShape.class), Shape2BorderMapping.class);
	// update();
	// }
	// }

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new DnD2Scale(this))
		// addInteractor(new DnD2MovePoint(this))
		// addInteractor(new DnD2MoveCtrlPoint(this))
		// addInteractor(new DnD2Rotate(this))
		// addInteractor(new DnD2ArcAngle(this))
	}

	/**
	 * Removes all the selected views.
	 * 
	 * @since 3.0
	 */
	public void clear() {
		// if(!selection.isEmpty()) {
		// selection.forEach(view ->
		// MappingRegistry.REGISTRY.removeMappingsUsingSource(MappingRegistry.REGISTRY.getSourceFromTarget(view,
		// IShape.class), Shape2BorderMapping.class));
		// selection.clear();
		// setActivated(false);
		// }
	}

	public Pickable getPickableAt(final double x, final double y) {
		if(activated) {
			Optional<Pickable> pickable = getHandlerAt(x, y, scaleHandlers);

			if(!pickable.isPresent() && rotHandler.contains(x, y))
				pickable = Optional.of(rotHandler);

			if(!pickable.isPresent())
				pickable = getHandlerAt(x, y, mvPtHandlers);

			if(!pickable.isPresent())
				pickable = getHandlerAt(x, y, ctrlPt1Handlers);

			if(!pickable.isPresent())
				pickable = getHandlerAt(x, y, ctrlPt2Handlers);

			// if(!pickable.isPresent() && _frameArcHandler!=null &&
			// _frameArcHandler.contains(x2, y2))
			// pickable = Some(_frameArcHandler);

			if(!pickable.isPresent() && arcHandlerStart.contains(x, y))
				pickable = Optional.of(arcHandlerStart);

			if(!pickable.isPresent() && arcHandlerEnd.contains(x, y))
				pickable = Optional.of(arcHandlerEnd);

			return pickable.orElse(null);
		}
		return null;
	}

	@SuppressWarnings("cast")
	private <T extends IHandler<?>> Optional<Pickable> getHandlerAt(final double x, final double y, final List<T> handlers) {
		return handlers.stream().filter(handler -> handler.contains(x, y)).findFirst().map(elt -> {
			if(elt instanceof Pickable)
				return Optional.of((Pickable)elt);
			return Optional.<Pickable> empty();
		}).get();
	}

	// public Picker getPickerAt(final double x, final double y) {
	// return null;
	// }

	// Supposing that there is no handler outside the border.
	public boolean contains(final Object obj) {
		return obj instanceof IHandler;
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
// var gap : IPoint = ShapeFactory.createPoint
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
// action.setValue(computeAngle(ShapeFactory.createPoint(pt.getX-gap.getX,
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
// sourcePt = ShapeFactory.createPoint(handler.getCentre)
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
// action.setNewCoord(instrument.getAdaptedGridPoint(ShapeFactory.createPoint(x,
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
//
//
//
// /**
// * This link maps a DnD interaction on a move point handler to an action that
// moves the selected point.
// */
// private sealed class DnD2MovePoint(ins : Border) extends
// InteractorImpl[MovePointShape, DnD, Border](ins, true,
// classOf[MovePointShape], classOf[DnD]) {
// /** The original coordinates of the moved point. */
// var sourcePt : IPoint = _
//
//
// override def initAction() {
// val group = instrument.canvas.getDrawing.getSelection
//
// if(group.size==1 && group.getShapeAt(0).isInstanceOf[IModifiablePointsShape])
// {
// val handler = movePtHandler.get
// sourcePt = ShapeFactory.createPoint(handler.getCentre)
// action.setIndexPt(handler.getIndexPt)
// action.setShape(group.getShapeAt(0).asInstanceOf[IModifiablePointsShape])
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
// action.setNewCoord(instrument.getAdaptedGridPoint(ShapeFactory.createPoint(x,
// y)))
// }
//
//
// override def isConditionRespected = movePtHandler.isDefined
//
//
// /**
// * @return The selected move point handler or null.
// * @since 3.0
// */
// private def movePtHandler : Option[MovePtHandler] = {
// val obj = interaction.getStartObject
// obj.isInstanceOf[MovePtHandler] && instrument.mvPtHandlers.contains(obj)
// match {
// case true => Some(obj.asInstanceOf[MovePtHandler])
// case false => None
// }
// }
// }
//
//
//
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
