package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.views.latex.DviPsColors
import java.awt.Color
import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * A parser grouping parsers parsing text commands.<br>
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
 * 2012-10-14<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait TextCommandsParser extends PSTAbstractParser with PSTBracketBlockParser with IPSTCodeParser {
	/**
	 * Parses commands handling texts.
	 */
	def parsetextCommands(ctx:PSTContext) : Parser[List[IShape]] =
		parseUseFontCommand(ctx) | parseColorCommand(ctx) | parseTextcolorCommand(ctx) | parseTextSizeCommand(ctx)


	private def parseTextSizeCommand(ctx:PSTContext):Parser[List[IShape]] =
		("\\tiny" | "\\scriptsize" | "\\footnotesize" | "\\small" | "\\normalsize" | "\\large" | "\\Large" | "\\huge" | "\\Huge") ^^ {
		case cmd =>
			ctx.textParsed +=cmd
			ctx.parsedTxtNoTxt = false
			Nil
	}


	/** Parses the command \textcolor */
	private def parseTextcolorCommand(ctx:PSTContext) : Parser[List[IShape]] = {
		val newCtx = new PSTContext(ctx)
		"\\textcolor" ~ parseColorBlock(newCtx) ~ parsePSTBlock(newCtx, newCtx.isPsCustom) ^^ {
			case _ ~ _ ~ shapes  => List(shapes)
		}
	}


	/** Parses the colour contained in the block. */
	private def parseColorBlock(ctx:PSTContext) : Parser[Unit] = {
		parseBracket(ctx) ^^ {
			case colourTxt =>
				DviPsColors.INSTANCE.getColour(colourTxt) match {
				case c:Color => ctx.textColor = c
				case _ =>
				}
		}
	}


	/** Parses the command \color */
	private def parseColorCommand(ctx:PSTContext) : Parser[List[IShape]] = "\\color" ~ parseColorBlock(ctx) ^^ {
		case _ ~ _ => Nil
	}


	/** Parses the usefont command. */
	private def parseUseFontCommand(ctx:PSTContext) : Parser[List[IShape]] =
		"\\usefont" ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ^^ {
		case _ ~ encoding ~ family ~ series ~ shapes =>
			ctx.textParsed += "\\usefont{"+encoding+"}{"+family+"}{"+series+"}{"+shapes+"}"
			fontShape.toFontShape(shapes) match {
				case Some(value) => ctx.currFontShape = value
				case _ =>
			}
			fontFamily.toFontFamily(family) match {
				case Some(value) => ctx.currFontFamily = value
				case _ =>
			}
			fontSerie.toFontSerie(series) match {
				case Some(value) => ctx.currFontSerie = value
				case _ =>
			}
			Nil
	}
}
