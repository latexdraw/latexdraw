package net.sf.latexdraw.models

import net.sf.latexdraw.util.LNumber
import net.sf.latexdraw.models.interfaces.shape.IPoint
import net.sf.latexdraw.models.interfaces.shape.ILine

/**
 * Defines some utilities function for the glib library.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
object GLibUtilities {
	/**
	 * @param pt The point to test.
	 * @return True if the given point is valid (not NaN nor infinite nor null).
	 * @since 3.0
	 */
	def isValidPoint(pt:IPoint) : Boolean = pt!=null && isValidPoint(pt.getX, pt.getY)

	/**
	 * @param coord The value to test.
	 * @return True if the given value is value (not NaN nor infinite).
	 * @since 3.0
	 */
	def isValidCoordinate(coord:Double) = !(java.lang.Double.isNaN(coord) || java.lang.Double.isInfinite(coord))

	/**
	 * @param x The X coordinates to test.
	 * @param y The Y coordinates to test.
	 * @return True if the given values are value (not NaN nor infinite).
	 * @since 3.0
	 */
	def isValidPoint(x:Double, y:Double) : Boolean = isValidCoordinate(x) && isValidCoordinate(y)

	/**
	 * Computes the altitude ha of the <b>right-triangle<b> ABC, right in A.
	 * @param a The point A.
	 * @param b The point B.
	 * @param c The point C.
	 * @return The altitude ha or 0.
	 * @since 2.0.0
	 */
	def getAltitude(a:IPoint, b:IPoint, c:IPoint) : Double = {
		if(!isValidPoint(a) || !isValidPoint(b) || !isValidPoint(c))
			return 0.0

		val ac = a.distance(c)
		val ab = a.distance(b)

		if(LNumber.equalsDouble(ab, ac))
			return a.distance((b.getX+c.getX)/2.0, (b.getY+c.getY)/2.0)

		return ab * ac / b.distance(c)
	}

	/**
	 * Given a right-rectangle ABC right in A, it computes the gap created by
	 * the corner of the triangle in B based on an initial gap.
	 * @param a The point A.
	 * @param b The point B.
	 * @param c The point C.
	 * @param gap The initial gap (for example, the thickness, the double border gap,...).
	 * @return The gap created by the corner of the point B.
	 * @since 2.0.0
	 */
	def getCornerGap(a:IPoint, b:IPoint, c:IPoint, gap:Double) : Double = {
		if(!isValidPoint(a) || !isValidPoint(b) || !isValidPoint(c))
			return 0.0
		return gap / getAltitude(a, b, c) * a.distance(b)
	}

	/**
	 * Creates the tangent to the ellipse at the given angle.
	 * @param angle The position of the tangent point in radian
	 * @param orientation Change the orientation of the tangent
	 * @return The tangent.
	 */
	def getTangenteAt(tl:IPoint, br:IPoint, gc:IPoint, angle:Double, orientation:Boolean) : ILine = {
//		final double th = (isDbleBorderable() && hasDbleBord() ? thickness*2.+ getDbleBordSep() : thickness)/2.;
//		tl.setPoint(tl.getX()+th, tl.getY()+th);
//		br.setPoint(br.getX()-th, br.getY()-th);
		val pt = ShapeFactory.createPoint(br.getX, (br.getY+tl.getY)/2.0).rotatePoint(gc, -angle)
		val a = Math.abs(tl.getX-gc.getX)
		val b = Math.abs(tl.getY-gc.getY)
		val dec = 100.0
		val tgt = ShapeFactory.createLine(pt.getX, pt.getY, 0.0, 0.0)

		if((angle%Math.PI.toFloat).toFloat<=0.01f) {
			tgt.setX2(pt.getX)
			if(orientation)
				 tgt.setY2(pt.getY - dec)
			else tgt.setY2(pt.getY + dec)
		}
		else {
			if(orientation)
				 tgt.setX2(pt.getX-dec)
			else tgt.setX2(pt.getX+dec)

			if((angle%(Math.PI/2f)).toFloat<=0.01f)
				 tgt.setY2(pt.getY)
			else tgt.setY2(-(b*(pt.getX-gc.getX)*(tgt.getX2-pt.getX))/(a*(pt.getY-gc.getY)) + pt.getY)
		}
		tgt.updateAandB
		return tgt
	}
}
