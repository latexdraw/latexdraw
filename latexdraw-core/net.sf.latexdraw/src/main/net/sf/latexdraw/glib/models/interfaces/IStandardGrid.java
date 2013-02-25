package net.sf.latexdraw.glib.models.interfaces;

/**
 * Defines an interface that classes defining a model for latex grid should implement.<br>
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
public interface IStandardGrid extends IPositionShape {
	/**
	 * @return The minimal X-tick of the grid.
	 * @since 3.0
	 */
	double getGridMinX();

	/**
	 * @return The maximal X-tick of the grid.
	 * @since 3.0
	 */
	double getGridMaxX();

	/**
	 * @return The minimal Y-tick of the grid.
	 * @since 3.0
	 */
	double getGridMinY();

	/**
	 * @return The maximal Y-tick of the grid.
	 * @since 3.0
	 */
	double getGridMaxY();

	/**
	 * @return The size of the labels.
	 * @since 3.0
	 */
	int getLabelsSize();


	/**
	 * Defines the size of the labels.
	 * @param labelsSize The new size of the labels.
	 * @since 3.0
	 */
	void setLabelsSize(final int labelsSize);


	/**
	 * @param x The x-coordinate to set.
	 */
	void setGridEndX(final double x);

	/**
	 * @param y The y-coordinate to set.
	 */
	void setGridEndY(final double y);

	/**
	 * @return the isXLabelSouth.
	 */
	boolean isXLabelSouth();

	/**
	 * @param isXLabelSouth the isXLabelSouth to set.
	 */
	void setXLabelSouth(final boolean isXLabelSouth);

	/**
	 * @return the isYLabelWest.
	 */
	boolean isYLabelWest();

	/**
	 * @param isYLabelWest the isYLabelWest to set.
	 */
	void setYLabelWest(final boolean isYLabelWest);

	/**
	 * @return The x-coordinate of the starting point of the grid.
	 */
	double getGridStartX();

	/**
	 * @return The y-coordinate of the starting point of the grid.
	 */
	double getGridStartY();

	/**
	 * @return The starting point of the grid.
	 * @since 3.0
	 */
	IPoint getGridStart();

	/**
	 * @return The ending point of the grid.
	 * @since 3.0
	 */
	IPoint getGridEnd();

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
	 * @return The y-coordinate of the ending point of the grid.
	 */
	double getGridEndY();

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
	 * @return The y-coordinate of the origin.
	 */
	double getOriginY();

	/**
	 * @param x The x-coordinate of the origin of the grid.
	 * @param y The y-coordinate of the origin of the grid.
	 */
	void setOrigin(final double x, final double y);

	/**
	 * @param y The y-coordinate to set.
	 */
	void setGridStartY(final double y);

	/**
	 * @param x The x-coordinate to set.
	 */
	void setGridStartX(final double x);

	/**
	 * @param x The X-coordinate to set.
	 */
	void setOriginX(final double x);

	/**
	 * @param y The Y-coordinate to set.
	 */
	void setOriginY(final double y);

	/**
	 * @return The size of a step of the grid (can be LShape.PPC or LShape.PPC*unit for instance).
	 * @since 3.0
	 */
	double getStep();
}
