package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

/**
 * Defines a view of the IRectangularView model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 03/01/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
abstract class LRectangularView<S extends IShape> extends LShapeView<S> {
	/**
	 * Initialises a rectangular view.
	 * @param model The rectangular model.
	 * @since 3.0
	 */
	protected LRectangularView(final S model) {
		super(model);
	}


	protected double getBorderGap() {
		final double thickness = shape.getThickness();
		switch(shape.getBordersPosition()) {
			case MID: return shape.hasDbleBord() ? thickness+shape.getDbleBordSep()/2. : thickness/2.;
			case OUT: return shape.hasDbleBord() ? thickness*2.+shape.getDbleBordSep() : thickness;
			case INTO:return 0.0;
		}
		return 0.0;
	}


	@Override
	protected void updateGeneralPathInside() {
		updateGeneralPath(shape.getThickness());
	}



	@Override
	protected void updateGeneralPathMiddle() {
		updateGeneralPath(0.);
	}



	@Override
	protected void updateGeneralPathOutside() {
		updateGeneralPath(-shape.getThickness());
	}



	/**
	 * Update the path of the rectangular shape.
	 * @param gap The value removed from the height and the width of the shape. Can equal 0.
	 * @since 3.0
	 */
	protected void updateGeneralPath(final double gap) {
		final IPoint tl 	= shape.getTopLeftPoint();
		final IPoint br 	= shape.getBottomRightPoint();

		path.reset();
		setRectangularShape(path, tl.getX()+gap/2., tl.getY()+gap/2., br.getX()-tl.getX()-gap, br.getY()-tl.getY()-gap);
	}



	/**
	 * Creates a rectangular shape in the given path using the given information. The path is not cleared.
	 * @param path The path to fill.
	 * @param tlx The X-coordinate of the top-left of the rectangular shape to draw.
	 * @param tly The Y-coordinate of the top-left of the rectangular shape to draw.
	 * @param width The width of the rectangular shape.
	 * @param height The height of the rectangular shape.
	 * @since 3.0
	 */
	protected abstract void setRectangularShape(final Path2D path, final double tlx, final double tly, final double width, final double height);



	@Override
	protected void updateDblePathInside() {
		updateGeneralPath(shape.getThickness()*2. + shape.getDbleBordSep());
	}



	@Override
	protected void updateDblePathMiddle() {
		updateGeneralPath(0.);
	}



	@Override
	protected void updateDblePathOutside() {
		updateGeneralPath(-(shape.getThickness()*2. + shape.getDbleBordSep()));
	}
}
