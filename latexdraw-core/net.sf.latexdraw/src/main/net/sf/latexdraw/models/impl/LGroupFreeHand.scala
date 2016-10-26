package net.sf.latexdraw.models.impl

import java.util.stream.Collectors

import net.sf.latexdraw.models.interfaces.prop.IFreeHandProp
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle
import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.IShape

/**
 * This trait encapsulates the code of the group related to the support of free hand shapes.<br>
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
private[impl] trait LGroupFreeHand extends IGroup {
	/** May return the first free hand shape of the group. */
	private def firstIFreeHand = fhShapes.stream.filter{_.isTypeOf(classOf[IFreeHandProp])}.findFirst

	private def fhShapes =
	    getShapes.stream.filter{_.isInstanceOf[IFreeHandProp]}.map[IShape with IFreeHandProp]{_.asInstanceOf[IShape with IFreeHandProp]}.collect(Collectors.toList())

	override def getType = if(firstIFreeHand.isPresent) firstIFreeHand.get.getType() else FreeHandStyle.CURVES

	override def setType(fhType : FreeHandStyle) {
		fhShapes.forEach{_.setType(fhType)}
	}

	override def isOpen = if(firstIFreeHand.isPresent) firstIFreeHand.get.isOpen else false

	override def setOpen(open : Boolean) {
		fhShapes.forEach{_.setOpen(open)}
	}

	override def getInterval = if(firstIFreeHand.isPresent) firstIFreeHand.get.getInterval else 0

	override def setInterval(interval : Int) {
		fhShapes.forEach{_.setInterval(interval)}
	}
}
