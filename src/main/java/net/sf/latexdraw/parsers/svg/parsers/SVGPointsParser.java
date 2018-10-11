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
package net.sf.latexdraw.parsers.svg.parsers;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a parser that parses points from polygons and polylines.
 * @author Arnaud BLOUIN
 * @since 2.0.3
 */
public class SVGPointsParser extends AbstractSVGParser {
	/**
	 * Parses the given code and return the parsed points or null.
	 * @param code The code to parse.
	 * @return The parsed points or null.
	 * @since 2.0.3
	 */
	public static List<Point2D> getPoints(final String code) {
		try {
			final SVGPointsParser pp = new SVGPointsParser(code);
			pp.parse();
			return pp.getPoints();
		}catch(final IllegalArgumentException | ParseException ex) {
			return null;
		}
	}

	/** The parsed points. */
	protected List<Point2D> points;


	/**
	 * Creates and initialises the parser.
	 * @param code The code to parse.
	 * @throws IllegalArgumentException If the given code is null.
	 * @since 2.0.3
	 */
	public SVGPointsParser(final String code) {
		super(code);

		points = new ArrayList<>();
	}

	@Override
	public void parse() throws ParseException {
		double c1;
		double c2;

		skipWSP();

		while(!isEOC()) {
			c1 = readNumber();
			skipWSPComma();
			c2 = readNumber();
			points.add(new Point2D.Double(c1, c2));
			skipWSPComma();
		}
	}


	/**
	 * Reads a number.
	 * @return The read number.
	 * @throws ParseException If the number is not valid.
	 * @since 2.0.3
	 */
	public double readNumber() throws ParseException {
		final double n;
		final boolean isNegative;
		boolean isFractional = false;
		boolean isFloating = false;
		final StringBuilder strn = new StringBuilder();

		skipWSP();

		if(getChar() == '-' || getChar() == '+') {
			isNegative = getChar() == '-';
			nextChar();
		}else {
			isNegative = false;
		}

		while(!isWSP() && getChar() != ',' && !isEOC()) {
			switch(getChar()) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					break;

				case '.':
					if(isFractional) {
						throw new ParseException("An unexpected dot was read: " + getCode(), getPosition()); //NON-NLS
					}
					isFractional = true;
					break;

				case 'e':
				case 'E':
					if(isFloating) {
						throw new ParseException("An unexpected exponantial token was read: " + getCode(), getPosition()); //NON-NLS
					}
					isFloating = true;
					break;

				case '-':
				case '+':
					if(!isFloating) {
						throw new ParseException("An unexpected sign was read: " + getCode(), getPosition()); //NON-NLS
					}
					break;

				default:
					throw new ParseException("The following character is not authorised:" + (char) getChar(), getPosition()); //NON-NLS
			}

			strn.append((char) getChar());
			nextChar();
		}

		try {
			n = Double.parseDouble(strn.toString());
		}catch(final NumberFormatException ex) {
			throw new ParseException("Not able to parse to given number:" + strn, getPosition()); //NON-NLS
		}

		return isNegative ? n * -1 : n;
	}


	/**
	 * @return The points.
	 * @since 2.0.3
	 */
	public List<Point2D> getPoints() {
		return points;
	}
}
