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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.SVGCircleElement;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGEllipseElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGImageElement;
import net.sf.latexdraw.parsers.svg.SVGLineElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.SVGPolyLineElement;
import net.sf.latexdraw.parsers.svg.SVGPolygonElement;
import net.sf.latexdraw.parsers.svg.SVGRectElement;
import net.sf.latexdraw.parsers.svg.SVGTextElement;
import net.sf.latexdraw.util.LNamespace;

/**
 * Creates IShape instances according to the given SVG element.
 * @author Arnaud BLOUIN
 */
public final class IShapeSVGFactory {
	/** The singleton. */
	public static final IShapeSVGFactory INSTANCE = new IShapeSVGFactory();

	/** A map to reduce the CC during the creation of shapes. */
	private final Map<String, BiFunction<SVGGElement, Boolean, IShape>> creationMap;

	private IShapeSVGFactory() {
		super();
		creationMap = new HashMap<>();
		creationMap.put(LPlotSVGGenerator.XML_TYPE_PLOT, (elt, withTransformations) -> new LPlotSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_RECT, (elt, withTransformations) -> new LRectangleSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_ELLIPSE, (elt, withTransformations) -> new LEllipseSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_CIRCLE, (elt, withTransformations) -> new LCircleSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_POLYGON, (elt, withTransformations) -> new LPolygonSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_SQUARE, (elt, withTransformations) -> new LSquareSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_TRIANGLE, (elt, withTransformations) -> new LTriangleSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_RHOMBUS, (elt, withTransformations) -> new LRhombusSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_JOINED_LINES, (elt, withTransformations) -> new LPolylinesSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_FREEHAND, (elt, withTransformations) -> new LFreeHandSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_LINE, (elt, withTransformations) -> new LPolylinesSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_BEZIER_CURVE, (elt, withTransformations) -> new LBezierCurveSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_GRID, (elt, withTransformations) -> new LGridSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_AXE, (elt, withTransformations) -> new LAxeSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_TEXT, (elt, withTransformations) -> new LTextSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_GROUP, (elt, withTransformations) -> new LGroupSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_DOT, (elt, withTransformations) -> new LDotSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_ARC, (elt, withTransformations) -> new LCircleArcSVGGenerator(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_PICTURE, (elt, withTransformations) -> {
			try {
				return new LPictureSVGGenerator(elt, withTransformations).getShape();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
				return null;
			}
		});
	}


	/**
	 * Creates a IShape instance using the given SVGElement.
	 * @param elt The SVGElement to parse.
	 * @return The created IShape instance or null.
	 * @since 3.0
	 */
	public IShape createShape(final SVGElement elt) {
		return createShape(elt, true);
	}


	private IShape createShapeFromPathElement(final SVGPathElement path) {
		if(path.isPolygon()) {
			return new LPolygonSVGGenerator(path).getShape();
		}

		if(path.isLines() || path.isLine()) {
			return new LPolylinesSVGGenerator(path).getShape();
		}

		if(path.isBezierCurve()) {
			return new LBezierCurveSVGGenerator(path).getShape();
		}

		return null;
	}

	private IShape createShapeFromGElement(final SVGGElement elt, final boolean withTransformations) {
		final String ltdPref = elt.lookupPrefixUsable(LNamespace.LATEXDRAW_NAMESPACE_URI);
		final String type = elt.getAttribute(ltdPref + LNamespace.XML_TYPE);

		// If we have a group of shapes.
		if(type == null || type.isEmpty() || LNamespace.XML_TYPE_GROUP.equals(type)) {
			switch(elt.getChildNodes().getLength()) {
				case 0:
					return null;
				case 1:
					return createShape((SVGElement) elt.getChildNodes().item(0));
				default:
					return new LGroupSVGGenerator(elt, withTransformations).getShape();
			}
		}

		// Otherwise, it should be a latexdraw shape saved in an SVG document.
		return creationMap.getOrDefault(type, (a, b) -> null).apply(elt, withTransformations);
	}

	/**
	 * Creates a IShape instance using the given SVGElement.
	 * @param elt The SVGElement to parse.
	 * @param withTransformations True: the set of transformations that concerned the given SVG element will be applied to the shape.
	 * @return The created IShape instance or null.
	 * @since 3.0
	 */
	public IShape createShape(final SVGElement elt, final boolean withTransformations) {
		if(elt == null || !elt.enableRendering()) return null;
		if(elt instanceof SVGRectElement) return new LRectangleSVGGenerator((SVGRectElement) elt).getShape();
		if(elt instanceof SVGEllipseElement) return new LEllipseSVGGenerator((SVGEllipseElement) elt).getShape();
		if(elt instanceof SVGCircleElement) return new LCircleSVGGenerator((SVGCircleElement) elt).getShape();
		if(elt instanceof SVGPolygonElement) return new LPolygonSVGGenerator((SVGPolygonElement) elt).getShape();
		if(elt instanceof SVGPolyLineElement) return new LPolylinesSVGGenerator((SVGPolyLineElement) elt).getShape();
		if(elt instanceof SVGImageElement) {
			try {
				return new LPictureSVGGenerator((SVGImageElement) elt).getShape();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
				return null;
			}
		}
		if(elt instanceof SVGLineElement) return new LPolylinesSVGGenerator((SVGLineElement) elt).getShape();
		if(elt instanceof SVGTextElement) return new LTextSVGGenerator((SVGTextElement) elt).getShape();
		if(elt instanceof SVGPathElement) return createShapeFromPathElement((SVGPathElement) elt);
		// If we have a group of shapes or a latexdraw shape.
		if(elt instanceof SVGGElement) return createShapeFromGElement((SVGGElement) elt, withTransformations);
		return null;
	}
}
