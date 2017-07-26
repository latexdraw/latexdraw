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
import net.sf.latexdraw.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * This trait encapsulates the code of the group related to the support of free hand shapes.
 * @author Arnaud Blouin
 */
interface LGroupFreeHand extends IGroup {
	/** May return the first free hand shape of the group. */
	default <T extends IShape & IFreeHandProp> Optional<T> firstIFreeHand() {
		return (Optional<T>) fhShapes().stream().filter(sh -> sh.isTypeOf(IFreeHandProp.class)).findFirst();
	}

	default <T extends IShape & IFreeHandProp> List<T> fhShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IFreeHandProp).map(sh -> (T) sh).collect(Collectors.toList());
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
	default boolean isOpen() {
		return firstIFreeHand().map(sh -> sh.isOpen()).orElse(false);
	}

	@Override
	default void setOpen(final boolean open) {
		fhShapes().forEach(sh -> sh.setOpen(open));
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
