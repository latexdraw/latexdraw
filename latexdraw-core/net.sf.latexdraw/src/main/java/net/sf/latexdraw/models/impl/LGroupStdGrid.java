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
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * This trait encapsulates the code of the group related to the support of standard grids.
 * @author Arnaud Blouin
 */
interface LGroupStdGrid extends IGroup {
	/** May return the first stdGrid of the group. */
	default <T extends IShape & IStdGridProp> Optional<T> firstIStdGrid() {
		return (Optional<T>) stdGridShapes().stream().filter(sh -> sh.isTypeOf(IStdGridProp.class)).findFirst();
	}

	default <T extends IShape & IStdGridProp> List<T> stdGridShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IStdGridProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default double getGridMinX() {
		return firstIStdGrid().map(sh -> sh.getGridMinX()).orElse(Double.NaN);
	}

	@Override
	default double getGridMaxX() {
		return firstIStdGrid().map(sh -> sh.getGridMaxX()).orElse(Double.NaN);
	}

	@Override
	default double getGridMinY() {
		return firstIStdGrid().map(sh -> sh.getGridMinY()).orElse(Double.NaN);
	}

	@Override
	default double getGridMaxY() {
		return firstIStdGrid().map(sh -> sh.getGridMaxY()).orElse(Double.NaN);
	}

	@Override
	default int getLabelsSize() {
		return firstIStdGrid().map(sh -> sh.getLabelsSize()).orElse(-1);
	}

	@Override
	default void setLabelsSize(final int labelsSize) {
		stdGridShapes().forEach(sh -> sh.setLabelsSize(labelsSize));
	}

	@Override
	default void setGridEndX(final double x) {
		stdGridShapes().forEach(sh -> sh.setGridEndX(x));
	}

	@Override
	default void setGridEndY(final double y) {
		stdGridShapes().forEach(sh -> sh.setGridEndY(y));
	}

	@Override
	default double getGridStartX() {
		return firstIStdGrid().map(sh -> sh.getGridStartX()).orElse(Double.NaN);
	}

	@Override
	default double getGridStartY() {
		return firstIStdGrid().map(sh -> sh.getGridStartY()).orElse(Double.NaN);
	}

	@Override
	default void setGridStart(final double x, final double y) {
		stdGridShapes().forEach(sh -> sh.setGridStart(x, y));
	}

	@Override
	default double getGridEndX() {
		return firstIStdGrid().map(sh -> sh.getGridEndX()).orElse(Double.NaN);
	}

	@Override
	default double getGridEndY() {
		return firstIStdGrid().map(sh -> sh.getGridEndY()).orElse(Double.NaN);
	}

	@Override
	default void setGridEnd(final double x, final double y) {
		stdGridShapes().forEach(sh -> sh.setGridEnd(x, y));
	}

	@Override
	default double getOriginX() {
		return firstIStdGrid().map(sh -> sh.getOriginX()).orElse(Double.NaN);
	}

	@Override
	default double getOriginY() {
		return firstIStdGrid().map(sh -> sh.getOriginY()).orElse(Double.NaN);
	}

	@Override
	default void setOrigin(final double x, final double y) {
		stdGridShapes().forEach(sh -> sh.setOrigin(x, y));
	}

	@Override
	default void setGridStartY(final double y) {
		stdGridShapes().forEach(sh -> sh.setGridStartY(y));
	}

	@Override
	default void setGridStartX(final double x) {
		stdGridShapes().forEach(sh -> sh.setGridStartX(x));
	}

	@Override
	default void setOriginX(final double x) {
		stdGridShapes().forEach(sh -> sh.setOriginX(x));
	}

	@Override
	default void setOriginY(final double y) {
		stdGridShapes().forEach(sh -> sh.setOriginY(y));
	}

	@Override
	default double getStep() {
		return firstIStdGrid().map(sh -> sh.getStep()).orElse(Double.NaN);
	}

	@Override
	default IPoint getGridStart() {
		return firstIStdGrid().map(sh -> sh.getGridStart()).orElse(null);
	}

	@Override
	default IPoint getGridEnd() {
		return firstIStdGrid().map(sh -> sh.getGridEnd()).orElse(null);
	}
}
