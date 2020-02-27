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
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import net.sf.latexdraw.model.api.shape.Rectangle;

/**
 * The JFX shape view for rectangles.
 * @author Arnaud Blouin
 */
public class ViewRectangle extends ViewRectangularBased<Rectangle> {
	final ChangeListener<Bounds> lineArcUp = (observable, oldValue, newValue) -> lineArcCall.changed(model.frameArcProperty(), model.getLineArc(), model.getLineArc());


	/**
	 * Creates the rectangle view.
	 * @param sh The model.
	 */
	ViewRectangle(final Rectangle sh) {
		super(sh);
		border.xProperty().bind(model.getPtAt(0).xProperty());
		border.yProperty().bind(model.getPtAt(0).yProperty());
		border.widthProperty().bind(Bindings.createDoubleBinding(model::getWidth, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.heightProperty().bind(Bindings.createDoubleBinding(model::getHeight, model.getPtAt(0).yProperty(), model.getPtAt(3).yProperty()));
		model.frameArcProperty().addListener(lineArcCall);
		border.boundsInLocalProperty().addListener(lineArcUp);
		lineArcCall.changed(model.frameArcProperty(), model.getLineArc(), model.getLineArc());
	}

	@Override
	public void flush() {
		super.flush();
		model.frameArcProperty().removeListener(lineArcCall);
		border.boundsInLocalProperty().removeListener(lineArcUp);
	}
}
