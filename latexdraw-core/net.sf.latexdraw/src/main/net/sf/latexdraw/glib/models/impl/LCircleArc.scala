package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp
import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape

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
private[impl] class LCircleArc(tl:IPoint, width:Double, uniqueID:Boolean) extends LSquaredShape(tl, width, uniqueID) with LArc with ICircleArc {
	override def copy(sh:IShape) {
		super[LSquaredShape].copy(sh)
		super[LArc].copy(sh)
		sh match {//FIXME somewhere else
			case arc:IArcProp =>
				startAngle 		= arc.getAngleStart
				endAngle 		= arc.getAngleEnd
        style			= arc.getArcStyle
			case _ =>
		}
	}
}
