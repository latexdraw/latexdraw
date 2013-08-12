package net.sf.latexdraw.util;

/**
 * The class define some useful methods to manage numbers.
 *
 * This file is part of LaTeXDraw.<br>
 * Copyright(c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 12/18/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0<br>
 */
public final class LNumber {
	/** The singleton. */
	public static final LNumber INSTANCE = new LNumber();
	

	private LNumber() {
		super();
	}
	
	
	/** The threshold used to compare double values. */
	public static final double THRESHOLD = 0.001;


	/**
	 * Compares two double values to know if they are approximately equal.
	 * @param a The first double value.
	 * @param b The second double value.
	 * @param threshold The threshold used to compare the given values.
	 * @return True if both values are approximatively equal using a threshold approximation value.
	 * @since 3.0
	 */
	public boolean equals(final double a, final double b, final double threshold){
		return Math.abs(a - b) <= threshold;
	}



	/**
	 * Compares two double values to know if they are approximatively equal.
	 * @param a The first double value.
	 * @param b The second double value.
	 * @return True if both values are approximatively equal using a threshold approximation value.
	 * @since 3.0
	 */
	public boolean equals(final double a, final double b){
		return equals(a, b, THRESHOLD);
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
		return Math.abs(value) < threshold ? 0f : value;
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
		return Math.abs(value) < threshold ? 0. : value;
	}
}
