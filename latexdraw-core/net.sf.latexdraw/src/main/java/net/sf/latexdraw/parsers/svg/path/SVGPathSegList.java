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

import java.util.ArrayList;

/**
 * A list of SVGPath segments.
 * @author Arnaud BLOUIN
 */
public class SVGPathSegList extends ArrayList<SVGPathSeg> implements SVGPathHandler {
	@Override
	public String toString() {
		final StringBuilder path = new StringBuilder();

		for(final SVGPathSeg seg : this) {
			if(seg != null) {
				path.append(seg);
				path.append(' ');
			}
		}

		return path.toString();
	}


	@Override
	public void onPathSeg(final SVGPathSeg pathSeg) {
		add(pathSeg);
	}
}
