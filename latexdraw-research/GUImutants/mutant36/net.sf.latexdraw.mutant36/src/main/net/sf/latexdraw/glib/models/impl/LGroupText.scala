package net.sf.latexdraw.glib.models.impl

import scala.collection.JavaConversions.asScalaBuffer

import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IText

/**
 * This trait encapsulates the code of the group related to the support of texts.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
	private def firstIText = getShapes.find{shape => shape.isInstanceOf[IText] }

	override def getTextPosition() : IText.TextPosition =
		firstIText match {
			case Some(txt) => txt.asInstanceOf[IText].getTextPosition
			case _ => null
		}


	override def setTextPosition(textPosition : IText.TextPosition) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IText])
				shape.asInstanceOf[IText].setTextPosition(textPosition)
		}
	}


	override def getText() : String =
		firstIText match {
			case Some(txt) => txt.asInstanceOf[IText].getText
			case _ => null
		}


	override def setText(text : String) {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[IText])
				shape.asInstanceOf[IText].setText(text)
		}
	}


	override def getX() = 0.0

	override def getY() = 0.0

	override def getPosition() : IPoint = null

	override def setPosition(pt : IPoint) { }

	override def setPosition(x : Double, y : Double) { }

	override def setX(x : Double) { }

	override def setY(y : Double) { }
}
