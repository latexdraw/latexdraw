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
package net.sf.latexdraw.view.svg;

import java.awt.geom.Point2D;
import java.util.List;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.parsers.SVGPointsParser;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.jfx.JFXToSVG;
import net.sf.latexdraw.view.jfx.ViewFactory;

/**
 * An SVG generator for dot shapes.
 * @author Arnaud BLOUIN
 */
class SVGDot extends SVGShape<IDot> {
	/**
	 * Creates a generator of SVG dots.
	 * @param dot The dot used for the generation.
	 * @throws IllegalArgumentException If dot is null.
	 */
	protected SVGDot(final IDot dot) {
		super(dot);
	}


	/**
	 * Creates a dot from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	protected SVGDot(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));

		if(elt == null) {
			throw new IllegalArgumentException();
		}

		String v = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_SIZE);
		final SVGElement main = getLaTeXDrawElement(elt, null);

		try {
			shape.setDotStyle(DotStyle.getStyle(elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_DOT_SHAPE)));
		}catch(final IllegalArgumentException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		if(v != null) {
			try {
				shape.setDiametre(Double.parseDouble(v));
			}catch(final NumberFormatException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}

		v = elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_POSITION);

		final List<Point2D> pos = SVGPointsParser.getPoints(v);

		if(pos != null && !pos.isEmpty()) {
			shape.setPosition(pos.get(0).getX(), pos.get(0).getY());
		}

		setSVGLatexdrawParameters(elt);
		setSVGParameters(main);

		if(withTransformation) {
			applyTransformations(elt);
		}

		if(!shape.isFillable() && shape.isFilled()) {
			shape.setLineColour(CSSColors.INSTANCE.getRGBColour(main.getFill()));
		}
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc == null) {
			return null;
		}

		final SVGElement root = new SVGGElement(doc);

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_DOT);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_SIZE, String.valueOf(shape.getDiametre()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_DOT_SHAPE, shape.getDotStyle().getPSTToken());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION, shape.getPosition().getX() + " " + shape.getPosition().getY());

		ViewFactory.INSTANCE.createView(shape).ifPresent(vdot -> JFXToSVG.INSTANCE.shapesToElements(vdot.getChildren(), doc).
			forEach(elt -> root.appendChild(elt)));

		setSVGRotationAttribute(root);

		return root;
	}
}
