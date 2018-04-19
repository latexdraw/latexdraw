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
package net.sf.latexdraw.parsers.svg.path;

/**
 * The SVGPath horizontal lineto segment.
 * @author Arnaud BLOUIN
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
		return String.valueOf(isRelative() ? 'h' : 'H') + ' ' + x;
	}


	@Override
	public double getY() {
		return getX();
	}
}
