package net.sf.latexdraw.parsers.pst.lexer

import java.text.ParseException

import scala.collection.mutable
import scala.util.parsing.combinator.lexical.Lexical
import scala.util.parsing.input.CharArrayReader.EofCh

/**
 * Defines a PSTricks lexical.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2017 Arnaud BLOUIN<br>
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
	/** This token is produced by a scanner Scanner when scanning failed. */
	override def errorToken(msg : String): PSTToken = KError(msg)


	def eof : Parser[Elem] = elem("eof", ch => ch == EofCh)


	override def whitespace : Parser[Any] = rep(whitespaceChar | comment)

	/** The reserved token of the PST language. */
	val reserved : mutable.HashSet[String] = mutable.HashSet("\\psellipse", "\\psframe", "\\psframe*", "\\psellipse*", "\\pscircle", "\\pscircle*",
			"\\qdisk", "\\psline", "\\psline*", "\\qline", "\\pspolygon*", "\\pspolygon", "\\pswedge*", "\\pswedge", "\\psarc*",
			"\\psarc", "\\psarcn*", "\\psarcn", "\\psbezier*", "\\psbezier", "\\parabola*", "\\parabola", "\\pscurve", "\\pscurve*",
			"\\psecurve*", "\\psecurve", "\\psccurve*", "\\psccurve", "\\psdiamond*", "\\psdiamond", "\\pstriangle*", "\\pstriangle",
			"\\psellipticarc*", "\\psellipticarc", "\\psellipticarcn*", "\\psellipticarcn", "\\psdot*", "\\psdot", "\\psdots*",
			"\\psdots", "\\psgrid", "\\begin", "\\end", "pspicture", "\\fileplot", "\\fileplot*", "\\dataplot*", "\\dataplot",
			"\\savedata", "\\readdata", "\\listplot", "\\listplot*", "\\psplot", "\\psplot*", "\\parametricplot*", "\\parametricplot",
			"\\psset", "\\newpsobject", "\\newpsstyle", "\\pscustom", "\\pscustom*", "\\newpath", "\\moveto", "\\lineto", "\\curveto",
			"\\closepath", "\\rcurveto", "\\gsave", "\\grestore", "\\stroke", "\\fill", "\\translate", "\\scale", "\\rotate", "\\swapaxes",
			"\\msave", "\\mrestore", "\\openshadow", "\\closedshadow", "\\movepath", "\\rlineto", "pspicture*", "\\rput*", "\\rput",
			"\\usefont", "\\psframebox", "\\psframebox*", "\\psdblframebox", "\\psdblframebox*", "\\psshadowbox", "\\psshadowbox*",
			"\\pscirclebox", "\\pscirclebox*", "\\psovalbox", "\\psovalbox*", "\\psdiabox", "\\psdiabox*", "\\pstribox", "\\pstribox*",
			"\\color", "\\textcolor", "\\definecolor", "\\scalebox", "center", "\\psscalebox", "\\tiny", "\\scriptsize", "\\footnotesize",
			"\\small", "\\normalsize", "\\large", "\\Large", "\\LARGE", "\\huge", "\\Huge", "\\`", "\\'", "\\^", "\\\"", "\\H", "\\~", "\\c",
			"\\k", "\\=", "\\b", "\\.", "\\d", "\\r", "\\u", "\\v", "\\t", "\\l", "\\textit", "\\textsf", "\\textsc", "\\textsl", "\\underline",
			"\\texttt", "\\emph", "\\textbf", "\\textit", "\\rmfamily", "\\sffamily", "\\ttfamily", "\\mdseries", "\\bfseries", "\\scshape",
			"\\slshape", "\\itshape", "\\upshape", "\\it", "\\sl", "\\sc", "\\bf", "\\psaxes", "\\includegraphics", "\\pspicture",
			"\\endpspicture")

	val textualDelimiters : mutable.HashSet[String] = mutable.HashSet(",", "(", ")", "[", "]", "=")

 	val delimiters : mutable.HashSet[String] = mutable.HashSet("{", "}", "\\") ++ textualDelimiters



 	def command : Parser[PSTToken] = positioned('\\' ~> (identifier | specialCommandName) ^^ { name => Command("\\" + name.chars) })

 	/** Companion of the parser 'command' for parsing special command names. */
	protected def specialCommandName : Parser[Identifier] =
		(elem('_')|elem('&')|elem('=')|elem('~')|elem('$')|elem('^')|elem('{')|elem('}')|
				elem('%')|elem('#')|elem('\\')|elem('\"')|elem('\'')|elem('*')|elem(',')|elem('.')|
				elem('/')|elem('@')|elem('`')) ^^ {char => Identifier(char.toString) }


	def comment : Parser[PSTToken] = positioned('%' ~> rep(chrExcept(EofCh, '\n')) ^^ { content => Comment(content.mkString) })


	def mathMode : Parser[MathMode] =
		positioned('$' ~> mathModeMultiLine ^^ { math => MathMode(math.chars) }) |
		positioned('\\' ~> '(' ~> mathModeParenthesisMultiLine ^^ { math => MathMode(math.chars) }) |
		positioned('\\' ~> '[' ~> mathModeBracketsMultiLine ^^ { math => MathMode(math.chars) })


	protected def mathModeMultiLine : Parser[MathMode] =
		'$' ^^ { _ => MathMode("")  } | chrExcept(EofCh) ~ mathModeMultiLine ^^ { case c ~ rc => MathMode(c+rc.chars) }


	protected def mathModeParenthesisMultiLine : Parser[MathMode] =
		'\\' ~ ')' ^^ { _ => MathMode("")  } | chrExcept(EofCh) ~ mathModeParenthesisMultiLine ^^ { case c ~ rc => MathMode(c+rc.chars) }


	protected def mathModeBracketsMultiLine : Parser[MathMode] =
		'\\' ~ ']' ^^ { _ => MathMode("")  } | chrExcept(EofCh) ~ mathModeBracketsMultiLine ^^ { case c ~ rc => MathMode(c+rc.chars) }


	/**
	 * Parses units.
	 */
	private def unit : Parser[String] = unitCM | unitMM | unitPT | unitIN


	/** Parses the cm unit. */
	private def unitCM : Parser[String] = elem('c') ~ elem('m') ^^ { case _ ~ _ => "cm"}

	/** Parses the mm unit. */
	private def unitMM : Parser[String] = elem('m') ~ elem('m') ^^ { case _ ~ _ => "mm"}

	/** Parses the pt unit. */
	private def unitPT : Parser[String] = elem('p') ~ elem('t') ^^ { case _ ~ _ => "pt"}

	/** Parses the in unit. */
	private def unitIN : Parser[String] = elem('i') ~ elem('n') ^^ { case _ ~ _ => "in"}


	/**
	 * Parses identifiers
	 */
	def identifier : Parser[Identifier] = positioned(letter ~ rep(letter|digit) ~ opt('*') ^^ {
		case firstChar ~ chars ~ star =>
			star match {
				case Some(_) => Identifier(firstChar+chars.mkString + '*')
				case None => Identifier(firstChar+chars.mkString)
			}
		})



	/**
	 * Parses float numbers.
	 */
	def floatNumber : Parser[NumericLit] =
		positioned(rep(elem('-')|elem('+')) ~ (parseDotDigit1 | parseDigitOptDotDigit) ~ opt(unit) ^^ {
			case signs ~ content ~ unit => unit match {
				case Some(value) => NumericLit(signs.mkString + content + value)
				case None => NumericLit(signs.mkString + content)
			}
		})


	/**
	 * Parses a character that could be part of LaTeX texts.
	 */
	def text : Parser[Text] = positioned(elem("char", _.isValidChar) ^^ { char => Text(char.toString) })


	private def parseDigitOptDotDigit : Parser[String] =
		rep1(digit) ~ opt(parseDotDigit) ^^ { case digits ~ decimals =>
			decimals match {
				case Some(value) => digits.mkString + value
				case None => digits.mkString
			}
		}


	private def parseDotDigit : Parser[String] = elem('.') ~ rep(digit) ^^ { case _ ~ digits => "." + digits.mkString }


	private def parseDotDigit1 : Parser[String] = elem('.') ~ rep1(digit) ^^ { case _ ~ digits => "." + digits.mkString }


	/**
	 * Parses all the possible tokens.
	 */
	def token: Parser[PSTToken] = (
		positioned(identifier)
		| positioned(mathMode)
		| positioned(comment)
		| positioned(command)
		| positioned(floatNumber)
		| positioned(eof ^^ {_ => KEOF() })
		| positioned('$' ^^ {_ => throw new  ParseException("Unclosed math expression (a closing $ is missing).", -1) })
		| positioned(delim)
		| positioned(text)
		| positioned(elem("illegal character", p => true) ^^^ KError("Illegal character"))
	)


	private lazy val _delim : Parser[PSTToken] = {
		// construct parser for delimiters by |'ing together the parsers for the individual delimiters,
		// starting with the longest one -- otherwise a delimiter D will never be matched if there is
		// another delimiter that is a prefix of D
		def parseDelim(str : String) : Parser[PSTToken] = positioned(accept(str.toList) ^^ { x => Delimiter(str) })

	    val d = new Array[String](delimiters.size)
	    delimiters.copyToArray(d, 0)
	    scala.util.Sorting.quickSort(d)
	    (d.toList map parseDelim).foldRight(failure("no matching delimiter") : Parser[PSTToken])((x, y) => y | x)
	}


	protected def delim: Parser[PSTToken] = _delim
}
