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
 * Defines the SVG tag <code>marker</code>.
 * @author Arnaud BLOUIN
 */
public class SVGMarkerElement extends SVGElement {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 * @param owner The owner document.
	 */
	public SVGMarkerElement(final SVGDocument owner) {
		super(owner);

		setNodeName(SVGElements.SVG_MARKER);
	}


	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGMarkerElement(final Node n, final SVGElement p) {
		super(n, p);
	}


	/**
	 * @return Represents the width of the viewport into which the marker is to be fitted when it is rendered.
	 */
	public double getMarkerWidth() {
		return SVGParserUtils.INSTANCE.parseLength(getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_WIDTH)).map(v -> v.getValue()).orElse(3d);
	}


	/**
	 * @return Represents the height of the viewport into which the marker is to be fitted when it is rendered.
	 */
	public double getMarkerHeight() {
		return SVGParserUtils.INSTANCE.parseLength(getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_HEIGHT)).map(v -> v.getValue()).orElse(3d);
	}


	@Override
	public boolean checkAttributes() {
		return getMarkerWidth() >= 0 && getMarkerHeight() >= 0;
	}


	@Override
	public boolean enableRendering() {
		return getMarkerWidth() > 0 && getMarkerHeight() > 0;
	}
}
