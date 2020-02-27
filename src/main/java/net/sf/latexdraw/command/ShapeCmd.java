/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command;

import io.github.interacto.command.Command;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This trait encapsulates a shape attribute.
 * @author Arnaud Blouin
 */
public interface ShapeCmd<T extends Shape> extends Command {
	/**
	 * Sets the shape to add.
	 * @param sh The shape to add.
	 */
	void setShape(final @NotNull T sh);

	/**
	 * @return The shape to modify.
	 */
	@NotNull T getShape();
}
