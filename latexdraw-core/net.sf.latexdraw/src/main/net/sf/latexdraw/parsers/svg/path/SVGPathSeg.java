package net.sf.latexdraw.parsers.svg.path;

/**
 * Defines a model for the SVGPath segments.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public abstract class SVGPathSeg {
	/** Defines if the segment path is relative or absolute. @since 2.0 */
	protected boolean isRelative;

	/**
	 * The main constructor.
	 * @param isRelative True: the path segment is relative, false it is absolute.
	 * @since 2.0
	 */
    protected SVGPathSeg(final boolean isRelative) {
		this.isRelative = isRelative;
	}


	/**
	 * @return the isRelative.
	 * @since 2.0
	 */
	public boolean isRelative() {
		return isRelative;
	}


	/**
	 * @param isRelative the isRelative to set.
	 * @since 2.0
	 */
	public void setRelative(final boolean isRelative) {
		this.isRelative = isRelative;
	}
}
