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

import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of an ellipse.
 * @author Arnaud Blouin
 */
class EllipseImpl extends RectangularShapeBase implements Ellipse {
	/**
	 * Creates an Ellipse.
	 */
	EllipseImpl() {
		this(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1.0, 1.0));
	}

	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	EllipseImpl(final Point tl, final Point br) {
		super(tl, br);
	}

	@Override
	public void setCentre(final @NotNull Point centre) {
		if(MathUtils.INST.isValidPt(centre)) {
			final Point gc = getGravityCentre();
			translate(centre.getX() - gc.getX(), centre.getY() - gc.getY());
		}
	}

	@Override
	public @NotNull Point getCenter() {
		return getGravityCentre();
	}


	@Override
	public double getA() {
		final double rx = getWidth() / 2.0;
		final double ry = getHeight() / 2.0;
		return Math.max(rx, ry);
	}


	@Override
	public double getB() {
		final double rx = getWidth() / 2.0;
		final double ry = getHeight() / 2.0;
		return Math.min(rx, ry);
	}
}
