package net.sf.latexdraw.glib.views.Java2D.impl;

import net.sf.latexdraw.glib.models.interfaces.shape.IAxes;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
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
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewsFactory;

/**
 * The factory that creates views from given models.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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

	/**
	 * Creates the factory.
	 */
	public LViewsFactory() {
		super();
	}


	@Override
	public IViewShape createView(final IShape shape) {
		if(shape instanceof IGroup) return new LGroupView((IGroup)shape);
		if(shape instanceof IPlot) return new LPlotView((IPlot)shape);
		if(shape instanceof ISquare) return new LSquareView((ISquare)shape);
		if(shape instanceof IRectangle) return new LRectangleView((IRectangle)shape);
		if(shape instanceof IText) return new LTextView((IText)shape);
		if(shape instanceof ICircleArc) return new LCircleArcView((ICircleArc)shape);
		if(shape instanceof ICircle) return new LCircleView((ICircle)shape);
		if(shape instanceof IEllipse) return new LEllipseView<>((IEllipse)shape);
		if(shape instanceof ITriangle) return new LTriangleView((ITriangle)shape);
		if(shape instanceof IRhombus) return new LRhombusView((IRhombus)shape);
		if(shape instanceof IPolyline) return new LPolylineView((IPolyline)shape);
		if(shape instanceof IPolygon) return new LPolygonView((IPolygon)shape);
		if(shape instanceof IBezierCurve) return new LBezierCurveView((IBezierCurve)shape);
		if(shape instanceof IAxes) return new LAxesView((IAxes)shape);
		if(shape instanceof IGrid) return new LGridView((IGrid)shape);
		if(shape instanceof IDot) return new LDotView((IDot)shape);
		if(shape instanceof IPicture) return new LPictureView((IPicture)shape);
		if(shape instanceof IFreehand) return new LFreeHandView((IFreehand)shape);
		return null;
	}
}
