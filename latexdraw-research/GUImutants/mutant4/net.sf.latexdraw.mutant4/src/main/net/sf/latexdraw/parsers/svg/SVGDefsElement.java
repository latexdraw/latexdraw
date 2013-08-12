package net.sf.latexdraw.parsers.svg;


import java.util.Objects;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines the SVG tag <code>defs</code>.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 09/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGDefsElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGDefsElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates a meta data element.
	 * @param owner The owner document.
	 * @throws IllegalArgumentException If owner is null.
	 * @since 0.1
	 */
	public SVGDefsElement(final SVGDocument owner) {
		super();
		ownerDocument = Objects.requireNonNull(owner);
		setNodeName(SVGElements.SVG_DEFS);
	}



	@Override
	public boolean checkAttributes() {
		return true;
	}



	@Override
	public boolean enableRendering() {
		return false;
	}



	@Override
	public SVGElement getDef(final String id) {
		if(id==null)
			return null;

		NodeList nl = getChildNodes();
		SVGElement e = null;
		Node n;

		for(int i=0, size = nl.getLength(); i<size && e==null; i++) {
			n = nl.item(i);

			if(n instanceof SVGElement && id.equals(((SVGElement)n).getId()))
				e = (SVGElement)n;
		}

		return e;
	}
}
