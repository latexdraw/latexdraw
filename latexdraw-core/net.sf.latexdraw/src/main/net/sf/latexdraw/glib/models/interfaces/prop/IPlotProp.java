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
public interface IPlotProp extends IScalable{
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
			for(final PlotStyle fh : values())
				if(fh.getPSTToken().equals(latexToken))
					return fh;
			return CURVE;
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
	 * @return the equation.
	 */
	String getPlotEquation();

	/**
	 * @param equation the equation to set.
	 */
	void setPlotEquation(final String equation);

	/**
	 * @return the minX.
	 */
	double getPlotMinX();

	/**
	 * @param minX the minX to set. Must be greater than maxX.
	 */
	void setPlotMinX(final double minX);

	/**
	 * @return the maxX. Must be lesser than minX.
	 */
	double getPlotMaxX();

	/**
	 * @param maxX the maxX to set.
	 */
	void setPlotMaxX(final double maxX);

	/**
	 * @return the nbPoints.
	 */
	int getNbPlottedPoints();

	/**
	 * @param nbPlottedPoints the nbPoints to set.
	 */
	void setNbPlottedPoints(final int nbPlottedPoints);

	/**
	 * @return The step between the points to plot.
	 */
	double getPlottingStep();

	//TODO later
//	/**
//	 * @return the xScale.
//	 */
//	double getXScale();
//
//	/**
//	 * @param scale the xScale to set.
//	 */
//	void setXScale(final double scale);
//
//	/**
//	 * @return the yScale.
//	 */
//	double getYScale();
//
//	/**
//	 * @param scale the yScale to set.
//	 */
//	void setYScale(final double scale);

	/** @return The current plot style. */
	PlotStyle getPlotStyle();

	/**
	 * @param style The new plot style.
	 */
	void setPlotStyle(final PlotStyle style);
}
