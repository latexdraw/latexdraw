/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGGElement;
import net.sf.latexdraw.parser.svg.SVGPathElement;
import net.sf.latexdraw.parser.svg.SVGPolygonElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * A SVG generator for a polygon.
 * @author Arnaud BLOUIN
 */
class SVGPolygon extends SVGModifiablePointsShape<Polygon> {
	/**
	 * Creates a generator for IPolygon.
	 * @param polygon The source polygon used to generate the SVG element.
	 */
	SVGPolygon(final Polygon polygon) {
		super(polygon);
	}


	/**
	 * Creates a latexdraw shape from the given SVG element.
	 * @param elt The source SVG element.
	 * @throws IllegalArgumentException If the given SVG element is null.
	 */
	SVGPolygon(final SVGPathElement elt) {
		super(ShapeFactory.INST.createPolygon(getLinePointsFromSVGPathElement(elt)));
		if(elt == null) {
			throw new IllegalArgumentException();
		}
		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates a polygon from an SVG polygon element.
	 * @param elt The source element.
	 */
	SVGPolygon(final SVGPolygonElement elt) {
		this(ShapeFactory.INST.createPolygon(getPointsFromSVGElement(elt)));
		setSVGParameters(elt);
		applyTransformations(elt);
	}

	/**
	 * Creates a polygon from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	SVGPolygon(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createPolygon(getPointsFromSVGElement(getLaTeXDrawElement(elt, null))));

		final SVGElement shapeElt = getLaTeXDrawElement(elt, null);
		setSVGLatexdrawParameters(elt);
		setSVGParameters(shapeElt);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		setRotationAngle(shapeElt);

		if(withTransformation) {
			applyTransformations(elt);
		}
	}

	@Override
	SVGElement toSVG(final @NotNull SVGDocument doc) {
		final SVGElement root = new SVGGElement(doc);
		final StringBuilder pointsBuilder = new StringBuilder();

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_POLYGON);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		for(final Point pt : shape.getPoints()) {
			pointsBuilder.append(pt.getX()).append(',').append(pt.getY()).append(' ');
		}

		final String points = pointsBuilder.toString();

		if(shape.hasShadow()) {
			final SVGPolygonElement shad = new SVGPolygonElement(doc);
			shad.setPoints(points);
			setSVGShadowAttributes(shad, true);
			root.appendChild(shad);
		}

		if(shape.hasShadow() && !PSTricksConstants.LINE_NONE_STYLE.equals(shape.getLineStyle().getLatexToken())) {
			// The background of the borders must be filled is there is a shadow.
			final SVGPolygonElement elt = new SVGPolygonElement(doc);
			elt.setPoints(points);
			setSVGBorderBackground(elt, root);
		}

		final SVGPolygonElement elt = new SVGPolygonElement(doc);
		elt.setPoints(points);
		root.appendChild(elt);
		setSVGAttributes(doc, elt, true);
		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

		if(shape.hasDbleBord()) {
			final SVGPolygonElement dblBord = new SVGPolygonElement(doc);
			dblBord.setPoints(points);
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}

		return root;
	}
}
