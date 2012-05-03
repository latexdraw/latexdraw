package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IArc
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IArrow
import net.sf.latexdraw.glib.models.interfaces.ICircleArc

/**
 * A parser grouping parsers parsing arcs.<br>
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
 * 2012-05-03<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSWedgeArcParser extends PSTAbstractParser
	with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser with PSTValueParser {
	/**
	 * Parses pswedge commands.
	 */
	def parsePswedge(ctx : PSTContext) : Parser[List[IShape]] =
		("\\pswedge*" | "\\pswedge") ~ opt(parseParam(ctx)) ~ opt(parseCoord(ctx)) ~ parseBracket(ctx) ~ parseBracket(ctx) ~ parseBracket(ctx) ^^ {
		case cmdName ~ _ ~ posRaw ~ radiusStr ~ angle1Str ~ angle2Str =>
		createArc(IArc.ArcStyle.WEDGE, cmdName.endsWith("*"), posRaw, radiusStr, angle1Str, angle2Str, None, ctx) match {
			case Some(shape) => List(shape)
			case None => Nil
		}
	}



	/**
	 * Parses pswedge commands.
	 */
	def parsePsarc(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psarc*" | "\\psarc") ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ opt(parseCoord(ctx)) ~
		parseBracket(ctx) ~ parseBracket(ctx) ~ opt(parseBracket(ctx)) ^^ {
		case cmdName ~ _ ~ firstBracketRaw ~ posRaw ~ radiusRaw ~ angle1Raw ~ angle2Raw =>

		// One of the two bracket blocks must be defined.
		firstBracketRaw.isDefined || angle2Raw.isDefined match {
			case true => parsePsarc_(cmdName, posRaw, firstBracketRaw, radiusRaw, angle1Raw, angle2Raw, ctx)
			case false => PSTParser.errorLogs += "One set of brackets is missing for the psarc command."; Nil
		}
	}


	/**
	 * Function associated to parsePsarc.
	 */
	private def parsePsarc_(cmdName : String, posRaw : Option[IPoint], firstBracketRaw : Option[String], radiusRaw : String,
							angle1Raw : String, angle2Raw : Option[String], ctx : PSTContext) : List[IShape] = {
		var arc : Option[IArc] = None

		posRaw match {
			// If the position is defined, that means that the last bracket block is defined as well.
			case Some(value) => arc = createArc(IArc.ArcStyle.ARC, cmdName.endsWith("*"), posRaw, radiusRaw, angle1Raw,
												angle2Raw.get, parseValueArrows(getArrows(firstBracketRaw)), ctx)
			case None =>
				// Otherwise, the last bracket block must be check to define if there is an arrow block.
				angle2Raw match {
					case Some(value) => arc = createArc(IArc.ArcStyle.ARC, cmdName.endsWith("*"), posRaw, radiusRaw, angle1Raw,
														value, parseValueArrows(getArrows(firstBracketRaw)), ctx)
					case None => arc = createArc(IArc.ArcStyle.ARC, cmdName.endsWith("*"), posRaw, firstBracketRaw.get, radiusRaw,
												angle1Raw, parseValueArrows(getArrows(firstBracketRaw)), ctx)
				}
		}

		// Creation of the list depending on the previous result.
		arc match {
			case Some(shape) => List(shape)
			case None => Nil
		}
	}


	/** Tool function to get the string corresponding to the given arrow. */
	private def getArrows(arrStr : Option[String]) =
		arrStr match {
			case Some(value) => value
			case None => ""
		}



	/**
	 * Creates an arc using the given parameters.
	 */
	private def createArc(arcType : IArc.ArcStyle, hasStar : Boolean, posRaw : Option[IPoint], radiusStr : String, angle1Str : String,
						angle2Str : String, arrows : Option[Tuple2[IArrow.ArrowStyle, IArrow.ArrowStyle]], ctx : PSTContext) : Option[IArc] = {
		val radius = parseValueDim(radiusStr) match {
			case Some(value) => value
			case None => PSTParser.errorLogs += "pswedge's radius cannot be parsed: " + radiusStr ; Double.NaN
		}

		val angle1 = parseValueAngle(angle1Str) match {
			case Some(value) => value
			case None => PSTParser.errorLogs += "pswedge's angle1 cannot be parsed: " + angle1Str ; Double.NaN
		}

		val angle2 = parseValueAngle(angle2Str) match {
			case Some(value) => value
			case None => PSTParser.errorLogs += "pswedge's angle2 cannot be parsed: " + angle2Str ; Double.NaN
		}

		val pos = posRaw match {
			case Some(value) => value
			case None => DrawingTK.getFactory.createPoint(ctx.origin.getX, ctx.origin.getY)
		}

		if(radius.isNaN || angle1.isNaN || angle2.isNaN)
			None
		else {
			val arc = DrawingTK.getFactory.createCircleArc(true)
			arc.setAngleStart(scala.math.toRadians(angle1))
			arc.setAngleEnd(scala.math.toRadians(angle2))
			arc.setRx(scala.math.abs(radius*IShape.PPC))
			arc.setCentre(transformPointTo2DScene(pos))
			arc.setArcStyle(arcType)
			setShapeParameters(arc, ctx)
			if(hasStar)
				setShapeForStar(arc)
			Some(arc)
		}
	}
}
