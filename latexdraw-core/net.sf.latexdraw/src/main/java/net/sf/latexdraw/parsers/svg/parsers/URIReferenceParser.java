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

/**
 * Defines a parser that parses URI references.
 * @author Arnaud BLOUIN
 */
public class URIReferenceParser extends AbstractSVGParser {
	/** The parsed URI. */
	protected String uri;


	/**
	 * Creates a parser.
	 * @param code The code to parse.
	 */
	public URIReferenceParser(final String code) {
		super(code);
	}


	/**
	 * Reads a URI reference string from an url(#ref) attribute - such as url(#id123) - returns the extracted reference (id123).
	 * @return The parsed reference or an empty string if the format of the code is not valid (not "url(#ref)")
	 */
	public String getURI() {
		skipWSP();
		final StringBuilder buf = new StringBuilder();


		if(getChar() == 'u' && nextChar() == 'r' && nextChar() == 'l' && nextChar() == '(') {
			nextChar();
			skipWSP();

			if(getChar() == '#') {
				while(nextChar() != ')' && !isEOC()) {
					buf.append((char) getChar());
				}

				if(getChar() != ')' && buf.length() > 0) {
					buf.delete(0, buf.length());
				}
			}
		}

		return buf.toString();
	}


	@Override
	public void parse() {
		uri = getURI();
	}


	/**
	 * @return The parsed URI.
	 * @since 2.0.3
	 */
	public String getUri() {
		return uri;
	}
}
