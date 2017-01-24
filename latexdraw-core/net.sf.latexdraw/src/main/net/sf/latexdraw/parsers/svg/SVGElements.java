package net.sf.latexdraw.parsers.svg;

/**
 * Contains SVG elements according to the SVG specification version 1.1.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2017 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE.  See the GNU General Public License for more details.<br>
 *<br>
 * 06/03/07<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 */
public final class SVGElements {
	private SVGElements() {
		super();
	}
	
	public static final String SVG_CIRCLE				= "circle";					//$NON-NLS-1$
	public static final String SVG_DEFS					= "defs";					//$NON-NLS-1$
	public static final String SVG_ELLIPSE	   			= "ellipse";				//$NON-NLS-1$
	public static final String SVG_G					= "g";						//$NON-NLS-1$
	public static final String SVG_METADATA				= "metadata";				//$NON-NLS-1$
	public static final String SVG_POLYGON				= "polygon";				//$NON-NLS-1$
	public static final String SVG_POLY_LINE			= "polyline";				//$NON-NLS-1$
	public static final String SVG_LINEAR_GRADIENT		= "linearGradient";			//$NON-NLS-1$
	public static final String SVG_RECT					= "rect";					//$NON-NLS-1$
	public static final String SVG_TEXT					= "text";					//$NON-NLS-1$
	public static final String SVG_SVG					= "svg";					//$NON-NLS-1$
	public static final String SVG_STOP					= "stop";					//$NON-NLS-1$
	public static final String SVG_PATTERN				= "pattern";				//$NON-NLS-1$
	public static final String SVG_PATH					= "path";					//$NON-NLS-1$
	public static final String SVG_MARKER				= "marker";					//$NON-NLS-1$
	public static final String SVG_LINE					= "line";					//$NON-NLS-1$
	public static final String SVG_IMAGE				= "image";					//$NON-NLS-1$
}
