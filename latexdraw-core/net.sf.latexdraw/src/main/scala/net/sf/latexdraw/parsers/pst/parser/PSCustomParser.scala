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
import java.util.Collections

import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.shape.{FreeHandStyle, IFreehand, IShape}

/**
 * A parser grouping parsers parsing commands related to the pscustom command.
 * @author Arnaud BLOUIN
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
	def parseMrestore(ctx : PSTContext) : Parser[List[IShape]] = "\\mrestore" ^^ { _ =>
		if (ctx.isPsCustom)
			PSTParser.errorLogs += "The command mrestore is not supported yet."
		else
			PSTParser.errorLogs += "The command mrestore " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses msave commands.
	 */
	def parseMsave(ctx : PSTContext) : Parser[List[IShape]] = "\\msave" ^^ { _ =>
		if (ctx.isPsCustom)
			PSTParser.errorLogs += "The command msave is not supported yet."
		else
			PSTParser.errorLogs += "The command msave " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses swapaxes commands.
	 */
	def parseSwapaxes(ctx : PSTContext) : Parser[List[IShape]] = "\\swapaxes" ^^ { _ =>
		if (ctx.isPsCustom)
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
	def parseGrestore(ctx : PSTContext) : Parser[List[IShape]] = "\\grestore" ^^ { _ =>
		if(ctx.isPsCustom)
			PSTParser.errorLogs += "The command grestore is not supported yet."
		else
			PSTParser.errorLogs += "The command grestore " + notIntoPscustomBlockErrorMsg
		Nil
	}


	/**
	 * Parses gsave commands.
	 */
	def parseGsave(ctx : PSTContext) : Parser[List[IShape]] = "\\gsave" ^^ { _ =>
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
	def parseClosepath(ctx : PSTContext) : Parser[List[IShape]] = "\\closepath" ^^ { _ =>
		if(ctx.isPsCustom) {
			val fh = ShapeFactory.INST.createFreeHand(Collections.emptyList())
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
		val freeHand = ShapeFactory.INST.createFreeHand(
			util.Arrays.asList(ShapeFactory.INST.createPoint(ctx.psCustomLatestPt), transformPointTo2DScene(pt, ctx)))

		if(isLine)
			freeHand.setType(FreeHandStyle.LINES)
		else
			freeHand.setType(FreeHandStyle.CURVES)

		setShapeGeneralParameters(freeHand, ctx)
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
	def parseNewpath(ctx : PSTContext) : Parser[List[IShape]] = "\\newpath" ^^ { _ =>
		if(!ctx.isPsCustom)
			PSTParser.errorLogs += "The command newpath " + notIntoPscustomBlockErrorMsg
		checkTextParsed(ctx)
	}
}
