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
	with PSFrameEllipseDiamondTriangleParser with PSCircleParser with PSLineParser with PSPolygonParser with PSWedgeArcParser
	with PSBezierParser with PSCurveParabolaParser with PSDotParser with PSGridAxes with PSTPlotParser {
	/** The entry point to parse PST texts. */
	def parsePSTCode(ctx : PSTContext) : Parser[IGroup] =
		rep(math | text |
			parsePSTBlock(ctx) | parsePspictureBlock(ctx) |
			parsePsellipse(ctx) | parsePsframe(ctx) | parsePsdiamond(ctx) | parsePstriangle(ctx) |
			parsePsline(ctx) | parserQline(ctx) |
			parsePscircle(ctx) | parseQdisk(ctx) |
			parsePspolygon(ctx) | parsePsbezier(ctx) |
			parsePsdot(ctx) | parsePsdots(ctx) |
			parsePsgrid(ctx) |
			parsePswedge(ctx) | parsePsarc(ctx) | parsePsarcn(ctx) | parsePsellipticarc(ctx) | parsePsellipticarcn(ctx) |
			parseParabola(ctx) | parsePscurve(ctx) | parsePsecurve(ctx) | parsePsccurve(ctx) |
			parsePSTPlotCommands(ctx)) ^^ {
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


	/**
	 * Parses begin{pspicture} \end{pspicture} blocks.
	 */
	def parsePspictureBlock(ctx : PSTContext) : Parser[IGroup] = {
		val ctx2 = new PSTContext(ctx)
		parseBeginPspicture(ctx2) ~> parsePSTCode(ctx2) <~ "\\end" <~ "{" <~ "pspicture" <~ "}"
	}


	/**
	 * Parses begin{pspicture} commands.
	 */
	private def parseBeginPspicture(ctx : PSTContext) : Parser[Any] =
		"\\begin" ~> "{" ~> "pspicture" ~> "}" ~> parseCoord(ctx) ~ opt(parseCoord(ctx)) ^^ {
		case p1 ~ p2 =>
		p2 match {
			case Some(value) =>
				ctx.pictureSWPt = p1
				ctx.pictureNEPt = value
			case _ =>
				ctx.pictureSWPt = DrawingTK.getFactory.createPoint
				ctx.pictureNEPt = p1
		}
	}
}
