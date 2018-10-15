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
	 * @return The start colour of the gradient or null.
	 */
	public Color getStartColor() {
		final SVGNodeList nl = getChildren(SVGElements.SVG_STOP);

		if(nl.getLength() == 2) {
			final SVGStopElement stop = ((SVGStopElement) nl.item(0)).getOffset() < ((SVGStopElement) nl.item(1)).getOffset() ?
				(SVGStopElement) nl.item(0) : (SVGStopElement) nl.item(1);
			return stop.getStopColor().newColorWithOpacity(stop.getOpacity(SVGAttributes.SVG_STOP_OPACITY));
		}

		return null;
	}


	/**
	 * @return The end colour of the gradient or null.
	 */
	public Color getEndColor() {
		final SVGNodeList nl = getChildren(SVGElements.SVG_STOP);

		if(nl.getLength() == 2) {
			final SVGStopElement stop = ((SVGStopElement) nl.item(0)).getOffset() > ((SVGStopElement) nl.item(1)).getOffset() ?
				(SVGStopElement) nl.item(0) : (SVGStopElement) nl.item(1);
			return stop.getStopColor().newColorWithOpacity(stop.getOpacity(SVGAttributes.SVG_STOP_OPACITY));
		}

		return null;
	}


	/**
	 * @return The middle point of the gradient (between 0 and 1 included) or NaN.
	 */
	public double getMiddlePoint() {
		final SVGNodeList nl = getChildren(SVGElements.SVG_STOP);

		if(nl.getLength() == 2) {
			return Math.max(((SVGStopElement) nl.item(0)).getOffset(), ((SVGStopElement) nl.item(1)).getOffset());
		}

		return Double.NaN;
	}


	/**
	 * @return The angle of the gradient in radian or NaN.
	 */
	public double getAngle() {
		return new SVGTransformList(getAttribute(getUsablePrefix() + SVGAttributes.SVG_GRADIENT_TRANSFORM)).
			stream().filter(tr -> tr.isRotation()).findFirst().
			map(svgTransform -> Math.toRadians(svgTransform.getRotationAngle())).orElse(0d);
	}
}
