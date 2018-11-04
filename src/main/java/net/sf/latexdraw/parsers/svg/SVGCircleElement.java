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

import java.text.ParseException;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>circle</code>.
 * @author Arnaud BLOUIN
 */
public class SVGCircleElement extends SVGElement {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGCircleElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates a circle with a radius equal to 0.
	 * @param doc The owner document.
	 */
	public SVGCircleElement(final SVGDocument doc) {
		super(doc);

		setNodeName(SVGElements.SVG_CIRCLE);
		setAttribute(SVGAttributes.SVG_R, "0");
	}


	/**
	 * Creates an SVG circle element.
	 * @param cx The X-centre coordinate.
	 * @param cy The Y-centre coordinate.
	 * @param r The radius of the circle.
	 * @param owner The document owner.
	 * @throws IllegalArgumentException If owner is null or if a given value is not valid.
	 */
	public SVGCircleElement(final double cx, final double cy, final double r, final SVGDocument owner) {
		super(owner);

		setAttribute(SVGAttributes.SVG_CX, String.valueOf(cx));
		setAttribute(SVGAttributes.SVG_CY, String.valueOf(cy));
		setAttribute(SVGAttributes.SVG_R, String.valueOf(r));
		setNodeName(SVGElements.SVG_CIRCLE);
		ownerDocument = owner;

		if(!checkAttributes()) {
			throw new IllegalArgumentException();
		}
	}


	/**
	 * @return The x-axis coordinate of the centre of the circle (0 if there it does not exist or it is not a coordinate).
	 */
	public double getCx() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_CX)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0d;
		}
	}

	/**
	 * Sets the X-coordinate of the circle.
	 * @param cx The new X-coordinate of the circle.
	 */
	public void setCx(final double cx) {
		setAttribute(getUsablePrefix() + SVGAttributes.SVG_CX, String.valueOf(cx));
	}

	/**
	 * @return The y-axis coordinate of the centre of the circle (0 if there it does not exist or it is not a coordinate).
	 */
	public double getCy() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_CY)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0d;
		}
	}

	/**
	 * Sets the Y-coordinate of the circle.
	 * @param cy The new Y-coordinate of the circle.
	 */
	public void setCy(final double cy) {
		setAttribute(getUsablePrefix() + SVGAttributes.SVG_CY, String.valueOf(cy));
	}

	/**
	 * @return The radius of the circle (NaN if there it does not exist or it is not a length).
	 */
	public double getR() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_R)).parseLength().getValue();
		}catch(final ParseException ignore) {
			return Double.NaN;
		}
	}

	/**
	 * Sets the radius of the circle.
	 * @param width The new radius of the circle.
	 */
	public void setR(final double width) {
		if(width >= 0d) {
			setAttribute(getUsablePrefix() + SVGAttributes.SVG_R, String.valueOf(width));
		}
	}


	@Override
	public boolean checkAttributes() {
		final double r = getR();
		return r >= 0d && !Double.isNaN(r);
	}


	@Override
	public boolean enableRendering() {
		return !MathUtils.INST.equalsDouble(getR(), 0d);
	}
}
