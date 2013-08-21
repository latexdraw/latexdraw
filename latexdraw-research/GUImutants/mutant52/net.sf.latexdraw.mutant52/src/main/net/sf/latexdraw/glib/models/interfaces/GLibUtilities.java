package net.sf.latexdraw.glib.models.interfaces;

import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.util.LNumber;

/**
 * Defines some utilities function for the glib library.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
public final class GLibUtilities {
	/** The singleton. */
	public static final GLibUtilities INSTANCE = new GLibUtilities();

	private GLibUtilities() {
		super();
	}

	/**
	 * @param pt The point to test.
	 * @return True if the given point is valid (not NaN nor infinite nor null).
	 * @since 3.0
	 */
	public boolean isValidPoint(final IPoint pt) {
		return pt!=null && isValidPoint(pt.getX(), pt.getY());
	}

	/**
	 * @param coord The value to test.
	 * @return True if the given value is value (not NaN nor infinite).
	 * @since 3.0
	 */
	public boolean isValidCoordinate(final double coord) {
		return !(Double.isNaN(coord) || Double.isInfinite(coord));
	}

	/**
	 * @param x The X coordinates to test.
	 * @param y The Y coordinates to test.
	 * @return True if the given values are value (not NaN nor infinite).
	 * @since 3.0
	 */
	public boolean isValidPoint(final double x, final double y) {
		return isValidCoordinate(x) && isValidCoordinate(y);
	}

	/**
	 * Computes the altitude ha of the <b>right-triangle<b> ABC, right in A.
	 * @param a The point A.
	 * @param b The point B.
	 * @param c The point C.
	 * @return The altitude ha or 0.
	 * @since 2.0.0
	 */
	public double getAltitude(final IPoint a, final IPoint b, final IPoint c) {
		if(!isValidPoint(a) || !isValidPoint(b) || !isValidPoint(c))
			return 0.;

		final double ac = a.distance(c);
		final double ab = a.distance(b);

		if(LNumber.INSTANCE.equals(ab, ac))
			return a.distance((b.getX()+c.getX())/2., (b.getY()+c.getY())/2.);

		return ab * ac / b.distance(c);
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
	public double getCornerGap(final IPoint a, final IPoint b, final IPoint c, final double gap) {
		if(!isValidPoint(a) ||!isValidPoint(b) || !isValidPoint(c))
			return 0.;

		return gap / getAltitude(a, b, c) * a.distance(b);
	}

	/**
	 * Defines the min and the max coordinates of the borders of the Bezier curve.
	 * @param points The initial control points.
	 * @param level The level of resolution.
	 * @return The maximum and the minimum coordinates of the Bezier curve;
	 * <code>[minX, minY, maxX, maxY]</code>
	 */
	public double[] getBezierCurveMinMax(final List<IPoint> points, final int level) {
		if(points==null)
			return null;

		if(level<=0) {
			final double x1 = points.get(0).getX() + 0.5;
			final double y1 = points.get(0).getY() + 0.5;
			final double x2 = points.get(3).getX() + 0.5;
			final double y2 = points.get(3).getY() + 0.5;

			return new double[]{Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2)};
		}

		List<IPoint> left  = new ArrayList<>();
		List<IPoint> right = new ArrayList<>();

		IShapeFactory factory = DrawingTK.getFactory();
		IPoint l1 = factory.createPoint(), l2 = factory.createPoint();
		IPoint l3 = factory.createPoint(), l4 = factory.createPoint();
		IPoint r1 = factory.createPoint(), r2 = factory.createPoint();
		IPoint r3 = factory.createPoint(), r4 = factory.createPoint();
		final IPoint p0 = points.get(0);
		final IPoint p1 = points.get(1);
		final IPoint p2 = points.get(2);
		final IPoint p3 = points.get(3);
		final double p0x = p0.getX();
		final double p0y = p0.getY();
		final double p1x = p1.getX();
		final double p1y = p1.getY();
		final double p2x = p2.getX();
		final double p2y = p2.getY();
		final double p3x = p3.getX();
		final double p3y = p3.getY();

		l1.setX(p0x);
		l1.setY(p0y);
		l2.setX((p0x + p1x) / 2.);
		l2.setY((p0y + p1y) / 2.);
		l3.setX((p0x + 2.*p1x + p2x) / 4.);
		l3.setY((p0y + 2.*p1y + p2y) / 4.);
		l4.setX((p0x + 3.*p1x + 3.*p2x + p3x) / 8.);
		l4.setY((p0y + 3.*p1y + 3.*p2y + p3y) / 8.);
		r1.setX(p3x);
		r1.setY(p3y);
		r2.setX((p1x + 2.*p2y + p3y) / 4.);
		r2.setY((p1y + 2.*p2y + p3y) / 4.);
		r3.setX((p2x + p3x) / 2.);
		r3.setY((p2y + p3y) / 2.);
		r4.setX(p3x);
		r4.setY(p3y);

		left.add(l1);
		left.add(l2);
		left.add(l3);
		left.add(l4);
		right.add(r1);
		right.add(r2);
		right.add(r3);
		right.add(r4);

        final double mmLeft[] 	= getBezierCurveMinMax(left, level-1);
        final double mmRight[] 	= getBezierCurveMinMax(right, level-1);

		return new double[] {Math.min(mmLeft[0], mmRight[0]), Math.min(mmLeft[1], mmRight[1]),
							 Math.max(mmLeft[2], mmRight[2]), Math.max(mmLeft[3], mmRight[3])};
	}
}
