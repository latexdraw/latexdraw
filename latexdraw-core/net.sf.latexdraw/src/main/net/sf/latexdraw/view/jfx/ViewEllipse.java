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
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for ellipses.
 */
public class ViewEllipse extends ViewSingleShape<IEllipse, Ellipse> {
	/**
	 * Creates the ellipse view.
	 * @param sh The model.
	 */
	public ViewEllipse(final @NonNull IEllipse sh) {
		super(sh);

		border.centerXProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getX(), model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.centerYProperty().bind(Bindings.createDoubleBinding(() -> model.getCenter().getY(), model.getPtAt(0).yProperty(), model.getPtAt(1).yProperty()));
		border.radiusXProperty().bind(Bindings.createDoubleBinding(() -> model.getWidth()/2d, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.radiusYProperty().bind(Bindings.createDoubleBinding(() -> model.getHeight()/2d, model.getPtAt(0).yProperty(), model.getPtAt(3).yProperty()));
	}

	@Override
	protected Ellipse createJFXShape() {
		return new Ellipse();
	}

	@Override
	public void flush() {
		border.centerXProperty().unbind();
		border.centerYProperty().unbind();
		border.radiusXProperty().unbind();
		border.radiusYProperty().unbind();
	}
}
