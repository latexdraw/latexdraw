package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.GLibUtilities
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IArc
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow
import net.sf.latexdraw.glib.models.interfaces.shape.ILine
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint
import net.sf.latexdraw.glib.models.interfaces.shape.ArcStyle

/**
 * Defines a model of an arc.<br>
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
private[impl] trait LArc extends IArc with LArrowableShape {
	/** The style of the arc. */
	var style = ArcStyle.ARC

	/** The start angle of the arc. In radian. */
	var startAngle = 0.0

	/** The end angle of the arc. In radian. */
	var endAngle = 3.0*Math.PI/2.0

	arrows += ShapeFactory.createArrow(this)
	arrows += ShapeFactory.createArrow(this)


	override def getArrowLine(arrow:IArrow) : ILine = {
		if(getArrowAt(0)==arrow)
			return GLibUtilities.getTangenteAt(getTopLeftPoint, getBottomRightPoint, getGravityCentre, startAngle, startAngle<Math.PI)
		if(getArrowAt(1)==arrow)
			return GLibUtilities.getTangenteAt(getTopLeftPoint, getBottomRightPoint, getGravityCentre, endAngle, endAngle>=Math.PI)
		return null
	}

	override def isShowPtsable = true

	override def getAngleEnd = endAngle

	override def getAngleStart = startAngle

	override def getEndPoint: IPoint = {
		val grav = getGravityCentre
		ShapeFactory.createPoint(grav.getX+Math.cos(endAngle)*getHeight/2.0, grav.getY-Math.sin(endAngle)*getHeight/2.0)
	}

	override def getStartPoint: IPoint = {
		val grav = getGravityCentre
		ShapeFactory.createPoint(grav.getX+Math.cos(startAngle)*getWidth/2.0, grav.getY-Math.sin(startAngle)*getWidth/2.0)
	}

	override def getArcStyle: ArcStyle = style

	override def setAngleEnd(angleEnd:Double) {
		if(GLibUtilities.isValidCoordinate(angleEnd))
			this.endAngle = angleEnd
	}

	override def setAngleStart(angleStart:Double) {
		if(GLibUtilities.isValidCoordinate(angleStart))
			this.startAngle = angleStart
	}

	override def setArcStyle(styl:ArcStyle) {
		if(styl!=null)
			this.style = styl
	}
}
