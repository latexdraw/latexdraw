package net.sf.latexdraw.parsers.svg;


import java.util.Objects;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>g</code>.
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 * 09/11/07
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGGElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGGElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates an empty G element.
	 * @param owner The owner document.
	 * @throws IllegalArgumentException If p is null.
	 * @since 0.1
	 */
	public SVGGElement(final SVGDocument owner) {
		super();
		ownerDocument = Objects.requireNonNull(owner);
		setNodeName(SVGElements.SVG_G);
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
