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
package net.sf.latexdraw.instrument;

import java.util.Arrays;
import java.util.Collections;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
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
import net.sf.latexdraw.util.SystemService;

/**
 * Defines the choices of edition of the canvas.
 * @author Arnaud BLOUIN
 */
public enum EditionChoice {
	RECT {
		@Override
		public Rectangle createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createRectangle();
		}
	}, DOT {
		@Override
		public Dot createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		}
	}, SQUARE {
		@Override
		public Square createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createSquare();
		}
	}, RHOMBUS {
		@Override
		public Rhombus createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createRhombus();
		}
	}, FREE_HAND {
		@Override
		public Freehand createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createFreeHand(Collections.singletonList(ShapeFactory.INST.createPoint()));
		}
	}, TRIANGLE {
		@Override
		public Triangle createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createTriangle();
		}
	}, LINES {
		@Override
		public Polyline createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint()));
		}
	}, CIRCLE {
		@Override
		public Circle createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createCircle();
		}
	}, GRID {
		@Override
		public Grid createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		}
	}, AXES {
		@Override
		public Axes createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		}
	}, ELLIPSE {
		@Override
		public Ellipse createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createEllipse();
		}
	}, POLYGON {
		@Override
		public Polygon createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createPolygon(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint()));
		}
	}, CIRCLE_ARC {
		@Override
		public CircleArc createShapeInstance(final SystemService system) {
			final CircleArc shape = ShapeFactory.INST.createCircleArc();
			shape.setArcStyle(ArcStyle.ARC);
			return shape;
		}
	}, BEZIER_CURVE {
		@Override
		public BezierCurve createShapeInstance(final SystemService system) {
			final BezierCurve shape = ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint()));
			shape.setOpened(true);
			return shape;
		}
	}, TEXT {
		@Override
		public Text createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createText();
		}
	}, PICTURE {
		@Override
		public Picture createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint(), system);
		}
	}, PLOT {
		@Override
		public Plot createShapeInstance(final SystemService system) {
			return ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 5, "x", false); //NON-NLS
		}
	};

	/**
	 * @return A new shape instance corresponding to the editing choice.
	 */
	public abstract Shape createShapeInstance(final SystemService system);
}
