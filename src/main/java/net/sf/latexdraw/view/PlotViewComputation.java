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
package net.sf.latexdraw.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Shape;

/**
 * @author Arnaud Blouin
 */
public interface PlotViewComputation {
	default Point getPolarPoint(final Plot shape, final double x, final double xs, final double ys, final double posX, final double posY) {
		final double radius = shape.getY(x);
		final double angle = Math.toRadians(x);
		final double x1 = radius * Math.cos(angle);
		final double y1 = -radius * Math.sin(angle);
		return ShapeFactory.INST.createPoint(x1 * Shape.PPC * xs + posX, y1 * Shape.PPC * ys + posY);
	}

	default List<Point> fillPoints(final Plot shape, final double posX, final double posY, final double minX,
								final double maxX, final double step) {
		final double xs = shape.getXScale();
		final double ys = shape.getYScale();
		double x = minX;
		final List<Point> pts = new ArrayList<>();

		if(shape.isPolar()) {
			for(int i = 0; i < shape.getNbPlottedPoints(); i++, x += step) {
				pts.add(getPolarPoint(shape, x, xs, ys, posX, posY));
			}
		}else {
			for(int i = 0; i < shape.getNbPlottedPoints(); i++, x += step) {
				pts.add(ShapeFactory.INST.createPoint(x * Shape.PPC * xs + posX, -shape.getY(x) * Shape.PPC * ys + posY));
			}
		}

		return pts;
	}


	default List<Dot> updatePoints(final Plot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		return ShapeFactory.INST.createPolyline(fillPoints(shape, posX, posY, minX, maxX, step)).getPoints().stream().map(pt -> {
			final Dot dot = ShapeFactory.INST.createDot(pt);
			dot.copy(shape);
			dot.setPosition(pt);
			dot.setRotationAngle(0d);
			return dot;
		}).collect(Collectors.toList());
	}


	default Polygon updatePolygon(final Plot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		final Polygon pg = ShapeFactory.INST.createPolygon(fillPoints(shape, posX, posY, minX, maxX, step));
		pg.copy(shape);
		return pg;
	}


	default Polyline updateLine(final Plot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		final Polyline pl = ShapeFactory.INST.createPolyline(fillPoints(shape, posX, posY, minX, maxX, step));
		pl.copy(shape);
		return pl;
	}


	default BezierCurve updateCurve(final Plot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		// The algorithm follows this definition:
		// https://stackoverflow.com/questions/15864441/how-to-make-a-line-curve-through-points
		final double scale = 0.33d;
		final BezierCurve bc = ShapeFactory.INST.createBezierCurve(fillPoints(shape, posX, posY, minX, maxX, step));

		bc.setOpened(shape.getPlotStyle() != PlotStyle.CCURVE);
		bc.copy(shape);
		int i = 0;
		final int last = bc.getPoints().size() - 1;

		for(final Point pt : bc.getPoints()) {
			if(i == 0) {
				final Point p2 = bc.getPtAt(i + 1);
				final Point tangent = p2.substract(pt);
				final Point q1 = pt.add(tangent.zoom(scale));
				bc.setXFirstCtrlPt(q1.getX(), i);
				bc.setYFirstCtrlPt(q1.getY(), i);
			}else if(i == last) {
				final Point p0 = bc.getPtAt(i - 1);
				final Point tangent = pt.substract(p0);
				final Point q0 = pt.substract(tangent.zoom(scale));
				bc.setXFirstCtrlPt(q0.getX(), i);
				bc.setYFirstCtrlPt(q0.getY(), i);
			}else {
				final Point p0 = bc.getPtAt(i - 1);
				final Point p2 = bc.getPtAt(i + 1);
				final Point tangent = p2.substract(p0).normalise();
				final Point q0 = pt.substract(tangent.zoom(scale * pt.substract(p0).magnitude()));
				bc.setXFirstCtrlPt(q0.getX(), i);
				bc.setYFirstCtrlPt(q0.getY(), i);
			}
			i++;
		}
		bc.updateSecondControlPoints();
		return bc;
	}
}
