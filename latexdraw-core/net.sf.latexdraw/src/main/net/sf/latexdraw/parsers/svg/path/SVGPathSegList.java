package net.sf.latexdraw.parsers.svg.path;

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


    @Override
	public String toString() {
		final StringBuilder path = new StringBuilder();

		for(final SVGPathSeg seg : this)
			if(seg!=null) {
				path.append(seg);
				path.append(' ');
			}

		return path.toString();
	}



	@Override
	public void onPathSeg(final SVGPathSeg pathSeg) {
		add(pathSeg);
	}
}
