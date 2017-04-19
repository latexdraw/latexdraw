/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.parsers.svg;

import java.text.ParseException;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>pattern</code>.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class SVGPatternElement extends SVGElement {
	/**
	 * See {@link SVGElement#SVGElement(Node, SVGElement)}.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGPatternElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates a new empty SVG Pattern element.
	 * @param doc The owner document.
	 */
	public SVGPatternElement(final SVGDocument doc) {
		super(doc);
		setNodeName(SVGElements.SVG_PATTERN);
	}


	@Override
	public boolean checkAttributes() {
		return !(getWidth()<0. || getHeight()<0.);
	}



	@Override
	public boolean enableRendering() {
        return !(MathUtils.INST.equalsDouble(getWidth(), 0.) || MathUtils.INST.equalsDouble(getHeight(), 0.));
    }


	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getX() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_X);
		double x;

		try { x = v==null ? 0. : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(final ParseException e) { x = 0.; }

		return x;
	}



	/**
	 * @return The value of the Y attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getY() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_Y);
		double y;

		try { y = v==null ? 0. : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(final ParseException e) { y = 0.; }

		return y;
	}



	/**
	 * @return The value of the <code>width</code> attribute.
	 * @since 0.1
	 */
	public double getWidth() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_WIDTH);
		double width;

		try { width = v==null ? 0. : new SVGLengthParser(v).parseLength().getValue(); }
		catch(final ParseException e) { width = 0.; }

		return width;
	}


	/**
	 * @return The value of the <code>height</code> attribute.
	 * @since 0.1
	 */
	public double getHeight() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_HEIGHT);
		double height;

		try { height = v==null ? 0. : new SVGLengthParser(v).parseLength().getValue(); }
		catch(final ParseException e) { height = 0.; }

		return height;
	}



	/**
	 * @return The coordinate system for the contents of the 'pattern'.
	 * @since 0.1
	 */
	public String getPatternContentUnits() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_PATTERN_CONTENTS_UNITS);

		return v==null || !SVGAttributes.SVG_UNITS_VALUE_OBJ.equals(v) &&
				!SVGAttributes.SVG_UNITS_VALUE_USR.equals(v) ? SVGAttributes.SVG_UNITS_VALUE_USR : v;
	}



	/**
	 * @return The coordinate system for attributes x, y, width and height.
	 * @since 0.1
	 */
	public String getPatternUnits() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_PATTERN_UNITS);

		return v==null || !SVGAttributes.SVG_UNITS_VALUE_OBJ.equals(v) &&
				!SVGAttributes.SVG_UNITS_VALUE_USR.equals(v) ? SVGAttributes.SVG_UNITS_VALUE_OBJ : v;
	}




	/**
	 * @return The g element of the pattern if there is a one (used to define if there is hatching).
	 * @since 0.1
	 */
	protected SVGGElement getGElement() {
		int i=0;
		final int size = children.getLength();
		SVGGElement g = null;

		while(g==null && i<size)
			if(children.getNodes().get(i) instanceof SVGGElement)
				g = (SVGGElement)children.getNodes().get(i);
			else
				i++;

		return g;
	}



	/**
	 * @return The background colour of the pattern if there is a one.
	 * @since 0.1
	 */
	public Color getBackgroundColor() {
		int i=0;
		final int size = children.getLength();
		SVGRectElement rec = null;

		while(rec==null && i<size)
			if(children.getNodes().get(i) instanceof SVGRectElement)
				rec = (SVGRectElement)children.getNodes().get(i);
			else
				i++;

		return rec==null ? null : CSSColors.INSTANCE.getRGBColour(rec.getFill());
	}


	/**
	 * @return The separation between each line of the hatching if there is an hatching (based on the two first lines of the possible
	 * hatching).
	 * @since 2.0.0
	 */
	public double getHatchingSep() {
		final SVGGElement g = getGElement();

		if(g==null)
			return Double.NaN;

		SVGPathElement p = null;
		int i = 0;
		final int size = g.children.getLength();
		int j=-1;

		while(p==null && i<size)
			if(g.children.getNodes().get(i) instanceof SVGPathElement)
				p = (SVGPathElement)g.children.getNodes().get(i);
			else
				i++;

		if(p==null)
			return Double.NaN;

		final String path = p.getPathData();
		i = path.indexOf('L');

		if(i!=-1)
			j = path.indexOf('L', i+1);

		if(i==-1 || j==-1)
			return Double.NaN;

		final String[] coord1 = path.substring(i, j).split(" ");//$NON-NLS-1$
		final String[] coord2 = path.substring(j).split(" ");//$NON-NLS-1$

		if(coord1.length<3 || coord2.length<3)
			return Double.NaN;

		try {
			final float l1x;
            final float l2x;
            final float l1y;
            final float l2y;

            l1x = Double.valueOf(coord1[1]).floatValue();
			l1y = Double.valueOf(coord1[2]).floatValue();
			l2x = Double.valueOf(coord2[1]).floatValue();
			l2y = Double.valueOf(coord2[2]).floatValue();

			if(MathUtils.INST.equalsDouble(l1x, l2x))
				return Math.abs(l1y-l2y)-getHatchingStrokeWidth();

			return Math.abs(l1x-l2x)-getHatchingStrokeWidth();
		}
		catch(final NumberFormatException e) { /* To ignore. */ }

		return Double.NaN;
	}


	/**
	 * @return The colour of the possible hatching if there is a one.
	 * @since 0.1
	 */
	public Color getHatchingColor() {
		final SVGGElement g = getGElement();

		return g==null ? null : CSSColors.INSTANCE.getRGBColour(g.getAttribute(getUsablePrefix()+SVGAttributes.SVG_STROKE));
	}



	/**
	 * @return The stroke width of the possible hatching.
	 * @since 0.1
	 */
	public double getHatchingStrokeWidth() {
		final SVGGElement g = getGElement();
		final String code = g==null ? null : g.getAttribute(getUsablePrefix()+SVGAttributes.SVG_STROKE_WIDTH);

		try { return code==null ? Double.NaN : new SVGLengthParser(code).parseLength().getValue(); }
		catch(final ParseException e){ return Double.NaN; }
	}
}
