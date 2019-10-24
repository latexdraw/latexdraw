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
package net.sf.latexdraw.command;

import io.github.interacto.command.CommandImpl;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * @author Arnaud Blouin
 * @param <T>
 */
public abstract class ShapeCmdImpl<T extends Shape> extends CommandImpl implements ShapeCmd<T> {
	/** The shape to add. */
	protected T shape;

	protected ShapeCmdImpl(final T sh) {
		super();
		shape = sh;
	}

	@Override
	public void setShape(final @NotNull T sh) {
		shape = sh;
	}

	@Override
	public @NotNull T getShape() {
		return shape;
	}
}
