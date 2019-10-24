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
import net.sf.latexdraw.model.api.property.SetShapesProp;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This trait implements the ISetShapes interface.
 * To turn as a full Java trait as soon as Java will support private attributes in interfaces.
 * @author Arnaud Blouin
 */
interface SetShapesBase extends SetShapesProp {
	@Override
	default boolean contains(final @NotNull Shape sh) {
		return getShapes().contains(sh);
	}

	@Override
	default void addShape(final @NotNull Shape sh) {
		if(!(sh instanceof SetShapesProp) || !((SetShapesProp) sh).isEmpty()) {
			getShapes().add(sh);
		}
	}

	@Override
	default void addShape(final @NotNull Shape sh, final int index) {
		final List<Shape> shapes = getShapes();
		if(index <= shapes.size() && (index == -1 || index >= 0) && (!(sh instanceof SetShapesProp) || !((SetShapesProp) sh).isEmpty())) {
			if(index == -1 || index == shapes.size()) {
				shapes.add(sh);
			}else {
				shapes.add(index, sh);
			}
		}
	}

	@Override
	default void clear() {
		final List<Shape> shapes = getShapes();
		if(!shapes.isEmpty()) {
			shapes.clear();
		}
	}

	@Override
	default @NotNull Optional<Shape> getShapeAt(final int i) {
		final List<Shape> shapes = getShapes();
		if(i < -1 || i >= shapes.size()) {
			return Optional.empty();
		}
		if(i == -1) {
			return Optional.ofNullable(shapes.get(shapes.size() - 1));
		}
		return Optional.ofNullable(shapes.get(i));
	}


	@Override
	default boolean isEmpty() {
		return getShapes().isEmpty();
	}


	@Override
	default boolean removeShape(final @NotNull Shape sh) {
		return getShapes().remove(sh);
	}


	@Override
	default @NotNull Optional<Shape> removeShape(final int i) {
		final List<Shape> shapes = getShapes();
		if(i < -1 || shapes.isEmpty() || i >= shapes.size()) {
			return Optional.empty();
		}
		if(i == -1) {
			return Optional.ofNullable(shapes.remove(shapes.size() - 1));
		}
		return Optional.ofNullable(shapes.remove(i));
	}


	@Override
	default int size() {
		return getShapes().size();
	}
}
