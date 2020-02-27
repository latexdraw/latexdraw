/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.shape;

import java.awt.geom.Point2D;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point3D;
import org.jetbrains.annotations.NotNull;

/**
 * The API for points.
 * @author Arnaud BLOUIN
 */
public interface Point {
	/**
	 * @return The Y coordinate of the point.
	 */
	double getY();

	/**
	 * Sets the Y coordinate of the point.
	 * @param newY The new Y coordinate. Must be valid (not equal too NaN,...).
	 */
	void setY(final double newY);

	/**
	 * @return The X coordinate of the point.
	 */
	double getX();

	/**
	 * Sets the X coordinate of the point.
	 * @param newX The new X coordinate. Must be valid (not equal too NaN,...).
	 */
	void setX(final double newX);

	/**
	 * Changes the coordinates of the point.
	 * @param pt The new position.
	 */
	void setPoint(final Point pt);

	/**
	 * Sets the coordinates of the point.
	 * @param newX The new X coordinate. Must be valid (not equal too NaN,...).
	 * @param newY The new Y coordinate. Must be valid (not equal too NaN,...).
	 */
	void setPoint(final double newX, final double newY);

	/**
	 * Gets a point by central symmetry.
	 * @param pt The centre of the symmetry.
	 * @return The resulting point.
	 */
	Point centralSymmetry(final Point pt);

	/**
	 * Returns horizontally the point.
	 * @param x The location of the horizontal axe.
	 * @return the computed point or null is the given point is not valid or if a problem occurs.
	 */
	Point horizontalSymmetry(final double x);

	/**
	 * Returns vertically the point.
	 * @param y The location of the vertical axe.
	 * @return the computed point or null is the given point is not valid or if a problem occurs.
	 */
	Point verticalSymmetry(final double y);

	/**
	 * Adds the given pt to the current one: this+pt
	 * @param pt The second point of the subtraction
	 * @return The result of the subtraction.
	 */
	@NotNull Point add(final Point pt);

	/**
	 * Subtracts the given pt to the current one: this-pt
	 * @param pt The second point of the subtraction
	 * @return The result of the subtraction.
	 */
	@NotNull Point substract(final Point pt);

	/**
	 * Normalizes the point (considered here as a vector).
	 * @return The vector normalization.
	 */
	@NotNull Point normalise();

	/**
	 * The magnitude (length) of the point considered here as a vector.
	 * @return The computed magnitude.
	 */
	double magnitude();

	/**
	 * Computes the angle of the given point where the calling point is used as
	 * the gravity centre.
	 * @param pt The point used to compute the angle.
	 * @return The angle or NaN if the given point is not valid.
	 */
	double computeAngle(final Point pt);

	/**
	 * Computes the angle of rotation between two points where the calling point is
	 * used as the gravity centre.
	 * @param pt1 The first point.
	 * @param pt2 The second point.
	 * @return The rotation point or NaN if one of the given point is not valid.
	 */
	double computeRotationAngle(final Point pt1, final Point pt2);

	/**
	 * Allows to know if the point p is equal to the current point considering a gap.
	 * @param p The point to compare.
	 * @param gap The approximation gap.
	 * @return True if they are equals considering the gap.
	 * @throws IllegalArgumentException When <code>gap<0</code>
	 */
	boolean equals(final Point p, final double gap);

	/**
	 * @param p The second point.
	 * @return The middle point of the current and given points.
	 */
	Point getMiddlePoint(final Point p);

	/**
	 * Rotates a point with as reference another point.
	 * @param gravityC The point of reference.
	 * @param theta The angle of rotation in radian.
	 * @return The rotated point.
	 */
	Point rotatePoint(final Point gravityC, final double theta);

	/**
	 * Creates a new point zoomed using the calling points.
	 * @param zoomLevel The zoom level.
	 * @return The zoomed point.
	 */
	Point zoom(final double zoomLevel);

	/**
	 * Translates the point. If one of the given coordinate is not valid (NaN, infinite,...), then
	 * the translation does not occur.
	 * @param tx The X translation.
	 * @param ty The Y translation.
	 */
	void translate(final double tx, final double ty);

	/**
	 * @param pt The second point.
	 * @return The distance between the two points.
	 */
	double distance(final Point pt);

	/**
	 * @param x The x coordinate of the second point.
	 * @param y The y coordinate of the second point.
	 * @return The distance between the two points.
	 */
	double distance(final double x, final double y);

	/**
	 * @return A Point2D.Double point equivalent to the current point.
	 */
	Point2D.Double toPoint2D();

	/**
	 * @return A Point3D point equivalent to the current point.
	 */
	Point3D toPoint3D();

	/**
	 * Defines the current point using the given point.
	 * @param pt The point 2D to copy.
	 */
	void setPoint2D(final Point2D pt);

	/**
	 * @return The X property of the point.
	 */
	DoubleProperty xProperty();

	/**
	 * @return The Y property of the point.
	 */
	DoubleProperty yProperty();
}
