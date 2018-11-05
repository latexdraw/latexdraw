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

/**
 * A trait for factorising code related to parsing rectangle dimensions.
 * @author Arnaud Blouin
 */
interface SVGRectParseTrait extends LElement {
	boolean isDimensionsRequired();

	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 */
	default double getX() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_X)).parseCoordinate().getValue();
		}catch(final ParseException ex) {
			return 0d;
		}
	}


	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 */
	default double getY() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_Y)).parseCoordinate().getValue();
		}catch(final ParseException ex) {
			return 0d;
		}
	}


	/**
	 * @return The value of the <code>width</code> attribute (0 if there it does not exist or it is not a length).
	 */
	default double getWidth() {
		final String value = getAttribute(getUsablePrefix() + SVGAttributes.SVG_WIDTH);
		final double defVal = isDimensionsRequired() ? Double.NaN : 0d;

		try {
			return value.isEmpty() ? defVal : new SVGLengthParser(value).parseLength().getValue();
		}catch(final ParseException ex) {
			return defVal;
		}
	}


	/**
	 * @return The value of the <code>height</code> attribute (0 if there it does not exist or it is not a length).
	 */
	default double getHeight() {
		final String value = getAttribute(getUsablePrefix() + SVGAttributes.SVG_HEIGHT);
		final double defVal = isDimensionsRequired() ? Double.NaN : 0d;

		try {
			return value.isEmpty() ? defVal : new SVGLengthParser(value).parseLength().getValue();
		}catch(final ParseException ex) {
			return defVal;
		}
	}
}
