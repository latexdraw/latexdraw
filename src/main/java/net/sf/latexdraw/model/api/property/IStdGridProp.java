/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.property;

import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * Properties of standard grids.
 * @author Arnaud Blouin
 */
public interface IStdGridProp {
	/**
	 * @return The minimal X-tick of the grid.
	 */
	double getGridMinX();

	/**
	 * @return The maximal X-tick of the grid.
	 */
	double getGridMaxX();

	/**
	 * @return The minimal Y-tick of the grid.
	 */
	double getGridMinY();

	/**
	 * @return The maximal Y-tick of the grid.
	 */
	double getGridMaxY();

	/**
	 * @return The size of the labels.
	 */
	int getLabelsSize();

	/**
	 * Defines the size of the labels.
	 * @param labelsSize The new size of the labels.
	 */
	void setLabelsSize(final int labelsSize);

	/**
	 * @return The x-coordinate of the starting point of the grid.
	 */
	double getGridStartX();

	/**
	 * @param x The x-coordinate to set.
	 */
	void setGridStartX(final double x);

	/**
	 * @return The y-coordinate of the starting point of the grid.
	 */
	double getGridStartY();

	/**
	 * @param y The y-coordinate to set.
	 */
	void setGridStartY(final double y);

	/**
	 * @return The starting point of the grid.
	 */
	@NotNull Point getGridStart();

	/**
	 * @return The ending point of the grid.
	 */
	@NotNull Point getGridEnd();

	/**
	 * @param x The x-coordinate of the starting point of the grid.
	 * @param y The y-coordinate of the starting point of the grid.
	 */
	void setGridStart(final double x, final double y);

	/**
	 * @return The x-coordinate of the ending point of the grid.
	 */
	double getGridEndX();

	/**
	 * @param x The x-coordinate to set.
	 */
	void setGridEndX(final double x);

	/**
	 * @return The y-coordinate of the ending point of the grid.
	 */
	double getGridEndY();

	/**
	 * @param y The y-coordinate to set.
	 */
	void setGridEndY(final double y);

	/**
	 * @param x The x-coordinate of the ending point of the grid.
	 * @param y The y-coordinate of the ending point of the grid.
	 */
	void setGridEnd(final double x, final double y);

	/**
	 * @return The x-coordinate of the origin.
	 */
	double getOriginX();

	/**
	 * @param x The X-coordinate to set.
	 */
	void setOriginX(final double x);

	/**
	 * @return The y-coordinate of the origin.
	 */
	double getOriginY();

	/**
	 * @param y The Y-coordinate to set.
	 */
	void setOriginY(final double y);

	/**
	 * @param x The x-coordinate of the origin of the grid.
	 * @param y The y-coordinate of the origin of the grid.
	 */
	void setOrigin(final double x, final double y);

	/**
	 * @return The size of a step of the grid (can be LShape.PPC or LShape.PPC*unit for instance).
	 */
	double getStep();
}
