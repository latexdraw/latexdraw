package net.sf.latexdraw.glib.models.impl

import java.awt.Color
import scala.collection.JavaConversions.asScalaBuffer
import net.sf.latexdraw.glib.models.interfaces.Dottable
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IDot

/**
 * This trait encapsulates the code of the group related to the support of dottable shapes.<br>
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
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
protected trait LGroupDottable extends IGroup {
	/** May return the first grid of the group. */
	private def firstDottable = getShapes.find{shape => shape.isInstanceOf[Dottable] }

	override def getDotFillingCol() : Color = {
		firstDottable match {
			case Some(dot) => dot.asInstanceOf[Dottable].getDotFillingCol
			case _ => null
		}
	}


	override def setDotFillingCol(fillingCol : Color) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[Dottable])
				shape.asInstanceOf[Dottable].setDotFillingCol(fillingCol)
		}
	}


	override def getDotStyle() : IDot.DotStyle = {
		firstDottable match {
			case Some(dot) => dot.asInstanceOf[Dottable].getDotStyle
			case _ => null
		}
	}


	override def setDotStyle(style : IDot.DotStyle) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[Dottable])
				shape.asInstanceOf[Dottable].setDotStyle(style)
		}
	}


	override def getRadius() : Double = {
		firstDottable match {
			case Some(dot) => dot.asInstanceOf[Dottable].getRadius
			case _ => Double.NaN
		}
	}


	override def setRadius(radius : Double) = {
		getShapes.foreach{shape =>
			if(shape.isInstanceOf[Dottable])
				shape.asInstanceOf[Dottable].setRadius(radius)
		}
	}
}
