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
package net.sf.latexdraw.models.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.latex.DviPsColors;

/**
 * This trait encapsulates the code of the group related to the support of grids.
 * @author Arnaud Blouin
 */
interface LGroupGrid extends IGroup {
	/** May return the first grid of the group. */
	default <T extends IShape & IGridProp> Optional<T> firstIGrid() {
		return (Optional<T>) gridShapes().stream().filter(sh -> sh.isTypeOf(IGridProp.class)).findFirst();
	}

	default <T extends IShape & IGridProp> List<T> gridShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IGridProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default boolean isXLabelSouth() {
		return firstIGrid().map(sh -> sh.isXLabelSouth()).orElse(Boolean.FALSE);
	}

	@Override
	default void setXLabelSouth(final boolean isXLabelSouth) {
		gridShapes().forEach(sh -> sh.setXLabelSouth(isXLabelSouth));
	}

	@Override
	default boolean isYLabelWest() {
		return firstIGrid().map(sh -> sh.isYLabelWest()).orElse(Boolean.FALSE);
	}

	@Override
	default void setYLabelWest(final boolean isYLabelWest) {
		gridShapes().forEach(sh -> sh.setYLabelWest(isYLabelWest));
	}

	@Override
	default int getGridDots() {
		return firstIGrid().map(sh -> sh.getGridDots()).orElse(0);
	}

	@Override
	default void setGridDots(final int gridDots) {
		gridShapes().forEach(sh -> sh.setGridDots(gridDots));
	}

	@Override
	default Color getGridLabelsColour() {
		return firstIGrid().map(sh -> sh.getGridLabelsColour()).orElse(DviPsColors.BLACK);
	}

	@Override
	default void setGridLabelsColour(final Color gridLabelsColour) {
		gridShapes().forEach(sh -> sh.setGridLabelsColour(gridLabelsColour));
	}

	@Override
	default double getGridWidth() {
		return firstIGrid().map(sh -> sh.getGridWidth()).orElse(Double.NaN);
	}

	@Override
	default void setGridWidth(final double gridWidth) {
		gridShapes().forEach(sh -> sh.setGridWidth(gridWidth));
	}

	@Override
	default Color getSubGridColour() {
		return firstIGrid().map(sh -> sh.getSubGridColour()).orElse(DviPsColors.BLACK);
	}

	@Override
	default void setSubGridColour(final Color subGridColour) {
		gridShapes().forEach(sh -> sh.setSubGridColour(subGridColour));
	}

	@Override
	default int getSubGridDiv() {
		return firstIGrid().map(sh -> sh.getSubGridDiv()).orElse(0);
	}

	@Override
	default void setSubGridDiv(final int subGridDiv) {
		gridShapes().forEach(sh -> sh.setSubGridDiv(subGridDiv));
	}

	@Override
	default int getSubGridDots() {
		return firstIGrid().map(sh -> sh.getSubGridDots()).orElse(0);
	}

	@Override
	default void setSubGridDots(final int subGridDots) {
		gridShapes().forEach(sh -> sh.setSubGridDots(subGridDots));
	}

	@Override
	default double getSubGridWidth() {
		return firstIGrid().map(sh -> sh.getSubGridWidth()).orElse(Double.NaN);
	}

	@Override
	default void setSubGridWidth(final double subGridWidth) {
		gridShapes().forEach(sh -> sh.setSubGridWidth(subGridWidth));
	}

	@Override
	default void setUnit(final double unit) {
		gridShapes().forEach(sh -> sh.setUnit(unit));
	}

	@Override
	default double getUnit() {
		return firstIGrid().map(sh -> sh.getUnit()).orElse(Double.NaN);
	}
}
