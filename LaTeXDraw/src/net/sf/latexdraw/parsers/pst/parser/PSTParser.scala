package net.sf.latexdraw.parsers.pst.parser

import java.text.ParseException

import net.sf.latexdraw.glib.models.interfaces.IGroup

/**
 * Defines a PST parser.<br>
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
 * 2012-04-23<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class PSTParser extends PSTAbstractParser with PSTCodeParser {
	@throws(classOf[ParseException])
	def parsePSTCode(content: String): Option[IGroup] = {
		val tokens = new lexical.Scanner(content + "\n")
		val result = phrase(parsePSTCode(new PSTContext()))(tokens)

		result match {
			case Success(tree, _) => Some(tree)
			case e: NoSuccess => throw new ParseException(result.toString, -1)
		}
	}
}
