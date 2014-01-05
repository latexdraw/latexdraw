package net.sf.latexdraw.actions.shape

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer

import org.malai.action.Action
import org.malai.undo.Undoable

import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapeAction
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.ui.LMagneticGrid

/**
 * This action updates the given shapes to magnetic grid if activated.<br>
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
 * 2013-07-24<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class UpdateToGrid extends Action with ShapeAction[IGroup] with Undoable with Modifying {
	/** The magnetic grid to use. */
	var _grid : LMagneticGrid = null

	val _listPts : Buffer[Buffer[IPoint]] = new ListBuffer[Buffer[IPoint]]()


	protected def doActionBody() {
		var list : Buffer[IPoint] = null

		shape.get.getShapes.foreach{sh=>
			list = new ListBuffer[IPoint]
			_listPts.add(list)
			sh.getPoints.foreach(pt=> list.add(ShapeFactory.factory.createPoint(pt)))
		}
		redo
	}


	override def canDo() = _shape!=null && _grid!=null

	override def undo() {
		var i = 0
		var j = 0
		shape.get.getShapes.foreach{sh=>
			j=0
			sh.getPoints.foreach{pt=> pt.setPoint(_listPts.get(i).get(j).getX, _listPts.get(i).get(j).getY); j=j+1}
			i=i+1
			sh.setModified(true)
		}
	}


	override def redo() {
		var i = 0
		var j = 0
		shape.get.getShapes.foreach{sh=>
			j=0
			sh.getPoints.foreach{pt=> pt.setPoint(_grid.getTransformedPointToGrid(pt.toPoint2D)); j=j+1}
			i=i+1
			sh.setModified(true)
		}
	}


	override def flush() {
		super.flush
		_listPts.clear
	}


	override def getUndoName() = "Update to grid"

	override def isRegisterable() = hadEffect

	/** Sets the magnetic grid to use. */
	def setGrid(grid:LMagneticGrid) {
		_grid = grid
	}
}
