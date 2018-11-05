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
package net.sf.latexdraw.command;

import java.util.Optional;
import net.sf.latexdraw.model.api.shape.Shape;
import org.malai.command.CommandImpl;

/**
 * @author Arnaud Blouin
 * @param <T>
 */
public abstract class ShapeCmdImpl<T extends Shape> extends CommandImpl implements ShapeCmd<T> {
	/** The shape to add. */
	protected Optional<T> shape;

	protected ShapeCmdImpl(final T sh) {
		super();
		shape = Optional.ofNullable(sh);
	}

	@Override
	public void setShape(final T sh) {
		shape = Optional.ofNullable(sh);
	}

	@Override
	public Optional<T> getShape() {
		return shape;
	}

	@Override
	public boolean canDo() {
		return shape.isPresent();
	}
}
