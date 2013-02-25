package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a model of an ellipse.<br>
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
class LEllipse extends LRectangularShape implements IEllipse {
	/**
	 * Creates an Ellipse.
	 * @param isUniqueID isUniqueID True: the ellipse will have a unique ID.
	 * @since 3.0
	 */
	protected LEllipse(final boolean isUniqueID) {
		this(new LPoint(), new LPoint(1, 1), isUniqueID);
	}


	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @param isUniqueID True: the ellipse will have a unique ID.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	protected LEllipse(final IPoint tl, final IPoint br, final boolean isUniqueID) {
		super(tl, br, isUniqueID);
	}

	
	@Override
	public IEllipse duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IEllipse ? (IEllipse)sh : null;
	}
	

	@Override
	public IPoint[] getIntersection(final ILine line) {
		if(line==null)
			return null;

		double lineb = line.getB();
		double linea = line.getA();
		boolean vert = !GLibUtilities.INSTANCE.isValidPoint(linea, lineb);// The line is vertical.
		IPoint[] pts;
		double a	 = getA()/2.;
		double b	 = getB()/2.;
		IPoint centre= getGravityCentre();

		if(vert) { // We rotate the two points of the line to get a horizontal line
			ILine line2 = new LLine(line.getPoint1().rotatePoint(centre, Math.PI/2.), line.getPoint2().rotatePoint(centre, Math.PI/2.));
			linea = line2.getA();
			lineb = line2.getB();
		}

		double alpha = lineb-centre.getY();
		double beta  = b*b+linea*linea*a*a;
		double gamma = 2.*alpha*linea*a*a-2.*centre.getX()*b*b;
		double mu    = b*b*centre.getX()*centre.getX()+a*a*alpha*alpha-a*a*b*b;
		double delta = gamma*gamma - 4*beta*mu;

		if(delta>0) {
			double x1 = (-gamma+Math.sqrt(delta))/(2.*beta);
			double x2 = (-gamma-Math.sqrt(delta))/(2.*beta);
			pts = new LPoint[2];

			pts[0] = new LPoint(x1, linea*x1+lineb);
			pts[1] = new LPoint(x2, linea*x2+lineb);
		}
		else
			if(LNumber.INSTANCE.equals(delta,0.)) {
				double x0 = -gamma/(2.*beta);
				pts = new LPoint[1];
				pts[0] = new LPoint(x0, linea*x0+lineb);
			}
			else
				pts = null;

		if(vert && pts!=null) {// If the line is vertical, we rotate the resulting points at their real position.
			pts[0] = pts[0].rotatePoint(centre, -Math.PI/2.);

			if(pts.length>1)
				pts[1] = pts[1].rotatePoint(centre, -Math.PI/2.);
		}

		return pts;
	}



	@Override
	public double getRx() {
		return getWidth()/2.;
	}


	@Override
	public double getRy() {
		return getHeight()/2.;
	}



	@Override
	public void setRx(final double rx) {
		if(rx<=0 && !GLibUtilities.INSTANCE.isValidCoordinate(rx))
			return ;

		double cx = getGravityCentre().getX();

		points.get(0).setX(cx-rx);
		points.get(3).setX(cx-rx);
		points.get(1).setX(cx+rx);
		points.get(2).setX(cx+rx);
	}


	@Override
	public void setRy(final double ry) {
		if(ry<=0 && !GLibUtilities.INSTANCE.isValidCoordinate(ry))
			return ;

		double cy = getGravityCentre().getY();

		points.get(0).setY(cy-ry);
		points.get(3).setY(cy-ry);
		points.get(1).setY(cy+ry);
		points.get(2).setY(cy+ry);
	}


	@Override
	public void setCentre(final IPoint centre) {
		if(!GLibUtilities.INSTANCE.isValidPoint(centre))
			return ;

		IPoint gc = getGravityCentre();

		translate(centre.getX()-gc.getX(), centre.getY()-gc.getY());
	}


	@Override
	public double getA() {
		double rx = getRx();
		double ry = getRy();

		return rx<ry ? ry : rx;
	}


	@Override
	public double getB() {
		double rx = getRx();
		double ry = getRy();

		return rx>ry ? ry : rx;
	}
}
