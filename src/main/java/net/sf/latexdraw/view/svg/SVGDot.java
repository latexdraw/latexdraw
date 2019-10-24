/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
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
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.parser.svg.CSSColors;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGGElement;
import net.sf.latexdraw.parser.svg.SVGParserUtils;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.jfx.JFXToSVG;
import net.sf.latexdraw.view.jfx.ViewFactory;
import org.jetbrains.annotations.NotNull;

/**
 * An SVG generator for dot shapes.
 * @author Arnaud BLOUIN
 */
class SVGDot extends SVGShape<Dot> {
	private final ViewFactory viewFactory;

	/**
	 * Creates a generator of SVG dots.
	 * @param dot The dot used for the generation.
	 * @throws IllegalArgumentException If dot is null.
	 */
	SVGDot(final Dot dot, final ViewFactory viewFactory) {
		super(dot);
		this.viewFactory = viewFactory;
	}


	/**
	 * Creates a dot from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	SVGDot(final SVGGElement elt, final boolean withTransformation, final ViewFactory viewFactory) {
		this(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()), viewFactory);

		if(elt == null) {
			throw new IllegalArgumentException();
		}

		final SVGElement main = getLaTeXDrawElement(elt, null);

		try {
			shape.setDotStyle(DotStyle.getStyle(elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_DOT_SHAPE)));
		}catch(final IllegalArgumentException ignore) {
		}

		try {
			shape.setDiametre(Double.parseDouble(elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_SIZE)));
		}catch(final NumberFormatException ignore) {
		}

		final List<Point2D> pos = SVGParserUtils.INSTANCE.parsePoints(elt.getAttribute(elt.getUsablePrefix(LNamespace.LATEXDRAW_NAMESPACE_URI) + LNamespace.XML_POSITION));

		if(!pos.isEmpty()) {
			shape.setPosition(pos.get(0).getX(), pos.get(0).getY());
		}

		setSVGLatexdrawParameters(elt);
		setSVGParameters(main);

		if(withTransformation) {
			applyTransformations(elt);
		}

		setFillLineColor(main);
	}

	final void setFillLineColor(final SVGElement elt) {
		if(!shape.isFillable() && shape.isFilled()) {
			final Color fillCol = CSSColors.INSTANCE.getRGBColour(elt.getFill());
			if(fillCol != null) {
				shape.setLineColour(fillCol.newColorWithOpacity(elt.getOpacity(SVGAttributes.SVG_OPACITY, SVGAttributes.SVG_FILL_OPACITY)));
			}
		}
	}

	@Override
	SVGElement toSVG(final @NotNull SVGDocument doc) {
		final SVGElement root = new SVGGElement(doc);

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_DOT);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_SIZE, String.valueOf(shape.getDiametre()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_DOT_SHAPE, shape.getDotStyle().getPSTToken());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION, shape.getPosition().getX() + " " + shape.getPosition().getY());

		viewFactory.createView(shape).ifPresent(vdot -> JFXToSVG.INSTANCE.shapesToElements(vdot.getChildren(), doc).forEach(elt -> root.appendChild(elt)));

		setSVGRotationAttribute(root);

		return root;
	}
}
