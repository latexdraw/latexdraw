/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.shape.{IPoint, IPolygon, IShape}

import scala.collection.JavaConverters
import scala.collection.mutable.ListBuffer

/**
 * A parser grouping parsers parsing polygons.
 * @author Arnaud BLOUIN
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
		val pol = ShapeFactory.INST.createPolygon(JavaConverters.bufferAsJavaList(pts))

		setShapeParameters(pol, ctx)

		if(hasStar)
			setShapeForStar(pol)
		pol
	}
}