package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IFreehand
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint

/**
 * This trait encapsulates the code of the group related to the support of free hand shapes.<br>
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
protected trait LGroupFreeHand extends IGroup {
	/** May return the first free hand shape of the group. */
	private def firstIFreeHand = getShapes.find{shape => shape.isInstanceOf[IFreehand] }

	override def getType() : IFreehand.FreeHandType = {
		firstIFreeHand match {
			case Some(fh) => fh.asInstanceOf[IFreehand].getType
			case _ => null
		}
	}


	override def setType(fhType : IFreehand.FreeHandType) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IFreehand])
				shape.asInstanceOf[IFreehand].setType(fhType)
		}
	}


	override def isOpen() : Boolean = {
		firstIFreeHand match {
			case Some(fh) => fh.asInstanceOf[IFreehand].isOpen
			case _ => false
		}
	}


	override def setOpen(open : Boolean) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IFreehand])
				shape.asInstanceOf[IFreehand].setOpen(open)
		}
	}


	override def getInterval() : Int = {
		firstIFreeHand match {
			case Some(fh) => fh.asInstanceOf[IFreehand].getInterval
			case _ => 0
		}
	}


	override def setInterval(interval : Int) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IFreehand])
				shape.asInstanceOf[IFreehand].setInterval(interval)
		}
	}


	override def addPoint(pt : IPoint) = {}

	override def addPoint(pt : IPoint, position : Int) = {}

	override def removePoint(pt : IPoint) = false

	override def removePoint(position : Int) : IPoint = null

	override def setPoint(p : IPoint, position : Int)  = false

	override def setPoint(x : Double, y : Double, position : Int) = false

	override def replacePoint(pt : IPoint, position : Int) : IPoint = null
}
