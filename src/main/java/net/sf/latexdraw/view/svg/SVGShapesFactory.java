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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.Triangle;
import net.sf.latexdraw.parser.svg.SVGCircleElement;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGEllipseElement;
import net.sf.latexdraw.parser.svg.SVGGElement;
import net.sf.latexdraw.parser.svg.SVGImageElement;
import net.sf.latexdraw.parser.svg.SVGLineElement;
import net.sf.latexdraw.parser.svg.SVGPathElement;
import net.sf.latexdraw.parser.svg.SVGPolyLineElement;
import net.sf.latexdraw.parser.svg.SVGPolygonElement;
import net.sf.latexdraw.parser.svg.SVGRectElement;
import net.sf.latexdraw.parser.svg.SVGTextElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.jfx.ViewFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates SVG elements based on latexdraw.
 * @author Arnaud BLOUIN
 */
public class SVGShapesFactory implements SVGShapeProducer {
	/** A map to reduce the CC during the creation of shapes. */
	private final @NotNull Map<String, BiFunction<SVGGElement, Boolean, Shape>> creationMap;
	private final @NotNull ViewFactory viewFactory;

	/**
	 * Creates the factory.
	 */
	public SVGShapesFactory(final ViewFactory viewFactory) {
		super();

		this.viewFactory = Objects.requireNonNull(viewFactory);
		creationMap = new HashMap<>();
		creationMap.put(SVGPlot.XML_TYPE_PLOT, (elt, withTransformations) -> new SVGPlot(elt, withTransformations, this).getShape());
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
		creationMap.put(LNamespace.XML_TYPE_GROUP, (elt, withTransformations) -> new SVGGroup(elt, withTransformations, this).getShape());
		creationMap.put(LNamespace.XML_TYPE_DOT, (elt, withTransformations) -> new SVGDot(elt, withTransformations, viewFactory).getShape());
		creationMap.put(LNamespace.XML_TYPE_ARC, (elt, withTransformations) -> new SVGCircleArc(elt, withTransformations).getShape());
		creationMap.put(LNamespace.XML_TYPE_PICTURE, (elt, withTransformations) -> new SVGPicture(elt, withTransformations).getShape());
	}


	@Override
	public SVGElement createSVGElement(final Shape shape, final SVGDocument doc) {
		if(shape instanceof Group) {
			return new SVGGroup((Group) shape, this).toSVG(doc);
		}
		if(shape instanceof Plot) {
			return new SVGPlot((Plot) shape, this).toSVG(doc);
		}
		if(shape instanceof Square) {
			return new SVGSquare((Square) shape).toSVG(doc);
		}
		if(shape instanceof Rectangle) {
			return new SVGRectangle((Rectangle) shape).toSVG(doc);
		}
		if(shape instanceof Text) {
			return new SVGText((Text) shape).toSVG(doc);
		}
		if(shape instanceof CircleArc) {
			return new SVGCircleArc((CircleArc) shape).toSVG(doc);
		}
		if(shape instanceof Circle) {
			return new SVGCircle((Circle) shape).toSVG(doc);
		}
		if(shape instanceof Ellipse) {
			return new SVGEllipse((Ellipse) shape).toSVG(doc);
		}
		if(shape instanceof Triangle) {
			return new SVGTriangle((Triangle) shape).toSVG(doc);
		}
		if(shape instanceof Rhombus) {
			return new SVGRhombus((Rhombus) shape).toSVG(doc);
		}
		if(shape instanceof Polyline) {
			return new SVGPolylines((Polyline) shape).toSVG(doc);
		}
		if(shape instanceof Polygon) {
			return new SVGPolygon((Polygon) shape).toSVG(doc);
		}
		if(shape instanceof BezierCurve) {
			return new SVGBezierCurve((BezierCurve) shape).toSVG(doc);
		}
		if(shape instanceof Axes) {
			return new SVGAxes((Axes) shape).toSVG(doc);
		}
		if(shape instanceof Grid) {
			return new SVGGrid((Grid) shape).toSVG(doc);
		}
		if(shape instanceof Dot) {
			return new SVGDot((Dot) shape, viewFactory).toSVG(doc);
		}
		if(shape instanceof Picture) {
			return new SVGPicture((Picture) shape).toSVG(doc);
		}
		if(shape instanceof Freehand) {
			return new SVGFreeHand((Freehand) shape).toSVG(doc);
		}
		return null;
	}

	@Override
	public Shape createShape(final SVGElement elt) {
		return createShape(elt, true);
	}


	private Shape createShapeFromPathElement(final SVGPathElement path) {
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

	private Shape createShapeFromGElement(final SVGGElement elt, final boolean withTransformations) {
		final String ltdPref = elt.lookupPrefixUsable(LNamespace.LATEXDRAW_NAMESPACE_URI);
		final String type = elt.getAttribute(ltdPref + LNamespace.XML_TYPE);

		// If we have a group of shapes.
		if(type.isEmpty() || LNamespace.XML_TYPE_GROUP.equals(type)) {
			switch(elt.getChildNodes().getLength()) {
				case 0:
					return null;
				case 1:
					return createShape((SVGElement) elt.getChildNodes().item(0));
				default:
					return new SVGGroup(elt, withTransformations, this).getShape();
			}
		}

		// Otherwise, it should be a latexdraw shape saved in an SVG document.
		return creationMap.getOrDefault(type, (a, b) -> null).apply(elt, withTransformations);
	}


	@Override
	public Shape createShape(final SVGElement elt, final boolean withTransformations) {
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
			return new SVGPicture((SVGImageElement) elt).getShape();
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
