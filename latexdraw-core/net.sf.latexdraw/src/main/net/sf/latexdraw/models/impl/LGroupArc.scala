package net.sf.latexdraw.models.impl

import java.util.stream.Collectors

import net.sf.latexdraw.models.interfaces.prop.IArcProp
import net.sf.latexdraw.models.interfaces.shape.ArcStyle
import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.IShape

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
private[impl] trait LGroupArc extends IGroup {
	/** May return the first IArcProp shape of the group. */
	private def firstIArcProp = arcShapes.stream.filter{_.isTypeOf(classOf[IArcProp])}.findFirst

	private def arcShapes = getShapes.stream.filter{_.isInstanceOf[IArcProp]}.map[IShape with IArcProp]{_.asInstanceOf[IShape with IArcProp]}.collect(Collectors.toList())

	override def getArcStyle = if(firstIArcProp.isPresent) firstIArcProp.get.getArcStyle else ArcStyle.ARC

	override def setArcStyle(typeArc : ArcStyle) {
		arcShapes.forEach{_.setArcStyle(typeArc)}
	}

	override def getAngleStart = if(firstIArcProp.isPresent) firstIArcProp.get.getAngleStart else Double.NaN

	override def setAngleStart(angleStart : Double) {
		arcShapes.forEach{_.setAngleStart(angleStart)}
	}

	override def getAngleEnd = if(firstIArcProp.isPresent) firstIArcProp.get.getAngleEnd else Double.NaN

	override def setAngleEnd(angleEnd : Double) {
		arcShapes.forEach{_.setAngleEnd(angleEnd)}
	}
}
