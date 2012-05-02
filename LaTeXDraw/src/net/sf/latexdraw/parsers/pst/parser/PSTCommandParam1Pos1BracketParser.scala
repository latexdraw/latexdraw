package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.ICircle

/**
 * A parser that parses PST commands composed of 1 coordinate block and
 * 1 bracket block (such as pscircle).<br>
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
 * 2012-05-02<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTCommandParam1Pos1BracketParser extends PSTAbstractParser
		with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser {
	/**
	 * Parses a PST command composed of 1 block of coordinates and 1 bracket block \foo[](,){}.
	 */
	def parseCommandParam1Pos1Bracket(ctx : PSTContext) : Parser[List[IShape]] =
		command ~ opt(parseParam(ctx)) ~ opt(parseCoord(ctx)) ~ parseBracket(ctx) ^^ { case cmd ~ param ~ pos ~ radius =>

		parseValueDim(radius) match {
			case Some(value) =>
				var pt : IPoint = null
				var hasParam = param.isDefined
				var hasPoint = pos.isDefined

				pos match {
					case Some(point) => pt = point
					case _ => pt = DrawingTK.getFactory.createPoint(ctx.origin.getX, ctx.origin.getY)
				}

				pt = transformPointTo2DScene(pt)
				createShape(cmd, pt, value*IShape.PPC, ctx, hasParam, hasPoint)
			case None => PSTParser.errorLogs += "Bracket's content cannot be empty: " + cmd; Nil
		}
	}


	/**
	 * Creates shapes corresponding to the given pst code.
	 */
	private def createShape(cmdName : String, centre : IPoint, radius : Double,
							ctx : PSTContext, hasParam : Boolean, hasPoint : Boolean) : List[IShape] = {
		val name = cmdName.substring(1)
		name match {
			case "pscircle*" | "pscircle" => List(createCircle(cmdName, centre, radius, ctx))
			case "qdisk" => createqdisk(cmdName, centre, radius, ctx, hasParam, hasPoint)
			case name => PSTParser.errorLogs += "Unknown command: " + name ; Nil
		}
	}


	/**
	 * Creates and initialises a circle using a qdisk command.
	 */
	private def createqdisk(cmdName : String, centre : IPoint, radius : Double, ctx : PSTContext,
							hasParam : Boolean, hasPoint : Boolean) : List[IShape] = {
		if(hasParam) { PSTParser.errorLogs += "The qdisk command cannot have parameters."; Nil }
		else if(!hasPoint) { PSTParser.errorLogs += "The qdisk command must have its centre defined."; Nil }
		else List(createCircle(cmdName+"*", centre, radius, ctx))
	}


	/**
	 * Creates and initialises a circle.
	 */
	private def createCircle(cmdName : String, centre : IPoint, radius : Double, ctx : PSTContext) : ICircle = {
		val circ = DrawingTK.getFactory.createCircle(true)
		circ.setRx(scala.math.max(0.1, radius))
		circ.setCentre(centre)
		setShapeParameters(circ, ctx)
		setShapeForStar(circ, cmdName)
		circ
	}
}
