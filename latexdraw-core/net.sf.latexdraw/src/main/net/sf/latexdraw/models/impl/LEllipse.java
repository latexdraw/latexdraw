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
package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.eclipse.jdt.annotation.NonNull;

/**
 * An implementation of an ellipse.
 */
class LEllipse extends LRectangularShape implements IEllipse {
	/**
	 * Creates an Ellipse.
	 * @since 3.0
	 */
	LEllipse() {
		this(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1.0, 1.0));
	}

	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	LEllipse(final IPoint tl, final IPoint br) {
		super(tl, br);
	}

	//
	//	@Override
	//	public IPoint[] getIntersection(final ILine line) {
	//		if(line==null)
	//			return null;
	//
	//		double lineb = line.getB();
	//		double linea = line.getA();
	//		boolean vert = !GLibUtilities.isValidPt(linea, lineb);// The line is vertical.
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
		if(MathUtils.INST.isValidPt(centre)) {
			final IPoint gc = getGravityCentre();
			translate(centre.getX() - gc.getX(), centre.getY() - gc.getY());
		}
	}

	@Override
	public @NonNull IPoint getCenter() {
		return getGravityCentre();
	}


	@Override
	public double getA() {
		final double rx = getWidth() / 2.0;
		final double ry = getHeight() / 2.0;
		return rx < ry ? ry : rx;
	}


	@Override
	public double getB() {
		final double rx = getWidth() / 2.0;
		final double ry = getHeight() / 2.0;
		return rx > ry ? ry : rx;
	}
}
