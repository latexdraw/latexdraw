package net.sf.latexdraw.actions.shape

import java.util.ArrayList

import scala.collection.JavaConversions.asScalaBuffer

import org.malai.action.Action
import org.malai.undo.Undoable

import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.ui.LMagneticGrid
import net.sf.latexdraw.util.LResources

/**
 * This action pastes the copied or cut shapes.<br>
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
class PasteShapes extends Action with DrawingAction with Undoable with Modifying {
	/** The cut or copy action. */
	var _copy : CopyShapes = _

	/** The magnetic grid to use. */
	var _grid : LMagneticGrid = _


	override def canDo() = _copy!=null && _grid!=null && _drawing.isDefined


	override def isRegisterable() = true


	override def doActionBody() {
		val dr = _drawing.get
		// While pasting cut shapes, the first paste must be at the same position that the original shapes.
		// But for pasting after just copying, a initial gap must be used.
		if(!(_copy.isInstanceOf[CutShapes]))
			_copy.nbTimeCopied+=1

		val gapPaste = if(_grid.isMagnetic()) _grid.getGridSpacing() else 10
		val gap = _copy.nbTimeCopied*gapPaste
		val dups = new ArrayList[IShape]

		_copy.copiedShapes.foreach{shape =>
			val sh = ShapeFactory.duplicate(shape)
			dups.add(sh)
			sh.translate(gap, gap)
			dr.addShape(sh)
		}

		if(_copy.isInstanceOf[CutShapes])
			_copy.nbTimeCopied+=1

		dr.setSelection(dups)
		dr.setModified(true)
	}


	override def undo() {
		var i = 0
		val nbShapes = _copy.copiedShapes.size
		val dr = _drawing.get

		while(i<nbShapes && !dr.isEmpty) {
			dr.removeShape(dr.size-1)
			i+=1
		}

		_copy.nbTimeCopied-=1
		dr.setModified(true)
	}


	override def redo() {
		doActionBody()
	}


	override def getUndoName() = LResources.LABEL_PASTE

	/**
	 * @param copy the copy to set.
	 */
	def setCopy(copy : CopyShapes) {
		_copy = copy
	}

	def copy = _copy

	/** Sets the magnetic grisd to use. */
	def setGrid(grid:LMagneticGrid) {
		_grid = grid
	}
}
