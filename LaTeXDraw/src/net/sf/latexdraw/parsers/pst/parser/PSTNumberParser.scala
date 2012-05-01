package net.sf.latexdraw.parsers.pst.parser

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
 * 2012-05-01<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTNumberParser extends PSTAbstractParser {
	/**
	 * Parses a number: a numeric value that may be followed by a unit.
	 */
	def parseNumber : Parser[Double] = numeric ^^ { case num =>
		parseValueNumber(num) match {
			case Some(value) => value
			case None => Double.NaN
		}
	}


	/**
	 * Parses a number: a numeric value that may be followed by a unit.
	 */
	def parseValueNumber(num : String) : Option[Double] = {
		if(num.length>2) {
			val value = num.substring(0, num.length-2)
			num.substring(num.length-2) match {
				case PSTricksConstants.TOKEN_CM => createValidNumber(value)
				case PSTricksConstants.TOKEN_MM => createValidNumber(value) match {
						case Some(num) => Some(num/10.)
						case _ => None
					}
				case PSTricksConstants.TOKEN_PS_PT => createValidNumber(value) match {
						case Some(num) => Some(num/PSTricksConstants.CM_VAL_PT)
						case _ => None
					}
				case PSTricksConstants.TOKEN_INCH => createValidNumber(value) match {
						case Some(num) => Some(num/PSTricksConstants.INCH_VAL_CM)
						case _ => None
					}
				case _ => createValidNumber(num)
			}
		}
		else createValidNumber(num)
	}


	/**
	 * Converts the given parsed coordinate into a valid Java value.
	 */
	private def createValidNumber(coord : String) : Option[Double] = {
		var coordValid = coord.replace("+", "")
		coordValid = coordValid.replace("--", "")
		try { Some(coordValid.toDouble) }
		catch{case _ => None}
	}
}
