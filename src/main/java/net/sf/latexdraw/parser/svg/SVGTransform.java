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
package net.sf.latexdraw.parser.svg;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.util.Tuple;
import org.jetbrains.annotations.NotNull;

/**
 * Defines an SVG transformation.
 * @author Arnaud BLOUIN
 */
public abstract class SVGTransform {
	/**
	 * A factory method that parses a string containing the SVG transformation to create a transformation.
	 * @param transformation The string to parse.
	 */
	public static Optional<SVGTransform> createTransformationFromCode(final String transformation) {
		if(transformation == null) {
			return Optional.empty();
		}

		String code = transformation.replaceAll("[ \t\n\r\f]+", " "); //NON-NLS
		code = code.replaceAll("^[ ]", ""); //NON-NLS
		code = code.replaceAll("[ ]$", ""); //NON-NLS
		code = code.replaceAll("[ ]?[(][ ]?", "("); //NON-NLS
		code = code.replaceAll("[ ]?[)]", ")"); //NON-NLS
		code = code.replaceAll("[ ]?,[ ]?", ","); //NON-NLS

		try {
			if(code.startsWith(SVGAttributes.SVG_TRANSFORM_ROTATE)) {
				return Optional.of(new SVGRotateTransformation(code));
			}
			if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SCALE)) {
				return Optional.of(new SVGScaleTransformation(code));
			}
			if(code.startsWith(SVGAttributes.SVG_TRANSFORM_TRANSLATE)) {
				return Optional.of(new SVGTranslateTransformation(code));
			}
			if(code.startsWith(SVGAttributes.SVG_TRANSFORM_MATRIX)) {
				return Optional.of(new SVGMatrixTransformation(code));
			}
			if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SKEW_X)) {
				return Optional.of(new SVGSkewXTransformation(code));
			}
			if(code.startsWith(SVGAttributes.SVG_TRANSFORM_SKEW_Y)) {
				return Optional.of(new SVGSkewYTransformation(code));
			}
		}catch(final IllegalArgumentException ignored) {
		}

		return Optional.empty();
	}


	/** The matrix of the transformation. */
	public final @NotNull SVGMatrix matrix;

	/**
	 * Creates a transformation with no type.
	 */
	SVGTransform(final SVGMatrix matrix) {
		super();
		this.matrix = Objects.requireNonNull(matrix);
	}


	public static class SVGMatrixTransformation extends SVGTransform {
		static SVGMatrix matrixFromMatrixTransformation(final String code) {
			if(code == null) {
				throw new IllegalArgumentException();
			}

			final Matcher matcher = Pattern.compile("\\s*" + SVGAttributes.SVG_TRANSFORM_MATRIX + "\\s*\\(\\s*(" + MathUtils.INST.doubleRegex + //NON-NLS
				")\\s*,?\\s*(" + MathUtils.INST.doubleRegex + ")\\s*,?\\s*(" + MathUtils.INST.doubleRegex + ")\\s*,?\\s*(" + //NON-NLS
				MathUtils.INST.doubleRegex + ")\\s*,?\\s*(" + MathUtils.INST.doubleRegex + ")\\s*,?\\s*(" + //NON-NLS
				MathUtils.INST.doubleRegex + ")\\s*\\)\\s*").matcher(code); //NON-NLS

			if(!matcher.matches()) {
				throw new IllegalArgumentException(code);
			}

			return new SVGMatrix(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2)), Double.parseDouble(matcher.group(3)),
				Double.parseDouble(matcher.group(4)), Double.parseDouble(matcher.group(5)), Double.parseDouble(matcher.group(6)));
		}

		SVGMatrixTransformation(final String code) {
			super(matrixFromMatrixTransformation(code));
		}

		@Override
		public String toString() {
			return SVGAttributes.SVG_TRANSFORM_MATRIX + '(' + matrix + ')';
		}
	}

	public static class SVGTranslateTransformation extends SVGTransform {
		static SVGMatrix matrixFromTranslateTransformation(final String code) {
			if(code == null) {
				throw new IllegalArgumentException();
			}

			final Matcher matcher = Pattern.compile("\\s*" + SVGAttributes.SVG_TRANSFORM_TRANSLATE + "\\s*\\(\\s*(" + MathUtils.INST.doubleRegex + //NON-NLS
				")\\s*,?\\s*(" + MathUtils.INST.doubleRegex + ")?\\s*\\)\\s*").matcher(code); //NON-NLS

			if(!matcher.matches()) {
				throw new IllegalArgumentException(code);
			}

			final double tx = Double.parseDouble(matcher.group(1));

			if(matcher.group(2) == null) {
				return SVGMatrix.createTranslate(tx, tx);
			}

			return SVGMatrix.createTranslate(tx, Double.parseDouble(matcher.group(2)));
		}


		/**
		 * Creates a translation.
		 * @param x The tx.
		 * @param y The ty.
		 */
		public SVGTranslateTransformation(final double x, final double y) {
			super(SVGMatrix.createTranslate(x, y));
		}

		/**
		 * Parses the given code to produce a translate transformation.
		 * @param code The code to parse.
		 * @throws IllegalArgumentException If a problem occurs.
		 */
		SVGTranslateTransformation(final String code) {
			super(matrixFromTranslateTransformation(code));
		}

		/**
		 * @return The X translation.
		 */
		public double getTx() {
			return matrix.e;
		}

		/**
		 * @return The Y translation.
		 */
		public double getTy() {
			return matrix.f;
		}

		@Override
		public String toString() {
			return SVGAttributes.SVG_TRANSFORM_TRANSLATE + '(' + getTx() + ' ' + getTy() + ')';
		}
	}


	public static class SVGScaleTransformation extends SVGTransform {
		static SVGMatrix matrixFromScaleTransformation(final String code) {
			if(code == null) {
				throw new IllegalArgumentException();
			}

			final Matcher matcher = Pattern.compile("\\s*" + SVGAttributes.SVG_TRANSFORM_SCALE + "\\s*\\(\\s*(" + MathUtils.INST.doubleRegex + //NON-NLS
				")\\s*,?\\s*(" + MathUtils.INST.doubleRegex + ")?\\s*\\)\\s*").matcher(code); //NON-NLS

			if(!matcher.matches()) {
				throw new IllegalArgumentException(code);
			}

			final double sx = Double.parseDouble(matcher.group(1));

			if(matcher.group(2) == null) {
				return SVGMatrix.createScale(sx, sx);
			}

			return SVGMatrix.createScale(sx, Double.parseDouble(matcher.group(2)));
		}


		SVGScaleTransformation(final String code) {
			super(matrixFromScaleTransformation(code));
		}

		/**
		 * @return The X scale factor.
		 */
		public double getXScaleFactor() {
			return matrix.a;
		}

		/**
		 * @return The Y scale factor.
		 */
		public double getYScaleFactor() {
			return matrix.d;
		}

		@Override
		public String toString() {
			return SVGAttributes.SVG_TRANSFORM_SCALE + '(' + getXScaleFactor() + ' ' + getYScaleFactor() + ')';
		}
	}

	public static class SVGRotateTransformation extends SVGTransform {
		static Tuple<SVGMatrix, Double> matrixfromRotateTransformation(final String code) {
			if(code == null) {
				throw new IllegalArgumentException();
			}

			final Matcher matcher = Pattern.compile("\\s*" + SVGAttributes.SVG_TRANSFORM_ROTATE + "\\s*\\(\\s*(" + MathUtils.INST.doubleRegex + //NON-NLS
				")(\\s*,?\\s*(" + MathUtils.INST.doubleRegex + ")\\s*,?\\s*(" + MathUtils.INST.doubleRegex + "))?\\s*\\)\\s*").matcher(code); //NON-NLS

			if(!matcher.matches()) {
				throw new IllegalArgumentException(code);
			}

			final double angle = Double.parseDouble(matcher.group(1));

			// The angle without centre point (groups 3 and 4)
			if(matcher.group(3) == null || matcher.group(4) == null) {
				return new Tuple<>(SVGMatrix.createRotate(Math.toRadians(angle)), angle);
			}

			return new Tuple<>(SVGMatrix.createRotate(Math.toRadians(angle), Double.parseDouble(matcher.group(3)), Double.parseDouble(matcher.group(4))), angle);
		}

		/** The angle of the rotation, in degree. */
		private final double angle;

		/**
		 * Creates a rotation.
		 * @param cx The X centre of the rotation.
		 * @param cy The Y centre of the rotation.
		 * @param angle The angle of rotation in degree.
		 */
		public SVGRotateTransformation(final double angle, final double cx, final double cy) {
			super(SVGMatrix.createRotate(Math.toRadians(angle), cx, cy));
			this.angle = angle;
		}

		/**
		 * Parses the given code to produce a rotate transformation.
		 * @param code The code to parse.
		 * @throws IllegalArgumentException If a problem occurs.
		 */
		SVGRotateTransformation(final String code) {
			super(matrixfromRotateTransformation(code).a);
			angle = matrixfromRotateTransformation(code).b;
		}

		/**
		 * @return The rotation angle in degree.
		 */
		public double getRotationAngle() {
			return angle;
		}

		/**
		 * @return The rotation X-position.
		 */
		public double getCx() {
			return matrix.e;
		}

		/**
		 * @return The rotation Y-position.
		 */
		public double getCy() {
			return matrix.f;
		}

		@Override
		public String toString() {
			return SVGAttributes.SVG_TRANSFORM_ROTATE + '(' + getRotationAngle() + ' ' + matrix.e + ' ' + matrix.f + ')';
		}
	}

	public static class SVGSkewXTransformation extends SVGTransform {
		static double angleFromSkewXTransformation(final String code) {
			if(code == null) {
				throw new IllegalArgumentException();
			}

			final Matcher matcher = Pattern.compile("\\s*" + SVGAttributes.SVG_TRANSFORM_SKEW_X + "\\s*\\(\\s*(" + //NON-NLS
				MathUtils.INST.doubleRegex + ")\\s*\\)\\s*").matcher(code); //NON-NLS

			if(!matcher.matches()) {
				throw new IllegalArgumentException(code);
			}

			return Double.parseDouble(matcher.group(1));
		}

		SVGSkewXTransformation(final String code) {
			super(SVGMatrix.createSkewX(angleFromSkewXTransformation(code)));
		}

		@Override
		public String toString() {
			return SVGAttributes.SVG_TRANSFORM_SKEW_X + '(' + Math.atan(matrix.c) + ')';
		}
	}


	public static class SVGSkewYTransformation extends SVGTransform {
		static double angleFromSkewYTransformation(final String code) {
			if(code == null) {
				throw new IllegalArgumentException();
			}

			final Matcher matcher = Pattern.compile("\\s*" + SVGAttributes.SVG_TRANSFORM_SKEW_Y + "\\s*\\(\\s*(" + MathUtils.INST.doubleRegex + //NON-NLS
				")\\s*\\)\\s*").matcher(code);

			if(!matcher.matches()) {
				throw new IllegalArgumentException(code);
			}

			return Double.parseDouble(matcher.group(1));
		}

		SVGSkewYTransformation(final String code) {
			super(SVGMatrix.createSkewY(angleFromSkewYTransformation(code)));
		}

		@Override
		public String toString() {
			return SVGAttributes.SVG_TRANSFORM_SKEW_Y + '(' + Math.atan(matrix.b) + ')';
		}
	}
}
