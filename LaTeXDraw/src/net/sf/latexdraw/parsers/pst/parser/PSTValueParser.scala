package net.sf.latexdraw.parsers.pst.parser

import java.awt.Color
import net.sf.latexdraw.glib.models.interfaces.IAxes
import net.sf.latexdraw.glib.views.latex.DviPsColors

/**
 * A parser that parses a value corresponding to an object.
 * An object can be a name or a command.<br>
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
trait PSTValueParser {
	/** The regex expression of an identifier. */
	val identPattern = """(\w)+""".r
	/** The regex expression of PST command name. */
	val cmdPattern = """(\\\\)ps\w+""".r


	/** This parser parses a object value that can be either a name nor a command. */
	def parseValueColour(value : String, ctx : PSTContext) : Option[Color] = {
		try {
			value match {
				case cmdPattern(_) =>
					ctx.getParam(value.replace("\\\\ps", "")) match {
						case col : Color => Some(col)
						case _ => None
					}
				case identPattern(_) =>
					DviPsColors.INSTANCE.getColour(value) match {
						case col : Color => Some(col)
						case _ => PSTParser.errorLogs += "The following colour is unknown: " + value; None
					}
				case _ => None
			}
		}catch{case ex => None }
	}


	/**
	 * Parses a boolean value.
	 */
	def parseValueBoolean(value : String) : Option[Boolean] = {
		value match {
			case "true" => Some(true)
			case "false" => Some(false)
			case _ => None
		}
	}
}