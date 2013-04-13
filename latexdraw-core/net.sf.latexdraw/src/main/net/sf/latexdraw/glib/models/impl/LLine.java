package net.sf.latexdraw.glib.models.impl;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a model of a line. This model must be used only to define other models.
 * It is not a shape. See the LLines class for the shape.<br>
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
 * 02/14/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LLine extends Line2D.Double implements ILine {
	private static final long serialVersionUID = 1L;

	/** The director coefficient of the line (y=ax+b). */
	protected double a;

	/** y=ax+b. */
	protected double b;


	/**
	 * Constructs a line from the specified coordinates.
	 * @param x1 the X coordinate of the start point.
	 * @param y1 the Y coordinate of the start point.
	 * @param x2 the X coordinate of the end point.
	 * @param y2 the Y coordinate of the end point.
	 * @throws IllegalArgumentException If one of the given coordinate is not valid.
	 */
	protected LLine(final double x1, final double y1, final double x2, final double y2) {
		this(new LPoint(x1, y1), new LPoint(x2, y2));
	}



	/**
	 * Creates a line by creating a second point with:
	 * @param b y = ax+ b
	 * @param p1 The first point.
	 * @throws IllegalArgumentException If one of the given parameter is not valid.
	 */
	protected LLine(final double b, final IPoint p1) {
		this(p1, new LPoint(0,b));
	}



	/**
	 * Constructs a line from the specified <code>Point2D</code> objects.
	 * @param p1 the start <code>Point2D</code> of this line segment.
	 * @param p2 the end <code>Point2D</code> of this line segment.
	 * @throws IllegalArgumentException If one of the given points is not valid.
	 */
	protected LLine(final IPoint p1, final IPoint p2) {
		super();

		if(!GLibUtilities.INSTANCE.isValidPoint(p1) || !GLibUtilities.INSTANCE.isValidPoint(p2))
			throw new IllegalArgumentException();

		setP1(p1);
		setP2(p2);
		updateAandB();
	}



	@Override
	public void setLine(final double x1, final double y1, final double x2, final double y2) {
		if(GLibUtilities.INSTANCE.isValidPoint(x1, y1) && GLibUtilities.INSTANCE.isValidPoint(x2, y2)) {
			super.setLine(x1, y1, x2, y2);
			updateAandB();
		}
	}



	@Override
	public boolean isInSegment(final IPoint pt) {
		if(pt==null) return false;

		final double minX = Math.min(x1, x2);
		final double maxX = Math.max(x1, x2);
		final double minY = Math.min(y1, y2);
		final double maxY = Math.max(y1, y2);
		final double x = pt.getX();
		final double y = pt.getY();

		if(isHorizontalLine()) return LNumber.INSTANCE.equals(y, minY) && x>=minX && x<=maxX;
		if(isVerticalLine()) return LNumber.INSTANCE.equals(x, minX) && y>=minY && y<=maxY;
		return y>=minY && y<=maxY && x>=minX && x<=maxX && LNumber.INSTANCE.equals(y, getA()*x+getB());
	}


	@Override
	public void updateAandB() {
		if(isVerticalLine()) {
			a = java.lang.Double.NaN;
			b = java.lang.Double.NaN;
		} else {
			a = (y1 -y2)/(x1 - x2);
			b = y1 - a * x1;
		}
	}



	@Override
	public double getXWithEquation(final double y) {
		return GLibUtilities.INSTANCE.isValidCoordinate(y) ? isVerticalLine() ? isHorizontalLine() ?
				java.lang.Double.NaN : getX1() : (y-b)/a : java.lang.Double.NaN;
	}



	@Override
	public double getYWithEquation(final double x) {
		return GLibUtilities.INSTANCE.isValidCoordinate(x) ? a*x+b : java.lang.Double.NaN;
	}



	@Override
	public IPoint[] findPoints(final Point2D p, final double distance) {
		return p==null ? null : findPoints(p.getX(), p.getY(), distance);
	}



	@Override
	public IPoint[] findPoints(final IPoint p, final double distance) {
		return p==null ? null : findPoints(p.getX(), p.getY(), distance);
	}


	@Override
	public IPoint[] findPoints(final double x, final double y, final double distance) {
		if(!GLibUtilities.INSTANCE.isValidPoint(x, y) || !GLibUtilities.INSTANCE.isValidCoordinate(distance))
			return null;

		if(LNumber.INSTANCE.equals(distance, 0.)) {
			LPoint[] sol = new LPoint[1];
			sol[0] 		 = new LPoint(x,y);

			return sol;
		}

		if(isVerticalLine()) {
			if(isHorizontalLine())// The line is a point. So no position can be computed.
				return null;

			LPoint sol[] = new LPoint[2];
			sol[0] = new LPoint(x, y-distance);
			sol[1] = new LPoint(x, y+distance);

			return sol;
		}

		double aLine = a*a+1.;
		double bLine = -2.*(x+y*a-a*b);
		double cLine = b*b-(2.*y*b)+y*y+x*x-(distance*distance);
		double delta = bLine*bLine-4.*aLine*cLine;

		if(delta>0.) {
			double x1b, x2b, y1b, y2b;
			LPoint sol[] = new LPoint[2];

			x1b = (-bLine+Math.sqrt(delta))/(2*aLine);
			x2b = (-bLine-Math.sqrt(delta))/(2*aLine);
			y1b = a*x1b+b;
			y2b = a*x2b+b;
			sol[0] = new LPoint(x1b, y1b);
			sol[1] = new LPoint(x2b, y2b);

			return sol;
		}
		else
			if(LNumber.INSTANCE.equals(delta, 0.)) {
				double x2b, y2b;
				LPoint sol[] = new LPoint[1];

				x2b = -bLine/2*aLine;
				y2b = a*x2b+b;
				sol[0] = new LPoint(x2b, y2b);

				return sol;
			}
			else
				return null;
	}



	@Override
	public ILine getPerpendicularLine(final IPoint pt) {
		/*//TODO
		if(isVerticalLine())
			return new Line(new Point2D.Double(-10000, y), new Point2D.Double(10000, y));

		if(isHorizontalLine())
			return new Line(new Point2D.Double(x, -10000), new Point2D.Double(x, 10000));

		final double a2 = -1./getA();
		final double b2 = y-a2*x;

		return new Line(new Point2D.Double(x, y), new Point2D.Double(-b2/a2, 0.));
		 */
		if(!GLibUtilities.INSTANCE.isValidPoint(pt))
			return null;

		if(isVerticalLine())//FIXME must always create a perpendicular line + add test
			return LNumber.INSTANCE.equals(pt.getX(), x1) ? new LLine(pt.getY(), new LPoint(pt)) : null;

		if(LNumber.INSTANCE.equals(pt.getX(), 0.)) {
			LPoint pt3  = new LPoint(getPoint2());
			LPoint pt2  = (LPoint) pt3.rotatePoint(pt, Math.PI/2.);

			return new LLine(pt2, pt);
		}

		if(LNumber.INSTANCE.equals(a, 0.))
			return new LLine(pt.getX(), pt.getY(), pt.getX(), pt.getY()-10.);

		double a2 = -1./a;

		return new LLine(pt.getY()-a2*pt.getX(), pt);
	}



	@Override
	public boolean isVerticalLine() {
		return LNumber.INSTANCE.equals(x1, x2);
	}


	@Override
	public boolean isHorizontalLine() {
		return LNumber.INSTANCE.equals(y1, y2);
	}



	@Override
	public IPoint getIntersection(final ILine l) {
		if(l==null) return null;
		if(LNumber.INSTANCE.equals(a, l.getA(), 0.00000000001)) return null;

		boolean verticalLine1 = isVerticalLine();
		boolean verticalLine2 = l.isVerticalLine();
		double x;
		double y;

		if(verticalLine2) {
			if(verticalLine1)// The two lines a parallels
				return null;

			if(l.isHorizontalLine())// Points of the line l are equal.
				return null;

			if(isHorizontalLine()) {
				x = l.getX1();
				y = getY1();
			}else {
				y = a*l.getX1()+b;
				x = (y-b)/a;
			}
		}else {
			double la = l.getA();
			double lb = l.getB();

			if(verticalLine1) {
				if(l.isHorizontalLine()) {
					x = getX1();
					y = l.getY1();
				} else {
					y = la*getX1()+lb;
					x = (y-lb)/la;
				}
			}else {
				x = (b-lb)/(la-a);
				y = a*x+b;
			}
		}

		return new LPoint(x, y);
	}




	@Override
	public IPoint getMiddlePt() {
		return new LPoint((getX1()+getX2())/2., (getY1()+getY2())/2.);
	}



	@Override
	public IPoint getIntersectionSegment(final ILine l) {
		IPoint p = getIntersection(l);

		if(p==null)
			return null;

		double px  = p.getX();
		double py  = p.getY();
		IPoint tl  = getTopLeftPoint();
		IPoint br  = getBottomRightPoint();
		IPoint tl2 = l.getTopLeftPoint();
		IPoint br2 = l.getBottomRightPoint();

		if((px>=tl.getX() && px<=br.getX() && py>=tl.getY() && py<=br.getY()) &&
		   (px>=tl2.getX() && px<=br2.getX() && py>=tl2.getY() && py<=br2.getY()))
			return p;

		return null;
	}




	@Override
	public IPoint getTopLeftPoint() {
		IPoint pt1 = getPoint1();
		IPoint pt2 = getPoint2();

		return new LPoint(pt1.getX()<pt2.getX() ? pt1.getX() : pt2.getX(), pt1.getY()<pt2.getY() ? pt1.getY() : pt2.getY());
	}



	@Override
	public IPoint getBottomRightPoint() {
		IPoint pt1 = getPoint1();
		IPoint pt2 = getPoint2();

		return new LPoint(pt1.getX()<pt2.getX() ? pt2.getX() : pt1.getX(), pt1.getY()<pt2.getY() ? pt2.getY() : pt1.getY());
	}



	@Override
	public double getA() {
		return a;
	}


	@Override
	public double getB() {
		return b;
	}



	@Override
	public IPoint getPoint1() {
		return new LPoint(x1, y1);
	}



	@Override
	public IPoint getPoint2() {
		return new LPoint(x2, y2);
	}



	@Override
	public void setP1(final IPoint pt) {
		if(GLibUtilities.INSTANCE.isValidPoint(pt)) {
			this.x1 = pt.getX();
			this.y1 = pt.getY();
		}
	}



	@Override
	public void setP1(final double x, final double y) {
		if(GLibUtilities.INSTANCE.isValidPoint(x, y)) {
			this.x1 = x;
			this.y1 = y;
		}
	}



	@Override
	public void setP2(final IPoint pt) {
		if(GLibUtilities.INSTANCE.isValidPoint(pt)) {
			this.x2 = pt.getX();
			this.y2 = pt.getY();
		}
	}



	@Override
	public void setP2(final double x, final double y) {
		if(GLibUtilities.INSTANCE.isValidPoint(x, y)) {
			this.x2 = x;
			this.y2 = y;
		}
	}



	@Override
	public void setX1(final double x1) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(x1))
			this.x1 = x1;
	}


	@Override
	public void setX2(final double x2) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(x2))
			this.x2 = x2;
	}


	@Override
	public void setY1(final double y1) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(y1))
			this.y1 = y1;
	}


	@Override
	public void setY2(final double y2) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(y2))
			this.y2 = y2;
	}


	@Override
	public double getLineAngle() {
		if(isHorizontalLine())
			return 0.;

		if(isVerticalLine())
			return Math.PI/2.;

		return Math.atan(getA());
	}


	@Override
	public boolean isDot() {
		return isVerticalLine() && isHorizontalLine();
	}


	@Override
	public String toString() {
		return "LLine [a=" + a + ", b=" + b + ", x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
	}
}
