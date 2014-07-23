package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow
import net.sf.latexdraw.glib.models.interfaces.shape.ILine
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline
import net.sf.latexdraw.glib.models.interfaces.shape.IShape

/**
 * Defines a view of a polyline.<br>
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
private[impl] class LPolyline(uniqueID:Boolean) extends LPolygon(uniqueID) with IPolyline with LArrowableShape {
	arrows += ShapeFactory.createArrow(this)
	arrows += ShapeFactory.createArrow(this)

	/**
	 * Creates a model with two points.
	 * @param uniqueID True: the shape will have a unique ID.
	 */
	def this(point:IPoint, point2:IPoint, uniqueID:Boolean) = {
		this(uniqueID)
		require(point!=null && point2!=null)
		addPoint(point)
		addPoint(point2)
	}

	override def copy(sh:IShape) {
		super[LPolygon].copy(sh)
		super[LArrowableShape].copy(sh)
	}

	override def getArrowLine(arrow:IArrow) : ILine = {
		if(getNbPoints<2) return null
		val index = arrows.indexOf(arrow)
		index match {
			case 0 => return ShapeFactory.createLine(points.get(0), points.get(1))
			case 1 => return ShapeFactory.createLine(points.get(getNbPoints-1), points.get(getNbPoints-2))
			case _ => return null
		}
	}

	override def isFillable = getNbPoints>2

	override def shadowFillsShape() = false
}
