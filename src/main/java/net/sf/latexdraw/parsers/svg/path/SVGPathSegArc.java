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

import java.awt.geom.Arc2D;

/**
 * Defines the SVGPath arc segment.
 * @author Arnaud BLOUIN
 */
public class SVGPathSegArc extends SVGPathPointSeg {
	/** The x radius of the arc. @since 2.0 */
	protected double rx;

	/** The y radius of the arc. @since 2.0 */
	protected double ry;

	/** The x-axis rotation angle. @since 2.0 */
	protected double angle;

	/** The value of the large-arc-flag parameter. */
	protected boolean largeArcFlag;

	/** The value of the sweep-flag parameter. */
	protected boolean sweepFlag;


	/**
	 * The main constructor.
	 * @param x The X-coordinate of the second point of the arc.
	 * @param y The Y-coordinate of the second point of the arc.
	 * @param rx The x radius of the arc.
	 * @param ry The y radius of the arc.
	 * @param angle The x-axis rotation angle.
	 * @param isRelative True: the path segment is relative, false it is absolute.
	 * @param largeArcFlag The value of the large-arc-flag parameter.
	 * @param sweepFlag The value of the sweep-flag parameter.
	 */
	public SVGPathSegArc(final double x, final double y, final double rx, final double ry, final double angle, final boolean largeArcFlag,
						final boolean sweepFlag, final boolean isRelative) {
		super(isRelative, x, y);

		this.rx = rx;
		this.ry = ry;
		this.angle = angle;
		this.largeArcFlag = largeArcFlag;
		this.sweepFlag = sweepFlag;
	}


	/**
	 * Creates a Java Arc2D corresponding to the position and the angles of the arc segment (computations based on the SVG
	 * specification instructions concerning the build of an arc, p. 643-649).
	 * @param x0 The X-coordinate of the initial position.
	 * @param y0 The Y-coordinate of the initial position.
	 * @return An Java Arc2D with double values.
	 * @since 2.0
	 */
	public Arc2D getArc2D(final double x0, final double y0) {
		double a = getAngle();
		double rx2 = getRX();
		double ry2 = getRY();
		final double x2 = getX();
		final double y2 = getY();
		final boolean laf = isLargeArcFlag();
		final boolean sf = isSweepFlag();

		final double dx2 = (x0 - x2) / 2.;
		final double dy2 = (y0 - y2) / 2.;
		a = Math.toRadians(a % 360.);

		// Step 1: Compute (x1', y1')
		final double x1 = Math.cos(a) * dx2 + Math.sin(a) * dy2;
		final double y1 = -Math.sin(a) * dx2 + Math.cos(a) * dy2;

		// Ensure radii are large enough
		rx2 = Math.abs(rx2);
		ry2 = Math.abs(ry2);
		double prx = rx2 * rx2;
		double pry = ry2 * ry2;
		final double px1 = x1 * x1;
		final double py1 = y1 * y1;
		final double radiiCheck = px1 / prx + py1 / pry;

		if(radiiCheck > 1) {
			rx2 = Math.sqrt(radiiCheck) * rx2;
			ry2 = Math.sqrt(radiiCheck) * ry2;
			prx = rx2 * rx2;
			pry = ry2 * ry2;
		}

		// Step 2: Compute (cx1, cy1)
		double sign = laf == sf ? -1 : 1;
		double sq = (prx * pry - prx * py1 - pry * px1) / (prx * py1 + pry * px1);
		sq = sq < 0 ? 0 : sq;
		final double coef = sign * Math.sqrt(sq);
		final double cx1 = coef * (rx2 * y1 / ry2);
		final double cy1 = coef * -(ry2 * x1 / rx2);

		// Step 3: Compute (cx, cy) from (cx1, cy1)
		final double sx2 = (x0 + x2) / 2.;
		final double sy2 = (y0 + y2) / 2.;
		final double cx = sx2 + (Math.cos(a) * cx1 - Math.sin(a) * cy1);
		final double cy = sy2 + (Math.sin(a) * cx1 + Math.cos(a) * cy1);

		// Step 4: Compute the angleStart (angle1) and the angleExtent (dangle)
		final double ux = (x1 - cx1) / rx2;
		final double uy = (y1 - cy1) / ry2;
		final double vx = (-x1 - cx1) / rx2;
		final double vy = (-y1 - cy1) / ry2;
		double p = ux;
		double n = Math.sqrt(ux * ux + uy * uy);

		sign = uy < 0 ? -1. : 1.;
		final double angleStart = Math.toDegrees(sign * Math.acos(p / n));

		// Compute the angle extent
		n = Math.sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy));
		p = ux * vx + uy * vy;
		sign = ux * vy - uy * vx < 0 ? -1. : 1.;

		double angleExtent = Math.toDegrees(sign * Math.acos(p / n));

		if(!sf && angleExtent > 0) {
			angleExtent -= 360.;
		}else {
			if(sf && angleExtent < 0) {
				angleExtent += 360.;
			}
		}

		return new Arc2D.Double(cx - rx2, cy - ry2, rx2 * 2., ry2 * 2., -angleStart % 360., -angleExtent % 360., Arc2D.OPEN);
	}

	/**
	 * @return the rx.
	 * @since 2.0
	 */
	public double getRX() {
		return rx;
	}

	/**
	 * @return the ry.
	 * @since 2.0
	 */
	public double getRY() {
		return ry;
	}

	/**
	 * @return the angle.
	 * @since 2.0
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @return the largeArcFlag.
	 * @since 2.0
	 */
	public boolean isLargeArcFlag() {
		return largeArcFlag;
	}

	/**
	 * @return the sweepFlag.
	 * @since 2.0
	 */
	public boolean isSweepFlag() {
		return sweepFlag;
	}

	@Override
	public String toString() {
		return String.valueOf(isRelative() ? 'a' : 'A') + ' ' + rx + ' ' + ry + ' ' + angle + ' ' + (largeArcFlag ? '1' : '0') + ' ' +
			(sweepFlag ? '1' : '0') + ' ' + x + ' ' + y;
	}
}
