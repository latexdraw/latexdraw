package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.views.pst.PSTricksConstants

/**
 * A parser that parses numbers.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 2012-04-28<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTCoordinateParser extends PSTAbstractParser {
	/**
	 * Parses a coordinate.
	 */
	def parseCoord(ctx : PSTContext) : Parser[IPoint] =
		"(" ~ opt(numeric) ~ "," ~ opt(numeric) ~ ")" ^^ { case _ ~ p1 ~ _ ~ p2 ~ _ =>
			DrawingTK.getFactory.createPoint(createValidCoordinate(p1), createValidCoordinate(p2))
	}


	/**
	 * Converts the given parsed coordinate into a valid Java value.
	 */
	private def createValidCoordinate(coord : Option[String]) : Double = {
		coord match {
			case Some(value) =>
				var coordValid = value.replace("+", "")
				coordValid = coordValid.replace("--", "")
				coordValid.toDouble
			case None => PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE
		}
	}
}
