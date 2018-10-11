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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;

/**
 * @author Arnaud Blouin
 */
public class ViewRhombus extends ViewPathShape<IRhombus> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewRhombus(final IRhombus sh) {
		super(sh);
		setupPath(border);
		setupPath(shadow);
		setupPath(dblBorder);
		rotateProperty().bind(Bindings.createDoubleBinding(() -> Math.toDegrees(model.getRotationAngle()), model.rotationAngleProperty()));
	}


	private void setupPath(final Path path) {
		final MoveTo moveTo = ViewFactory.INSTANCE.createMoveTo(0d, 0d);
		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(0).getX() + model.getWidth() / 2d,
			model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		moveTo.yProperty().bind(model.getPtAt(0).yProperty());
		path.getElements().add(moveTo);

		LineTo lineTo = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo.xProperty().bind(model.getPtAt(2).xProperty());
		lineTo.yProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(1).getY() + model.getHeight() / 2d,
			model.getPtAt(1).yProperty(), model.getPtAt(2).yProperty()));
		path.getElements().add(lineTo);

		lineTo = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo.xProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(0).getX() + model.getWidth() / 2d,
			model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		lineTo.yProperty().bind(model.getPtAt(2).yProperty());
		path.getElements().add(lineTo);

		lineTo = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo.xProperty().bind(model.getPtAt(0).xProperty());
		lineTo.yProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(0).getY() + model.getHeight() / 2d,
			model.getPtAt(0).yProperty(), model.getPtAt(2).yProperty()));
		path.getElements().add(lineTo);

		path.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}
}
