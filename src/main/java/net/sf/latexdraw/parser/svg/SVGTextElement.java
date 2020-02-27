/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.parser.svg;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines the SVG tag <code>text</code>.
 * @author Arnaud BLOUIN
 */
public class SVGTextElement extends SVGElement implements SVGRectParseTrait {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 * @param owner The owner document.
	 */
	public SVGTextElement(final SVGDocument owner) {
		super(owner);
		setNodeName(SVGElements.SVG_TEXT);
	}

	public SVGTextElement(final Node n, final SVGElement p) {
		super(n, p);
	}


	/**
	 * @return The text of the SVG text element.
	 */
	public @NotNull String getText() {
		final NodeList nl = getChildNodes();
		int i;
		final int size = nl.getLength();
		final StringBuilder buf = new StringBuilder();

		for(i = 0; i < size; i++) {
			if(nl.item(i) instanceof SVGText) {
				buf.append(((SVGText) nl.item(i)).getData());
			}
		}

		return buf.toString();
	}


	@Override
	public boolean checkAttributes() {
		return true;
	}


	@Override
	public boolean enableRendering() {
		return true;
	}


	@Override
	public boolean isDimensionsRequired() {
		return false;
	}
}
