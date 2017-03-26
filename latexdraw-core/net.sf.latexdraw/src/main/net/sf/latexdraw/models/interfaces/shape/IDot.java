/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.interfaces.shape;

import net.sf.latexdraw.models.interfaces.prop.IDotProp;

/**
 * The API for dots.
 * @author Arnaud BLOUIN
 */
public interface IDot extends IPositionShape, IDotProp {
	/** Useful to calculate the thickness of dot with the o style. */
	double THICKNESS_O_STYLE_FACTOR = 16.;

	/**
	 * While getTopLeftPoint takes care about the current shape of the dot to compute the top left point,
	 * this function computes the top left point only using the centre and the width of the dot which are
	 * the same for all the dot styles
	 * @return The top left point of the dot.
	 * @since 3.0
	 */
	IPoint getLazyTopLeftPoint();

	/**
	 * While getBottomRightPoint takes care about the current shape of the dot to compute the bottom right point,
	 * this function computes the bottom right point only using the centre and the width of the dot which are
	 * the same for all the dot styles
	 * @return The top bottom right of the dot.
	 * @since 3.0
	 */
	IPoint getLazyBottomRightPoint();

	/**
	 * @return The gap used to create plus-shaped dots.
	 * @since 3.0
	 */
	double getPlusGap();

	/**
	 * @return The gap used to create cross-shaped dots.
	 * @since 3.0
	 */
	double getCrossGap();

	/**
	 * @return The gap used to create bar-shaped dots.
	 * @since 3.0
	 */
	double getBarGap();

	/**
	 * @return The thickness used to create bar-shaped dots.
	 * @since 3.0
	 */
	double getBarThickness();

	/**
	 * @return The gap used to compute the shape of several kinds of dot.
	 * @since 3.0
	 */
	double getGeneralGap();

	/**
	 * @return The gap used to create O-shaped dots.
	 * @since 3.0
	 */
	double getOGap();

	/**
	 * @return True if the dot can be filled.
	 * @since 3.0
	 */
	@Override
	boolean isFillable();
}
