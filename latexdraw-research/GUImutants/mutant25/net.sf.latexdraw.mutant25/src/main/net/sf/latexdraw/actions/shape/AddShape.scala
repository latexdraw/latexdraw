package net.sf.latexdraw.actions.shape

import org.malai.action.Action
import org.malai.undo.Undoable

import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.lang.LangTool

/**
 * This action adds a shape to a drawing.<br>
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
class AddShape extends Action with ShapeAction[IShape] with DrawingAction with Undoable with Modifying {

	override protected def doActionBody() {
		_drawing.get.addShape(shape.get)
		_drawing.get.setModified(true)
	}


	override def isRegisterable() = true


	override def getUndoName() = LangTool.INSTANCE.getStringOthers("UndoRedoManager.create") //$NON-NLS-1$


	override def redo() {
		doActionBody()
	}


	override def undo() {
		//Mutant25
		//drawing.get.removeShape(shape.get)
		drawing.get.setModified(true)
	}


	override def canDo() = _drawing.isDefined && _shape.isDefined
}
