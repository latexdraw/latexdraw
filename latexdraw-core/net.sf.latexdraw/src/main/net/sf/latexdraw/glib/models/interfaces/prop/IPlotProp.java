package net.sf.latexdraw.glib.models.interfaces.prop;

import java.util.Arrays;

import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;

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
public interface IPlotProp extends IScalable, IDotProp{
	/**
	 * The different possible plotting styles.
	 */
	enum PlotStyle {
		CURVE {
			@Override public String getPSTToken() { return "curve"; } //$NON-NLS-1$
		}, LINE {
			@Override public String getPSTToken() { return "line";} //$NON-NLS-1$
		}, DOTS {
			@Override public String getPSTToken() { return "dots";} //$NON-NLS-1$
		}, POLYGON {
			@Override public String getPSTToken() { return "polygon";} //$NON-NLS-1$
		}, ECURVE {
			@Override public String getPSTToken() { return "ecurve";} //$NON-NLS-1$
		}, CCURVE {
			@Override public String getPSTToken() {return "ccurve";} //$NON-NLS-1$
		};

		/** @return The PST token corresponding to the plot style. */
		public abstract String getPSTToken();

		/**
		 * @param latexToken The latex token to check.
		 * @return The style corresponding to the PSTricks token given as parameter, or CURVE otherwise.
		 * @since 3.2
		 */
		public static PlotStyle getPlotStyle(final String latexToken) {
			return Arrays.asList(values()).stream().filter(it -> it.getPSTToken().equals(latexToken)).findFirst().orElse(CURVE);
		}
	}

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
	void setPlotEquation(final String equation) throws InvalidFormatPSFunctionException;

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
