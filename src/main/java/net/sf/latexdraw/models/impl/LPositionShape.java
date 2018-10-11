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
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * A model of a shape that has a position.
 * @author Arnaud Blouin
 */
abstract class LPositionShape extends LShape implements IPositionShape {
	/**
	 * Creates a LPositionShape with a predefined point.
	 * @param pt The position. If pt is not valid, a point at position (0,0) is used.
	 */
	LPositionShape(final IPoint pt) {
		super();
		points.add(MathUtils.INST.isValidPt(pt) ? ShapeFactory.INST.createPoint(pt) : ShapeFactory.INST.createPoint());
	}

	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IPositionShape) {
			setPosition(((IPositionShape) sh).getPosition());
		}
	}

	@Override
	public void setPosition(final double x, final double y) {
		if(MathUtils.INST.isValidPt(x, y)) {
			final IPoint pos = getPosition();
			translate(x - pos.getX(), y - pos.getY());
		}
	}

	@Override
	public IPoint getPosition() {
		return getBottomLeftPoint();
	}

	@Override
	public void setPosition(final IPoint pt) {
		if(MathUtils.INST.isValidPt(pt)) {
			setPosition(pt.getX(), pt.getY());
		}
	}

	@Override
	public double getX() {
		return getPosition().getX();
	}

	@Override
	public void setX(final double x) {
		if(MathUtils.INST.isValidCoord(x)) {
			translate(x - getPosition().getX(), 0);
		}
	}

	@Override
	public double getY() {
		return getPosition().getY();
	}

	@Override
	public void setY(final double y) {
		if(MathUtils.INST.isValidCoord(y)) {
			translate(0, y - getPosition().getY());
		}
	}
}
