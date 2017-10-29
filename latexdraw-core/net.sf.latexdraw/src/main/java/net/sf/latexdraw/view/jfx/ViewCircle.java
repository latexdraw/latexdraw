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

import javafx.beans.binding.Bindings;
import net.sf.latexdraw.models.interfaces.shape.ICircle;

/**
 * The JFX shape view for circles.
 * @author Arnaud Blouin
 */
public class ViewCircle extends ViewEllipseBased<ICircle> {
	/**
	 * Creates the circle view.
	 * @param sh The model.
	 */
	ViewCircle(final ICircle sh) {
		super(sh);
		border.centerXProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getX(), model.getPtAt(2).xProperty(), model.getPtAt(3).xProperty()));
		border.centerYProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getY(), model.getPtAt(0).yProperty(), model.getPtAt(3).yProperty()));
		border.radiusXProperty().bind(Bindings.createDoubleBinding(model::getRadius, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.radiusYProperty().bind(Bindings.createDoubleBinding(model::getRadius, model.getPtAt(1).yProperty(), model.getPtAt(2).yProperty()));
	}
}
