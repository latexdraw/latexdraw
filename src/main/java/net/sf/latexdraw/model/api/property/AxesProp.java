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

import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import org.jetbrains.annotations.NotNull;

/**
 * Properties of axes.
 * @author Arnaud Blouin
 */
public interface AxesProp extends IStdGridProp {
	/**
	 * @return The X increment of the axes.
	 */
	double getIncrementX();

	/**
	 * @param increment the X increment to set.
	 */
	void setIncrementX(final double increment);

	/**
	 * @return The Y increment of the axes.
	 */
	double getIncrementY();

	/**
	 * @param increment the Y increment to set.
	 */
	void setIncrementY(final double increment);

	/**
	 * @return The increments of the axes.
	 */
	@NotNull Point getIncrement();

	/**
	 * @param increment The axes' increment to set.
	 */
	void setIncrement(final Point increment);

	/**
	 * @return The distance between the labels of the axes.
	 */
	@NotNull Point getDistLabels();

	/**
	 * @param distLabels The distance between the labels of the axes.
	 */
	void setDistLabels(final Point distLabels);

	/**
	 * @return the distLabels.x.
	 */
	double getDistLabelsX();

	/**
	 * @param distLabelsX the distLabels.x to set.
	 */
	void setDistLabelsX(final double distLabelsX);

	/**
	 * @return the distLabels.y.
	 */
	double getDistLabelsY();

	/**
	 * @param distLabelsY the distLabels.y to set.
	 */
	void setDistLabelsY(final double distLabelsY);

	/**
	 * @return the labelsDisplayed.
	 */
	@NotNull PlottingStyle getLabelsDisplayed();

	/**
	 * @param labelsDisplayed the labelsDisplayed to set.
	 */
	void setLabelsDisplayed(final PlottingStyle labelsDisplayed);

	/**
	 * @return the showOrigin.
	 */
	boolean isShowOrigin();

	/**
	 * @param showOrigin the showOrigin to set.
	 */
	void setShowOrigin(final boolean showOrigin);

	/**
	 * @return the ticksDisplayed.
	 */
	@NotNull PlottingStyle getTicksDisplayed();

	/**
	 * @param ticksDisplayed the ticksDisplayed to set.
	 */
	void setTicksDisplayed(final @NotNull PlottingStyle ticksDisplayed);

	/**
	 * @return the ticksStyle.
	 */
	@NotNull TicksStyle getTicksStyle();

	/**
	 * @param ticksStyle the ticksStyle to set.
	 */
	void setTicksStyle(final @NotNull TicksStyle ticksStyle);

	/**
	 * @return the ticksSize.
	 */
	double getTicksSize();

	/**
	 * @param ticksSize the ticksSize to set.
	 */
	void setTicksSize(final double ticksSize);

	/**
	 * @return the axesStyle.
	 */
	@NotNull AxesStyle getAxesStyle();

	/**
	 * @param axesStyle the axesStyle to set.
	 */
	void setAxesStyle(final @NotNull AxesStyle axesStyle);
}
