package net.sf.latexdraw.glib.models.impl

import java.awt.Color

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IDot
import net.sf.latexdraw.glib.models.interfaces.IGroup

/**
 * This trait encapsulates the code of the group related to the support of dot shapes.<br>
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
protected trait LGroupDot extends IGroup {
	/** May return the first grid of the group. */
	private def firstDottable = dotShapes.find{_.isTypeOf(classOf[IDot])}

	private def dotShapes = getShapes.flatMap{case x:IDot => x::Nil; case _ => Nil}

	override def getDotFillingCol() : Color = {
		firstDottable match {
			case Some(dot) => dot.getDotFillingCol
			case _ => null
		}
	}


	override def setDotFillingCol(fillingCol : Color) {
		dotShapes.foreach{_.setDotFillingCol(fillingCol)}
	}


	override def getDotStyle() : IDot.DotStyle = {
		firstDottable match {
			case Some(dot) => dot.getDotStyle
			case _ => null
		}
	}


	override def setDotStyle(style : IDot.DotStyle) {
		dotShapes.foreach{_.setDotStyle(style)}
	}


	override def getRadius() : Double = {
		firstDottable match {
			case Some(dot) => dot.getRadius
			case _ => Double.NaN
		}
	}


	override def setRadius(radius : Double) {
		dotShapes.foreach{_.setRadius(radius)}
	}

	override def getLazyTopLeftPoint() = {
		firstDottable match {
			case Some(dot) => dot.getLazyTopLeftPoint
			case _ => null
		}
	}

	override def getLazyBottomRightPoint() = {
		firstDottable match {
			case Some(dot) => dot.getLazyBottomRightPoint
			case _ => null
		}
	}

	override def getPlusGap() = {
		firstDottable match {
			case Some(dot) => dot.getPlusGap
			case _ => Double.NaN
		}
	}

	override def getCrossGap() = {
		firstDottable match {
			case Some(dot) => dot.getCrossGap
			case _ => Double.NaN
		}
	}

	override def getBarGap() = {
		firstDottable match {
			case Some(dot) => dot.getBarGap
			case _ => Double.NaN
		}
	}

	override def getBarThickness() = {
		firstDottable match {
			case Some(dot) => dot.getBarThickness
			case _ => Double.NaN
		}
	}

	override def getGeneralGap() = {
		firstDottable match {
			case Some(dot) => dot.getGeneralGap
			case _ => Double.NaN
		}
	}

	override def getOGap() = {
		firstDottable match {
			case Some(dot) => dot.getOGap
			case _ => Double.NaN
		}
	}
}
