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
import net.sf.latexdraw.model.api.property.ClosableProp;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;

/**
 * This trait encapsulates the code of the group related to the support of the closable shape's properties.
 * @author Arnaud Blouin
 */
public interface GroupClosableBase extends Group {
	/** May return the first closable shape of the group. */
	default <T extends Shape & ClosableProp> Optional<T> firstClosable() {
		return (Optional<T>) closableShapes().stream().filter(sh -> sh.isTypeOf(ClosableProp.class)).findFirst();
	}

	default <T extends Shape & ClosableProp> List<T> closableShapes() {
		return getShapes().stream().filter(sh -> sh instanceof ClosableProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default boolean isOpened() {
		return firstClosable().map(sh -> sh.isOpened()).orElse(Boolean.FALSE);
	}

	@Override
	default void setOpened(final boolean closed) {
		closableShapes().forEach(sh -> sh.setOpened(closed));
	}
}
