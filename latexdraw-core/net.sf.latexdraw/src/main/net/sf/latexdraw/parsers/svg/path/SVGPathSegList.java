package net.sf.latexdraw.parsers.svg.path;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Defines a list of SVGPath segments.<br>
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
public class SVGPathSegList extends ArrayList<SVGPathSeg> implements SVGPathHandler {
	private static final long	serialVersionUID	= 1L;


	/**
	 * The constructor by default.
	 */
	public SVGPathSegList() {
		super();
	}


	@Override
	public String toString() {
		final StringBuilder path = new StringBuilder();

		for(SVGPathSeg seg : this)
			if(seg!=null) {
				path.append(seg.toString());
				path.append(' ');
			}

		return path.toString();
	}



	@Override
	public void onPathSeg(final SVGPathSeg pathSeg) {
		if(pathSeg!=null)
			add(pathSeg);
	}


	/**
	 * Returns the initial point of the path containing the element at the position i.
	 * @param pos The position the begin the research.
	 * @return The initial point of the path.
	 * @since 0.1
	 */
	public Point2D getInitialPoint(final int pos) {
		if(pos<0 || pos>=size())
			return null;

		SVGPathSegMoveto m=null;

		while(m==null && pos>0)
			if(!get(pos).isRelative() && get(pos) instanceof SVGPathSegMoveto)
				m = (SVGPathSegMoveto)get(pos);

		if(m==null)
			return null;

		return new Point2D.Double(m.getX(), m.getY());
	}
}
