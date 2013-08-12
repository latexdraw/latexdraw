package net.sf.latexdraw.generators.svg;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.parsers.svg.CSSStyleList;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;

/**
 * Defines method to get/set CSS style from/to a LaTeXDraw shape.<br>
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
 * 10/24/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
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
		if(shape==null || styles==null)
			return ;

		LShapeSVGGenerator.setThickness(shape, styles.getCSSValue(SVGAttributes.SVG_STROKE_WIDTH), styles.getCSSValue(SVGAttributes.SVG_STROKE));
		LShapeSVGGenerator.setLineColour(shape, styles.getCSSValue(SVGAttributes.SVG_STROKE));
		LShapeSVGGenerator.setDashedDotted(shape, styles.getCSSValue(SVGAttributes.SVG_STROKE_DASHARRAY), styles.getCSSValue(SVGAttributes.SVG_STROKE_LINECAP));
		LShapeSVGGenerator.setFill(shape, styles.getCSSValue(SVGAttributes.SVG_FILL), defs);
	}
}
