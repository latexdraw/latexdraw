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
package net.sf.latexdraw.handlers;

import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * A handler for moving control points (for Bézier curves).
 * @author Arnaud BLOUIN
 */
public class CtrlPointHandler extends Ellipse implements Handler {
	/** The control point to move. */
	private IPoint point;

	/**
	 * Creates the handler.
	 * @param pt The control point to move.
	 * @throws NullPointerException If the given point is null.
	 */
	public CtrlPointHandler(final IPoint pt) {
		super();
		point = Objects.requireNonNull(pt);
		setRadiusX(DEFAULT_SIZE / 2d);
		setRadiusY(DEFAULT_SIZE / 2d);
		translateXProperty().bind(Bindings.createDoubleBinding(() -> pt.getX() - DEFAULT_SIZE / 2d, pt.xProperty()));
		translateYProperty().bind(Bindings.createDoubleBinding(() -> pt.getY() - DEFAULT_SIZE / 2d, pt.yProperty()));
		setStroke(null);
		setFill(DEFAULT_COLOR);
	}

	@Override
	public void flush() {
		translateXProperty().unbind();
		translateYProperty().unbind();
		point = null;
	}

	/**
	 * @return The control point controlled by the handler.
	 */
	public IPoint getPoint() {
		return point;
	}
}
