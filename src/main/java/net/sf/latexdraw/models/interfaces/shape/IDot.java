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
package net.sf.latexdraw.models.interfaces.shape;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;

/**
 * The API for dots.
 * @author Arnaud BLOUIN
 */
public interface IDot extends IPositionShape, IDotProp {
	/** Useful to calculate the thickness of dot with the o style. */
	double THICKNESS_O_STYLE_FACTOR = 16d;

	/** The thickness of the plus shape is computed with that coefficient. */
	double PLUS_COEFF_WIDTH = 6.5;

	/**
	 * While getTopLeftPoint takes care about the current shape of the dot to compute the top left point,
	 * this function computes the top left point only using the centre and the width of the dot which are
	 * the same for all the dot styles
	 * @return The top left point of the dot.
	 */
	IPoint getLazyTopLeftPoint();

	/**
	 * While getBottomRightPoint takes care about the current shape of the dot to compute the bottom right point,
	 * this function computes the bottom right point only using the centre and the width of the dot which are
	 * the same for all the dot styles
	 * @return The top bottom right of the dot.
	 */
	IPoint getLazyBottomRightPoint();

	/**
	 * @return The gap used to create plus-shaped dots.
	 */
	double getPlusGap();

	/**
	 * @return The gap used to create cross-shaped dots.
	 */
	double getCrossGap();

	/**
	 * @return The gap used to create bar-shaped dots.
	 */
	double getBarGap();

	/**
	 * @return The thickness used to create bar-shaped dots.
	 */
	double getBarThickness();

	/**
	 * @return The gap used to compute the shape of several kinds of dot.
	 */
	double getGeneralGap();

	/**
	 * @return The gap used to create O-shaped dots.
	 */
	double getOGap();

	/**
	 * @return True if the dot can be filled.
	 */
	@Override
	boolean isFillable();

	/**
	 * @return The dot style property.
	 */
	ObjectProperty<DotStyle> styleProperty();

	/**
	 * @return The dot diametre property.
	 */
	DoubleProperty diametreProperty();
}
