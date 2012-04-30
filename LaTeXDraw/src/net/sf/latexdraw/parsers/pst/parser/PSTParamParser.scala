package net.sf.latexdraw.parsers.pst.parser

import scala.util.parsing.input.CharArrayReader

/**
 * A parser that parses parameters of a command.<br>
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
//			("cornersize", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
//			("plotstyle", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
			("linestyle", (str : String, ctx : PSTContext) => parseValueLineStyle(str)),
			("dimen", (str : String, ctx : PSTContext) => parseValueDimen(str)),
			("fillstyle", (str : String, ctx : PSTContext) => parseValueFillingStyle(str)))
//			("labels", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
//			("ticks", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
//			("tickstyle", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),
//			("axesstyle", (str : String, ctx : PSTContext) => parseValueText.apply(obj)),


	def parseParam(context : PSTContext) : Parser[Unit] = "[" ~ repsep(parseParamSetting(context), ",") ~ "]" ^^ {
		case _ ~ _ ~ _ =>
	}

// doublesep linewidth arrows linearc framearc  arcsepA arcsepB arcsep xunit yunit unit curvature
// dotstyle dotscale dotangle gridwidth  griddots gridlabels  subgriddiv subgridwidth  subgriddots  origin plotpoints
// dash dotsep border shadowsize shadowangle hatchwidth hatchsep  hatchangle
// arrowsize arrowlength arrowinset tbarsize bracketlength rbracketlength dotsize arrowscale linetyle liftpen labelsep Ox Oy Dx Dy dx oy
// ticksize framesep nodesep offset arm angle arcangle ncurv loopsize coilwidth coilheight coilarm coilaspect coilinc
// gradlines gradmidpoint gradangle
// string (gray, triangle*), command (\psfillcolor), double (.5, ), mix (.5\pslinewidth), double unit (.5cm, 1pt), integer (1), symbol (-, oo-oo), repetition (1 .1 0)
// point ((0,1) {0,1})

	def parseParamSetting(ctx : PSTContext) : Parser[Unit] = ident ~ "=" ~ rep1(chrExcept(',', ']', CharArrayReader.EofCh)) ^^ {
		case name ~ _ ~ value  =>
		val valueStr = value.mkString

		paramsMap.get(name) match {
			case Some(fct) => fct(valueStr, ctx) match {
				case Some(res) => ctx.setParam(name, res)
				case None => PSTParser.errorLogs += "Value not valid: " + name + "=" + valueStr
			}
			case None => PSTParser.errorLogs += "Unknown parameter: " + name
		}
	}
}
