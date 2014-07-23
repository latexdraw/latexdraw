package net.sf.latexdraw.parsers.svg.path;

/**
 * Defines the SVGPath smooth curveto segment.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
 * 10/20/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGPathSegCurvetoCubicSmooth extends SVGPathSeg {
	/** The X-coordinate of the second point of the curve. @since 2.0 */
	protected double x;

	/** The Y-coordinate of the second point of the curve. @since 2.0 */
	protected double y;

	/** The x-coordinate of the second control point. @since 2.0 */
	protected double x2;

	/** The y-coordinate of the second control point. @since 2.0 */
	protected double y2;


	/**
	 * The main constructor.
	 * @param x The X-coordinate of the second point of the curve.
	 * @param y The Y-coordinate of the second point of the curve.
	 * @param x2 The x-coordinate of the second control point.
	 * @param y2 The y-coordinate of the second control point.
	 * @param isRelative isRelative True: the path segment is relative, false it is absolute.
	 */
	public SVGPathSegCurvetoCubicSmooth(final double x, final double y, final double x2, final double y2, final boolean isRelative) {
		super(isRelative);

		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}


	@Override
	public String toString() {
        return String.valueOf(isRelative() ? 's' : 'S') + ' ' + x2 + ' ' + y2 + ' ' + x + ' ' + y;
	}


	/**
	 * @return the x.
	 * @since 2.0
	 */
	public double getX() {
		return x;
	}


	/**
	 * @return the y.
	 * @since 2.0
	 */
	public double getY() {
		return y;
	}


	/**
	 * @return the x2.
	 * @since 2.0
	 */
	public double getX2() {
		return x2;
	}


	/**
	 * @return the y2.
	 * @since 2.0
	 */
	public double getY2() {
		return y2;
	}
}
