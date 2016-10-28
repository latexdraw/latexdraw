package net.sf.latexdraw.actions.shape

import net.sf.latexdraw.actions.Modifying
import org.malai.undo.Undoable
import org.malai.action.Action
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.models.interfaces.shape.IShape
import net.sf.latexdraw.lang.LangTool

/**
 * This action mirrors a shape.<br>
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
 * 2013-03-07<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class MirrorShapes extends Action with ShapeAction[IShape] with Undoable with Modifying {
	/** If true, the mirroring is horizontal. */
	var _horizontally = true

	override protected def doActionBody() {
		val sh = _shape.get

		if(_horizontally)
			sh.mirrorHorizontal(sh.getGravityCentre)
		else
			sh.mirrorVertical(sh.getGravityCentre)
		sh.setModified(true)
	}


	override def canDo = _shape.isPresent


	override def undo() {
		doActionBody()
	}

	override def redo() {
		doActionBody()
	}

	override def getUndoName = LangTool.INSTANCE.getStringActions("Actions.7")

	override def isRegisterable = true

	/**
	 * If true, the mirroring is horizontal. Otherwise, vertical.
	 */
	def setHorizontally(horiz:Boolean) {
		_horizontally = horiz
	}
}