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
package net.sf.latexdraw.model.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.latexdraw.model.api.property.AxesProp;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TicksStyle;

/**
 * This trait encapsulates the code of the group related to the support of axes.
 * @author Arnaud Blouin
 */
interface GroupAxesBase extends Group {
	/** May return the first axes shape of the group. */
	default <T extends Shape & AxesProp> Optional<T> firstIAxes() {
		return (Optional<T>) axesShapes().stream().filter(sh -> sh.isTypeOf(AxesProp.class)).findFirst();
	}

	default <T extends Shape & AxesProp> List<T> axesShapes() {
		return getShapes().stream().filter(sh -> sh instanceof AxesProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default double getIncrementX() {
		return firstIAxes().map(sh -> sh.getIncrementX()).orElse(Double.NaN);
	}

	@Override
	default double getIncrementY() {
		return firstIAxes().map(sh -> sh.getIncrementY()).orElse(Double.NaN);
	}

	@Override
	default void setIncrementX(final double increment) {
		axesShapes().forEach(sh -> sh.setIncrementX(increment));
	}

	@Override
	default void setIncrementY(final double increment) {
		axesShapes().forEach(sh -> sh.setIncrementY(increment));
	}

	@Override
	default double getDistLabelsX() {
		return firstIAxes().map(sh -> sh.getDistLabelsX()).orElse(Double.NaN);
	}

	@Override
	default double getDistLabelsY() {
		return firstIAxes().map(sh -> sh.getDistLabelsY()).orElse(Double.NaN);
	}

	@Override
	default void setDistLabelsX(final double distLabelsX) {
		axesShapes().forEach(sh -> sh.setDistLabelsX(distLabelsX));
	}

	@Override
	default void setDistLabelsY(final double distLabelsY) {
		axesShapes().forEach(sh -> sh.setDistLabelsY(distLabelsY));
	}

	@Override
	default PlottingStyle getLabelsDisplayed() {
		return firstIAxes().map(sh -> sh.getLabelsDisplayed()).orElse(PlottingStyle.ALL);
	}

	@Override
	default void setLabelsDisplayed(final PlottingStyle labelsDisplayed) {
		axesShapes().forEach(sh -> sh.setLabelsDisplayed(labelsDisplayed));
	}

	@Override
	default boolean isShowOrigin() {
		return firstIAxes().map(sh -> sh.isShowOrigin()).orElse(Boolean.FALSE);
	}

	@Override
	default void setShowOrigin(final boolean showOrigin) {
		axesShapes().forEach(sh -> sh.setShowOrigin(showOrigin));
	}

	@Override
	default PlottingStyle getTicksDisplayed() {
		return firstIAxes().map(sh -> sh.getTicksDisplayed()).orElse(PlottingStyle.ALL);
	}

	@Override
	default void setTicksDisplayed(final PlottingStyle ticksDisplayed) {
		axesShapes().forEach(sh -> sh.setTicksDisplayed(ticksDisplayed));
	}

	@Override
	default TicksStyle getTicksStyle() {
		return firstIAxes().map(sh -> sh.getTicksStyle()).orElse(TicksStyle.FULL);
	}

	@Override
	default void setTicksStyle(final TicksStyle ticksStyle) {
		axesShapes().forEach(sh -> sh.setTicksStyle(ticksStyle));
	}

	@Override
	default double getTicksSize() {
		return firstIAxes().map(sh -> sh.getTicksSize()).orElse(Double.NaN);
	}

	@Override
	default void setTicksSize(final double ticksSize) {
		axesShapes().forEach(sh -> sh.setTicksSize(ticksSize));
	}

	@Override
	default AxesStyle getAxesStyle() {
		return firstIAxes().map(sh -> sh.getAxesStyle()).orElse(AxesStyle.AXES);
	}

	@Override
	default void setAxesStyle(final AxesStyle axesStyle) {
		axesShapes().forEach(sh -> sh.setAxesStyle(axesStyle));
	}

	@Override
	default Point getIncrement() {
		return firstIAxes().map(sh -> sh.getIncrement()).orElse(null);
	}

	@Override
	default void setIncrement(final Point increment) {
		axesShapes().forEach(sh -> sh.setIncrement(increment));
	}

	@Override
	default Point getDistLabels() {
		return firstIAxes().map(sh -> sh.getDistLabels()).orElse(null);
	}

	@Override
	default void setDistLabels(final Point distLabels) {
		axesShapes().forEach(sh -> sh.setDistLabels(distLabels));
	}
}
