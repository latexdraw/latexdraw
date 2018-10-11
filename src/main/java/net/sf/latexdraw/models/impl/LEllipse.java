/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * An implementation of an ellipse.
 * @author Arnaud Blouin
 */
class LEllipse extends LRectangularShape implements IEllipse {
	/**
	 * Creates an Ellipse.
	 * @since 3.0
	 */
	LEllipse() {
		this(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1.0, 1.0));
	}

	/**
	 * Creates an ellipse.
	 * @param tl The top-left point of the ellipse.
	 * @param br The bottom-right point of the ellipse.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	LEllipse(final IPoint tl, final IPoint br) {
		super(tl, br);
	}

	@Override
	public void setCentre(final IPoint centre) {
		if(MathUtils.INST.isValidPt(centre)) {
			final IPoint gc = getGravityCentre();
			translate(centre.getX() - gc.getX(), centre.getY() - gc.getY());
		}
	}

	@Override
	public IPoint getCenter() {
		return getGravityCentre();
	}


	@Override
	public double getA() {
		final double rx = getWidth() / 2.0;
		final double ry = getHeight() / 2.0;
		return rx < ry ? ry : rx;
	}


	@Override
	public double getB() {
		final double rx = getWidth() / 2.0;
		final double ry = getHeight() / 2.0;
		return rx > ry ? ry : rx;
	}
}
