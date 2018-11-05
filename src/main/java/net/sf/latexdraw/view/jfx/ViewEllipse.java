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
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import net.sf.latexdraw.model.api.shape.Ellipse;

/**
 * The JFX shape view for ellipses.
 * @author Arnaud Blouin
 */
public class ViewEllipse extends ViewEllipseBased<Ellipse> {
	/**
	 * Creates the ellipse view.
	 * @param sh The model.
	 */
	ViewEllipse(final Ellipse sh) {
		super(sh);
		border.centerXProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getX(), model.getPtAt(2).xProperty(), model.getPtAt(3).xProperty()));
		border.centerYProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getY(), model.getPtAt(0).yProperty(), model.getPtAt(3).yProperty()));
		border.radiusXProperty().bind(Bindings.createDoubleBinding(() -> model.getWidth() / 2d, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.radiusYProperty().bind(Bindings.createDoubleBinding(() -> model.getHeight() / 2d, model.getPtAt(1).yProperty(), model.getPtAt(2).yProperty()));
	}
}
