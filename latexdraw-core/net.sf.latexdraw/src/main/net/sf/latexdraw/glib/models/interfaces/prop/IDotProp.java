package net.sf.latexdraw.glib.models.interfaces.prop;

import net.sf.latexdraw.glib.models.interfaces.shape.Color;
import net.sf.latexdraw.glib.models.interfaces.shape.DotStyle;

/**
 * Properties of dot shapes.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 */
public interface IDotProp {
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
	 * @return the diametre of the dot.
	 * @since 3.0
	 */
	double getDiametre();

	/**
	 * Defines the diametre of the dot.
	 * @param diametre the diametre to set. Must be greater than 0.
	 * @since 3.0
	 */
	void setDiametre(final double diametre);

	/**
	 * @return The filling colour of the dottable or null if not fillable.
	 * @since 3.0
	 */
	Color getDotFillingCol();

	/**
	 * Sets the filling colour of the dottable.
	 * @param fillingCol its new colour.
	 * @since 3.0
	 */
	void setDotFillingCol(final Color fillingCol);
}
