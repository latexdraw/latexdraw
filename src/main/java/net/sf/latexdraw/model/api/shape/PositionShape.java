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
package net.sf.latexdraw.model.api.shape;

/**
 * The API for shapes that have a position.
 * @author Arnaud BLOUIN
 */
public interface PositionShape extends SingleShape {
	/**
	 * @return The X coordinate of the shape (of the bottom-left point of the shape).
	 */
	double getX();

	/**
	 * Sets the X coordinate of the shape (of the bottom-left point of the shape).
	 * @param x The X coordinate of the shape.
	 */
	void setX(final double x);

	/**
	 * @return The Y coordinate of the shape (of the bottom-left point of the shape).
	 */
	double getY();

	/**
	 * Sets the Y coordinate of the shape (of the bottom-left point of the shape).
	 * @param y The Y coordinate of the shape.
	 */
	void setY(final double y);

	/**
	 * @return The position of the shape (the bottom-left point of the shape).
	 */
	Point getPosition();

	/**
	 * Sets the position of the shape (the bottom-left point of the shape).
	 * @param pt The new position of the shape.
	 */
	void setPosition(final Point pt);

	/**
	 * Sets the position of the shape (the bottom-left point of the shape).
	 * @param x The X coordinate of the new position of the shape.
	 * @param y The Y coordinate of the new position of the shape.
	 */
	void setPosition(final double x, final double y);
}
