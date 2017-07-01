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

import javafx.beans.value.ChangeListener;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;

/**
 * @author Arnaud Blouin
 */
public class ViewPolyline extends ViewPolyPoint<IPolyline> {
	private final ViewArrowableTrait viewArrows = new ViewArrowableTrait(model);
	private final ChangeListener<Number> updateArrow1 = (observable, oldValue, newValue) -> viewArrows.update(true, 0);
	private final ChangeListener<Number> updateArrow2 = (observable, oldValue, newValue) -> viewArrows.update(true, 1);

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyline(final IPolyline sh) {
		super(sh);

		getChildren().add(viewArrows);
		viewArrows.update(true);

		// TODO: to optimise: the arrows may be re-computed four times instead of a single one (on a shape move for example).
		model.getPtAt(0).xProperty().addListener(updateArrow1);
		model.getPtAt(0).yProperty().addListener(updateArrow1);
		model.getPtAt(-1).xProperty().addListener(updateArrow2);
		model.getPtAt(-1).yProperty().addListener(updateArrow2);
	}

	@Override
	public void flush() {
		model.getPtAt(0).xProperty().removeListener(updateArrow1);
		model.getPtAt(0).yProperty().removeListener(updateArrow1);
		model.getPtAt(-1).xProperty().removeListener(updateArrow2);
		model.getPtAt(-1).yProperty().removeListener(updateArrow2);
		super.flush();
	}
}
