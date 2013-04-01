package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.views.pst.PSTricksConstants

/**
 * A parser that parses coordinates.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
trait PSTCoordinateParser extends PSTAbstractParser with PSTNumberParser {
	/**
	 * Parses a coordinate.
	 */
	def parseCoord(ctx : PSTContext) : Parser[PointUnit] =
		"(" ~ opt(parseNumber) ~ "," ~ opt(parseNumber) ~ ")" ^^ { case _ ~ p1 ~ _ ~ p2 ~ _ =>
			val x = p1 match {
				case Some(value) => value
				case None => Tuple2(PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE,"")
			}
			val y = p2 match {
				case Some(value) => value
				case None => Tuple2(PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE,"")
			}
			new PointUnit(x._1, y._1, x._2, y._2)
	}
}
