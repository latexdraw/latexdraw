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
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGGElement;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.PlotViewComputation;
import org.jetbrains.annotations.NotNull;

/**
 * An SVG generator for plotted functions.
 * @author Arnaud BLOUIN
 */
class SVGPlot extends SVGShape<Plot> implements PlotViewComputation {
	static final String XML_TYPE_PLOT = "plot"; //NON-NLS
	static final String XML_NB_POINTS = "nbpts"; //NON-NLS
	static final String XML_EQ = "eq"; //NON-NLS
	static final String XML_MIN = "min"; //NON-NLS
	static final String XML_MAX = "max"; //NON-NLS
	static final String XML_XSCALE = "xscale"; //NON-NLS
	static final String XML_YSCALE = "yscale"; //NON-NLS
	static final String XML_POLAR = "polar"; //NON-NLS
	static final String XML_STYLE = "plotstyle"; //NON-NLS

	private final SVGShapeProducer shapeProducer;

	SVGPlot(final Plot plot, final SVGShapeProducer shapeProducer) {
		super(plot);
		this.shapeProducer = shapeProducer;
	}


	/**
	 * Creates a Bézier curve from a latexdraw-SVG element.
	 * @param elt The source element.
	 */
	SVGPlot(final SVGGElement elt, final boolean withTransformation, final SVGShapeProducer shapeProducer) {
		this(ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 5, "x", false), shapeProducer); //NON-NLS

		setSVGParameters(elt);
		setPlotParameters(elt);

		if(elt.getChildNodes().getLength() > 0 && elt.getChildNodes().item(0) instanceof SVGElement) {
			final Shape sh = shapeProducer.createShape((SVGElement) elt.getChildNodes().item(0));
			if(sh instanceof Dot) {
				final Dot dot = (Dot) sh;
				shape.setDiametre(dot.getDiametre());
				shape.setDotStyle(dot.getDotStyle());
				shape.setLineColour(dot.getLineColour());
				if(dot.isFillable()) {
					shape.setDotFillingCol(dot.getDotFillingCol());
				}
			}
		}

		setSVGShadowParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_SHADOW));
		setSVGDbleBordersParameters(getLaTeXDrawElement(elt, LNamespace.XML_TYPE_DBLE_BORDERS));

		if(withTransformation) {
			applyTransformations(elt);
		}
	}

	private final void setPlotParameters(final SVGGElement elt) {
		shape.setPlotEquation(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_EQ));
		shape.setPlotStyle(PlotStyle.getPlotStyle(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_STYLE)));

		try {
			shape.setPlotMinX(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MIN)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		try {
			shape.setPlotMaxX(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MAX)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		// Repetition just to assure the setting of these values because of the minX<MaxX constraint.
		try {
			shape.setPlotMinX(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MIN)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		try {
			shape.setPlotMaxX(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MAX)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		try {
			shape.setNbPlottedPoints(Integer.parseInt(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_NB_POINTS)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		shape.setPolar(Boolean.parseBoolean(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_POLAR)));

		try {
			shape.setX(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION_X)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		try {
			shape.setY(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION_Y)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		try {
			shape.setXScale(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_XSCALE)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}

		try {
			shape.setYScale(Double.parseDouble(elt.getAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_YSCALE)));
		}catch(final NumberFormatException | NullPointerException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}

	private void toSVGShape(final @NotNull SVGDocument doc, final @NotNull SVGElement elt) {
		final double minX = shape.getPlotMinX();
		final double maxX = shape.getPlotMaxX();
		final double step = shape.getPlottingStep();
		final double posX = shape.getPosition().getX();
		final double posY = shape.getPosition().getY();

		switch(shape.getPlotStyle()) {
			case LINE:
				toSVGLine(elt, doc, posX, posY, minX, maxX, step);
				break;
			case CURVE:
				toSVGCurve(elt, doc, posX, posY, minX, maxX, step);
				break;
			case ECURVE:
				toSVGCurve(elt, doc, posX, posY, minX + step, maxX - step, step);
				break;
			case CCURVE:
				toSVGCurve(elt, doc, posX, posY, minX, maxX, step);
				break;
			case DOTS:
				toSVGDots(elt, doc, posX, posY, minX, maxX, step);
				break;
			case POLYGON:
				toSVGPolygon(elt, doc, posX, posY, minX, maxX, step);
				break;
		}
	}

	@Override
	SVGElement toSVG(final @NotNull SVGDocument doc) {
		if(doc.getFirstChild().getDefs() == null) {
			return null;
		}

		final SVGElement root = new SVGGElement(doc);

		if(shape.hasShadow()) {
			final SVGGElement shad = new SVGGElement(doc);
			toSVGShape(doc, shad);
			setSVGShadowAttributes(shad, false);
			root.appendChild(shad);
		}

		if(shape.hasDbleBord()) {
			final SVGGElement dble = new SVGGElement(doc);
			toSVGShape(doc, dble);
			setSVGDoubleBordersAttributes(dble);
			root.appendChild(dble);
		}

		toSVGShape(doc, root);

		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, XML_TYPE_PLOT);
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_POLAR, Boolean.toString(shape.isPolar()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_EQ, shape.getPlotEquation());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_STYLE, shape.getPlotStyle().getPSTToken());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MIN, Double.toString(shape.getPlotMinX()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_MAX, Double.toString(shape.getPlotMaxX()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_NB_POINTS, Integer.toString(shape.getNbPlottedPoints()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_XSCALE, Double.toString(shape.getXScale()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + XML_YSCALE, Double.toString(shape.getYScale()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION_X, Double.toString(shape.getX()));
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_POSITION_Y, Double.toString(shape.getY()));
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		setSVGAttributes(doc, root, true);
		setSVGRotationAttribute(root);

		return root;
	}


	private void toSVGDots(final SVGElement elt, final @NotNull SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		for(final Dot dot : updatePoints(shape, posX, posY, minX, maxX, step)) {
			elt.appendChild(shapeProducer.createSVGElement(dot, doc));
		}
	}


	private void toSVGPolygon(final SVGElement elt, final @NotNull SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		elt.appendChild(shapeProducer.createSVGElement(updatePolygon(shape, posX, posY, minX, maxX, step), doc));
	}


	private void toSVGLine(final SVGElement elt, final @NotNull SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		elt.appendChild(shapeProducer.createSVGElement(updateLine(shape, posX, posY, minX, maxX, step), doc));
	}


	private void toSVGCurve(final SVGElement elt, final @NotNull SVGDocument doc, final double posX, final double posY, final double minX, final double maxX, final double step) {
		elt.appendChild(shapeProducer.createSVGElement(updateCurve(shape, posX, posY, minX, maxX, step), doc));
	}
}
