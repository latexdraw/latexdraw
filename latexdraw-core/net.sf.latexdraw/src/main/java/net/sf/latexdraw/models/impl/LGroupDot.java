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
package net.sf.latexdraw.models.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.latex.DviPsColors;

/**
 * This trait encapsulates the code of the group related to the support of dot shapes.
 * @author Arnaud Blouin
 */
interface LGroupDot extends IGroup {
	/** May return the first grid of the group. */
	default <T extends IShape & IDotProp> Optional<T> firstDottable() {
		return (Optional<T>) dotShapes().stream().filter(sh -> sh.isTypeOf(IDotProp.class)).findFirst();
	}

	default <T extends IShape & IDotProp> List<T> dotShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IDotProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default Color getDotFillingCol() {
		return firstDottable().map(sh -> sh.getDotFillingCol()).orElse(DviPsColors.BLACK);
	}

	@Override
	default void setDotFillingCol(final Color fillingCol) {
		dotShapes().forEach(sh -> sh.setDotFillingCol(fillingCol));
	}

	@Override
	default DotStyle getDotStyle() {
		return firstDottable().map(sh -> sh.getDotStyle()).orElse(DotStyle.DOT);
	}

	@Override
	default void setDotStyle(final DotStyle style) {
		dotShapes().forEach(sh -> sh.setDotStyle(style));
	}

	@Override
	default double getDiametre() {
		return firstDottable().map(sh -> sh.getDiametre()).orElse(Double.NaN);
	}

	@Override
	default void setDiametre(final double dia) {
		dotShapes().forEach(sh -> sh.setDiametre(dia));
	}
}
