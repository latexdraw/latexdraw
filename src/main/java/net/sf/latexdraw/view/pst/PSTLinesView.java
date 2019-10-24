/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.pst;

import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polyline;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a PSTricks view of the LLines model.
 * @author Arnaud Blouin
 */
public class PSTLinesView extends PSTPolygonView {
	/**
	 * Creates and initialises a LLines PSTricks view.
	 * @param model The model to view.
	 */
	protected PSTLinesView(final @NotNull Polyline model) {
		super(model);
	}


	@Override
	public @NotNull String getCode(final @NotNull Point position, final float ppc) {
		final StringBuilder points = getPointsCode(position, ppc);
		final StringBuilder arrowsStyle = getArrowsStyleCode();
		final StringBuilder code = new StringBuilder();

		code.append("\\psline["); //NON-NLS
		code.append(getPropertiesCode(ppc));
		code.append(']');
		if(arrowsStyle != null) {
			code.append(arrowsStyle);
		}
		code.append(points);

		return code.toString();
	}
}
