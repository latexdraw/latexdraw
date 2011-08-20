package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IArc.ArcType;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.ISquare;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;
import net.sf.latexdraw.lang.LangTool;

/**
 * Defines the choices of edition of the canvas.<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.3"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IRectangle.class);
		}
	},
	DOT {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.4"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IDot.class);
		}
	},
	SQUARE {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.5"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(ISquare.class);
		}
	},
	RHOMBUS {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.6"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IRhombus.class);
		}
	},
	FREE_HAND {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.7"); //$NON-NLS-1$;
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IFreehand.class);
		}
	},
	TRIANGLE {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.8"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(ITriangle.class);
		}
	},
	LINES {
		@Override
		public String getLabel() {
			return LangTool.LANG.getString16("LaTeXDrawFrame.4");  //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IPolyline.class);
		}
	},
	CIRCLE {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.9"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(ICircle.class);
		}
	},
	GRID {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.10"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IGrid.class);
		}
	},
	AXES {
		@Override
		public String getLabel() {
			return LangTool.LANG.getString18("LaTeXDrawFrame.0"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IAxes.class);
		}
	},
	ELLIPSE {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.11"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IEllipse.class);
		}
	},
	POLYGON {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.12"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IPolygon.class);
		}
	},
//	SELECTION {//TODO relocate ? remove ?
//		@Override
//		public String getLabel() {
//			return LaTeXDrawLang.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.13"); //$NON-NLS-1$
//		}
//	},
	WEDGE {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.36"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			final IShape shape = DrawingTK.getFactory().newShape(IArc.class);
			((IArc)shape).setType(ArcType.WEDGE);
			return shape;
		}
	},
	CIRCLE_ARC {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.35"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			final IShape shape = DrawingTK.getFactory().newShape(ICircleArc.class);
			((ICircleArc)shape).setType(ArcType.ARC);
			return shape;
		}
	},
	CHORD {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.37"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			final IShape shape = DrawingTK.getFactory().newShape(IArc.class);
			((IArc)shape).setType(ArcType.CHORD);
			return shape;
		}
	},
	BEZIER_CURVE {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.63"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			final IShape shape = DrawingTK.getFactory().newShape(IBezierCurve.class);
			((IBezierCurve)shape).setClosed(IBezierCurve.CloseType.NONE);
			return shape;
		}
	},
	BEZIER_CURVE_CLOSED {
		@Override
		public String getLabel() {
			return LangTool.LANG.getString19("LaTeXDrawFrame.1"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			final IShape shape = DrawingTK.getFactory().newShape(IBezierCurve.class);
			((IBezierCurve)shape).setClosed(IBezierCurve.CloseType.CURVE);
			return shape;
		}
	},
	TEXT {
		@Override
		public String getLabel() {
			return LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.60"); //$NON-NLS-1$
		}

		@Override
		public IShape createShapeInstance() {
			return DrawingTK.getFactory().newShape(IText.class);
		}
	};


	/**
	 * @return The label of the edition choice.
	 * @since 3.0
	 */
	public abstract String getLabel();


	/**
	 * @return A new shape instance corresponding to the editing choice.
	 * @since 3.0
	 */
	public abstract IShape createShapeInstance();
}
