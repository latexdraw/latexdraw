/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * An implementation of a polyline.
 * @author Arnaud Blouin
 */
class LPolyline extends LPolygon implements IPolyline, LArrowableShape {
	private final List<IArrow> arrows;

	LPolyline() {
		super();
		arrows = Arrays.asList(ShapeFactory.INST.createArrow(this), ShapeFactory.INST.createArrow(this));
	}

	LPolyline(final IPoint point, final IPoint point2) {
		this();
		addPoint(Objects.requireNonNull(point));
		addPoint(Objects.requireNonNull(point2));
	}

	@Override
	public void copy(final IShape sh) {
		super.copy(sh);
		LArrowableShape.super.copy(sh);
	}

	@Override
	public ILine getArrowLine(final IArrow arrow) {
		if(getNbPoints() < 2) {
			return null;
		}
		switch(arrows.indexOf(arrow)) {
			case 0:
				return ShapeFactory.INST.createLine(points.get(0), points.get(1));
			case 1:
				return ShapeFactory.INST.createLine(points.get(getNbPoints() - 1), points.get(getNbPoints() - 2));
			default:
				return null;
		}
	}

	@Override
	public boolean isFillable() {
		return getNbPoints() > 2;
	}

	@Override
	public boolean shadowFillsShape() {
		return false;
	}

	@Override
	public List<IArrow> getArrows() {
		return arrows;
	}
}
