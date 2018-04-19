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
package net.sf.latexdraw.parsers.svg;

/**
 * Defines a matrix according to the SVG specifications.
 * @author Arnaud BLOUIN
 */
public class SVGMatrix {
	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	protected double a;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	protected double b;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	protected double c;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	protected double d;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	protected double e;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	protected double f;


	/**
	 * Creates a matrix by initialising it as the identity.
	 */
	public SVGMatrix() {
		super();
		initMatrix();
	}


	/**
	 * Initialises the matrix as the identity.
	 */
	public void initMatrix() {
		a = 1.;
		d = 1.;
		b = 0.;
		c = 0.;
		e = 0.;
		f = 0.;
	}


	/**
	 * Rotates the matrix (without reinitialisation).
	 * @param angle The angle of rotation (in radian).
	 */
	public void rotate(final double angle) {
		a = Math.cos(angle);
		b = Math.sin(angle);
		c = -Math.sin(angle);
		d = Math.cos(angle);
	}


	/**
	 * Translates the matrix (without reinitialisation).
	 * @param x The x translation.
	 * @param y The y translation.
	 */
	public void translate(final double x, final double y) {
		e = x;
		f = y;
	}


	/**
	 * Scales the matrix (without reinitialisation).
	 * @param scaleFactor The scale factor.
	 */
	public void scale(final double scaleFactor) {
		scaleNonUniform(scaleFactor, scaleFactor);
	}


	/**
	 * Scales the matrix (without reinitialisation).
	 * @param scaleFactorX The x scale factor.
	 * @param scaleFactorY The y scale factor.
	 */
	public void scaleNonUniform(final double scaleFactorX, final double scaleFactorY) {
		a = scaleFactorX;
		d = scaleFactorY;
	}


	/**
	 * Skews the matrix (without reinitialisation).
	 * @param angle The X angle.
	 */
	public void skewX(final double angle) {
		c = Math.tan(angle);
	}


	/**
	 * Skews the matrix (without reinitialisation).
	 * @param angle The Y angle.
	 */
	public void skewY(final double angle) {
		b = Math.tan(angle);
	}


	/**
	 * @param m The matrix to multiply to the called matrix: this * m = out. Or null if m is null.
	 * @return Creates a SVGMatrix  from the multiplication of the two given matrixes.
	 */
	public SVGMatrix multiply(final SVGMatrix m) {
		if(m == null) {
			return null;
		}

		final SVGMatrix out = new SVGMatrix();

		out.a = a * m.a + c * m.b;
		out.b = b * m.a + d * m.b;
		out.c = a * m.c + c * m.d;
		out.d = b * m.c + d * m.d;
		out.e = a * m.e + c * m.f + e;
		out.f = b * m.e + d * m.f + f;

		return out;
	}


	/**
	 * Sets the matrix with the given values.
	 * @param a The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param b The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param c The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param d The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param e The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param f The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 */
	public void setMatrix(final double a, final double b, final double c, final double d, final double e, final double f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}


	/**
	 * @return The (0,0) element of the matrix.
	 */
	public double getA() {
		return a;
	}


	/**
	 * @return The (1,0) element of the matrix.
	 */
	public double getB() {
		return b;
	}


	/**
	 * @return The (2,0) element of the matrix.
	 */
	public double getC() {
		return c;
	}


	/**
	 * @return The (0,1) element of the matrix.
	 */
	public double getD() {
		return d;
	}


	/**
	 * @return The (1,1) element of the matrix.
	 */
	public double getE() {
		return e;
	}


	/**
	 * @return The (2,1) element of the matrix.
	 */
	public double getF() {
		return f;
	}


	@Override
	public String toString() {
		return String.valueOf(a) + ' ' + c + ' ' + e + ' ' + b + ' ' + d + ' ' + f;
	}
}
