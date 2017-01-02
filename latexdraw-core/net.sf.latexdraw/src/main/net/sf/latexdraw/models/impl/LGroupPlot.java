/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;

interface LPlotGroup extends IGroup {
	default <T extends IShape & IPlotProp> Optional<T> firstPlot() {
		return (Optional<T>) plotShapes().stream().filter(sh -> sh.isTypeOf(IPlotProp.class)).findFirst();
	}

	default <T extends IShape & IPlotProp> List<T> plotShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IPlotProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default double getYScale() {
		return firstPlot().map(sh -> sh.getYScale()).orElse(Double.NaN);
	}

	@Override
	default void setScale(final double s) {
		plotShapes().forEach(sh -> sh.setScale(s));
	}

	@Override
	default void setYScale(final double sy) {
		plotShapes().forEach(sh -> sh.setYScale(sy));
	}

	@Override
	default double getXScale() {
		return firstPlot().map(sh -> sh.getXScale()).orElse(Double.NaN);
	}

	@Override
	default void setXScale(final double sx) {
		plotShapes().forEach(sh -> sh.setXScale(sx));
	}

	@Override
	default boolean isPolar() {
		return firstPlot().map(sh -> sh.isPolar()).orElse(false);
	}

	@Override
	default void setPolar(final boolean polar) {
		plotShapes().forEach(sh -> sh.setPolar(polar));
	}

	@Override
	default PlotStyle getPlotStyle() {
		return firstPlot().map(sh -> sh.getPlotStyle()).orElse(PlotStyle.CURVE);
	}

	@Override
	default void setPlotStyle(final PlotStyle style) {
		plotShapes().forEach(sh -> sh.setPlotStyle(style));
	}

	@Override
	default String getPlotEquation() {
		return firstPlot().map(sh -> sh.getPlotEquation()).orElse("");
	}

	@Override
	default void setPlotEquation(final String eq) {
		plotShapes().forEach(sh -> sh.setPlotEquation(eq));
	}

	@Override
	default int getNbPlottedPoints() {
		return firstPlot().map(sh -> sh.getNbPlottedPoints()).orElse(0);
	}

	@Override
	default void setNbPlottedPoints(final int nb) {
		plotShapes().forEach(sh -> sh.setNbPlottedPoints(nb));
	}

	@Override
	default double getPlotMinX() {
		return firstPlot().map(sh -> sh.getPlotMinX()).orElse(Double.NaN);
	}

	@Override
	default void setPlotMinX(final double minX) {
		plotShapes().forEach(sh -> sh.setPlotMinX(minX));
	}

	@Override
	default double getPlotMaxX() {
		return firstPlot().map(sh -> sh.getPlotMaxX()).orElse(Double.NaN);
	}

	@Override
	default void setPlotMaxX(final double maxX) {
		plotShapes().forEach(sh -> sh.setPlotMaxX(maxX));
	}

	@Override
	default double getPlottingStep() {
		return firstPlot().map(sh -> sh.getPlottingStep()).orElse(0d);
	}
}
