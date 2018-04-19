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

import java.util.List;
import java.util.stream.Collectors;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * A model of a shape that contains points that can be modified.
 * @author Arnaud Blouin
 */
abstract class LModifiablePointsShape extends LShape implements IModifiablePointsShape {
	protected LModifiablePointsShape(final List<IPoint> pts) {
		super();
		if(pts == null || pts.stream().anyMatch(pt -> !MathUtils.INST.isValidPt(pt))) {
			throw new IllegalArgumentException();
		}
		points.addAll(pts.stream().map(pt -> ShapeFactory.INST.createPoint(pt)).collect(Collectors.toList()));
	}

	@Override
	public abstract IModifiablePointsShape duplicate();

	@Override
	public void setRotationAngleOnly(final double rotationAngle) {
		super.setRotationAngle(rotationAngle);
	}

	@Override
	public void rotate(final IPoint point, final double angle) {
		setRotationAngle(point, angle);
	}


	public void setRotationAngle(final IPoint gc, final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			final double diff = angle - getRotationAngle();
			final IPoint gc2 = gc == null ? getGravityCentre() : gc;

			super.setRotationAngle(angle);
			points.forEach(pt -> pt.setPoint(pt.rotatePoint(gc2, diff)));
		}
	}


	@Override
	public void setRotationAngle(final double angle) {
		setRotationAngle(null, angle);
	}


	@Override
	public boolean setPoint(final double x, final double y, final int position) {
		if(!MathUtils.INST.isValidPt(x, y) || position < -1 || position > points.size() || points.isEmpty()) {
			return false;
		}

		final IPoint p = position == -1 ? points.get(points.size() - 1) : points.get(position);
		p.setPoint(x, y);

		return true;
	}
}
