package net.sf.latexdraw.glib.views.Java2D.impl;

import java.awt.geom.Path2D;

import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a view of the IEllipse model.<br>
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
 * 03/11/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LEllipseView<S extends IEllipse> extends LRectangularView<S> {
	/** Used to compute the drawing of an ellipse. */
	public static final double U = 2./3.*(Math.sqrt(2.)-1.);

	/** Used to compute the drawing of an ellipse. */
	protected static final double POINTS[][] = {
            { 1.0, 0.5 + U, 0.5 + U, 1.0, 0.5, 1.0 },
            { 0.5 - U, 1.0, 0.0, 0.5 + U, 0.0, 0.5 },
            { 0.0, 0.5 - U, 0.5 - U, 0.0, 0.5, 0.0 },
            { 0.5 + U, 0.0, 1.0, 0.5 - U, 1.0, 0.5 }
	};


	/**
	 * Creates an initialises the Java view of a LEllipse.
	 * @param model The model to view.
	 * @since 3.0
	 */
	protected LEllipseView(final S model) {
		super(model);
		update();
	}


	@Override
	public void updateBorder() {
		final double angle = shape.getRotationAngle();

		if(LNumber.INSTANCE.equals(angle, 0.))
			super.updateBorder();
		else {
			// See: http://math.stackexchange.com/questions/91132/how-to-get-the-limits-of-rotated-ellipse
			final IPoint gc = shape.getGravityCentre();
			final double a = shape.getA()+getBorderGap();
			final double b = shape.getB()+getBorderGap();
			double cosAngle = Math.cos(angle)*Math.cos(angle);
			double sinAngle = Math.sin(angle)*Math.sin(angle);
			double xMin = -Math.sqrt(a*a*cosAngle+b*b*sinAngle);
			double yMin = -Math.sqrt(a*a*sinAngle+b*b*cosAngle);
			border.setFrame(xMin+gc.getX(), yMin+gc.getY(), -xMin*2., -yMin*2.);
		}
	}


	/**
	 * Adds a "curveTo" segment to the given path. This segment is one of the four quarter of an ellipse.
	 * @param tlx The top-left X-coordinate of the ellipse.
	 * @param tly The top-left Y-coordinate of the ellipse.
	 * @param width The width of the ellipse.
	 * @param height The height of the ellipse.
	 * @param path The path to complete.
	 * @param points @see POINTS attribute. Contains values used to draw the ellipse quarter.
	 * @throws NullPointerException If one of the given parameters is null.
	 * @since 3.0
	 */
	protected static void curveQuarter(final double tlx, final double tly, final double width, final double height,
								final Path2D path, final double[] points) {
		path.curveTo(tlx+points[0]*width, tly+points[1]*height, tlx+points[2]*width, tly+points[3]*height, tlx+points[4]*width, tly+points[5]*height);
	}



	@Override
	protected void setRectangularShape(final Path2D path, final double tlx, final double tly, final double width, final double height) {
		setEllipsePath(path, tlx, tly, width, height);
	}


	/**
	 * Creates an ellipse in the given path.
	 * @param path The path to fill.
	 * @param tlx The top left X-coordinate of the ellipse.
	 * @param tly The top left Y-coordinate of the ellipse.
	 * @param width The width of the ellipse.
	 * @param height The height of the ellipse.
	 * @since 3.0
	 */
	public static void setEllipsePath(final Path2D path, final double tlx, final double tly, final double width, final double height) {
		if(path!=null) {
			final double w2  = width<1. && height<1. ? 1. : width;
			double p[] = POINTS[3];

			path.moveTo(tlx + p[4]*w2, tly + p[5]*height);

			for(byte cpt=1; cpt<5; cpt++)
				curveQuarter(tlx, tly, w2, height, path, POINTS[cpt-1]);

			path.closePath();
		}
	}
}
