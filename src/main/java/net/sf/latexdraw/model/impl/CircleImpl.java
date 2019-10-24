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

import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of a circle.
 * @author Arnaud Blouin
 */
class CircleImpl extends SquaredShapeBase implements Circle {
	CircleImpl(final Point pos, final double width) {
		super(pos, width);
	}

	@Override
	public @NotNull Point getCenter() {
		return getGravityCentre();
	}

	@Override
	public double getRadius() {
		return getWidth() / 2d;
	}
}
