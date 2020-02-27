/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.handler;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * A handler for moving control points (for Bézier curves).
 * @author Arnaud BLOUIN
 */
public class CtrlPointHandler extends Ellipse implements Handler {
	/** The control point to move. */
	private final @NotNull Point point;

	/**
	 * Creates the handler.
	 * @param pt The control point to move.
	 */
	public CtrlPointHandler(final @NotNull Point pt) {
		super();
		point = pt;
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
	}

	/**
	 * @return The control point controlled by the handler.
	 */
	public @NotNull Point getPoint() {
		return point;
	}
}
