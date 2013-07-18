package net.sf.latexdraw.actions.shape

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.mutable.Buffer

import org.malai.action.Action
import org.malai.undo.Undoable

import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapesAction
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.lang.LangTool

/**
 * This action joins shapes.<br>
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
class JoinShapes extends Action with ShapesAction with DrawingAction with Undoable with Modifying {
	/** The index of the joined shapes into the original list. */
	protected var positionShapes : Buffer[Int] = null

	/** The added group of shapes. */
	protected var addedGroup : IGroup = null


	override protected def doActionBody() {
		val drawingSh = _drawing.get.getShapes
		addedGroup = DrawingTK.getFactory().createGroup(true)
		positionShapes = shapes.map{sh => drawingSh.indexOf(sh)}
		joinShapes
	}


	override def canDo = !_shapes.isEmpty && _drawing.isDefined


	private def joinShapes() {
		val dr = _drawing.get
		_shapes.foreach{sh =>
			dr.removeShape(sh)
			addedGroup.addShape(sh)
		}

		dr.addShape(addedGroup)
		dr.setModified(true)
	}


	override def undo() {
		val dr = _drawing.get
		dr.removeShape(addedGroup)
		addedGroup.clear
		for(i <- 0 to positionShapes.length-1) dr.addShape(_shapes.get(i), positionShapes.get(i))
	}


	override def redo() {
		joinShapes
	}


	override def getUndoName() = LangTool.INSTANCE.getStringOthers("UndoRedoManager.join")


	override def isRegisterable()  = true
}
