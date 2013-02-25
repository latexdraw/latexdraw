package net.sf.latexdraw.parsers.svg;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NodeList;

/**
 * Defines an SVG node list.<br>
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
 * 09/16/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGNodeList implements NodeList {
	/** The nodes of the list. @since 0.1 */
	protected List<SVGElement> nodes;


	/**
	 * The constructor by default.
	 */
	public SVGNodeList() {
		nodes = new ArrayList<>();
	}



	@Override
	public int getLength() {
		return nodes==null ? 0 : nodes.size();
	}



	@Override
	public SVGElement item(final int index) {
		return nodes==null || index<0 || index>=nodes.size() ? null : nodes.get(index);
	}



	@Override
	public String toString() {
		StringBuilder str = new StringBuilder().append('{');

		for(SVGElement e : nodes)
			str.append(e.toString()).append(", ");//$NON-NLS-1$

		return str.append('}').toString();
	}



	/**
	 * @return the nodes.
	 * @since 0.1
	 */
	public List<SVGElement> getNodes() {
		return nodes;
	}
}
