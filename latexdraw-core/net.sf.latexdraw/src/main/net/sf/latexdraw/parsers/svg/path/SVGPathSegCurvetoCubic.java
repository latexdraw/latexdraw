package net.sf.latexdraw.parsers.svg.path;

import java.awt.geom.Point2D;

/**
 * Defines the SVGPath curveto segment.<br>
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
public class SVGPathSegCurvetoCubic extends SVGPathPointSeg implements CtrlPointsSeg {
	/** The x-coordinate of the first control point. @since 2.0 */
	protected double x1;

	/** The y-coordinate of the first control point. @since 2.0 */
	protected double y1;

	/** The x-coordinate of the second control point. @since 2.0 */
	protected double x2;

	/** The y-coordinate of the second control point. @since 2.0 */
	protected double y2;


	/**
	 * The main constructor.
	 * @param x The X-coordinate of the second point of the curve.
	 * @param y The Y-coordinate of the second point of the curve.
	 * @param x1 The x-coordinate of the first control point.
	 * @param y1 The y-coordinate of the first control point
	 * @param x2 The x-coordinate of the second control point.
	 * @param y2 The y-coordinate of the second control point.
	 * @param isRelative isRelative True: the path segment is relative, false it is absolute.
	 */
	public SVGPathSegCurvetoCubic(final double x, final double y, final double x1, final double y1, final double x2, final double y2, final boolean isRelative) {
		super(isRelative, x, y);
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}


	@Override
	public String toString() {
        return String.valueOf(isRelative() ? 'c' : 'C') + ' ' + x1 + ' ' + y1 + ' ' + x2 + ' ' + y2 + ' ' + x + ' ' + y;
	}

	@Override
	public Point2D getCtrl1(final Point2D prevPoint) {
		return getPoint(x1, y1, prevPoint, isRelative);
	}

	@Override
	public Point2D getCtrl2(final Point2D prevPoint) {
		return getPoint(x2, y2, prevPoint, isRelative);
	}
}
