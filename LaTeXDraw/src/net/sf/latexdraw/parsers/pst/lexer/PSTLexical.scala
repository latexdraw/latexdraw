package net.sf.latexdraw.parsers.pst.lexer

import scala.collection.mutable.HashSet
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.combinator.lexical.Lexical
import scala.util.parsing.combinator.lexical.Scanners
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.input.CharArrayReader.EofCh
import scala.util.parsing.input.Position
import scala.collection.JavaConversions._

/**
 * Defines a PSTricks lexical.<br>
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
class PSTLexical extends Lexical with PSTTokens {
  /** This token is produced by a scanner {@see Scanner} when scanning failed. */
  override def errorToken(msg: String): PSTToken = new KError(msg)

  def eof = elem("eof", ch => ch == EofCh)

  protected def kident(name: String) : PSTToken = Identifier(name)

  override def whitespace: Parser[Any] = rep(whitespaceChar)

  val delimiters : HashSet[String] = HashSet("{", "}", ",", "(", ")", "[", "]","=")

  def comment : Parser[PSTToken] = (
	positioned('%' ~> rep( chrExcept(EofCh, '\n') ) ^^ { case content => Comment(content.mkString) })
  )

   def identifier : Parser[PSTToken] = (
    positioned(identChar ~ rep( identChar | digit ) ^^ { case first ~ rest => Identifier((first::rest).mkString) } )
   )

  // legal identifier chars other than digits
  def identChar = letter | elem('_')


  // see `token' in `Scanners'
  def token: Parser[PSTToken] = (
     positioned(identifier ^^{ case id => kident(id.toString) })
     | positioned(comment ^^{ case c => c })
     | positioned(eof ^^ {case _ => KEOF() })
     | positioned(delim)
     /* | floatingToken*/
     |  positioned( elem("illegal character", p => true ) ^^^KError("illegal character") ) //  failure("illegal character"))
    )


  private lazy val _delim: Parser[PSTToken] = {
    // construct parser for delimiters by |'ing together the parsers for the individual delimiters,
    // starting with the longest one -- otherwise a delimiter D will never be matched if there is
    // another delimiter that is a prefix of D
    def parseDelim(s: String): Parser[PSTToken] = positioned(accept(s.toList) ^^ { x => Delimiter(s) })

    val d = new Array[String](delimiters.size)
    delimiters.copyToArray(d, 0)
    scala.util.Sorting.quickSort(d)
    (d.toList map parseDelim).foldRight(failure("no matching delimiter") : Parser[PSTToken])((x, y) => y | x)
  }


  protected def delim: Parser[PSTToken] = _delim
}
