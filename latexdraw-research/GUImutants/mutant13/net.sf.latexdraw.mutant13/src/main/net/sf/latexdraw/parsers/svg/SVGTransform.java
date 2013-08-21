package net.sf.latexdraw.parsers.svg;

import net.sf.latexdraw.util.LNumber;

/**
 * Defines an SVG transformation.<br>
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
public class SVGTransform {
	public static final int SVG_TRANSFORM_UNKNOWN	= 0;
	public static final int SVG_TRANSFORM_MATRIX	= 1;
	public static final int SVG_TRANSFORM_TRANSLATE	= 2;
	public static final int SVG_TRANSFORM_SCALE		= 3;
	public static final int SVG_TRANSFORM_ROTATE	= 4;
	public static final int SVG_TRANSFORM_SKEWX		= 5;
	public static final int SVG_TRANSFORM_SKEWY		= 6;

	/** The type of the transformation. */
	protected int type;

	/** The matrix of the transformation. */
	protected SVGMatrix matrix;

	/** The angle of a possible rotation or skew. */
	protected double angle;

	/** The possible rotation X-position. */
	protected double cx;

	/** The possible rotation Y-position. */
	protected double cy;


	/**
	 * Creates a transformation with no type.
	 */
	public SVGTransform() {
		super();
		type 	= SVG_TRANSFORM_UNKNOWN;
		matrix 	= new SVGMatrix();
		angle 	= Double.NaN;
		cx		= Double.NaN;
		cy		= Double.NaN;
	}



	/**
	 * The constructor using a string containing the transformation. The transformation is not added if it is not valid.
	 * @param transformation The SVG transformation.
	 * @throws IllegalArgumentException If the transformation is no valid.
	 * @since 0.1
	 */
	public SVGTransform(final String transformation) {
		this();
		setTransformation(transformation);
	}




	/**
	 * Parses a string containing the SVG transformation in order to set the transformation.
	 * @param transformation The string to parse.
	 * @throws IllegalArgumentException If the transformation is no valid.
	 * @since 0.1
	 */
	public void setTransformation(final String transformation) {
		if(transformation==null)
			throw new IllegalArgumentException();

		String code = transformation.replaceAll("[ \t\n\r\f]+", " ");//$NON-NLS-1$//$NON-NLS-2$
		code = code.replaceAll("^[ ]", "");//$NON-NLS-1$//$NON-NLS-2$
		code = code.replaceAll("[ ]$", "");//$NON-NLS-1$//$NON-NLS-2$
		code = code.replaceAll("[ ]?[(][ ]?", "(");//$NON-NLS-1$//$NON-NLS-2$
		code = code.replaceAll("[ ]?[)]", ")");//$NON-NLS-1$//$NON-NLS-2$
		code = code.replaceAll("[ ]?,[ ]?", ",");//$NON-NLS-1$//$NON-NLS-2$
		int i, lgth = code.length(), j, k;

		if(code.startsWith(SVGAttributes.SVG_TRANSFORM_ROTATE)) {
			i = SVGAttributes.SVG_TRANSFORM_ROTATE.length();
			j = code.indexOf(')');
			int k1 = code.indexOf(' '), k2 = code.indexOf(',');

			if(i>=lgth || code.charAt(i)!='(' || j==-1)
				throw new IllegalArgumentException();

			k = k1==-1 ? k2 : k2==-1 ? k1 : Math.min(k1, k2);

			if(k==-1)
				setRotate(Double.parseDouble(code.substring(i+1, j)), 0., 0.);
			else {
				double cx2, cy2, rotAngle;

				rotAngle = Double.parseDouble(code.substring(i+1, k));
				i = k+1;

				k1 = code.indexOf(' ', k+1);
				k2 = code.indexOf(',', k+1);
				k = k1==-1 ? k2 : k2==-1 ? k1 : Math.min(k1, k2);

				if(k==-1)
					throw new IllegalArgumentException();

				cx2 = Double.parseDouble(code.substring(i, k));
				cy2 = Double.parseDouble(code.substring(k+1, j));

				setRotate(rotAngle, cx2, cy2);
			}
		}
		else if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SCALE)) {
			k = code.indexOf(',');
			i = SVGAttributes.SVG_TRANSFORM_SCALE.length();
			j = code.indexOf(')');
			double sx, sy;

			if(i>=lgth || code.charAt(i)!='(' || j==-1)
				throw new IllegalArgumentException();

			if(k==-1)
				k = code.indexOf(' ');

			if(k==-1) {
				double val = Double.parseDouble(code.substring(i+1, j));
				sx = val;
				sy = val;
			}
			else {
				sx = Double.parseDouble(code.substring(i+1, k));
				sy = Double.parseDouble(code.substring(k+1, j));
			}

			setScale(sx, sy);
		}
		else if(code.startsWith(SVGAttributes.SVG_TRANSFORM_TRANSLATE)) {
			k = code.indexOf(',');
			i = SVGAttributes.SVG_TRANSFORM_TRANSLATE.length();
			j = code.indexOf(')');
			double tx, ty;

			if(i>=lgth || code.charAt(i)!='(' || j==-1)
				throw new IllegalArgumentException();

			if(k==-1)
				k = code.indexOf(' ');

			if(k==-1) {
				double val = Double.parseDouble(code.substring(i+1, j));
				tx = val;
				ty = val;
			}
			else {
				tx = Double.parseDouble(code.substring(i+1, k));
				ty = Double.parseDouble(code.substring(k+1, j));
			}

			setTranslate(tx, ty);
		}
		else if(code.startsWith(SVGAttributes.SVG_TRANSFORM_MATRIX)) {
			int nbPts = 6, l, k1, k2;
			i = SVGAttributes.SVG_TRANSFORM_MATRIX.length();
			j = code.indexOf(')');
			double coords[] = new double[nbPts];

			if(i>=lgth || code.charAt(i)!='(' || j==-1)
				throw new IllegalArgumentException();

			i++;
			for(l=0; l<nbPts-1; l++) {
				k1 = code.indexOf(' ', i);
				k2 = code.indexOf(',', i);

				k = k1==-1 ? k2 : k2==-1 ? k1 : Math.min(k1, k2);

				if(k==-1)
					throw new IllegalArgumentException();

				coords[l] = Double.parseDouble(code.substring(i, k));
				i = k+1;
			}

			coords[nbPts-1] = Double.parseDouble(code.substring(i, j));

			setMatrix(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
		}
		else if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SKEW_X)) {
			i = SVGAttributes.SVG_TRANSFORM_SKEW_X.length();
			j = code.indexOf(')');

			if(i>=lgth || code.charAt(i)!='(' || j==-1)
				throw new IllegalArgumentException();

			setSkewX(Double.parseDouble(code.substring(i+1, j)));
		}
		else if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SKEW_Y)) {
			i = SVGAttributes.SVG_TRANSFORM_SKEW_Y.length();
			j = code.indexOf(')');

			if(i>=lgth || code.charAt(i)!='(' || j==-1)
				throw new IllegalArgumentException();

			setSkewY(Double.parseDouble(code.substring(i+1, j)));
		}
		else
			throw new IllegalArgumentException();
	}



	/**
	 * The transformation will be a translation.
	 * @param tx The X translation.
	 * @param ty The Y translation.
	 * @since 0.1
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
	 * @since 0.1
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
	 * @since 0.1
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
	 * @since 0.1
	 */
	public void setRotate(final double angle, final double cx, final double cy) {
		SVGMatrix m1 = new SVGMatrix();
		SVGMatrix m2 = new SVGMatrix();

		type = SVG_TRANSFORM_ROTATE;
		matrix.initMatrix();

		m1.translate(cx, cy);
		m2.rotate(Math.toRadians(angle));
		m1 = m1.multiply(m2);
		m2.translate(-cx, -cy);
		m1.multiply(m2);

		matrix.setMatrix(m1.a, m1.b, m1.c, m1.d, m1.e, m1.f);
		this.angle 	= angle;
		this.cx 	= cx;
		this.cy 	= cy;
	}



	/**
	 * The transformation will be a X skew.
	 * @param angle The angle of the skew in degree.
	 * @since 0.1
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
	 * @since 0.1
	 */
	public int getType() {
		return type;
	}



	/**
	 * @return True if the transformation is a rotation.
	 * @since 0.1
	 */
	public boolean isRotation() {
		return getType()==SVG_TRANSFORM_ROTATE;
	}



	/**
	 * @return True if the transformation is a translation.
	 * @since 0.1
	 */
	public boolean isTranslation() {
		return getType()==SVG_TRANSFORM_TRANSLATE;
	}



	/**
	 * @return True if the transformation is a scale.
	 * @since 0.1
	 */
	public boolean isScale() {
		return getType()==SVG_TRANSFORM_SCALE;
	}



	/**
	 * @return True if the transformation is a X skew.
	 * @since 0.1
	 */
	public boolean isXSkew() {
		return getType()==SVG_TRANSFORM_SKEWX;
	}



	/**
	 * @return True if the transformation is a Y skew.
	 * @since 0.1
	 */
	public boolean isYSkew() {
		return getType()==SVG_TRANSFORM_SKEWY;
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
	 * @since 0.1
	 */
	public SVGMatrix getMatrix() {
		return matrix;
	}


	/**
	 * @return The rotation X-position or NaN is the transformation is not a rotation.
	 * @since 3.0
	 */
	public double getCx() {
		return cx;
	}



	/**
	 * @return The rotation Y-position or NaN is the transformation is not a rotation.
	 * @since 3.0
	 */
	public double getCy() {
		return cy;
	}


	/**
	 * Creates a translation.
	 * @param x The tx.
	 * @param y The ty.
	 * @return The created translation.
	 * @since 2.0.0
	 */
	public static SVGTransform createTranslation(final double x, final double y) {
		SVGTransform t = new SVGTransform();
		t.setTranslate(x, y);

		return t;
	}


	/**
	 * Creates a rotation.
	 * @param cx The X centre of the rotation.
	 * @param cy The Y centre of the rotation.
	 * @param angle The angle of rotation in radian.
	 * @return The created translation.
	 * @since 0.1
	 */
	public static SVGTransform createRotation(final double angle, final double cx, final double cy) {
		SVGTransform r = new SVGTransform();
		r.setRotate(angle, cx, cy);

		return r;
	}


	/**
	 * Tests if the given transformation cancels the calling one.
	 * @param transform The transformation to test.
	 * @return True if the given transformation cancels the calling one.
	 * @since 3.0
	 */
	public boolean cancels(final SVGTransform transform) {
		if(transform==null)
			return false;
		if(isTranslation())
			return cancelsTranslation(transform);
		if(isRotation())
			return cancelsRotation(transform);
		if(isScale())
			return cancelsScale(transform);
		if(isXSkew())
			return cancelsXSkew(transform);
		if(isYSkew())
			return cancelsYSkew(transform);
		return false;
	}


	/**
	 * @param transform The transformation to test.
	 * @return True: if the given transformation is a rotation that cancels the calling one.
	 * @since 3.0
	 */
	private boolean cancelsRotation(final SVGTransform transform) {
		return transform.isRotation() && LNumber.INSTANCE.equals(-(getRotationAngle()%360), transform.getRotationAngle()%360) &&
				LNumber.INSTANCE.equals(getCx(), transform.getCx()) && LNumber.INSTANCE.equals(getCy(), transform.getCy());
	}

	/**
	 * @param transform The transformation to test.
	 * @return True: if the given transformation is a translation that cancels the calling one.
	 * @since 3.0
	 */
	private boolean cancelsTranslation(final SVGTransform transform) {
		return transform.isTranslation() && LNumber.INSTANCE.equals(-getTX(), transform.getTX()) && LNumber.INSTANCE.equals(-getTY(), transform.getTY());
	}

	/**
	 * @param transform The transformation to test.
	 * @return True: if the given transformation is a scaling that cancels the calling one.
	 * @since 3.0
	 */
	private boolean cancelsScale(final SVGTransform transform) {
		return transform.isScale() && LNumber.INSTANCE.equals(getXScaleFactor()*transform.getXScaleFactor(), 1.) &&
			   LNumber.INSTANCE.equals(getYScaleFactor()*transform.getYScaleFactor(), 1.);
	}

	/**
	 * @param transform The transformation to test.
	 * @return True: if the given transformation is an X-skewing that cancels the calling one.
	 * @since 3.0
	 */
	private boolean cancelsXSkew(final SVGTransform transform) {
		throw new IllegalArgumentException("Not yet implemented");
	}

	/**
	 * @param transform The transformation to test.
	 * @return True: if the given transformation is an Y-skewing that cancels the calling one.
	 * @since 3.0
	 */
	private boolean cancelsYSkew(final SVGTransform transform) {
		throw new IllegalArgumentException("Not yet implemented");
	}



	@Override
	public String toString() {
		StringBuilder code 	= new StringBuilder();
		SVGMatrix m 		= getMatrix();

		switch(getType()) {
			case SVG_TRANSFORM_MATRIX:
				code.append(SVGAttributes.SVG_TRANSFORM_MATRIX).append('(').append(m.toString()).append(')');
				break;

			case SVG_TRANSFORM_ROTATE:
				code.append(SVGAttributes.SVG_TRANSFORM_ROTATE).append('(').append(getRotationAngle());

				if(!LNumber.INSTANCE.equals(m.getE(), 0.) || !LNumber.INSTANCE.equals(m.getF(), 0.))
					code.append(' ').append(m.getE()).append(' ').append(m.getF());

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
