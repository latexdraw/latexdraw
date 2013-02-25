package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines the SVG tag <code>text</code>.<br>
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
 * 11/05/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 0.1
 */
public class SVGTextElement extends SVGElement {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 * @param owner The owner document.
	 */
	public SVGTextElement(final SVGDocument owner) {
		super(owner);
		setNodeName(SVGElements.SVG_TEXT);
	}

	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGTextElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}



	/**
	 * @return The text of the SVG text element.
	 * @since 0.1
	 */
	public String getText() {
		NodeList nl = getChildNodes();
		int i, size = nl.getLength();
		StringBuilder buf = new StringBuilder();


		for(i=0; i<size; i++)
			if(nl.item(i) instanceof SVGText)
				buf.append(((SVGText)nl.item(i)).getData());

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



	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getX() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_X);
		double x;

		try { x = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { x = 0; }

		return x;
	}



	/**
	 * @return The value of the Y attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getY() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_Y);
		double y;

		try { y = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { y = 0; }

		return y;
	}



	/**
	 * @return The value of the dx attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getDX() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_DX);
		double dx;

		try { dx = v==null ? 0 : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { dx = 0; }

		return dx;
	}



	/**
	 * @return The value of the dy attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getDY() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_DY);
		double dy;

		try { dy = v==null ? 0 : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { dy = 0; }

		return dy;
	}
}
