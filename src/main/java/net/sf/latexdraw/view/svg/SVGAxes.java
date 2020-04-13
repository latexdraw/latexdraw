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

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import net.sf.latexdraw.parser.svg.SVGAttributes;
import net.sf.latexdraw.parser.svg.SVGDocument;
import net.sf.latexdraw.parser.svg.SVGElement;
import net.sf.latexdraw.parser.svg.SVGElements;
import net.sf.latexdraw.parser.svg.SVGGElement;
import net.sf.latexdraw.parser.svg.SVGNodeList;
import net.sf.latexdraw.parser.svg.SVGParserUtils;
import net.sf.latexdraw.parser.svg.SVGPathElement;
import net.sf.latexdraw.parser.svg.SVGTextElement;
import net.sf.latexdraw.parser.svg.SVGTransform;
import net.sf.latexdraw.parser.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parser.svg.path.SVGPathSegList;
import net.sf.latexdraw.parser.svg.path.SVGPathSegMoveto;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.GenericAxes;
import org.jetbrains.annotations.NotNull;

/**
 * SVG/latexdraw axes import export.
 * @author Arnaud BLOUIN
 */
class SVGAxes extends SVGShape<Axes> implements GenericAxes<SVGTextElement> {
	private SVGDocument currentDoc;
	private SVGPathSegList currentPath;
	private SVGGElement currentTicks;

	SVGAxes(final Axes shape) {
		super(shape);
	}


	/**
	 * Creates axes from a latexdraw-SVG element.
	 * @param elt The source element.
	 * @param withTransformation If true, the SVG transformations will be applied.
	 */
	SVGAxes(final SVGGElement elt, final boolean withTransformation) {
		this(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));

		if(elt == null) {
			throw new IllegalArgumentException();
		}

		setSVGParameters(elt);

		List<Point2D> values;
		final String pref = LNamespace.LATEXDRAW_NAMESPACE + ':';

		shape.setShowOrigin(Boolean.parseBoolean(elt.getAttribute(pref + LNamespace.XML_AXE_SHOW_ORIGIN)));
		shape.setAxesStyle(AxesStyle.getStyle(elt.getAttribute(pref + LNamespace.XML_STYLE)));
		shape.setTicksDisplayed(PlottingStyle.getStyle(elt.getAttribute(pref + LNamespace.XML_AXE_SHOW_TICKS)));
		shape.setTicksStyle(TicksStyle.getStyle(elt.getAttribute(pref + LNamespace.XML_AXE_TICKS_STYLE)));
		shape.setLabelsDisplayed(PlottingStyle.getStyle(elt.getAttribute(pref + LNamespace.XML_AXE_LABELS_STYLE)));
		try {
			shape.setTicksSize(Double.parseDouble(elt.getAttribute(pref + LNamespace.XML_AXE_TICKS_SIZE)));
		}catch(final NumberFormatException ignored) {
		}

		values = SVGParserUtils.INSTANCE.parsePoints(elt.getAttribute(pref + LNamespace.XML_GRID_END));

		if(!values.isEmpty()) {
			shape.setGridEndX((int) values.get(0).getX());
			shape.setGridEndY((int) values.get(0).getY());
		}

		values = SVGParserUtils.INSTANCE.parsePoints(elt.getAttribute(pref + LNamespace.XML_GRID_START));

		if(!values.isEmpty()) {
			shape.setGridStartX((int) values.get(0).getX());
			shape.setGridStartY((int) values.get(0).getY());
		}

		values = SVGParserUtils.INSTANCE.parsePoints(elt.getAttribute(pref + LNamespace.XML_GRID_ORIGIN));

		if(!values.isEmpty()) {
			shape.setOriginX((int) values.get(0).getX());
			shape.setOriginY((int) values.get(0).getY());
		}

		values = SVGParserUtils.INSTANCE.parsePoints(elt.getAttribute(pref + LNamespace.XML_AXE_INCREMENT));

		if(!values.isEmpty()) {
			shape.setIncrementX(values.get(0).getX());
			shape.setIncrementY(values.get(0).getY());
		}

		values = SVGParserUtils.INSTANCE.parsePoints(elt.getAttribute(pref + LNamespace.XML_AXE_DIST_LABELS));

		if(!values.isEmpty()) {
			shape.setDistLabelsX(values.get(0).getX());
			shape.setDistLabelsY(values.get(0).getY());
		}

		setParametersFromSVG(elt);

		homogeniseArrows(shape.getArrowAt(0), shape.getArrowAt(1));
		homogeniseArrows(shape.getArrowAt(1), shape.getArrowAt(2));
		homogeniseArrows(shape.getArrowAt(2), shape.getArrowAt(3));

		if(withTransformation) {
			applyTransformations(elt);
		}
	}

	private final void setParametersFromSVG(final SVGGElement elt) {
		if(shape.getAxesStyle() == AxesStyle.AXES) {
			setAxesStyleFromSVG(elt);
		}
	}

	private void setAxesStyleFromSVG(final SVGGElement elt) {
		/* Looking for the two axe in order to get the position of the axes. */
		final SVGNodeList nl = elt.getChildren(SVGElements.SVG_G);
		int i = 0;
		final int size = nl.getLength();
		SVGGElement l1 = null;
		SVGGElement l2 = null;
		SVGElement element;

		while((l1 == null || l2 == null) && i < size) {
			element = nl.item(i);

			if(element instanceof SVGGElement) {
				if(l1 == null) {
					l1 = (SVGGElement) element;
				}else {
					l2 = (SVGGElement) element;
				}
			}

			i++;
		}

		if(l1 != null && l2 != null) {
			try {
				final Polyline la = new SVGPolylines(l1, false).shape;
				final Polyline lb = new SVGPolylines(l2, false).shape;

				// Legacy code: in older versions the position was computed from the lines
				// now, a translation is used.
				if(elt.getTransform().stream().noneMatch(tr -> tr instanceof SVGTransform.SVGTranslateTransformation)) {
					shape.setPosition(ShapeFactory.INST.createPoint(lb.getPtAt(0).getX(), la.getPtAt(0).getY()));
				}
				//End of legacy

				shape.setLineStyle(la.getLineStyle());
				shape.getArrowAt(1).setArrowStyle(la.getArrowAt(0).getArrowStyle());
				shape.getArrowAt(3).setArrowStyle(la.getArrowAt(1).getArrowStyle());
				shape.getArrowAt(0).setArrowStyle(lb.getArrowAt(0).getArrowStyle());
				shape.getArrowAt(2).setArrowStyle(lb.getArrowAt(1).getArrowStyle());
			}catch(final IllegalArgumentException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}
	}


	@Override
	SVGElement toSVG(final @NotNull SVGDocument doc) {
		currentDoc = doc;
		currentPath = new SVGPathSegList();
		currentTicks = new SVGGElement(doc);
		final SVGElement root = new SVGGElement(doc);
		final String pref = LNamespace.LATEXDRAW_NAMESPACE + ':';
		final SVGPathElement path = new SVGPathElement(doc);

		setSVGAttributes(doc, root, false);

		root.setAttribute(SVGAttributes.SVG_TRANSFORM,
			"translate(" + MathUtils.INST.format.format(shape.getPosition().getX()) + ',' + MathUtils.INST.format.format(shape.getPosition().getY()) + ')'); //NON-NLS
		root.setAttribute(pref + LNamespace.XML_STYLE, shape.getAxesStyle().toString());
		root.setAttribute(pref + LNamespace.XML_GRID_START, shape.getGridStartX() + " " + shape.getGridStartY());
		root.setAttribute(pref + LNamespace.XML_GRID_END, shape.getGridEndX() + " " + shape.getGridEndY());
		root.setAttribute(pref + LNamespace.XML_GRID_ORIGIN, shape.getOriginX() + " " + shape.getOriginY());
		root.setAttribute(pref + LNamespace.XML_AXE_INCREMENT, shape.getIncrementX() + " " + shape.getIncrementY());
		root.setAttribute(pref + LNamespace.XML_AXE_DIST_LABELS, shape.getDistLabelsX() + " " + shape.getDistLabelsY());
		root.setAttribute(pref + LNamespace.XML_AXE_TICKS_SIZE, String.valueOf(shape.getTicksSize()));
		root.setAttribute(pref + LNamespace.XML_AXE_SHOW_ORIGIN, String.valueOf(shape.isShowOrigin()));
		root.setAttribute(pref + LNamespace.XML_AXE_SHOW_TICKS, shape.getTicksDisplayed().toString());
		root.setAttribute(pref + LNamespace.XML_AXE_LABELS_STYLE, shape.getLabelsDisplayed().toString());
		root.setAttribute(pref + LNamespace.XML_AXE_TICKS_STYLE, shape.getTicksStyle().toString());
		root.setAttribute(LNamespace.LATEXDRAW_NAMESPACE + ':' + LNamespace.XML_TYPE, LNamespace.XML_TYPE_AXE);
		root.setAttribute(SVGAttributes.SVG_ID, getSVGID());
		createSVGAxe(root, doc);
		setSVGRotationAttribute(root);

		updatePathTicks();
		updatePathLabels();
		root.appendChild(currentTicks);
		path.setPathData(currentPath);
		root.appendChild(path);

		return root;
	}


	private void createArrows(final @NotNull SVGElement elt, final @NotNull SVGDocument document) {
		if(shape.getAxesStyle().supportsArrows() && shape.getNbArrows() == 4) {
			final double posX = shape.getPosition().getX();
			final double posY = shape.getPosition().getY();
			final Arrow arr0 = shape.getArrowAt(1);
			final Arrow arr1 = shape.getArrowAt(3);
			final double arr0Reduction = arr0.getArrowStyle().needsLineReduction() ? arr0.getArrowShapedWidth() : 0.;
			final double arr1Reduction = arr1.getArrowStyle().needsLineReduction() ? arr1.getArrowShapedWidth() : 0.;
			final Polyline xLine = ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(posX + shape.getGridStartX() *
				Shape.PPC + arr0Reduction, posY), ShapeFactory.INST.createPoint(posX + shape.getGridEndX() * Shape.PPC - arr1Reduction, posY)));
			final Polyline yLine = ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(posX, posY - shape.getGridStartY() *
				Shape.PPC - arr0Reduction), ShapeFactory.INST.createPoint(posX, posY - shape.getGridEndY() * Shape.PPC + arr1Reduction)));


			xLine.getArrowAt(0).copy(arr0);
			xLine.getArrowAt(1).copy(arr1);
			yLine.getArrowAt(0).copy(shape.getArrowAt(0));
			yLine.getArrowAt(1).copy(shape.getArrowAt(2));
			final SVGElement eltX = new SVGPolylines(xLine).toSVG(document);
			final SVGElement eltY = new SVGPolylines(yLine).toSVG(document);
			final String transform = "translate(" + MathUtils.INST.format.format(-shape.getPosition().getX()) + ',' + //NON-NLS
				MathUtils.INST.format.format(-shape.getPosition().getY()) + ')';

			eltX.setAttribute(SVGAttributes.SVG_TRANSFORM, transform);
			eltY.setAttribute(SVGAttributes.SVG_TRANSFORM, transform);
			elt.appendChild(eltX);
			elt.appendChild(eltY);
		}
	}


	private void createFrame(final @NotNull SVGElement elt, final @NotNull SVGDocument document) {
		final double gridEndx = shape.getGridEndX();
		final double gridEndy = shape.getGridEndY();

		if(gridEndx > 0 || gridEndy > 0) {
			final double positionx = shape.getPosition().getX();
			final double positiony = shape.getPosition().getY();
			final double xMax = positionx + gridEndx * Shape.PPC;
			final double yMax = positiony - gridEndy * Shape.PPC;
			final Point pos = ShapeFactory.INST.createPoint(positionx, gridEndy > 0 ? yMax : positiony);
			final Rectangle r = ShapeFactory.INST.createRectangle(pos, Math.abs(pos.getX() - (gridEndx > 0 ? xMax : positionx)),
				Math.abs(pos.getY() - positiony));

			r.setBordersPosition(BorderPos.MID);
			r.setLineColour(shape.getLineColour());
			r.setLineStyle(shape.getLineStyle());
			r.setThickness(shape.getThickness());

			final SVGElement frame = new SVGRectangle(r).toSVG(document);
			frame.setAttribute(SVGAttributes.SVG_TRANSFORM, "translate(" + MathUtils.INST.format.format(-shape.getPosition().getX()) + ',' + //NON-NLS
				MathUtils.INST.format.format(-shape.getPosition().getY()) + ')');
			elt.appendChild(frame);
		}
	}


	private void createSVGAxe(final SVGElement elt, final SVGDocument document) {
		switch(shape.getAxesStyle()) {
			case AXES -> createArrows(elt, document);
			case FRAME -> createFrame(elt, document);
		}
	}

	@Override
	public Axes getModel() {
		return getShape();
	}

	@Override
	public SVGTextElement createTextLabel(final String text, final double x, final double y, final Font font) {
		if(currentTicks != null && currentDoc != null) {
			final SVGTextElement textElt = new SVGTextElement(currentDoc);
			textElt.setAttribute(SVGAttributes.SVG_X, MathUtils.INST.format.format(x));
			textElt.setAttribute(SVGAttributes.SVG_Y, MathUtils.INST.format.format(y));
			textElt.setTextContent(text);
			currentTicks.appendChild(textElt);
			return textElt;
		}
		return null;
	}

	@Override
	public void createPathTicksMoveTo(final double x, final double y) {
		if(currentPath != null) {
			currentPath.add(new SVGPathSegMoveto(x, y, false));
		}
	}

	@Override
	public void createPathTicksLineTo(final double x, final double y) {
		if(currentPath != null) {
			currentPath.add(new SVGPathSegLineto(x, y, false));
		}
	}

	@Override
	public void disablePathTicks(final boolean disable) {
		// Nothing to do.
	}

	@Override
	public void setPathTicksFill(final Color color) {
		if(currentTicks != null) {
			currentTicks.setFill(ShapeFactory.INST.createColorFX(color));
		}
	}
}
