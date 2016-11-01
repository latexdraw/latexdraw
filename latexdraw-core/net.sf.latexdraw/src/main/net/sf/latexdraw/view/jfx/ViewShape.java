/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.view.jfx;

import javafx.scene.Group;
import javafx.scene.Node;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The base class of a JFX shape view.
 * @param <S> The type of the model.
 * @param <T> The type of the JFX shape used to draw the view.
 */
public abstract class ViewShape<S extends IShape, T extends Node> extends Group {
	/** The model of the view. */
	protected final @NonNull S model;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	protected ViewShape(final @NonNull S sh) {
		super();
		model = sh;

		setFocusTraversable(false);
	}

	/**
	 * Flushes the view.
	 */
	public void flush() {
		getChildren().clear();
		// Should be overridden to flush the bindings.
	}

	public @NonNull S getModel() {
		return model;
	}
}
