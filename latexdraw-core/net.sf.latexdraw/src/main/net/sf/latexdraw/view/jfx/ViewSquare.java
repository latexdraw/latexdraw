/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for squares.
 */
public class ViewSquare extends ViewRectangularBased<ISquare> {
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
}
