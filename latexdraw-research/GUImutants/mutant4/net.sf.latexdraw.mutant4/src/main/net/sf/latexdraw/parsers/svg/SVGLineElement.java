package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>line</code>.<br>
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
 * 10/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 0.1
 */
public class SVGLineElement extends SVGElement {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGLineElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates an SVGLine.
	 * @param owner The owner document.
	 * @param x1 The X-coordinate of the first point of the line.
	 * @param x2 The X-coordinate of the second point of the line.
	 * @param y1 The Y-coordinate of the first point of the line.
	 * @param y2 The Y-coordinate of the second point of the line.
	 * @throws MalformedSVGDocument If the given coordinates are not valid.
	 * @since 0.1
	 */
	public SVGLineElement(final double x1, final double y1, final double x2, final double y2, final SVGDocument owner) throws MalformedSVGDocument {
		this(owner);

		setAttribute(SVGAttributes.SVG_X1, String.valueOf(x1));
		setAttribute(SVGAttributes.SVG_X2, String.valueOf(x2));
		setAttribute(SVGAttributes.SVG_Y1, String.valueOf(y1));
		setAttribute(SVGAttributes.SVG_Y2, String.valueOf(y2));

		if(!checkAttributes())
			throw new MalformedSVGDocument();
	}



	/**
	 * Creates an SVG line (0,0 ; 0,0)
	 * @param doc The owner document.
	 * @since 0.1
	 */
	public SVGLineElement(final SVGDocument doc) {
		super(doc);
		setNodeName(SVGElements.SVG_LINE);
		setAttribute(SVGAttributes.SVG_X1, "0");//$NON-NLS-1$
		setAttribute(SVGAttributes.SVG_X2, "0");//$NON-NLS-1$
		setAttribute(SVGAttributes.SVG_Y1, "0");//$NON-NLS-1$
		setAttribute(SVGAttributes.SVG_Y2, "0");//$NON-NLS-1$
	}



	/**
	 * @return The x-axis coordinate of the start of the line (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getX1() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_X1);
		double x1;

		try { x1 = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { x1 = 0; }

		return x1;
	}


	/**
	 * @return The y-axis coordinate of the start of the line (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getY1() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_Y1);
		double y1;

		try { y1 = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { y1 = 0; }

		return y1;
	}



	/**
	 * @return The x-axis coordinate of the end of the line (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getX2() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_X2);
		double x2;

		try { x2 = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { x2 = 0; }

		return x2;
	}


	/**
	 * @return The y-axis coordinate of the end of the line (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getY2() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_Y2);
		double y2;

		try { y2 = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { y2 = 0; }

		return y2;
	}


	/**
	 * Sets the first X-coordinate of the line.
	 * @param x1 The first X-coordinate of the line.
	 * @since 2.0.0
	 */
	public void setX1(final double x1) {
		setAttribute(getUsablePrefix()+SVGAttributes.SVG_X1, String.valueOf(x1));
	}


	/**
	 * Sets the second X-coordinate of the line.
	 * @param x2 The second X-coordinate of the line.
	 * @since 2.0.0
	 */
	public void setX2(final double x2) {
		setAttribute(getUsablePrefix()+SVGAttributes.SVG_X2, String.valueOf(x2));
	}


	/**
	 * Sets the first Y-coordinate of the line.
	 * @param y1 The first Y-coordinate of the line.
	 * @since 2.0.0
	 */
	public void setY1(final double y1) {
		setAttribute(getUsablePrefix()+SVGAttributes.SVG_Y1, String.valueOf(y1));
	}


	/**
	 * Sets the second Y-coordinate of the line.
	 * @param y2 The second Y-coordinate of the line.
	 * @since 2.0.0
	 */
	public void setY2(final double y2) {
		setAttribute(getUsablePrefix()+SVGAttributes.SVG_Y2, String.valueOf(y2));
	}



	@Override
	public boolean checkAttributes() {
		return true;
	}



	@Override
	public boolean enableRendering() {
		return true;
	}
}
