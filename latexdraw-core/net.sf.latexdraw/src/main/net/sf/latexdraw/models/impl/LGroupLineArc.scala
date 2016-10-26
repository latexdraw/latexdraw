package net.sf.latexdraw.models.impl

import java.util.stream.Collectors

import net.sf.latexdraw.models.interfaces.prop.ILineArcProp
import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.IShape

/**
 * This trait encapsulates the code of the group related to the support of line arc shapes.<br>
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
private[impl] trait LGroupLineArc extends IGroup {
	/** May return the first free hand shape of the group. */
	private def firstLineArc = lineArcShapes.stream.filter{_.isTypeOf(classOf[ILineArcProp])}.findFirst

	private def lineArcShapes = 
	  getShapes.stream.filter{_.isInstanceOf[ILineArcProp]}.map[IShape with ILineArcProp]{_.asInstanceOf[IShape with ILineArcProp]}.collect(Collectors.toList())

	override def getLineArc = if(firstLineArc.isPresent) firstLineArc.get.getLineArc else Double.NaN

	override def setLineArc(lineArc : Double) {
		lineArcShapes.forEach{_.setLineArc(lineArc)}
	}

	override def isRoundCorner = firstLineArc.isPresent && firstLineArc.get.isRoundCorner
}
