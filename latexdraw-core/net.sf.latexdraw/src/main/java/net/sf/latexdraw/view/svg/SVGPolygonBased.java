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
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGPolygonElement;
import net.sf.latexdraw.view.pst.PSTricksConstants;

abstract class SVGPolygonBased<T extends IShape> extends SVGShape<T> {
	/**
	 * Creates the SVG generator.
	 * @param sh The shape used for the generation.
	 * @throws IllegalArgumentException If the given shape is null.
	 */
	SVGPolygonBased(final T sh) {
		super(sh);
	}

	void setDbleBorderPolygon(final SVGDocument doc, final SVGElement root, final String points) {
		if(shape.hasDbleBord()) {
			final SVGElement dblBord = new SVGPolygonElement(doc);
			dblBord.setAttribute(SVGAttributes.SVG_POINTS, points);
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}
	}

	void setShadowPolygon(final SVGDocument doc, final SVGElement root, final String points) {
		if(shape.hasShadow()) {
			final SVGElement elt = new SVGPolygonElement(doc);
			elt.setAttribute(SVGAttributes.SVG_POINTS, points);
			setSVGShadowAttributes(elt, true);
			root.appendChild(elt);
		}

		if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE)) {
			// The background of the borders must be filled is there is a shadow.
			final SVGElement elt = new SVGPolygonElement(doc);
			elt.setAttribute(SVGAttributes.SVG_POINTS, points);
			setSVGBorderBackground(elt, root);
		}
	}
}
