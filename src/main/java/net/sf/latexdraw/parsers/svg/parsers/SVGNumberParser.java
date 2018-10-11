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

import java.text.ParseException;
import java.util.Optional;

/**
 * Defines a SVG number parser.
 * @author Arnaud BLOUIN
 */
public class SVGNumberParser extends AbstractSVGParser {
	/**
	 * The constructor.
	 * @param code The code to parse.
	 */
	public SVGNumberParser(final String code) {
		super(code);
	}


	/**
	 * Parses a flag (a boolean).
	 * @return True or false.
	 * @throws ParseException If an error occurs.
	 */
	public boolean parseFlag() throws ParseException {
		skipWSP();
		final int c = getChar();

		if(c == '0' || c == '1') {
			nextChar();
			return c == '1';
		}

		throw new ParseException("Flag expected.", getPosition()); //NON-NLS
	}


	private Optional<Integer> readSign(final int c, final boolean unsigned) throws ParseException {
		if(c == '-' || c == '+') {
			if(unsigned) {
				throw new ParseException("Unsigned number expected.", getPosition());
			}

			return Optional.of(nextChar());
		}
		return Optional.empty();
	}

	/**
	 * Parses a number (a double).
	 * @param unsigned True: the parsed number must be unsigned.
	 * @return The parsed number as a string.
	 * @throws ParseException If an error occurs or if the parsed number is signed and <code>unsigned</code> is true.
	 */
	public String parseNumberAsString(final boolean unsigned) throws ParseException {
		boolean again = true;
		int c;
		final int start;

		skipWSP();
		start = getPosition();
		c = getChar();

		// Reading the sign
		c = readSign(c, unsigned).orElse(c);

		while(again && !isEOC()) { // Reading the first part of the number.
			if(c < 48 || c > 58) {
				again = false;
			}else {
				c = nextChar();
			}
		}

		if(c == '.') {
			c = nextChar();
			again = true;

			// Reading the second part of the number.
			while(again && !isEOC()) {
				if(c < 48 || c > 58) {
					again = false;
				}else {
					c = nextChar();
				}
			}
		}

		// Reading the exponent.
		if(c == 'E' || c == 'e') {
			c = nextChar();
			again = true;

			// Reading the sign
			c = readSign(c, false).orElse(c);

			// Reading the exponent.
			while(again && !isEOC()) {
				if(c < 48 || c > 58) {
					again = false;
				}else {
					c = nextChar();
				}
			}
		}

		try {
			Double.parseDouble(getCode().substring(start, getPosition()));
		}catch(final NumberFormatException ex) {
			throw new ParseException("Invalid number.", getPosition()); //NON-NLS
		}

		return getCode().substring(start, getPosition());
	}


	/**
	 * Parses a number (a double).
	 * @param unsigned True: the parsed number must be unsigned.
	 * @return The parsed number.
	 * @throws ParseException If an error occurs or if the parsed number is signed and <code>unsigned</code> is true.
	 */
	public double parseNumber(final boolean unsigned) throws ParseException {
		final String number = parseNumberAsString(unsigned);

		try {
			return Double.parseDouble(number);
		}catch(final NumberFormatException e) {
			throw new ParseException("Invalid number.", getPosition()); //NON-NLS
		}
	}


	/**
	 * @param unsigned True: the next number must not have a sign.
	 * @return True if the current character is the beginning of a number.
	 */
	protected boolean isNumber(final boolean unsigned) {
		final int c = getChar();

		if(unsigned) {
			return c == '.' || (c >= 48 && c <= 57);
		}

		return c == '-' || c == '+' || c == '.' || (c >= 48 && c <= 57);
	}


	@Override
	public void parse() throws ParseException {
		// Nothing to do.
	}
}
