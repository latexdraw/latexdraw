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

import java.text.ParseException;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>pattern</code>.
 * @author Arnaud BLOUIN
 */
public class SVGPatternElement extends SVGElement implements SVGRectParseTrait {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGPatternElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates a new empty SVG Pattern element.
	 * @param doc The owner document.
	 */
	public SVGPatternElement(final SVGDocument doc) {
		super(doc);
		setNodeName(SVGElements.SVG_PATTERN);
	}


	@Override
	public boolean checkAttributes() {
		return true;
	}


	@Override
	public boolean enableRendering() {
		return !(MathUtils.INST.equalsDouble(getWidth(), 0d) || MathUtils.INST.equalsDouble(getHeight(), 0d));
	}


	/**
	 * @return The coordinate system for the contents of the 'pattern'.
	 */
	public String getPatternContentUnits() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_PATTERN_CONTENTS_UNITS);
		return (v == null || (!SVGAttributes.SVG_UNITS_VALUE_OBJ.equals(v) && !SVGAttributes.SVG_UNITS_VALUE_USR.equals(v))) ? SVGAttributes.SVG_UNITS_VALUE_USR : v;
	}


	/**
	 * @return The coordinate system for attributes x, y, width and height.
	 */
	public String getPatternUnits() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_PATTERN_UNITS);
		return (v == null || (!SVGAttributes.SVG_UNITS_VALUE_OBJ.equals(v) && !SVGAttributes.SVG_UNITS_VALUE_USR.equals(v))) ? SVGAttributes.SVG_UNITS_VALUE_OBJ : v;
	}


	/**
	 * @return The g element of the pattern if there is a one (used to define if there is hatching).
	 */
	protected SVGGElement getGElement() {
		return children.getNodes().stream().filter(ch -> ch instanceof SVGGElement).map(ch -> (SVGGElement) ch).findAny().orElse(null);
	}


	/**
	 * @return The background colour of the pattern if there is a one.
	 */
	public Color getBackgroundColor() {
		return children.getNodes().stream().filter(ch -> ch instanceof SVGRectElement).
			map(ch -> CSSColors.INSTANCE.getRGBColour(ch.getFill())).findAny().orElse(null);
	}


	/**
	 * @return The separation between each line of the hatching if there is an hatching (based on the two first lines of the possible hatching).
	 */
	public double getHatchingSep() {
		final SVGGElement g = getGElement();
		SVGPathElement p = null;

		if(g != null) {
			p = g.children.getNodes().stream().filter(ch -> ch instanceof SVGPathElement).map(ch -> (SVGPathElement) ch).findAny().orElse(null);
		}

		if(p == null) {
			return Double.NaN;
		}

		final String path = p.getPathData();
		final int i = path.indexOf('L');
		int j = -1;

		if(i != -1) {
			j = path.indexOf('L', i + 1);
		}

		if(i == -1 || j == -1) {
			return Double.NaN;
		}

		final String[] coord1 = path.substring(i, j).split(" "); //NON-NLS
		final String[] coord2 = path.substring(j).split(" "); //NON-NLS

		if(coord1.length < 3 || coord2.length < 3) {
			return Double.NaN;
		}

		try {
			final float l1x;
			final float l2x;
			final float l1y;
			final float l2y;

			l1x = Double.valueOf(coord1[1]).floatValue();
			l1y = Double.valueOf(coord1[2]).floatValue();
			l2x = Double.valueOf(coord2[1]).floatValue();
			l2y = Double.valueOf(coord2[2]).floatValue();

			if(MathUtils.INST.equalsDouble(l1x, l2x)) {
				return Math.abs(l1y - l2y) - getHatchingStrokeWidth();
			}

			return Math.abs(l1x - l2x) - getHatchingStrokeWidth();
		}catch(final NumberFormatException ex) {
			/* To ignore. */
		}

		return Double.NaN;
	}


	/**
	 * @return The colour of the possible hatching if there is a one.
	 */
	public Color getHatchingColor() {
		final SVGGElement g = getGElement();
		return g == null ? null : g.getStroke();
	}


	/**
	 * @return The stroke width of the possible hatching.
	 */
	public double getHatchingStrokeWidth() {
		final SVGGElement g = getGElement();
		final String code = g == null ? null : g.getAttribute(getUsablePrefix() + SVGAttributes.SVG_STROKE_WIDTH);

		try {
			return code == null ? Double.NaN : new SVGLengthParser(code).parseLength().getValue();
		}catch(final ParseException ex) {
			return Double.NaN;
		}
	}

	@Override
	public boolean isDimensionsRequired() {
		return false;
	}
}
