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
	public static enum PlotStyle {
		CURVE {
			@Override public String getPSTToken() { return "curve"; }
		}, LINE {
			@Override public String getPSTToken() { return "line";}
		}, DOTS {
			@Override public String getPSTToken() { return "dots";}
		}, POLYGON {
			@Override public String getPSTToken() { return "polygon";}
		}, ECURVE {
			@Override public String getPSTToken() { return "ecurve";}
		}, CCURVE {
			@Override public String getPSTToken() {return "ccurve";}
		};

		/** @return The PST token corresponding to the plot style. */
		public abstract String getPSTToken();

		/**
		 * @param latexToken The latex token to check.
		 * @return The style corresponding to the PSTricks token given as parameter.
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
	 * @param minX the minX to set. Must be greater than maxX.
	 */
	void setMinX(final double minX);

	/**
	 * @return the maxX. Must be lesser than minX.
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
