package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IText
import net.sf.latexdraw.glib.models.interfaces.prop.ITextProp

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
protected trait LGroupText extends IGroup {
	/** May return the first free hand shape of the group. */
	private def firstIText = txtShapes.find{_.isTypeOf(classOf[IText])}

	private def txtShapes = getShapes.flatMap{case x:IText => x::Nil; case _ => Nil}

	override def getTextPosition() : ITextProp.TextPosition =
		firstIText match {
			case Some(txt) => txt.getTextPosition
			case _ => null
		}

	override def setTextPosition(textPosition : ITextProp.TextPosition) {
		txtShapes.foreach{_.setTextPosition(textPosition)}
	}

	override def getText() : String =
		firstIText match {
			case Some(txt) => txt.getText
			case _ => null
		}

	override def setText(text : String) {
		txtShapes.foreach{_.setText(text)}
	}
}
