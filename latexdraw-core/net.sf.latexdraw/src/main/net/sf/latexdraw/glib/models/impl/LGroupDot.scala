package net.sf.latexdraw.glib.models.impl

import java.util.stream.Collectors

import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp
import net.sf.latexdraw.glib.models.interfaces.shape.Color
import net.sf.latexdraw.glib.models.interfaces.shape.DotStyle
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup
import net.sf.latexdraw.glib.models.interfaces.shape.IShape
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
	private def firstDottable = dotShapes.stream.filter{_.isTypeOf(classOf[IDotProp])}.findFirst

	private def dotShapes =
	  getShapes.stream.filter{_.isInstanceOf[IDotProp]}.map[IShape with IDotProp]{_.asInstanceOf[IShape with IDotProp]}.collect(Collectors.toList())

	override def getDotFillingCol = if(firstDottable.isPresent) firstDottable.get.getDotFillingCol else DviPsColors.BLACK

	override def setDotFillingCol(fillingCol : Color) {
		dotShapes.forEach{_.setDotFillingCol(fillingCol)}
	}

	override def getDotStyle = if(firstDottable.isPresent) firstDottable.get.getDotStyle() else DotStyle.DOT

	override def setDotStyle(style : DotStyle) {
		dotShapes.forEach{_.setDotStyle(style)}
	}

	override def getDiametre = if(firstDottable.isPresent) firstDottable.get.getDiametre else Double.NaN

	override def setDiametre(dia : Double) {
		dotShapes.forEach{_.setDiametre(dia)}
	}
}
