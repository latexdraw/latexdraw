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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Node;

/**
 * Defines a model for shapes that have the <code>points</code> SVG attribute.
 * @author Arnaud BLOUIN
 */
public abstract class AbstractPointsElement extends SVGElement {
	/** The points of the element. */
	protected final @NotNull List<Point2D> points;

	/**
	 * Creates an SVG element that can contains points.
	 * @param node The node used to create the SVG element.
	 * @param parentNode The parentNode SVG element.
	 * @throws IllegalArgumentException If errors with the arguments.
	 */
	protected AbstractPointsElement(final Node node, final SVGElement parentNode) {
		super(node, parentNode);
		points = new ArrayList<>();
		parsePoints(getPoints());
	}


	/**
	 * {@link SVGElement#SVGElement(SVGDocument)}
	 */
	protected AbstractPointsElement(final SVGDocument doc) {
		super(doc);
		points = new ArrayList<>();
		setPoints("0,0 1,1");
	}


	/**
	 * Parses the points of the element.
	 */
	protected boolean parsePoints(final String code) {
		final List<Point2D> pts = SVGParserUtils.INSTANCE.parsePoints(code);
		if(pts.isEmpty()) {
			return false;
		}
		points.clear();
		points.addAll(pts);
		return true;
	}


	@Override
	public boolean checkAttributes() {
		return !SVGParserUtils.INSTANCE.parsePoints(getPoints()).isEmpty();
	}

	/**
	 * @return The points that make up the shape.
	 */
	public String getPoints() {
		return getAttribute(getUsablePrefix() + SVGAttributes.SVG_POINTS);
	}

	/**
	 * Parses and sets the pts to the element.
	 * @param pts The string corresponding to the SVG pts of this element.
	 */
	public void setPoints(final String pts) {
		if(pts != null && parsePoints(pts)) {
			setAttribute(SVGAttributes.SVG_POINTS, pts);
		}
	}

	/**
	 * @return The set of points in the Point2D format or null if the format of <code>points</code> is not valid.
	 */
	public List<Point2D> getPoints2D() {
		return points;
	}
}
