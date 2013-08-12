package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.DrawingTK

/**
 * A parser grouping parsers parsing curves and parabolas.<br>
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
 * 2012-05-04<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSCurveParabolaParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser with PSTValueParser {
	/**
	 * Parses pscurve commands.
	 */
	def parsePscurve(ctx : PSTContext) : Parser[List[IShape]] = ("\\pscurve*" | "\\pscurve") ~> parsePscurves(ctx)


	/**
	 * Parses pscurve commands.
	 */
	def parsePsecurve(ctx : PSTContext) : Parser[List[IShape]] = ("\\psecurve*" | "\\psecurve") ~> parsePscurves(ctx)


	/**
	 * Parses pscurve commands.
	 */
	def parsePsccurve(ctx : PSTContext) : Parser[List[IShape]] = ("\\psccurve*" | "\\psccurve") ~> parsePscurves(ctx)


	private def parsePscurves(ctx : PSTContext) : Parser[List[IShape]] =
		opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ repN(3, parseCoord(ctx)) ~ rep(parseCoord(ctx)) ^^ {
		case _ ~ arrowRaw ~ firstPtsRaw ~ lastPtsRaw =>
		PSTParser.errorLogs += "Commands pscurve, psecurve, and psccurve not supported yet."
		Nil
	}


	/**
	 * Parses parabola commands.
	 */
	def parseParabola(ctx : PSTContext) : Parser[List[IShape]] =
		("\\parabola*" | "\\parabola") ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ parseCoord(ctx) ~ parseCoord(ctx) ^^ {
			case cmdName ~ _ ~ arrowRaw ~ pt1Raw ~ pt2Raw =>
		PSTParser.errorLogs += "Command parabola not supported yet."
		Nil
	}
}
