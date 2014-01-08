package net.sf.latexdraw.instruments

import java.awt.Component
import java.awt.Point
import java.util.List
import java.util.Objects

import javax.swing.JFileChooser
import javax.swing.SwingUtilities

import net.sf.latexdraw.actions.shape.AddShape
import net.sf.latexdraw.actions.shape.InitTextSetter
import net.sf.latexdraw.actions.shape.InsertPicture
import net.sf.latexdraw.badaboom.BadaboomCollector
import net.sf.latexdraw.filters.PictureFilter
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape._
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos
import net.sf.latexdraw.glib.ui.ICanvas
import net.sf.latexdraw.glib.ui.LMagneticGrid
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK
import net.sf.latexdraw.util.LNumber

import org.malai.instrument.Instrument
import org.malai.instrument.Link
import org.malai.interaction.Interaction
import org.malai.interaction.library.Press
import org.malai.stateMachine.MustAbortStateMachineException
import org.malai.swing.instrument.library.WidgetZoomer
import org.malai.swing.interaction.library.AbortableDnD
import org.malai.swing.interaction.library.MultiClick
import org.malai.swing.widget.MLayeredPane

/**
 * This instrument allows to draw shapes.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 05/13/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class Pencil(canvas : ICanvas, val textSetter:TextSetter, val layers:MLayeredPane) extends CanvasInstrument(canvas) {
	/** The current editing choice (rectangle, ellipse, etc.) of the instrument. */
	var _currentChoice = EditionChoice.RECT

	/** The file chooser used to select pictures. */
	protected lazy val _pictureFileChooser = {
		val fc = new JFileChooser()
		fc.setMultiSelectionEnabled(false)
		fc.setAcceptAllFileFilterUsed(true)
		fc.setFileFilter(new PictureFilter)
		fc
	}

	protected lazy val _groupParams = {
		val group = ShapeFactory.createGroup(false)
		group.addShape(ShapeFactory.createRectangle(false))
		group.addShape(ShapeFactory.createDot(ShapeFactory.createPoint, false))
		group.addShape(ShapeFactory.createGrid(false, ShapeFactory.createPoint))
		group.addShape(ShapeFactory.createAxes(false, ShapeFactory.createPoint))
		group.addShape(ShapeFactory.createText(false))
		group.addShape(ShapeFactory.createCircleArc(false))
		group.addShape(ShapeFactory.createPolyline(false))
		group.addShape(ShapeFactory.createBezierCurve(false))
		group.addShape(ShapeFactory.createFreeHand(false))
		group
	}

	override def setActivated(activated:Boolean) {
		if(this.activated!=activated)
			super.setActivated(activated)
	}

	override def interimFeedback() {
		canvas.setTempView(null)
		canvas.refresh
	}

	override protected def initialiseLinks() {
		try{
			addLink(new Press2AddShape(this))
			addLink(new Press2AddText(this))
			addLink(new Press2InsertPicture(this))
			addLink(new DnD2AddShape(this))
			addLink(new MultiClic2AddShape(this))
			addLink(new Press2InitTextSetter(this))
		}catch{ case e : Exception => BadaboomCollector.INSTANCE.add(e) }
	}

	/**
	 * @return The shape that contains the parameters of the pencil. These parameters
	 * will be used for the creation of new shapes.
	 * @since 3.0
	 */
	def groupParams() = _groupParams

	/**
	 * @return An instance of a shape configured (thickness, colours, etc.) with the parameters of the pencil.
	 * @since 3.0
	 */
	def createShapeInstance() = setShapeParameters(_currentChoice.createShapeInstance)

	/**
	 * Configures the given shape with the parameters (e.g. thickness, colours, etc.) of the pencil.
	 * @param shape The shape to configure.
	 * @return The modified shape given as argument.
	 * @since 3.0
	 */
	def setShapeParameters(shape:IShape) = {
		if(shape.isInstanceOf[IModifiablePointsShape] && !shape.isInstanceOf[IFreehand]) {//FIXME
			val mod = shape.asInstanceOf[IModifiablePointsShape]
			mod.addPoint(ShapeFactory.createPoint)
			mod.addPoint(ShapeFactory.createPoint)
		}

		shape.copy(groupParams)
		shape.setModified(true)
		shape
	}

	/** @return The file chooser used to select pictures. */
	def pictureFileChooser() = _pictureFileChooser

	/** @return The current editing choice. */
	def currentChoice() = _currentChoice

	/**
	 * Sets the current editing choice.
	 * @param currentChoice The new editing choice to set.
	 * @since 3.0
	 */
	def setCurrentChoice(currentChoice:EditionChoice) {
		_currentChoice = currentChoice
	}
}



/**
 * This class defines a generic link for the pencil.
 * @author Arnaud Blouin
 * @since 3.0
 * @version 3.0
 */
private abstract sealed class PencilLink[I <: Interaction](pencil:Pencil, exec:Boolean, clazzInteraction:Class[I])
				extends Link[AddShape, I, Pencil](pencil, false, classOf[AddShape], clazzInteraction) {
	protected var tmpShape : IViewShape = _

	override def initAction() {
		val sh = instrument.createShapeInstance
		tmpShape = View2DTK.getFactory.createView(sh)
		action.setShape(sh)
		action.setDrawing(instrument.canvas.getDrawing)
		instrument.canvas.setTempView(tmpShape)
	}

	override def interimFeedback() {
		tmpShape.update
		instrument.canvas.refresh
	}
}



/**
 * This link allows to create shapes using a multi-clic interaction.
 */
private sealed class MultiClic2AddShape(pencil:Pencil) extends PencilLink[MultiClick](pencil, false, classOf[MultiClick]) {
	// To avoid the overlapping with the DnD2AddShape, the starting interaction must be
	// aborted when the condition is not respected, i.e. when the selected shape type is
	// not devoted to the multi-clic interaction.
	override def isInteractionMustBeAborted() = !isConditionRespected

	override def updateAction() {
		val pts	= interaction.getPoints
		val currPoint = instrument.getAdaptedPoint(interaction.getCurrentPosition)
		val shape = action.shape.get.asInstanceOf[IModifiablePointsShape]

		if(shape.getNbPoints==pts.size && !interaction.isLastPointFinalPoint)
			shape.addPoint(ShapeFactory.createPoint(currPoint.getX, currPoint.getY), pts.size-1)
		else
			shape.setPoint(currPoint.getX, currPoint.getY, -1)

		// Curves need to be balanced.
		if(shape.isInstanceOf[IControlPointShape])
			shape.asInstanceOf[IControlPointShape].balance

		shape.setModified(true)
		action.drawing.get.setModified(true)
	}


	override def initAction() {
		super.initAction
		val shape = action.shape.get

		if(shape.isInstanceOf[IModifiablePointsShape]) {
			val modShape = shape.asInstanceOf[IModifiablePointsShape]
			val pt = instrument.getAdaptedPoint(interaction.getPoints.get(0))
			modShape.setPoint(pt, 0)
			modShape.setPoint(pt.getX+1, pt.getY+1, 1)
		}
	}

	override def isConditionRespected() = instrument.currentChoice==EditionChoice.POLYGON ||
			instrument.currentChoice==EditionChoice.LINES || instrument.currentChoice==EditionChoice.BEZIER_CURVE ||
			instrument.currentChoice==EditionChoice.BEZIER_CURVE_CLOSED

	override def interactionStarts(inter:Interaction) {
		super.interactionStarts(inter)
		if(instrument.currentChoice==EditionChoice.POLYGON)
			interaction.setMinPoints(3)
		else
			interaction.setMinPoints(2)
	}
}



/**
 * This link allows to create shapes using a drag-and-drop interaction.
 */
private sealed class DnD2AddShape(pencil:Pencil) extends PencilLink[AbortableDnD](pencil, false, classOf[AbortableDnD]) {
	override def initAction() {
		super.initAction
		action.shape match {
			case Some(shape) =>
				val ec = instrument.currentChoice
				val pt = instrument.getAdaptedPoint(interaction.getStartPt)

				// For squares and circles, the centre of the shape is the reference point during the creation.
				shape match {
					case sq:ISquaredShape =>
							sq.setPosition(pt.getX-1, pt.getY-1)
							sq.setWidth(2.0)
					case fh:IFreehand => fh.addPoint(ShapeFactory.createPoint(pt.getX, pt.getY))
					case _ => shape.translate(pt.getX, pt.getY)
				}
			case _ =>
		}
	}


	override def isConditionRespected() = {
		val ec = instrument.currentChoice
		ec==EditionChoice.RECT || ec==EditionChoice.ELLIPSE || ec==EditionChoice.SQUARE || ec==EditionChoice.CIRCLE ||
		ec==EditionChoice.RHOMBUS || ec==EditionChoice.TRIANGLE  || ec==EditionChoice.CIRCLE_ARC || ec==EditionChoice.FREE_HAND;
	}


	override def updateAction() {
		val shape = action.shape.get
		val ec = instrument.currentChoice
		// Getting the points depending on the current zoom.
		val startPt = instrument.getAdaptedPoint(interaction.getStartPt)
		val endPt   = instrument.getAdaptedPoint(interaction.getEndPt)

		shape match {
			case sq:ISquaredShape =>
					updateShapeFromCentre(sq, startPt, endPt.getX)
					shape.setModified(true)
					action.drawing.get.setModified(true)
			case fh:IFreehand =>
				val lastPoint = fh.getPtAt(-1)
				if(!LNumber.equalsDouble(lastPoint.getX, endPt.getX, 0.0001) && !LNumber.equalsDouble(lastPoint.getY, endPt.getY, 0.0001))
					fh.addPoint(endPt)
			case rec:IRectangularShape =>
					updateShapeFromDiag(rec, startPt, endPt)
					shape.setModified(true)
					action.drawing.get.setModified(true)
			case _ =>
		}
	}


	/**
	 * @param shape The shape to analyse.
	 * @return The gap that must respect the pencil to not allow shape to disappear when they are too small.
	 * @since 3.0
	 */
	private def getGap(shape:IShape) : Double = {
		// These lines are necessary to avoid shape to disappear. It appends when the borders position is INTO. In this case, the
		// minimum radius must be computed using the thickness and the double size.
		if(shape.isBordersMovable && shape.getBordersPosition()==BorderPos.INTO)
			shape.getThickness + (if(shape.isDbleBorderable && shape.hasDbleBord) shape.getDbleBordSep else 0.0)
		else 1.0
	}


	private def updateShapeFromCentre(shape:ISquaredShape, startPt:IPoint, endX:Double) {
		val sx = startPt.getX
		val radius = Math.max(if(sx<endX) endX-sx else sx - endX, getGap(shape))
		shape.setPosition(sx-radius, startPt.getY+radius)
		shape.setWidth(radius*2.0)
	}


	private def updateShapeFromDiag(shape:IRectangularShape, startPt:IPoint, endPt:IPoint) {
		val gap = getGap(shape)
		var v1 = startPt.getX
		var v2 = endPt.getX
		var tlx = Double.NaN
		var tly = Double.NaN
		var brx = Double.NaN
		var bry = Double.NaN
		var ok = true

		if(Math.abs(v1-v2)>gap)
			if(v1<v2) {
				brx = v2
				tlx = v1
			}
			else {
				brx = v1
				tlx = v2
			}
		else ok = false

		v1 = startPt.getY
		v2 = endPt.getY

		if(Math.abs(v1-v2)>gap)
			if(v1<v2) {
				bry = v2
				tly = v1
			}
			else {
				bry = v1
				tly = v2
			}
		else ok = false

		if(ok) {
			shape.setPosition(tlx, bry)
			shape.setWidth(brx - tlx)
			shape.setHeight(bry - tly)
		}
	}
}



/**
 * Maps a mouse press interaction to an action that asks and adds a picture into the drawing.
 */
private sealed class Press2InsertPicture(pencil:Pencil) extends Link[InsertPicture, Press, Pencil](pencil, false, classOf[InsertPicture], classOf[Press]) {
	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
		action.setShape(ShapeFactory.createPicture(true, instrument.getAdaptedPoint(interaction.getPoint)))
		action.setFileChooser(instrument.pictureFileChooser)
	}

	override def isConditionRespected() = instrument.currentChoice==EditionChoice.PICTURE
}



private sealed class Press2AddShape(pencil:Pencil) extends PencilLink[Press](pencil, false, classOf[Press]) {
	override def initAction() {
		super.initAction
		val shape = action.shape.get
		if(shape.isInstanceOf[IPositionShape]) {
			shape.asInstanceOf[IPositionShape].setPosition(instrument.getAdaptedPoint(interaction.getPoint))
			shape.setModified(true)
		}
	}

	override def isConditionRespected() = instrument.currentChoice==EditionChoice.GRID || instrument.currentChoice==EditionChoice.DOT || instrument.currentChoice==EditionChoice.AXES;
}



/**
 * When a user starts to type a text using the text setter and then he clicks somewhere else
 * in the canvas, the text typed must be added (if possible to the canvas) before starting typing
 * a new text.
 */
private sealed class Press2AddText(pencil:Pencil) extends PencilLink[Press](pencil, false, classOf[Press]) {
	override def initAction() {
		action.setDrawing(instrument.canvas.getDrawing)
		action.setShape(ShapeFactory.createText(true, ShapeFactory.createPoint(instrument.textSetter.relativePoint), instrument.textSetter.getTextField.getText))
	}

	// The action is created only when the user uses the text setter and the text field of the text setter is not empty.
	override def isConditionRespected() = instrument.textSetter.isActivated && instrument.textSetter.getTextField.getText.length>0
}


/**
 * This link maps a press interaction to activate the instrument
 * that allows to add and modify some texts.
 */
private sealed class Press2InitTextSetter(pencil:Pencil) extends Link[InitTextSetter, Press, Pencil](pencil, false, classOf[InitTextSetter], classOf[Press]) {
	override def initAction() {
		action.setText("")
		action.setTextShape(null)
		action.setInstrument(instrument.textSetter)
		action.setTextSetter(instrument.textSetter)
		action.setAbsolutePoint(ShapeFactory.createPoint(
				SwingUtilities.convertPoint(interaction.getTarget.asInstanceOf[Component],interaction.getPoint, instrument.layers)))
		action.setRelativePoint(instrument.getAdaptedPoint(interaction.getPoint))
	}

	override def isConditionRespected() = instrument.currentChoice==EditionChoice.TEXT
}
