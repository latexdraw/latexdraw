package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IRectangle
import net.sf.latexdraw.glib.models.interfaces.IEllipse
import net.sf.latexdraw.glib.models.interfaces.IPositionShape
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape

/**
 * A parser that parses commands composed of 1 optional parameter block,
 * and 1 mandatory position block, and 1 optional position block.<br>
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
trait PSTCommandParam2PosParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser {
	/**
	 * Parses a PST command composed of 2 blocks of coordinates \foo[](,)(,).
	 */
	def parseCommandParam2Pos(ctx : PSTContext) : Parser[List[IShape]] =
		command ~ opt(parseParam(ctx)) ~ parseCoord(ctx) ~ opt(parseCoord(ctx)) ^^ { case cmd ~ _ ~ pt1 ~ pt2 =>
			var p1 : IPoint = null
			var p2 : IPoint = null

			pt2 match {
				case Some(pt) =>
					p1 = pt1
					p2 = pt
				case _ =>
					p1 = DrawingTK.getFactory.createPoint(ctx.origin.getX, ctx.origin.getY)
					p2 = pt1
			}

			// Transforming the PST point into a Java point.
			p1 = transformPointTo2DScene(p1)
			p2 = transformPointTo2DScene(p2)

			createShape(cmd, p1, p2, ctx)
	}


	/**
	 * Creates shapes corresponding to the given pst code.
	 */
	private def createShape(cmd : String, p1 : IPoint, p2 : IPoint, ctx : PSTContext) : List[IShape] = {
		val name = cmd.substring(1)
		name match {
			case "psframe*" | "psframe" => List(createRectangle(cmd, p1, p2, ctx))
			case "psellipse*" | "psellipse" => List(createEllipse(cmd, p1, p2, ctx))
			case name => PSTParser.errorLogs += "Unknown command: " + name ; Nil
		}
	}


	/**
	 * Creates and initialises an ellipse.
	 */
	private def createEllipse(cmdName : String, p1 : IPoint, p2 : IPoint, ctx : PSTContext) : IEllipse = {
		val ell = DrawingTK.getFactory.createEllipse(true)
		setShape(ell, p1.getX-p2.getX, p1.getY-p2.getY, scala.math.abs(p2.getX*2), scala.math.abs(p2.getY*2), cmdName, ctx)
		ell
	}


	/**
	 * Creates and initialises a rectangle.
	 */
	private def createRectangle(cmdName : String, p1 : IPoint, p2 : IPoint, ctx : PSTContext) : IRectangle = {
		// The x-coordinates of p1 must be lower than p2 one.
		if(p1.getX>p2.getX) {
			val tmp = p1.getX
			p1.setX(p2.getX)
			p2.setX(tmp)
		}

		// The y-coordinates of p1 must be lower than p2 one.
		if(p1.getY<p2.getY) {
			val tmp = p1.getY
			p1.setY(p2.getY)
			p2.setY(tmp)
		}

		val rec = DrawingTK.getFactory.createRectangle(true)
		setShape(rec, p1.getX, p1.getY, scala.math.abs(p2.getX-p1.getX), scala.math.abs(p2.getY-p1.getY), cmdName, ctx)
		rec
	}


	/**
	 * Sets the created shapes with the given parameters.
	 */
	private def setShape(sh : IRectangularShape, x : Double, y : Double, w : Double, h : Double, cmdName : String, ctx : PSTContext) {
		sh.setPosition(x, y)
		sh.setWidth(scala.math.max(0.1, w))
		sh.setHeight(scala.math.max(0.1, h))
		setShapeParameters(sh, ctx)
		setShapeForStar(sh, cmdName)
	}
}
