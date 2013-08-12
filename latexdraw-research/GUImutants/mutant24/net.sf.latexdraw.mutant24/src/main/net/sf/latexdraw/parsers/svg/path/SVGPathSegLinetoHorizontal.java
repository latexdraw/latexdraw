package net.sf.latexdraw.parsers.svg.path;

/**
 * Defines the SVGPath horizontal lineto segment.<br>
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
public class SVGPathSegLinetoHorizontal extends SVGPathSegLineto {
	/**
	 * The main constructor.
	 * @param x The X-coordinate of the second point of the line.
	 * @param isRelative isRelative True: the path segment is relative, false it is absolute.
	 */
	public SVGPathSegLinetoHorizontal(final double x, final boolean isRelative) {
		super(x, x, isRelative);
	}


	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();

		builder.append(isRelative() ? 'h' : 'H');
		builder.append(' ');
		builder.append(x);

		return builder.toString() ;
	}


	@Override
	public PathSeg getType() {
		return isRelative() ? PathSeg.LINETO_HORIZONTAL_REL : PathSeg.LINETO_HORIZONTAL_ABS;
	}


	@Override
	public double getY() {
		return getX();
	}
}
