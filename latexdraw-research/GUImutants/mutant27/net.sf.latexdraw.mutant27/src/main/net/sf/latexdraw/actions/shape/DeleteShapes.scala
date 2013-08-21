package net.sf.latexdraw.actions.shape

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.mutable.Buffer

import org.malai.action.Action
import org.malai.undo.Undoable

import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapesAction

/**
 * This action removes shapes from a drawing.<br>
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
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class DeleteShapes extends Action with ShapesAction with DrawingAction with Undoable with Modifying {
	/** The index of the deleted shapes into the original list. */
	protected var positionShapes : Buffer[Int] = null


	def isRegisterable() = true


	protected def doActionBody() = {
		val drawingSh = _drawing.get.getShapes
		positionShapes = for(shape <- _shapes) yield drawingSh.indexOf(shape)
		deleteShapes
	}


	override def canDo() = _drawing.isDefined && !_shapes.isEmpty


	/**
	 * Delete the shapes from the drawing.
	 * @since 3.0
	 */
	private def deleteShapes() {
		val dr = _drawing.get
		_shapes.foreach(sh => dr.removeShape(sh))
		dr.setModified(true)
	}


	override def undo() {
		val dr = _drawing.get
		for(i <- 0 until positionShapes.length) dr.addShape(_shapes.get(i), positionShapes.get(i))

		dr.setModified(true)
	}


	override def redo() {
		deleteShapes()
	}


	override def getUndoName() = "delete"
}
