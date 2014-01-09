package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp
import net.sf.latexdraw.glib.models.ShapeFactory
import java.util.ArrayList
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow

/**
 * Defines a model of a rounded arc.<br>
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
 * 02/13/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
private class LCircleArc(tl:IPoint, width:Double, uniqueID:Boolean) extends LSquaredShape(tl, width, uniqueID) with LArc with ICircleArc {
	//FIXME should be common to all arcs
	arrows = new ArrayList[IArrow]()
	arrows.add(ShapeFactory.createArrow(this))
	arrows.add(ShapeFactory.createArrow(this))

//	override def duplicate() =
//		super.duplicate match {
//			case sh:ICircleArc => sh
//			case _ => null
//		}

	override def copy(sh:IShape) {//FIXME somewhere else
		super.copy(sh)

		sh match {
			case arc:IArcProp =>
				startAngle 		= arc.getAngleStart
				endAngle 		= arc.getAngleEnd;
				style			= arc.getArcStyle
			case _ =>
		}
	}
}
