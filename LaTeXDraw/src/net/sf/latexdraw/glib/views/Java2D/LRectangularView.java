package net.sf.latexdraw.glib.views.Java2D;

import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of the IRectangularView model.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public abstract class LRectangularView<S extends IRectangularShape> extends LShapeView<S> {
	/**
	 * Initialises a rectangular view.
	 * @param model The rectangular model.
	 * @since 3.0
	 */
	public LRectangularView(final S model) {
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


	/**
	 * Gives the top-left and the bottom-right points of the rotated rectangle.
	 * @param tlx The top-left x-coordinate of the rectangle to rotate.
	 * @param tly The top-left y-coordinate of the rectangle to rotate.
	 * @param width The width of the rectangle to rotate.
	 * @param height The height of the rectangle to rotate.
	 * @param angle The rotation angle.
	 * @param gravityCentre The gravity centre used for the rotation.
	 * @param tl The resulting top-left point. Must not be null.
	 * @param br The resulting bottom-right point. Must not be null.
	 * @since 3.0
	 */
	protected static void getRotatedRectangle(final double tlx, final double tly, final double width,
											 final double height, final double angle, final IPoint gravityCentre,
											 final IPoint tl, final IPoint br) {
		final IShapeFactory factory = DrawingTK.getFactory();
		IPoint pts[] = new IPoint[4];
		// Rotation of the four points of the rectangle.
		pts[0] = factory.createPoint(tlx, tly).rotatePoint(gravityCentre, angle);
		pts[1] = factory.createPoint(tlx+width, tly).rotatePoint(gravityCentre, angle);
		pts[2] = factory.createPoint(tlx+width, tly+height).rotatePoint(gravityCentre, angle);
		pts[3] = factory.createPoint(tlx, tly+height).rotatePoint(gravityCentre, angle);
		tl.setPoint(Double.MAX_VALUE, Double.MAX_VALUE);
		br.setPoint(Double.MIN_VALUE, Double.MIN_VALUE);

		// Defining the border of the rotated rectangle.
		for(int i=0; i<pts.length; i++) {
			if(pts[i].getX()<tl.getX())
				tl.setX(pts[i].getX());
			if(pts[i].getX()>br.getX())
				br.setX(pts[i].getX());
			if(pts[i].getY()<tl.getY())
				tl.setY(pts[i].getY());
			if(pts[i].getY()>br.getY())
				br.setY(pts[i].getY());
		}
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
	private void updateGeneralPath(final double gap) {
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
