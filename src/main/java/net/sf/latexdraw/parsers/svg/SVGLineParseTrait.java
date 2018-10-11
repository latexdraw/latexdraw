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
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;

/**
 * An internal trait to factorise code related to parsing line parameters.
 * @author Arnaud Blouin
 */
interface SVGLineParseTrait extends LElement {
	/**
	 * @return The x-axis coordinate of the start of the line (0 if there it does not exist or it is not a length).
	 */
	default double getX1() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_X1);
		double x1;

		try {
			x1 = v == null ? 0d : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException ex) {
			x1 = 0d;
		}

		return x1;
	}


	/**
	 * @return The y-axis coordinate of the start of the line (0 if there it does not exist or it is not a length).
	 */
	default double getY1() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_Y1);
		double y1;

		try {
			y1 = v == null ? 0d : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException ex) {
			y1 = 0d;
		}

		return y1;
	}


	/**
	 * @return The x-axis coordinate of the end of the line (0 if there it does not exist or it is not a length).
	 */
	default double getX2() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_X2);
		double x2;

		try {
			x2 = v == null ? 0d : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException ex) {
			x2 = 0d;
		}

		return x2;
	}


	/**
	 * @return The y-axis coordinate of the end of the line (0 if there it does not exist or it is not a length).
	 */
	default double getY2() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_Y2);
		double y2;

		try {
			y2 = v == null ? 0d : new SVGLengthParser(v).parseCoordinate().getValue();
		}catch(final ParseException ex) {
			y2 = 0d;
		}

		return y2;
	}
}
