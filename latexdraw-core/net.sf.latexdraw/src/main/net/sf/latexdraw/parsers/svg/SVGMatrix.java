package net.sf.latexdraw.parsers.svg;

/**
 * Defines a matrix according to the SVG specifications.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE.  See the GNU General Public License for more details.<br>
 *<br>
 * 10/16/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 0.1<br>
 */
public class SVGMatrix {
	public static final int WIDTH	= 3;

	public static final int HEIGHT	= 3;

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
	 * @since 0.1
	 */
	public SVGMatrix() {
		super();
		initMatrix();
	}


	/**
	 * Initialises the matrix as the identity.
	 * @since 0.1
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
	 * @since 0.1
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
	 * @since 0.1
	 */
	public void translate(final double x, final double y) {
		e = x;
		f = y;
	}



	/**
	 * Scales the matrix (without reinitialisation).
	 * @param scaleFactor The scale factor.
	 * @since 0.1
	 */
	public void scale(final double scaleFactor) {
		scaleNonUniform(scaleFactor, scaleFactor);
	}



	/**
	 * Scales the matrix (without reinitialisation).
	 * @param scaleFactorX The x scale factor.
	 * @param scaleFactorY The y scale factor.
	 * @since 0.1
	 */
	public void scaleNonUniform(final double scaleFactorX, final double scaleFactorY) {
		a = scaleFactorX;
		d = scaleFactorY;
	}



	/**
	 * Skews the matrix (without reinitialisation).
	 * @param angle The X angle.
	 * @since 0.1
	 */
	public void skewX(final double angle) {
		c = Math.tan(angle);
	}



	/**
	 * Skews the matrix (without reinitialisation).
	 * @param angle The Y angle.
	 * @since 0.1
	 */
	public void skewY(final double angle) {
		b = Math.tan(angle);
	}




	/**
	 * @param m The matrix to multiply to the called matrix: this * m = out. Or null if m is null.
	 * @return Creates a SVGMatrix  from the multiplication of the two given matrixes.
	 */
	public SVGMatrix multiply(final SVGMatrix m) {
		if(m==null)
			return null;

		SVGMatrix out = new SVGMatrix();

		out.a = a*m.a + c*m.b;
		out.b = b*m.a + d*m.b;
		out.c = a*m.c + c*m.d;
		out.d = b*m.c + d*m.d;
		out.e = a*m.e + c*m.f + e;
		out.f = b*m.e + d*m.f + f;

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
	 * @since 0.1
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
	 * @since 0.1
	 */
	public double getA() {
		return a;
	}


	/**
	 * @return The (1,0) element of the matrix.
	 * @since 0.1
	 */
	public double getB() {
		return b;
	}


	/**
	 * @return The (2,0) element of the matrix.
	 * @since 0.1
	 */
	public double getC() {
		return c;
	}


	/**
	 * @return The (0,1) element of the matrix.
	 * @since 0.1
	 */
	public double getD() {
		return d;
	}


	/**
	 * @return The (1,1) element of the matrix.
	 * @since 0.1
	 */
	public double getE() {
		return e;
	}


	/**
	 * @return The (2,1) element of the matrix.
	 * @since 0.1
	 */
	public double getF() {
		return f;
	}



	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(a);
		stringBuilder.append(' ');
		stringBuilder.append(c);
		stringBuilder.append(' ');
		stringBuilder.append(e);
		stringBuilder.append(' ');
		stringBuilder.append(b);
		stringBuilder.append(' ');
		stringBuilder.append(d);
		stringBuilder.append(' ');
		stringBuilder.append(f);

		return stringBuilder.toString();
	}
}
