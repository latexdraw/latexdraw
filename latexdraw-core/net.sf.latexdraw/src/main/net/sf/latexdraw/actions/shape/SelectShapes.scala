package net.sf.latexdraw.actions.shape

import scala.collection.JavaConversions.asScalaBuffer

import org.malai.action.Action

import net.sf.latexdraw.actions.DrawingAction
import net.sf.latexdraw.actions.Modifying
import net.sf.latexdraw.actions.ShapesAction

/**
 * This action allows to (un-)select shapes.<br>
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
class SelectShapes extends Action with ShapesAction with DrawingAction with Modifying {
	override def isRegisterable() = true


	override def doActionBody() {
		val selection = _drawing.get.getSelection

		if(_shapes.isEmpty)
			selection.clear
		else {
			for(i <- selection.size-1 to 0 by -1)
				if(!_shapes.contains(selection.getShapeAt(i)))
					selection.removeShape(i)

			for(sh <- _shapes)
				if(!selection.contains(sh))
					selection.addShape(sh)
		}
	}


	override def cancelledBy(action : Action) = action.isInstanceOf[SelectShapes] || action.isInstanceOf[CutShapes] || action.isInstanceOf[DeleteShapes]


	def canDo() = _drawing.isDefined
}
