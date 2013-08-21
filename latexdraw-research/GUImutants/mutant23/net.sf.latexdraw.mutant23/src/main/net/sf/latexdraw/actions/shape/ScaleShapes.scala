package net.sf.latexdraw.actions.shape

import java.awt.geom.Rectangle2D
import org.malai.action.Action
import org.malai.undo.Undoable
import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.glib.models.interfaces.IShape.Position
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.util.LNumber

/**
 * This action scales a shape.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-03-07<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class ScaleShapes extends Action with ShapeAction[IGroup] with DrawingAction with Undoable with Modifying {
	/** The direction of the scaling. */
	protected var _refPosition : Option[Position] = None

	/** The new X position used to compute the scale factor. */
	protected var _newX : Double = Double.NaN

	/** The new Y position used to compute the scale factor. */
	protected var _newY : Double = Double.NaN

	/** The bound of the selected shapes used to perform the scaling. */
	private val bound : Rectangle2D = new Rectangle2D.Double()

	/** The old width of the selection. */
	private var oldWidth : Double = Double.NaN

	/** The old height of the selection. */
	private var oldHeight : Double = Double.NaN

	private var doneOnce = false



	override def isRegisterable() = hadEffect

	override def hadEffect = isDone && (!LNumber.INSTANCE.equals(oldWidth, bound.getWidth) || !LNumber.INSTANCE.equals(oldHeight, bound.getHeight))

	override def canDo() = _drawing.isDefined && _shape.isDefined && _refPosition.isDefined && isValidScales


	private def isValidScales = {
		_refPosition.get match {
			case Position.EAST => isValidScale(_newX) && scaledWidth(_newX)>1.0
			case Position.WEST => isValidScale(_newX) && scaledWidth(_newX)>1.0
			case Position.NORTH => isValidScale(_newY) && scaledHeight(_newY)>1.0
			case Position.SOUTH => isValidScale(_newY) && scaledHeight(_newY)>1.0
			case _ => isValidScale(_newX) && isValidScale(_newY) && scaledHeight(_newY)>1.0 && scaledWidth(_newX)>1.0
		}
	}


	private def isValidScale(scale : Double) = GLibUtilities.INSTANCE.isValidCoordinate(scale) && scale>0


	protected def doActionBody() {
		if(oldWidth.isNaN) {
			val br = _shape.get.getBottomRightPoint
			val tl = _shape.get.getTopLeftPoint
			oldWidth  = br.getX - tl.getX
			oldHeight = br.getY - tl.getY
			updateBound(tl, br)
		}
		redo
	}


	private def updateBound(tl : IPoint, br : IPoint) {
		bound.setFrameFromDiagonal(tl.getX, tl.getY, br.getX, br.getY)
	}


	override def undo() {
		val sh = _shape.get
		//Mutant23
		//sh.scale(oldWidth, oldHeight, _refPosition.get, bound)
		sh.setModified(true)
		_drawing.get.setModified(true)
		updateBound(sh.getTopLeftPoint, sh.getBottomRightPoint)
	}


	override def setShape(shape : IGroup) {
		super.setShape(shape)

		if(shape!=null)
			updateBound(shape.getTopLeftPoint, shape.getBottomRightPoint)
	}


	private def scaledHeight(y:Double) : Double = {
		_refPosition.get match {
			case ref if(ref.isSouth) => bound.getHeight + bound.getY - y
			case ref if(ref.isNorth) =>  bound.getHeight + y - bound.getMaxY
			case _ => 0.0
		}
	}


	private def scaledWidth(x:Double) : Double = {
		_refPosition.get match {
			case ref if(ref.isWest) => bound.getWidth + x - bound.getMaxX
			case ref if(ref.isEast) => bound.getWidth + bound.getX - x
			case _ => 0.0
		}
	}


	override def redo() {
		val sh = _shape.get
		sh.scale(scaledWidth(_newX), scaledHeight(_newY), _refPosition.get, bound)
		sh.setModified(true)
		_drawing.get.setModified(true)
		updateBound(sh.getTopLeftPoint, sh.getBottomRightPoint)
	}


	override def getUndoName() = "Resizing"


	def refPosition = _refPosition

	/**
	 * @param refPosition The reference position of the scaling.
	 */
	def refPosition_=(refPosition : Position) {
		_refPosition = Some(refPosition)
	}

	def newX = _newX

	/**
	 * @param newX The new X position used to compute the scale factor.
	 */
	def newX_=(newX : Double) {
		if(scaledWidth(newX)>1)
			_newX = newX
	}

	def newY = _newY

	/**
	 * @param newY The new Y position used to compute the scale factor.
	 */
	def newY_=(newY : Double) {
		if(scaledHeight(newY)>1)
			_newY = newY
	}
}
