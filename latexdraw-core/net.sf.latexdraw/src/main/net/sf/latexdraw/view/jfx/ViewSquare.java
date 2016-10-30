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

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for squares.
 */
public class ViewSquare extends ViewSingleShape<ISquare, Rectangle> {
	/**
	 * Creates the square view.
	 * @param sh The model.
	 */
	public ViewSquare(final @NonNull ISquare sh) {
		super(sh);

		border.xProperty().bind(model.getPtAt(0).xProperty());
		border.yProperty().bind(model.getPtAt(0).yProperty());
		border.widthProperty().bind(Bindings.createDoubleBinding(model::getWidth, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.heightProperty().bind(Bindings.createDoubleBinding(model::getWidth, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
	}

	@Override
	protected Rectangle createJFXShape() {
		return new Rectangle();
	}

	@Override
	public void flush() {
		border.xProperty().unbind();
		border.yProperty().unbind();
		border.widthProperty().unbind();
		border.heightProperty().unbind();
	}
}
