package net.sf.latexdraw.parsers.svg.parsers;

import net.sf.latexdraw.parsers.CodeParser;

/**
 * Defines a model for the SVG parsers.<br>
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
 * @since 2.0.0
 */
public abstract class AbstractSVGParser extends CodeParser {
	/**
	 * The constructor.
	 * @param code The path to parse.
	 */
	public AbstractSVGParser(final String code) {
		super(code);
	}



	@Override
	public boolean isComment() {
		return false;
	}


	@Override
	public boolean isWSP() {
    	final int c = getChar();
    	return c==0x20 || c==0x9 || c==0xD || c==0xA;
	}



	@Override
	public void skipWSP() {
    	while(!isEOC() && isWSP())
    		nextChar();
	}


	/**
	 * Skips the useless characters and a possible comma.
	 */
    public void skipWSPComma() {
    	skipWSP();

    	if(getChar()==',') {
    		nextChar();
    		skipWSP();
    	}
	}


	@Override
	public String skipComment() {
		return null;
	}
}
