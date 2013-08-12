package net.sf.latexdraw.actions.shape

import scala.collection.JavaConversions.asScalaBuffer

import org.malai.action.Action
import org.malai.undo.Undoable

import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.lang.LangTool

/**
 * This action separates joined shapes.<br>
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
 * 2012-04-20<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class SeparateShapes extends Action with ShapeAction[IGroup] with DrawingAction with Modifying with Undoable {

	override def canDo() = _drawing.isDefined && _shape.isDefined && !_shape.get.isEmpty


	override protected def doActionBody() {
		val dr = _drawing.get
		val position = dr.getShapes.indexOf(_shape.get)
		val insertPos = if(position>=(dr.size-1))  -1 else position

		dr.removeShape(position)
		_shape.get.getShapes.foreach{sh => dr.addShape(sh, insertPos)}
		dr.setModified(true)
	}


	override def undo() {
		val dr = _drawing.get
		val position = dr.getShapes.indexOf(_shape.get.getShapeAt(0))
		val addPosition = if(position>=dr.size) -1 else position

		_shape.get.getShapes.foreach{_ => dr.removeShape(position)}

		dr.addShape(_shape.get, addPosition)
		dr.setModified(true)
	}


	override def redo() {
		doActionBody()
	}


	override def getUndoName() = LangTool.INSTANCE.getStringOthers("UndoRedoManager.seperate")


	override def isRegisterable() = true
}
