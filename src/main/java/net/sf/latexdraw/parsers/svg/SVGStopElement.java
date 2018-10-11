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
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>stop</code>.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class SVGStopElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGStopElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates a new empty SVG STOP element.
	 * @param doc The owner document.
	 */
	public SVGStopElement(final SVGDocument doc) {
		super(doc);

		setNodeName(SVGElements.SVG_STOP);
	}


	@Override
	public boolean checkAttributes() {
		return !Double.isNaN(getOffset());
	}


	@Override
	public boolean enableRendering() {
		return true;
	}


	/**
	 * The offset attribute indicates where the gradient stop is placed.
	 * @return The read offset or NaN.
	 * @since 0.1
	 */
	public double getOffset() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_OFFSET);
		double offset;

		try {
			offset = v == null ? Double.NaN : new SVGLengthParser(v).parseNumberOrPercent().getValue();
		}catch(final ParseException e) {
			offset = Double.NaN;
		}

		return offset;
	}


	/**
	 * Indicates what colour to use at that gradient stop.
	 * @return The read colour or black.
	 * @since 0.1
	 */
	public Color getStopColor() {
		final Color c = CSSColors.INSTANCE.getRGBColour(getAttribute(getUsablePrefix() + SVGAttributes.SVG_STOP_COLOR));

		return c == null ? DviPsColors.BLACK : c;
	}
}
