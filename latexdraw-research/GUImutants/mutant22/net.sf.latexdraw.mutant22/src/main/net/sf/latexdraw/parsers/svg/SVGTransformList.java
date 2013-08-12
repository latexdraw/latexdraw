package net.sf.latexdraw.parsers.svg;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

/**
 * Defines a list containing SVG transformations.<br>
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
public class SVGTransformList extends ArrayList<SVGTransform> {
	private static final long serialVersionUID = 1L;


	/**
	 * The constructor by default.
	 * @since 0.1
	 */
	public SVGTransformList() {
		super();
	}



	/**
	 * The constructor using a string containing the transformations.
	 * @param transformations The set of SVG transformations.
	 * @since 0.1
	 */
	public SVGTransformList(final String transformations) {
		this();

		addTransformations(transformations);
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for(final SVGTransform transform : this)
			builder.append(transform.toString()).append(' ');

		return builder.toString();
	}



	/**
	 * Add some transformations using a string containing the transformations.
	 * @param transformations The set of SVG transformations.
	 * @since 0.1
	 */
	public void addTransformations(final String transformations) {
		if(transformations==null)
			return ;

		try {
			String code = transformations.replaceAll("[ \t\n\r\f]+", " ");//$NON-NLS-1$//$NON-NLS-2$
			code = code.replaceAll("^[ ]", "");//$NON-NLS-1$//$NON-NLS-2$
			code = code.replaceAll("[ ]$", "");//$NON-NLS-1$//$NON-NLS-2$
			code = code.replaceAll("[ ]?[(][ ]?", "(");//$NON-NLS-1$//$NON-NLS-2$
			code = code.replaceAll("[ ]?[)]", ")");//$NON-NLS-1$//$NON-NLS-2$
			code = code.replaceAll("[ ]?,[ ]?", ",");//$NON-NLS-1$//$NON-NLS-2$
			code = code.replaceAll("[)][, ]?", ")_");//$NON-NLS-1$//$NON-NLS-2$

			final String[] trans = code.split("_");//$NON-NLS-1$

			for(int i=0; i<trans.length; i++)
				try{ add(new SVGTransform(trans[i])); }
				catch(final IllegalArgumentException e) { /* */ }
		}
		catch(final PatternSyntaxException e){ /* */ }
	}




	/**
	 * @return The global transformation which is the multiplication of all the transformation matrix of the list. Or
	 * null is the list has no transformation.
	 * @since 0.1
	 */
	public SVGMatrix getGlobalTransformationMatrix() {
		if(isEmpty())
			return null;

		SVGMatrix out = get(0).getMatrix();

		for(int i=1, size=size(); i<size; i++)
			out = out.multiply(get(i).getMatrix());

		return out;
	}



	/**
	 * Transforms a point according to the transformation of the list. Or null if pt is null.
	 * @param pt The point to transform.
	 * @return The transformed point.
	 */
	public Point2D transformPoint(final Point2D pt) {
		if(pt==null)
			return null;

		SVGMatrix m = getGlobalTransformationMatrix();

		if(m==null)
			return new Point2D.Double(pt.getX(), pt.getY());

		SVGMatrix ptM = new SVGMatrix();
		SVGMatrix out;

		ptM.e = pt.getX();
		ptM.f = pt.getY();
		out = m.multiply(ptM);

		if(out==null)
			return new Point2D.Double(pt.getX(), pt.getY());

		return new Point2D.Double(out.e, out.f);
	}
}
