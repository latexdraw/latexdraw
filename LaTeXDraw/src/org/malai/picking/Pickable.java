package org.malai.picking;

/**
 * Defines an interface for objects that can be picked. A pickable object must be contained by a picker object.<br>
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
public interface Pickable {
	/**
	 * Tests if the given point is into the pickable object.
	 * @param x The x-coordinate of the point to test.
	 * @param y The y-coordinate of the point to test.
	 * @return True if the given point is into the pickable object.
	 * @since 0.1
	 */
	boolean contains(final double x, final double y);

	/**
	 * @return The picker object that contains the pickable object.
	 * @since 0.2
	 */
	Picker getPicker();
}
