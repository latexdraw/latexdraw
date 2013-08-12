package net.sf.latexdraw.actions.shape

import org.malai.action.Action
import org.malai.undo.Undoable
import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapesAction
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.glib.models.interfaces.IGroup
import collection.JavaConversions._
import scala.collection.mutable.Buffer

/**
 * This action puts in background / foreground shapes.<br>
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
 * 2013-04-21<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class MoveBackForegroundShapes extends Action with ShapeAction[IGroup] with DrawingAction with Undoable with Modifying {
	/** Defines whether the shapes must be placed in the foreground. */
	var foreground : Boolean = false

	/** The former position of the shapes. */
	var formerId : Array[Int] = null

	/** The shapes sorted by their position. */
	var sortedSh : Buffer[IShape] = null


	override protected def doActionBody() {
		if(foreground)
			moveInForeground
		else
			moveInBackground

	}


	/** Puts the shapes in the foreground. */
	def moveInForeground() {
		val size = _shape.get.size
		formerId = Array.ofDim[Int](size)
		var sh : IShape = null
		val drawing = _drawing.get
		val drawingShapes = drawing.getShapes
		sortedSh = _shape.get.getShapes().sortBy(drawingShapes.indexOf(_))

		for(i <- 0 until size) {
			sh = sortedSh(i)
			formerId(i) = drawingShapes.indexOf(sh)
			drawing.removeShape(sh)
			drawing.addShape(sh)
		}
		drawing.setModified(true)
	}


	/** Puts the shapes in the background. */
	def moveInBackground() {
		val size = _shape.get.size
		formerId = Array.ofDim[Int](size)
		var sh : IShape = null
		val drawing = _drawing.get
		val drawingShapes = drawing.getShapes
		sortedSh = _shape.get.getShapes().sortBy(drawingShapes.indexOf(_))

		for(i <- size-1 to 0 by -1) {
			sh = sortedSh(i)
			formerId(i) = drawingShapes.indexOf(sh)
			drawing.removeShape(sh)
			drawing.addShape(sh, 0)
		}
		drawing.setModified(true)

	}


	override def canDo = _shape.isDefined && !_shape.get.isEmpty() && _drawing.isDefined

	override def undo() {
		val drawing = _drawing.get

		if(foreground) {
			var i = formerId.size-1
			sortedSh.reverse.foreach{sh =>
				drawing.removeShape(sh)
				drawing.addShape(sh, formerId(i))
				i -= 1
			}
		}
		else {
			var i = 0
			sortedSh.foreach{sh =>
				drawing.removeShape(sh)
				drawing.addShape(sh, formerId(i))
				i += 1
			}
		}

		drawing.setModified(true)
	}

	override def redo() {
		doActionBody()
	}

	override def getUndoName() = "move back/foreground"

	override def isRegisterable()  = true

	/** Defines whether the shapes must be placed in the foreground. */
	def setIsForeground(foreground:Boolean) { this.foreground = foreground }
}
