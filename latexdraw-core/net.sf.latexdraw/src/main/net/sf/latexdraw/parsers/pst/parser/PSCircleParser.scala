package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.ICircle

/**
 * A parser grouping parsers parsing circles.<br>
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
 * 2012-05-02<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSCircleParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser {
	/**
	 * Parses pscircle commands.
	 */
	def parsePscircle(ctx : PSTContext) : Parser[List[IShape]] =
		("\\pscircle*" | "\\pscircle") ~ opt(parseParam(ctx)) ~ opt(parseCoord(ctx)) ~ parseBracket(ctx) ^^ {
			case cmdName ~ _ ~ pos ~ radius =>

		parseValueDim(radius) match {
			case Some(value) =>
				var pt : PointUnit = null

				pos match {
					case Some(point) => pt = point
					case _ => pt = ctx.origin.dup
				}

				val pt2d = transformPointTo2DScene(pt, ctx)
				checkTextParsed(ctx) ::: List(createCircle(cmdName.endsWith("*"), pt2d, value._1*IShape.PPC, ctx))
			case None => PSTParser.errorLogs += "Bracket's content cannot be empty: " + cmdName; Nil
		}
	}


	/**
	 * Parses qdisk commands.
	 */
	def parseQdisk(ctx : PSTContext) : Parser[List[IShape]] =
		"\\qdisk" ~ parseCoord(ctx) ~ parseBracket(ctx) ^^ { case cmdName ~ pos ~ radius =>
		parseValueDim(radius) match {
			case Some(value) =>
				checkTextParsed(ctx) ::: List(createCircle(true, transformPointTo2DScene(pos, ctx), value._1*IShape.PPC, ctx))
			case None => PSTParser.errorLogs += "Bracket's content cannot be empty: " + cmdName; Nil
		}
	}


	/**
	 * Creates and initialises a circle.
	 */
	private def createCircle(hasStar : Boolean, centre : IPoint, radius : Double, ctx : PSTContext) : ICircle = {
		val circ = DrawingTK.getFactory.createCircle(true)
		circ.setRx(scala.math.max(0.1, radius))
		circ.setCentre(centre)
		setShapeParameters(circ, ctx)
		if(hasStar)
			setShapeForStar(circ)
		circ
	}
}