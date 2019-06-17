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
package net.sf.latexdraw.handler;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * A handler that moves a point of a shape.
 * @author Arnaud BLOUIN
 */
public class MovePtHandler extends Rectangle implements Handler {
	/** The point moved by the handler. */
	private final @NotNull Point point;

	/**
	 * The constructor by default.
	 */
	public MovePtHandler(final @NotNull Point pt) {
		super();
		point = pt;
		setWidth(DEFAULT_SIZE);
		setHeight(DEFAULT_SIZE);
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
	 * @return The point controlled by the handler.
	 */
	public @NotNull Point getPoint() {
		return point;
	}
}
