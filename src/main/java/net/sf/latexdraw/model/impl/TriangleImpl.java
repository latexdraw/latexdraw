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
package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Triangle;

/**
 * A model of a triangle.
 * @author Arnaud Blouin
 */
class TriangleImpl extends RectangularShapeBase implements Triangle {
	/**
	 * Creates a triangle at the position (0,0).
	 */
	TriangleImpl() {
		this(ShapeFactory.INST.createPoint(), 1, 1);
	}

	/**
	 * Creates a triangle.
	 * @param pos The north-west point of the triangle.
	 * @param width The width of the triangle.
	 * @param height The height of the triangle.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	TriangleImpl(final Point pos, final double width, final double height) {
		super(pos, pos == null ? null : ShapeFactory.INST.createPoint(pos.getX() + width, pos.getY() + height));
	}
}

