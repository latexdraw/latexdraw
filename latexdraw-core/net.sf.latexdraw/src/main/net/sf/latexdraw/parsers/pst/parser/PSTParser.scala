package net.sf.latexdraw.parsers.pst.parser

import java.text.ParseException
import net.sf.latexdraw.glib.models.interfaces.IGroup
import scala.collection.mutable.ListBuffer

/**
 * Defines a PST parser.<br>
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
 * 2012-04-23<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class PSTParser extends PSTAbstractParser with PSTCodeParser {
	@throws(classOf[ParseException])
	def parsePSTCode(content : String) : Option[IGroup] = {
		val tokens = new lexical.Scanner("{\n" + content + "\n}\n")
		val result = phrase(parsePSTCode(new PSTContext(false)))(tokens)

		PSTParser._errorLogs.foreach{msg => println(msg)}

		result match {
			case Success(tree, _) => Some(tree)
			case e: NoSuccess => throw new ParseException(result.toString, -1)
		}
	}
}


/**
 * Companion object of the PST parser used to encapsulate shared elements.
 */
object PSTParser {
	protected val _errorLogs = ListBuffer[String]()

	/**
	 * Adds the given message to the error loggers.
	 */
	def errorLogs_+=(msg : String) {
		if(_errorLogs!=null)
			_errorLogs += msg
	}


	/** The error logger. */
	def errorLogs = _errorLogs

	/**
	 * Cleans the parsing logs.
	 */
	def cleanErrors() {
		_errorLogs.clear
	}
}
