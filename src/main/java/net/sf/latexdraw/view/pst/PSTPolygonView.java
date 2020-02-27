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
package net.sf.latexdraw.view.pst;

import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polygon;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a PSTricks view of the LPolygon model.
 * @author Arnaud Blouin
 */
public class PSTPolygonView extends PSTClassicalView<Polygon> {
	/**
	 * Creates and initialises a LRect PSTricks view.
	 * @param model The model to view.
	 */
	protected PSTPolygonView(final @NotNull Polygon model) {
		super(model);
	}


	/**
	 * @param position The reference point of the PSTricks drawing.
	 * @param ppc The number of pixels per centimetre.
	 * @return The PSTricks code of the polygon coordinates.
	 */
	protected @NotNull StringBuilder getPointsCode(final @NotNull Point position, final float ppc) {
		Point p;
		int i;
		final int size = shape.getNbPoints();
		final StringBuilder points = new StringBuilder();

		for(i = 0; i < size; i++) {
			p = shape.getPtAt(i);
			points.append('(').append(MathUtils.INST.getCutNumberFloat((p.getX() - position.getX()) / ppc));
			points.append(',').append(MathUtils.INST.getCutNumberFloat((position.getY() - p.getY()) / ppc)).append(')');
		}

		return points;
	}


	@Override
	public @NotNull String getCode(final @NotNull Point position, final float ppc) {
		return "\\pspolygon[" + getPropertiesCode(ppc) + ']' + getPointsCode(position, ppc); //NON-NLS
	}
}
