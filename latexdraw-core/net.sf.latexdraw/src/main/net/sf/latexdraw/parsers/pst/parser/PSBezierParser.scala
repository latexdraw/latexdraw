package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * A parser grouping parsers parsing bézier curves.<br>
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
trait PSBezierParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser {
	/**
	 * Parses psbezier commands.
	 */
	def parsePsbezier(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psbezier*" | "\\psbezier") ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ rep(repN(3, parseCoord(ctx))) ~ opt(parseCoord(ctx)) ^^ {
			case cmdName ~ _ ~ arrowRaw ~ ptsRaw ~ ptRaw =>

		val listPts = ptRaw match {
			case Some(value) => ptsRaw.flatten ::: List(value)
			case None => DrawingTK.getFactory.createPoint(ctx.origin.getX, ctx.origin.getY) :: ptsRaw.flatten
		}

		val bezier = DrawingTK.getFactory.createBezierCurve(true)
		var j = 0

		// Setting the points.
		for(i <- 0 to listPts.length-1 by 3) bezier.addPoint(transformPointTo2DScene(listPts(i)))
		// Setting the second control points.
		for(i <- 1 to listPts.length-1 by 3){
			bezier.getSecondCtrlPtAt(j).setPoint(transformPointTo2DScene(listPts(i)))
			j +=1
		}
		// Setting the first control points
		j = 1
		for(i <- 2 to listPts.length-1 by 3){
			bezier.getFirstCtrlPtAt(j).setPoint(transformPointTo2DScene(listPts(i)))
			j +=1
		}

		setShapeParameters(bezier, ctx)
		setArrows(bezier, arrowRaw, false)
		bezier.updateSecondControlPoints

		if(cmdName.endsWith("*"))
			setShapeForStar(bezier)

		checkTextParsed(ctx) ::: List(bezier)
	}
}
