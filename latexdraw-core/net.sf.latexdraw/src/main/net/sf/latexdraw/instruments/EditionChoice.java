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
package net.sf.latexdraw.instruments;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
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

/**
 * Defines the choices of edition of the canvas.
 * @author Arnaud BLOUIN
 */
public enum EditionChoice {
	RECT {
		@Override
		public IRectangle createShapeInstance() {
			return ShapeFactory.INST.createRectangle();
		}
	}, DOT {
		@Override
		public IDot createShapeInstance() {
			return ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		}
	}, SQUARE {
		@Override
		public ISquare createShapeInstance() {
			return ShapeFactory.INST.createSquare();
		}
	}, RHOMBUS {
		@Override
		public IRhombus createShapeInstance() {
			return ShapeFactory.INST.createRhombus();
		}
	}, FREE_HAND {
		@Override
		public IFreehand createShapeInstance() {
			return ShapeFactory.INST.createFreeHand();
		}
	}, TRIANGLE {
		@Override
		public ITriangle createShapeInstance() {
			return ShapeFactory.INST.createTriangle();
		}
	}, LINES {
		@Override
		public IPolyline createShapeInstance() {
			return ShapeFactory.INST.createPolyline();
		}
	}, CIRCLE {
		@Override
		public ICircle createShapeInstance() {
			return ShapeFactory.INST.createCircle();
		}
	}, GRID {
		@Override
		public IGrid createShapeInstance() {
			return ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		}
	}, AXES {
		@Override
		public IAxes createShapeInstance() {
			return ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		}
	}, ELLIPSE {
		@Override
		public IEllipse createShapeInstance() {
			return ShapeFactory.INST.createEllipse();
		}
	}, POLYGON {
		@Override
		public IPolygon createShapeInstance() {
			return ShapeFactory.INST.createPolygon();
		}
	}, WEDGE {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = ShapeFactory.INST.createCircleArc();
			shape.setArcStyle(ArcStyle.WEDGE);
			return shape;
		}
	}, CIRCLE_ARC {
		@Override
		public ICircleArc createShapeInstance() {
			final ICircleArc shape = ShapeFactory.INST.createCircleArc();
			shape.setArcStyle(ArcStyle.ARC);
			return shape;
		}
	}, CHORD {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = ShapeFactory.INST.createCircleArc();
			shape.setArcStyle(ArcStyle.CHORD);
			return shape;
		}
	}, BEZIER_CURVE {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = ShapeFactory.INST.createBezierCurve();
			shape.setIsClosed(false);
			return shape;
		}
	}, BEZIER_CURVE_CLOSED {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = ShapeFactory.INST.createBezierCurve();
			shape.setIsClosed(true);
			return shape;
		}
	}, TEXT {
		@Override
		public IText createShapeInstance() {
			return ShapeFactory.INST.createText();
		}
	}, PICTURE {
		@Override
		public IPicture createShapeInstance() {
			return ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint());
		}
	}, PLOT {
		@Override
		public IPlot createShapeInstance() {
			return ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 5, "x", false);
		}
	};

	/**
	 * @return A new shape instance corresponding to the editing choice.
	 * @since 3.0
	 */
	public abstract IShape createShapeInstance();
}
