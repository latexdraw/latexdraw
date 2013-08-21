package net.sf.latexdraw.parsers.pst.parser

import scala.annotation.migration

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IDot
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * A parser grouping parsers parsing dots.<br>
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
trait PSDotParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTValueParser {
	/**
	 * Parses psdot commands.
	 */
	def parsePsdot(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psdot*" | "\\psdot") ~ opt(parseParam(ctx)) ~ opt(parseCoord(ctx)) ^^ { case cmdName ~ _ ~ posRaw =>


		val pos = posRaw match {
			case Some(value) => transformPointTo2DScene(value, ctx)
			case None => transformPointTo2DScene(ctx.origin, ctx)
		}

		checkTextParsed(ctx) ::: List(createDot(pos, cmdName.endsWith("*"), ctx))
	}



	/**
	 * Parses psdots commands.
	 */
	def parsePsdots(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psdots*" | "\\psdots") ~ opt(parseParam(ctx)) ~ rep1(parseCoord(ctx)) ^^ { case cmdName ~ _ ~ ptList =>

		val hasStar = cmdName.endsWith("*")
		checkTextParsed(ctx) ::: ptList.map{pt => createDot(transformPointTo2DScene(pt, ctx), hasStar, ctx)}
	}



	private def createDot(pos : IPoint, hasStar : Boolean, ctx : PSTContext) : IDot = {
		val dot = DrawingTK.getFactory.createDot(pos, true)
		val dotSizeDim = if(ctx.arrowDotSize._1+ctx.arrowDotSize._2<0) scala.math.abs(ctx.arrowDotSize._1) else ctx.arrowDotSize._1
		val dotSizeNum = if(ctx.arrowDotSize._1+ctx.arrowDotSize._2<0) scala.math.abs(ctx.arrowDotSize._2) else ctx.arrowDotSize._2

		dot.setRadius((dotSizeDim+dotSizeNum*ctx.lineWidth)*IShape.PPC*ctx.dotScale._1)
		dot.setRotationAngle(dot.getRotationAngle+scala.math.toRadians(ctx.dotAngle))
		setShapeParameters(dot, ctx)
		dot.setDotStyle(ctx.dotStyle)
		if(hasStar)
			setShapeForStar(dot)
		dot
	}
}
