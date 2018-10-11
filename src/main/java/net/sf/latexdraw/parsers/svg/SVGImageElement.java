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

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.w3c.dom.Node;

/**
 * Defines the SVG element defining a picture.
 * @author Arnaud BLOUIN
 */
public class SVGImageElement extends SVGElement implements SVGRectParseTrait {
	/**
	 * {@link SVGImageElement#SVGImageElement(SVGDocument, String)}
	 * @param n The node.
	 * @param p The parent SVG element.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGImageElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates an SVG image.
	 * @param doc The owner document.
	 * @param pathSource The path of the picture.
	 * @throws NullPointerException If the document is null.
	 * @throws IllegalArgumentException If the given path is null.
	 */
	public SVGImageElement(final SVGDocument doc, final String pathSource) {
		super(doc);

		if(pathSource == null) {
			throw new IllegalArgumentException();
		}

		setNodeName(SVGElements.SVG_IMAGE);
		setAttribute("xlink:href", pathSource); //NON-NLS
	}


	/**
	 * @return The URI reference.
	 */
	public String getURI() {
		return getAttribute("xlink:href"); //NON-NLS
	}


	@Override
	public boolean checkAttributes() {
		final double vWidth = getWidth();
		final double vHeight = getHeight();
		return !(Double.isNaN(vWidth) || Double.isNaN(vHeight) || vWidth < 0d || vHeight < 0d);
	}


	@Override
	public boolean enableRendering() {
		if(getWidth() == 0d || getHeight() == 0d || getURI() == null) {
			return false;
		}

		try {
			final URI uri = new URI(getURI());
			final File f = new File(uri.getPath());
			return f.exists() && f.canRead();
		}catch(final URISyntaxException ex) {
			return false;
		}
	}

	@Override
	public boolean isDimensionsRequired() {
		return true;
	}
}
