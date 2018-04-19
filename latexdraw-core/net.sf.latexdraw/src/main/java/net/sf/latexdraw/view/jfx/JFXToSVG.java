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
package net.sf.latexdraw.view.jfx;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGEllipseElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import net.sf.latexdraw.parsers.svg.path.SVGPathSeg;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegClosePath;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegCurvetoCubic;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegLineto;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegList;
import net.sf.latexdraw.parsers.svg.path.SVGPathSegMoveto;

/**
 * Converts JFX shapes to SVG elements.
 * Work in progress.
 * @author Arnaud Blouin
 */
public final class JFXToSVG {
	public static final JFXToSVG INSTANCE = new JFXToSVG();

	private JFXToSVG() {
		super();
	}

	public List<SVGElement> shapesToElements(final List<Node> nodes, final SVGDocument doc) {
		if(nodes == null) {
			return Collections.emptyList();
		}
		return nodes.stream().filter(node -> !node.isDisable() && node.isVisible()).map(shape -> {
			if(shape instanceof Shape) {
				return shapeToElement((Shape) shape, doc);
			}
			if(shape instanceof Group) {
				return groupToSVGGElement((Group) shape, doc);
			}
			return null;
		}).filter(elt -> elt != null).collect(Collectors.toList());
	}

	public SVGGElement groupToSVGGElement(final Group group, final SVGDocument doc) {
		final SVGGElement g = new SVGGElement(doc);
		shapesToElements(group.getChildren(), doc).forEach(elt -> g.appendChild(elt));
		return g;
	}

	public SVGElement shapeToElement(final Shape shape, final SVGDocument doc) {
		if(shape instanceof Path) {
			return pathToSVGPath((Path) shape, doc);
		}
		if(shape instanceof Ellipse) {
			return ellipseToSVGEllipse((Ellipse) shape, doc);
		}
		return null;
	}

	private void copyPropertiesToSVG(final SVGElement elt, final Shape shape) {
		elt.setStrokeWidth(shape.getStrokeWidth());
		elt.setStrokeLineCap(strokeLineCapToSVGLineCap(shape.getStrokeLineCap()));

		if(shape.getStroke() instanceof Color) {
			final Color col = (Color) shape.getStroke();
			elt.setStroke(ShapeFactory.INST.createColorFX(col));
			if(col.getOpacity() < 1d) {
				elt.setAttribute(SVGAttributes.SVG_STROKE_OPACITY, String.valueOf(col.getOpacity()));
			}
		}else {
			elt.setStroke(null);
		}

		if(shape.getFill() instanceof Color) {
			final Color col = (Color) shape.getFill();
			elt.setFill(ShapeFactory.INST.createColorFX(col));
			if(col.getOpacity() < 1d) {
				elt.setAttribute(SVGAttributes.SVG_FILL_OPACITY, String.valueOf(col.getOpacity()));
			}
		}else {
			elt.setFill(null);
		}
	}

	private String strokeLineCapToSVGLineCap(final StrokeLineCap cap) {
		switch(cap) {
			case SQUARE: return SVGAttributes.SVG_LINECAP_VALUE_SQUARE;
			case BUTT: return SVGAttributes.SVG_LINECAP_VALUE_BUTT;
			case ROUND: return SVGAttributes.SVG_LINECAP_VALUE_ROUND;
		}
		return null;
	}

	public SVGEllipseElement ellipseToSVGEllipse(final Ellipse ell, final SVGDocument doc) {
		final SVGEllipseElement elt = new SVGEllipseElement(ell.getCenterX(), ell.getCenterY(), ell.getRadiusX(), ell.getRadiusY(), doc);
		copyPropertiesToSVG(elt, ell);
		return elt;
	}

	/**
	 * Converts a JFX path to an SVGPath.
	 * @param path To JFX path to convert.
	 * @param doc The SVG document.
	 * @return The created SVGPath.
	 * @throws NullPointerException If one of the given parameter is null.
	 */
	public SVGPathElement pathToSVGPath(final Path path, final SVGDocument doc) {
		final SVGPathElement svgPath = new SVGPathElement(doc);
		final SVGPathSegList list = new SVGPathSegList();
		list.addAll(path.getElements().stream().map(elt -> createSVGPathSeg(elt)).filter(elt -> elt != null).collect(Collectors.toList()));
		svgPath.setPathData(list);
		copyPropertiesToSVG(svgPath, path);
		return svgPath;
	}

	private SVGPathSeg createSVGPathSeg(final PathElement elt) {
		if(elt instanceof LineTo) {
			return createSVGPathSegLineto((LineTo) elt);
		}
		if(elt instanceof MoveTo) {
			return createSVGPathSegMoveto((MoveTo) elt);
		}
		if(elt instanceof ClosePath) {
			return new SVGPathSegClosePath();
		}
		if(elt instanceof CubicCurveTo) {
			return createSVGPathSegCurvetoCubic((CubicCurveTo) elt);
		}
		return null;
	}

	public SVGPathSegLineto createSVGPathSegLineto(final LineTo elt) {
		return new SVGPathSegLineto(elt.getX(), elt.getY(), !elt.isAbsolute());
	}

	public SVGPathSegMoveto createSVGPathSegMoveto(final MoveTo elt) {
		return new SVGPathSegMoveto(elt.getX(), elt.getY(), !elt.isAbsolute());
	}

	public SVGPathSegCurvetoCubic createSVGPathSegCurvetoCubic(final CubicCurveTo elt) {
		return new SVGPathSegCurvetoCubic(elt.getX(), elt.getY(), elt.getControlX1(), elt.getControlY1(), elt.getControlX2(),
			elt.getControlY2(), !elt.isAbsolute());
	}
}
