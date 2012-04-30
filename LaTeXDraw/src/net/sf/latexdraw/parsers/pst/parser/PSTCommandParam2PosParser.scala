package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * A parser that parses commands composed of 1 optional parameter block,
 * and 1 mandatory position block, and 1 optional position block.<br>
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
 * 2012-04-26<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTCommandParam2PosParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser {
	def parseCommandParame2Pos(ctx : PSTContext) : Parser[List[IShape]] =
		command ~ opt(parseParam(ctx)) ~ parseCoord(ctx) ~ opt(parseCoord(ctx)) ^^ { case cmd ~ _ ~ pt1 ~ pt2 =>
			var p1 : IPoint = null
			var p2 : IPoint = null

			pt2 match {
				case Some(pt) =>
					p1 = pt1
					p2 = pt
				case _ =>
					p1 = DrawingTK.getFactory.createPoint(ctx.origin.getX, ctx.origin.getY)
					p2 = pt1
			}

			p1 = transformPointTo2DScene(p1)
			p2 = transformPointTo2DScene(p2)

			cmd.substring(1) match {
				case "psframe*" | "psframe" =>
					val rec = DrawingTK.getFactory.createRectangle(true)
					rec.setPosition(p1)
					rec.setWidth(scala.math.abs(p2.getX-p1.getX))
					rec.setHeight(scala.math.abs(p2.getY-p1.getY))
					setShapeParameters(rec, ctx)
					List(rec)
				case name => println("Unknown command: " + name) ; Nil
			}
	}
}
