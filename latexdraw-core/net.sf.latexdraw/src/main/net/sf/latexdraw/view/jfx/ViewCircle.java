/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2015 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for circles.
 */
public class ViewCircle extends ViewEllipseBased<ICircle> {
	/**
	 * Creates the circle view.
	 * @param sh The model.
	 */
	public ViewCircle(final @NonNull ICircle sh) {
		super(sh);
		border.centerXProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getX(), model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.centerYProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getY(), model.getPtAt(0).yProperty(), model.getPtAt(1).yProperty()));
		border.radiusXProperty().bind(Bindings.createDoubleBinding(model::getRadius, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.radiusYProperty().bind(Bindings.createDoubleBinding(model::getRadius, model.getPtAt(0).yProperty(), model.getPtAt(1).yProperty()));
	}
}
