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
package net.sf.latexdraw.command.shape;

import io.github.interacto.command.library.ModifyValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This command modifies a shape property of an object.
 * @author Arnaud Blouin
 */
public abstract class ShapePropertyCmd<T> extends ModifyValue<T> {
	/** The property to set. */
	protected final @NotNull ShapeProperties<T> property;

	/**
	 * Creates and initialises the command.
	 */
	protected ShapePropertyCmd(final @NotNull ShapeProperties<T> property, final @Nullable T value) {
		super(value);
		this.property = property;
	}

	@Override
	protected boolean isValueMatchesProperty() {
		return isPropertySupported();
	}

	/**
	 * @return True if the property to modify is supported.
	 */
	protected boolean isPropertySupported() {
		return true;
	}
}
