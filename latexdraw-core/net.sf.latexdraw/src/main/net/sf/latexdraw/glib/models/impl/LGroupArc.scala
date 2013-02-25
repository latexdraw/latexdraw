package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IArc
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.ILine
import net.sf.latexdraw.glib.models.interfaces.IPoint

/**
 * This trait encapsulates the code of the group related to the support of IArc shapes.<br>
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
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
protected trait LGroupArc extends IGroup {
	/** May return the first IArc shape of the group. */
	private def firstIArc = getShapes.find{shape => shape.isInstanceOf[IArc] }

	override def getArcStyle() : IArc.ArcStyle =
		firstIArc match {
			case Some(arc) => arc.asInstanceOf[IArc].getArcStyle
			case _ => null
		}


	override def setArcStyle(typeArc : IArc.ArcStyle) =
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IArc])
				shape.asInstanceOf[IArc].setArcStyle(typeArc)
		}


	override def getAngleStart() : Double =
		firstIArc match {
			case Some(arc) => arc.asInstanceOf[IArc].getAngleStart
			case _ => Double.NaN
		}


	override def setAngleStart(angleStart : Double) =
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IArc])
				shape.asInstanceOf[IArc].setAngleStart(angleStart)
		}


	override def getAngleEnd() : Double =
		firstIArc match {
			case Some(arc) => arc.asInstanceOf[IArc].getAngleEnd
			case _ => Double.NaN
		}


	override def setAngleEnd(angleEnd : Double) =
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IArc])
				shape.asInstanceOf[IArc].setAngleEnd(angleEnd)
		}


	override def getStartPoint() : IPoint = null

	override def getEndPoint() : IPoint = null

	override def getA() = Double.NaN

	override def getB() = Double.NaN

	override def getRx() = Double.NaN

	override def getRy() = Double.NaN

	override def setRx(rx : Double) = {}

	override def setRy(ry : Double) = {}

	override def setCentre(centre : IPoint) = {}

	override def getIntersection(line : ILine) : Array[IPoint] = null

	override def setWidth(width : Double) = {}

	override def setHeight(height : Double) = {}

	override def getWidth() = Double.NaN

	override def getHeight() = Double.NaN
}
