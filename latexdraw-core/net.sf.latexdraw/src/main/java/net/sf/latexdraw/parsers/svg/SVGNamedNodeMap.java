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
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Defines an SVG named node map.
 * @author Arnaud BLOUIN
 */
public class SVGNamedNodeMap implements NamedNodeMap, Cloneable {
	/** The set of nodes. */
	private List<SVGAttr> nnm;

	/**
	 * The constructor by default.
	 */
	public SVGNamedNodeMap() {
		super();
		nnm = new ArrayList<>();
	}

	@Override
	public int getLength() {
		return nnm == null ? 0 : nnm.size();
	}


	@Override
	public Node getNamedItem(final String name) {
		if(nnm == null || name == null) {
			return null;
		}

		int i = 0;
		final int size = getLength();
		boolean found = false;

		while(i < size && !found) {
			if(nnm.get(i).getName().equals(name)) {
				found = true;
			}else {
				i++;
			}
		}

		if(found) {
			return nnm.get(i);
		}

		return null;
	}


	@Override
	public Node item(final int index) {
		return nnm == null || index < 0 || index >= getLength() ? null : nnm.get(index);
	}


	@Override
	public Node removeNamedItem(final String name) {
		if(name == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "name is null");
		}

		int i = 0;
		final int size = getLength();
		boolean found = false;

		while(i < size && !found) {
			if(nnm.get(i).getName().equals(name)) {
				found = true;
			}else {
				i++;
			}
		}

		if(found) {
			return nnm.remove(i);
		}

		throw new DOMException(DOMException.NOT_FOUND_ERR, name);
	}


	@Override
	public Node setNamedItem(final Node node) {
		if(!(node instanceof SVGAttr)) {
			return null;
		}

		final Node attr = getNamedItem(node.getNodeName());

		if(attr == null) {
			nnm.add((SVGAttr) node);
		}else {
			if(attr == node) {
				return null;
			}

			final int index = nnm.indexOf(attr);
			nnm.remove(attr);
			nnm.add(index, (SVGAttr) node);
		}

		return attr;
	}


	@Override
	public Object clone() {
		try {
			final SVGNamedNodeMap clone = (SVGNamedNodeMap) super.clone();
			clone.nnm = new ArrayList<>();

			for(final SVGAttr attr : nnm) {
				clone.nnm.add((SVGAttr) attr.cloneNode(false));
			}
			return clone;
		}catch(final CloneNotSupportedException e) {
			return null;
		}
	}


	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder().append('{');

		for(final SVGAttr e : nnm) {
			str.append(e).append(',').append(' ');
		}

		return str.append('}').toString();
	}


	@Override
	public Node getNamedItemNS(final String namespaceURI, final String localName) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Node setNamedItemNS(final Node arg) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Node removeNamedItemNS(final String namespaceURI, final String localName) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}


	/**
	 * @return the attributes.
	 */
	public List<SVGAttr> getAttributes() {
		return nnm;
	}


	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}

		final SVGNamedNodeMap map = (SVGNamedNodeMap) obj;
		boolean ok = true;
		int i;
		final int size = getLength();

		if(size != map.getLength()) {
			return false;
		}

		for(i = 0; i < size && ok; i++) {
			ok = item(i).isEqualNode(map.item(i));
		}

		return ok;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ getLength();
	}
}
