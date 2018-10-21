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

import java.util.Objects;

/**
 * Defines a matrix according to the SVG specifications.
 * @author Arnaud BLOUIN
 */
public class SVGMatrix {
	/**
	 * Creates rotation matrix.
	 * @param angle The angle of rotation (in radian).
	 */
	public static SVGMatrix createRotate(final double angle) {
		return new SVGMatrix(Math.cos(angle), Math.sin(angle), -Math.sin(angle), Math.cos(angle), 0d, 0d);
	}

	/**
	 * Creates a rotation matrix
	 * @param angle The angle of rotation (in radian).
	 * @param cx The X centre of the rotation.
	 * @param cy The Y centre of the rotation.
	 */
	public static SVGMatrix createRotate(final double angle, final double cx, final double cy) {
		return SVGMatrix.createTranslate(cx, cy).multiply(SVGMatrix.createRotate(angle));
	}

	/**
	 * Translates the matrix (without reinitialisation).
	 * @param x The x translation.
	 * @param y The y translation.
	 */
	public static SVGMatrix createTranslate(final double x, final double y) {
		return new SVGMatrix(1d, 0d, 0d, 1d, x, y);
	}

	/**
	 * Scales the matrix (without reinitialisation).
	 * @param scaleFactorX The x-scale factor.
	 * @param scaleFactorY The y-scale factor.
	 */
	public static SVGMatrix createScale(final double scaleFactorX, final double scaleFactorY) {
		return new SVGMatrix(scaleFactorX, 0d, 0d, scaleFactorY, 0d, 0d);
	}

	/**
	 * Skews the matrix (without reinitialisation).
	 * @param angle The X angle.
	 */
	public static SVGMatrix createSkewX(final double angle) {
		return new SVGMatrix(1d, 0d, Math.tan(angle), 1d, 0d, 0d);
	}

	/**
	 * Skews the matrix (without reinitialisation).
	 * @param angle The Y angle.
	 */
	public static SVGMatrix createSkewY(final double angle) {
		return new SVGMatrix(1d, Math.tan(angle), 0d, 1d, 0d, 0d);
	}


	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	public final double a;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	public final double b;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	public final double c;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	public final double d;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	public final double e;

	/** [a, c, e, b, d, f, 0, 0, 1] An element of the matrix. */
	public final double f;


	/**
	 * Creates a matrix by initialising it as the identity.
	 */
	public SVGMatrix(final double a, final double b, final double c, final double d, final double e, final double f) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	/**
	 * @param m The matrix to multiply to the called matrix: this * m = out. Or null if m is null.
	 * @return Creates a SVGMatrix  from the multiplication of the two given matrixes.
	 */
	public SVGMatrix multiply(final SVGMatrix m) {
		return m == null ? null : new SVGMatrix(a * m.a + c * m.b,
			b * m.a + d * m.b,
			a * m.c + c * m.d,
			b * m.c + d * m.d,
			a * m.e + c * m.f + e,
			b * m.e + d * m.f + f);
	}

	public SVGMatrix translate(final double tx, final double ty) {
		return new SVGMatrix(a, b, c, d, tx, ty);
	}

	@Override
	public String toString() {
		return String.valueOf(a) + ' ' + c + ' ' + e + ' ' + b + ' ' + d + ' ' + f;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof SVGMatrix)) {
			return false;
		}
		final SVGMatrix svgMatrix = (SVGMatrix) o;
		return Double.compare(svgMatrix.a, a) == 0 && Double.compare(svgMatrix.b, b) == 0 && Double.compare(svgMatrix.c, c) == 0 &&
			Double.compare(svgMatrix.d, d) == 0 && Double.compare(svgMatrix.e, e) == 0 && Double.compare(svgMatrix.f, f) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b, c, d, e, f);
	}
}
