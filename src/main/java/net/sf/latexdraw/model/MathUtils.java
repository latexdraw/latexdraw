/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * A singleton that contains helper geom methods.
 * @author Arnaud Blouin
 */
public final class MathUtils {
	public static final @NotNull MathUtils INST = new MathUtils();

	/** The threshold used to compare double values. */
	public static final double THRESHOLD = 0.001;

	public final @NotNull DecimalFormat format;

	public final @NotNull String doubleRegex;

	private MathUtils() {
		super();
		format = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.ENGLISH);
		format.setMaximumFractionDigits(3);
		format.setRoundingMode(RoundingMode.HALF_EVEN);
		format.getDecimalFormatSymbols().setDecimalSeparator('.');
		format.setDecimalSeparatorAlwaysShown(false);
		format.setGroupingSize(0);
		doubleRegex = "[-]?[0-9]*\\.?[0-9]+";
	}


	/**
	 * Tries to parse the given string and convert it as a double value.
	 * @param str The string to parse.
	 * @return Empty if the string is not a double. The value otherwise.
	 */
	public @NotNull OptionalDouble parserDouble(final @NotNull String str) {
		try {
			return OptionalDouble.of(Double.parseDouble(str));
		}catch(final NumberFormatException ignored) {
			return OptionalDouble.empty();
		}
	}


	public double mod2pi(final double angle) {
		return angle % (2d * Math.PI);
	}

	/**
	 * @param pt The point to test.
	 * @return True if the given point is valid (not NaN nor infinite nor null).
	 */
	public boolean isValidPt(final Point pt) {
		return pt != null && isValidPt(pt.getX(), pt.getY());
	}

	/**
	 * @param coord The value to test.
	 * @return True if the given value is value (not NaN nor infinite).
	 */
	public boolean isValidCoord(final double coord) {
		return !(java.lang.Double.isNaN(coord) || java.lang.Double.isInfinite(coord));
	}

	/**
	 * @param x The X coordinates to test.
	 * @param y The Y coordinates to test.
	 * @return True if the given values are value (not NaN nor infinite).
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
	 */
	private double getAltitude(final Point a, final Point b, final Point c) {
		if(!isValidPt(a) || !isValidPt(b) || !isValidPt(c)) {
			return 0d;
		}

		final double ac = a.distance(c);
		final double ab = a.distance(b);

		if(MathUtils.INST.equalsDouble(ab, ac)) {
			return a.distance((b.getX() + c.getX()) / 2d, (b.getY() + c.getY()) / 2d);
		}

		final double distance = b.distance(c);
		return equalsDouble(distance, 0d) ? 0d : ab * ac / b.distance(c);
	}

	/**
	 * Given a right-rectangle ABC right in A, it computes the gap created by
	 * the corner of the triangle in B based on an initial gap.
	 * @param a The point A.
	 * @param b The point B.
	 * @param c The point C.
	 * @param gap The initial gap (for example, the thickness, the double border gap,...).
	 * @return The gap created by the corner of the point B.
	 */
	public double getCornerGap(final Point a, final Point b, final Point c, final double gap) {
		if(!isValidPt(a) || !isValidPt(b) || !isValidPt(c)) {
			return 0d;
		}
		final double altitude = getAltitude(a, b, c);
		return equalsDouble(altitude, 0d) ? 0d : gap / altitude * a.distance(b);
	}


	/**
	 * Value 104 and modulo 10 => 100
	 * Value 206 and modulo 10 => 210
	 * Gives the closest value rounded to the given modulo
	 * @param value The source value
	 * @param modulo The modulo
	 * @return The closest value rounded to the given modulo
	 */
	public double getClosestModuloValue(final double value, final double modulo) {
		final int quotient = (int) (value / modulo);
		return Math.abs(value % modulo) < Math.abs(modulo / 2d) ? quotient * modulo :
			value < 0 ? quotient * modulo - modulo : quotient * modulo + modulo;
	}


	public OptionalInt parseInt(final String intvalue) {
		try {
			return OptionalInt.of(Integer.parseInt(intvalue));
		}catch(final NumberFormatException ignored) {
			return OptionalInt.empty();
		}
	}


	/**
	 * Compares two double values to know whether they are approximately equal.
	 * @param a The first double value.
	 * @param b The second double value.
	 * @param threshold The threshold used to compare the given values.
	 * @return True if both values are approximately equal using a threshold approximation value.
	 */
	public boolean equalsDouble(final double a, final double b, final double threshold) {
		return Math.abs(a - b) <= threshold;
	}

	/**
	 * Compares two double values to know if they are approximatively equal.
	 * @param a The first double value.
	 * @param b The second double value.
	 * @return True if both values are approximatively equal using a threshold approximation value.
	 */
	public boolean equalsDouble(final double a, final double b) {
		return equalsDouble(a, b, THRESHOLD);
	}

	/**
	 * See getCutNumber(float value, double threshold). The threshold is by default the constant value THRESHOLD.
	 * @param value The value to cut.
	 * @return The cut or the intact number.
	 */
	public float getCutNumber(final float value) {
		return getCutNumber(value, THRESHOLD);
	}

	/**
	 * See getCutNumber(double value, double threshold). The threshold is by default the constant value THRESHOLD.
	 * @param value The value to cut.
	 * @return The cut or the intact number.
	 */
	public double getCutNumber(final double value) {
		return getCutNumber(value, THRESHOLD);
	}

	/**
	 * See getCutNumber(double value, double threshold). The threshold is by default the constant value THRESHOLD.
	 * @param value The value to cut.
	 * @return The cut or the intact number.
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
	 */
	public double getCutNumber(final double value, final double threshold) {
		return Math.abs(value) < Math.abs(threshold) ? 0d : value;
	}
}
