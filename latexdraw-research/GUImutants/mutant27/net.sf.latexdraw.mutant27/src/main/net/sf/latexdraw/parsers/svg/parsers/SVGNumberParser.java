package net.sf.latexdraw.parsers.svg.parsers;

import java.text.ParseException;

/**
 * Defines a SVG number parser.<br>
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
 * 10/20/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
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
	 * @since 0.1
	 */
	public boolean parseFlag() throws ParseException {
		skipWSP();
		int c = getChar();

		if(c=='0' || c=='1') {
				boolean flag = c=='1' ? true : false;

				nextChar();
				return flag;
		}

		throw new ParseException("Flag expected.", getPosition());		//$NON-NLS-1$
	}



	/**
	 * Parses a number (a double).
	 * @param unsigned True: the parsed number must be unsigned.
	 * @return The parsed number as a string.
	 * @throws ParseException If an error occurs or if the parsed number is signed and <code>unsigned</code> is true.
	 */
	public String parseNumberAsString(final boolean unsigned) throws ParseException {
		boolean again = true;
		int c, start;

		skipWSP();
		start = getPosition();
		c = getChar();

		if(c=='-' || c=='+') {// Reading the sign
			if(unsigned)
				throw new ParseException("Unsigned number expected.", getPosition());//$NON-NLS-1$

			c = nextChar();
		}

		while(again && !isEOC()) // Reading the first part of the number.
			if(c<48 || c>58)
				again = false;
			else
				c = nextChar();

		if(c=='.') {
			c = nextChar();
			again = true;

			while(again && !isEOC()) // Reading the second part of the number.
				if(c<48 || c>58)
					again = false;
				else
					c = nextChar();
		}

		if(c=='E' || c=='e') { // Reading the exponent.
			c = nextChar();
			again = true;

			if(c=='-' || c=='+')// Reading the sign
				c = nextChar();

			while(again && !isEOC()) // Reading the exponent.
				if(c<48 || c>58)
					again = false;
				else
					c = nextChar();
		}

		try {  Double.parseDouble(getCode().substring(start, getPosition())); }
		catch(NumberFormatException e) { throw new ParseException("Invalid number.", getPosition()); }//$NON-NLS-1$

		return getCode().substring(start, getPosition());
	}



	/**
	 * Parses a number (a double).
	 * @param unsigned True: the parsed number must be unsigned.
	 * @return The parsed number.
	 * @throws ParseException If an error occurs or if the parsed number is signed and <code>unsigned</code> is true.
	 */
	public double parseNumber(final boolean unsigned) throws ParseException {
		String number = parseNumberAsString(unsigned);

		try {  return Double.parseDouble(number); }
		catch(NumberFormatException e) { throw new ParseException("Invalid number.", getPosition()); }//$NON-NLS-1$
	}



	/**
	 * @return True if the current character is the beginning of a number.
	 * @param unsigned True: the next number must not have a sign.
	 */
	protected boolean isNumber(final boolean unsigned) {
		int c = getChar();

		if(unsigned)
			return c=='.' || (c>=48 && c<=57);

		return c=='-' || c=='+' || c=='.' || (c>=48 && c<=57);
	}



	@Override
	public void parse() throws ParseException {
		// Nothing to do.
	}
}
