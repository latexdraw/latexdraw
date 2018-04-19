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


import java.util.Objects;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Defines a text node.
 * @author Arnaud BLOUIN
 */
public class SVGText extends SVGElement implements Text {
	public static final String TEXT_NODE_NAME = "#text"; //NON-NLS

	/** The content of the text node. */
	private String data;


	/**
	 * Creates a text node.
	 * @param text The content of the node.
	 * @param owner The owner document.
	 * @throws NullPointerException When text is null.
	 */
	public SVGText(final String text, final SVGDocument owner) {
		super();
		ownerDocument = owner;
		data = Objects.requireNonNull(text);
		setNodeName(TEXT_NODE_NAME);
	}


	@Override
	public String getNodeValue() {
		return data;
	}


	@Override
	public void appendData(final String da) {
		if(da != null) {
			setData(getData() + da);
		}
	}


	@Override
	public String getData() {
		return data;
	}


	@Override
	public int getLength() {
		return data.length();
	}


	@Override
	public short getNodeType() {
		return Node.TEXT_NODE;
	}


	@Override
	public boolean checkAttributes() {
		return true;
	}


	@Override
	public boolean enableRendering() {
		return false;
	}


	@Override
	public void setData(final String newData) {
		if(newData != null) {
			data = newData;
		}
	}


	@Override
	public String getWholeText() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public boolean isElementContentWhitespace() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Text replaceWholeText(final String content) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Text splitText(final int offset) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public void deleteData(final int offset, final int count) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public void insertData(final int offset, final String arg) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public void replaceData(final int offset, final int count, final String arg) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public String substringData(final int offset, final int count) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}
}
