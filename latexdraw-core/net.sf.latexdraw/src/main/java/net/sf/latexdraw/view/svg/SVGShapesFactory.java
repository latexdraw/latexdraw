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
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.parsers.svg.SVGCircleElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
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
 * Creates SVG elements based on latexdraw.
 * @author Arnaud BLOUIN
 */
public final class SVGShapesFactory {
	/** The singleton. */
	public static final SVGShapesFactory INSTANCE = new SVGShapesFactory();
	/** A map to reduce the CC during the creation of shapes. */
	private final Map<String, BiFunction<SVGGElement, Boolean, IShape>> creationMap;


	/**
	 * Creates the factory.
	 */
	private SVGShapesFactory() {
		super();

		creationMap = new HashMap<>();
		creationMap.put(SVGPlot.XML_TYPE_PLOT, (elt, withTransformations) -> new SVGPlot(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_RECT, (elt, withTransformations) -> new SVGRectangle(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_ELLIPSE, (elt, withTransformations) -> new SVGEllipse(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_CIRCLE, (elt, withTransformations) -> new SVGCircle(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_POLYGON, (elt, withTransformations) -> new SVGPolygon(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_SQUARE, (elt, withTransformations) -> new SVGSquare(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_TRIANGLE, (elt, withTransformations) -> new SVGTriangle(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_RHOMBUS, (elt, withTransformations) -> new SVGRhombus(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_JOINED_LINES, (elt, withTransformations) -> new SVGPolylines(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_FREEHAND, (elt, withTransformations) -> new SVGFreeHand(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_LINE, (elt, withTransformations) -> new SVGPolylines(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_BEZIER_CURVE, (elt, withTransformations) -> new SVGBezierCurve(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_GRID, (elt, withTransformations) -> new SVGGrid(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_AXE, (elt, withTransformations) -> new SVGAxes(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_TEXT, (elt, withTransformations) -> new SVGText(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_GROUP, (elt, withTransformations) -> new SVGGroup(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_DOT, (elt, withTransformations) -> new SVGDot(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_ARC, (elt, withTransformations) -> new SVGCircleArc(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_PICTURE, (elt, withTransformations) -> {
			try {
				return new SVGPicture(elt, withTransformations).getShape();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
				return null;
			}
		});
	}

	/**
	 * Creates an SVG Element corresponding to the given shape.
	 * @param shape The shape used to determine which SVG element to create.
	 * @param doc The SVG document used to instantiate to SVG element.
	 * @return The created SVG element.
	 */
	public SVGElement createSVGElement(final IShape shape, final SVGDocument doc) {
		if(shape instanceof IGroup) {
			return new SVGGroup((IGroup) shape).toSVG(doc);
		}
		if(shape instanceof IPlot) {
			return new SVGPlot((IPlot) shape).toSVG(doc);
		}
		if(shape instanceof ISquare) {
			return new SVGSquare((ISquare) shape).toSVG(doc);
		}
		if(shape instanceof IRectangle) {
			return new SVGRectangle((IRectangle) shape).toSVG(doc);
		}
		if(shape instanceof IText) {
			return new SVGText((IText) shape).toSVG(doc);
		}
		if(shape instanceof ICircleArc) {
			return new SVGCircleArc((ICircleArc) shape).toSVG(doc);
		}
		if(shape instanceof ICircle) {
			return new SVGCircle((ICircle) shape).toSVG(doc);
		}
		if(shape instanceof IEllipse) {
			return new SVGEllipse((IEllipse) shape).toSVG(doc);
		}
		if(shape instanceof ITriangle) {
			return new SVGTriangle((ITriangle) shape).toSVG(doc);
		}
		if(shape instanceof IRhombus) {
			return new SVGRhombus((IRhombus) shape).toSVG(doc);
		}
		if(shape instanceof IPolyline) {
			return new SVGPolylines((IPolyline) shape).toSVG(doc);
		}
		if(shape instanceof IPolygon) {
			return new SVGPolygon((IPolygon) shape).toSVG(doc);
		}
		if(shape instanceof IBezierCurve) {
			return new SVGBezierCurve((IBezierCurve) shape).toSVG(doc);
		}
		if(shape instanceof IAxes) {
			return new SVGAxes((IAxes) shape).toSVG(doc);
		}
		if(shape instanceof IGrid) {
			return new SVGGrid((IGrid) shape).toSVG(doc);
		}
		if(shape instanceof IDot) {
			return new SVGDot((IDot) shape).toSVG(doc);
		}
		if(shape instanceof IPicture) {
			return new SVGPicture((IPicture) shape).toSVG(doc);
		}
		if(shape instanceof IFreehand) {
			return new SVGFreeHand((IFreehand) shape).toSVG(doc);
		}
		return null;
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
			return new SVGPolygon(path).getShape();
		}

		if(path.isLines() || path.isLine()) {
			return new SVGPolylines(path).getShape();
		}

		if(path.isBezierCurve()) {
			return new SVGBezierCurve(path).getShape();
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
					return new SVGGroup(elt, withTransformations).getShape();
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
		if(elt == null || !elt.enableRendering()) {
			return null;
		}
		if(elt instanceof SVGRectElement) {
			return new SVGRectangle((SVGRectElement) elt).getShape();
		}
		if(elt instanceof SVGEllipseElement) {
			return new SVGEllipse((SVGEllipseElement) elt).getShape();
		}
		if(elt instanceof SVGCircleElement) {
			return new SVGCircle((SVGCircleElement) elt).getShape();
		}
		if(elt instanceof SVGPolygonElement) {
			return new SVGPolygon((SVGPolygonElement) elt).getShape();
		}
		if(elt instanceof SVGPolyLineElement) {
			return new SVGPolylines((SVGPolyLineElement) elt).getShape();
		}
		if(elt instanceof SVGImageElement) {
			try {
				return new SVGPicture((SVGImageElement) elt).getShape();
			}catch(final IOException ex) {
				BadaboomCollector.INSTANCE.add(ex);
				return null;
			}
		}
		if(elt instanceof SVGLineElement) {
			return new SVGPolylines((SVGLineElement) elt).getShape();
		}
		if(elt instanceof SVGTextElement) {
			return new SVGText((SVGTextElement) elt).getShape();
		}
		if(elt instanceof SVGPathElement) {
			return createShapeFromPathElement((SVGPathElement) elt);
		}
		// If we have a group of shapes or a latexdraw shape.
		if(elt instanceof SVGGElement) {
			return createShapeFromGElement((SVGGElement) elt, withTransformations);
		}
		return null;
	}
}
