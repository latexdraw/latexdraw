package org.malai.picking;

import java.awt.geom.Point2D;

/**
 * Defines an interface for objects that contains pickable and picker objects.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/10/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.1
 * @since 0.1
 */
public interface Picker {
	/**
	 * @param x The x-coordinate of the position used to get the pickable object.
	 * @param y The y-coordinate of the position used to get the pickable object.
	 * @return The pickable object at the given position.
	 * @since 0.1
	 */
	Pickable getPickableAt(final double x, final double y);

	/**
	 * @param x The x-coordinate of the position used to get the picker object.
	 * @param y The y-coordinate of the position used to get the picker object.
	 * @return The pickable object at the given position.
	 * @since 0.1
	 */
	Picker getPickerAt(final double x, final double y);

	/**
	 * @param x The x-coordinate of the position to convert.
	 * @param y The y-coordinate of the position to convert.
	 * @param o An object contained by the calling picker. This function will computed the real position of the given
	 * point in <code>o</code>.
	 * @return Converts the given point in to fit the coordinates of the given object contained by the picker.
	 * For instance, given an object <code>o1</code> that contains an other object <code>o2</code> at position <code>(10, 10)</code>. <code>o1.getRelativePoint(30, 30, o2)</code>
	 * will return <code>(20, 20)</code>.
	 * @since 0.1
	 */
	Point2D getRelativePoint(final double x, final double y, final Object o);

	/**
	 * Tests if the given object is contained by the calling picker.
	 * @param obj The object to test.
	 * @return True: the given object is contained by the calling picker.
	 * @since 0.1
	 */
	boolean contains(final Object obj);//TODO Should obj be a Pickable?
}
