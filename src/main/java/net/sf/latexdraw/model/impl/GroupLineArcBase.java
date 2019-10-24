/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
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
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;

/**
 * This trait encapsulates the code of the group related to the support of line arc shapes.
 * @author Arnaud Blouin
 */
interface GroupLineArcBase extends Group {
	/** May return the first free hand shape of the group. */
	default <T extends Shape & LineArcProp> Optional<T> firstLineArc() {
		return (Optional<T>) lineArcShapes().stream().filter(sh -> sh.isTypeOf(LineArcProp.class)).findFirst();
	}

	default <T extends Shape & LineArcProp> List<T> lineArcShapes() {
		return getShapes().stream().filter(sh -> sh instanceof LineArcProp).map(sh -> (T) sh).collect(Collectors.toList());
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
