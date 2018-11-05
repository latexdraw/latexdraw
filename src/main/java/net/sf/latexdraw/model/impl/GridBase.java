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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.IStdGridProp;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.StandardGrid;

/**
 * A implementation of an abstract latex grid.
 * @author Arnaud Blouin
 */
abstract class GridBase extends PositionShapeBase implements StandardGrid {
	/** The x-minimum values of the axes */
	protected final DoubleProperty gridStartx;
	/** The y-minimum values of the axes */
	protected final DoubleProperty gridStarty;
	/** The x-maximum values of the axes */
	protected final DoubleProperty gridEndx;
	/** The y-maximum values of the axes */
	protected final DoubleProperty gridEndy;
	/** The x-coordinate of the origin of the grid */
	protected final DoubleProperty originx;
	/** The y-coordinate of the origin of the grid */
	protected final DoubleProperty originy;
	/** The size of the labels. */
	protected final IntegerProperty labelSize;


	/**
	 * Creates an abstract grid.
	 * @param pt The position
	 */
	GridBase(final Point pt) {
		super(pt);
		originx = new SimpleDoubleProperty(0d);
		originy = new SimpleDoubleProperty(0d);
		gridStartx = new SimpleDoubleProperty(0d);
		gridStarty = new SimpleDoubleProperty(0d);
		gridEndx = new SimpleDoubleProperty(2d);
		gridEndy = new SimpleDoubleProperty(2d);
		labelSize = new SimpleIntegerProperty(10);
	}


	@Override
	public double getGridMinX() {
		return getGridEndX() < getGridStartX() ? getGridEndX() : getGridStartX();
	}


	@Override
	public double getGridMaxX() {
		return getGridEndX() >= getGridStartX() ? getGridEndX() : getGridStartX();
	}


	@Override
	public double getGridMinY() {
		return getGridEndY() < getGridStartY() ? getGridEndY() : getGridStartY();
	}


	@Override
	public double getGridMaxY() {
		return getGridEndY() >= getGridStartY() ? getGridEndY() : getGridStartY();
	}


	@Override
	public Point getBottomRightPoint() {
		final Point pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMaxX() * PPC, pos.getY() - getGridMinY() * PPC);
	}


	@Override
	public Point getTopLeftPoint() {
		final Point pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMinX() * PPC, pos.getY() - getGridMaxY() * PPC);
	}


	@Override
	public Point getTopRightPoint() {
		final Point pos = getPosition();
		final double step = getStep();
		//FIXME strange: different from getTopLeftPoint and co. but works for scale.
		return ShapeFactory.INST.createPoint(pos.getX() + step * (getGridEndX() - getGridStartX()), pos.getY() - step * (getGridEndY() - getGridStartY()));
	}


	@Override
	public void mirrorHorizontal(final double x) {
		if(MathUtils.INST.isValidCoord(x)) {
			final Point bl = points.get(0).horizontalSymmetry(x);
			final Point br = getBottomRightPoint().horizontalSymmetry(x);
			points.get(0).setPoint(br.getX() < bl.getX() ? br.getX() : bl.getX(), br.getY());
		}
	}


	@Override
	public void mirrorVertical(final double y) {
		if(MathUtils.INST.isValidCoord(y)) {
			final Point bl = points.get(0).verticalSymmetry(y);
			final Point tl = getTopLeftPoint().verticalSymmetry(y);
			points.get(0).setPoint(bl.getX(), bl.getY() > tl.getY() ? bl.getY() : tl.getY());
		}
	}


	@Override
	public void setLabelsSize(final int labelsSize) {
		if(labelsSize >= 0) {
			labelSize.set(labelsSize);
		}
	}


	@Override
	public double getGridEndX() {
		return gridEndx.get();
	}


	@Override
	public double getGridEndY() {
		return gridEndy.get();
	}

	@Override
	public double getGridStartX() {
		return gridStartx.get();
	}


	@Override
	public double getGridStartY() {
		return gridStarty.get();
	}


	@Override
	public int getLabelsSize() {
		return labelSize.get();
	}


	@Override
	public double getOriginX() {
		return originx.get();
	}

	@Override
	public double getOriginY() {
		return originy.get();
	}


	@Override
	public void setGridEnd(final double x, final double y) {
		setGridEndX(x);
		setGridEndY(y);
	}


	@Override
	public void setGridEndX(final double x) {
		if(x >= getGridStartX() && MathUtils.INST.isValidCoord(x)) {
			gridEndx.set(x);
		}
	}


	@Override
	public void setGridEndY(final double y) {
		if(y >= getGridStartY() && MathUtils.INST.isValidCoord(y)) {
			gridEndy.set(y);
		}
	}


	@Override
	public void setGridStart(final double x, final double y) {
		setGridStartX(x);
		setGridStartY(y);
	}


	@Override
	public void setGridStartX(final double x) {
		if(x <= getGridEndX() && MathUtils.INST.isValidCoord(x)) {
			gridStartx.set(x);
		}
	}


	@Override
	public void setGridStartY(final double y) {
		if(y <= getGridEndY() && MathUtils.INST.isValidCoord(y)) {
			gridStarty.set(y);
		}
	}


	@Override
	public void setOrigin(final double x, final double y) {
		setOriginX(x);
		setOriginY(y);
	}


	@Override
	public void setOriginX(final double x) {
		if(MathUtils.INST.isValidCoord(x)) {
			originx.set(x);
		}
	}


	@Override
	public void setOriginY(final double y) {
		if(MathUtils.INST.isValidCoord(y)) {
			originy.set(y);
		}
	}


	@Override
	public void copy(final Shape s) {
		super.copy(s);

		if(s instanceof IStdGridProp) {
			final IStdGridProp grid = (IStdGridProp) s;
			gridEndx.set(grid.getGridEndX());
			gridEndy.set(grid.getGridEndY());
			gridStartx.set(grid.getGridStartX());
			gridStarty.set(grid.getGridStartY());
			originx.set(grid.getOriginX());
			originy.set(grid.getOriginY());
			setLabelsSize(grid.getLabelsSize());
		}
	}


	@Override
	public Point getGridStart() {
		return ShapeFactory.INST.createPoint(getGridStartX(), getGridStartY());
	}


	@Override
	public Point getGridEnd() {
		return ShapeFactory.INST.createPoint(getGridEndX(), getGridEndY());
	}

	@Override
	public IntegerProperty labelsSizeProperty() {
		return labelSize;
	}

	@Override
	public DoubleProperty gridStartXProperty() {
		return gridStartx;
	}

	@Override
	public DoubleProperty gridStartYProperty() {
		return gridStarty;
	}

	@Override
	public DoubleProperty gridEndXProperty() {
		return gridEndx;
	}

	@Override
	public DoubleProperty gridEndYProperty() {
		return gridEndy;
	}

	@Override
	public DoubleProperty originXProperty() {
		return originx;
	}

	@Override
	public DoubleProperty originYProperty() {
		return originy;
	}

	@Override
	public boolean isInteriorStylable() {
		return false;
	}

	@Override
	public boolean isFillable() {
		return false;
	}

	@Override
	public boolean isShadowable() {
		return false;
	}

	@Override
	public boolean isThicknessable() {
		return false;
	}

	@Override
	public abstract StandardGrid duplicate();
}
