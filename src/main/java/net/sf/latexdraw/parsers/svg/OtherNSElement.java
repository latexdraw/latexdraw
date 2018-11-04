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


import java.util.Objects;
import org.w3c.dom.Node;

/**
 * Defines a generic element to store a non-SVG tag.
 * @author Arnaud BLOUIN
 */
public class OtherNSElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public OtherNSElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}



	/**
	 * Creates an non SVG element.
	 * @param document The owner document.
	 * @throws IllegalArgumentException if document is null.
	 */
	public OtherNSElement(final SVGDocument document) {
		super();
		ownerDocument = Objects.requireNonNull(document);
	}



	@Override
	public boolean checkAttributes() {
		return true;
	}



	@Override
	public boolean enableRendering() {
		return false;
	}
}
