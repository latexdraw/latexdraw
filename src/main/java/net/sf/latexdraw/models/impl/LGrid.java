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

import java.awt.geom.Rectangle2D;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.Position;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * A model of a grid.
 * @author Arnaud BLOUIN
 */
class LGrid extends LAbstractGrid implements IGrid {
	/** If true, the x label will be displayed at the south of the grid. Else at the north */
	private final BooleanProperty xLabelSouth;

	/** If true, the y label will be displayed at the west of the grid. Else at the east */
	private final BooleanProperty yLabelWest;

	/** The colour of the sub-grid. */
	private final ObjectProperty<Color> subGridColour;

	/** The number of division in a sub-grid. */
	private final IntegerProperty subGridDiv;

	/** The thickness of the main borders of the grid. */
	private final DoubleProperty gridWidth;

	/** The colour of the labels */
	private final ObjectProperty<Color> gridLabelsColour;

	/** The number of dots in the lines of the grid ( if >0, replace a plain line) */
	private final IntegerProperty gridDots;

	/** The thickness of the lines of the sub-grid */
	private final DoubleProperty subGridWidth;

	/** The number of dots in the lines of the sub-grid ( if >0, replace a plain line) */
	private final IntegerProperty subGridDots;

	/** The unit of the grid */
	private final DoubleProperty unit;


	/**
	 * Creates a grid with a predefined point.
	 * @param pt The position.
	 */
	LGrid(final IPoint pt) {
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
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IGridProp) {
			final IGridProp grid = (IGridProp) sh;

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
	public IGrid duplicate() {
		final IGrid grid = ShapeFactory.INST.createGrid(getPosition());
		grid.copy(this);
		return grid;
	}

	@Override
	public IPoint getPosition() {
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
	public IPoint getBottomRightPoint() {
		final IPoint pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMaxX() * PPC * getUnit(), pos.getY() - getGridMinY() * PPC);
	}


	@Override
	public IPoint getTopLeftPoint() {
		final IPoint pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMinX() * PPC, pos.getY() - getGridMaxY() * PPC * getUnit());
	}

	@Override
	public void scale(final double x, final double y, final Position pos, final Rectangle2D bound) {
		scaleWithRatio(x, y, pos, bound);
	}

	@Override
	public void scaleWithRatio(final double x, final double y, final Position pos, final Rectangle2D bound) {
		if(pos == null || bound == null) {
			return;
		}

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
					final IPoint tr = getTopRightPoint();
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
	public Color getGridLabelsColour() {
		return gridLabelsColour.get();
	}

	@Override
	public void setGridLabelsColour(final Color gridLabelsCol) {
		if(gridLabelsCol != null) {
			gridLabelsColour.setValue(gridLabelsCol);
		}
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
	public Color getSubGridColour() {
		return subGridColour.get();
	}

	@Override
	public void setSubGridColour(final Color subGridCol) {
		if(subGridCol != null) {
			subGridColour.set(subGridCol);
		}
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
		if(un > 0d && MathUtils.INST.isValidCoord(un)) { //TODO unit may be lesser than 0.
			unit.set(un);
		}
	}

	@Override
	public ObjectProperty<Color> gridLabelsColourProperty() {
		return gridLabelsColour;
	}

	@Override
	public IntegerProperty gridDotsProperty() {
		return gridDots;
	}

	@Override
	public DoubleProperty unitProperty() {
		return unit;
	}

	@Override
	public DoubleProperty subGridWidthProperty() {
		return subGridWidth;
	}

	@Override
	public IntegerProperty subGridDotsProperty() {
		return subGridDots;
	}

	@Override
	public IntegerProperty subGridDivProperty() {
		return subGridDiv;
	}

	@Override
	public ObjectProperty<Color> subGridColourProperty() {
		return subGridColour;
	}

	@Override
	public DoubleProperty gridWidthProperty() {
		return gridWidth;
	}

	@Override
	public BooleanProperty yLabelWestProperty() {
		return yLabelWest;
	}

	@Override
	public BooleanProperty xLabelSouthProperty() {
		return xLabelSouth;
	}

	@Override
	public double getStep() {
		return getUnit() * IShape.PPC;
	}
}
