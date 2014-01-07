package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.shape.ILine;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

/**
 * Defines a model of an ellipse.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
		this(ShapeFactory.createPoint(), ShapeFactory.createPoint(1, 1), isUniqueID);
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


	/**
	 * Creates the tangent to the ellipse at the given angle.
	 * @param angle The position of the tangent point in radian
	 * @param orientation Change the orientation of the tangent
	 * @return The tangent.
	 */
	public ILine getTangenteAt(final double angle, final boolean orientation) {
//		final double th = (isDbleBorderable() && hasDbleBord() ? thickness*2.+ getDbleBordSep() : thickness)/2.;
		final IPoint tl = getTopLeftPoint();
		final IPoint br = getBottomRightPoint();
//		tl.setPoint(tl.getX()+th, tl.getY()+th);
//		br.setPoint(br.getX()-th, br.getY()-th);
		final IPoint gc = getGravityCentre();
		final IPoint pt = ShapeFactory.createPoint(br.getX(), (br.getY()+tl.getY())/2.).rotatePoint(gc, -angle);
		final double a = Math.abs(tl.getX()-gc.getX());
		final double b = Math.abs(tl.getY()-gc.getY());
		final double dec = 100.;
		final ILine tgt = ShapeFactory.createLine(pt.getX(), pt.getY(), 0., 0.);

		if((float)angle%(float)Math.PI<=0.01) {
			tgt.setX2(pt.getX());
			if(orientation)
				 tgt.setY2(pt.getY() - dec);
			else tgt.setY2(pt.getY() + dec);
		}
		else {
			if(orientation)
				 tgt.setX2(pt.getX()-dec);
			else tgt.setX2(pt.getX()+dec);

			if((float)angle%((float)Math.PI/2f)<=0.01)
				 tgt.setY2(pt.getY());
			else tgt.setY2(-(b*(pt.getX()-gc.getX())*(tgt.getX2()-pt.getX()))/(a*(pt.getY()-gc.getY())) + pt.getY());
		}
		tgt.updateAandB();
		return tgt;
	}

//
//	@Override
//	public IPoint[] getIntersection(final ILine line) {
//		if(line==null)
//			return null;
//
//		double lineb = line.getB();
//		double linea = line.getA();
//		boolean vert = !GLibUtilities.isValidPoint(linea, lineb);// The line is vertical.
//		IPoint[] pts;
//		double a	 = getA()/2.;
//		double b	 = getB()/2.;
//		IPoint centre= getGravityCentre();
//
//		if(vert) { // We rotate the two points of the line to get a horizontal line
//			ILine line2 = new LLine(line.getPoint1().rotatePoint(centre, Math.PI/2.), line.getPoint2().rotatePoint(centre, Math.PI/2.));
//			linea = line2.getA();
//			lineb = line2.getB();
//		}
//
//		double alpha = lineb-centre.getY();
//		double beta  = b*b+linea*linea*a*a;
//		double gamma = 2.*alpha*linea*a*a-2.*centre.getX()*b*b;
//		double mu    = b*b*centre.getX()*centre.getX()+a*a*alpha*alpha-a*a*b*b;
//		double delta = gamma*gamma - 4*beta*mu;
//
//		if(delta>0) {
//			double x1 = (-gamma+Math.sqrt(delta))/(2.*beta);
//			double x2 = (-gamma-Math.sqrt(delta))/(2.*beta);
//			pts = new LPoint[2];
//
//			pts[0] = new LPoint(x1, linea*x1+lineb);
//			pts[1] = new LPoint(x2, linea*x2+lineb);
//		}
//		else
//			if(LNumber.equalsDouble(delta,0.)) {
//				double x0 = -gamma/(2.*beta);
//				pts = new LPoint[1];
//				pts[0] = new LPoint(x0, linea*x0+lineb);
//			}
//			else
//				pts = null;
//
//		if(vert && pts!=null) {// If the line is vertical, we rotate the resulting points at their real position.
//			pts[0] = pts[0].rotatePoint(centre, -Math.PI/2.);
//
//			if(pts.length>1)
//				pts[1] = pts[1].rotatePoint(centre, -Math.PI/2.);
//		}
//
//		return pts;
//	}


	@Override
	public void setCentre(final IPoint centre) {
		if(!GLibUtilities.isValidPoint(centre))
			return ;

		IPoint gc = getGravityCentre();

		translate(centre.getX()-gc.getX(), centre.getY()-gc.getY());
	}


	@Override
	public double getA() {
		double rx = getWidth()/2.;
		double ry = getHeight()/2.;

		return rx<ry ? ry : rx;
	}


	@Override
	public double getB() {
		double rx = getWidth()/2.;
		double ry = getHeight()/2.;

		return rx>ry ? ry : rx;
	}
}
