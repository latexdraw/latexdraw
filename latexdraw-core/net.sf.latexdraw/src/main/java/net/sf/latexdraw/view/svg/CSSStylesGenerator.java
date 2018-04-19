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
package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.CSSStyleList;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;

/**
 * Methods to get/set CSS style from/to a LaTeXDraw shape.
 * @author Arnaud BLOUIN
 */
final class CSSStylesGenerator {
	/** The singleton. */
	public static final CSSStylesGenerator INSTANCE = new CSSStylesGenerator();


	private CSSStylesGenerator() {
		super();
	}


	/**
	 * Sets the CSS styles of the given list to the given LaTeXDraw shape.
	 * @param shape The shape to set.
	 * @param styles The styles to set to the shape.
	 * @param defs The definitions, may be null.
	 * @since 2.0.0
	 */
	public void setCSSStyles(final IShape shape, final CSSStyleList styles, final SVGDefsElement defs) {
		if(shape == null || styles == null) {
			return;
		}

		SVGShape.setThickness(shape, styles.getCSSValue(SVGAttributes.SVG_STROKE_WIDTH), styles.getCSSValue(SVGAttributes.SVG_STROKE));
		SVGShape.setLineColour(shape, styles.getCSSValue(SVGAttributes.SVG_STROKE), styles.getCSSValue(SVGAttributes.SVG_STROKE_OPACITY));
		SVGShape.setDashedDotted(shape, styles.getCSSValue(SVGAttributes.SVG_STROKE_DASHARRAY), styles.getCSSValue(SVGAttributes.SVG_STROKE_LINECAP));
		SVGShape.setFillFromSVG(shape, styles.getCSSValue(SVGAttributes.SVG_FILL), styles.getCSSValue(SVGAttributes.SVG_FILL_OPACITY), defs);
	}
}
