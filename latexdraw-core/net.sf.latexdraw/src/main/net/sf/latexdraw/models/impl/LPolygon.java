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
package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;

/**
 * a model of a polygon.
 * @author Arnaud Blouin
 */
class LPolygon extends LModifiablePointsShape implements IPolygon {
	/**
	 * Creates a model with no point.
	 */
	LPolygon() {
		super();
	}

	/**
	 * Creates a model with two points.
	 * @throws IllegalArgumentException If one of the two points is null.
	 */
	LPolygon(final IPoint point, final IPoint point2) {
		this();

		if(point == null || point2 == null) throw new IllegalArgumentException();

		addPoint(point);
		addPoint(point2);
	}

	@Override
	public boolean isDbleBorderable() {
		return true;
	}

	@Override
	public boolean isFillable() {
		return true;
	}

	@Override
	public boolean isInteriorStylable() {
		return true;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}

	@Override
	public boolean isShadowable() {
		return true;
	}

	@Override
	public boolean isThicknessable() {
		return true;
	}
}
