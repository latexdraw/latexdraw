package net.sf.latexdraw.glib.models.interfaces;

import java.awt.Color;

/**
 * Defines an interface that classes defining a latex grid should implement.<br>
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
public interface IGrid extends IStandardGrid {
	/**
	 * @return Returns the gridDots.
	 */
	int getGridDots();

	/**
	 * @param gridDots The gridDots to set. Must be greater or equal than 0.
	 */
	void setGridDots(final int gridDots);

	/**
	 * @return Returns the gridLabelsColor.
	 */
	Color getGridLabelsColour();

	/**
	 * @param gridLabelsColour The gridLabelsColor to set.
	 */
	void setGridLabelsColour(final Color gridLabelsColour);


	/**
	 * @return Returns the gridWidth.
	 */
	double getGridWidth();

	/**
	 * @param gridWidth The gridWidth to set. Must be greater than 0.
	 */
	void setGridWidth(final double gridWidth);

	/**
	 * @return Returns the subGridColor.
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
	 * @param unit The unit to set. Must be greater than 0.
	 */
	void setUnit(final double unit);

	/**
	 * @return Returns the unit.
	 */
	double getUnit();
}
