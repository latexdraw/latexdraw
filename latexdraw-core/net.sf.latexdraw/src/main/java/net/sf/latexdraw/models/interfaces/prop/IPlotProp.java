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
package net.sf.latexdraw.models.interfaces.prop;

import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;

/**
 * Properties of plots.
 * @author Arnaud Blouin
 */
public interface IPlotProp extends IScalable, IDotProp {
	/**
	 * @return True if the plot is defined for polar coordinates (false: for a cartesian coordinates).
	 * @since 3.3
	 */
	boolean isPolar();

	/**
	 * Sets if the plot is defined for a radial or a cartesian system.
	 * @param polar True: polar, false: cartesian
	 * @since 3.3
	 */
	void setPolar(final boolean polar);

	/**
	 * @return The equation.
	 */
	String getPlotEquation();

	/**
	 * Sets the equation.
	 * @param equation The equation to set. Nothing done if null or empty.
	 * @throws InvalidFormatPSFunctionException When the given equation is not valid.
	 */
	void setPlotEquation(final String equation);

	/**
	 * @return Returns the X-min value of the plotted function.
	 */
	double getPlotMinX();

	/**
	 * Sets the X-min value of the plotted function.
	 * @param minX The X-min value of the plotted function. Must be lower than X-max.
	 */
	void setPlotMinX(final double minX);

	/**
	 * @return Returns the X-max value of the plotted function.
	 */
	double getPlotMaxX();

	/**
	 * Sets the X-max value of the plotted function.
	 * @param maxX The X-max value of the plotted function. Must be greater than X-min.
	 */
	void setPlotMaxX(final double maxX);

	/**
	 * @return the nbPoints.
	 */
	int getNbPlottedPoints();

	/**
	 * Sets the number of points to plot.
	 * @param nbPlottedPoints The number of points to plot. Must be greater than 1.
	 */
	void setNbPlottedPoints(final int nbPlottedPoints);

	/**
	 * @return The step between the points to plot.
	 */
	double getPlottingStep();

	/** @return The current plot style. */
	PlotStyle getPlotStyle();

	/**
	 * Sets the plot style.
	 * @param style The new plot style. Nothing done if null.
	 */
	void setPlotStyle(final PlotStyle style);
}
