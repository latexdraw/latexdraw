package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.shape.IShape
import net.sf.latexdraw.glib.models.ShapeFactory
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp

/**
 * Parsers parsing commands of the pst-plot package.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
 * 2012-05-09<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTPlotParser extends PSTAbstractParser with PSTParamParser with PSTBracketBlockParser {
	/**
	 * General parser parsing all the commands of the package PST plot.
	 */
	def parsePSTPlotCommands(ctx : PSTContext) : Parser[List[IShape]] =
		parseFileplot(ctx) | parseDataplot(ctx) | parseSavedata(ctx) | parseReaddata(ctx) |
		parseListplot(ctx) | parsePsplot(ctx) | parseParametricplot(ctx)


	/**
	 * Parses readdata commands.
	 */
	private def parseReaddata(ctx : PSTContext) : Parser[List[IShape]] = "\\readdata" ~ parseBracket(ctx) ~ parseBracket(ctx) ^^
	{ case cmdName ~ command ~ file => PSTParser.errorLogs += "Command readdata not supported yet."; Nil }


	/**
	 * Parses savedata commands.
	 */
	private def parseSavedata(ctx : PSTContext) : Parser[List[IShape]] = "\\savedata" ~ parseBracket(ctx) ~ parseSquaredBracket(ctx) ^^
	{ case cmdName ~ command ~ data => PSTParser.errorLogs += "Command savedata not supported yet."; Nil }


	/**
	 * Parses psplot commands.
	 */
	private def parseParametricplot(ctx : PSTContext) : Parser[List[IShape]] =
	("\\parametricplot*" | "\\parametricplot") ~ opt(parseParam(ctx)) ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ^^
	{ case cmdName ~ _ ~ xmin ~ xmax ~ function => PSTParser.errorLogs += "Command parametricplot not supported yet."; Nil }


	/**
	 * Parses psplot commands.
	 */
	private def parsePsplot(ctx : PSTContext) : Parser[List[IShape]] =
	("\\psplot*" | "\\psplot") ~ opt(parseParam(ctx)) ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx, " ") ^^ {
		case cmdName ~ _ ~ tmin ~ tmax ~ function =>
		val plot = ShapeFactory.createPlot(true, ShapeFactory.createPoint, tmin.toDouble, tmax.toDouble, function, false)//FIXME support radialplot
		setShapeParameters(plot, ctx)
		plot.setNbPlottedPoints(ctx.plotPoints)
		plot.setPlotStyle(IPlotProp.PlotStyle.getPlotStyle(ctx.plotStyle))
		plot.setXScale(ctx.xUnit)
		plot.setYScale(ctx.yUnit)
		plot.setPolar(ctx.polarPlot)
		if(cmdName.endsWith("*")) setShapeForStar(plot)
		checkTextParsed(ctx) ::: List(plot)
	}



	/**
	 * Parses listplot commands.
	 */
	private def parseListplot(ctx : PSTContext) : Parser[List[IShape]] = ("\\listplot*" | "\\listplot") ~ opt(parseParam(ctx)) ~ parseBracket(ctx) ^^
	{ case cmdName ~ _ ~ list => PSTParser.errorLogs += "Command listplot not supported yet."; Nil }


	/**
	 * Parses dataplot commands.
	 */
	private def parseDataplot(ctx : PSTContext) : Parser[List[IShape]] = ("\\dataplot*" | "\\dataplot") ~ opt(parseParam(ctx)) ~ parseBracket(ctx) ^^
	{ case cmdName ~ _ ~ commands => PSTParser.errorLogs += "Command dataplot not supported yet."; Nil }


	/**
	 * Parses fileplot commands.
	 */
	private def parseFileplot(ctx : PSTContext) : Parser[List[IShape]] = ("\\fileplot*" | "\\fileplot") ~ opt(parseParam(ctx)) ~ parseBracket(ctx) ^^
	{ case cmdName ~ _ ~ file => PSTParser.errorLogs += "Command fileplot not supported yet."; Nil }
}
