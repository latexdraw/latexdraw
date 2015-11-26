package net.sf.latexdraw.actions.shape

import org.malai.undo.Undoable
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.util.LangTool
import java.util.stream.Collectors

/**
 * This action cuts the selected shapes.<br>
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
 * 2012-04-20<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class CutShapes extends CopyShapes with Undoable with Modifying {
	/** The index of the cut shapes. */
	var _positionShapes : java.util.List[Int] = _


	override def doActionBody() {
		// Removing the shapes.
		val drawingSh	= selection.get.drawing.get.getShapes

		_positionShapes = selection.get.shapes.stream.map[Int]{sh =>
			copiedShapes.add(sh)
			drawingSh.indexOf(sh)
		}.collect(Collectors.toList())

		deleteShapes
		selection.get.shapes.clear
	}


	/**
	 * Delete the shapes from the drawing.
	 * @since 3.0
	 */
	private def deleteShapes() {
		val dr = _selection.get.drawing.get
		copiedShapes.forEach{sh => dr.removeShape(sh)}
		dr.setModified(true)
	}


	override def redo() {
		deleteShapes
	}


	override def undo() {
		val dr = _selection.get.drawing.get
		for(i <- 0 to _positionShapes.size-1) dr.addShape(copiedShapes.get(i), _positionShapes.get(i))
		dr.setModified(true)
	}


	override def getUndoName = LangTool.INSTANCE.getBundle.getString("LaTeXDrawFrame.44")
}
