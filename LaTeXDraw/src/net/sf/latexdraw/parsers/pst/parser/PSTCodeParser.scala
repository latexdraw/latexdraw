package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IShape
import scala.collection.JavaConversions._
import java.text.ParseException

/**
 * Defines a parser parsing PST expressions.<br>
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
 * 2012-04-24<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTCodeParser extends PSTAbstractParser
	with PSFrameEllipseParser with PSCircleParser with PSLineParser with PSPolygonParser with PSWedgeArcParser
	with PSBezierParser with PSCurveParabolaParser {
	/** The entry point to parse PST texts. */
	def parsePSTCode(ctx : PSTContext) : Parser[IGroup] =
		rep(math | text |
			parsePSTBlock(ctx) |
			parsePsellipse(ctx) | parsePsframe(ctx) |
			parsePsline(ctx) | parserQline(ctx) |
			parsePscircle(ctx) | parseQdisk(ctx) |
			parsePswedge(ctx) | parsePsarc(ctx) | parsePsarcn(ctx) |
			parsePspolygon(ctx) | parsePsbezier(ctx) |
			parseParabola(ctx) | parsePscurve(ctx) | parsePsecurve(ctx) | parsePsccurve(ctx)) ^^ {
		case list =>
		val group = DrawingTK.getFactory.createGroup(false)

		list.foreach{_ match {
				case gp : List[_] => gp.foreach{sh => group.addShape(sh.asInstanceOf[IShape])}
				case gp : IGroup => gp.getShapes.foreach{sh => group.addShape(sh)}
				case sh : IShape => group.addShape(sh)
				case str: String => val txt = DrawingTK.getFactory.createText(true); txt.setText(str); group.addShape(txt)
				case _ =>
			}
		}

		group
	}


	/** Parses a PST block surrounded with brackets. */
	def parsePSTBlock(context : PSTContext) : Parser[IGroup] = "{" ~ parsePSTCode(new PSTContext(context)) ~ "}" ^^ {
		case _ ~ shapes ~ _ => shapes
	}
}
