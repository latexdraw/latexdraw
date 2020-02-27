/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.parser.svg;

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>ellipse</code>.
 * @author Arnaud BLOUIN
 */
public class SVGEllipseElement extends SVGElement {
	public SVGEllipseElement(final Node n, final SVGElement p) {
		super(n, p);
	}


	/**
	 * Creates an SVG ellipse element.
	 * @param cx The X-centre coordinate.
	 * @param cy The Y-centre coordinate.
	 * @param rx The X-radius of the ellipse.
	 * @param ry The Y-radius of the ellipse.
	 * @param owner The document owner.
	 * @throws IllegalArgumentException If owner is null or if a given value is not valid.
	 */
	public SVGEllipseElement(final double cx, final double cy, final double rx, final double ry, final SVGDocument owner) {
		super(owner);

		setAttribute(SVGAttributes.SVG_CX, String.valueOf(cx));
		setAttribute(SVGAttributes.SVG_CY, String.valueOf(cy));
		setAttribute(SVGAttributes.SVG_RX, String.valueOf(rx));
		setAttribute(SVGAttributes.SVG_RY, String.valueOf(ry));
		setNodeName(SVGElements.SVG_ELLIPSE);
		ownerDocument = owner;

		if(!checkAttributes()) {
			throw new IllegalArgumentException();
		}
	}


	/**
	 * @return The x-axis coordinate of the centre of the ellipse (0 if there it does not exist or it is not a coordinate).
	 */
	public double getCx() {
		return SVGParserUtils.INSTANCE.parseLength(getAttribute(getUsablePrefix() + SVGAttributes.SVG_CX)).map(val -> val.getValue()).orElse(0d);
	}


	/**
	 * @return The y-axis coordinate of the centre of the ellipse (0 if there it does not exist or it is not a coordinate).
	 */
	public double getCy() {
		return SVGParserUtils.INSTANCE.parseLength(getAttribute(getUsablePrefix() + SVGAttributes.SVG_CY)).map(val -> val.getValue()).orElse(0d);
	}


	/**
	 * @return The x-axis radius of the ellipse (NaN if there it does not exist or it is not a length).
	 */
	public double getRx() {
		return SVGParserUtils.INSTANCE.parseLength(getAttribute(getUsablePrefix() + SVGAttributes.SVG_RX)).map(val -> val.getValue()).orElse(0d);
	}


	/**
	 * @return The y-axis radius of the ellipse (NaN if there it does not exist or it is not a length).
	 */
	public double getRy() {
		return SVGParserUtils.INSTANCE.parseLength(getAttribute(getUsablePrefix() + SVGAttributes.SVG_RY)).map(val -> val.getValue()).orElse(Double.NaN);
	}


	@Override
	public boolean checkAttributes() {
		final double rx = getRx();
		final double ry = getRy();

		return rx >= 0d && ry >= 0d && !Double.isNaN(rx) && !Double.isNaN(ry);
	}


	@Override
	public boolean enableRendering() {
		return Double.compare(getRx(), 0d) != 0 && Double.compare(getRy(), 0d) != 0;
	}
}
