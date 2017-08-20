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

import net.sf.latexdraw.models.interfaces.shape.IPolyline;

/**
 * @author Arnaud Blouin
 */
public class ViewPolyline extends ViewPolyPoint<IPolyline> {
	protected final ViewArrowableTrait viewArrows = new ViewArrowableTrait(this);

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyline(final IPolyline sh) {
		super(sh);
		getChildren().add(viewArrows);
		viewArrows.updateArr();
	}
}
