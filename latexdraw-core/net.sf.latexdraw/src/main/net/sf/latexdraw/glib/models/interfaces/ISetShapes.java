package net.sf.latexdraw.glib.models.interfaces;

import java.util.List;

/**
 * Defines an interface of a set of shapes.<br>
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
 * 02/22/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface ISetShapes {
	/**
	 * Adds a shape to the drawing.
	 * @param s The shape to add. Does nothing if the given shape is null.
	 */
	void addShape(final IShape s);

	/**
	 * Adds a shape to the drawing at at given position.
	 * @param s The shape to add. Does nothing if the given shape is null.
	 * @param index The position where the figure must be inserted. Does nothing if the given position is not valid.
	 */
	void addShape(final IShape s, final int index);

	/**
	 * Removes a shape of the drawing.
	 * @param s The shape to remove.
	 * @return true if the given shape is removed. False if the given shape is null.
	 */
	boolean removeShape(final IShape s);

	/**
	 * Removes a shape of the drawing a the given position.
	 * @param i the position of the shape in the vector (-1: the last shape of the vector).
	 * @return The deleted shape if it exists. Null if the given position is not valid.
	 * @since 1.9.1
	 */
	IShape removeShape(final int i);

	/**
	 * Allows to get the shape located at the given position.
	 * @param i The position of the figure (-1: the last shape of the drawing).
	 * @return The searched shape if it exists. Null if the given position is not valid.
	 */
	IShape getShapeAt(final int i);

	/**
	 * Allows to get the number of shapes that contains the drawing.
	 * @return The number of shapes in the drawing.
	 */
	int size();

	/**
	 * Allows to know if a shape is in the drawing.
	 * @param s The shape to check.
	 * @return True if the shape is in the drawing. False when the given shape is null.
	 */
	boolean contains(final IShape s);

	/**
	 * Allows to know if the drawing is empty or not.
	 * @return True if there is at least one shape in the drawing.
	 */
	boolean isEmpty();

	/**
	 * Empties the drawing.
	 */
	void clear();

	/**
	 * @return The shapes of the drawing.
	 */
	List<IShape> getShapes();
}
