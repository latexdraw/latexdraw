package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.shape.*;

import java.util.Optional;

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
	public Optional<PSTShapeView<?>> createView(final IShape shape) {
		if(shape instanceof IGroup) return Optional.of(new PSTGroupView((IGroup)shape));
		if(shape instanceof IPlot) return Optional.of(new PSTPlotView((IPlot)shape));
		if(shape instanceof ISquare) return Optional.of(new PSTSquareView((ISquare)shape));
		if(shape instanceof IRectangle) return Optional.of(new PSTRectView((IRectangle)shape));
		if(shape instanceof IText) return Optional.of(new PSTTextView((IText)shape));
		if(shape instanceof ICircleArc) return Optional.of(new PSTArcView((IArc)shape));
		if(shape instanceof ICircle) return Optional.of(new PSTCircleView((ICircle)shape));
		if(shape instanceof IEllipse) return Optional.of(new PSTEllipseView((IEllipse)shape));
		if(shape instanceof ITriangle) return Optional.of(new PSTTriangleView((ITriangle)shape));
		if(shape instanceof IRhombus) return Optional.of(new PSTRhombusView((IRhombus)shape));
		if(shape instanceof IPolyline) return Optional.of(new PSTLinesView((IPolyline)shape));
		if(shape instanceof IPolygon) return Optional.of(new PSTPolygonView((IPolygon)shape));
		if(shape instanceof IBezierCurve) return Optional.of(new PSTBezierCurveView((IBezierCurve)shape));
		if(shape instanceof IAxes) return Optional.of(new PSTAxesView((IAxes)shape));
		if(shape instanceof IGrid) return Optional.of(new PSTGridView((IGrid)shape));
		if(shape instanceof IDot) return Optional.of(new PSTDotView((IDot)shape));
		if(shape instanceof IPicture) return Optional.of(new PSTPictureView((IPicture)shape));
		if(shape instanceof IFreehand) return Optional.of(new PSTFreeHandView((IFreehand)shape));
		return Optional.empty();
	}
}
