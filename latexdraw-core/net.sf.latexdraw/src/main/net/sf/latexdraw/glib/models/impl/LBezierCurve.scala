package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve
import net.sf.latexdraw.glib.models.interfaces.shape.ILine
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IShape

/**
 * Defines a model of a Bezier curve.<br>
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
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
private[impl] class LBezierCurve(var isClosed:Boolean, uniqueID:Boolean)
	extends LAbstractCtrlPointShape(uniqueID) with IBezierCurve with LArrowableShape {

	arrows += ShapeFactory.createArrow(this)
	arrows += ShapeFactory.createArrow(this)

	/**
	 * Creates a bezier curve with two points.
	 * @param point The first point of the curve.
	 * @param point2 The second point of the curve.
	 * @param uniqueID uniqueID True: the model will have a unique ID.
	 */
	def this(point:IPoint, point2:IPoint, closed:Boolean, uniqueID:Boolean) = {
		this(closed, uniqueID)
		addPoint(point)
		addPoint(point2)
	}

	override def getArrowLine(arrow:IArrow) : ILine = {
		if(getNbPoints()<2) return null
		arrows.indexOf(arrow) match {
			case 0 => return ShapeFactory.createLine(points.get(0), firstCtrlPts.get(0))
			case 1 => return ShapeFactory.createLine(points.get(points.size-1), firstCtrlPts.get(points.size-1))
			case _ => return null
		}
	}

	override def setIsClosed(isClosed:Boolean) {
		this.isClosed = isClosed
	}

	override def isDbleBorderable() = true

	override def isFillable() = true

	override def isInteriorStylable() = true

	override def copy(sh:IShape) {
		super[LAbstractCtrlPointShape].copy(sh)
		super[LArrowableShape].copy(sh)
	}

	override def isLineStylable() = true

	override def isShadowable() = true

	override def isThicknessable() = true

	override def shadowFillsShape() = false

	override def isShowPtsable() = true
}
