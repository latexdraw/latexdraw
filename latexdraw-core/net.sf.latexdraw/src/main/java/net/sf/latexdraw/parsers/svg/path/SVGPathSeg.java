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
 * The model for the SVGPath segments.
 * @author Arnaud BLOUIN
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
		super();
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
