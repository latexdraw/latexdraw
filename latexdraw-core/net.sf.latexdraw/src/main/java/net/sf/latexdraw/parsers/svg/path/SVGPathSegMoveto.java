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
 * The SVGPath moveto segment.
 * @author Arnaud BLOUIN
 */
public class SVGPathSegMoveto extends SVGPathSegLineto {
	/**
	 * The main constructor.
	 * @param x The X-coordinate of the point to go.
	 * @param y The Y-coordinate of the point to go.
	 * @param isRelative Whether the seg is relative or absolute.
	 */
	public SVGPathSegMoveto(final double x, final double y, final boolean isRelative) {
		super(x, y, isRelative);
	}

	@Override
	public String toString() {
		return String.valueOf(isRelative() ? 'm' : 'M') + ' ' + x + ' ' + y;
	}
}
