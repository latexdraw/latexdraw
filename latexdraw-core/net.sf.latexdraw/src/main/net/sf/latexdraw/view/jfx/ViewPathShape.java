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
package net.sf.latexdraw.view.jfx;

import javafx.scene.shape.Path;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX view for shapes that can be painted as a path.
 * @param <S> The type of the model.
 * @author Arnaud BLOUIN
 * @since 4.0
 */
public abstract class ViewPathShape<S extends ISingleShape> extends ViewSingleShape<S, Path> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPathShape(final @NonNull S sh) {
		super(sh);
	}

	@Override
	protected @NonNull Path createJFXShape() {
		return new Path();
	}

	@Override
	public void flush() {
		border.getElements().clear();
		super.flush();
	}
}
