/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.view.jfx;

import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import java.util.Optional;

/**
 * The factory that creates views from given models.
 */
@NonNullByDefault
public final class ViewFactory {
	/** The singleton. */
	public static final ViewFactory INSTANCE = new ViewFactory();

	private ViewFactory() {
		super();
	}

	/**
	 * Creates a view from a shape.
	 * @param shape The shape used to create the view.
	 * @return The created view or empty.
	 * @since 3.0
	 */
	public Optional<ViewShape<?>> createView(final @Nullable IShape shape) {
		// if(shape instanceof IGroup) return new LGroupView((IGroup)shape);
		// if(shape instanceof IPlot) return new LPlotView((IPlot)shape);
		if(shape instanceof ISquare) return Optional.of(new ViewSquare((ISquare) shape));
		if(shape instanceof IRectangle) return Optional.of(new ViewRectangle((IRectangle) shape));
		if(shape instanceof IText) return Optional.of(new ViewTextText((IText)shape));
		// if(shape instanceof ICircleArc) return new LCircleArcView((ICircleArc)shape);
		if(shape instanceof ICircle) return Optional.of(new ViewCircle((ICircle) shape));
		if(shape instanceof IEllipse) return Optional.of(new ViewEllipse((IEllipse) shape));
		if(shape instanceof ITriangle) return Optional.of(new ViewTriangle((ITriangle) shape));
		if(shape instanceof IRhombus) return Optional.of(new ViewRhombus((IRhombus) shape));
		if(shape instanceof IPolyline) return Optional.of(new ViewPolyline((IPolyline) shape));
		if(shape instanceof IPolygon) return Optional.of(new ViewPolygon((IPolygon) shape));
		// if(shape instanceof IBezierCurve) return new LBezierCurveView((IBezierCurve)shape);
		// if(shape instanceof IAxes) return new LAxesView((IAxes)shape);
		if(shape instanceof IGrid) return Optional.of(new ViewGrid((IGrid)shape));
		// if(shape instanceof IDot) return new LDotView((IDot)shape);
		if(shape instanceof IPicture) return Optional.of(new ViewPicture((IPicture) shape));
		// if(shape instanceof IFreehand) return new LFreeHandView((IFreehand)shape);
		return Optional.empty();
	}
}
