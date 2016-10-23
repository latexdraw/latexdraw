/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2015 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.glib.views;

import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import java.util.Objects;


/**
 * Defines an abstract view.
 * @param <S> The type of the observed model.
 */
public abstract class AbstractView<S extends IShape> {
	/** The shape model. */
	protected final S shape;


	/**
	 * Creates an abstract view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is null.
	 * @since 3.0
	 */
	protected AbstractView(final S model) {
		super();
		shape = Objects.requireNonNull(model);
	}


	public S getShape() {
		return shape;
	}
}
