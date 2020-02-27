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
 * Defines the SVG tag <code>polyline</code>.
 * @author Arnaud BLOUIN
 */
public class SVGPolyLineElement extends AbstractPointsElement {
	public SVGPolyLineElement(final Node n, final SVGElement p) {
		super(n, p);
	}

	/**
	 * @param doc The owner document.
	 */
	public SVGPolyLineElement(final SVGDocument doc) {
		super(doc);
		setNodeName(SVGElements.SVG_POLY_LINE);
	}

	@Override
	public boolean enableRendering() {
		return true;
	}
}
