package net.sf.latexdraw.parsers;

import java.text.ParseException;

/**
 * This class defines the general structure of a parser.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 09/09/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public interface Parser {
	/**
	 * Launches the parsing.
	 * @throws ParseException If an error occurs.
	 * @since 2.0.2
	 */
	void parse() throws ParseException;
}
