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

import java.util.Optional;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
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
	public <T extends IShape, S extends ViewShape<T>> Optional<S> createView(final @Nullable T shape) {
		if(shape instanceof IGroup) return Optional.of((S) new ViewGroup((IGroup) shape));
		// if(shape instanceof IPlot) return new LPlotView((IPlot)shape);
		if(shape instanceof ISquare) return Optional.of((S) new ViewSquare((ISquare) shape));
		if(shape instanceof IRectangle) return Optional.of((S) new ViewRectangle((IRectangle) shape));
		if(shape instanceof IText) return Optional.of((S) new ViewTextText((IText) shape));
		// if(shape instanceof ICircleArc) return new LCircleArcView((ICircleArc)shape);
		if(shape instanceof ICircle) return Optional.of((S) new ViewCircle((ICircle) shape));
		if(shape instanceof IEllipse) return Optional.of((S) new ViewEllipse((IEllipse) shape));
		if(shape instanceof ITriangle) return Optional.of((S) new ViewTriangle((ITriangle) shape));
		if(shape instanceof IRhombus) return Optional.of((S) new ViewRhombus((IRhombus) shape));
		if(shape instanceof IPolyline) return Optional.of((S) new ViewPolyline((IPolyline) shape));
		if(shape instanceof IPolygon) return Optional.of((S) new ViewPolygon((IPolygon) shape));
		if(shape instanceof IBezierCurve) return Optional.of((S) new ViewBezierCurve((IBezierCurve) shape));
		if(shape instanceof IAxes) return Optional.of((S) new ViewAxes((IAxes) shape));
		if(shape instanceof IGrid) return Optional.of((S) new ViewGrid((IGrid) shape));
		// if(shape instanceof IDot) return new LDotView((IDot)shape);
		if(shape instanceof IPicture) return Optional.of((S) new ViewPicture((IPicture) shape));
		if(shape instanceof IFreehand) return Optional.of((S) new ViewFreeHand((IFreehand) shape));
		return Optional.empty();
	}
}
