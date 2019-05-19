/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.impl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.atan;

/**
 * Defines a model of a point. Not a shape.
 * @author Arnaud Blouin
 */
class PointImpl implements Point {
	private final @NotNull DoubleProperty x;
	private final @NotNull DoubleProperty y;

	/**
	 * Creates a Point2D with coordinates (0, 0).
	 */
	PointImpl() {
		this(0d, 0d);
	}

	/**
	 * Creates a point from a IPoint
	 * @param pt The IPoint, if null the default value (0,0) will be used.
	 */
	PointImpl(final Point pt) {
		this(pt == null ? 0d : pt.getX(), pt == null ? 0d : pt.getY());
	}

	/**
	 * Creates a Point2D with the specified coordinates.
	 * @param xCoord The X-coordinate to set.
	 * @param yCoord The Y-coordinate to set.
	 */
	PointImpl(final double xCoord, final double yCoord) {
		super();
		x = new SimpleDoubleProperty(xCoord);
		y = new SimpleDoubleProperty(yCoord);
	}

	@Override
	public double computeAngle(final Point pt) {
		if(!MathUtils.INST.isValidPt(pt)) {
			return Double.NaN;
		}

		double angle;
		final double x2 = pt.getX() - getX();
		final double y2 = pt.getY() - getY();

		if(MathUtils.INST.equalsDouble(x2, 0d)) {
			angle = Math.PI / 2d;

			if(y2 < 0d) {
				angle = Math.PI * 2d - angle;
			}
		}else {
			angle = x2 < 0d ? Math.PI - atan(-y2 / x2) : atan(y2 / x2);
		}

		return angle;
	}

	@Override
	public Point zoom(final double zoomLevel) {
		return ShapeFactory.INST.createPoint(getX() * zoomLevel, getY() * zoomLevel);
	}

	@Override
	public double computeRotationAngle(final Point pt1, final Point pt2) {
		if(!MathUtils.INST.isValidPt(pt1) || !MathUtils.INST.isValidPt(pt2)) {
			return Double.NaN;
		}

		final double thetaOld = computeAngle(pt1);
		final double thetaNew = computeAngle(pt2);

		return thetaNew - thetaOld;
	}

	@Override
	public Point centralSymmetry(final Point centre) {
		return rotatePoint(centre, Math.PI);
	}

	@Override
	public Point rotatePoint(final Point gravityC, final double theta) {
		if(!MathUtils.INST.isValidPt(gravityC) || !MathUtils.INST.isValidCoord(theta)) {
			return null;
		}

		final double[] coords = {getX(), getY()};
		AffineTransform.getRotateInstance(theta, gravityC.getX(), gravityC.getY()).transform(coords, 0, coords, 0, 1);
		return ShapeFactory.INST.createPoint(coords[0], coords[1]);
	}

	@Override
	public boolean equals(final Point p, final double gap) {
		return !(!MathUtils.INST.isValidCoord(gap) || !MathUtils.INST.isValidPt(p)) && MathUtils.INST.equalsDouble(getX(), p.getX(), gap) &&
			MathUtils.INST.equalsDouble(getY(), p.getY(), gap);
	}

	@Override
	public Point getMiddlePoint(final Point p) {
		return p == null ? null : ShapeFactory.INST.createPoint((getX() + p.getX()) / 2., (getY() + p.getY()) / 2d);
	}

	@Override
	public void translate(final double tx, final double ty) {
		if(MathUtils.INST.isValidPt(tx, ty)) {
			setPoint(getX() + tx, getY() + ty);
		}
	}

	@Override
	public Point horizontalSymmetry(final double x) {
		if(!MathUtils.INST.isValidCoord(x)) {
			return null;
		}

		return ShapeFactory.INST.createPoint(2d * x - getX(), getY());
	}

	@Override
	public Point verticalSymmetry(final double y) {
		if(!MathUtils.INST.isValidCoord(y)) {
			return null;
		}

		return ShapeFactory.INST.createPoint(getX(), 2d * y - getY());
	}

	@Override
	public void setPoint(final double newX, final double newY) {
		setX(newX);
		setY(newY);
	}

	@Override
	public void setX(final double newX) {
		if(MathUtils.INST.isValidCoord(newX)) {
			x.set(newX);
		}
	}

	@Override
	public void setY(final double newY) {
		if(MathUtils.INST.isValidCoord(newY)) {
			y.set(newY);
		}
	}

	@Override
	public void setPoint(final Point pt) {
		if(pt != null) {
			setPoint(pt.getX(), pt.getY());
		}
	}

	@Override
	public double distance(final Point pt) {
		return pt == null ? java.lang.Double.NaN : distance(pt.getX(), pt.getY());
	}

	@Override
	public Point2D.Double toPoint2D() {
		return new Point2D.Double(x.getValue(), y.getValue());
	}

	@Override
	public Point3D toPoint3D() {
		return new Point3D(x.getValue(), y.getValue(), 0d);
	}

	@Override
	public void setPoint2D(final Point2D pt) {
		if(pt != null) {
			setPoint(pt.getX(), pt.getY());
		}
	}

	@Override
	public @NotNull Point substract(final Point pt) {
		final Point sub = ShapeFactory.INST.createPoint(this);
		if(pt != null) {
			sub.translate(-pt.getX(), -pt.getY());
		}
		return sub;
	}

	@Override
	public @NotNull Point normalise() {
		final double magnitude = magnitude();
		return ShapeFactory.INST.createPoint(getX() / magnitude, getY() / magnitude);
	}

	@Override
	public double magnitude() {
		return Math.hypot(getX(), getY());
	}

	@Override
	public @NotNull Point add(final Point pt) {
		final Point added = ShapeFactory.INST.createPoint(this);
		if(pt != null) {
			added.translate(pt.getX(), pt.getY());
		}
		return added;
	}

	@Override
	public  @NotNull DoubleProperty xProperty() {
		return x;
	}

	@Override
	public  @NotNull DoubleProperty yProperty() {
		return y;
	}

	@Override
	public double getY() {
		return y.get();
	}

	@Override
	public double getX() {
		return x.get();
	}

	@Override
	public double distance(final double xCoord, final double yCoord) {
		return Math.hypot(xCoord - x.get(), yCoord - y.get());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp = Double.doubleToLongBits(x.doubleValue());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y.doubleValue());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Point)) {
			return false;
		}
		return equals((Point) obj, 0.0000001);
	}

	@Override
	public String toString() {
		return "LPoint [x=" + x.doubleValue() + ", y=" + y.doubleValue() + "]"; //NON-NLS
	}
}
