package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>ciricle</code>.<br>
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
public class SVGCircleElement extends SVGElement {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGCircleElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates a circle with a radius equal to 0.
	 * @param doc The owner document.
	 * @since 0.1
	 */
	public SVGCircleElement(final SVGDocument doc) {
		super(doc);

		setNodeName(SVGElements.SVG_CIRCLE);
		setAttribute(SVGAttributes.SVG_R, "0");//$NON-NLS-1$
	}



	/**
	 * Creates an SVG circle element.
	 * @param cx The X-centre coordinate.
	 * @param cy The Y-centre coordinate.
	 * @param r The radius of the circle.
	 * @param owner The document owner.
	 * @throws IllegalArgumentException If owner is null or if a given value is not valid.
	 * @since 0.1
	 */
	public SVGCircleElement(final double cx, final double cy, final double r, final SVGDocument owner) {
		super(owner);

		setAttribute(SVGAttributes.SVG_CX, String.valueOf(cx));
		setAttribute(SVGAttributes.SVG_CY, String.valueOf(cy));
		setAttribute(SVGAttributes.SVG_R, String.valueOf(r));
		setNodeName(SVGElements.SVG_CIRCLE);
		ownerDocument = owner;

		if(!checkAttributes())
			throw new IllegalArgumentException();
	}


	/**
	 * @return The x-axis coordinate of the centre of the circle (0 if there it does not exist or it is not a coordinate).
	 * @since 0.1
	 */
	public double getCx() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_CX);
		double cx;

		try { cx = v==null ? 0. : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { cx = 0.; }

		return cx;
	}



	/**
	 * @return The y-axis coordinate of the centre of the circle (0 if there it does not exist or it is not a coordinate).
	 * @since 0.1
	 */
	public double getCy() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_CY);
		double cy;

		try { cy = v==null ? 0. : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(ParseException e) { cy = 0.; }

		return cy;
	}



	/**
	 * @return The radius of the circle (NaN if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getR() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_R);
		double r;

		try { r = v==null ? Double.NaN : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { r = Double.NaN; }

		return r;
	}



	/**
	 * Sets the X-coordinate of the circle.
	 * @param cx The new X-coordinate of the circle.
	 * @since 0.1
	 */
	public void setCx(final double cx) {
		setAttribute(getUsablePrefix()+SVGAttributes.SVG_CX, String.valueOf(cx));
	}




	/**
	 * Sets the Y-coordinate of the circle.
	 * @param cy The new Y-coordinate of the circle.
	 * @since 0.1
	 */
	public void setCy(final double cy) {
		setAttribute(getUsablePrefix()+SVGAttributes.SVG_CY, String.valueOf(cy));
	}



	/**
	 * Sets the radius of the circle.
	 * @param width The new radius of the circle.
	 * @since 0.1
	 */
	public void setR(final double width) {
		if(width>=0.)
			setAttribute(getUsablePrefix()+SVGAttributes.SVG_R, String.valueOf(width));
	}


	@Override
	public boolean checkAttributes() {
		double r = getR();

		return !Double.isNaN(r) && r>=0.;
	}



	@Override
	public boolean enableRendering() {
		final double r = getR();
		return r>0. || r<0. ;
	}
}
