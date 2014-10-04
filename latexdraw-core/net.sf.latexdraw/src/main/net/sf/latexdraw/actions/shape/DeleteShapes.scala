package net.sf.latexdraw.actions.shape

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.mutable.Buffer
import org.malai.action.Action
import org.malai.undo.Undoable
import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapesAction
import net.sf.latexdraw.lang.LangTool

/**
 * This action removes shapes from a drawing.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
	var positionShapes : Buffer[Int] = _


	def isRegisterable = true

	protected def doActionBody() = {
		val dr = _drawing.get
		val drawingSh = dr.getShapes
		positionShapes = _shapes.map{sh =>
			val pos = drawingSh.indexOf(sh)
			dr.removeShape(sh)
			pos
		}
		dr.setModified(true)
	}

	override def canDo = _drawing.isDefined && !_shapes.isEmpty

	override def undo() {
		val dr = _drawing.get
		for(i <- positionShapes.length-1 to 0 by -1)
			dr.addShape(_shapes.get(i), positionShapes(i))
		dr.setModified(true)
	}

	override def redo() {
		doActionBody
	}

	override def getUndoName = LangTool.INSTANCE.getStringActions("Actions.30")
}
