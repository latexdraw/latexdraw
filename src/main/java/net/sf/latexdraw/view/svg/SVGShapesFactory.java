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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.jfx.ViewFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Creates SVG elements based on latexdraw.
 * @author Arnaud BLOUIN
 */
public class SVGShapesFactory implements SVGShapeProducer {
	/** A map to reduce the CC during the creation of shapes. */
	private final @NotNull ViewFactory viewFactory;
	private final @NotNull Map<String, BiFunction<SVGGElement, Boolean, Shape>> xmlToSVGProducers;
	private final @NotNull List<Tuple<Class<? extends Shape>, BiFunction<Shape, SVGDocument, SVGElement>>> shapeToSVGProducers;
	private final @NotNull List<Tuple<Class<? extends SVGElement>, BiFunction<SVGElement, Boolean, Shape>>> svgToShapeProducers;

	/**
	 * Creates the factory.
	 */
	@Inject
	public SVGShapesFactory(final ViewFactory viewFactory) {
		super();
		shapeToSVGProducers = new ArrayList<>();
		svgToShapeProducers = new ArrayList<>();
		this.viewFactory = Objects.requireNonNull(viewFactory);
		xmlToSVGProducers = new HashMap<>();
		fillStringCreationMap();
		fillShapeCreationList();
		fillSVGToShapeProducers();
	}

	private final void fillShapeCreationList() {
		shapeToSVGProducers.add(new Tuple<>(Group.class, (sh, doc) -> new SVGGroup((Group) sh, this).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Plot.class, (sh, doc) -> new SVGPlot((Plot) sh, this).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Square.class, (sh, doc) -> new SVGSquare((Square) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Rectangle.class, (sh, doc) -> new SVGRectangle((Rectangle) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Text.class, (sh, doc) -> new SVGText((Text) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(CircleArc.class, (sh, doc) -> new SVGCircleArc((CircleArc) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Circle.class, (sh, doc) -> new SVGCircle((Circle) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Ellipse.class, (sh, doc) -> new SVGEllipse((Ellipse) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Triangle.class, (sh, doc) -> new SVGTriangle((Triangle) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Rhombus.class, (sh, doc) -> new SVGRhombus((Rhombus) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Polyline.class, (sh, doc) -> new SVGPolylines((Polyline) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Polygon.class, (sh, doc) -> new SVGPolygon((Polygon) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(BezierCurve.class, (sh, doc) -> new SVGBezierCurve((BezierCurve) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Axes.class, (sh, doc) -> new SVGAxes((Axes) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Grid.class, (sh, doc) -> new SVGGrid((Grid) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Dot.class, (sh, doc) -> new SVGDot((Dot) sh, viewFactory).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Picture.class, (sh, doc) -> new SVGPicture((Picture) sh).toSVG(doc)));
		shapeToSVGProducers.add(new Tuple<>(Freehand.class, (sh, doc) -> new SVGFreeHand((Freehand) sh).toSVG(doc)));
	}

	private final void fillStringCreationMap() {
		xmlToSVGProducers.put(SVGPlot.XML_TYPE_PLOT, (elt, withTransformations) -> new SVGPlot(elt, withTransformations, this).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_RECT, (elt, withTransformations) -> new SVGRectangle(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_ELLIPSE, (elt, withTransformations) -> new SVGEllipse(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_CIRCLE, (elt, withTransformations) -> new SVGCircle(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_POLYGON, (elt, withTransformations) -> new SVGPolygon(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_SQUARE, (elt, withTransformations) -> new SVGSquare(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_TRIANGLE, (elt, withTransformations) -> new SVGTriangle(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_RHOMBUS, (elt, withTransformations) -> new SVGRhombus(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_JOINED_LINES, (elt, withTransformations) -> new SVGPolylines(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_FREEHAND, (elt, withTransformations) -> new SVGFreeHand(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_LINE, (elt, withTransformations) -> new SVGPolylines(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_BEZIER_CURVE, (elt, withTransformations) -> new SVGBezierCurve(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_GRID, (elt, withTransformations) -> new SVGGrid(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_AXE, (elt, withTransformations) -> new SVGAxes(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_TEXT, (elt, withTransformations) -> new SVGText(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_GROUP, (elt, withTransformations) -> new SVGGroup(elt, withTransformations, this).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_DOT, (elt, withTransformations) -> new SVGDot(elt, withTransformations, viewFactory).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_ARC, (elt, withTransformations) -> new SVGCircleArc(elt, withTransformations).getShape());
		xmlToSVGProducers.put(LNamespace.XML_TYPE_PICTURE, (elt, withTransformations) -> new SVGPicture(elt, withTransformations).getShape());
	}

	private final void fillSVGToShapeProducers() {
		svgToShapeProducers.add(new Tuple<>(SVGRectElement.class, (svgElt, t) -> new SVGRectangle((SVGRectElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGEllipseElement.class, (svgElt, t) -> new SVGEllipse((SVGEllipseElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGCircleElement.class, (svgElt, t) -> new SVGCircle((SVGCircleElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGPolygonElement.class, (svgElt, t) -> new SVGPolygon((SVGPolygonElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGPolyLineElement.class, (svgElt, t) -> new SVGPolylines((SVGPolyLineElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGImageElement.class, (svgElt, t) -> new SVGPicture((SVGImageElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGLineElement.class, (svgElt, t) -> new SVGPolylines((SVGLineElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGTextElement.class, (svgElt, t) -> new SVGText((SVGTextElement) svgElt).getShape()));
		svgToShapeProducers.add(new Tuple<>(SVGPathElement.class, (svgElt, t) -> createShapeFromPathElement((SVGPathElement) svgElt)));
		svgToShapeProducers.add(new Tuple<>(SVGGElement.class, (svgElt, t) -> createShapeFromGElement((SVGGElement) svgElt, t)));
	}

	@Override
	public SVGElement createSVGElement(final @NotNull Shape shape, final @NotNull SVGDocument doc) {
		// Makes use of a list of tuples to reduce the CC.
		// Looking for the tuple which type matches the type of the given shape.
		// Then calling the associated function that produces the PST view.
		return shapeToSVGProducers.stream()
			.filter(t -> t.a.isAssignableFrom(shape.getClass()))
			.findFirst()
			.map(t -> t.b.apply(shape, doc))
			.orElse(null);
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
		return xmlToSVGProducers.getOrDefault(type, (a, b) -> null).apply(elt, withTransformations);
	}


	@Override
	public Shape createShape(final SVGElement elt, final boolean withTransformations) {
		if(elt == null || !elt.enableRendering()) {
			return null;
		}
		return svgToShapeProducers.stream()
			.filter(t -> t.a.isAssignableFrom(elt.getClass()))
			.findFirst()
			.map(t -> t.b.apply(elt, withTransformations))
			.orElse(null);
	}
}
