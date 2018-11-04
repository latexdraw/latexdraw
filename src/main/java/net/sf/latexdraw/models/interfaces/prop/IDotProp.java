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
package net.sf.latexdraw.models.interfaces.prop;

import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;

/**
 * Properties of dots.
 * @author Arnaud Blouin
 */
public interface IDotProp {
	/**
	 * @return The style of the dot.
	 */
	DotStyle getDotStyle();

	/**
	 * Defines the style of the dot.
	 * @param style The new style.
	 */
	void setDotStyle(final DotStyle style);

	/**
	 * @return the diametre of the dot.
	 */
	double getDiametre();

	/**
	 * Defines the diametre of the dot.
	 * @param diametre the diametre to set. Must be greater than 0.
	 */
	void setDiametre(final double diametre);

	/**
	 * @return The filling colour of the dottable or null if not fillable.
	 */
	Color getDotFillingCol();

	/**
	 * Sets the filling colour of the dottable.
	 * @param fillingCol its new colour.
	 */
	void setDotFillingCol(final Color fillingCol);
}
