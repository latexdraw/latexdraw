package net.sf.latexdraw.generators.svg;

import java.awt.geom.Point2D;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.parsers.svg.AbstractPointsElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;

/**
 * Defines an SVG generator for shapes composed of points.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 11/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
abstract class LModifiablePointsGenerator<S extends IModifiablePointsShape> extends LShapeSVGGenerator<S> {
	/**
	 * Creates a generator for IModifiablePointsShape.
	 * @param modShape The source shape used to generate the SVG element.
	 */
	protected LModifiablePointsGenerator(final S modShape) {
		super(modShape);
	}



	/**
	 * Sets the latexdraw polygon using the given SVG element that contains points.
	 * @param ape The source SVG element used to define the latexdraw shape.
	 * @since 3.0
	 */
	protected void setSVGModifiablePointsParameters(final AbstractPointsElement ape) {
		setSVGParameters(ape);
		final List<Point2D> ptsPol 		= ape.getPoints2D();
		final IShapeFactory factory 	= DrawingTK.getFactory();

		if(ptsPol==null)
			throw new IllegalArgumentException();

		for(final Point2D pt : ptsPol)
			shape.addPoint(factory.createPoint(pt.getX(), pt.getY()));
	}




	/**
	 * Sets the points of the modifiable points shape using the given SVG element.
	 * @param elt The SVG element that contains the points to set.
	 * @since 3.0
	 */
	protected void initModifiablePointsShape(final SVGPathElement elt) {
		final SVGPathSegList segs = elt.getSegList();
		SVGPathSeg seg;
		final int size = segs.size()-1;
		int i;
		Point2D pt;
		final IShapeFactory factory	= DrawingTK.getFactory();

		for(i=0; i<size; i++) {
			seg = segs.get(i);

			if(!(seg instanceof SVGPathSegLineto))
				throw new IllegalArgumentException("The given SVG path element is not a polygon."); //$NON-NLS-1$

			pt = ((SVGPathSegLineto)seg).getPoint();
			shape.addPoint(factory.createPoint(pt.getX(), pt.getY()));
		}

		setSVGParameters(elt);
		applyTransformations(elt);
	}
}
