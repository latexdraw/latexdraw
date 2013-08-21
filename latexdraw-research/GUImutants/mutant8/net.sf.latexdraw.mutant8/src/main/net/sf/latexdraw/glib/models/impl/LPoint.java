package net.sf.latexdraw.glib.models.impl;

import static java.lang.Math.PI;
import static java.lang.Math.atan;

import java.awt.geom.Point2D;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a model of a point. This model must be used only to define other models.
 * It is not a shape. See the LDot class for the shape.<br>
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
 * 02/13/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LPoint extends Point2D.Double implements IPoint {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a Point2D with coordinates (0, 0).
	 */
	protected LPoint() {
		super();
	}


	/**
	 * Creates a point from a IPoint
	 * @param pt The IPoint, if null the default value (0,0) will be used.
	 */
	protected LPoint(final IPoint pt) {
		super();

		if(pt==null)
			setLocation(0, 0);
		else
			setLocation(pt.getX(), pt.getY());
	}


	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param x The X-coordinate to set.
	 * @param y The Y-coordinate to set.
	 */
	protected LPoint(final double x, final double y) {
		super(x, y);
	}


	@Override
	public double computeAngle(final IPoint pt) {
		if(!GLibUtilities.INSTANCE.isValidPoint(pt))
			return java.lang.Double.NaN;

		double angle;
		double x2 = pt.getX() - x;
		double y2 = pt.getY() - y;

		if(LNumber.INSTANCE.equals(x2, 0.)) {
			angle = Math.PI/2.;

			if(y2<0.)
				angle = Math.PI*2. - angle;
		}
		else
			angle = x2<0. ? Math.PI-atan(-y2/x2) : atan(y2/x2);

		return angle;
	}


	@Override
	public IPoint zoom(final double zoomLevel) {
		return new LPoint(x*zoomLevel, y*zoomLevel);
	}


	@Override
	public double computeRotationAngle(final IPoint pt1, final IPoint pt2) {
		if(!GLibUtilities.INSTANCE.isValidPoint(pt1) || !GLibUtilities.INSTANCE.isValidPoint(pt2))
			return java.lang.Double.NaN;

		double thetaOld = computeAngle(pt1);
		double thetaNew = computeAngle(pt2);

		return thetaNew-thetaOld;
	}


	@Override
	public IPoint centralSymmetry(final IPoint centre) {
		return rotatePoint(centre, Math.PI);
	}


	@Override
	public IPoint rotatePoint(final IPoint gravityC, final double theta) {
		if(!GLibUtilities.INSTANCE.isValidPoint(gravityC) || !GLibUtilities.INSTANCE.isValidCoordinate(theta))
			return null;

		IPoint pt 		= new LPoint();
		double cosTheta;
		double sinTheta;
		double angle 	= theta;
		double gx 		= gravityC.getX();
		double gy 		= gravityC.getY();

		if(angle<0.)
			angle = 2.*PI + angle;


		angle = angle%(2.*PI);

		if(LNumber.INSTANCE.equals(angle, 0.))
			return (LPoint)clone();

		if(LNumber.INSTANCE.equals(angle-PI/2., 0.)) {
			cosTheta = 0.;
			sinTheta = 1.;
		}
		else if(LNumber.INSTANCE.equals(angle-PI, 0.)) {
				cosTheta = -1.;
				sinTheta = 0.;
			}
			else if(LNumber.INSTANCE.equals(angle-3.*PI/2., 0.)) {
					cosTheta = 0.;
					sinTheta = -1.;
				}
				else {
					cosTheta = Math.cos(angle);
					sinTheta = Math.sin(angle);
				}

		pt.setX(cosTheta * (x - gx) - sinTheta * (y - gy) + gx);
		pt.setY(sinTheta * (x - gx) + cosTheta * (y - gy) + gy);

		return pt;
	}


	@Override
	public boolean equals(final IPoint p, final double gap) {
		if(!GLibUtilities.INSTANCE.isValidCoordinate(gap) || !GLibUtilities.INSTANCE.isValidPoint(p))
			return false;

		return LNumber.INSTANCE.equals(x, p.getX(), gap) && LNumber.INSTANCE.equals(y, p.getY(), gap);
	}



	@Override
	public IPoint getMiddlePoint(final IPoint p) {
		return p==null ? null : new LPoint((x+p.getX())/2., (y+p.getY())/2.);
	}



	@Override
	public void translate(final double tx, final double ty) {
		if(GLibUtilities.INSTANCE.isValidPoint(tx, ty))
			setLocation(x+tx, y+ty);
	}


	@Override
	public IPoint horizontalSymmetry(final IPoint origin) {
		if(!GLibUtilities.INSTANCE.isValidPoint(origin))
			return null;

		return new LPoint(2.*origin.getX()-x, y);
	}


	@Override
	public IPoint verticalSymmetry(final IPoint origin) {
		if(!GLibUtilities.INSTANCE.isValidPoint(origin))
			return null;

		return new LPoint(x, 2.*origin.getY() - y);
	}



	@Override
	public void setPoint(final double newX, final double newY) {
		if(GLibUtilities.INSTANCE.isValidPoint(newX, newY))
			setLocation(newX, newY);
	}


	@Override
	public void setX(final double newX) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(newX))
			x = newX;
	}


	@Override
	public void setY(final double newY) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(newY))
			y = newY;
	}


	@Override
	public void setPoint(final IPoint pt) {
		if(GLibUtilities.INSTANCE.isValidPoint(pt))
			setLocation(pt.getX(), pt.getY());
	}


	@Override
	public double distance(final IPoint pt) {
		return pt==null ? java.lang.Double.NaN : distance(pt.getX(), pt.getY());
	}


	@Override
	public Point2D.Double toPoint2D() {
		return new Point2D.Double(x, y);
	}


	@Override
	public void setPoint2D(final Point2D pt) {
		if(pt!=null)
			setPoint(pt.getX(), pt.getY());
	}
}
