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
package net.sf.latexdraw.model.api.property;

import net.sf.latexdraw.model.api.shape.Color;

/**
 * Properties of grids.
 * @author Arnaud Blouin
 */
public interface GridProp extends IStdGridProp {
	/**
	 * @return Returns the gridDots.
	 */
	int getGridDots();

	/**
	 * @param gridDots The gridDots to set. Must be greater or equal than 0.
	 */
	void setGridDots(final int gridDots);

	/**
	 * @return Returns the gridLabelsDviPsColors.
	 */
	Color getGridLabelsColour();

	/**
	 * @param gridLabelsColour The gridLabelsColor to set.
	 */
	void setGridLabelsColour(final Color gridLabelsColour);

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
	 * @return Returns the gridWidth.
	 */
	double getGridWidth();

	/**
	 * @param gridWidth The gridWidth to set. Must be greater than 0.
	 */
	void setGridWidth(final double gridWidth);

	/**
	 * @return Returns the subGridDviPsColors.
	 */
	Color getSubGridColour();

	/**
	 * @param subGridColour The subGridColor to set.
	 */
	void setSubGridColour(final Color subGridColour);

	/**
	 * @return Returns the subGridDiv.
	 */
	int getSubGridDiv();

	/**
	 * @param subGridDiv The subGridDiv to set. Must be greater or equal than 0.
	 */
	void setSubGridDiv(final int subGridDiv);

	/**
	 * @return Returns the subGridDots.
	 */
	int getSubGridDots();

	/**
	 * @param subGridDots The subGridDots to set. Must be greater or equal than 0.
	 */
	void setSubGridDots(final int subGridDots);

	/**
	 * @return Returns the subGridWidth.
	 */
	double getSubGridWidth();

	/**
	 * @param subGridWidth The subGridWidth to set. Must be greater than 0.
	 */
	void setSubGridWidth(final double subGridWidth);

	/**
	 * @return Returns the unit.
	 */
	double getUnit();

	/**
	 * @param unit The unit to set. Must be greater than 0.
	 */
	void setUnit(final double unit);
}
