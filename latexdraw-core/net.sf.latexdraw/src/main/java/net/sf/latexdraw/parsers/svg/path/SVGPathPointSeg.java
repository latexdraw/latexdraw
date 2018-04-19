/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.parsers.svg.path;

import java.awt.geom.Point2D;

/**
 * Defines a path segment composed of 2D coordinates.
 * @author Arnaud BLOUIN
 */
public abstract class SVGPathPointSeg extends SVGPathSeg implements PointSeg {
	/** The X-coordinate of the segment. */
	protected double x;

	/** The Y-coordinate of the segment. */
	protected double y;

	/**
	 * The main constructor.
	 * @param isrel True: the path segment is relative, false it is absolute.
	 */
	protected SVGPathPointSeg(final boolean isrel, final double xcoord, final double ycoord) {
		super(isrel);
		x = xcoord;
		y = ycoord;
	}

	/**
	 * @return the x.
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y.
	 */
	public double getY() {
		return y;
	}

	@Override
	public Point2D getPoint(final Point2D prevPoint) {
		return getPoint(x, y, prevPoint, isRelative);
	}

	/**
	 * Creates a point from the SVG information.
	 * @param x The X-coordinate of the point.
	 * @param y The Y-coordinate of the point.
	 * @param prevPt The previous point of the path. May be null is the point is not relative.
	 * @param isRelative Defines whether the point is relative.
	 * @return The point converted or not according to isRelative.
	 */
	public static Point2D getPoint(final double x, final double y, final Point2D prevPt, final boolean isRelative) {
		if(isRelative) {
			if(prevPt==null) {
				throw new IllegalArgumentException("The path is relative but the given previous point is null.");
			}
			return new Point2D.Double(prevPt.getX()+x, prevPt.getY()+y);
		}
		return new Point2D.Double(x, y);
	}
}
