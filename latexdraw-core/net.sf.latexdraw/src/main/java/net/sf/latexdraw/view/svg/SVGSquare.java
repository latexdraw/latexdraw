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
package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGRectElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * An SVG generator for a square.
 * @author Arnaud BLOUIN
 */
class SVGSquare extends SVGRectangular<ISquare> {
	/**
	 * Creates an SVG generator for squares.
	 * @param square The source square to convert in SVG.
	 * @since 3.0
	 */
	protected SVGSquare(final ISquare square) {
		super(square);
	}


	/**
	 * Creates a square from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected SVGSquare(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createSquare());
		initRectangle(elt, withTransformation);
	}


	@Override
	protected void setSVGRectParameters(final SVGRectElement elt) {
		if(elt == null) return;

		setSVGParameters(elt);

		final double rx = elt.getRx();
		final double gap = getPositionGap();

		shape.setPosition(elt.getX() + gap / 2d, elt.getY() + elt.getHeight() - gap / 2d);
		shape.setWidth(elt.getWidth() - gap);
		shape.setLineArc(2d * rx / (shape.getWidth() - (shape.hasDbleBord() ? shape.getDbleBordSep() + shape.getThickness() : 0d)));
	}


	@Override
	public SVGElement toSVG(final SVGDocument document) {
		if(document == null || document.getFirstChild().getDefs() == null) {
			throw new IllegalArgumentException();
		}

		final double gap = getPositionGap();
		final IPoint tl = shape.getTopLeftPoint();
		final IPoint br = shape.getBottomRightPoint();
		SVGElement elt;
		final SVGElement root = new SVGGElement(document);
		final double width = Math.max(1d, br.getX() - tl.getX() + gap);
		final double x = tl.getX() - gap / 2d;
		final double y = tl.getY() - gap / 2d;

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_SQUARE);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		setShadowSVGRect(root, x, y, width, width, document);

		if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE)) {
			// The background of the borders must be filled is there is a shadow.
			elt = new SVGRectElement(x, y, width, width, document);
			setSVGBorderBackground(elt, root);
			setSVGRoundCorner(elt);
		}

		elt = new SVGRectElement(x, y, width, width, document);
		root.appendChild(elt);
		setSVGAttributes(document, elt, true);
		setSVGRoundCorner(elt);
		setDbleBordSVGRect(root, x, y, width, width, document);
		setSVGRotationAttribute(root);

		return root;
	}
}
