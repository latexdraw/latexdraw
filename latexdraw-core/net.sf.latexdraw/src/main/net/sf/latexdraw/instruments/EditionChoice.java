package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IArc;
import net.sf.latexdraw.glib.models.interfaces.shape.IAxes;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid;
import net.sf.latexdraw.glib.models.interfaces.shape.IPicture;
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquare;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.glib.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.lang.LangTool;

/**
 * Defines the choices of edition of the canvas.<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 05/14/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public enum EditionChoice {
	RECT {
		@Override
		public IRectangle createShapeInstance() { return ShapeFactory.newShape(IRectangle.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, DOT {
		@Override
		public IDot createShapeInstance() {return ShapeFactory.newShape(IDot.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.9");
		}
	}, SQUARE {
		@Override
		public ISquare createShapeInstance() {return ShapeFactory.newShape(ISquare.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, RHOMBUS {
		@Override
		public IRhombus createShapeInstance() {return ShapeFactory.newShape(IRhombus.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, FREE_HAND {
		@Override
		public IFreehand createShapeInstance() {return ShapeFactory.newShape(IFreehand.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, TRIANGLE {
		@Override
		public ITriangle createShapeInstance() {return ShapeFactory.newShape(ITriangle.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, LINES {
		@Override
		public IPolyline createShapeInstance() {return ShapeFactory.newShape(IPolyline.class).get();}

		@Override
		public String getExplanations() {
			return "Right-click to add points. Left-click to add the last point.";
		}
	}, CIRCLE {
		@Override
		public ICircle createShapeInstance() {return ShapeFactory.newShape(ICircle.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, GRID {
		@Override
		public IGrid createShapeInstance() {return ShapeFactory.newShape(IGrid.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.6");
		}
	}, AXES {
		@Override
		public IAxes createShapeInstance() {return ShapeFactory.newShape(IAxes.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.7");
		}
	}, ELLIPSE {
		@Override
		public IEllipse createShapeInstance() {return ShapeFactory.newShape(IEllipse.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, POLYGON {
		@Override
		public IPolygon createShapeInstance() {return ShapeFactory.newShape(IPolygon.class).get();}

		@Override
		public String getExplanations() {
			return "Right-click to add points. Left-click to add the last point.";
		}
	}, WEDGE {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = ShapeFactory.newShape(IArc.class).get();
			shape.setArcStyle(ArcStyle.WEDGE);
			return shape;
		}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, CIRCLE_ARC {
		@Override
		public ICircleArc createShapeInstance() {
			final ICircleArc shape = ShapeFactory.newShape(ICircleArc.class).get();
			shape.setArcStyle(ArcStyle.ARC);
			return shape;
		}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, CHORD {
		@Override
		public IArc createShapeInstance() {
			final IArc shape = ShapeFactory.newShape(IArc.class).get();
			shape.setArcStyle(ArcStyle.CHORD);
			return shape;
		}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.8");
		}
	}, BEZIER_CURVE {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = ShapeFactory.newShape(IBezierCurve.class).get();
			shape.setIsClosed(false);
			return shape;
		}

		@Override
		public String getExplanations() {
			return "Right-click to add points. Left-click to add the last point.";
		}
	}, BEZIER_CURVE_CLOSED {
		@Override
		public IBezierCurve createShapeInstance() {
			final IBezierCurve shape = ShapeFactory.newShape(IBezierCurve.class).get();
			shape.setIsClosed(true);
			return shape;
		}

		@Override
		public String getExplanations() {
			return "Right-click to add points. Left-click to add the last point.";
		}
	}, TEXT {
		@Override
		public IText createShapeInstance() {return ShapeFactory.newShape(IText.class).get();}

		@Override
		public String getExplanations() {
			return LangTool.INSTANCE.getString19("MenusListener.5");
		}
	}, PICTURE {
		@Override
		public IPicture createShapeInstance() {return ShapeFactory.newShape(IPicture.class).get();}

		@Override
		public String getExplanations() {
			return "Left-click to show the file dialogue to select the picture to insert.";
		}
	}, PLOT {
		@Override
		public IPlot createShapeInstance() {return ShapeFactory.newShape(IPlot.class).get();}

		@Override
		public String getExplanations() {
			return "Left-click to show the text field to enter the formula. Then, press 'enter' to validate or 'escape' to cancel.";
		}
	};


	/**
	 * @return A new shape instance corresponding to the editing choice.
	 * @since 3.0
	 */
	public abstract IShape createShapeInstance();

	/**
	 * @return The explanations to draw the shape.
	 */
	public abstract String getExplanations();
}
