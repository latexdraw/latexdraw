package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.shape.IArc;
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

/**
 * Defines a generator that generates PSTricks views from given models.<br>
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
	public PSTShapeView<?> createView(final IShape shape) {
		if(shape instanceof IGroup) return new PSTGroupView((IGroup)shape);
		if(shape instanceof IPlot) return new PSTPlotView((IPlot)shape);
		if(shape instanceof ISquare) return new PSTSquareView((ISquare)shape);
		if(shape instanceof IRectangle) return new PSTRectView((IRectangle)shape);
		if(shape instanceof IText) return new PSTTextView((IText)shape);
		if(shape instanceof ICircleArc) return new PSTArcView((IArc)shape);
		if(shape instanceof ICircle) return new PSTCircleView((ICircle)shape);
		if(shape instanceof IEllipse) return new PSTEllipseView((IEllipse)shape);
		if(shape instanceof ITriangle) return new PSTTriangleView((ITriangle)shape);
		if(shape instanceof IRhombus) return new PSTRhombusView((IRhombus)shape);
		if(shape instanceof IPolyline) return new PSTLinesView((IPolyline)shape);
		if(shape instanceof IPolygon) return new PSTPolygonView((IPolygon)shape);
		if(shape instanceof IBezierCurve) return new PSTBezierCurveView((IBezierCurve)shape);
		if(shape instanceof IAxes) return new PSTAxesView((IAxes)shape);
		if(shape instanceof IGrid) return new PSTGridView((IGrid)shape);
		if(shape instanceof IDot) return new PSTDotView((IDot)shape);
		if(shape instanceof IPicture) return new PSTPictureView((IPicture)shape);
		if(shape instanceof IFreehand) return new PSTFreeHandView((IFreehand)shape);
		return null;
	}
}
