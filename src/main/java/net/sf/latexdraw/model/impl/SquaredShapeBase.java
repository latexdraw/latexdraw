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

import java.awt.geom.Rectangle2D;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Position;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.SquaredShape;
import org.jetbrains.annotations.NotNull;

/**
 * A model of a squared shape.
 * @author Arnaud Blouin
 */
abstract class SquaredShapeBase extends PositionShapeBase implements SquaredShape {
	SquaredShapeBase(final Point tl, final double width) {
		super(tl);

		if(width <= 0 || !MathUtils.INST.isValidPt(tl) || !MathUtils.INST.isValidCoord(width)) {
			throw new IllegalArgumentException();
		}

		points.add(ShapeFactory.INST.createPoint(tl));
		points.add(ShapeFactory.INST.createPoint(tl));
		points.add(ShapeFactory.INST.createPoint(tl));
		setWidth(width);
	}

	@Override
	public void scale(final double prevWidth, final double prevHeight, final @NotNull Position pos, final @NotNull Rectangle2D bound) {
		scaleSetPointsWithRatio(points, prevWidth, prevHeight, pos, bound);
	}

	@Override
	public void copy(final Shape sh) {
		super.copy(sh);
		if(sh instanceof SquaredShape) {
			setWidth(sh.getWidth());
		}
	}

	@Override
	public void setWidth(final double width) {
		if(width > 0 && MathUtils.INST.isValidCoord(width)) {
			final Point pt = points.get(points.size() - 1);
			final double xPos = pt.getX() + width;
			final double yPos = pt.getY() - width;
			points.get(1).setX(xPos);
			points.get(2).setX(xPos);
			points.get(0).setY(yPos);
			points.get(1).setY(yPos);
		}
	}

	@Override
	public double getHeight() {
		return getWidth();
	}

	@Override
	public double getWidth() {
		return points.get(1).getX() - points.get(0).getX();
	}

	@Override
	public boolean isBordersMovable() {
		return true;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}

	@Override
	public boolean isDbleBorderable() {
		return true;
	}
}
