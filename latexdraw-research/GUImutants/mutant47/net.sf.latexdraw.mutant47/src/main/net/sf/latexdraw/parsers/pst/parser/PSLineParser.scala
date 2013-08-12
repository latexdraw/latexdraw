package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPolyline
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.IArrow

/**
 * A parser grouping parsers parsing lines.<br>
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
trait PSLineParser extends PSTAbstractParser
	with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser with PSTValueParser {
	/**
	 * Parses psline commands.
	 */
	def parsePsline(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psline*" | "\\psline") ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ rep1(parseCoord(ctx)) ^^ { case cmdName ~ _ ~ arr ~ ptList =>

		val ptList2 = ptList.length match {
				case 1 => ctx.origin.dup :: ptList
				case _ => ptList
			}

		val ptList3 = new ListBuffer[IPoint]
		ptList2.foreach{pt => ptList3 += transformPointTo2DScene(pt, ctx)}

		checkTextParsed(ctx) ::: List(createLine(cmdName.endsWith("*"), ptList3, arr, ctx))
	}


	/**
	 * Parses qline commands.
	 */
	def parserQline(ctx : PSTContext) : Parser[List[IShape]] =
		"\\qline" ~ parseCoord(ctx) ~ parseCoord(ctx) ^^ { case _ ~ pt1 ~ pt2 =>

		val ptList = ListBuffer(transformPointTo2DScene(pt1, ctx), transformPointTo2DScene(pt2, ctx))
		checkTextParsed(ctx) ::: List(createLine(false, ptList, None, ctx))
	}


	/**
	 * Creates and initialises a line.
	 */
	private def createLine(hasStar : Boolean, pts : ListBuffer[IPoint], arrows : Option[String], ctx : PSTContext) : IPolyline = {
		val line = DrawingTK.getFactory.createPolyline(true)
		pts.foreach{pt => line.addPoint(pt)}

		setShapeParameters(line, ctx)

		setArrows(line, arrows, false, ctx)

		if(hasStar)
			setShapeForStar(line)
		line
	}
}
