package net.sf.latexdraw.parsers.svg;

import java.util.HashMap;

import net.sf.latexdraw.parsers.svg.parsers.CSSStyleHandler;

/**
 * Defines a list of CSS styles.<br>
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
 * 10/24/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class CSSStyleList extends HashMap<String, String> implements CSSStyleHandler {
	private static final long serialVersionUID = 1L;


	/**
	 * Adds a CSS style to the list.
	 * @param name The name of the style.
	 * @param value The value of the style.
	 * @since 0.1
	 */
	public void addCSSStyle(final String name, final String value) {
		if(name!=null && value!=null)
			put(name, value);
	}



	/**
	 * @param styleName The name of the style.
	 * @return The value of the given CSS style name or null.
	 */
	public String getCSSValue(final String styleName)
	{
		return get(styleName);
	}



	@Override
	public void onCSSStyle(final String name, final String value) {
		if(name!=null && value!=null)
			addCSSStyle(name, value);
	}
}
