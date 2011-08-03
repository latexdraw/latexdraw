package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IPicture;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRhombus;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;

/**
 * Defines a generator that generates PSTricks views from given models.<br>
 *<br>
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
 * 04/15/08<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @version 3.0
 */
public final class PSTViewsFactory {
	/** The singleton. */
	public static final PSTViewsFactory INSTANCE = new PSTViewsFactory();
	
	private PSTViewsFactory() {
		super();
	}
	
	/**
	 * Creates a view from a shape.
	 * @param shape The shape used to create the view.
	 * @return The created view or null.
	 * @since 3.0
	 */
	public PSTShapeView<?> generateView(final IShape shape) {
		PSTShapeView<?> view;

		if(shape==null)
			view = null;
		else if(shape instanceof ITriangle)
			view = new PSTTriangleView((ITriangle)shape);
		else if(shape instanceof IRhombus)
			view = new PSTRhombusView((IRhombus)shape);
		else if(shape instanceof IRectangle)
			view = new PSTRectView((IRectangle)shape);
		else if(shape instanceof IArc)
			view = new PSTArcView((IArc)shape);
		else if(shape instanceof ICircle)
			view = new PSTCircleView((ICircle)shape);
		else if(shape instanceof IEllipse)
			view = new PSTEllipseView((IEllipse)shape);
		else if(shape instanceof IGroup)
			view = new PSTGroupView((IGroup)shape);
		else if(shape instanceof IPolyline)
			view = new PSTLinesView((IPolyline)shape);
		else if(shape instanceof IPolygon)
			view = new PSTPolygonView((IPolygon)shape);
		else if(shape instanceof IBezierCurve)
			view = new PSTBezierCurveView((IBezierCurve)shape);
		else if(shape instanceof IGrid)
			view = new PSTGridView((IGrid)shape);
		else if(shape instanceof IAxes)
			view = new PSTAxesView((IAxes)shape);
		else if(shape instanceof IDot)
			view = new PSTDotView((IDot)shape);
		else if(shape instanceof IFreehand)
			view = new PSTFreeHandView((IFreehand)shape);
		else if(shape instanceof IText)
			view = new PSTTextView((IText)shape);
		else if(shape instanceof IPicture)
			view = new PSTPictureView((IPicture)shape);
		else
			view = null;

		return view;
	}
}
