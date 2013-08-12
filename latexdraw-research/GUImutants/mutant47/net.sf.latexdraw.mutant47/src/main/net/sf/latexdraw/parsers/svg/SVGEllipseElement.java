package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>ellipse</code>.<br>
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
public class SVGEllipseElement extends SVGElement {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGEllipseElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates an SVG ellipse element.
	 * @param cx The X-centre coordinate.
	 * @param cy The Y-centre coordinate.
	 * @param rx The X-radius of the ellipse.
	 * @param ry The Y-radius of the ellipse.
	 * @param owner The document owner.
	 * @throws IllegalArgumentException If owner is null or if a given value is not valid.
	 * @since 0.1
	 */
	public SVGEllipseElement(final double cx, final double cy, final double rx, final double ry, final SVGDocument owner) {
		super(owner);

		setAttribute(SVGAttributes.SVG_CX, String.valueOf(cx));
		setAttribute(SVGAttributes.SVG_CY, String.valueOf(cy));
		setAttribute(SVGAttributes.SVG_RX, String.valueOf(rx));
		setAttribute(SVGAttributes.SVG_RY, String.valueOf(ry));
		setNodeName(SVGElements.SVG_ELLIPSE);
		ownerDocument = owner;

		if(!checkAttributes())
			throw new IllegalArgumentException();
	}


	/**
	 * Creates an empty ellipse.
	 * @param doc The owner document.
	 * @since 0.1
	 */
	public SVGEllipseElement(final SVGDocument doc) {
		super(doc);

		setNodeName(SVGElements.SVG_ELLIPSE);
		setAttribute(SVGAttributes.SVG_RX, "0");//$NON-NLS-1$
		setAttribute(SVGAttributes.SVG_RY, "0");//$NON-NLS-1$
	}



	/**
	 * @return The x-axis coordinate of the centre of the ellipse (0 if there it does not exist or it is not a coordinate).
	 * @since 0.1
	 */
	public double getCx() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_CX);
		double cx;

		try { cx = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { cx = 0; }

		return cx;
	}


	/**
	 * @return The y-axis coordinate of the centre of the ellipse (0 if there it does not exist or it is not a coordinate).
	 * @since 0.1
	 */
	public double getCy() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_CY);
		double cy;

		try { cy = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { cy = 0; }

		return cy;
	}



	/**
	 * @return The x-axis radius of the ellipse (NaN if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getRx() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_RX);
		double rx;

		try { rx = v==null ? Double.NaN : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { rx = Double.NaN; }

		return rx;
	}



	/**
	 * @return The y-axis radius of the ellipse (NaN if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getRy() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_RY);
		double ry;

		try { ry = v==null ? Double.NaN : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { ry = Double.NaN; }

		return ry;
	}



	@Override
	public boolean checkAttributes() {
		double rx = getRx(), ry = getRy();

		return !Double.isNaN(rx) && !Double.isNaN(ry) && rx>=0 && ry>=0;
	}



	@Override
	public boolean enableRendering() {
		return getRx()!=0 && getRy()!=0 ;
	}
}
