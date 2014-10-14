package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.Color
import net.sf.latexdraw.glib.views.latex.DviPsColors

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
private[impl] trait LGroupDot extends IGroup {
	/** May return the first grid of the group. */
	private def firstDottable = dotShapes.find{_.isTypeOf(classOf[IDotProp])}

	private def dotShapes = getShapes.flatMap{case x:IDotProp => x::Nil; case _ => Nil}

	override def getDotFillingCol: Color = {
		firstDottable match {
			case Some(dot) => dot.getDotFillingCol
			case _ => DviPsColors.BLACK
		}
	}


	override def setDotFillingCol(fillingCol : Color) {
		dotShapes.foreach{_.setDotFillingCol(fillingCol)}
	}


	override def getDotStyle: IDotProp.DotStyle = {
		firstDottable match {
			case Some(dot) => dot.getDotStyle
			case _ => IDotProp.DotStyle.DOT
		}
	}


	override def setDotStyle(style : IDotProp.DotStyle) {
		dotShapes.foreach{_.setDotStyle(style)}
	}


	override def getDiametre: Double = {
		firstDottable match {
			case Some(dot) => dot.getDiametre
			case _ => Double.NaN
		}
	}


	override def setDiametre(dia : Double) {
		dotShapes.foreach{_.setDiametre(dia)}
	}
}
