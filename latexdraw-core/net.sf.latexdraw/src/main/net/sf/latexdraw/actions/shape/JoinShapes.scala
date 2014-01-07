package net.sf.latexdraw.actions.shape

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.mutable.Buffer
import org.malai.action.Action
import org.malai.undo.Undoable
import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapesAction
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.lang.LangTool
import net.sf.latexdraw.glib.models.ShapeFactory

/**
 * This action joins shapes.<br>
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
class JoinShapes extends Action with ShapesAction with DrawingAction with Undoable with Modifying {
	/** The added group of shapes. */
	val addedGroup = ShapeFactory.createGroup(true)


	override protected def doActionBody() {
		joinShapes
	}


	override def canDo = !_shapes.isEmpty && _drawing.isDefined


	private def joinShapes() {
		val dr = _drawing.get
		val drawingSh = dr.getShapes
		// creating a map from the shapes to join and their index
		val map = _shapes.map{sh => (drawingSh.indexOf(sh), sh)}.toMap

		map.keySet.toIndexedSeq.sorted.map{index => map.get(index).get}.foreach{sh =>
			dr.removeShape(sh)
			addedGroup.addShape(sh)
		}

		dr.addShape(addedGroup)
		dr.setModified(true)
	}


	override def undo() {
		val dr = _drawing.get
		val drawingSh = dr.getShapes
		val map = _shapes.map{sh => (sh, drawingSh.indexOf(sh))}.toMap

		dr.removeShape(addedGroup)
		addedGroup.getShapes.foreach{sh => dr.addShape(sh, map.get(sh).get)}
		addedGroup.clear
		dr.setModified(true)
	}


	override def redo() {
		joinShapes
	}


	override def getUndoName() = LangTool.INSTANCE.getStringOthers("UndoRedoManager.join")


	override def isRegisterable() = true
}
