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

import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/**
 * A handler to rotate shapes.
 * @author Arnaud BLOUIN
 */
public class RotationHandler extends Group implements Handler {
	/**
	 * The constructor by default.
	 * @param border The selection border.
	 */
	public RotationHandler(final Rectangle border) {
		super();
		final Arc arc = new Arc();
		arc.setCenterX(DEFAULT_SIZE / 2d);
		arc.setRadiusX(DEFAULT_SIZE / 2d);
		arc.setRadiusY(DEFAULT_SIZE / 2d);
		arc.setType(ArcType.OPEN);
		arc.setLength(270d);
		arc.setStroke(DEFAULT_COLOR);
		arc.setStrokeWidth(2.5d);
		arc.setStrokeLineCap(StrokeLineCap.BUTT);
		arc.setFill(new Color(1d, 1d, 1d, 0d));
		getChildren().add(arc);

		final Path arrows = new Path();
		arrows.setStroke(null);
		arrows.setFill(new Color(0d, 0d, 0d, 0.4));
		arrows.getElements().add(new MoveTo(DEFAULT_SIZE + DEFAULT_SIZE / 4d, 0d));
		arrows.getElements().add(new LineTo(DEFAULT_SIZE, DEFAULT_SIZE / 2d));
		arrows.getElements().add(new LineTo(DEFAULT_SIZE - DEFAULT_SIZE / 4d, 0d));
		arrows.getElements().add(new ClosePath());
		getChildren().add(arrows);

		translateXProperty().bind(Bindings.createDoubleBinding(() -> border.getLayoutX() + border.getWidth(), border.xProperty(),
			border.widthProperty(), border.layoutXProperty()));
		translateYProperty().bind(Bindings.createDoubleBinding(() -> border.getLayoutY() + DEFAULT_SIZE, border.yProperty(),
			border.heightProperty(), border.layoutYProperty()));
	}
}
