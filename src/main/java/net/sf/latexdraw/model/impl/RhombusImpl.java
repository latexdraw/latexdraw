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

import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Rhombus;

/**
 * A model of a rhombus.
 * @author Arnaud Blouin
 */
class RhombusImpl extends RectangularShapeBase implements Rhombus {
	/**
	 * Creates a rhombus at the bottom-left position (0,0) with width=height=1.
	 */
	RhombusImpl() {
		this(ShapeFactory.INST.createPoint(0.5, -0.5), 1d, 1d);
	}

	/**
	 * Creates a rhombus.
	 * @param centre The centre of the rhombus.
	 * @param width The width of the rhombus.
	 * @param height The height of the rhombus.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 * @throws NullPointerException If the given point is null.
	 */
	RhombusImpl(final Point centre, final double width, final double height) {
		super(ShapeFactory.INST.createPoint(centre.getX() - width / 2d, centre.getY() - height / 2d),
			ShapeFactory.INST.createPoint(centre.getX() + width / 2d, centre.getY() + height / 2d));
	}
}
