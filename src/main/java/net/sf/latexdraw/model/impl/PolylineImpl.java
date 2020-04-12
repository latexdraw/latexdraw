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

import java.util.Arrays;
import java.util.List;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of a polyline.
 * @author Arnaud Blouin
 */
class PolylineImpl extends PolygonImpl implements Polyline, ArrowableShapeBase {
	private final @NotNull List<Arrow> arrows;

	/**
	 * Creates a model with a set of points.
	 * @throws IllegalArgumentException If one of the points is null.
	 */
	PolylineImpl(final @NotNull List<Point> pts) {
		super(pts);
		arrows = Arrays.asList(ShapeFactory.INST.createArrow(this), ShapeFactory.INST.createArrow(this));
	}

	@Override
	public void copy(final Shape sh) {
		super.copy(sh);
		ArrowableShapeBase.super.copy(sh);
	}

	@Override
	public @NotNull Polyline duplicate() {
		final Polyline dup = ShapeFactory.INST.createPolyline(points);
		dup.copy(this);
		return dup;
	}

	@Override
	public Line getArrowLine(final int index) {
		if(getNbPoints() < 2) {
			return null;
		}
		return switch(index) {
			case 0 -> ShapeFactory.INST.createLine(points.get(0), points.get(1));
			case 1 -> ShapeFactory.INST.createLine(points.get(getNbPoints() - 1), points.get(getNbPoints() - 2));
			default -> null;
		};
	}

	@Override
	public boolean shadowFillsShape() {
		return false;
	}

	@Override
	public @NotNull List<Arrow> getArrows() {
		return arrows;
	}
}
