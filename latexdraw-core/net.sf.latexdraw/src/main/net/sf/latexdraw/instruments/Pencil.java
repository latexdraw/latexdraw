package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import javafx.scene.Cursor;
import net.sf.latexdraw.filters.PictureFilter;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import javax.swing.*;

/**
 * This instrument allows to draw shapes.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/13/10<br>
 * 
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Pencil extends CanvasInstrument {
	/** The current editing choice (rectangle, ellipse, etc.) of the instrument. */
	protected EditionChoice currentChoice;

	@Inject protected TextSetter textSetter;

//	protected MLayeredPane layers;

	private JFileChooser pictureFileChooser;

	private IGroup groupParams;

	public Pencil() {
		super();
		currentChoice = EditionChoice.RECT;
	}

	public JFileChooser getPictureFileChooser() {
		if(pictureFileChooser==null) {
			pictureFileChooser = new JFileChooser();
			pictureFileChooser.setMultiSelectionEnabled(false);
			pictureFileChooser.setAcceptAllFileFilterUsed(true);
			pictureFileChooser.setFileFilter(new PictureFilter());
		}
		return pictureFileChooser;
	}

	public IGroup getGroupParams() {
		if(groupParams==null) {
			groupParams = ShapeFactory.createGroup();
			groupParams.addShape(ShapeFactory.createRectangle());
			groupParams.addShape(ShapeFactory.createDot(ShapeFactory.createPoint()));
			groupParams.addShape(ShapeFactory.createGrid(ShapeFactory.createPoint()));
			groupParams.addShape(ShapeFactory.createAxes(ShapeFactory.createPoint()));
			groupParams.addShape(ShapeFactory.createText());
			groupParams.addShape(ShapeFactory.createCircleArc());
			groupParams.addShape(ShapeFactory.createPolyline());
			groupParams.addShape(ShapeFactory.createBezierCurve());
			groupParams.addShape(ShapeFactory.createFreeHand());
			groupParams.addShape(ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 10, "x", false));
		}
		return groupParams;
	}

	@Override
	public void setActivated(boolean act) {
		if(this.activated!=act)
			super.setActivated(act);
	}

	@Override
	public void interimFeedback() {
//		canvas.setTempView(null);
		// canvas.refresh();
		if(canvas.getScene()!=null)
			canvas.getScene().setCursor(Cursor.DEFAULT);
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new DnD2MoveViewport(canvas, this))
		// addInteractor(new Press2AddShape(this))
		// addInteractor(new Press2AddText(this))
		// addInteractor(new Press2InsertPicture(this))
		// addInteractor(new DnD2AddShape(this))
		// addInteractor(new MultiClic2AddShape(this))
		// addInteractor(new Press2InitTextSetter(this))
	}

	/**
	 * @return An instance of a shape configured (thickness, colours, etc.) with
	 *         the parameters of the pencil.
	 * @since 3.0
	 */
	public IShape createShapeInstance() {
		return setShapeParameters(currentChoice.createShapeInstance());
	}

	/**
	 * Configures the given shape with the parameters (e.g. thickness, colours,
	 * etc.) of the pencil.
	 * 
	 * @param shape
	 *            The shape to configure.
	 * @return The modified shape given as argument.
	 * @since 3.0
	 */
	public IShape setShapeParameters(final IShape shape) {
		if(shape instanceof IModifiablePointsShape&&!(shape instanceof IFreehand)) {
			final IModifiablePointsShape mod = (IModifiablePointsShape)shape;
			mod.addPoint(ShapeFactory.createPoint());
			mod.addPoint(ShapeFactory.createPoint());
		}

		shape.copy(getGroupParams());
		shape.setModified(true);
		return shape;
	}

	/** @return The file chooser used to select pictures. */
	public JFileChooser pictureFileChooser() {
		return pictureFileChooser;
	}

	/** @return The current editing choice. */
	public EditionChoice getCurrentChoice() {
		return currentChoice;
	}

	/**
	 * Sets the current editing choice.
	 * 
	 * @param choice
	 *            The new editing choice to set.
	 * @since 3.0
	 */
	public void setCurrentChoice(EditionChoice choice) {
		currentChoice = choice;
	}
}

// /**
// * This class defines a generic link for the pencil.
// * @author Arnaud Blouin
// * @since 3.0
// * @version 3.0
// */
// private abstract sealed class PencilInteractor[I <:
// Interaction](pencil:Pencil, exec:Boolean, clazzInteraction:Class[I])
// extends InteractorImpl[AddShape, I, Pencil](pencil, false, classOf[AddShape],
// clazzInteraction) {
// protected var tmpShape : IViewShape = _
//
// override def initAction() {
// val sh = instrument.createShapeInstance
// tmpShape = View2DTK.getFactory.createView(sh)
// action.setShape(sh)
// action.setDrawing(instrument.canvas.getDrawing)
// instrument.canvas.setTempView(tmpShape)
// }
//
// override def interimFeedback() {
// if(tmpShape!=null) {
// tmpShape.update
// instrument.canvas.refresh
// }
// }
// }
//
//
//
// /**
// * This link allows to create shapes using a multi-clic interaction.
// */
// private sealed class MultiClic2AddShape(pencil:Pencil) extends
// PencilInteractor[MultiClick](pencil, false, classOf[MultiClick]) {
// // To avoid the overlapping with the DnD2AddShape, the starting interaction
// must be
// // aborted when the condition is not respected, i.e. when the selected shape
// type is
// // not devoted to the multi-clic interaction.
// override def isInteractionMustBeAborted = !isConditionRespected
//
// override def updateAction() {
// val pts = interaction.getPoints
// val currPoint = instrument.getAdaptedPoint(interaction.getCurrentPosition)
// val shape = action.shape.get.asInstanceOf[IModifiablePointsShape]
//
// if(shape.getNbPoints==pts.size && !interaction.isLastPointFinalPoint)
// shape.addPoint(ShapeFactory.createPoint(currPoint.getX, currPoint.getY),
// pts.size-1)
// else
// shape.setPoint(currPoint.getX, currPoint.getY, -1)
//
// // Curves need to be balanced.
// shape match {
// case shape1: IControlPointShape => shape1.balance
// case _ =>
// }
//
// shape.setModified(true)
// action.drawing.get.setModified(true)
// }
//
//
// override def initAction() {
// super.initAction
// val shape = action.shape.get
//
// shape match {
// case modShape: IModifiablePointsShape =>
// val pt = instrument.getAdaptedPoint(interaction.getPoints.get(0))
// modShape.setPoint(pt, 0)
// modShape.setPoint(pt.getX + 1, pt.getY + 1, 1)
// case _ =>
// }
// }
//
// override def isConditionRespected =
// instrument.currentChoice==EditionChoice.POLYGON ||
// instrument.currentChoice==EditionChoice.LINES ||
// instrument.currentChoice==EditionChoice.BEZIER_CURVE ||
// instrument.currentChoice==EditionChoice.BEZIER_CURVE_CLOSED
//
// override def interactionStarts(inter:Interaction) {
// super.interactionStarts(inter)
// if(instrument.currentChoice==EditionChoice.POLYGON)
// interaction.setMinPoints(3)
// else
// interaction.setMinPoints(2)
// }
// }
//
//
//
// /**
// * This link allows to create shapes using a drag-and-drop interaction.
// */
// private sealed class DnD2AddShape(pencil:Pencil) extends
// PencilInteractor[AbortableDnD](pencil, false, classOf[AbortableDnD]) {
// override def initAction() {
// super.initAction
// action.shape match {
// case Some(shape) =>
// val pt = instrument.getAdaptedPoint(interaction.getStartPt)
//
// // For squares and circles, the centre of the shape is the reference point
// during the creation.
// shape match {
// case sq:ISquaredShape =>
// sq.setPosition(pt.getX-1, pt.getY-1)
// sq.setWidth(2.0)
// case fh:IFreehand => fh.addPoint(ShapeFactory.createPoint(pt.getX, pt.getY))
// case _ => shape.translate(pt.getX, pt.getY)
// }
// case _ =>
// }
// }
//
//
// override def isConditionRespected = {
// val ec = instrument.currentChoice
// interaction.getButton==MouseEvent.BUTTON1 && (ec==EditionChoice.RECT ||
// ec==EditionChoice.ELLIPSE || ec==EditionChoice.SQUARE ||
// ec==EditionChoice.CIRCLE ||
// ec==EditionChoice.RHOMBUS || ec==EditionChoice.TRIANGLE ||
// ec==EditionChoice.CIRCLE_ARC || ec==EditionChoice.FREE_HAND)
// }
//
//
// override def updateAction() {
// val shape = action.shape.get
// // Getting the points depending on the current zoom.
// val startPt = instrument.getAdaptedPoint(interaction.getStartPt)
// val endPt = instrument.getAdaptedPoint(interaction.getEndPt)
//
// shape match {
// case sq:ISquaredShape =>
// updateShapeFromCentre(sq, startPt, endPt.getX)
// shape.setModified(true)
// action.drawing.get.setModified(true)
// case fh:IFreehand =>
// val lastPoint = fh.getPtAt(-1)
// if(!LNumber.equalsDouble(lastPoint.getX, endPt.getX, 0.0001) &&
// !LNumber.equalsDouble(lastPoint.getY, endPt.getY, 0.0001))
// fh.addPoint(endPt)
// case rec:IRectangularShape =>
// updateShapeFromDiag(rec, startPt, endPt)
// shape.setModified(true)
// action.drawing.get.setModified(true)
// case _ =>
// }
// }
//
//
// /**
// * @param shape The shape to analyse.
// * @return The gap that must respect the pencil to not allow shape to
// disappear when they are too small.
// * @since 3.0
// */
// private def getGap(shape:IShape) : Double = {
// // These lines are necessary to avoid shape to disappear. It appends when the
// borders position is INTO. In this case, the
// // minimum radius must be computed using the thickness and the double size.
// if(shape.isBordersMovable && shape.getBordersPosition==BorderPos.INTO)
// shape.getThickness + (if(shape.isDbleBorderable && shape.hasDbleBord)
// shape.getDbleBordSep else 0.0)
// else 1.0
// }
//
//
// private def updateShapeFromCentre(shape:ISquaredShape, startPt:IPoint,
// endX:Double) {
// val sx = startPt.getX
// val radius = Math.max(if(sx<endX) endX-sx else sx - endX, getGap(shape))
// shape.setPosition(sx-radius, startPt.getY+radius)
// shape.setWidth(radius*2.0)
// }
//
//
// private def updateShapeFromDiag(shape:IRectangularShape, startPt:IPoint,
// endPt:IPoint) {
// val gap = getGap(shape)
// var v1 = startPt.getX
// var v2 = endPt.getX
// var tlx = Double.NaN
// var tly = Double.NaN
// var brx = Double.NaN
// var bry = Double.NaN
// var ok = true
//
// if(Math.abs(v1-v2)>gap)
// if(v1<v2) {
// brx = v2
// tlx = v1
// }
// else {
// brx = v1
// tlx = v2
// }
// else ok = false
//
// v1 = startPt.getY
// v2 = endPt.getY
//
// if(Math.abs(v1-v2)>gap)
// if(v1<v2) {
// bry = v2
// tly = v1
// }
// else {
// bry = v1
// tly = v2
// }
// else ok = false
//
// if(ok) {
// shape.setPosition(tlx, bry)
// shape.setWidth(brx - tlx)
// shape.setHeight(bry - tly)
// }
// }
// }
//
//
//
// /**
// * Maps a mouse press interaction to an action that asks and adds a picture
// into the drawing.
// */
// private sealed class Press2InsertPicture(pencil:Pencil) extends
// InteractorImpl[InsertPicture, Press, Pencil](pencil, false,
// classOf[InsertPicture], classOf[Press]) {
// override def initAction() {
// action.setDrawing(instrument.canvas.getDrawing)
// action.setShape(ShapeFactory.createPicture(instrument.getAdaptedPoint(interaction.getPoint)))
// action.setFileChooser(instrument.pictureFileChooser)
// }
//
// override def isConditionRespected =
// instrument.currentChoice==EditionChoice.PICTURE &&
// interaction.getButton==MouseEvent.BUTTON1
// }
//
//
//
// private sealed class Press2AddShape(pencil:Pencil) extends
// PencilInteractor[Press](pencil, false, classOf[Press]) {
// override def initAction() {
// super.initAction
// val shape = action.shape.get
// shape match {
// case shape1: IPositionShape =>
// shape1.setPosition(instrument.getAdaptedPoint(interaction.getPoint))
// shape.setModified(true)
// case _ =>
// }
// }
//
// override def isConditionRespected =
// (instrument.currentChoice==EditionChoice.GRID ||
// instrument.currentChoice==EditionChoice.DOT ||
// instrument.currentChoice==EditionChoice.AXES) &&
// interaction.getButton==MouseEvent.BUTTON1
// }
//
//
//
// /**
// * When a user starts to type a text using the text setter and then he clicks
// somewhere else
// * in the canvas, the text typed must be added (if possible to the canvas)
// before starting typing
// * a new text.
// */
// private sealed class Press2AddText(pencil:Pencil) extends
// PencilInteractor[Press](pencil, false, classOf[Press]) {
// override def initAction() {
// action.setDrawing(instrument.canvas.getDrawing)
// action.setShape(ShapeFactory.createText(ShapeFactory.createPoint(instrument.textSetter.relativePoint),
// instrument.textSetter.getTextField.getText))
// }
//
// // The action is created only when the user uses the text setter and the text
// field of the text setter is not empty.
// override def isConditionRespected =
// instrument.currentChoice==EditionChoice.TEXT &&
// instrument.textSetter.isActivated &&
// instrument.textSetter.getTextField.getText.length>0
// }
//
//
// /**
// * This link maps a press interaction to activate the instrument
// * that allows to add and modify some texts.
// */
// private sealed class Press2InitTextSetter(pencil:Pencil) extends
// InteractorImpl[InitTextSetter, Press, Pencil](pencil, false,
// classOf[InitTextSetter], classOf[Press]) {
// override def initAction() {
// action.setText("")
// action.setTextShape(null)
// action.setInstrument(instrument.textSetter)
// action.setTextSetter(instrument.textSetter)
// action.setAbsolutePoint(ShapeFactory.createPoint(
// SwingUtilities.convertPoint(instrument.canvas.asInstanceOf[LCanvas],interaction.getPoint,
// instrument.layers)))
// action.setRelativePoint(instrument.getAdaptedPoint(interaction.getPoint))
// }
//
// override def isConditionRespected =
// (instrument.currentChoice==EditionChoice.TEXT||
// instrument.currentChoice==EditionChoice.PLOT) &&
// interaction.getButton==MouseEvent.BUTTON1
// }
