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
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;

/**
 * The JFX view for positionable shapes.
 * @param <T> The type of the shape to view.
 * @author Arnaud Blouin
 */
public abstract class ViewPositionShape<T extends IPositionShape> extends ViewShape<T> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPositionShape(final T sh) {
		super(sh);
		translateXProperty().bind(model.getPosition().xProperty());
		translateYProperty().bind(model.getPosition().yProperty());
		rotateProperty().bind(Bindings.createDoubleBinding(() -> Math.toDegrees(model.getRotationAngle()), model.rotationAngleProperty()));
	}

	@Override
	public void flush() {
		translateXProperty().unbind();
		translateYProperty().unbind();
		super.flush();
	}
}
