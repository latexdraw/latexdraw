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
package net.sf.latexdraw.commands;

import java.util.Optional;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.command.Command;

/**
 * This trait encapsulates a shape attribute.
 * @author Arnaud Blouin
 */
public interface ShapeCmd<T extends IShape> extends Command {
	/**
	 * Sets the shape to add.
	 * @param sh The shape to add.
	 * @since 3.0
	 */
	void setShape(final T sh);

	/**
	 * @return The shape to modify.
	 * @since 3.0
	 */
	Optional<T> getShape();

	@Override
	default boolean canDo() {
		return getShape().isPresent();
	}
}
