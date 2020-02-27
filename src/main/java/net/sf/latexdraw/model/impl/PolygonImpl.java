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
package net.sf.latexdraw.model.impl;

import java.util.List;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polygon;
import org.jetbrains.annotations.NotNull;

/**
 * a model of a polygon.
 * @author Arnaud Blouin
 */
class PolygonImpl extends ModifiablePointsShapeBase implements Polygon {
	/**
	 * Creates a model with a set of points.
	 * @throws IllegalArgumentException If one of the points is null.
	 */
	PolygonImpl(final @NotNull List<Point> pts) {
		super(pts);
	}

	@Override
	public @NotNull Polygon duplicate() {
		final Polygon dup = ShapeFactory.INST.createPolygon(points);
		dup.copy(this);
		return dup;
	}

	@Override
	public boolean isDbleBorderable() {
		return true;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}
}
