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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.GridProp;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Position;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * A model of a grid.
 * @author Arnaud BLOUIN
 */
class GridImpl extends GridBase implements Grid {
	/** If true, the x label will be displayed at the south of the grid. Else at the north */
	private final @NotNull BooleanProperty xLabelSouth;

	/** If true, the y label will be displayed at the west of the grid. Else at the east */
	private final @NotNull BooleanProperty yLabelWest;

	/** The colour of the sub-grid. */
	private final @NotNull ObjectProperty<Color> subGridColour;

	/** The number of division in a sub-grid. */
	private final @NotNull IntegerProperty subGridDiv;

	/** The thickness of the main borders of the grid. */
	private final @NotNull DoubleProperty gridWidth;

	/** The colour of the labels */
	private final @NotNull ObjectProperty<Color> gridLabelsColour;

	/** The number of dots in the lines of the grid ( if >0, replace a plain line) */
	private final @NotNull IntegerProperty gridDots;

	/** The thickness of the lines of the sub-grid */
	private final @NotNull DoubleProperty subGridWidth;

	/** The number of dots in the lines of the sub-grid ( if >0, replace a plain line) */
	private final @NotNull IntegerProperty subGridDots;

	/** The unit of the grid */
	private final @NotNull DoubleProperty unit;


	/**
	 * Creates a grid with a predefined point.
	 * @param pt The position.
	 */
	GridImpl(final Point pt) {
		super(pt);
		xLabelSouth = new SimpleBooleanProperty(true);
		yLabelWest = new SimpleBooleanProperty(true);
		gridDots = new SimpleIntegerProperty(PSTricksConstants.DEFAULT_GRIDDOTS);
		gridLabelsColour = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_LABELGRIDCOLOR);
		labelSize.set((int) PSTricksConstants.DEFAULT_GRID_LABEL);
		gridWidth = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_GRID_WIDTH * PPC);
		subGridColour = new SimpleObjectProperty<>(PSTricksConstants.DEFAULT_SUB_GRID_COLOR);
		subGridDiv = new SimpleIntegerProperty(PSTricksConstants.DEFAULT_SUBGRIDDIV);
		subGridDots = new SimpleIntegerProperty(PSTricksConstants.DEFAULT_SUBGRIDDOTS);
		subGridWidth = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_SUB_GRID_WIDTH * PPC);
		unit = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_UNIT);
	}


	@Override
	public void copy(final Shape sh) {
		super.copy(sh);

		if(sh instanceof GridProp) {
			final GridProp grid = (GridProp) sh;

			gridDots.set(grid.getGridDots());
			subGridColour.set(grid.getSubGridColour());
			subGridDiv.set(grid.getSubGridDiv());
			subGridDots.set(grid.getSubGridDots());
			gridLabelsColour.set(grid.getGridLabelsColour());
			xLabelSouth.set(grid.isXLabelSouth());
			yLabelWest.set(grid.isYLabelWest());
			unit.set(grid.getUnit());
			gridWidth.set(grid.getGridWidth());
			subGridWidth.set(grid.getSubGridWidth());
		}
	}

	@Override
	public @NotNull Grid duplicate() {
		final Grid grid = ShapeFactory.INST.createGrid(getPosition());
		grid.copy(this);
		return grid;
	}

	@Override
	public @NotNull Point getPosition() {
		return getPtAt(0);
	}

	@Override
	public boolean isXLabelSouth() {
		return xLabelSouth.get();
	}

	@Override
	public void setXLabelSouth(final boolean isXLabelSouth) {
		xLabelSouth.set(isXLabelSouth);
	}

	@Override
	public boolean isYLabelWest() {
		return yLabelWest.get();
	}

	@Override
	public void setYLabelWest(final boolean isYLabelWest) {
		yLabelWest.set(isYLabelWest);
	}

	@Override
	public @NotNull Point getBottomRightPoint() {
		final Point pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMaxX() * PPC * getUnit(), pos.getY() - getGridMinY() * PPC);
	}


	@Override
	public @NotNull Point getTopLeftPoint() {
		final Point pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMinX() * PPC, pos.getY() - getGridMaxY() * PPC * getUnit());
	}

	@Override
	public void scale(final double x, final double y, final @NotNull Position pos, final @NotNull Rectangle2D bound) {
		scaleWithRatio(x, y, pos, bound);
	}

	@Override
	public void scaleWithRatio(final double x, final double y, final @NotNull Position pos, final @NotNull Rectangle2D bound) {
		final double sx = x / bound.getWidth();
		final double sy = y / bound.getHeight();
		final double u = getUnit();

		switch(pos) {
			case WEST:
			case SW:
				if(u + sx - 1d >= 0.5) {
					setUnit(u + sx - 1d);
				}
				break;
			case SOUTH:
				if(u + sy - 1d >= 0.5) {
					setUnit(u + sy - 1d);
				}
				break;
			case NORTH:
			case NW:
				if(u + sy - 1d >= 0.5) {
					setUnit(u + sy - 1d);
					translate(0., -getTopRightPoint().getY() + bound.getY());
				}
				break;
			case NE:
				if(u + sy - 1d >= 0.5) {
					setUnit(u + sy - 1d);
					final Point tr = getTopRightPoint();
					translate(-tr.getX() + bound.getMaxX(), -tr.getY() + bound.getY());
				}
				break;
			case EAST:
			case SE:
				if(u + sx - 1d >= 0.5) {
					setUnit(u + sx - 1d);
					translate(-getTopRightPoint().getX() + bound.getMaxX(), 0d);
				}
				break;
		}
	}

	@Override
	public int getGridDots() {
		return gridDots.get();
	}

	@Override
	public void setGridDots(final int grDots) {
		if(grDots >= 0) {
			gridDots.set(grDots);
		}
	}

	@Override
	public @NotNull Color getGridLabelsColour() {
		return gridLabelsColour.get();
	}

	@Override
	public void setGridLabelsColour(final @NotNull Color gridLabelsCol) {
		gridLabelsColour.setValue(gridLabelsCol);
	}

	@Override
	public double getGridWidth() {
		return gridWidth.get();
	}

	@Override
	public void setGridWidth(final double gridW) {
		if(gridW > 0d && MathUtils.INST.isValidCoord(gridW)) {
			gridWidth.set(gridW);
		}
	}

	@Override
	public @NotNull Color getSubGridColour() {
		return subGridColour.get();
	}

	@Override
	public void setSubGridColour(final @NotNull Color subGridCol) {
		subGridColour.set(subGridCol);
	}

	@Override
	public int getSubGridDiv() {
		return subGridDiv.get();
	}

	@Override
	public void setSubGridDiv(final int subGridD) {
		if(subGridD >= 0) {
			subGridDiv.set(subGridD);
		}
	}

	@Override
	public int getSubGridDots() {
		return subGridDots.get();
	}

	@Override
	public void setSubGridDots(final int subGridD) {
		if(subGridD >= 0) {
			subGridDots.set(subGridD);
		}
	}

	@Override
	public double getSubGridWidth() {
		return subGridWidth.get();
	}

	@Override
	public void setSubGridWidth(final double subGridW) {
		if(subGridW > 0d && MathUtils.INST.isValidCoord(subGridW)) {
			subGridWidth.set(subGridW);
		}
	}

	@Override
	public double getUnit() {
		return unit.get();
	}

	@Override
	public void setUnit(final double un) {
		if(un > 0d && MathUtils.INST.isValidCoord(un)) {
			unit.set(un);
		}
	}

	@Override
	public @NotNull ObjectProperty<Color> gridLabelsColourProperty() {
		return gridLabelsColour;
	}

	@Override
	public @NotNull IntegerProperty gridDotsProperty() {
		return gridDots;
	}

	@Override
	public @NotNull DoubleProperty unitProperty() {
		return unit;
	}

	@Override
	public @NotNull DoubleProperty subGridWidthProperty() {
		return subGridWidth;
	}

	@Override
	public @NotNull IntegerProperty subGridDotsProperty() {
		return subGridDots;
	}

	@Override
	public @NotNull IntegerProperty subGridDivProperty() {
		return subGridDiv;
	}

	@Override
	public @NotNull ObjectProperty<Color> subGridColourProperty() {
		return subGridColour;
	}

	@Override
	public @NotNull DoubleProperty gridWidthProperty() {
		return gridWidth;
	}

	@Override
	public @NotNull BooleanProperty yLabelWestProperty() {
		return yLabelWest;
	}

	@Override
	public @NotNull BooleanProperty xLabelSouthProperty() {
		return xLabelSouth;
	}

	@Override
	public double getStep() {
		return getUnit() * Shape.PPC;
	}
}
