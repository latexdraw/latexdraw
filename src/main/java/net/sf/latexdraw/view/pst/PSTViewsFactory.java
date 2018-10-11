/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import java.util.Optional;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
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
 * A singleton factory that produces PSTricks views.
 * @author Arnaud Blouin
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
	public <T extends IShape> Optional<PSTShapeView<T>> createView(final T shape) {
		if(shape instanceof IGroup) {
			return Optional.of((PSTShapeView<T>) new PSTGroupView((IGroup) shape));
		}
		if(shape instanceof IPlot) {
			return Optional.of((PSTShapeView<T>) new PSTPlotView((IPlot) shape));
		}
		if(shape instanceof ISquare) {
			return Optional.of((PSTShapeView<T>) new PSTSquareView((ISquare) shape));
		}
		if(shape instanceof IRectangle) {
			return Optional.of((PSTShapeView<T>) new PSTRectView((IRectangle) shape));
		}
		if(shape instanceof IText) {
			return Optional.of((PSTShapeView<T>) new PSTTextView((IText) shape));
		}
		if(shape instanceof ICircleArc) {
			return Optional.of((PSTShapeView<T>) new PSTArcView((IArc) shape));
		}
		if(shape instanceof ICircle) {
			return Optional.of((PSTShapeView<T>) new PSTCircleView((ICircle) shape));
		}
		if(shape instanceof IEllipse) {
			return Optional.of((PSTShapeView<T>) new PSTEllipseView((IEllipse) shape));
		}
		if(shape instanceof ITriangle) {
			return Optional.of((PSTShapeView<T>) new PSTTriangleView((ITriangle) shape));
		}
		if(shape instanceof IRhombus) {
			return Optional.of((PSTShapeView<T>) new PSTRhombusView((IRhombus) shape));
		}
		if(shape instanceof IPolyline) {
			return Optional.of((PSTShapeView<T>) new PSTLinesView((IPolyline) shape));
		}
		if(shape instanceof IPolygon) {
			return Optional.of((PSTShapeView<T>) new PSTPolygonView((IPolygon) shape));
		}
		if(shape instanceof IBezierCurve) {
			return Optional.of((PSTShapeView<T>) new PSTBezierCurveView((IBezierCurve) shape));
		}
		if(shape instanceof IAxes) {
			return Optional.of((PSTShapeView<T>) new PSTAxesView((IAxes) shape));
		}
		if(shape instanceof IGrid) {
			return Optional.of((PSTShapeView<T>) new PSTGridView((IGrid) shape));
		}
		if(shape instanceof IDot) {
			return Optional.of((PSTShapeView<T>) new PSTDotView((IDot) shape));
		}
		if(shape instanceof IPicture) {
			return Optional.of((PSTShapeView<T>) new PSTPictureView((IPicture) shape));
		}
		if(shape instanceof IFreehand) {
			return Optional.of((PSTShapeView<T>) new PSTFreeHandView((IFreehand) shape));
		}
		return Optional.empty();
	}
}
