package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IRectangle
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.IEllipse
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape
import net.sf.latexdraw.glib.models.interfaces.IRhombus
import net.sf.latexdraw.glib.models.interfaces.ITriangle
import net.sf.latexdraw.util.LNumber

/**
 * A parser grouping parsers parsing ellipses and rectangles.<br>
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
 * 2012-05-02<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSFrameEllipseDiamondTriangleParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser {
	/**
	 * Parses psframe commands.
	 */
	def parsePsframe(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psframe*" ~> parsePsFrameEllipseDiamondTriangle("\\psframe*", ctx)) | ("\\psframe" ~> parsePsFrameEllipseDiamondTriangle("\\psframe", ctx))


	/**
	 * Parses psellipse commands.
	 */
	def parsePsellipse(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psellipse*" ~> parsePsFrameEllipseDiamondTriangle("\\psellipse*", ctx)) | ("\\psellipse" ~> parsePsFrameEllipseDiamondTriangle("\\psellipse", ctx))


	/**
	 * Parses psdiamond commands.
	 */
	def parsePsdiamond(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psdiamond*" ~> parsePsFrameEllipseDiamondTriangle("\\psdiamond*", ctx)) | ("\\psdiamond" ~> parsePsFrameEllipseDiamondTriangle("\\psdiamond", ctx))


	/**
	 * Parses pstriangle commands.
	 */
	def parsePstriangle(ctx : PSTContext) : Parser[List[IShape]] =
	("\\pstriangle*" ~> parsePsFrameEllipseDiamondTriangle("\\pstriangle*", ctx)) | ("\\pstriangle" ~> parsePsFrameEllipseDiamondTriangle("\\pstriangle", ctx))



	private def parsePsFrameEllipseDiamondTriangle(cmd : String, ctx : PSTContext) : Parser[List[IShape]] =
		opt(parseParam(ctx)) ~ parseCoord(ctx) ~ opt(parseCoord(ctx)) ^^ {
			case _ ~ pt1 ~ dim => checkTextParsed(ctx) ::: createRectangleEllipseDiamondTriangle(cmd, cmd.endsWith("*"), pt1, dim, ctx)
	}


	/**
	 * Creates a rectangle or an ellipse depending on the given parameters.
	 */
	private def createRectangleEllipseDiamondTriangle(cmd : String, hasStar : Boolean, pt1 : PointUnit, pt2 : Option[PointUnit], ctx : PSTContext) : List[IShape] = {
			var p1 : PointUnit = null
			var p2 : PointUnit = null

			pt2 match {
				case Some(pt) =>
					p1 = pt1
					p2 = pt
				case _ =>
					p1 = ctx.origin.dup
					p2 = pt1
			}

			// Transforming the PST point into a Java point.
			val p12D = transformPointTo2DScene(p1, ctx)
			val p22D = transformPointTo2DScene(p2, ctx)

			val name = cmd.substring(1)
			name match {
				case "psframe*" | "psframe" => List(createRectangle(hasStar, p12D, p22D, ctx))
				case "psellipse*" | "psellipse" => List(createEllipse(hasStar, p12D, p22D, ctx))
				case "psdiamond*" | "psdiamond" => List(createDiamond(hasStar, p12D, p22D, ctx))
				case "pstriangle*" | "pstriangle" => List(createTriangle(hasStar, p12D, p22D, ctx))
				case name => PSTParser.errorLogs += "Unknown command: " + name ; Nil
			}
	}


	/**
	 * Creates and initialises a triangle.
	 */
	private def createTriangle(hasStar : Boolean, p1 : IPoint, p2 : IPoint, ctx : PSTContext) : ITriangle = {
		val rh = DrawingTK.getFactory.createTriangle(true)
		setRectangularShape(rh, p1.getX-p2.getX/2.0, p1.getY, scala.math.abs(p2.getX), scala.math.abs(p2.getY), hasStar, ctx)

		if(!LNumber.INSTANCE.equals(ctx.gangle, 0.0)) {
			val gc = rh.getGravityCentre
			val newGc = gc.rotatePoint(p1, scala.math.toRadians(-ctx.gangle))
			rh.setRotationAngle(rh.getRotationAngle+scala.math.toRadians(ctx.gangle))
			rh.translate(newGc.getX-gc.getX, newGc.getY-gc.getY)
		}

		// If the height is negative, the position and the rotation of the triangle changes.
		if(p2.getY>0) {
			rh.setRotationAngle(rh.getRotationAngle+scala.math.Pi)
			rh.translate(0, rh.getHeight)
		}
		rh
	}


	/**
	 * Creates and initialises a rhombus.
	 */
	private def createDiamond(hasStar : Boolean, p1 : IPoint, p2 : IPoint, ctx : PSTContext) : IRhombus = {
		val rh = DrawingTK.getFactory.createRhombus(true)
		setRectangularShape(rh, p1.getX-p2.getX, p1.getY-p2.getY, scala.math.abs(p2.getX*2), scala.math.abs(p2.getY*2), hasStar, ctx)
		rh
	}


	/**
	 * Creates and initialises an ellipse.
	 */
	private def createEllipse(hasStar : Boolean, p1 : IPoint, p2 : IPoint, ctx : PSTContext) : IEllipse = {
		val ell = DrawingTK.getFactory.createEllipse(true)
		setRectangularShape(ell, p1.getX-p2.getX, p1.getY-p2.getY, scala.math.abs(p2.getX*2), scala.math.abs(p2.getY*2), hasStar, ctx)
		ell
	}


	/**
	 * Creates and initialises a rectangle.
	 */
	private def createRectangle(hasStar : Boolean, p1 : IPoint, p2 : IPoint, ctx : PSTContext) : IRectangle = {
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
		rec.setLineArc(ctx.frameArc)
		setRectangularShape(rec, p1.getX, p1.getY, scala.math.abs(p2.getX-p1.getX), scala.math.abs(p2.getY-p1.getY), hasStar, ctx)
		rec
	}


	/**
	 * Sets the created shapes with the given parameters.
	 */
	private def setRectangularShape(sh : IRectangularShape, x : Double, y : Double, w : Double, h : Double, hasStar : Boolean, ctx : PSTContext) {
		sh.setPosition(x, y)
		sh.setWidth(scala.math.max(0.1, w))
		sh.setHeight(scala.math.max(0.1, h))
		setShapeParameters(sh, ctx)
		if(hasStar)
			setShapeForStar(sh)
	}
}
