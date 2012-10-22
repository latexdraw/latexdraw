package net.sf.latexdraw.parsers.pst.parser

/**
 * A parser parsing psframebox commands.<br>
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
 * 2012-10-22<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSFrameboxParser extends PSTAbstractParser with PSTBracketBlockParser {
	def parsePsFrameboxCmds(ctx:PSTContext) : Parser[Unit] =
		("\\psframebox" | "\\psframebox*" | "\\psdblframebox" | "\\psdblframebox*" | "\\psshadowbox" | "\\psshadowbox*" |
		"\\pscirclebox" | "\\pscirclebox*" | "\\psovalbox" | "\\psovalbox*" | "\\psdiabox" | "\\psdiabox*"  | "\\pstribox" | "\\pstribox*") ~
		opt(parseSquaredBracket(ctx)) ~ parseBracket(ctx) ^^ {
		case nameCmd ~ param ~ block =>
			val paramStr = if(param.isDefined) "[" + param.get + "]" else ""
			ctx.textParsed += nameCmd + paramStr + "{" + block + "}"
	}
}
