package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.glib.models.interfaces.IPoint

/**
 * A parser that parses numbers.<br>
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
	def parseNumber : Parser[Tuple2[Double,String]] = numeric ^^ { case num =>
		parseValueDim(num) match {
			case Some(value) => value
			case None => Tuple2(Double.NaN,"")
		}
	}


	/**
	 * Parses the given string to extract a value that can be either 0, 1, or 2.
	 * Otherwise, None is returned.
	 */
	def parseValue012(num : String) : Option[Int] = {
		createValidNumber(num) match {
			case Some(value) if(value==0 || value==1 || value==2) => Some(value.toInt)
			case _ => None
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


	/** Parses numerical values. */
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
	def parseValueDimDim(str : String) : Option[Tuple2[Double,Double]] = {
		var val1 : Option[Tuple2[Double,String]] = None
		var val2 : Option[Tuple2[Double,String]] = None

		str.split(" ") match {
			case Array(v1, u1, v2, u2) => val1 = parseValueDim(v1+u1) ; val2 = parseValueDim(v2+u2)
			case Array(v1, v2) => val1 = parseValueDim(v1) ; val2 = parseValueDim(v2)
			case Array(v1, o1, o2) =>
				if(parseValueDim(o2).isDefined) { val1 = parseValueDim(v1+o1) ; val2 = parseValueDim(o2) }
				else { val1 = parseValueDim(v1) ; val2 = parseValueDim(o1+o2) }
			case _ =>
		}

		if(val1.isDefined && val2.isDefined)
			Some(Tuple2(val1.get._1, val2.get._1))
		else
			None
	}


	def parseValueDimNoUnit(str:String) : Option[Double] = {
		parseValueDim(str) match {
			case Some(Tuple2(value,_)) => Some(value)
			case _ => None
		}
	}


	/**
	 * Parses a number: a numeric value that may be followed by a unit.
	 */
	def parseValueDim(str : String) : Option[Tuple2[Double,String]] = {
		if(str.length>2) {
			val value = str.substring(0, str.length-2)
			str.substring(str.length-2) match {
				case PSTricksConstants.TOKEN_CM =>
					createValidNumber(value) match {
						case Some(v) => Some(Tuple2(v, PSTricksConstants.TOKEN_CM))
						case _ => None
					}
				case PSTricksConstants.TOKEN_MM => createValidNumber(value) match {
						case Some(num) => Some(Tuple2(num/10., PSTricksConstants.TOKEN_MM))
						case _ => None
					}
				case PSTricksConstants.TOKEN_PS_PT => createValidNumber(value) match {
						case Some(num) => Some(num/PSTricksConstants.CM_VAL_PT, PSTricksConstants.TOKEN_PS_PT)
						case _ => None
					}
				case PSTricksConstants.TOKEN_INCH => createValidNumber(value) match {
						case Some(num) => Some(num/PSTricksConstants.INCH_VAL_CM, PSTricksConstants.TOKEN_INCH)
						case _ => None
					}
				case _ => createValidNumber(str) match {
					case Some(v) => Some(v, "")
					case _ => None
				}
			}
		}
		else createValidNumber(str) match {
				case Some(v) => Some(v, "")
				case _ => None
			}
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
