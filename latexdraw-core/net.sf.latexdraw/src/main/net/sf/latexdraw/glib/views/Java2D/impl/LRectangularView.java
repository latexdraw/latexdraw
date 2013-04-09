package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of the IRectangularView model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
abstract class LRectangularView<S extends IRectangularShape> extends LShapeView<S> {
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
		double gap;

		switch(shape.getBordersPosition()) {
			case MID:
				gap = shape.hasDbleBord() ? thickness+shape.getDbleBordSep()/2. : thickness/2.;
				break;
			case OUT:
				gap = shape.hasDbleBord() ? thickness*2.+shape.getDbleBordSep() : thickness;
				break;
			default:
			case INTO:
				gap = 0.;
				break;
		}

		return gap;
	}


	@Override
	public void updateBorder() {
		final double height		= shape.getHeight();
		final double thickness	= shape.getThickness();
		double gap				= getBorderGap();

		// Defining the coordinates and dimensions of the not rotated border.
		double tlx = shape.getX()-gap;
		double tly = shape.getY()-height-gap;
		final double widthBorder  = Math.max(shape.getWidth()+gap*2., thickness);
		final double heightBorder = Math.max(height+gap*2., thickness);
		final double angle 		  = shape.getRotationAngle();

		if(LNumber.INSTANCE.equals(angle, 0.))
			border.setFrame(tlx, tly, widthBorder, heightBorder);
		else {
			IPoint tl = DrawingTK.getFactory().createPoint();
			IPoint br = DrawingTK.getFactory().createPoint();
			getRotatedRectangle(tlx, tly, widthBorder, heightBorder, angle, shape.getGravityCentre(), tl, br);
			// The border of the rotated rectangle is now the border of the rectangular view.
			border.setFrameFromDiagonal(tl.getX(), tl.getY(), br.getX(), br.getY());
		}
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
