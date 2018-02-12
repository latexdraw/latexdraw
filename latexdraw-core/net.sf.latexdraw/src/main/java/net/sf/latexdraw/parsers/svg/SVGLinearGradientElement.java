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

import java.awt.geom.Point2D;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.Color;
import org.w3c.dom.Node;

/**
 * Defines the SVG tag <code>linearGradient</code>.
 * @author Arnaud BLOUIN
 */
public class SVGLinearGradientElement extends SVGElement implements SVGLineParseTrait {
	/**
	 * {@link SVGElement#SVGElement(Node, SVGElement)}
	 */
	public SVGLinearGradientElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}


	/**
	 * Creates an simple SVGLinearGradientElement with the owner document.
	 * @param owner The owner document.
	 * @throws IllegalArgumentException If owner is null.
	 * @since 0.1
	 */
	public SVGLinearGradientElement(final SVGDocument owner) {
		super(owner);
		setNodeName(SVGElements.SVG_LINEAR_GRADIENT);
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
	 * @return The coordinate system for attributes x1, y1, x2, and y2.
	 */
	public String getGradientUnits() {
		final String v = getAttribute(getUsablePrefix() + SVGAttributes.SVG_GRADIENT_UNITS);
		return (v == null || (!SVGAttributes.SVG_UNITS_VALUE_OBJ.equals(v) && !SVGAttributes.SVG_UNITS_VALUE_USR.equals(v))) ? SVGAttributes.SVG_UNITS_VALUE_OBJ : v;
	}


	/**
	 * @return The start colour of the gradient or null.
	 */
	public Color getStartColor() {
		final SVGNodeList nl = getChildren(SVGElements.SVG_STOP);

		if(nl.getLength() == 2) {
			if(((SVGStopElement) nl.item(0)).getOffset() < ((SVGStopElement) nl.item(1)).getOffset()) {
				return ((SVGStopElement) nl.item(0)).getStopColor();
			}
			return ((SVGStopElement) nl.item(1)).getStopColor();
		}

		if(nl.getLength() == 3) {
			final SVGStopElement s1 = (SVGStopElement) nl.item(0);
			final SVGStopElement s2 = (SVGStopElement) nl.item(1);
			final SVGStopElement s3 = (SVGStopElement) nl.item(2);

			if(s1.getOffset() < s2.getOffset()) {
				return s1.getOffset() < s3.getOffset() ? s1.getStopColor() : s3.getStopColor();
			}

			return s2.getOffset() < s3.getOffset() ? s2.getStopColor() : s3.getStopColor();
		}

		return null;
	}


	/**
	 * @return The end colour of the gradient or null.
	 */
	public Color getEndColor() {
		final SVGNodeList nl = getChildren(SVGElements.SVG_STOP);

		if(nl.getLength() == 2) {
			if(((SVGStopElement) nl.item(0)).getOffset() > ((SVGStopElement) nl.item(1)).getOffset()) {
				return ((SVGStopElement) nl.item(0)).getStopColor();
			}
			return ((SVGStopElement) nl.item(1)).getStopColor();
		}

		if(nl.getLength() == 3) {
			final SVGStopElement s1 = (SVGStopElement) nl.item(0);
			final SVGStopElement s2 = (SVGStopElement) nl.item(1);
			final SVGStopElement s3 = (SVGStopElement) nl.item(2);

			if(s1.getOffset() > s2.getOffset()) {
				return s1.getOffset() < s3.getOffset() ? s1.getStopColor() : s3.getStopColor();
			}

			return s2.getOffset() < s3.getOffset() ? s2.getStopColor() : s3.getStopColor();
		}

		return null;
	}


	/**
	 * @return The middle point of the gradient (between 0 and 1 inclued) or NaN.
	 */
	public double getMiddlePoint() {
		final SVGNodeList nl = getChildren(SVGElements.SVG_STOP);

		if(nl.getLength() == 2) {
			return Math.max(((SVGStopElement) nl.item(0)).getOffset(), ((SVGStopElement) nl.item(1)).getOffset());
		}

		if(nl.getLength() == 3) {
			final SVGStopElement s1 = (SVGStopElement) nl.item(0);
			final SVGStopElement s2 = (SVGStopElement) nl.item(1);
			final SVGStopElement s3 = (SVGStopElement) nl.item(2);

			if(s1.getOffset() > s2.getOffset()) {
				return Math.min(s1.getOffset(), s3.getOffset());
			}

			return Math.min(s2.getOffset(), s3.getOffset());
		}

		return Double.NaN;
	}


	/**
	 * @return The angle of the gradient in radian or NaN.
	 */
	public double getAngle() {
		final double x1 = getX1();
		final double x2 = getX2();
		final double y1 = getY1();
		final double y2 = getY2();
		double angle = Double.NaN;

		if(MathUtils.INST.equalsDouble(x1, x2)) {
			angle = SVGAttributes.SVG_UNITS_VALUE_OBJ.equals(getGradientUnits()) ? Math.PI / 2d : 0d;
		}else {
			if(MathUtils.INST.equalsDouble(y1, y2)) {
				angle = Math.PI;
			}
		}

		if(Double.isNaN(angle)) {
			final Point2D.Double c = new Point2D.Double((x1 + x2) / 2d, (y1 + y2) / 2d);
			angle = Math.asin(c.distance(x1, c.y) / c.distance(x1, y1));
		}

		return angle % (2d * Math.PI);
	}
}
