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
package net.sf.latexdraw.parsers.css;

import net.sf.latexdraw.parsers.CodeParser;

/**
 * An abstract CSS parser that contains functions to help the CSS parsing.
 * @author Arnaud BLOUIN
 * @since 2.0.3
 */
public abstract class AbstractCSSParser extends CodeParser {
	/**
	 * Creates and initialises the CSS parser.
	 * @param code The code to parse.
	 * @throws IllegalArgumentException If the given code is null.
	 * @since 2.0.3
	 */
	protected AbstractCSSParser(final String code) {
		super(code);
	}

	@Override
	public boolean isComment() {
		return getChar() == '/' && getCharAt(getPosition() + 1) == '*';
	}

	@Override
	public String skipComment() {
		if(!isComment()) {
			return null;
		}

		final StringBuilder comment = new StringBuilder();
		boolean again = true;

		nextChar();
		nextChar();

		while(again && !isEOC()) {
			if(getChar() == '*' && getCharAt(getPosition() + 1) == '/') {
				again = false;
				nextChar();
			}else {
				comment.append((char) getChar());
			}

			nextChar();
		}

		return comment.toString();
	}

	@Override
	public boolean isWSP() {
		final int c = getChar();
		return c == '\t' || c == ' ' || c == '\r' || c == '\n' || c == '\f';
	}

	@Override
	public void skipWSP() {
		while(isWSP() && !isEOC()) {
			nextChar();
		}
	}
}
