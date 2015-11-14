/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.view.jfx;

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

/**
 * The factory that creates views from given models.<br>
 * 2015-11-13<br>
 * @author Arnaud BLOUIN
 * @since 4.0
 */
public final class ViewFactory {
	/** The singleton. */
	public static final @NonNull ViewFactory INSTANCE = new ViewFactory();

	private ViewFactory() {
		super();
	}

	/**
	 * Creates a view from a shape.
	 * @param shape The shape used to create the view.
	 * @return The created view or empty.
	 * @since 3.0
	 */
	@SuppressWarnings("null")
	public @NonNull Optional<ViewShape<?, ?>> createView(final IShape shape) {
		// if(shape instanceof IGroup) return new LGroupView((IGroup)shape);
		// if(shape instanceof IPlot) return new LPlotView((IPlot)shape);
		// if(shape instanceof ISquare) return new LSquareView((ISquare)shape);
		if(shape instanceof IRectangle) return Optional.of(new ViewRectangle((IRectangle)shape));
		// if(shape instanceof IText) return new LTextView((IText)shape);
		// if(shape instanceof ICircleArc) return new LCircleArcView((ICircleArc)shape);
		// if(shape instanceof ICircle) return new LCircleView((ICircle)shape);
		// if(shape instanceof IEllipse) return new LEllipseView<>((IEllipse)shape);
		// if(shape instanceof ITriangle) return new LTriangleView((ITriangle)shape);
		// if(shape instanceof IRhombus) return new LRhombusView((IRhombus)shape);
		// if(shape instanceof IPolyline) return new LPolylineView((IPolyline)shape);
		// if(shape instanceof IPolygon) return new LPolygonView((IPolygon)shape);
		// if(shape instanceof IBezierCurve) return new LBezierCurveView((IBezierCurve)shape);
		// if(shape instanceof IAxes) return new LAxesView((IAxes)shape);
		// if(shape instanceof IGrid) return new LGridView((IGrid)shape);
		// if(shape instanceof IDot) return new LDotView((IDot)shape);
		// if(shape instanceof IPicture) return new LPictureView((IPicture)shape);
		// if(shape instanceof IFreehand) return new LFreeHandView((IFreehand)shape);
		return Optional.empty();
	}
}
