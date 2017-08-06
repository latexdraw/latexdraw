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

import java.util

import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.shape.{IPoint, IShape}

/**
 * A parser grouping parsers parsing bézier curves.
 * @author Arnaud BLOUIN
 */
trait PSBezierParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser {
	/**
	 * Parses psbezier commands.
	 */
	def parsePsbezier(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psbezier*" | "\\psbezier") ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ rep(repN(3, parseCoord(ctx))) ~ opt(parseCoord(ctx)) ^^ {
			case cmdName ~ _ ~ arrowRaw ~ ptsRaw ~ ptRaw =>

		val listPts = ptRaw match {
			case Some(value) => ptsRaw.flatten ::: List(value)
			case None => ctx.origin.dup :: ptsRaw.flatten
		}

		val pts = new util.ArrayList[IPoint]()
		val ctrlpts = new util.ArrayList[IPoint]()
		val size = listPts.length

		// Setting the points.
		for(i <- 0 until size by 3) {
			pts.add(transformPointTo2DScene(listPts(i), ctx))
		}

		for(i <- 2 until size by 3) {
			ctrlpts.add(transformPointTo2DScene(listPts(i), ctx))
		}

		if(size > 1) {
			ctrlpts.add(0, transformPointTo2DScene(listPts(1), ctx))
		}

		var closed = false

		if(pts.size() > 2 && pts.get(0).equals(pts.get(pts.size() - 1))) {
			pts.remove(pts.size() - 1)
			ctrlpts.remove(ctrlpts.size() - 1)
			closed = true
		}

		val bezier = ShapeFactory.INST.createBezierCurve(pts, ctrlpts)
		bezier.setIsClosed(closed)
		setShapeParameters(bezier, ctx)
		setArrows(bezier, arrowRaw, invert = false, ctx)
		bezier.updateSecondControlPoints()

		if(cmdName.endsWith("*")) {
			setShapeForStar(bezier)
		}

		checkTextParsed(ctx) ::: List(bezier)
	}
}
