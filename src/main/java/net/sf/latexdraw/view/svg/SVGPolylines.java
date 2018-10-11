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

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGLineElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.SVGPolyLineElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * An SVG generator for some joined lines.
 * @author Arnaud BLOUIN
 */
class SVGPolylines extends SVGModifiablePointsShape<IPolyline> {
	/**
	 * Creates a generator for IPolyline.
	 * @param polyline The source polyline used to generate the SVG element.
	 */
	protected SVGPolylines(final IPolyline polyline) {
		super(polyline);
	}


	/**
	 * Creates some lines using a SVG path.
	 * @param elt The SVG path.
	 */
	protected SVGPolylines(final SVGPathElement elt) {
		super(ShapeFactory.INST.createPolyline(getLinePointsFromSVGPathElement(elt)));
		if(elt == null || (!elt.isLines() && !elt.isLine())) {
			throw new IllegalArgumentException();
		}
		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates some joined-lines from an SVG polyline element.
	 * @param elt The source element.
	 */
	protected SVGPolylines(final SVGPolyLineElement elt) {
		this(ShapeFactory.INST.createPolyline(getPointsFromSVGElement(elt)));
		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates a line from an SVG line element.
	 * @param elt The source element.
	 */
	protected SVGPolylines(final SVGLineElement elt) {
		this(ShapeFactory.INST.createPolyline(Collections.emptyList()));

		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates lines from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	protected SVGPolylines(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createPolyline(SVGPolygon.getPointsFromSVGElement(getLaTeXDrawElement(elt, null))));

		final SVGElement shapeElt = getLaTeXDrawElement(elt, null);
		setSVGParameters(shapeElt);
		setSVGLatexdrawParameters(elt);
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));
		final IArrow arrow1 = shape.getArrowAt(0);
		final IArrow arrow2 = shape.getArrowAt(-1);
		setSVGArrow(arrow1, shapeElt.getAttribute(shapeElt.getUsablePrefix() + SVGAttributes.SVG_MARKER_START), shapeElt, SVGAttributes.SVG_MARKER_START);
		setSVGArrow(arrow2, shapeElt.getAttribute(shapeElt.getUsablePrefix() + SVGAttributes.SVG_MARKER_END), shapeElt, SVGAttributes.SVG_MARKER_END);
		homogeniseArrows(arrow1, arrow2);
		setRotationAngle(shapeElt);

		if(withTransformation) {
			applyTransformations(elt);
		}
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc == null) {
			return null;
		}

		final SVGElement root = new SVGGElement(doc);
		final SVGDefsElement defs = doc.getFirstChild().getDefs();
		final StringBuilder points = new StringBuilder();
		final List<IPoint> pts = shape.getPoints();
		SVGPolyLineElement elt;

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_JOINED_LINES);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

		for(final IPoint pt : pts) {
			points.append(pt.getX()).append(',').append(pt.getY()).append(' ');
		}

		final String pointsStr = points.toString();

		if(shape.hasShadow()) {
			final SVGPolyLineElement shad = new SVGPolyLineElement(doc);
			try {
				shad.setPoints(pointsStr);
			}catch(final ParseException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
			setSVGShadowAttributes(shad, false);
			root.appendChild(shad);
			setSVGArrow(shape, shad, 0, true, doc, defs);
			setSVGArrow(shape, shad, 1, true, doc, defs);
		}

		if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE) && shape.isFilled()) {
			// The background of the borders must be filled is there is a shadow.
			elt = new SVGPolyLineElement(doc);
			try {
				elt.setPoints(pointsStr);
			}catch(final ParseException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
			setSVGBorderBackground(elt, root);
		}

		elt = new SVGPolyLineElement(doc);
		try {
			elt.setPoints(pointsStr);
		}catch(final ParseException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
		root.appendChild(elt);

		if(shape.hasDbleBord()) {
			final SVGPolyLineElement dblBord = new SVGPolyLineElement(doc);
			try {
				dblBord.setPoints(pointsStr);
			}catch(final ParseException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
			setSVGDoubleBordersAttributes(dblBord);
			root.appendChild(dblBord);
		}

		setSVGAttributes(doc, elt, false);
		elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

		setSVGArrow(shape, elt, 0, false, doc, defs);
		setSVGArrow(shape, elt, shape.getNbArrows() - 1, false, doc, defs);

		return root;
	}
}
