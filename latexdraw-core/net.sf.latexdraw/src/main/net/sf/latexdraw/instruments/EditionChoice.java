package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IArc.ArcStyle;

/**
 * Defines the choices of edition of the canvas.<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 05/14/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public enum EditionChoice {//TODO remove "LaTeXDrawFrame.2"?
	RECT {
		@Override
		public IRectangle createShapeInstance() {
			return DrawingTK.getFactory().newShape(IRectangle.class).get();
		}
	},
	DOT {
		@Override
		public IDot createShapeInstance() {
			return DrawingTK.getFactory().newShape(IDot.class).get();
		}
	},
	SQUARE {
		@Override
		public ISquare createShapeInstance() {
			return DrawingTK.getFactory().newShape(ISquare.class).get();
		}
	},
	RHOMBUS {
		@Override
		public IRhombus createShapeInstance() {
			return DrawingTK.getFactory().newShape(IRhombus.class).get();
		}
	},
	FREE_HAND {
		@Override
		public IFreehand createShapeInstance() {
			return DrawingTK.getFactory().newShape(IFreehand.class).get();
		}
	},
	TRIANGLE {
		@Override
		public ITriangle createShapeInstance() {
			return DrawingTK.getFactory().newShape(ITriangle.class).get();
		}
	},
	LINES {
		@Override
		public IPolyline createShapeInstance() {
			return DrawingTK.getFactory().newShape(IPolyline.class).get();
		}
	},
	CIRCLE {
		@Override
		public ICircle createShapeInstance() {
			return DrawingTK.getFactory().newShape(ICircle.class).get();
		}
	},
	GRID {
		@Override
		public IGrid createShapeInstance() {
			return DrawingTK.getFactory().newShape(IGrid.class).get();
		}
	},
	AXES {
		@Override
		public IAxes createShapeInstance() {
			return DrawingTK.getFactory().newShape(IAxes.class).get();
		}
	},
	ELLIPSE {
		@Override
		public IEllipse createShapeInstance() {
			return DrawingTK.getFactory().newShape(IEllipse.class).get();
		}
	},
	POLYGON {
		@Override
		public IPolygon createShapeInstance() {
			return DrawingTK.getFactory().newShape(IPolygon.class).get();
		}
	},
	WEDGE {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = DrawingTK.getFactory().newShape(IArc.class).get();
			shape.setArcStyle(ArcStyle.WEDGE);
			return shape;
		}
	},
	CIRCLE_ARC {
		@Override
		public ICircleArc createShapeInstance() {
			final ICircleArc shape = DrawingTK.getFactory().newShape(ICircleArc.class).get();
			shape.setArcStyle(ArcStyle.ARC);
			return shape;
		}
	},
	CHORD {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = DrawingTK.getFactory().newShape(IArc.class).get();
			shape.setArcStyle(ArcStyle.CHORD);
			return shape;
		}
	},
	BEZIER_CURVE {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = DrawingTK.getFactory().newShape(IBezierCurve.class).get();
			shape.setIsClosed(false);
			return shape;
		}
	},
	BEZIER_CURVE_CLOSED {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = DrawingTK.getFactory().newShape(IBezierCurve.class).get();
			shape.setIsClosed(true);
			return shape;
		}
	},
	TEXT {
		@Override
		public IText createShapeInstance() {
			return DrawingTK.getFactory().newShape(IText.class).get();
		}
	},
	PICTURE {
		@Override
		public IPicture createShapeInstance() {
			return DrawingTK.getFactory().newShape(IPicture.class).get();
		}
	};


	/**
	 * @return A new shape instance corresponding to the editing choice.
	 * @since 3.0
	 */
	public abstract IShape createShapeInstance();
}
