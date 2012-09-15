package net.sf.latexdraw.parsers.pst.parser

import scala.collection.JavaConversions._
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IFreehand

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
	with PSBezierParser with PSCurveParabolaParser with PSDotParser with PSGridAxes with PSTPlotParser with PSCustomParser {
	/** The entry point to parse PST texts. */
	def parsePSTCode(ctx : PSTContext) : Parser[IGroup] =
		rep(math | text |
			parsePSTBlock(ctx, ctx.isPsCustom) | parsePspictureBlock(ctx) | parsePsset(ctx) |
			parsePsellipse(ctx) | parsePsframe(ctx) | parsePsdiamond(ctx) | parsePstriangle(ctx) |
			parsePsline(ctx) | parserQline(ctx) |
			parsePscircle(ctx) | parseQdisk(ctx) |
			parsePspolygon(ctx) | parsePsbezier(ctx) |
			parsePsdot(ctx) | parsePsdots(ctx) |
			parsePsgrid(ctx) | parseRput(ctx) |
			parsePswedge(ctx) | parsePsarc(ctx) | parsePsarcn(ctx) | parsePsellipticarc(ctx) | parsePsellipticarcn(ctx) |
			parseParabola(ctx) | parsePscurve(ctx) | parsePsecurve(ctx) | parsePsccurve(ctx) |
			parsePSTPlotCommands(ctx) | parseNewpsobject(ctx) | parseNewpsstyle(ctx) | parsePscustom(ctx) |
			parsePSCustomCommands(ctx)) ^^ {
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


	def parsePscustom(ctx : PSTContext) : Parser[IGroup] = ("\\pscustom*" | "\\pscustom") ~ opt(parseParam(ctx)) ~ parsePSTBlock(ctx, true) ^^ {
		case cmdName ~ _ ~ shapes => shapes
			if(cmdName.endsWith("*"))
				shapes.getShapes.foreach{sh => setShapeForStar(sh)}

			var fh : IFreehand = null
			val gp = DrawingTK.getFactory.createGroup(false)

			// The different created freehand shapes must be merged into a single one.
			shapes.getShapes.foreach{sh =>
				sh match {
					case ifh : IFreehand =>
						fh match {
							case null => gp.addShape(ifh) ; fh = ifh // This shape is now the reference shape used for the merge.
							case _ =>
								if(ifh.getNbPoints==1) {// If the shape has a single point, it means it is a closepath command
									fh.setOpen(ifh.isOpen)
								} else {// Otherwise, the shape has two points. So, we take the last one and add it to the first shape.
									fh.addPoint(ifh.getPtAt(ifh.getNbPoints-1))
									fh.setType(ifh.getType)
								}
						}
					case _ => gp.addShape(sh)
				}
			}
			gp
	}


	def parseNewpsobject(ctx : PSTContext) : Parser[List[IShape]] = "\\newpsobject" ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ^^ {
		case _ ~ name ~ obj ~ attributes => PSTParser.errorLogs += "The command newpsobject is not supported yet" ; Nil
	}


	def parseNewpsstyle(ctx : PSTContext) : Parser[List[IShape]] = "\\newpsstyle" ~ parseBracket(ctx) ~ parseBracket(ctx) ^^ {
		case _ ~ name ~ attributes => PSTParser.errorLogs += "The command newpsstyle is not supported yet" ; Nil
	}


	/** Parses a PST block surrounded with brackets. */
	def parsePSTBlock(ctx : PSTContext, isPsCustomBlock : Boolean) : Parser[IGroup] = "{" ~ parsePSTCode(new PSTContext(ctx, isPsCustomBlock)) ~ "}" ^^ {
		case _ ~ shapes ~ _ => shapes
	}


	/**
	 * Parses rput commands.
	 */
	def parseRput(ctx : PSTContext) : Parser[IGroup] = ("\\rput*" | "\\rput") ~ opt(parseSquaredBracket(ctx)) ~
			opt(parseBracket(ctx)) ~ parseCoord(ctx) ~ parsePSTBlock(ctx, false) ^^ { case _ ~ refPos ~ rotation ~ coord ~ figs =>
		figs
	}


	/**
	 * Parses begin{pspicture} \end{pspicture} blocks.
	 */
	def parsePspictureBlock(ctx : PSTContext) : Parser[IGroup] = {
		val ctx2 = new PSTContext(ctx, false)
		(parseBeginPspicture(ctx2, false) ~> parsePSTCode(ctx2) <~ "\\end" <~ "{" <~ "pspicture" <~ "}") |
		(parseBeginPspicture(ctx2, true) ~> parsePSTCode(ctx2) <~ "\\end" <~ "{" <~ "pspicture*" <~ "}")
	}


	/**
	 * Parses begin{pspicture} commands.
	 */
	private def parseBeginPspicture(ctx : PSTContext, star : Boolean) : Parser[Any] =
		"\\begin" ~> "{" ~> (if(star) "pspicture*" else "pspicture") ~> "}" ~> parseCoord(ctx) ~ opt(parseCoord(ctx)) ^^ {
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
