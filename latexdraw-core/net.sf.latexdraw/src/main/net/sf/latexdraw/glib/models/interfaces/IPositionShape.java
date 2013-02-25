package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface that classes defining a shape that has a position should implement.<br>
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
public interface IPositionShape extends IShape {
	/**
	 * @return The X coordinate of the shape (of the bottom-left point of the shape).
	 * @since 3.0
	 */
	double getX();

	/**
	 * @return The Y coordinate of the shape (of the bottom-left point of the shape).
	 * @since 3.0
	 */
	double getY();

	/**
	 * @return The position of the shape (the bottom-left point of the shape).
	 * @since 3.0
	 */
	IPoint getPosition();

	/**
	 * Sets the X coordinate of the shape (of the bottom-left point of the shape).
	 * @param x The X coordinate of the shape.
	 * @since 3.0
	 */
	void setX(final double x);

	/**
	 * Sets the Y coordinate of the shape (of the bottom-left point of the shape).
	 * @param y The Y coordinate of the shape.
	 * @since 3.0
	 */
	void setY(final double y);

	/**
	 * Sets the position of the shape (the bottom-left point of the shape).
	 * @param pt The new position of the shape.
	 * @since 3.0
	 */
	void setPosition(final IPoint pt);

	/**
	 * Sets the position of the shape (the bottom-left point of the shape).
	 * @param x The X coordinate of the new position of the shape.
	 * @param y The Y coordinate of the new position of the shape.
	 * @since 3.0
	 */
	void setPosition(final double x, final double y);
}
