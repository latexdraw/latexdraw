package net.sf.latexdraw.instruments;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.*;

/**
 * Defines the choices of edition of the canvas.<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/14/10<br>
 * 
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public enum EditionChoice {
	RECT {
		@Override
		public IRectangle createShapeInstance() {
			return ShapeFactory.createRectangle();
		}
	},
	DOT {
		@Override
		public IDot createShapeInstance() {
			return ShapeFactory.createDot(ShapeFactory.createPoint());
		}
	},
	SQUARE {
		@Override
		public ISquare createShapeInstance() {
			return ShapeFactory.createSquare();
		}
	},
	RHOMBUS {
		@Override
		public IRhombus createShapeInstance() {
			return ShapeFactory.createRhombus();
		}
	},
	FREE_HAND {
		@Override
		public IFreehand createShapeInstance() {
			return ShapeFactory.createFreeHand();
		}
	},
	TRIANGLE {
		@Override
		public ITriangle createShapeInstance() {
			return ShapeFactory.createTriangle();
		}
	},
	LINES {
		@Override
		public IPolyline createShapeInstance() {
			return ShapeFactory.createPolyline();
		}
	},
	CIRCLE {
		@Override
		public ICircle createShapeInstance() {
			return ShapeFactory.createCircle();
		}
	},
	GRID {
		@Override
		public IGrid createShapeInstance() {
			return ShapeFactory.createGrid(ShapeFactory.createPoint());
		}
	},
	AXES {
		@Override
		public IAxes createShapeInstance() {
			return ShapeFactory.createAxes(ShapeFactory.createPoint());
		}
	},
	ELLIPSE {
		@Override
		public IEllipse createShapeInstance() {
			return ShapeFactory.createEllipse();
		}
	},
	POLYGON {
		@Override
		public IPolygon createShapeInstance() {
			return ShapeFactory.createPolygon();
		}
	},
	WEDGE {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = ShapeFactory.createCircleArc();
			shape.setArcStyle(ArcStyle.WEDGE);
			return shape;
		}
	},
	CIRCLE_ARC {
		@Override
		public ICircleArc createShapeInstance() {
			final ICircleArc shape = ShapeFactory.createCircleArc();
			shape.setArcStyle(ArcStyle.ARC);
			return shape;
		}
	},
	CHORD {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = ShapeFactory.createCircleArc();
			shape.setArcStyle(ArcStyle.CHORD);
			return shape;
		}
	},
	BEZIER_CURVE {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = ShapeFactory.createBezierCurve();
			shape.setIsClosed(false);
			return shape;
		}
	},
	BEZIER_CURVE_CLOSED {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = ShapeFactory.createBezierCurve();
			shape.setIsClosed(true);
			return shape;
		}
	},
	TEXT {
		@Override
		public IText createShapeInstance() {
			return ShapeFactory.createText();
		}
	},
	PICTURE {
		@Override
		public IPicture createShapeInstance() {
			return ShapeFactory.createPicture(ShapeFactory.createPoint());
		}
	},
	PLOT {
		@Override
		public IPlot createShapeInstance() {
			return ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 5, "x", false);
		}
	};

	/**
	 * @return A new shape instance corresponding to the editing choice.
	 * @since 3.0
	 */
	public abstract IShape createShapeInstance();
}
