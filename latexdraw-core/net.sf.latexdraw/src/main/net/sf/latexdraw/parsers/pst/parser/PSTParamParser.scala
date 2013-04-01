package net.sf.latexdraw.parsers.pst.parser

import scala.util.parsing.input.CharArrayReader

/**
 * A parser that parses parameters of a command.<br>
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
trait PSTParamParser extends PSTAbstractParser with PSTValueParser {
	val paramsMap : Map[String, (String, PSTContext) => Option[Any]] = Map(
			("linecolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("fillcolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("gridcolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("gridlabelcolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("subgridcolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("bordercolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("doublecolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("shadowcolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("hatchcolor", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("gradend", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("gradbegin", (str : String, ctx : PSTContext) => parseValueColour(str, ctx)),
			("showpoints", (str : String, ctx : PSTContext) => parseValueBoolean(str)),
			("swapaxes", (str : String, ctx : PSTContext) => parseValueBoolean(str)),
			("doubleline", (str : String, ctx : PSTContext) => parseValueBoolean(str)),
			("shadow", (str : String, ctx : PSTContext) => parseValueBoolean(str)),
			("showorigin", (str : String, ctx : PSTContext) => parseValueBoolean(str)),
			("boxsep", (str : String, ctx : PSTContext) => parseValueBoolean(str)),
			("cornersize", (str : String, ctx : PSTContext) => parseValueCornersize(str)),
			("plotstyle", (str : String, ctx : PSTContext) => parseValuePlotstyle(str)),
			("linestyle", (str : String, ctx : PSTContext) => parseValueLineStyle(str)),
			("dimen", (str : String, ctx : PSTContext) => parseValueDimen(str)),
			("fillstyle", (str : String, ctx : PSTContext) => parseValueFillingStyle(str)),
			("addfillstyle", (str : String, ctx : PSTContext) => parseValueFillingStyle(str)),
			("framearc", (str : String, ctx : PSTContext) => parseValue01Interval(str)),
			("linearc", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("gradlines", (str : String, ctx : PSTContext) => parseValueInt(str)),
			("plotpoints", (str : String, ctx : PSTContext) => parseValueInt(str)),
			("gradmidpoint", (str : String, ctx : PSTContext) => parseValue01Interval(str)),
			("hatchangle", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("gangle", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("gradangle", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("griddots", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("shadowangle", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("shadowsize", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("doublesep", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("hatchsep", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("hatchwidth", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("subgridwidth", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("arrows", (str : String, ctx : PSTContext) => parseValueArrows(str)),
			("arcsepA", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("arcsepB", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("arcsep", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("gridlabels", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("linewidth", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("dotstyle", (str : String, ctx : PSTContext) => parseValueDotStyle(str)),
			("curvature", (str : String, ctx : PSTContext) => parseValueCurvature(str)),
			("dotsize", (str : String, ctx : PSTContext) => parseValueDimNum(str)),
			("arrowsize", (str : String, ctx : PSTContext) => parseValueDimNum(str)),
			("arrowscale", (str : String, ctx : PSTContext) => parseValueDimNum(str)),//TODO to support
			("tbarsize", (str : String, ctx : PSTContext) => parseValueDimNum(str)),
			("subgriddiv", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("dotscale", (str : String, ctx : PSTContext) => parseValueNumNum(str)),
			("dotangle", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("arrowlength", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("arrowinset", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("subgriddots", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("bracketlength", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("rbracketlength", (str : String, ctx : PSTContext) => parseValueNum(str)),
			("gridwidth", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("unit", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("dotsep", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("border", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("dash", (str : String, ctx : PSTContext) => parseValueDimDim(str)),
			("xunit", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)),
			("liftpen", (str : String, ctx : PSTContext) => parseValue012(str)),
			("yunit", (str : String, ctx : PSTContext) => parseValueDimNoUnit(str)))
//			("origin", (str : String, ctx : PSTContext) => parseValueOrigin(str)))
//			("labels", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
//			("ticks", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
//			("tickstyle", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
//			("axesstyle", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),



	/**
	 * Parses the psset command.
	 */
	def parsePsset(ctx : PSTContext) : Parser[Unit] = "\\psset" ~ "{" ~ repsep(parseParamSetting(ctx, '}'), ",") ~ "}" ^^ {
		case _ ~ _ ~ _ =>
	}



	/**
	 * Parses the list of parameters.
	 */
	def parseParam(ctx : PSTContext) : Parser[Unit] = "[" ~ repsep(parseParamSetting(ctx, ']'), ",") ~ "]" ^^ {
		case _ ~ _ ~ _ =>
	}

// labelsep Ox Oy Dx Dy dx oy
// ticksize framesep nodesep offset arm angle arcangle ncurv loopsize coilwidth coilheight coilarm coilaspect coilinc

	/**
	 * Parses the setting of parameters.
	 */
	def parseParamSetting(ctx : PSTContext, closingChar : Char) : Parser[Unit] = ident ~ "=" ~ rep1(chrExcept(',', closingChar, CharArrayReader.EofCh)) ^^ {
		case name ~ _ ~ value  =>
		val valueStr = value.mkString(" ")

		paramsMap.get(name) match {
			case Some(fct) => fct(valueStr, ctx) match {
				case Some(res) => ctx.setParam(name, res)
				case None => PSTParser.errorLogs += "Value not valid: " + name + "=" + valueStr
			}
			case None => PSTParser.errorLogs += "Unknown parameter: " + name + "=" + valueStr
		}
	}
}
