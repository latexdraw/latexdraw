/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;

/**
 * A trait for factorising code related to parsing rectangle dimensions.
 * @author Arnaud Blouin
 */
interface SVGRectParseTrait extends LElement {
	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 */
	default double getX() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_X);
		double x;

		try {
			x = v == null ? 0d : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException e) {
			x = 0d;
		}

		return x;
	}


	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 */
	default double getY() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_Y);
		double y;

		try {
			y = v == null ? 0d : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException ex) {
			y = 0d;
		}

		return y;
	}


	/**
	 * @return The value of the <code>width</code> attribute (0 if there it does not exist or it is not a length).
	 */
	default double getWidth() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_WIDTH);
		double width;

		try {
			width = v == null ? 1d : new SVGLengthParser(v).parseLength().getValue();
		}catch(final ParseException ex) {
			width = 1d;
		}

		return width;
	}


	/**
	 * @return The value of the <code>height</code> attribute (0 if there it does not exist or it is not a length).
	 */
	default double getHeight() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_HEIGHT);
		double height;

		try {
			height = v == null ? 1d : new SVGLengthParser(v).parseLength().getValue();
		}catch(final ParseException ex) {
			height = 1d;
		}

		return height;
	}
}
