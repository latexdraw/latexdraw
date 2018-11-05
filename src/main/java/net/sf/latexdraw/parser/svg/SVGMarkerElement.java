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
package net.sf.latexdraw.parser.svg;

import java.text.ParseException;
import net.sf.latexdraw.parser.svg.parsers.SVGLengthParser;
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
	public SVGMarkerElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * @return The x-axis coordinate of the reference point which is to be aligned exactly at the marker position.
	 */
	public double getRefX() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_REF_X)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0;
		}
	}


	/**
	 * @return The y-axis coordinate of the reference point which is to be aligned exactly at the marker position.
	 */
	public double getRefY() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_REF_Y)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0;
		}
	}


	/**
	 * @return Represents the width of the viewport into which the marker is to be fitted when it is rendered.
	 */
	public double getMarkerWidth() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_WIDTH)).parseLength().getValue();
		}catch(final ParseException ignore) {
			return 3;
		}
	}


	/**
	 * @return Represents the height of the viewport into which the marker is to be fitted when it is rendered.
	 */
	public double getMarkerHeight() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_HEIGHT)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 3;
		}
	}


	/**
	 * @return The coordinate system for attributes markerWidth, markerHeight and the contents of the 'marker'.
	 */
	public String getMarkerUnits() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_MARKER_UNITS);
		return (!SVGAttributes.SVG_UNITS_VALUE_STROKE.equals(v) && !SVGAttributes.SVG_UNITS_VALUE_USR.equals(v)) ? SVGAttributes.SVG_UNITS_VALUE_STROKE : v;
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
