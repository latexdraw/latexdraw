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
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * This trait encapsulates the code of the group related to the support of line arc shapes.
 * @author Arnaud Blouin
 */
interface LGroupLineArc extends IGroup {
	/** May return the first free hand shape of the group. */
	default <T extends IShape & ILineArcProp> Optional<T> firstLineArc() {
		return (Optional<T>) lineArcShapes().stream().filter(sh -> sh.isTypeOf(ILineArcProp.class)).findFirst();
	}

	default <T extends IShape & ILineArcProp> List<T> lineArcShapes() {
		return getShapes().stream().filter(sh -> sh instanceof ILineArcProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default double getLineArc() {
		return firstLineArc().map(sh -> sh.getLineArc()).orElse(Double.NaN);
	}

	@Override
	default void setLineArc(final double lineArc) {
		lineArcShapes().forEach(sh -> sh.setLineArc(lineArc));
	}

	@Override
	default boolean isRoundCorner() {
		return firstLineArc().map(sh -> sh.isRoundCorner()).orElse(Boolean.FALSE);
	}
}
