package net.sf.latexdraw.glib.models.interfaces;

import java.awt.geom.Point2D;

/**
 * Defines an interface that classes defining a line should implement.<br>
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
 * 07/02/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface ILine {
	/**
	 * Computes the angle of the line.
	 * @return The angle of the line.
	 * @since 3.0
	 */
	double getLineAngle();

	/**
	 * @return The x coordinate of the first point.
	 * @since 3.0
	 */
	double getX1();

	/**
	 * @return The x coordinate of the second point.
	 * @since 3.0
	 */
	double getX2();

	/**
	 * @return The y coordinate of the first point.
	 * @since 3.0
	 */
	double getY1();

	/**
	 * @return The y coordinate of the second point.
	 * @since 3.0
	 */
	double getY2();

	/**
	 * @return The first point.
	 * @since 3.0
	 */
	IPoint getPoint1();

	/**
	 * @return The second point.
	 * @since 3.0
	 */
	IPoint getPoint2();


	/**
	 * @return True if the line is vertical.
	 * @since 3.0
	 */
	boolean isVerticalLine();


	/**
	 * @return True if the line is horizontal.
	 * @since 3.0
	 */
	boolean isHorizontalLine();


	/**
	 * @return True if the two points of the line are equals meaning this is not a line but a 'dot'.
	 * @since 3.0
	 */
	boolean isDot();

	/**
	 * @return True if the segment defined by the line contains the given point. False otherwise or
	 * if the given point is null.
	 * @param pt The point to check.
	 * @since 3.0
	 */
	boolean isInSegment(final IPoint pt);

	/**
	 * Sets the position of the line. Do nothing if one of the given parameter
	 * is not valid.
	 * @param x1 The x coordinate of the first point.
	 * @param y1 The y coordinate of the first point.
	 * @param x2 The x coordinate of the second point.
	 * @param y2 The y coordinate of the second point.
	 * @since 3.0
	 */
	void setLine(final double x1, final double y1, final double x2, final double y2);

	/**
	 * Sets the x coordinate of the first point.
	 * @param x1 The new x coordinate of the first point.
	 * @since 3.0
	 */
	void setX1(final double x1);

	/**
	 * Sets the x coordinate of the second point.
	 * @param x2 The new x coordinate of the second point.
	 * @since 3.0
	 */
	void setX2(final double x2);

	/**
	 * Sets the y coordinate of the first point.
	 * @param y1 The new y coordinate of the first point.
	 * @since 3.0
	 */
	void setY1(final double y1);

	/**
	 * Sets the y coordinate of the second point.
	 * @param y2 The new y coordinate of the second point.
	 * @since 3.0
	 */
	void setY2(final double y2);

	/**
	 * Sets the first point.
	 * @param pt The new first point.
	 * @since 3.0
	 */
	void setP1(final IPoint pt);

	/**
	 * Sets the second point.
	 * @param pt The new second point.
	 * @since 3.0
	 */
	void setP2(final IPoint pt);

	/**
	 * Sets the coordinates of the first point.
	 * @param x The new x coordinate of the first point.
	 * @param y The new y coordinate of the first point.
	 * @since 3.0
	 */
	void setP1(final double x, final double y);

	/**
	 * Sets the coordinates of the second point.
	 * @param x The new x coordinate of the second point.
	 * @param y The new y coordinate of the second point.
	 * @since 3.0
	 */
	void setP2(final double x, final double y);

	/**
	 * @return the a parameter of the line.
	 */
	double getA();

	/**
	 * @return the b parameter of the line.
	 */
	double getB();

	/**
	 * @return The top left point of the line (may be not a point on the line).
	 */
	IPoint getTopLeftPoint();

	/**
	 * @return The bottom right point of the line (may be not a point on the line).
	 */
	IPoint getBottomRightPoint();

	/**
	 * Creates the line which is perpendicular to the current line at the point pt.
	 * @param pt The point of crossing between the two lines.
	 * @return The perpendicular line.
	 */
	ILine getPerpendicularLine(final IPoint pt);

	/**
	 * @param l The second lines
	 * @return The intersection between two lines. Null if the given is not valid or
	 * if both lines are parallels or both lines are identical.
	 */
	IPoint getIntersection(final ILine l);

	/**
	 * @return The middle point of the line.
	 */
	IPoint getMiddlePt();

	/**
	 * @param l The second line.
	 * @return The point of the intersection between of the two segments.
	 */
	IPoint getIntersectionSegment(final ILine l);

	/**
	 * Gets the points which are on the line and at the distance
	 * "distance" of the point "p" of the line.
	 * @param x The x-coordinate of the point of reference.
	 * @param y The y-coordinate of the point of reference.
	 * @param distance The distance between p and the points we must find.
	 * @return The found points or null if a problem occurs.
	 */
	IPoint[] findPoints(final double x, final double y, final double distance);

	/**
	 * Gets the points which are on the line and at the distance
	 * "distance" of the point "p" of the line.
	 * @param p The point of reference.
	 * @param distance The distance between p and the points we must find.
	 * @return The found points or null if a problem occurs.
	 */
	IPoint[] findPoints(final IPoint p, final double distance);

	/**
	 * Gets the points which are on the line and at the distance
	 * "distance" of the point "p" of the line.
	 * @param p The point of reference.
	 * @param distance The distance between p and the points we must find.
	 * @return The found points or null if a problem occurs.
	 */
	IPoint[] findPoints(final Point2D p, final double distance);

	/**
	 * Gets the Y-coordinate thanks to the equation of the line and the X-coordinate of the point.
	 * The line must not by vertical.
	 * @param x The X-coordinate of the point.
	 * @return The Y-coordinate of the point or NaN if the line is vertical or if the two points
	 * of the line are equal, or if the given x is not valid.
	 */
	double getYWithEquation(final double x);

	/**
	 * Gets the X-coordinate thanks to the equation of the line and the Y-coordinate of the point.
	 * The line must not by horizontal.
	 * @param y The Y-coordinate of the point.
	 * @return The X-coordinate of the point or NaN if the line is horizontal or if the two points
	 * of the line are equal, or if the given y is not valid.
	 */
	double getXWithEquation(final double y);

	/**
	 * Update the y-intercept <code>b</code> and slope <code>a</code>.
	 */
	void updateAandB();
}
