package net.sf.latexdraw.glib.models.interfaces.prop;

/**
 * Plot shapes' properties.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public interface IPlotProp {
	/**
	 * @return the interval
	 */
	double getInterval();

	/**
	 * @return the equation.
	 */
	String getEquation();

	/**
	 * @param equation the equation to set.
	 */
	void setEquation(final String equation);

	/**
	 * @return the minX.
	 */
	double getMinX();

	/**
	 * @param minX the minX to set.
	 */
	void setMinX(final double minX);

	/**
	 * @return the maxX.
	 */
	double getMaxX();

	/**
	 * @param maxX the maxX to set.
	 */
	void setMaxX(final double maxX);

	/**
	 * @return the nbPoints.
	 */
	int getNbPlottedPoints();

	/**
	 * @param nbPlottedPoints the nbPoints to set.
	 */
	void setNbPlottedPoints(final int nbPlottedPoints);

	/**
	 * @return the xScale.
	 */
	double getXScale();

	/**
	 * @param scale the xScale to set.
	 */
	void setXScale(final double scale);

	/**
	 * @return the yScale.
	 */
	double getYScale();

	/**
	 * @param scale the yScale to set.
	 */
	void setYScale(final double scale);
}
