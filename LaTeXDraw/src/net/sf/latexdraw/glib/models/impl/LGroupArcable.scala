package net.sf.latexdraw.glib.models.impl

import net.sf.latexdraw.glib.models.interfaces.IGroup
import scala.collection.JavaConversions._
import net.sf.latexdraw.glib.models.interfaces.Arcable
import net.sf.latexdraw.glib.models.interfaces.Arcable.ArcStyle

/**
 * This trait encapsulates the code of the group related to the support of arcable shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
	/** May return the first arcable shape of the group. */
	private def firstArcable = getShapes.find{shape => shape.isInstanceOf[Arcable] }

	override def getArcStyle() : ArcStyle = {
		firstArcable match {
			case Some(arc) => arc.asInstanceOf[Arcable].getArcStyle
			case _ => null
		}
	}


	override def setArcStyle(typeArc : ArcStyle) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[Arcable])
				shape.asInstanceOf[Arcable].setArcStyle(typeArc)
		}
	}


	override def getAngleStart() : Double = {
		firstArcable match {
			case Some(arc) => arc.asInstanceOf[Arcable].getAngleStart
			case _ => Double.NaN
		}
	}


	override def setAngleStart(angleStart : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[Arcable])
				shape.asInstanceOf[Arcable].setAngleStart(angleStart)
		}
	}


	override def getAngleEnd() : Double = {
		firstArcable match {
			case Some(arc) => arc.asInstanceOf[Arcable].getAngleEnd
			case _ => Double.NaN
		}
	}


	override def setAngleEnd(angleEnd : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[Arcable])
				shape.asInstanceOf[Arcable].setAngleEnd(angleEnd)
		}
	}
}
