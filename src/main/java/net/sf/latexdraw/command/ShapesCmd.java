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

import io.github.interacto.command.Command;
import java.util.List;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A command that handles a set of shapes attribute.
 * @author Arnaud Blouin
 */
public interface ShapesCmd extends Command {
	/**
	 * Sets the shape to handle.
	 * @param shape The shape to handle. Can be null.
	 */
	default void setShape(final @Nullable Shape shape) {
		getShapes().clear();

		if(shape != null) {
			getShapes().add(shape);
		}
	}

	/**
	 * Add a shape to the list of shapes to handle.
	 * @param shape The shape to handle.
	 */
	default void addShape(final @Nullable Shape shape) {
		if(shape != null) {
			getShapes().add(shape);
		}
	}

	/**
	 * @return The shapes to handle.
	 */
	@NotNull List<Shape> getShapes();
}
