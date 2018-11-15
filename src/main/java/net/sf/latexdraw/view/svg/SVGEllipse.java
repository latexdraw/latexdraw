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

import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGEllipseElement;
import net.sf.latexdraw.parser.svg.SVGGElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * An SVG generator for an ellipse.
 * @author Arnaud BLOUIN
 */
class SVGEllipse extends SVGShape<Ellipse> {
	/**
	 * Creates a generator of SVG ellipse.
	 * @param ellipse The ellipse shape used for the generation.
	 * @throws IllegalArgumentException If ellipse is null.
	 */
	SVGEllipse(final Ellipse ellipse) {
		super(ellipse);
	}


	/**
	 * Creates an ellipse from an SVG ellipse element.
	 * @param elt The source element.
	 */
	SVGEllipse(final SVGEllipseElement elt) {
		this(ShapeFactory.INST.createEllipse());

		setSVGParameters(elt);
		setEllipseParameters(elt, 0.);
		applyTransformations(elt);
	}


	/**
	 * Creates an ellipse from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	SVGEllipse(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createEllipse());

		final SVGElement elt2 = getLaTeXDrawElement(elt, null);

		if(elt == null || !(elt2 instanceof SVGEllipseElement)) {
			throw new IllegalArgumentException();
		}

		setSVGLatexdrawParameters(elt);
		setSVGParameters(elt2);
		setEllipseParameters((SVGEllipseElement) elt2, getPositionGap());

		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation) {
			applyTransformations(elt);
		}
	}


	/**
	 * Sets the ellipse parameters.
	 * @param ellipseElt The source SVG ellipse element.
	 * @param gap The gap used to define the latexdraw ellipse.
	 */
	final void setEllipseParameters(final SVGEllipseElement ellipseElt, final double gap) {
		final double width = 2. * ellipseElt.getRx() - gap;
		final double height = 2. * ellipseElt.getRy() - gap;

		shape.setPosition(ellipseElt.getCx() - width / 2., ellipseElt.getCy() + height / 2.);
		shape.setWidth(width);
		shape.setHeight(height);
	}


	@Override
	SVGElement toSVG(final @NotNull SVGDocument doc) {
		if(doc.getFirstChild().getDefs() == null) {
			return null;
		}

		final Point tl = shape.getTopLeftPoint();
		final Point br = shape.getBottomRightPoint();
		final double tlx = tl.getX();
		final double tly = tl.getY();
		final double brx = br.getX();
		final double bry = br.getY();
		SVGElement elt;
		final SVGElement root = new SVGGElement(doc);
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_ELLIPSE);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		final double gap = getPositionGap();
		final double width = Math.max(1., (brx - tlx + gap) / 2.);
		final double height = Math.max(1., (bry - tly + gap) / 2.);
		final double x = (brx + tlx) / 2.;
		final double y = (bry + tly) / 2.;

		if(shape.hasShadow()) {
			elt = new SVGEllipseElement(x, y, width, height, doc);
			setSVGShadowAttributes(elt, true);
			root.appendChild(elt);
		}

		if(shape.hasShadow() && !PSTricksConstants.LINE_NONE_STYLE.equals(shape.getLineStyle().getLatexToken())) {
			// The background of the borders must be filled is there is a shadow.
			elt = new SVGEllipseElement(x, y, width, height, doc);
			setSVGBorderBackground(elt, root);
		}

		elt = new SVGEllipseElement(x, y, width, height, doc);
		setSVGAttributes(doc, elt, true);
		root.appendChild(elt);

		if(shape.hasDbleBord()) {
			elt = new SVGEllipseElement(x, y, width, height, doc);
			setSVGDoubleBordersAttributes(elt);
			root.appendChild(elt);
		}

		setSVGRotationAttribute(root);

		return root;
	}
}
