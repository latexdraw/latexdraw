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
import java.text.ParseException;
import java.util.List;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import org.w3c.dom.Node;

/**
 * Defines a model for shapes that have the <code>points</code> SVG attribute.
 * @author Arnaud BLOUIN
 */
public abstract class AbstractPointsElement extends SVGElement {
	/** The points of the element. @since 2.0.3 */
	protected List<Point2D> points;

	/**
	 * Creates an SVG element that can contains points.
	 * @param node The node used to create the SVG element.
	 * @param parentNode The parentNode SVG element.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 * @throws ParseException If the points string is not well formed.
	 * @since 2.0.3
	 */
	protected AbstractPointsElement(final Node node, final SVGElement parentNode) throws MalformedSVGDocument, ParseException {
		super(node, parentNode);

		parsePoints();
	}


	/**
	 * {@link SVGElement#SVGElement(SVGDocument)}
	 */
	protected AbstractPointsElement(final SVGDocument doc) {
		super(doc);

		try {
			setPoints("0,0 1,1");
		}catch(final ParseException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * Parses the points of the element.
	 * @throws ParseException If the format of the points is not valid.
	 * @since 2.0.3
	 */
	public void parsePoints() throws ParseException {
		final SVGPointsParser parser = new SVGPointsParser(getPoints());
		parser.parse();
		points = parser.getPoints();
	}


	@Override
	public boolean checkAttributes() {
		return getPoints() != null;
	}

	/**
	 * @return The points that make up the shape.
	 * @since 0.1
	 */
	public String getPoints() {
		return getAttribute(getUsablePrefix() + SVGAttributes.SVG_POINTS);
	}

	/**
	 * Parses and sets the pts to the element.
	 * @param pts The string corresponding to the SVG pts of this element.
	 * @throws ParseException If the format of the pts is not valid.
	 * @since 2.0.3
	 */
	public void setPoints(final String pts) throws ParseException {
		if(pts != null) {
			setAttribute(SVGAttributes.SVG_POINTS, pts);
			parsePoints();
		}
	}

	/**
	 * @return The set of points in the Point2D format or null if the format of <code>points</code> is not valid.
	 */
	public List<Point2D> getPoints2D() {
		return points;
	}
}
