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

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.ClosableProp;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of a Bezier curve.
 * @author Arnaud Blouin
 */
class BezierCurveImpl extends CtrlPointShapeBase implements BezierCurve, ArrowableShapeBase {
	private final @NotNull List<Arrow> arrows;
	/** Defines if the drawing is opened of closed. */
	private final @NotNull BooleanProperty open;

	/**
	 * Creates a bezier curve with a set of points.
	 * @param pts The list of points.
	 */
	BezierCurveImpl(final @NotNull List<Point> pts) {
		this(pts, computeDefaultFirstCtrlPoints(pts));
	}

	BezierCurveImpl(final @NotNull List<Point> pts, final @NotNull List<Point> ptsCtrl) {
		super(pts, ptsCtrl);
		arrows = new ArrayList<>();
		arrows.add(ShapeFactory.INST.createArrow(this));
		arrows.add(ShapeFactory.INST.createArrow(this));
		open = new SimpleBooleanProperty(true);
	}

	@Override
	public Line getArrowLine(final int index) {
		if(getNbPoints() < 2) {
			return null;
		}
		switch(index) {
			case 0:
				return ShapeFactory.INST.createLine(points.get(0), firstCtrlPts.get(0));
			case 1:
				return ShapeFactory.INST.createLine(points.get(points.size() - 1), firstCtrlPts.get(points.size() - 1));
			default:
				return null;
		}
	}

	@Override
	public boolean isOpened() {
		return open.get();
	}

	@Override
	public void setOpened(final boolean isOpen) {
		open.set(isOpen);
	}

	@Override
	public boolean isDbleBorderable() {
		return true;
	}

	@Override
	public void copy(final Shape sh) {
		super.copy(sh);
		ArrowableShapeBase.super.copy(sh);
		if(sh instanceof ClosableProp) {
			setOpened(((ClosableProp) sh).isOpened());
		}
	}

	@Override
	public @NotNull BezierCurve duplicate() {
		final BezierCurve dup = ShapeFactory.INST.createBezierCurve(points, firstCtrlPts);
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
	public @NotNull List<Arrow> getArrows() {
		return arrows;
	}

	@Override
	public @NotNull BooleanProperty openedProperty() {
		return open;
	}
}
