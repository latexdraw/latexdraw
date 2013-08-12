package net.sf.latexdraw.parsers.pst.parser

import java.awt.Color
import net.sf.latexdraw.glib.models.interfaces.IAxes
import net.sf.latexdraw.glib.views.latex.DviPsColors
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.views.pst.PSTricksConstants
import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.models.interfaces.IDot


/**
 * A parser that parses a value corresponding to an object.
 * An object can be a name or a command.<br>
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
 * 2012-04-26<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTValueParser extends PSTNumberParser {
	/** The regex expression of an identifier. */
	val identPattern = """(\w)+""".r
	/** The regex expression of PST command name. */
	val cmdPattern = """(\\)ps\w+""".r
	/** The regex expression of PST arrows. */
	val arrowPattern = """([\]\[\)\(\*\|<>ocC]{0,2})-([\]\[\)\(\*\|<>ocC]{0,2})""".r



	/**
	 * Parses the rotation value of put commands (rput, etc.) and converts it in a radian value (or None).
	 * The second value of the returned tuple (Boolean) defines if the rotation must consider the previous
	 * rotations (the char * in the rput command).
	 */
	def parseValuePutRotation(value : String) : Option[Tuple2[Double,Boolean]] = {
		value match {
			case "U" => Some(Tuple2(0, true))
			case "L" => Some(Tuple2(-scala.math.Pi/2.0, true))
			case "D" => Some(Tuple2(-scala.math.Pi, true))
			case "R" => Some(Tuple2(-3.0*scala.math.Pi/2.0, true))
			case "N" => Some(Tuple2(0, false))
			case "W" => Some(Tuple2(-scala.math.Pi/2.0, false))
			case "S" => Some(Tuple2(-scala.math.Pi, false))
			case "E" => Some(Tuple2(-3.0*scala.math.Pi/2.0, false))
			case value =>
				parseValueNum(value) match {
					case Some(num) => Some(Tuple2(-scala.math.toRadians(num), true))
					case _ =>
						val withStar = value.startsWith("*")
						val numStr = if(withStar) value.substring(1) else value

						parseValuePutRotation(numStr) match {
							case Some(Tuple2(num, _)) => Some(Tuple2(num, !withStar))
							case _ => None
						}
				}
		}
	}


	/**
	 * Parses the value of the curvature parameter.
	 */
	def parseValueCurvature(value : String) : Option[Tuple3[Double, Double, Double]] = {
		value.split(" ") match {
			case Array(val1, val2, val3) =>
				try { Some(Tuple3(val1.toDouble, val2.toDouble, val3.toDouble)) }
				catch{case _ => None}
			case _ => None
		}
	}


	/**
	 * Parses a num value that may be followed by another num value.
	 */
	def parseValueNumNum(value : String) : Option[Tuple2[Double, Double]] = {
		value.split(" ") match {
			case Array(num1) =>
				// When a single value is defined, it means that the second value equals the single one defined.
				parseValueNum(num1) match {
					case Some(value) => Some(Tuple2(value, value))
					case _ => None
				}
			case Array(num1, num2) =>
				val n1 = parseValueNum(num1)
				val n2 = parseValueNum(num2)

				if(n1.isDefined && n2.isDefined)
					Some(Tuple2(n1.get, n2.get))
				else
					None
			case _ => None
		}
	}


	/**
	 * Parses a dim value that may be followed by a num value.
	 * Example: dotsize=2cm 4
	 */
	def parseValueDimNum(value : String) : Option[Tuple2[Double, Double]] = {
		value.split(" ") match {
			case Array(dim) => parseValueOptDimNum(dim, "")
			case Array(dim, num) =>
				// When two elements compose the value, it can be either
				// 3 cm or 2.4 1 for instance.
				if(parseValueNum(num).isDefined)
					parseValueOptDimNum(dim, num)
				else parseValueOptDimNum(dim+num, "")
			case Array(dim, unit, num) => parseValueOptDimNum(dim+unit, num)
			case _ => None
		}
	}


	private def parseValueOptDimNum(dim : String, num : String) : Option[Tuple2[Double, Double]] = {
		val dimOpt = parseValueDim(dim)
		val numOpt = parseValueNum(num) match {
			case Some(value) => value
			case _ => 0.0
		}

		if(dimOpt.isDefined)
			Some(Tuple2(dimOpt.get._1, numOpt))
		else
			None
	}


	/**
	 * Parses arrows.
	 */
	def parseValueArrows(value : String) : Option[Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle]] =
		value.replace(" ", "") match {
			// The arrow string must contains the separator - and at least one arrow.
			case arrowPattern(arr1, arr2) => Some(Tuple2(IArrow.ArrowStyle.getArrowStyle(arr1), IArrow.ArrowStyle.getArrowStyle(arr2)))
			case _ => None
		}



	def setArrows(sh : IShape, arrowsRaw : Option[String], invert : Boolean, ctx:PSTContext) {
		arrowsRaw match {
			case Some(value) =>
				val arrows = parseValueArrows(value)
				if(arrows.isDefined) {
					ctx.arrowStyle = arrows.get
					if(invert) {
						sh.setArrowStyle(arrows.get._2, 0)
						sh.setArrowStyle(arrows.get._1, -1)
					}else {
						sh.setArrowStyle(arrows.get._1, 0)
						sh.setArrowStyle(arrows.get._2, -1)
					}
				}
			case None =>
		}
	}


	/**
	 * Parses the cornersize value and returns true if the corner is relative, false
	 * is absolute and None is the value is not correct.
	 */
	def parseValueCornersize(value : String) : Option[Boolean] =
		value match {
			case PSTricksConstants.TOKEN_RELATIVE => Some(true)
			case PSTricksConstants.TOKEN_ABSOLUTE => Some(false)
			case _ => None
		}


	/**
	 * Parses the line styles.
	 */
	def parseValueDotStyle(value : String) : Option[IDot.DotStyle] =
		value.replace(" ", "") match {
			case PSTricksConstants.ASTERISK_STYLE => Some(IDot.DotStyle.ASTERISK)
			case PSTricksConstants.BAR_STYLE => Some(IDot.DotStyle.BAR)
			case PSTricksConstants.DIAMOND_STYLE => Some(IDot.DotStyle.DIAMOND)
			case PSTricksConstants.DOT_STYLE => Some(IDot.DotStyle.DOT)
			case PSTricksConstants.FDIAMOND_STYLE => Some(IDot.DotStyle.FDIAMOND)
			case PSTricksConstants.FPENTAGON_STYLE => Some(IDot.DotStyle.FPENTAGON)
			case PSTricksConstants.FSQUARE_STYLE => Some(IDot.DotStyle.FSQUARE)
			case PSTricksConstants.FTRIANGLE_STYLE => Some(IDot.DotStyle.FTRIANGLE)
			case PSTricksConstants.O_STYLE => Some(IDot.DotStyle.O)
			case PSTricksConstants.OPLUS_STYLE => Some(IDot.DotStyle.OPLUS)
			case PSTricksConstants.OTIMES_STYLE => Some(IDot.DotStyle.OTIMES)
			case PSTricksConstants.PENTAGON_STYLE => Some(IDot.DotStyle.PENTAGON)
			case PSTricksConstants.PLUS_STYLE => Some(IDot.DotStyle.PLUS)
			case PSTricksConstants.SQUARE_STYLE => Some(IDot.DotStyle.SQUARE)
			case PSTricksConstants.TRIANGLE_STYLE => Some(IDot.DotStyle.TRIANGLE)
			case PSTricksConstants.X_STYLE => Some(IDot.DotStyle.X)
			case _ => PSTParser.errorLogs += "Unknown dot style: " + value.replace(" ", ""); None
		}


	/**
	 * Parses the line styles.
	 */
	def parseValueDimen(value : String) : Option[IShape.BorderPos] =
		value match {
			case PSTricksConstants.BORDERS_INSIDE => Some(IShape.BorderPos.INTO)
			case PSTricksConstants.BORDERS_MIDDLE => Some(IShape.BorderPos.MID)
			case PSTricksConstants.BORDERS_OUTSIDE=> Some(IShape.BorderPos.OUT)
			case _ => PSTParser.errorLogs += "Unknown border position: " + value; None
		}


	/**
	 * Parses the plotstyle values.
	 */
	def parseValuePlotstyle(value : String) : Option[String] =
		value match {
			case "dots" | "line" | "polygon" | "curve" | "ecurve" | "ccurve" => Some(value)
			case _ => PSTParser.errorLogs += "Unknown plotstyle: " + value; None
	}


	/**
	 * Parses the line styles.
	 */
	def parseValueLineStyle(value : String) : Option[IShape.LineStyle] =
		value match {
			case PSTricksConstants.LINE_DASHED_STYLE => Some(IShape.LineStyle.DASHED)
			case PSTricksConstants.LINE_DOTTED_STYLE => Some(IShape.LineStyle.DOTTED)
			case PSTricksConstants.LINE_SOLID_STYLE => Some(IShape.LineStyle.SOLID)
			case PSTricksConstants.LINE_NONE_STYLE =>
				PSTParser.errorLogs += "line style '"+PSTricksConstants.LINE_NONE_STYLE+"' not supported yet"
				None
			case _ => PSTParser.errorLogs += "Unknown line style: " + value; None
		}


	/**
	 * Parses the filling styles.
	 */
	def parseValueFillingStyle(value : String) : Option[IShape.FillingStyle] =
		value match {
			case "solid" => Some(IShape.FillingStyle.PLAIN)
			case "none" => Some(IShape.FillingStyle.NONE)
			case "vlines" => Some(IShape.FillingStyle.VLINES)
			case "vlines*" => Some(IShape.FillingStyle.VLINES_PLAIN)
			case "hlines" => Some(IShape.FillingStyle.HLINES)
			case "hlines*" => Some(IShape.FillingStyle.HLINES_PLAIN)
			case "clines" => Some(IShape.FillingStyle.CLINES)
			case "clines*" => Some(IShape.FillingStyle.CLINES_PLAIN)
			case "gradient" => Some(IShape.FillingStyle.GRAD)
			case _ => PSTParser.errorLogs += "Unknown filling style: " + value; None
		}


	/** This parser parses a object value that can be either a name nor a command. */
	def parseValueColour(value : String, ctx : PSTContext) : Option[Color] =
		try {
			value match {
				case cmdPattern(_) =>
					ctx.getParam(value.replace("\\ps", "")) match {
						case col : Color => Some(col)
						case _ => None
					}
				case identPattern(_) =>
					DviPsColors.INSTANCE.getColour(value) match {
						case col : Color => Some(col)
						case _ => PSTParser.errorLogs += "The following colour is unknown: " + value; None
					}
				case _ => None
			}
		}catch{case ex => None }


	/**
	 * Parses a boolean value.
	 */
	def parseValueBoolean(value : String) : Option[Boolean] =
		value match {
			case "true" => Some(true)
			case "false" => Some(false)
			case _ => None
		}
}
