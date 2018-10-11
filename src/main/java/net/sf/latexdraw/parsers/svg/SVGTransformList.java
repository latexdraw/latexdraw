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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

/**
 * Defines a list containing SVG transformations.
 * @author Arnaud BLOUIN
 */
public class SVGTransformList extends ArrayList<SVGTransform> {
	/**
	 * The constructor by default.
	 */
	public SVGTransformList() {
		super();
	}

	/**
	 * The constructor using a string containing the transformations.
	 * @param transformations The set of SVG transformations.
	 */
	public SVGTransformList(final String transformations) {
		this();

		addTransformations(transformations);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();

		for(final SVGTransform transform : this) {
			builder.append(transform).append(' ');
		}

		return builder.toString();
	}

	/**
	 * Add some transformations using a string containing the transformations.
	 * @param transformations The set of SVG transformations.
	 */
	public void addTransformations(final String transformations) {
		if(transformations == null) {
			return;
		}

		try {
			String code = transformations.replaceAll("[ \t\n\r\f]+", " ");
			code = code.replaceAll("^[ ]", "");
			code = code.replaceAll("[ ]$", "");
			code = code.replaceAll("[ ]?[(][ ]?", "(");
			code = code.replaceAll("[ ]?[)]", ")");
			code = code.replaceAll("[ ]?,[ ]?", ",");
			code = code.replaceAll("[)][, ]?", ")_");

			final String[] trans = code.split("_");

			for(final String tran : trans) {
				try {
					add(new SVGTransform(tran));
				}catch(final IllegalArgumentException ex) { /* */ }
			}
		}catch(final PatternSyntaxException ex) { /* */ }
	}


	/**
	 * @return The global transformation which is the multiplication of all the transformation matrix of the list. Or
	 * null is the list has no transformation.
	 */
	public SVGMatrix getGlobalTransformationMatrix() {
		if(isEmpty()) {
			return null;
		}

		SVGMatrix out = get(0).getMatrix();

		for(int i = 1, size = size(); i < size; i++) {
			out = out.multiply(get(i).getMatrix());
		}

		return out;
	}


	/**
	 * Transforms a point according to the transformation of the list. Or null if pt is null.
	 * @param pt The point to transform.
	 * @return The transformed point.
	 */
	public Point2D transformPoint(final Point2D pt) {
		if(pt == null) {
			return null;
		}

		final SVGMatrix m = getGlobalTransformationMatrix();

		if(m == null) {
			return new Point2D.Double(pt.getX(), pt.getY());
		}

		final SVGMatrix ptM = new SVGMatrix();
		final SVGMatrix out;

		ptM.e = pt.getX();
		ptM.f = pt.getY();
		out = m.multiply(ptM);

		if(out == null) {
			return new Point2D.Double(pt.getX(), pt.getY());
		}

		return new Point2D.Double(out.e, out.f);
	}
}
