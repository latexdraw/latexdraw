package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface for rectangular shapes.<br>
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
public interface IRectangularShape extends IPositionShape {
	/**
	 * @return The width of the rectangle.
	 * @since 3.0
	 */
	double getWidth();

	/**
	 * @return The height of the rectangle.
	 * @since 3.0
	 */
	double getHeight();

	/**
	 * Sets the width of the rectangle (the reference point is the bottom-left point of the rectangle).
	 * @param width The new width.
	 * @since 3.0
	 */
	void setWidth(final double width);

	/**
	 * Sets the height of the rectangle (the reference point is the bottom-left point of the rectangle).
	 * @param height The new height.
	 * @since 3.0
	 */
	void setHeight(final double height);
}
