package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IArc
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.models.interfaces.ICircleArc

/**
 * A parser grouping parsers parsing arcs.<br>
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
 * 2012-05-03<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSWedgeArcParser extends PSTAbstractParser
	with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser with PSTValueParser {
	/**
	 * Parses pswedge commands.
	 */
	def parsePswedge(ctx : PSTContext) : Parser[List[IShape]] =
		("\\pswedge*" | "\\pswedge") ~ opt(parseParam(ctx)) ~ opt(parseCoord(ctx)) ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ^^ {
		case cmdName ~ _ ~ posRaw ~ radiusStr ~ angle1Str ~ angle2Str =>

		val pos = posRaw match {
			case Some(value) => value
			case None => DrawingTK.getFactory.createPoint(ctx.origin.getX, ctx.origin.getY)
		}

		val radius = parseValueDim(radiusStr) match {
			case Some(value) => value
			case None => PSTParser.errorLogs += "pswedge's radius cannot be parsed: " + radiusStr ; Double.NaN
		}

		val angle1 = parseValueAngle(angle1Str) match {
			case Some(value) => value
			case None => PSTParser.errorLogs += "pswedge's angle1 cannot be parsed: " + angle1Str ; Double.NaN
		}

		val angle2 = parseValueAngle(angle2Str) match {
			case Some(value) => value
			case None => PSTParser.errorLogs += "pswedge's angle2 cannot be parsed: " + angle2Str ; Double.NaN
		}

		if(radius.isNaN || angle1.isNaN || angle2.isNaN)
			Nil
		else
			List(createCircleArc(IArc.ArcStyle.WEDGE, cmdName.endsWith("*"), transformPointTo2DScene(pos), radius, angle1, angle2, None, ctx))
	}


	/**
	 * Creates an arc using the given parameters.
	 */
	private def createCircleArc(arcType : IArc.ArcStyle, hasStar : Boolean, pos : IPoint, radius : Double, angle1 : Double, angle2 : Double,
							arrows : Option[Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle]], ctx : PSTContext) : ICircleArc = {
		val arc = DrawingTK.getFactory.createCircleArc(true)
		arc.setAngleStart(scala.math.toRadians(angle1))
		arc.setAngleEnd(scala.math.toRadians(angle2))
		arc.setCentre(pos)
		arc.setRx(scala.math.abs(radius*IShape.PPC))
		arc.setArcStyle(arcType)
		setShapeParameters(arc, ctx)
		if(hasStar)
			setShapeForStar(arc)
		arc
	}
}
