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
import java.util.Objects;
import net.sf.latexdraw.parsers.css.AbstractCSSParser;

/**
 * A parser that parses SVG CSS style attributes.
 * @author Arnaud BLOUIN
 */
public class CSSStyleParser extends AbstractCSSParser {
	/** The handler which will manage actions to do when a CSS style is parsed. @since 0.1 */
	protected CSSStyleHandler handler;


	/**
	 * The constructor.
	 * @param code The code to parse (must not be null).
	 * @param csshandler The CSS style csshandler.
	 * @throws IllegalArgumentException If the given csshandler is null.
	 */
	public CSSStyleParser(final String code, final CSSStyleHandler csshandler) {
		super(code);
		handler = Objects.requireNonNull(csshandler);
	}


	@Override
	public void parse() throws ParseException {
		final StringBuilder name = new StringBuilder();
		final StringBuilder value = new StringBuilder();

		skipWSPComments();

		while(!isEOC()) {
			while(getChar() != ':' && !isWSP() && !isEOC() && !isComment()) {
				name.append((char) getChar());
				nextChar();
			}

			skipWSPComments();

			if(name.length() == 0 || isEOC() || getChar() != ':') {
				throw new ParseException("Invalid CSS style definition.", getPosition()); //NON-NLS
			}

			nextChar();
			skipWSPComments();

			while(getChar() != ';' && !isEOC() && !isComment()) {
				if(!isWSP()) {
					value.append((char) getChar());
				}

				nextChar();
			}

			if(value.length() == 0) {
				throw new ParseException("value expected but not found.", getPosition()); //NON-NLS
			}

			handler.onCSSStyle(name.toString(), value.toString());
			name.delete(0, name.length());
			value.delete(0, value.length());

			skipWSPComments();

			if(!isEOC() && getChar() != ';') {
				throw new ParseException("token ';' expected but not found.", getPosition()); //NON-NLS
			}

			nextChar();
			skipWSPComments();
		}
	}
}
