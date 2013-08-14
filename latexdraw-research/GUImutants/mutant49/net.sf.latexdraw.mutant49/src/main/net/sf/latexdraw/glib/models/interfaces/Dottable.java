package net.sf.latexdraw.glib.models.interfaces;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;

/**
 * Defines an interface for shapes that support dot styles and dot radius.<br>
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
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface Dottable {
	/**
	 * @return The style of the dot.
	 * @since 3.0
	 */
	DotStyle getDotStyle();

	/**
	 * Defines the style of the dot.
	 * @param style The new style.
	 * @since 3.0
	 */
	void setDotStyle(final DotStyle style);

	/**
	 * @return the radius of the dot.
	 * @since 3.0
	 */
	double getRadius();

	/**
	 * Defines the radius of the dot.
	 * @param radius the radius to set. Must be greater than 0.
	 * @since 3.0
	 */
	void setRadius(final double radius);

	/**
	 * @return The filling colour of the dottable or null if not fillable.
	 * @since 3.0
	 */
	Color getDotFillingCol();

	/**
	 * @return True if the dot can be filled.
	 * @since 3.0
	 */
	boolean isFillable();

	/**
	 * Sets the filling colour of the dottable.
	 * @param fillingCol its new colour.
	 * @since 3.0
	 */
	void setDotFillingCol(final Color fillingCol);
}
