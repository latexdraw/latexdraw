package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;

import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;

import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>rect</code>.<br>
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
 * 09/11/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 0.1
 */
public class SVGRectElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGRectElement(final Node node, final SVGElement elt) throws MalformedSVGDocument {
		super(node, elt);
	}



	/**
	 * Creates an SVG rect element.
	 * @param x The top-left X coordinate.
	 * @param y The top-left Y coordinate.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 * @param owner The document owner.
	 * @throws IllegalArgumentException If owner is null or if a given value is not valid.
	 * @since 0.1
	 */
	public SVGRectElement(final double x, final double y, final double w, final double h, final SVGDocument owner) {
		super(owner);

		setAttribute(SVGAttributes.SVG_X, String.valueOf(x));
		setAttribute(SVGAttributes.SVG_Y, String.valueOf(y));
		setAttribute(SVGAttributes.SVG_WIDTH, String.valueOf(w));
		setAttribute(SVGAttributes.SVG_HEIGHT, String.valueOf(h));
		setNodeName(SVGElements.SVG_RECT);
		ownerDocument = owner;

		if(!checkAttributes())
			throw new IllegalArgumentException();
	}



	/**
	 * Creates an SVG rectangle with width=height=0.
	 * @param doc The owner document.
	 * @since 0.1
	 */
	public SVGRectElement(final SVGDocument doc) {
		super(doc);

		setNodeName(SVGElements.SVG_RECT);
		setAttribute(SVGAttributes.SVG_WIDTH, "0");//$NON-NLS-1$
		setAttribute(SVGAttributes.SVG_HEIGHT, "0");//$NON-NLS-1$
	}



	@Override
	public boolean checkAttributes() {
		double vWidth	= getWidth();
		double vHeight	= getHeight();
		double vrx		= getRx();
		double vry		= getRy();

		if(Double.isNaN(vWidth) || Double.isNaN(vHeight) || vWidth<0 || vHeight<0 || vrx<0 || vry<0)
			return false;

		return true;
	}




	@Override
	public boolean enableRendering() {
		if(getWidth()==0 || getHeight()==0)
			return false;

		return true;
	}


	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 * The x-axis coordinate of the side of the rectangle which has the smaller x-axis coordinate value in the current user coordinate system.
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
	 * The y-axis coordinate of the side of the rectangle which has the smaller y-axis coordinate value in the current user coordinate system.
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
	 * @return The value of the <code>width</code> attribute. The width of the rectangle.
	 * @since 0.1
	 */
	public double getWidth() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_WIDTH);
		double width;

		try { width = v==null ? Double.NaN : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { width = Double.NaN; }

		return width;
	}


	/**
	 * @return The value of the <code>height</code> attribute. The height of the rectangle.
	 * @since 0.1
	 */
	public double getHeight() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_HEIGHT);
		double height;

		try { height = v==null ? Double.NaN : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { height = Double.NaN; }

		return height;
	}


	/**
	 * @return The value of the rx attribute (0 if there it does not exist or it is not a length).
	 * For rounded rectangles, the x-axis radius of the ellipse used to round off the corners of the rectangle.
	 * @since 0.1
	 */
	public double getRx() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_RX);
		double rx;

		try { rx = v==null ? 0 : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { rx = 0; }

		return rx;
	}


	/**
	 * @return The value of the ry attribute (0 if there it does not exist or it is not a length).
	 * For rounded rectangles, the y-axis radius of the ellipse used to round off the corners of the rectangle.
	 * @since 0.1
	 */
	public double getRy() {
		String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_RY);
		double ry;

		try { ry = v==null ? 0 : new SVGLengthParser(v).parseLength().getValue(); }
		catch(ParseException e) { ry = 0; }

		return ry;
	}
}
