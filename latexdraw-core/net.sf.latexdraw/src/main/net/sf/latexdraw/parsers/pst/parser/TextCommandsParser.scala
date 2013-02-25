package net.sf.latexdraw.parsers.pst.parser

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
trait TextCommandsParser extends PSTAbstractParser with PSTBracketBlockParser {
	/**
	 * Parses commands handling texts.
	 */
	def parsetextCommands(ctx:PSTContext) : Parser[Unit] = parseUseFontCommand(ctx)


	/**
	 * Parses the usefont command.
	 */
	private def parseUseFontCommand(ctx:PSTContext) : Parser[Unit] = "\\usefont" ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ^^ {
		case _ ~ encoding ~ family ~ series ~ shapes =>
			fontShape.toFontShape(shapes) match {
				case Some(value) => ctx.fontShape = Some(value)
				case _ =>
			}
			fontFamily.toFontFamily(family) match {
				case Some(value) => ctx.fontFamily = Some(value)
				case _ =>
			}
			fontSerie.toFontSerie(series) match {
				case Some(value) => ctx.fontSerie = Some(value)
				case _ =>
			}
	}
}
