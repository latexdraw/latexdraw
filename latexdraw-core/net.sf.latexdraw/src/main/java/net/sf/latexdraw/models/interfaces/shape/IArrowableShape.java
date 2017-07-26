/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.interfaces.shape;

import java.util.List;
import net.sf.latexdraw.models.interfaces.prop.IArrowable;

/**
 * The API for shapes that can have arrows.
 * @author Arnaud Blouin
 */
public interface IArrowableShape extends IShape, IArrowable {
	/**
	 * @param arrow The arrow to look for.
	 * @return The index of the given arrow in the set of arrows of the shape. -1 is not in the set.
	 * @since 3.1
	 */
	int getArrowIndex(final IArrow arrow);

	/**
	 * @return The number of arrows.
	 * @since 3.1
	 */
	int getNbArrows();

	/**
	 * Sets the style of the arrow at the given position.
	 * @param style The style to set.
	 * @param position The position of the arrow to modify.
	 * @since 3.0
	 */
	void setArrowStyle(final ArrowStyle style, final int position);

	/**
	 * @param position The position of the arrow to use.
	 * @return The style of the arrow at the given position.
	 * @since 3.0
	 */
	ArrowStyle getArrowStyle(final int position);

	/**
	 * @param position The position of the wanted arrow (-1 for the last arrow).
	 * @return The arrow at the given position or null if the position is not valid.
	 * @since 3.0
	 */
	IArrow getArrowAt(final int position);

	List<IArrow> getArrows();
}
