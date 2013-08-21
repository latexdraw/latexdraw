package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IPolygon

/**
 * A parser grouping parsers parsing polygons.<br>
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
 * 2012-05-03<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSPolygonParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTValueParser {
	/**
	 * Parses pspolygon commands.
	 */
	def parsePspolygon(ctx : PSTContext) : Parser[List[IShape]] =
		("\\pspolygon*" | "\\pspolygon") ~ opt(parseParam(ctx)) ~ parseCoord(ctx) ~ rep1(parseCoord(ctx)) ^^ { case cmdName ~ _ ~ pt1 ~ pts =>

		val ptList = pts.length match {
				case 1 => ctx.origin.dup :: pt1 :: pts
				case _ => pt1 :: pts
			}

		val ptList2 = new ListBuffer[IPoint]
		ptList.foreach{pt => ptList2 += transformPointTo2DScene(pt, ctx)}

		checkTextParsed(ctx) ::: List(createPolygon(cmdName.endsWith("*"), ptList2, ctx))
	}


	/**
	 * Creates and initialises a line.
	 */
	private def createPolygon(hasStar : Boolean, pts : ListBuffer[IPoint], ctx : PSTContext) : IPolygon = {
		val pol = DrawingTK.getFactory.createPolygon(true)
		pts.foreach{pt => pol.addPoint(pt)}

		setShapeParameters(pol, ctx)

		if(hasStar)
			setShapeForStar(pol)
		pol
	}
}