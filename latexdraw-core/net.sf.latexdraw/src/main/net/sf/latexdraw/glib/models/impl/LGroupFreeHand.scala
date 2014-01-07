package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp

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
protected trait LGroupFreeHand extends IGroup {
	/** May return the first free hand shape of the group. */
	private def firstIFreeHand = fhShapes.find{_.isTypeOf(classOf[IFreeHandProp])}

	private def fhShapes = getShapes.flatMap{case x:IFreeHandProp => x::Nil; case _ => Nil}

	override def getType() : IFreeHandProp.FreeHandType = {
		firstIFreeHand match {
			case Some(fh) => fh.getType
			case _ => IFreeHandProp.FreeHandType.CURVES
		}
	}

	override def setType(fhType : IFreeHandProp.FreeHandType) {
		fhShapes.foreach{_.setType(fhType)}
	}

	override def isOpen() : Boolean = {
		firstIFreeHand match {
			case Some(fh) => fh.isOpen
			case _ => false
		}
	}

	override def setOpen(open : Boolean) {
		fhShapes.foreach{_.setOpen(open)}
	}

	override def getInterval() : Int = {
		firstIFreeHand match {
			case Some(fh) => fh.getInterval
			case _ => 0
		}
	}

	override def setInterval(interval : Int) {
		fhShapes.foreach{_.setInterval(interval)}
	}
}
