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

import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * An implementation of a Bezier curve.
 * @author Arnaud Blouin
 */
class LBezierCurve extends LAbstractCtrlPointShape implements IBezierCurve, LArrowableShape {
	private final List<IArrow> arrows;
	private boolean isClosed;

	/**
	 * Creates a bezier curve with a set of points.
	 * @param pts The list of points.
	 */
	LBezierCurve(final List<IPoint> pts, final boolean closed) {
		this(pts, computeDefaultFirstCtrlPoints(pts), closed);
	}

	LBezierCurve(final List<IPoint> pts, final List<IPoint> ptsCtrl, final boolean closed) {
		super(pts, ptsCtrl);
		arrows = new ArrayList<>();
		arrows.add(ShapeFactory.INST.createArrow(this));
		arrows.add(ShapeFactory.INST.createArrow(this));
		isClosed = closed;
	}

	@Override
	public ILine getArrowLine(final IArrow arrow) {
		if(getNbPoints() < 2) {
			return null;
		}
		switch(arrows.indexOf(arrow)) {
			case 0:
				return ShapeFactory.INST.createLine(points.get(0), firstCtrlPts.get(0));
			case 1:
				return ShapeFactory.INST.createLine(points.get(points.size() - 1), firstCtrlPts.get(points.size() - 1));
			default:
				return null;
		}
	}

	@Override
	public boolean isClosed() {
		return isClosed;
	}

	@Override
	public void setIsClosed(final boolean closed) {
		isClosed = closed;
	}

	@Override
	public boolean isDbleBorderable() {
		return true;
	}

	@Override
	public void copy(final IShape sh) {
		super.copy(sh);
		LArrowableShape.super.copy(sh);
		if(sh instanceof IBezierCurve) {
			setIsClosed(((IBezierCurve) sh).isClosed());
		}
	}

	@Override
	public IBezierCurve duplicate() {
		final IBezierCurve dup = ShapeFactory.INST.createBezierCurve(points, firstCtrlPts);
		dup.copy(this);
		return dup;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}

	@Override
	public boolean shadowFillsShape() {
		return false;
	}

	@Override
	public boolean isShowPtsable() {
		return true;
	}

	@Override
	public List<IArrow> getArrows() {
		return arrows;
	}
}
