package net.sf.latexdraw.actions.shape

import java.util.{ArrayList, List}

import net.sf.latexdraw.actions.{Modifying, ShapeAction}
import net.sf.latexdraw.lang.LangTool
import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.shape.{IGroup, IPoint}
import net.sf.latexdraw.view.MagneticGrid
import org.malai.action.Action
import org.malai.undo.Undoable

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
	var _grid : MagneticGrid = null

	val _listPts : List[List[IPoint]] = new ArrayList[List[IPoint]]()


	protected def doActionBody() {
		var list : List[IPoint] = null

		shape.get.getShapes.forEach{sh =>
			list = new ArrayList[IPoint]
			_listPts.add(list)
			sh.getPoints.forEach(pt => list.add(ShapeFactory.createPoint(pt)))
		}
		redo
	}


	override def canDo = _shape!=null && _grid!=null

	override def undo() {
		var i = 0
		var j = 0
		shape.get.getShapes.forEach{sh =>
			j=0
			sh.getPoints.forEach{pt => pt.setPoint(_listPts.get(i).get(j).getX, _listPts.get(i).get(j).getY); j=j+1}
			i=i+1
			sh.setModified(true)
		}
	}


	override def redo() {
		var i = 0
		var j = 0
		shape.get.getShapes.forEach{sh =>
			j=0
			sh.getPoints.forEach{pt => pt.setPoint(_grid.getTransformedPointToGrid(pt.toPoint2D)); j=j+1}
			i=i+1
			sh.setModified(true)
		}
	}


	override def flush() {
		super.flush
		_listPts.clear
	}


	override def getUndoName = LangTool.INSTANCE.getStringActions("Actions.33")

	override def isRegisterable = hadEffect

	/** Sets the magnetic grid to use. */
	def setGrid(grid:MagneticGrid) {
		_grid = grid
	}
}
