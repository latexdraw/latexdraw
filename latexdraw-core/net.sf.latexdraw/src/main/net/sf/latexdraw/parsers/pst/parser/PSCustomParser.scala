package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IFreehand
import net.sf.latexdraw.glib.models.interfaces.IPoint

/**
 * A parser grouping parsers parsing commands related to the pscustom command.<br>
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
 * 2012-07-09<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSCustomParser extends PSTAbstractParser with PSTCoordinateParser with PSTParamParser with PSTBracketBlockParser {
	protected val notIntoPscustomBlockErrorMsg = "must be located into a pscustom block"

	/**
	 * This parser contains all the parser related to pscustom commands.
	 */
	def parsePSCustomCommands(ctx : PSTContext) : Parser[List[IShape]] =
		parseNewpath(ctx) | parseMoveTo(ctx) | parseLineTo(ctx) | parseCurveTo(ctx) | parseClosepath(ctx) |
		parseRcurveTo(ctx) | parseGsave(ctx) | parseGrestore(ctx) | parseStroke(ctx) | parseFill(ctx) |
		parseTranslate(ctx) | parseScale(ctx) | parseRotate(ctx) | parseSwapaxes(ctx) | parseMsave(ctx) |
		parseMrestore(ctx) | parseOpenshadow(ctx) | parseClosedshadow(ctx) | parseMovepath(ctx) | parseRlineto(ctx)


	/**
	 * Parses rlineto commands.
	 */
	def parseRlineto(ctx : PSTContext) : Parser[List[IShape]] = "\\rlineto" ~ parseCoord(ctx) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command rlineto is not supported yet."
		else
			PSTParser.errorLogs += "The command rlineto " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses movepath commands.
	 */
	def parseMovepath(ctx : PSTContext) : Parser[List[IShape]] = "\\movepath" ~ parseCoord(ctx) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command movepath is not supported yet."
		else
			PSTParser.errorLogs += "The command movepath " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses closedshadow commands.
	 */
	def parseClosedshadow(ctx : PSTContext) : Parser[List[IShape]] = "\\closedshadow" ~ opt(parseParam(ctx)) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command closedshadow is not supported yet."
		else
			PSTParser.errorLogs += "The command closedshadow " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses openshadow commands.
	 */
	def parseOpenshadow(ctx : PSTContext) : Parser[List[IShape]] = "\\openshadow" ~ opt(parseParam(ctx)) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command openshadow is not supported yet."
		else
			PSTParser.errorLogs += "The command openshadow " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses mrestore commands.
	 */
	def parseMrestore(ctx : PSTContext) : Parser[List[IShape]] = "\\mrestore" ^^ { case _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command mrestore is not supported yet."
		else
			PSTParser.errorLogs += "The command mrestore " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses msave commands.
	 */
	def parseMsave(ctx : PSTContext) : Parser[List[IShape]] = "\\msave" ^^ { case _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command msave is not supported yet."
		else
			PSTParser.errorLogs += "The command msave " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses swapaxes commands.
	 */
	def parseSwapaxes(ctx : PSTContext) : Parser[List[IShape]] = "\\swapaxes" ^^ { case _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command swapaxes is not supported yet."
		else
			PSTParser.errorLogs += "The command swapaxes " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses rotate commands.
	 */
	def parseRotate(ctx : PSTContext) : Parser[List[IShape]] = "\\rotate" ~ parseBracket(ctx) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command rotate is not supported yet."
		else
			PSTParser.errorLogs += "The command rotate " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses scale commands.
	 */
	def parseScale(ctx : PSTContext) : Parser[List[IShape]] = "\\scale" ~ parseBracket(ctx) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command scale is not supported yet."
		else
			PSTParser.errorLogs += "The command scale " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses translate commands.
	 */
	def parseTranslate(ctx : PSTContext) : Parser[List[IShape]] = "\\translate" ~ parseCoord(ctx) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command translate is not supported yet."
		else
			PSTParser.errorLogs += "The command translate " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses fill commands.
	 */
	def parseFill(ctx : PSTContext) : Parser[List[IShape]] = "\\fill" ~ opt(parseParam(ctx)) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command fill is not supported yet."
		else
			PSTParser.errorLogs += "The command fill " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses stroke commands.
	 */
	def parseStroke(ctx : PSTContext) : Parser[List[IShape]] = "\\stroke" ~ opt(parseParam(ctx)) ^^ { case _ ~ _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command stroke is not supported yet."
		else
			PSTParser.errorLogs += "The command stroke " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses grestore commands.
	 */
	def parseGrestore(ctx : PSTContext) : Parser[List[IShape]] = "\\grestore" ^^ { case _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command grestore is not supported yet."
		else
			PSTParser.errorLogs += "The command grestore " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses gsave commands.
	 */
	def parseGsave(ctx : PSTContext) : Parser[List[IShape]] = "\\gsave" ^^ { case _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command gsave is not supported yet."
		else
			PSTParser.errorLogs += "The command gsave " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses rcurveto commands.
	 */
	def parseRcurveTo(ctx : PSTContext) : Parser[List[IShape]] = "\\rcurveto" ~ parseCoord(ctx) ~ parseCoord(ctx) ~ parseCoord(ctx) ^^ {
		case _ ~ _ ~ _ ~ _ =>
			if(ctx.isPsCustom)
				PSTParser.errorLogs += "The command rcurveto is not supported yet."
			else
				PSTParser.errorLogs += "The command rcurveto " + notIntoPscustomBlockErrorMsg
			Nil
	}


	/**
	 * Parses closepath commands.
	 */
	def parseClosepath(ctx : PSTContext) : Parser[List[IShape]] = "\\closepath" ^^ { case _ =>
		if(ctx.isPsCustom) {
			val fh = DrawingTK.getFactory.createFreeHand(DrawingTK.getFactory.createPoint, false)
			fh.setOpen(false)
			checkTextParsed(ctx) ::: List(fh)
		}
		else {
			PSTParser.errorLogs += "The command closepath " + notIntoPscustomBlockErrorMsg
			Nil
		}
	}


	/**
	 * Parses curveto commands.
	 */
	def parseCurveTo(ctx : PSTContext) : Parser[List[IShape]] = "\\curveto" ~ parseCoord(ctx) ~ parseCoord(ctx) ~ parseCoord(ctx) ^^ {
		case _ ~ _ ~ _ ~ pt3 =>
			if(ctx.isPsCustom)
				checkTextParsed(ctx) ::: List(createFreeHand(false, ctx, pt3))
			else {
				PSTParser.errorLogs += "The command curveto " + notIntoPscustomBlockErrorMsg
				Nil
		}
	}


	/**
	 * Parses lineto commands.
	 */
	def parseLineTo(ctx : PSTContext) : Parser[List[IShape]] = "\\lineto" ~ parseCoord(ctx) ^^ { case _ ~ pt =>
		if(ctx.isPsCustom)
			checkTextParsed(ctx) ::: List(createFreeHand(true, ctx, pt))
		else {
			PSTParser.errorLogs += "The command lineto " + notIntoPscustomBlockErrorMsg
			Nil
		}
	}


	private def createFreeHand(isLine : Boolean, ctx : PSTContext, pt : PointUnit) : IFreehand = {
		val freeHand = DrawingTK.getFactory.createFreeHand(DrawingTK.getFactory.createPoint(ctx.psCustomLatestPt), true)
		freeHand.addPoint(transformPointTo2DScene(pt, ctx))

		if(isLine)
			freeHand.setType(IFreehand.FreeHandType.LINES)
		else
			freeHand.setType(IFreehand.FreeHandType.CURVES)

		ctx.psCustomLatestPt.setPoint(transformPointTo2DScene(pt, ctx))
		freeHand
	}


	/**
	 * Parses moveto commands.
	 */
	def parseMoveTo(ctx : PSTContext) : Parser[List[IShape]] = "\\moveto" ~ parseCoord(ctx) ^^ { case _ ~ pt =>
		if(ctx.isPsCustom)
			ctx.psCustomLatestPt.setPoint(transformPointTo2DScene(pt, ctx))
		else
			PSTParser.errorLogs += "The command moveto " + notIntoPscustomBlockErrorMsg
		checkTextParsed(ctx)
	}


	/**
	 * Parses newpath commands.
	 */
	def parseNewpath(ctx : PSTContext) : Parser[List[IShape]] = "\\newpath" ^^ { case _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command newpath is not supported yet"
		else
			PSTParser.errorLogs += "The command newpath " + notIntoPscustomBlockErrorMsg
		checkTextParsed(ctx)
	}
}
