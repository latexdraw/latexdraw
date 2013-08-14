package net.sf.latexdraw.parsers.svg.path;

/**
 * Defines a model for the SVGPath segments.<br>
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
public abstract class SVGPathSeg {
	/** All the different path segments. */
	public static enum PathSeg { UNKNOWN, CLOSEPATH, MOVETO_ABS, MOVETO_REL,
								LINETO_ABS, LINETO_REL, CURVETO_CUBIC_ABS, CURVETO_CUBIC_REL,
								CURVETO_QUADRATIC_ABS, CURVETO_QUADRATIC_REL, ARC_ABS,
								ARC_REL, LINETO_HORIZONTAL_ABS, LINETO_HORIZONTAL_REL,
								LINETO_VERTICAL_ABS, LINETO_VERTICAL_REL, CURVETO_CUBIC_SMOOTH_ABS,
								CURVETO_CUBIC_SMOOTH_REL, CURVETO_QUADRATIC_SMOOTH_ABS,
								CURVETO_QUADRATIC_SMOOTH_REL }


	/** Defines if the segment path is relative or absolute. @since 2.0 */
	protected boolean isRelative;

	/** The type of the segment path. @since 2.0 */
	protected PathSeg type;


	/**
	 * The main constructor.
	 * @param isRelative True: the path segment is relative, false it is absolute.
	 * @since 2.0
	 */
	public SVGPathSeg(final boolean isRelative) {
		this.isRelative = isRelative;
	}


	/**
	 * @return The type of the segment path.
	 * @since 2.0
	 */
	public abstract PathSeg getType();


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
