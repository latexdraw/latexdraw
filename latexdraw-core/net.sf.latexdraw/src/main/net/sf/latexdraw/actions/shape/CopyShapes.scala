package net.sf.latexdraw.actions.shape

import java.util.ArrayList

import scala.collection.JavaConversions.asScalaBuffer

import org.malai.action.Action

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * This action copies the selected shapes.<br>
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
class CopyShapes extends Action {
	/** The selection action. */
	protected[shape] var _selection : Option[SelectShapes] = None

	/** The copied shapes from the selection. */
	private[shape] var copiedShapes : java.util.List[IShape] = new ArrayList[IShape]()

	/** The number of times that the shapes have been copied. Use to compute the gap while pasting. */
	private[shape] var nbTimeCopied : Int = 0


	override protected def doActionBody() {
		_selection.get.shapes.foreach{shape => copiedShapes.add(DrawingTK.getFactory.duplicate(shape))}
	}


	override def cancelledBy(action : Action) = action.isInstanceOf[CopyShapes]


	override def isRegisterable() = true


	override def canDo() = _selection.isDefined && !_selection.get.shapes.isEmpty


	/**
	 * @param selection The selected shapes to copy or cut.
	 */
	def setSelection(selection : SelectShapes) {
		_selection = Some(selection)
	}

	def selection = _selection


	override def flush() {
		super.flush
		copiedShapes.clear
		_selection 	 = None
	}
}
