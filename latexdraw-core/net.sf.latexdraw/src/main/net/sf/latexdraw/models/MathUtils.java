/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models;

import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * A singleton that contains helper geom methods.
 * @author Arnaud Blouin
 */
public final class MathUtils {
	public static final MathUtils INST = new MathUtils();

	/** The threshold used to compare double values. */
	public static final double THRESHOLD = 0.001;

	private MathUtils() {
		super();
	}

	/**
	 * @param pt The point to test.
	 * @return True if the given point is valid (not NaN nor infinite nor null).
	 * @since 3.0
	 */
	public boolean isValidPt(final IPoint pt) {
		return pt != null && isValidPt(pt.getX(), pt.getY());
	}

	/**
	 * @param coord The value to test.
	 * @return True if the given value is value (not NaN nor infinite).
	 * @since 3.0
	 */
	public boolean isValidCoord(final double coord) {
		return !(java.lang.Double.isNaN(coord) || java.lang.Double.isInfinite(coord));
	}

	/**
	 * @param x The X coordinates to test.
	 * @param y The Y coordinates to test.
	 * @return True if the given values are value (not NaN nor infinite).
	 * @since 3.0
	 */
	public boolean isValidPt(final double x, final double y) {
		return isValidCoord(x) && isValidCoord(y);
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
		if(!isValidPt(a) || !isValidPt(b) || !isValidPt(c)) return 0d;

		final double ac = a.distance(c);
		final double ab = a.distance(b);

		if(MathUtils.INST.equalsDouble(ab, ac)) return a.distance((b.getX() + c.getX()) / 2d, (b.getY() + c.getY()) / 2d);

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
		if(!isValidPt(a) || !isValidPt(b) || !isValidPt(c)) return 0d;
		return gap / getAltitude(a, b, c) * a.distance(b);
	}

	/**
	 * Creates the tangent to the ellipse at the given angle.
	 * @param angle The position of the tangent point in radian
	 * @param orientation Change the orientation of the tangent
	 * @return The tangent.
	 */
	public ILine getTangenteAt(final IPoint tl, final IPoint br, final IPoint gc, final double angle, final boolean orientation) {
		final IPoint pt = ShapeFactory.INST.createPoint(br.getX(), (br.getY() + tl.getY()) / 2d).rotatePoint(gc, -angle);
		final double dec = 100d;
		final ILine tgt = ShapeFactory.INST.createLine(pt.getX(), pt.getY(), 0d, 0d);
		final float fAngle = MathUtils.INST.getCutNumber((float) angle);
		final float fPI = MathUtils.INST.getCutNumber((float) Math.PI);

		if(fAngle % fPI <= 0.01f) {
			tgt.setX2(pt.getX());
			if(orientation) {
				tgt.setY2(pt.getY() - dec);
			}else {
				tgt.setY2(pt.getY() + dec);
			}
		}else {
			if(orientation) {
				tgt.setX2(pt.getX() - dec);
			}else {
				tgt.setX2(pt.getX() + dec);
			}

			if(fAngle % (fPI / 2f) <= 0.01f) {
				tgt.setY2(pt.getY());
			}else {
				final double a = Math.abs(tl.getX() - gc.getX());
				final double b = Math.abs(tl.getY() - gc.getY());
				tgt.setY2(-(b * (pt.getX() - gc.getX()) * (tgt.getX2() - pt.getX())) / (a * (pt.getY() - gc.getY())) + pt.getY());
			}
		}
		tgt.updateAandB();
		return tgt;
	}

	/**
	 * Compares two double values to know if they are approximately equal.
	 * @param a The first double value.
	 * @param b The second double value.
	 * @param threshold The threshold used to compare the given values.
	 * @return True if both values are approximatively equal using a threshold approximation value.
	 * @since 3.0
	 */
	public boolean equalsDouble(final double a, final double b, final double threshold) {
		return Math.abs(a - b) <= threshold;
	}

	/**
	 * Compares two double values to know if they are approximatively equal.
	 * @param a The first double value.
	 * @param b The second double value.
	 * @return True if both values are approximatively equal using a threshold approximation value.
	 * @since 3.0
	 */
	public boolean equalsDouble(final double a, final double b) {
		return equalsDouble(a, b, THRESHOLD);
	}

	/**
	 * See getCutNumber(float value, double threshold). The threshold is by default the constant value THRESHOLD.
	 * @param value The value to cut.
	 * @return The cut or the intact number.
	 * @since 3.0
	 */
	public float getCutNumber(final float value) {
		return getCutNumber(value, THRESHOLD);
	}

	/**
	 * See getCutNumber(double value, double threshold). The threshold is by default the constant value THRESHOLD.
	 * @param value The value to cut.
	 * @return The cut or the intact number.
	 * @since 3.0
	 */
	public double getCutNumber(final double value) {
		return getCutNumber(value, THRESHOLD);
	}

	/**
	 * See getCutNumber(double value, double threshold). The threshold is by default the constant value THRESHOLD.
	 * @param value The value to cut.
	 * @return The cut or the intact number.
	 * @since 3.0
	 */
	public float getCutNumberFloat(final double value) {
		return (float) getCutNumber(value, THRESHOLD);
	}

	/**
	 * This method allows to set a threshold that if a number is under it, it will be considered as valuing 0;
	 * for instance, given the numbers 2E-10 and 0.002 and the
	 * threshold 0.00001; then this method will cut the first number and will return 0. The absolute value of
	 * the second number is not lesser than the threshold so it will be returned intact.
	 * @param value The number to check.
	 * @param threshold The minimum threshold of the value.
	 * @return The cut or the intact number.
	 * @since 1.9
	 */
	public float getCutNumber(final float value, final double threshold) {
		return Math.abs(value) < Math.abs(threshold) ? 0f : value;
	}

	/**
	 * This method allows to set a threshold that if a number is under it, it will be considered as valuing 0;
	 * for instance, given the numbers 2E-10 and 0.002 and the
	 * threshold 0.00001; then this method will cut the first number and will return 0. The absolute value of
	 * the second number is not lesser than the threshold so it will be returned intact.
	 * @param value The number to check.
	 * @param threshold The minimum threshold of the value.
	 * @return The cut or the intact number.
	 * @since 1.9
	 */
	public double getCutNumber(final double value, final double threshold) {
		return Math.abs(value) < Math.abs(threshold) ? 0d : value;
	}
}
