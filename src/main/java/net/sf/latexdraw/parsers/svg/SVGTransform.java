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

import net.sf.latexdraw.models.MathUtils;

/**
 * Defines an SVG transformation.
 * @author Arnaud BLOUIN
 */
public class SVGTransform {
	public static final int SVG_TRANSFORM_UNKNOWN = 0;
	public static final int SVG_TRANSFORM_MATRIX = 1;
	public static final int SVG_TRANSFORM_TRANSLATE = 2;
	public static final int SVG_TRANSFORM_SCALE = 3;
	public static final int SVG_TRANSFORM_ROTATE = 4;
	public static final int SVG_TRANSFORM_SKEWX = 5;
	public static final int SVG_TRANSFORM_SKEWY = 6;

	/**
	 * Creates a translation.
	 * @param x The tx.
	 * @param y The ty.
	 * @return The created translation.
	 */
	public static SVGTransform createTranslation(final double x, final double y) {
		final SVGTransform t = new SVGTransform();
		t.setTranslate(x, y);
		return t;
	}

	/**
	 * Creates a rotation.
	 * @param cx The X centre of the rotation.
	 * @param cy The Y centre of the rotation.
	 * @param angle The angle of rotation in radian.
	 * @return The created translation.
	 */
	public static SVGTransform createRotation(final double angle, final double cx, final double cy) {
		final SVGTransform r = new SVGTransform();
		r.setRotate(angle, cx, cy);
		return r;
	}

	/** The type of the transformation. */
	private int type;
	/** The matrix of the transformation. */
	private final SVGMatrix matrix;
	/** The angle of a possible rotation or skew. */
	private double angle;
	/** The possible rotation X-position. */
	private double cx;
	/** The possible rotation Y-position. */
	private double cy;


	/**
	 * Creates a transformation with no type.
	 */
	public SVGTransform() {
		super();
		type = SVG_TRANSFORM_UNKNOWN;
		matrix = new SVGMatrix();
		angle = Double.NaN;
		cx = Double.NaN;
		cy = Double.NaN;
	}


	/**
	 * The constructor using a string containing the transformation. The transformation is not added if it is not valid.
	 * @param transformation The SVG transformation.
	 * @throws IllegalArgumentException If the transformation is no valid.
	 */
	public SVGTransform(final String transformation) {
		this();
		setTransformation(transformation);
	}

	/**
	 * A helper function to choose between v1 and v2.
	 */
	private int getv1orv2(final int v1, final int v2) {
		if(v1 == -1) {
			return v2;
		}
		return v2 == -1 ? v1 : Math.min(v1, v2);
	}

	/**
	 * Parses the given code to produce a translate transformation.
	 * @param code The code to parse.
	 * @throws IllegalArgumentException If a problem occurs.
	 */
	private void setTranslateTransformation(final String code) {
		final int lgth = code.length();
		int k = code.indexOf(',');
		final int i = SVGAttributes.SVG_TRANSFORM_TRANSLATE.length();
		final int j = code.indexOf(')');
		final double tx;
		final double ty;

		if(i >= lgth || code.charAt(i) != '(' || j == -1) {
			throw new IllegalArgumentException();
		}

		if(k == -1) {
			k = code.indexOf(' ');
		}

		if(k == -1) {
			final double val = Double.parseDouble(code.substring(i + 1, j));
			tx = val;
			ty = val;
		}else {
			tx = Double.parseDouble(code.substring(i + 1, k));
			ty = Double.parseDouble(code.substring(k + 1, j));
		}

		setTranslate(tx, ty);
	}

	/**
	 * Parses the given code to produce a rotate transformation.
	 * @param code The code to parse.
	 * @throws IllegalArgumentException If a problem occurs.
	 */
	private void setRotateTransformation(final String code) {
		final int lgth = code.length();
		int i = SVGAttributes.SVG_TRANSFORM_ROTATE.length();
		final int j = code.indexOf(')');
		int k1 = code.indexOf(' ');
		int k2 = code.indexOf(',');
		int k = getv1orv2(k1, k2);

		if(i >= lgth || code.charAt(i) != '(' || j == -1) {
			throw new IllegalArgumentException(code);
		}

		if(k == -1) {
			setRotate(Double.parseDouble(code.substring(i + 1, j)), 0., 0.);
		}else {
			final double cx2;
			final double cy2;
			final double rotAngle;

			rotAngle = Double.parseDouble(code.substring(i + 1, k));
			i = k + 1;

			k1 = code.indexOf(' ', k + 1);
			k2 = code.indexOf(',', k + 1);
			k = getv1orv2(k1, k2);

			if(k == -1) {
				throw new IllegalArgumentException();
			}

			cx2 = Double.parseDouble(code.substring(i, k));
			cy2 = Double.parseDouble(code.substring(k + 1, j));

			setRotate(rotAngle, cx2, cy2);
		}
	}

	public void setScaleTransformation(final String code) {
		final int lgth = code.length();
		int k = code.indexOf(',');
		final int i = SVGAttributes.SVG_TRANSFORM_SCALE.length();
		final int j = code.indexOf(')');
		final double sx;
		final double sy;

		if(i >= lgth || code.charAt(i) != '(' || j == -1) {
			throw new IllegalArgumentException();
		}

		if(k == -1) {
			k = code.indexOf(' ');
		}

		if(k == -1) {
			sx = Double.parseDouble(code.substring(i + 1, j));
			sy = sx;
		}else {
			sx = Double.parseDouble(code.substring(i + 1, k));
			sy = Double.parseDouble(code.substring(k + 1, j));
		}

		setScale(sx, sy);
	}

	private void skewYTransformation(final String code) {
		final int lgth = code.length();
		final int i = SVGAttributes.SVG_TRANSFORM_SKEW_Y.length();
		final int j = code.indexOf(')');

		if(i >= lgth || code.charAt(i) != '(' || j == -1) {
			throw new IllegalArgumentException();
		}

		setSkewY(Double.parseDouble(code.substring(i + 1, j)));
	}

	private void skewXTransformation(final String code) {
		final int lgth = code.length();
		final int i = SVGAttributes.SVG_TRANSFORM_SKEW_X.length();
		final int j = code.indexOf(')');

		if(i >= lgth || code.charAt(i) != '(' || j == -1) {
			throw new IllegalArgumentException();
		}

		setSkewX(Double.parseDouble(code.substring(i + 1, j)));
	}

	private void matrixTransformation(final String code) {
		final int lgth = code.length();
		final int nbPts = 6;
		int i = SVGAttributes.SVG_TRANSFORM_MATRIX.length();
		final int j = code.indexOf(')');
		final double[] coords = new double[nbPts];

		if(i >= lgth || code.charAt(i) != '(' || j == -1) {
			throw new IllegalArgumentException();
		}

		i++;
		for(int l = 0; l < nbPts - 1; l++) {
			final int k1 = code.indexOf(' ', i);
			final int k2 = code.indexOf(',', i);
			final int k = getv1orv2(k1, k2);

			if(k == -1) {
				throw new IllegalArgumentException();
			}

			coords[l] = Double.parseDouble(code.substring(i, k));
			i = k + 1;
		}

		coords[nbPts - 1] = Double.parseDouble(code.substring(i, j));

		setMatrix(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
	}

	/**
	 * Parses a string containing the SVG transformation in order to set the transformation.
	 * @param transformation The string to parse.
	 * @throws IllegalArgumentException If the transformation is no valid.
	 */
	public void setTransformation(final String transformation) {
		if(transformation == null) {
			throw new IllegalArgumentException();
		}

		String code = transformation.replaceAll("[ \t\n\r\f]+", " "); //NON-NLS
		code = code.replaceAll("^[ ]", ""); //NON-NLS
		code = code.replaceAll("[ ]$", ""); //NON-NLS
		code = code.replaceAll("[ ]?[(][ ]?", "("); //NON-NLS
		code = code.replaceAll("[ ]?[)]", ")"); //NON-NLS
		code = code.replaceAll("[ ]?,[ ]?", ","); //NON-NLS

		if(code.startsWith(SVGAttributes.SVG_TRANSFORM_ROTATE)) {
			setRotateTransformation(code);
			return;
		}
		if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SCALE)) {
			setScaleTransformation(code);
			return;
		}
		if(code.startsWith(SVGAttributes.SVG_TRANSFORM_TRANSLATE)) {
			setTranslateTransformation(code);
			return;
		}
		if(code.startsWith(SVGAttributes.SVG_TRANSFORM_MATRIX)) {
			matrixTransformation(code);
			return;
		}
		if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SKEW_X)) {
			skewXTransformation(code);
			return;
		}
		if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SKEW_Y)) {
			skewYTransformation(code);
		}else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * The transformation will be a translation.
	 * @param tx The X translation.
	 * @param ty The Y translation.
	 */
	public void setTranslate(final double tx, final double ty) {
		type = SVG_TRANSFORM_TRANSLATE;
		matrix.initMatrix();
		matrix.translate(tx, ty);
		angle = Double.NaN;
	}

	/**
	 * The transformation will be set by the given values.
	 * @param a The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param b The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param c The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param d The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param e The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 * @param f The values of the matrix: [a, c, e, b, d, f, 0, 0, 1].
	 */
	public void setMatrix(final double a, final double b, final double c, final double d, final double e, final double f) {
		type = SVG_TRANSFORM_MATRIX;
		matrix.setMatrix(a, b, c, d, e, f);
		angle = Double.NaN;
	}

	/**
	 * The transformation will be a scaling.
	 * @param sx The X scaling.
	 * @param sy The Y scaling.
	 */
	public void setScale(final double sx, final double sy) {
		type = SVG_TRANSFORM_SCALE;
		matrix.initMatrix();
		matrix.scaleNonUniform(sx, sy);
		angle = Double.NaN;
	}

	/**
	 * The transformation will be a rotation form the origin.
	 * @param angle The rotation angle in degree.
	 * @param cx The X centre of the rotation.
	 * @param cy The Y centre of the rotation.
	 */
	public void setRotate(final double angle, final double cx, final double cy) {
		SVGMatrix m1 = new SVGMatrix();
		final SVGMatrix m2 = new SVGMatrix();

		type = SVG_TRANSFORM_ROTATE;
		matrix.initMatrix();

		m1.translate(cx, cy);
		m2.rotate(Math.toRadians(angle));
		m1 = m1.multiply(m2);
		m2.translate(-cx, -cy);
		m1.multiply(m2);

		matrix.setMatrix(m1.a, m1.b, m1.c, m1.d, m1.e, m1.f);
		this.angle = angle;
		this.cx = cx;
		this.cy = cy;
	}

	/**
	 * The transformation will be a X skew.
	 * @param angle The angle of the skew in degree.
	 */
	public void setSkewX(final double angle) {
		type = SVG_TRANSFORM_SKEWX;
		matrix.initMatrix();
		matrix.skewX(Math.toRadians(angle));
		this.angle = angle;
	}

	/**
	 * The transformation will be a Y skew.
	 * @param angle The angle of the skew in degree.
	 * @since 0.1
	 */
	public void setSkewY(final double angle) {
		type = SVG_TRANSFORM_SKEWY;
		matrix.initMatrix();
		matrix.skewY(Math.toRadians(angle));
		this.angle = angle;
	}

	/**
	 * @return the type of the transformation.
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return True if the transformation is a rotation.
	 */
	public boolean isRotation() {
		return getType() == SVG_TRANSFORM_ROTATE;
	}

	/**
	 * @return True if the transformation is a translation.
	 */
	public boolean isTranslation() {
		return getType() == SVG_TRANSFORM_TRANSLATE;
	}

	/**
	 * @return True if the transformation is a scale.
	 */
	public boolean isScale() {
		return getType() == SVG_TRANSFORM_SCALE;
	}

	/**
	 * @return True if the transformation is a X skew.
	 */
	public boolean isXSkew() {
		return getType() == SVG_TRANSFORM_SKEWX;
	}

	/**
	 * @return True if the transformation is a Y skew.
	 */
	public boolean isYSkew() {
		return getType() == SVG_TRANSFORM_SKEWY;
	}

	/**
	 * @return The rotation angle in degree if the transformation is a rotation. NaN otherwise.
	 */
	public double getRotationAngle() {
		return isRotation() ? angle : Double.NaN;
	}

	/**
	 * @return The skew angle in degree if the transformation is a X skew. NaN otherwise.
	 */
	public double getXSkewAngle() {
		return isXSkew() ? angle : Double.NaN;
	}

	/**
	 * @return The skew angle in degree if the transformation is a Y skew. NaN otherwise.
	 */
	public double getYSkewAngle() {
		return isYSkew() ? angle : Double.NaN;
	}

	/**
	 * @return The X scale factor if the transformation is a scaling. NaN otherwise.
	 */
	public double getXScaleFactor() {
		return isScale() ? matrix.getA() : Double.NaN;
	}

	/**
	 * @return The Y scale factor if the transformation is a scaling. NaN otherwise.
	 */
	public double getYScaleFactor() {
		return isScale() ? matrix.getD() : Double.NaN;
	}

	/**
	 * @return The X translation if the transformation is a translation. NaN otherwise.
	 */
	public double getTX() {
		return isTranslation() ? matrix.getE() : Double.NaN;
	}

	/**
	 * @return The Y translation if the transformation is a translation. NaN otherwise.
	 */
	public double getTY() {
		return isTranslation() ? matrix.getF() : Double.NaN;
	}

	/**
	 * @return the matrix.
	 */
	public SVGMatrix getMatrix() {
		return matrix;
	}

	/**
	 * @return The rotation X-position or NaN is the transformation is not a rotation.
	 */
	public double getCx() {
		return cx;
	}

	/**
	 * @return The rotation Y-position or NaN is the transformation is not a rotation.
	 */
	public double getCy() {
		return cy;
	}

	@Override
	public String toString() {
		final StringBuilder code = new StringBuilder();
		final SVGMatrix m = getMatrix();

		switch(getType()) {
			case SVG_TRANSFORM_MATRIX:
				code.append(SVGAttributes.SVG_TRANSFORM_MATRIX).append('(').append(m).append(')');
				break;
			case SVG_TRANSFORM_ROTATE:
				code.append(SVGAttributes.SVG_TRANSFORM_ROTATE).append('(').append(getRotationAngle());

				if(!MathUtils.INST.equalsDouble(m.getE(), 0d) || !MathUtils.INST.equalsDouble(m.getF(), 0d)) {
					code.append(' ').append(m.getE()).append(' ').append(m.getF());
				}

				code.append(')');
				break;
			case SVG_TRANSFORM_SCALE:
				code.append(SVGAttributes.SVG_TRANSFORM_SCALE).append('(').append(getXScaleFactor()).append(' ').append(getYScaleFactor()).append(')');
				break;
			case SVG_TRANSFORM_SKEWX:
				code.append(SVGAttributes.SVG_TRANSFORM_SKEW_X).append('(').append(getXSkewAngle()).append(')');
				break;
			case SVG_TRANSFORM_SKEWY:
				code.append(SVGAttributes.SVG_TRANSFORM_SKEW_Y).append('(').append(getYSkewAngle()).append(')');
				break;
			case SVG_TRANSFORM_TRANSLATE:
				code.append(SVGAttributes.SVG_TRANSFORM_TRANSLATE).append('(').append(getTX()).append(' ').append(getTY()).append(')');
				break;
		}

		return code.toString();
	}
}
