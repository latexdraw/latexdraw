package net.sf.latexdraw.parsers.svg.path;

/**
 * Defines the SVGPath closepath segment.<br>
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
public class SVGPathSegClosePath extends SVGPathSeg {
	/**
	 * The constructor.
	 * @since 2.0
	 */
	public SVGPathSegClosePath() {
		super(true);
	}


	@Override
	public String toString() {
		return "z";//$NON-NLS-1$
	}

	@Override
	public PathSeg getType() {
		return PathSeg.CLOSEPATH;
	}
}
