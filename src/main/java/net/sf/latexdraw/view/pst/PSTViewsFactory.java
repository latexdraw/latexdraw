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
package net.sf.latexdraw.view.pst;

import java.util.Optional;
import net.sf.latexdraw.model.api.shape.Arc;
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
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;

/**
 * A singleton factory that produces PSTricks views.
 * @author Arnaud Blouin
 */
public final class PSTViewsFactory implements PSTViewProducer {
	@Inject private SystemService system;
	@Inject private LangService lang;

	public PSTViewsFactory() {
		super();
	}

	@Override
	public <T extends Shape> Optional<PSTShapeView<T>> createView(final T shape) {
		if(shape instanceof Group) {
			return Optional.of((PSTShapeView<T>) new PSTGroupView((Group) shape, this));
		}
		if(shape instanceof Plot) {
			return Optional.of((PSTShapeView<T>) new PSTPlotView((Plot) shape));
		}
		if(shape instanceof Square) {
			return Optional.of((PSTShapeView<T>) new PSTSquareView((Square) shape));
		}
		if(shape instanceof Rectangle) {
			return Optional.of((PSTShapeView<T>) new PSTRectView((Rectangle) shape));
		}
		if(shape instanceof Text) {
			return Optional.of((PSTShapeView<T>) new PSTTextView((Text) shape));
		}
		if(shape instanceof CircleArc) {
			return Optional.of((PSTShapeView<T>) new PSTArcView((Arc) shape));
		}
		if(shape instanceof Circle) {
			return Optional.of((PSTShapeView<T>) new PSTCircleView((Circle) shape));
		}
		if(shape instanceof Ellipse) {
			return Optional.of((PSTShapeView<T>) new PSTEllipseView((Ellipse) shape));
		}
		if(shape instanceof Triangle) {
			return Optional.of((PSTShapeView<T>) new PSTTriangleView((Triangle) shape));
		}
		if(shape instanceof Rhombus) {
			return Optional.of((PSTShapeView<T>) new PSTRhombusView((Rhombus) shape));
		}
		if(shape instanceof Polyline) {
			return Optional.of((PSTShapeView<T>) new PSTLinesView((Polyline) shape));
		}
		if(shape instanceof Polygon) {
			return Optional.of((PSTShapeView<T>) new PSTPolygonView((Polygon) shape));
		}
		if(shape instanceof BezierCurve) {
			return Optional.of((PSTShapeView<T>) new PSTBezierCurveView((BezierCurve) shape));
		}
		if(shape instanceof Axes) {
			return Optional.of((PSTShapeView<T>) new PSTAxesView((Axes) shape));
		}
		if(shape instanceof Grid) {
			return Optional.of((PSTShapeView<T>) new PSTGridView((Grid) shape));
		}
		if(shape instanceof Dot) {
			return Optional.of((PSTShapeView<T>) new PSTDotView((Dot) shape));
		}
		if(shape instanceof Picture) {
			return Optional.of((PSTShapeView<T>) new PSTPictureView((Picture) shape, system, lang.getBundle()));
		}
		if(shape instanceof Freehand) {
			return Optional.of((PSTShapeView<T>) new PSTFreeHandView((Freehand) shape));
		}
		return Optional.empty();
	}
}
