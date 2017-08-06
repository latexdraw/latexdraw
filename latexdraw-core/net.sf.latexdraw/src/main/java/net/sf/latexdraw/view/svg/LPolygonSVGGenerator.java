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

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.parsers.svg.AbstractPointsElement;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGLineElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.SVGPolygonElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * A SVG generator for a polygon.
 * @author Arnaud BLOUIN
 */
class LPolygonSVGGenerator extends LShapeSVGGenerator<IPolygon> {
	/**
	 * Creates a generator for IPolygon.
	 * @param polygon The source polygon used to generate the SVG element.
	 */
	protected LPolygonSVGGenerator(final IPolygon polygon) {
		super(polygon);
	}


	/**
	 * Creates a latexdraw shape from the given SVG element.
	 * @param elt The source SVG element.
	 * @throws IllegalArgumentException If the given SVG element is null.
	 * @since 3.0
	 */
	protected LPolygonSVGGenerator(final SVGPathElement elt) {
		super(ShapeFactory.INST.createPolygon(initModifiablePointsShape(elt)));
		if(elt == null) throw new IllegalArgumentException();
		setSVGParameters(elt);
		applyTransformations(elt);
	}



	/**
	 * Creates a polygon from an SVG polygon element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LPolygonSVGGenerator(final SVGPolygonElement elt) {
		this(ShapeFactory.INST.createPolygon(getPointsFromSVGElement(elt)));
		setSVGParameters(elt);
		applyTransformations(elt);
	}


	/**
	 * Creates a polygon from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @since 2.0.0
	 */
	protected LPolygonSVGGenerator(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createPolygon(getPointsFromSVGElement(getLaTeXDrawElement(elt, null))));

		setSVGLatexdrawParameters(elt);
		setSVGParameters(getLaTeXDrawElement(elt, null));
		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation) {
			applyTransformations(elt);
		}
	}


	@Override
	public SVGElement toSVG(final SVGDocument doc) {
		if(doc==null)
			throw new IllegalArgumentException();

		final SVGElement root = new SVGGElement(doc);
		SVGPolygonElement elt;
		final StringBuilder pointsBuilder = new StringBuilder();

        root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_TYPE, LNamespace.XML_TYPE_POLYGON);
        root.setAttribute(SVGAttributes.SVG_ID, getSVGID());

        for(final IPoint pt : shape.getPoints())
     	   pointsBuilder.append(pt.getX()).append(',').append(pt.getY()).append(' ');

        final String points = pointsBuilder.toString();

        if(shape.hasShadow()) {
        	final SVGPolygonElement shad = new SVGPolygonElement(doc);
        	try { shad.setPoints(points); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
        	setSVGShadowAttributes(shad, true);
        	root.appendChild(shad);
        }

        if(shape.hasShadow() && !shape.getLineStyle().getLatexToken().equals(PSTricksConstants.LINE_NONE_STYLE)) {
        	// The background of the borders must be filled is there is a shadow.
        	elt = new SVGPolygonElement(doc);
        	try { elt.setPoints(points); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
        	setSVGBorderBackground(elt, root);
        }

        elt = new SVGPolygonElement(doc);
        try { elt.setPoints(points); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
        root.appendChild(elt);
        setSVGAttributes(doc, elt, true);
        elt.setAttribute(LNamespace.LATEXDRAW_NAMESPACE +':'+ LNamespace.XML_ROTATION, String.valueOf(shape.getRotationAngle()));

        if(shape.hasDbleBord()) {
        	final SVGPolygonElement dblBord = new SVGPolygonElement(doc);
        	try { dblBord.setPoints(points); }catch(final ParseException ex) { BadaboomCollector.INSTANCE.add(ex); }
        	setSVGDoubleBordersAttributes(dblBord);
        	root.appendChild(dblBord);
        }

        return root;
	}

	/**
	 * Returns a set of points from an SVG element.
	 */
	static List<IPoint> getPointsFromSVGElement(final SVGElement elt) {
		if(elt instanceof SVGLineElement) {
			final SVGLineElement lineElt = (SVGLineElement)elt;
			return Arrays.asList(
				ShapeFactory.INST.createPoint(lineElt.getX1(), lineElt.getY1()),
				ShapeFactory.INST.createPoint(lineElt.getX2(), lineElt.getY2()));
		}

		if(elt instanceof AbstractPointsElement) {
			final List<Point2D> ptsPol = ((AbstractPointsElement) elt).getPoints2D();
			if(ptsPol == null) return null;
			return ptsPol.stream().map(pt -> ShapeFactory.INST.createPoint(pt.getX(), pt.getY())).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}


	/**
	 * Sets the points of the modifiable points shape using the given SVG element.
	 */
	static List<IPoint> initModifiablePointsShape(final SVGPathElement elt) {
		if(elt == null) return Collections.emptyList();

		final SVGPathSegList segs = elt.getSegList();
		final int size = segs.get(segs.size() - 1) instanceof SVGPathSegClosePath ? segs.size() - 1 : segs.size();
		Point2D pt = new Point2D.Double(); // Creating a point to support when the first path element is relative.
		final List<IPoint> pts = new ArrayList<>();

		for(int i = 0; i < size; i++) {
			final SVGPathSeg seg = segs.get(i);

			if(!(seg instanceof SVGPathSegLineto))
				throw new IllegalArgumentException("The given SVG path element is not a polygon."); //$NON-NLS-1$

			pt = ((SVGPathSegLineto) seg).getPoint(pt);
			pts.add(ShapeFactory.INST.createPoint(pt.getX(), pt.getY()));
		}

		return pts;
	}
}
