package net.sf.latexdraw.parsers.pst.parser.sub

import scala.collection.mutable.HashMap
import scala.util.parsing.combinator.syntactical.TokenParsers
import net.sf.latexdraw.parsers.pst.lexer.PSTLexical

/**
 * Defines an abstract PST parser.<br>
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
trait PSTAbstractParser extends TokenParsers {
  type Tokens = net.sf.latexdraw.parsers.pst.lexer.PSTTokens

  val lexical = new net.sf.latexdraw.parsers.pst.lexer.PSTLexical {
    override def whitespace: Parser[Any] = rep(whitespaceChar | comment)
  }

  import lexical._

  protected val delimCache : HashMap[String, Parser[String]] = HashMap.empty

  /** A parser which matches an identifier */
  def ident: Parser[String] = elem("identifier", _.isInstanceOf[Identifier]) ^^ (_.chars)

  //Error handling
  def orFailure[A](a : Parser[A], msg : String) : Parser[A] = ( a | failure(msg) )
}
