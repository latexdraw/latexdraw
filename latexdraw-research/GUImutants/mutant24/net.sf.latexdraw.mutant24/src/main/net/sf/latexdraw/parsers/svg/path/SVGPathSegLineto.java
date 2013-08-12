package net.sf.latexdraw.parsers.svg.path;

import java.awt.geom.Point2D;

/**
 * Defines the SVGPath lineto segment.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
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
 * 10/20/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGPathSegLineto extends SVGPathSeg {
	/** The X-coordinate of the second point of the line. @since 2.0 */
	protected double x;

	/** The Y-coordinate of the second point of the line. @since 2.0 */
	protected double y;


	/**
	 * The main constructor.
	 * @param x The X-coordinate of the second point of the line.
	 * @param y The Y-coordinate of the second point of the line.
	 * @param isRelative isRelative True: the path segment is relative, false it is absolute.
	 */
	public SVGPathSegLineto(final double x, final double y, final boolean isRelative) {
		super(isRelative);

		this.x = x;
		this.y = y;
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
	 * @return The position of the segment.
	 * @since 2.0
	 */
	public Point2D getPoint() {
		return new Point2D.Double(x, y);
	}



	@Override
	public PathSeg getType() {
		return isRelative() ? PathSeg.LINETO_REL : PathSeg.LINETO_ABS;
	}


	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();

		builder.append(isRelative() ? 'l' : 'L');
		builder.append(' ');
		builder.append(x);
		builder.append(' ');
		builder.append(y);

		return builder.toString();
	}
}
