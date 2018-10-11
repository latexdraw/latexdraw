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

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>line</code>.
 * @author Arnaud BLOUIN
 */
public class SVGLineElement extends SVGElement implements SVGLineParseTrait {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGLineElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates an SVG line (0,0 ; 0,0)
	 * @param doc The owner document.
	 */
	public SVGLineElement(final SVGDocument doc) {
		super(doc);
		setNodeName(SVGElements.SVG_LINE);
		setAttribute(SVGAttributes.SVG_X1, "0"); //NON-NLS
		setAttribute(SVGAttributes.SVG_X2, "0"); //NON-NLS
		setAttribute(SVGAttributes.SVG_Y1, "0"); //NON-NLS
		setAttribute(SVGAttributes.SVG_Y2, "0"); //NON-NLS
	}


	/**
	 * Sets the first X-coordinate of the line.
	 * @param x1 The first X-coordinate of the line.
	 */
	public void setX1(final double x1) {
		setAttribute(getUsablePrefix() + SVGAttributes.SVG_X1, String.valueOf(x1));
	}


	/**
	 * Sets the second X-coordinate of the line.
	 * @param x2 The second X-coordinate of the line.
	 */
	public void setX2(final double x2) {
		setAttribute(getUsablePrefix() + SVGAttributes.SVG_X2, String.valueOf(x2));
	}


	/**
	 * Sets the first Y-coordinate of the line.
	 * @param y1 The first Y-coordinate of the line.
	 */
	public void setY1(final double y1) {
		setAttribute(getUsablePrefix() + SVGAttributes.SVG_Y1, String.valueOf(y1));
	}


	/**
	 * Sets the second Y-coordinate of the line.
	 * @param y2 The second Y-coordinate of the line.
	 */
	public void setY2(final double y2) {
		setAttribute(getUsablePrefix() + SVGAttributes.SVG_Y2, String.valueOf(y2));
	}


	@Override
	public boolean checkAttributes() {
		return true;
	}


	@Override
	public boolean enableRendering() {
		return true;
	}
}
