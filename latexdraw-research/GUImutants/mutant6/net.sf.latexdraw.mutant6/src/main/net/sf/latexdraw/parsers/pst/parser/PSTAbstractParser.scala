package net.sf.latexdraw.parsers.pst.parser

import scala.collection.mutable.HashMap
import scala.util.parsing.combinator.syntactical.TokenParsers

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IText

/**
 * Defines an abstract PST parser.<br>
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
trait PSTAbstractParser extends TokenParsers {
	type Tokens = net.sf.latexdraw.parsers.pst.lexer.PSTTokens

	val lexical = new net.sf.latexdraw.parsers.pst.lexer.PSTLexical

	import lexical._

	protected val keywordCache : HashMap[String, Parser[String]] = HashMap.empty
	protected val delimCache : HashMap[String, Parser[String]] = HashMap.empty


	/** A parser which matches an identifier. */
	def ident : Parser[String] = elem("identifier", _.isInstanceOf[Identifier]) ^^ (_.toString)


	/** A parser which matches a math expression. */
	def math : Parser[String] = elem("mathMode", _.isInstanceOf[MathMode]) ^^ (_.toString)


	/** A parser which matches an PST command name defined in the lexer. */
	def commandKnown : Parser[String] = elem("command", cmd => cmd.isInstanceOf[Command] && lexical.reserved.contains(cmd.chars)) ^^ (_.toString)


	/** A parser which matches an PST command name undefined in the lexer. */
	def commandUnknown : Parser[String] = elem("command", cmd => cmd.isInstanceOf[Command] && !lexical.reserved.contains(cmd.chars)) ^^ (_.toString)


	/** A parser which matches a float or integer value. */
	def numeric : Parser[String] = elem("numeric", _.isInstanceOf[NumericLit]) ^^ (_.toString)


	/** A parser which matches a text. */
	def text : Parser[String] = elem("text", elt => elt.isInstanceOf[Text] || lexical.textualDelimiters.contains(elt.chars)) ^^ (_.toString)


	/** A parser that parses all characters excepted the given ones. */
	def chrExcept(cs : Char*) = elem("", ch => !cs.exists{c => ch.chars.equals(c.toString)})


	// Error handling
	def orFailure[A](a : Parser[A], msg : String) : Parser[A] = a | failure(msg)


	/**
	 * Workaround for consuming tokens.
	 */
	def consume[T](p: => Parser[T])(): Parser[T] = Parser{ in =>
    	val r = p(in)
    	r
	}


	/**
	 * For debugging purpose.
	 */
	def log[T](p: => Parser[T], ctx:PSTContext)(name: String): Parser[T] = Parser{ in =>
    	println("trying "+ name +" at "+ in + " with " + ctx + " having " + ctx.textParsed)
    	val r = p(in)
    	println(name +" --> "+ r + " with " + ctx + " having " + ctx.textParsed)
    	r
	}


	/**
	 * This operation checks if some texts have been parsed. In such a case a text shape
	 * is created and returned into a list. Otherwise, Nil is returned.
	 */
	protected def checkTextParsed(ctx:PSTContext) : List[IShape] = {
		ctx.textParsed match {
			case "" => Nil
			case _ =>
				if(ctx.parsedTxtNoTxt)
					Nil
				else {
					val text = DrawingTK.getFactory.createText(true)
					if(ctx.textParsed.endsWith(" "))
						text.setText(ctx.textParsed.substring(0, ctx.textParsed.length()-1))
					else
						text.setText(ctx.textParsed)
					ctx.textParsed = ""
					setShapeParameters(text, ctx)
					text.setTextPosition(IText.TextPosition.getTextPosition(ctx.textPosition))
					text.setLineColour(ctx.textColor);
					List(text)
				}
		}
	}



	/**
	 * Sets the parameters of the given shape using the given context.
	 */
	protected def setShapeParameters(sh : IShape, ctx : PSTContext) {
		if(sh!=null && ctx!=null) {
			setShapeGeneralParameters(sh, ctx)

			if(sh.isArrowable)
				setShapeArrows(sh, ctx)
		}
	}


	protected def transformPointTo2DScene(pt : PointUnit, ctx:PSTContext) = {
		val newX = if(pt.xUnit.length==0) pt.x*IShape.PPC*ctx.xUnit*ctx.unit else pt.x*IShape.PPC
		val newY = if(pt.yUnit.length==0) -pt.y*IShape.PPC*ctx.yUnit*ctx.unit else -pt.y*IShape.PPC
		DrawingTK.getFactory.createPoint(newX, newY)
	}


	/**
	 * Configures the given shape to fit stared command (e.g. psellipse*).
	 */
	protected def setShapeForStar(sh : IShape) {
		sh.setFillingStyle(IShape.FillingStyle.PLAIN)
		sh.setFillingCol(sh.getLineColour)
		sh.setBordersPosition(IShape.BorderPos.INTO)
		sh.setLineStyle(IShape.LineStyle.SOLID)
		sh.setHasShadow(false)
		sh.setHasDbleBord(false)
	}


	/**
	 * Sets the arrows' parameters.
	 */
	protected def setShapeArrows(sh : IShape, ctx : PSTContext) {
		sh.setArrowStyle(ctx.arrowStyle._1, 0)
		sh.setArrowStyle(ctx.arrowStyle._2, 1)
		sh.setArrowSizeDim(ctx.arrowSize._1*IShape.PPC)
		sh.setArrowSizeNum(ctx.arrowSize._2)
		sh.setArrowLength(ctx.arrowLgth)
		sh.setArrowInset(ctx.arrowInset)
		sh.setTBarSizeDim(ctx.arrowTBar._1*IShape.PPC)
		sh.setTBarSizeNum(ctx.arrowTBar._2)
		sh.setBracketNum(ctx.arrowBrLgth)
		sh.setRBracketNum(ctx.arrowrBrLgth)
	}


	/**
	 * Sets the common shape's parameters.
	 */
	protected def setShapeGeneralParameters(sh : IShape, ctx : PSTContext) {
		sh.setLineColour(ctx.lineColor)
		sh.setRotationAngle(ctx.rputAngle)

		if(sh.isThicknessable)
			sh.setThickness(ctx.lineWidth*IShape.PPC)

		if(sh.isBordersMovable)
			sh.setBordersPosition(ctx.borderPos)

		if(sh.isLineStylable)
			sh.setLineStyle(ctx.lineStyle)

		if(sh.isDbleBorderable) {
			sh.setHasDbleBord(ctx.dbleLine)
			sh.setDbleBordCol(ctx.dbleColor)
			sh.setDbleBordSep(ctx.dbleSep*IShape.PPC)
		}

		if(sh.isShadowable) {
			sh.setHasShadow(ctx.shadow)
			sh.setShadowAngle(scala.math.toRadians(ctx.shadowAngle))
			sh.setShadowCol(ctx.shadowCol)
			sh.setShadowSize(ctx.shadowSize*IShape.PPC)
		}

		if(sh.isInteriorStylable) {
			sh.setFillingCol(ctx.fillColor)
			sh.setFillingStyle(ctx.fillStyle)
			sh.setGradAngle(scala.math.toRadians(ctx.gradAngle))
			sh.setGradColEnd(ctx.gradEnd)
			sh.setGradColStart(ctx.gradBegin)
			sh.setGradMidPt(ctx.gradMidPoint)
			sh.setHatchingsAngle(scala.math.toRadians(ctx.hatchAngle))
			sh.setHatchingsCol(ctx.hatchCol)
			sh.setHatchingsSep(ctx.hatchSep*IShape.PPC)
			sh.setHatchingsWidth(ctx.hatchWidth*IShape.PPC)
		}
	}


	// An implicit keyword function that gives a warning when a given word is not in the delimiters list
	implicit def keyword(chars : String) : Parser[String] = {
		if(lexical.reserved.contains(chars))
			if(chars.startsWith("\\"))
				keywordCache.getOrElseUpdate(chars, accept(Command(chars)) ^^ (_.chars))
			else
				keywordCache.getOrElseUpdate(chars, accept(Identifier(chars)) ^^ (_.chars))
		else
			if(lexical.delimiters.contains(chars))
				delimCache.getOrElseUpdate(chars, accept(Delimiter(chars)) ^^ (_.chars))
			else
				failure("You are trying to parse \"" + chars + "\", but it is neither contained in the delimiters list of your lexical object")
	}
}
