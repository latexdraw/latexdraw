package net.sf.latexdraw.parsers.pst.lexer

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.combinator.token.Tokens
import scala.util.parsing.input.OffsetPosition
import scala.util.parsing.input.Positional
import scala.util.parsing.input.CharArrayReader

/**
 * Defines the concept of PSTricks token.<br>
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
trait PSTTokens extends Tokens with Parsers {

	trait PSTToken extends Token with Positional {
		def getOffset : java.lang.Integer  = this.pos.asInstanceOf[OffsetPosition].offset
		def getLength : java.lang.Integer = this.toString.length
	}

	/** The class of comment tokens */
	case class Whitespace() extends PSTToken {
	  	override def chars = " "
		override def toString = " "
	}


	/** The class of comment tokens */
	case class Comment(chars : String) extends PSTToken {
		override def toString = "%"+chars
	}


	/** The class of Math mode tokens */
	case class MathMode(chars : String) extends PSTToken {
		override def toString = "$"+chars+"$"
	}


	/** The class of delim tokens */
	case class Delimiter(chars : String) extends PSTToken {
		override def toString = chars
	}


	/** The class of numeric literal tokens */
	case class NumericLit(chars : String) extends PSTToken {
		override def toString = chars
	}


	/** The class of identifier tokens */
	case class Command(chars : String) extends PSTToken {
		override def toString = chars
	}


	/** The class of text tokens */
	case class Text(chars : String) extends PSTToken {
		override def toString = chars
	}


	/** The class of identifier tokens */
	case class Identifier(chars : String) extends PSTToken {
		override def toString = chars
	}


	case class KEOF() extends PSTToken {
		override def toString = chars
		override def chars = CharArrayReader.EofCh.toString
	}


	case class KIncomplet(chars : String, msg : String) extends PSTToken {
		override def toString = chars
	}


	case class KError(chars : String) extends PSTToken {
		override def toString = chars
		override def getLength = 1
	}
}
