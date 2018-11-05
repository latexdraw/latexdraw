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
import net.sf.latexdraw.model.api.property.FreeHandProp;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;

/**
 * This trait encapsulates the code of the group related to the support of free hand shapes.
 * @author Arnaud Blouin
 */
interface GroupFreeHandBase extends Group {
	/** May return the first free hand shape of the group. */
	default <T extends Shape & FreeHandProp> Optional<T> firstIFreeHand() {
		return (Optional<T>) fhShapes().stream().filter(sh -> sh.isTypeOf(FreeHandProp.class)).findFirst();
	}

	default <T extends Shape & FreeHandProp> List<T> fhShapes() {
		return getShapes().stream().filter(sh -> sh instanceof FreeHandProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default FreeHandStyle getType() {
		return firstIFreeHand().map(sh -> sh.getType()).orElse(FreeHandStyle.CURVES);
	}

	@Override
	default void setType(final FreeHandStyle fhType) {
		fhShapes().forEach(sh -> sh.setType(fhType));
	}

	@Override
	default int getInterval() {
		return firstIFreeHand().map(sh -> sh.getInterval()).orElse(0);
	}

	@Override
	default void setInterval(final int interval) {
		fhShapes().forEach(sh -> sh.setInterval(interval));
	}
}
