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
package net.sf.latexdraw.actions;

import java.util.Optional;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.action.ActionImpl;

/**
 * @author Arnaud Blouin
 * @param <T>
 */
public abstract class ShapeActionImpl<T extends IShape> extends ActionImpl implements ShapeAction<T> {
	/** The shape to add. */
	protected Optional<T> shape;

	protected ShapeActionImpl(final T sh) {
		super();
		shape = Optional.ofNullable(sh);
	}

	protected ShapeActionImpl() {
		this(null);
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
