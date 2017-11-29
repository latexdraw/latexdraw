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
package net.sf.latexdraw.view;

import javafx.geometry.Point3D;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.malai.properties.Modifiable;
import org.malai.properties.Preferenciable;

/**
 * The API that defines a magnetic grid.
 * @author Arnaud Blouin
 */
public interface MagneticGrid extends Preferenciable, Modifiable {
	/**
	 * Transform a point to another "stick" to the magnetic grid.
	 * @param pt The point to transform.
	 * @return The transformed point or if there is no magnetic grid, a clone of the given point.
	 */
	IPoint getTransformedPointToGrid(final Point3D pt);

	/**
	 * @return The gap between the lines of the magnetic grid.
	 */
	double getMagneticGridGap();

	/**
	 * @return True: the grid is magnetic.
	 */
	boolean isMagnetic();

	/**
	 * @param isMagnetic True: the grid will be magnetic.
	 */
	void setMagnetic(final boolean isMagnetic);

	/**
	 * @return The style of the magnetic grid.
	 */
	GridStyle getGridStyle();

	/**
	 * @param style The new style of the grid. If null, nothing is performed.
	 */
	void setGridStyle(final GridStyle style);

	/**
	 * @return True: The magnetic grid must be displayed.
	 */
	boolean isGridDisplayed();

	/**
	 * @return True: the grid is magnetic.
	 */
	boolean isPersonalGrid();

	/**
	 * @return The new spacing between lines of the personal grid.
	 */
	int getGridSpacing();

	/**
	 * @param gridSpacing The new spacing between lines of the personal grid.
	 */
	void setGridSpacing(final int gridSpacing);

	/**
	 * Reinitialises the magnetic grid.
	 */
	void reinitGrid();

	/**
	 * Recomputes the grid.
	 */
	void update();
}
