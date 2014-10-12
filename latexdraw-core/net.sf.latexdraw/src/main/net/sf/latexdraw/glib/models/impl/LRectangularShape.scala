package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangularShape

/**
 * Defines a model of a rectangular shape.<br>
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
 * 02/16/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
private[impl] abstract class LRectangularShape(tl:IPoint, br:IPoint) extends LPositionShape(tl) with IRectangularShape {

	require(GLibUtilities.isValidPoint(tl) && GLibUtilities.isValidPoint(br) && tl.getX<br.getX && tl.getY<br.getY)

	points.add(ShapeFactory.createPoint(br.getX, tl.getY))
	points.add(ShapeFactory.createPoint(br))
	points.add(ShapeFactory.createPoint(tl.getX, br.getY))

	override def mirrorHorizontal(origin:IPoint) {
		super.mirrorHorizontal(origin)
		if(getWidth<0) {
			val tmp = ShapeFactory.createPoint(points.get(0))
			points.get(0).setPoint(points.get(1))
			points.get(1).setPoint(tmp)
			tmp.setPoint(points.get(2))
			points.get(2).setPoint(points.get(3))
			points.get(3).setPoint(tmp)
		}
	}

	override def mirrorVertical(origin:IPoint) {
		super.mirrorVertical(origin)
		if(getHeight<0) {
			val tmp = ShapeFactory.createPoint(points.get(0))
			points.get(0).setPoint(points.get(3))
			points.get(3).setPoint(tmp)
			tmp.setPoint(points.get(1))
			points.get(1).setPoint(points.get(2))
			points.get(2).setPoint(tmp)
		}
	}

	override def getHeight = points.get(2).getY - points.get(0).getY

	override def getWidth = points.get(1).getX - points.get(0).getX

	override def setWidth(width:Double) {
		if(GLibUtilities.isValidCoordinate(width) && width>0) {
			val xPos = points.get(points.size-1).getX + width
			points.get(1).setX(xPos)
			points.get(2).setX(xPos)
		}
	}

	override def setHeight(height:Double) {
		if(GLibUtilities.isValidCoordinate(height) && height>0) {
			val yPos = points.get(points.size-1).getY - height
			points.get(0).setY(yPos)
			points.get(1).setY(yPos)
		}
	}

	override def isBordersMovable = true

	override def isDbleBorderable = true

	override def isFillable = true

	override def isInteriorStylable = true

	override def isLineStylable = true

	override def isShadowable = true

	override def isThicknessable = true
}
