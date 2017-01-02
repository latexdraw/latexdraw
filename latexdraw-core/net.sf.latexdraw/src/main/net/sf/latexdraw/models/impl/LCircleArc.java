/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl;

import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * An implementation of a rounded arc.
 */
class LCircleArc extends LSquaredShape implements ICircleArc, LArrowableShape {
	private final List<IArrow> arrows;
	/** The style of the arc. */
	private ArcStyle style;
	/** The start angle of the arc. In radian. */
	private double startAngle;
	/** The end angle of the arc. In radian. */
	private double endAngle;


	LCircleArc(final IPoint tl, final double width) {
		super(tl, width);
		arrows = new ArrayList<>();
		arrows.add(ShapeFactory.INST.createArrow(this));
		arrows.add(ShapeFactory.INST.createArrow(this));
		style = ArcStyle.ARC;
		startAngle = 0.0;
		endAngle = 3.0 * Math.PI / 2.0;
	}

	@Override
	public void copy(final IShape sh) {
		super.copy(sh);
		if(sh instanceof IArcProp) {
			IArcProp arc = (IArcProp) sh;
			startAngle = arc.getAngleStart();
			endAngle = arc.getAngleEnd();
			style = arc.getArcStyle();
		}
	}

	@Override
	public IPoint getCenter() {
		return getGravityCentre();
	}

	@Override
	public double getRadius() {
		return getWidth() / 2.0;
	}

	@Override
	public ILine getArrowLine(final IArrow arrow) {
		if(getArrowAt(0) == arrow) {
			return MathUtils.INST.getTangenteAt(getTopLeftPoint(), getBottomRightPoint(), getGravityCentre(), startAngle, startAngle < Math.PI);
		}
		if(getArrowAt(1) == arrow) {
			return MathUtils.INST.getTangenteAt(getTopLeftPoint(), getBottomRightPoint(), getGravityCentre(), endAngle, endAngle >= Math.PI);
		}
		return null;
	}

	@Override
	public boolean isShowPtsable() {
		return true;
	}

	@Override
	public double getAngleEnd() {
		return endAngle;
	}

	@Override
	public double getAngleStart() {
		return startAngle;
	}

	@Override
	public IPoint getEndPoint() {
		final IPoint grav = getGravityCentre();
		return ShapeFactory.INST.createPoint(grav.getX() + Math.cos(endAngle) * getHeight() / 2.0, grav.getY() - Math.sin(endAngle) * getHeight() / 2.0);
	}

	@Override
	public IPoint getStartPoint() {
		final IPoint grav = getGravityCentre();
		return ShapeFactory.INST.createPoint(grav.getX() + Math.cos(startAngle) * getWidth() / 2.0, grav.getY() - Math.sin(startAngle) * getWidth() / 2.0);
	}

	@Override
	public ArcStyle getArcStyle() {
		return style;
	}

	@Override
	public void setAngleEnd(final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			endAngle = angle;
		}
	}

	@Override
	public void setAngleStart(final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			startAngle = angle;
		}
	}

	@Override
	public void setArcStyle(ArcStyle arcStyle) {
		if(arcStyle != null) {
			style = arcStyle;
		}
	}

	@Override
	public List<IArrow> getArrows() {
		return arrows;
	}
}
