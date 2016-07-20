package net.sf.latexdraw.parsers.svg.path;

import java.awt.geom.Point2D;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Defines the SVGPath lineto segment.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
	public @NonNull Point2D getPoint(Point2D prevPoint) {
		if(isRelative) {
			if(prevPoint==null)
				throw new IllegalArgumentException("The path is relative but the given previous point is null " + toString());
			return new Point2D.Double(prevPoint.getX()+x, prevPoint.getY()+y);
		}
		return new Point2D.Double(x, y);
	}


	@Override
	public String toString() {
        return String.valueOf(isRelative() ? 'l' : 'L') + ' ' + x + ' ' + y;
	}
}
