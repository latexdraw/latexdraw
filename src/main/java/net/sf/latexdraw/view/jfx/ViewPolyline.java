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

import net.sf.latexdraw.model.api.shape.Polyline;

/**
 * @author Arnaud Blouin
 */
public class ViewPolyline extends ViewPolyPoint<Polyline> {
	protected final ViewArrowableTraitPath<Polyline> viewArrows;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyline(final Polyline sh, final PathElementProducer pathProducer) {
		super(sh, pathProducer);
		viewArrows = new ViewArrowableTraitPath<>(this, pathProducer);
		getChildren().add(viewArrows);
		viewArrows.updateAllArrows();
	}
}
