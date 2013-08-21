package net.sf.latexdraw.parsers.svg.path;

/**
 * Defines the SVGPath moveto segment.<br>
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
public class SVGPathSegMoveto extends SVGPathSegLineto {
	/**
	 * The main constructor.
	 * @param x The X-coordinate of the point to go.
	 * @param y The Y-coordinate of the point to go.
	 * @param isRelative
	 */
	public SVGPathSegMoveto(final double x, final double y, final boolean isRelative) {
		super(x, y, isRelative);
	}


	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();

		builder.append(isRelative() ? 'm' : 'M');
		builder.append(' ');
		builder.append(x);
		builder.append(' ');
		builder.append(y);

		return builder.toString();
	}


	@Override
	public PathSeg getType() {
		return isRelative() ? PathSeg.MOVETO_REL : PathSeg.MOVETO_ABS;
	}
}
