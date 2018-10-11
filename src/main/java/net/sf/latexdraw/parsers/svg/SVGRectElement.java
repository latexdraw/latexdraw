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
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>rect</code>.
 * @author Arnaud BLOUIN
 */
public class SVGRectElement extends SVGElement implements SVGRectParseTrait {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGRectElement(final Node node, final SVGElement elt) throws MalformedSVGDocument {
		super(node, elt);
	}


	/**
	 * Creates an SVG rect element.
	 * @param x The top-left X coordinate.
	 * @param y The top-left Y coordinate.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 * @param owner The document owner.
	 * @throws IllegalArgumentException If owner is null or if a given value is not valid.
	 */
	public SVGRectElement(final double x, final double y, final double w, final double h, final SVGDocument owner) {
		super(owner);

		setAttribute(SVGAttributes.SVG_X, String.valueOf(x));
		setAttribute(SVGAttributes.SVG_Y, String.valueOf(y));
		setAttribute(SVGAttributes.SVG_WIDTH, String.valueOf(w));
		setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf(h));
		setNodeName(SVGElements.SVG_RECT);
		ownerDocument = owner;

		if(!checkAttributes()) {
			throw new IllegalArgumentException();
		}
	}


	/**
	 * Creates an SVG rectangle with width=height=0.
	 * @param doc The owner document.
	 */
	public SVGRectElement(final SVGDocument doc) {
		super(doc);

		setNodeName(SVGElements.SVG_RECT);
		setAttribute(SVGAttributes.SVG_WIDTH, "0"); //NON-NLS
		setAttribute(SVGAttributes.SVG_HEIGHT, "0"); //NON-NLS
	}


	@Override
	public boolean checkAttributes() {
		final double vWidth = getWidth();
		final double vHeight = getHeight();
		final double vrx = getRx();
		final double vry = getRy();

		return !(Double.isNaN(vWidth) || Double.isNaN(vHeight) || vWidth < 0d || vHeight < 0d || vrx < 0d || vry < 0d);

	}


	@Override
	public boolean enableRendering() {
		return !(Double.compare(getWidth(), 0d) == 0 || Double.compare(getHeight(), 0d) == 0);

	}


	/**
	 * @return The value of the rx attribute (0 if there it does not exist or it is not a length).
	 * For rounded rectangles, the x-axis radius of the ellipse used to round off the corners of the rectangle.
	 */
	public double getRx() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_RX);
		double rx;

		try {
			rx = v == null ? 0d : new SVGLengthParser(v).parseLength().getValue();
		}catch(final ParseException ex) {
			rx = 0d;
		}

		return rx;
	}


	/**
	 * @return The value of the ry attribute (0 if there it does not exist or it is not a length).
	 * For rounded rectangles, the y-axis radius of the ellipse used to round off the corners of the rectangle.
	 */
	public double getRy() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_RY);
		double ry;

		try {
			ry = v == null ? 0d : new SVGLengthParser(v).parseLength().getValue();
		}catch(final ParseException ex) {
			ry = 0d;
		}

		return ry;
	}

	@Override
	public boolean isDimensionsRequired() {
		return true;
	}
}
