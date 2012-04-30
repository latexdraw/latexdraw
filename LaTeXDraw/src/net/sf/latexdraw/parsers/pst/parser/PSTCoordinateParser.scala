package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
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
		"(" ~ opt(parseNumber) ~ "," ~ opt(parseNumber) ~ ")" ^^ { case _ ~ p1 ~ _ ~ p2 ~ _ =>
			val x = p1 match {
				case Some(value) => value
				case None => PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE
			}
			val y = p2 match {
				case Some(value) => value
				case None => PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE
			}
			DrawingTK.getFactory.createPoint(x, y)
	}


	/**
	 * Parses a number: a numeric value that may be followed by a unit.
	 */
	def parseNumber : Parser[Double] = numeric ~ opt(unit) ^^ { case num ~ unit =>
		unit match {
			case Some(value) => value match {
				case PSTricksConstants.TOKEN_CM => createValidCoordinate(num)
				case PSTricksConstants.TOKEN_MM => createValidCoordinate(num)/10.
				case PSTricksConstants.TOKEN_PS_PT => createValidCoordinate(num)/PSTricksConstants.CM_VAL_PT
				case PSTricksConstants.TOKEN_INCH => createValidCoordinate(num)/PSTricksConstants.INCH_VAL_CM
			}
			case None => createValidCoordinate(num)
		}
	}


	/**
	 * Converts the given parsed coordinate into a valid Java value.
	 */
	private def createValidCoordinate(coord : String) : Double = {
//		coord match {
//			case Some(value) =>
				var coordValid = coord.replace("+", "")
				coordValid = coordValid.replace("--", "")
				coordValid.toDouble
//			case None => PSTricksConstants.DEFAULT_VALUE_MISSING_COORDINATE
//		}
	}
}
