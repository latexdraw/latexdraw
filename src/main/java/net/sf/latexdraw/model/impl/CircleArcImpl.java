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
package net.sf.latexdraw.model.impl;

import java.util.Arrays;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.ArcProp;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;

/**
 * An implementation of the circle arc.
 * @author Arnaud Blouin
 */
class CircleArcImpl extends SquaredShapeBase implements CircleArc, ArrowableShapeBase {
	private final List<Arrow> arrows;
	/** The style of the arc. */
	private final ObjectProperty<ArcStyle> style;
	/** The start angle of the arc. In radian. */
	private final DoubleProperty startAngle;
	/** The end angle of the arc. In radian. */
	private final DoubleProperty endAngle;


	CircleArcImpl(final Point tl, final double width) {
		super(tl, width);
		arrows = Arrays.asList(ShapeFactory.INST.createArrow(this), ShapeFactory.INST.createArrow(this));
		style = new SimpleObjectProperty<>(ArcStyle.ARC);
		startAngle = new SimpleDoubleProperty(0d);
		endAngle = new SimpleDoubleProperty(3d * Math.PI / 2d);
	}

	@Override
	public void copy(final Shape sh) {
		super.copy(sh);
		ArrowableShapeBase.super.copy(sh);
		if(sh instanceof ArcProp) {
			final ArcProp arc = (ArcProp) sh;
			startAngle.set(arc.getAngleStart());
			endAngle.set(arc.getAngleEnd());
			style.set(arc.getArcStyle());
		}
	}

	@Override
	public Point getCenter() {
		return getGravityCentre();
	}

	@Override
	public double getRadius() {
		return getWidth() / 2d;
	}

	@Override
	public Line getArrowLine(final int index) {
		final Arrow arrow = getArrowAt(index);

		if(arrow == null) {
			return null;
		}

		// Computing the angle to use for the second point of the line:
		// the length of the arrow and the radius are used to compute the angle that separates the two points of the line.
		final double gap = Math.asin(Math.max(-1d, Math.min(1d, arrow.getArrowShapeLength() / getRadius())));

		if(index == 0) {
			final Point sp = getStartPoint();
			final Point ep = getPointOnArc(getAngleStart() < getAngleEnd() ? getAngleStart() + gap : getAngleStart() - gap);
			return ShapeFactory.INST.createLine(sp, ep);
		}

		// For sure index == 1 here.
		final Point sp = getEndPoint();
		final Point ep = getPointOnArc(getAngleEnd() < getAngleStart() ? getAngleEnd() + gap : getAngleEnd() - gap);
		return ShapeFactory.INST.createLine(sp, ep);
	}

	@Override
	public boolean isShowPtsable() {
		return true;
	}

	@Override
	public double getAngleEnd() {
		return endAngle.get();
	}

	@Override
	public double getAngleStart() {
		return startAngle.get();
	}

	@Override
	public Point getEndPoint() {
		return getPointOnArc(getAngleEnd());
	}

	@Override
	public Point getStartPoint() {
		return getPointOnArc(getAngleStart());
	}

	private Point getPointOnArc(final double angle) {
		final Point grav = getGravityCentre();
		return ShapeFactory.INST.createPoint(grav.getX() + Math.cos(angle) * getRadius(), grav.getY() - Math.sin(angle) * getRadius());
	}

	@Override
	public ArcStyle getArcStyle() {
		return style.get();
	}

	@Override
	public void setAngleEnd(final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			endAngle.set(angle);
		}
	}

	@Override
	public void setAngleStart(final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			startAngle.set(angle);
		}
	}

	@Override
	public void setArcStyle(final ArcStyle arcStyle) {
		if(arcStyle != null) {
			style.set(arcStyle);
		}
	}

	@Override
	public List<Arrow> getArrows() {
		return arrows;
	}

	@Override
	public ObjectProperty<ArcStyle> arcStyleProperty() {
		return style;
	}

	@Override
	public DoubleProperty angleStartProperty() {
		return startAngle;
	}

	@Override
	public DoubleProperty angleEndProperty() {
		return endAngle;
	}
}
