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
package net.sf.latexdraw.parsers.svg;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NodeList;

/**
 * Defines an SVG node list.
 * @author Arnaud BLOUIN
 */
public class SVGNodeList implements NodeList {
	/** The nodes of the list. */
	private final List<SVGElement> nodes;


	/**
	 * The constructor by default.
	 */
	public SVGNodeList() {
		super();
		nodes = new ArrayList<>();
	}


	@Override
	public int getLength() {
		return nodes == null ? 0 : nodes.size();
	}


	@Override
	public SVGElement item(final int index) {
		return nodes == null || index < 0 || index >= nodes.size() ? null : nodes.get(index);
	}


	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder().append('{');

		for(final SVGElement e : nodes) {
			str.append(e).append(", ");
		}

		return str.append('}').toString();
	}


	/**
	 * @return the nodes.
	 */
	public List<SVGElement> getNodes() {
		return nodes;
	}
}
