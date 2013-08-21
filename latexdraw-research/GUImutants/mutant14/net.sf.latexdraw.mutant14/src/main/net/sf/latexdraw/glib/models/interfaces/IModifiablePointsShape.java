package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface for shapes that contain modifiable points.<br>
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
public interface IModifiablePointsShape extends IShape {
	/**
	 * Adds a point to the shape model.
	 * @param pt The point to add. Must be valid.
	 * @since 3.0
	 */
	void addPoint(final IPoint pt);

	/**
	 * Adds the given point to the points list at the given position. The model is not updated!
	 * @param pt The point to add.
	 * @param position The position of insertion (-1 corresponds to the last point).
	 * @since 3.0
	 */
	void addPoint(final IPoint pt, final int position);

	/**
	 * Removes the given point of the shape.
	 * @param pt The point to remove.
	 * @return True if the point is removed. False otherwise.
	 * @since 3.0
	 */
	boolean removePoint(final IPoint pt);

	/**
	 * Removes the point at the given position.
	 * @param position The position of the point to remove (-1 corresponds to the last point).
	 * @return The removed point or null.
	 * @since 3.0
	 */
	IPoint removePoint(final int position);

	/**
	 * Sets the point at the given position to the given coordinate. The model is not updated!
	 * @param p The new position of the wanted point.
	 * @param position The position of the point to move in the points list (-1 corresponds to the last point).
	 * @return true if the operation is successful.
	 * @since 3.0
	 */
	boolean setPoint(final IPoint p, final int position);

	/**
	 * Sets the point at the given position to the given coordinate.
	 * @param x The new X-coordinate. The model is not updated!
	 * @param y The new Y-coordinate.
	 * @param position The position of the point to move in the points list (-1 corresponds to the last point).
	 * @return true if the operation is successful.
	 * @since 3.0
	 */
	boolean setPoint(final double x, final double y, final int position);

	/**
	 * Replaces the point at the given position by the given point.
	 * @param pt The new point. Must not be a point of the shape.
	 * @param position The position of the point to remove (-1 corresponds to the last point).
	 * @return The removed point or null.
	 * @since 3.0
	 */
	IPoint replacePoint(final IPoint pt, final int position);
}
