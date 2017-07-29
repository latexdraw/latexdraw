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
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import net.sf.latexdraw.models.interfaces.shape.ISquare;

/**
 * The JFX shape view for squares.
 * @author Arnaud Blouin
 */
public class ViewSquare extends ViewRectangularBased<ISquare> {
	final  ChangeListener<Bounds> lineArcUp = (observable, oldValue, newValue) -> lineArcCall.changed(model.frameArcProperty(), model.getLineArc(), model.getLineArc());

	/**
	 * Creates the square view.
	 * @param sh The model.
	 */
	ViewSquare(final  ISquare sh) {
		super(sh);
		border.xProperty().bind(model.getPtAt(0).xProperty());
		border.yProperty().bind(model.getPtAt(0).yProperty());
		border.widthProperty().bind(Bindings.createDoubleBinding(model::getWidth, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.heightProperty().bind(Bindings.createDoubleBinding(model::getWidth, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
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
