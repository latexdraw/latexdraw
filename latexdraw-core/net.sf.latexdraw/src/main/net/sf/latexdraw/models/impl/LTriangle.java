/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;

/**
 * A model of a triangle.
 */
class LTriangle extends LRectangularShape implements ITriangle {
	/**
	 * Creates a triangle at the position (0,0).
	 */
	LTriangle() {
		this(ShapeFactory.INST.createPoint(), 1, 1);
	}

	/**
	 * Creates a triangle.
	 * @param pos The north-west point of the triangle.
	 * @param width The width of the triangle.
	 * @param height The height of the triangle.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	LTriangle(final IPoint pos, final double width, final double height) {
		super(pos, pos == null ? null : ShapeFactory.INST.createPoint(pos.getX() + width, pos.getY() + height));
	}
}

