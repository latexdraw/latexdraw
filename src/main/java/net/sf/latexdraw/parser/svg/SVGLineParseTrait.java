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
 * An internal trait to factorise code related to parsing line parameters.
 * @author Arnaud Blouin
 */
interface SVGLineParseTrait extends LElement {
	/**
	 * @return The x-axis coordinate of the start of the line (0 if there it does not exist or it is not a length).
	 */
	default double getX1() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_X1)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0d;
		}
	}


	/**
	 * @return The y-axis coordinate of the start of the line (0 if there it does not exist or it is not a length).
	 */
	default double getY1() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_Y1)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0d;
		}
	}


	/**
	 * @return The x-axis coordinate of the end of the line (0 if there it does not exist or it is not a length).
	 */
	default double getX2() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_X2)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0d;
		}
	}


	/**
	 * @return The y-axis coordinate of the end of the line (0 if there it does not exist or it is not a length).
	 */
	default double getY2() {
		try {
			return new SVGLengthParser(getAttribute(getUsablePrefix() + SVGAttributes.SVG_Y2)).parseCoordinate().getValue();
		}catch(final ParseException ignore) {
			return 0d;
		}
	}
}
