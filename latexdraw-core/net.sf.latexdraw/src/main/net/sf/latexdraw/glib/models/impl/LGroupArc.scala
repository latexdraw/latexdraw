package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp

/**
 * This trait encapsulates the code of the group related to the support of IArcProp shapes.<br>
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
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
protected trait LGroupArc extends IGroup {
	/** May return the first IArcProp shape of the group. */
	private def firstIArcProp = arcShapes.find{_.isTypeOf(classOf[IArcProp])}

	private def arcShapes = getShapes.flatMap{case x:IArcProp => x::Nil; case _ => Nil}

	override def getArcStyle() : IArcProp.ArcStyle =
		firstIArcProp match {
			case Some(arc) => arc.getArcStyle
			case _ => null
		}

	override def setArcStyle(typeArc : IArcProp.ArcStyle) {
		arcShapes.foreach{_.setArcStyle(typeArc)}
	}

	override def getAngleStart() : Double =
		firstIArcProp match {
			case Some(arc) => arc.getAngleStart
			case _ => Double.NaN
		}

	override def setAngleStart(angleStart : Double) {
		arcShapes.foreach{_.setAngleStart(angleStart)}
	}

	override def getAngleEnd() : Double =
		firstIArcProp match {
			case Some(arc) => arc.getAngleEnd
			case _ => Double.NaN
		}

	override def setAngleEnd(angleEnd : Double) {
		arcShapes.foreach{_.setAngleEnd(angleEnd)}
	}
}
