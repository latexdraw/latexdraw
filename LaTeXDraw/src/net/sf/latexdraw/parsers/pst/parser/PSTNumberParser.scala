package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.glib.models.interfaces.IPoint

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
//		/** The regex expression of the parameter origin. */
//	val originPattern = """\{([\d\s +-]*),([\d\s +-]*)\}""".r


	/**
	 * Parses a number: a numeric value that may be followed by a unit.
	 */
	def parseNumber : Parser[Double] = numeric ^^ { case num =>
		parseValueDim(num) match {
			case Some(value) => value
			case None => Double.NaN
		}
	}


	/**
	 * Parses double values contained in the interval [0..1]
	 */
	def parseValue01Interval(num : String) : Option[Double] = {
		createValidNumber(num) match {
			case Some(value) if(value>=0.0 && value<=1.0) => Some(value)
			case _ => None
		}
	}


	/**
	 * Parses int values.
	 */
	def parseValueInt(num : String) : Option[Int] = {
		createValidNumber(num) match {
			case Some(value) => scala.math.abs(value-scala.math.ceil(value))<0.00001 match {
				case true => Some(value.toInt)
				case false => None
			}
			case None => None
		}
	}


	/** Parses angle values. The returned value is in degree. */
	def parseValueNum(num : String) = createValidNumber(num)


//	/** Parses the origin parameter. */
//	def parseValueOrigin(str : String) : Option[IPoint] = {
//		str match {
//			case originPattern(coord1, coord2) => println(coord1 + " " + coord2)
//			case _ => println("fooooo:"+ str)
//		}
//
//		None
//	}


	/**
	 * Parses a number: a numeric value that may be followed by a unit.
	 */
	def parseValueDim(str : String) : Option[Double] = {
		if(str.length>2) {
			val value = str.substring(0, str.length-2)
			str.substring(str.length-2) match {
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
				case _ => createValidNumber(str)
			}
		}
		else createValidNumber(str)
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
