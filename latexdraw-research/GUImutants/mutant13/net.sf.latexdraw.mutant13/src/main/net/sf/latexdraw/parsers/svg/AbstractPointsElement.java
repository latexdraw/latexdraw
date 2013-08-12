package net.sf.latexdraw.parsers.svg;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;

import org.w3c.dom.Node;

/**
 * Defines a model for shapes that have the <code>points</code> SVG attribute.<br>
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
 * 09/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 0.1
 */
public abstract class AbstractPointsElement extends SVGElement {
	/** The points of the element. @since 2.0.3 */
	protected List<Point2D> points;

	/**
	 * Creates an SVG element that can contains points.
	 * @param node The node used to create the SVG element.
	 * @param parent The parent SVG element.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 * @throws ParseException If the points string is not well formed.
	 * @since 2.0.3
	 */
	public AbstractPointsElement(final Node node, final SVGElement parent) throws MalformedSVGDocument, ParseException {
		super(node, parent);

		parsePoints();
	}


	/**
	 * {@link SVGElement#SVGElement(SVGDocument)}
	 */
	public AbstractPointsElement(final SVGDocument doc) {
		super(doc);

		try { setPoints("0,0 1,1"); }//$NON-NLS-1$
		catch(final ParseException e) { BadaboomCollector.INSTANCE.add(e); }
	}


	/**
	 * Parses the points of the element.
	 * @throws ParseException If the format of the points is not valid.
	 * @since 2.0.3
	 */
	public void parsePoints() throws ParseException {
		SVGPointsParser parser = new SVGPointsParser(getPoints());
		parser.parse();
		points = parser.getPoints();
	}



	@Override
	public boolean checkAttributes() {
		return getPoints()!=null;
	}


	/**
	 * Parses and sets the points to the element.
	 * @param points The string corresponding to the SVG points of this element.
	 * @throws ParseException If the format of the points is not valid.
	 * @since 2.0.3
	 */
	public void setPoints(final String points) throws ParseException {
		if(points!=null) {
			setAttribute(SVGAttributes.SVG_POINTS, points);
			parsePoints();
		}
	}



	/**
	 * @return The points that make up the shape.
	 * @since 0.1
	 */
	public String getPoints() {
		return getAttribute(getUsablePrefix()+SVGAttributes.SVG_POINTS);
	}



	/**
	 * @return The set of points in the Point2D format or null if the format of <code>points</code> is not valid.
	 * @since 0.1
	 */
	public List<Point2D> getPoints2D() {
		return points;
	}
}
