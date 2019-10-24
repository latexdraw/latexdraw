/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
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
 * Defines the SVG tag <code>polygon</code>.
 * @author Arnaud BLOUIN
 */
public class SVGPolygonElement extends AbstractPointsElement {
	public SVGPolygonElement(final Node n, final SVGElement p) {
		super(n, p);
	}


	/**
	 * @param doc The owner document.
	 */
	public SVGPolygonElement(final SVGDocument doc) {
		super(doc);

		setNodeName(SVGElements.SVG_POLYGON);
	}


	@Override
	public boolean enableRendering() {
		return true;
	}
}
