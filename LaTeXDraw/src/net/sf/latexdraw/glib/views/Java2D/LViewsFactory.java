package net.sf.latexdraw.glib.views.Java2D;

import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewsFactory;

/**
 * The factory that creates views from given models.<br>
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
 * 03/10/08<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @version 3.0
 */
public class LViewsFactory implements IViewsFactory {
	public LViewsFactory() {
		super();
	}


	/**
	 * Creates a view from a shape.
	 * @param shape The shape used to create the view.
	 * @return The created view or null.
	 * @since 3.0
	 */
	@Override
	public IViewShape<?> generateView(final IShape shape) {
		IViewShape<?> view;

		if(shape==null)
			view = null;
		else if(shape instanceof ITriangle)
			view = new LTriangleView((ITriangle)shape);
		else if(shape instanceof IRhombus)
			view = new LRhombusView((IRhombus)shape);
		else if(shape instanceof ISquare)
			view = new LSquareView((ISquare)shape);
		else if(shape instanceof IRectangle)
			view = new LRectangleView((IRectangle)shape);
		else if(shape instanceof ICircle)
			view = new LCircleView((ICircle)shape);
		else if(shape instanceof ICircleArc)
			view = new LCircleArcView((ICircleArc)shape);
		else if(shape instanceof IArc)
			view = new LArcView((IArc)shape);
		else if(shape instanceof IEllipse)
			view = new LEllipseView<IEllipse>((IEllipse)shape);
		else if(shape instanceof IGroup)
			view = new LGroupView((IGroup)shape);
		else if(shape instanceof IPolyline)
			view = new LPolylineView((IPolyline)shape);
		else if(shape instanceof IPolygon)
			view = new LPolygonView((IPolygon)shape);
		else if(shape instanceof IBezierCurve)
			view = new LBezierCurveView((IBezierCurve)shape);
		else if(shape instanceof IGrid)
			view = new LGridView((IGrid)shape);
		else if(shape instanceof IAxes)
			view = new LAxesView((IAxes)shape);
		else if(shape instanceof IDot)
			view = new LDotView((IDot)shape);
		else if(shape instanceof IFreehand)
			view = new LFreeHandView((IFreehand)shape);
		else if(shape instanceof IText)
			view = new LTextView((IText)shape);
		else if(shape instanceof IPicture)
			view = new LPictureView((IPicture)shape);
		else
			view = null;

		return view;
	}
}
