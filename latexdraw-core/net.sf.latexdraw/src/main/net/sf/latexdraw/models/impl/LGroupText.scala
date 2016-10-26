package net.sf.latexdraw.models.impl

import java.util.stream.Collectors

import net.sf.latexdraw.models.interfaces.shape.IGroup
import net.sf.latexdraw.models.interfaces.shape.IText
import net.sf.latexdraw.models.interfaces.shape.TextPosition

/**
 * This trait encapsulates the code of the group related to the support of texts.<br>
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
 * 2012-04-17<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
private[impl] trait LGroupText extends IGroup {
	/** May return the first free hand shape of the group. */
	private def firstIText = txtShapes.stream.filter{_.isTypeOf(classOf[IText])}.findFirst

	private def txtShapes =
	  getShapes.stream.filter{_.isInstanceOf[IText]}.map[IText]{_.asInstanceOf[IText]}.collect(Collectors.toList())

	override def getTextPosition = if(firstIText.isPresent) firstIText.get.getTextPosition() else null

	override def setTextPosition(textPosition : TextPosition) {
		txtShapes.forEach{_.setTextPosition(textPosition)}
	}

	override def getText = if(firstIText.isPresent) firstIText.get.getText else null

	override def setText(text : String) {
		txtShapes.forEach{_.setText(text)}
	}
}
