package net.sf.latexdraw.glib.models.impl;

import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.shape.Color;
import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

/**
 * Defines a model of a grid.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LGrid extends LAbstractGrid implements IGrid {
	/** The colour of the sub-grid. */
	protected Color subGridColour;

	/** The number of division in a sub-grid. */
	protected int subGridDiv;

	/** The thickness of the main borders of the grid. */
	protected double gridWidth;

	/** The colour of the labels */
	protected Color gridLabelsColour;

	/** The number of dots in the lines of the grid
	 * ( if >0, replace a plain line) */
	protected int gridDots;

	/** The thickness of the lines of the sub-grid */
	protected double subGridWidth;

	/** The number of dots in the lines of the sub-grid
	 * ( if >0, replace a plain line) */
	protected int subGridDots;

	/** The unit of the grid */
	protected double unit;

	/** The minimum possible size of the labels */
	public static final int MIN_LABELS_SIZE = 5;



	/**
	 * Creates a grid with a predefined point.
	 * @param pt The position.
	 */
	protected LGrid(final IPoint pt) {
		super(pt);
		gridDots    		= PSTricksConstants.DEFAULT_GRIDDOTS;
		gridLabelsColour 	= PSTricksConstants.DEFAULT_LABELGRIDCOLOR;
		labelSize  			= (int)PSTricksConstants.DEFAULT_GRID_LABEL;
		gridWidth       	= PSTricksConstants.DEFAULT_GRID_WIDTH*PPC;
		subGridColour 		= PSTricksConstants.DEFAULT_SUB_GRID_COLOR;
		subGridDiv   		= PSTricksConstants.DEFAULT_SUBGRIDDIV;
		subGridDots  		= PSTricksConstants.DEFAULT_SUBGRIDDOTS;
		subGridWidth 		= PSTricksConstants.DEFAULT_SUB_GRID_WIDTH*PPC;
		unit 		 		= PSTricksConstants.DEFAULT_UNIT;
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IGridProp) {
			final IGridProp grid = (IGridProp) sh;

			gridDots 		= grid.getGridDots();
			subGridColour 	= grid.getSubGridColour();
			subGridDiv 		= grid.getSubGridDiv();
			subGridDots 	= grid.getSubGridDots();
			gridLabelsColour= grid.getGridLabelsColour();
			xLabelSouth 	= grid.isXLabelSouth();
			yLabelWest 		= grid.isYLabelWest();
			unit			= grid.getUnit();
			gridWidth		= grid.getGridWidth();
			subGridWidth	= grid.getSubGridWidth();
		}
	}


	@Override
	public boolean isXLabelSouth() {
		return xLabelSouth;
	}


	@Override
	public boolean isYLabelWest() {
		return yLabelWest;
	}


	@Override
	public void setXLabelSouth(final boolean isXLabelSouth) {
		xLabelSouth = isXLabelSouth;
	}


	@Override
	public void setYLabelWest(final boolean isYLabelWest) {
		yLabelWest = isYLabelWest;
	}

	@Override
	public IPoint getBottomRightPoint() {
		final IPoint pos = getPosition();
		return ShapeFactory.createPoint(pos.getX()+getGridMaxX()*PPC*unit, pos.getY()-getGridMinY()*PPC);
	}


	@Override
	public IPoint getTopLeftPoint() {
		final IPoint pos = getPosition();
		return ShapeFactory.createPoint(pos.getX()+getGridMinX()*PPC, pos.getY()-getGridMaxY()*PPC*unit);
	}


	@Override
	public void scale(final double x, final double y, final Position pos, final Rectangle2D bound) {
		if(pos==null || bound==null) return;

		final double sx = x/bound.getWidth();
		final double sy = y/bound.getHeight();
		switch(pos) {
			case WEST: case SW:
				if(unit+sx-1>=0.5)
					setUnit(unit+sx-1);
				break;
			case SOUTH:
				if(unit+sy-1>=0.5)
					setUnit(unit+sy-1);
				break;
			case NORTH: case NW:
				if(unit+sy-1>=0.5) {
					setUnit(unit+sy-1);
					translate(0., -getTopRightPoint().getY()+bound.getY());
				}
				break;
			case NE:
				if(unit+sy-1>=0.5) {
					setUnit(unit+sy-1);
					final IPoint tr = getTopRightPoint();
					translate(-tr.getX()+bound.getMaxX(), -tr.getY()+bound.getY());
				}
				break;
			case EAST: case SE:
				if(unit+sx-1>=0.5) {
					setUnit(unit+sx-1);
					translate(-getTopRightPoint().getX()+bound.getMaxX(), 0.);
				}
				break;
		}
	}



	@Override
	public int getGridDots() {
		return gridDots;
	}


	@Override
	public Color getGridLabelsColour() {
		return gridLabelsColour;
	}


	@Override
	public double getGridWidth() {
		return gridWidth;
	}


	@Override
	public Color getSubGridColour() {
		return subGridColour;
	}


	@Override
	public int getSubGridDiv() {
		return subGridDiv;
	}


	@Override
	public int getSubGridDots() {
		return subGridDots;
	}


	@Override
	public double getSubGridWidth() {
		return subGridWidth;
	}


	@Override
	public double getUnit() {
		return unit;
	}


	@Override
	public void setGridDots(final int grDots) {
		if(grDots>=0)
			this.gridDots = grDots;
	}


	@Override
	public void setGridLabelsColour(final Color gridLabelsCol) {
		if(gridLabelsCol!=null)
			this.gridLabelsColour = gridLabelsCol;
	}



	@Override
	public void setGridWidth(final double gridW) {
		if(gridW>0 && GLibUtilities.isValidCoordinate(gridW))
			this.gridWidth = gridW;
	}


	@Override
	public void setSubGridColour(final Color subGridCol) {
		if(subGridCol!=null)
			this.subGridColour = subGridCol;
	}


	@Override
	public void setSubGridDiv(final int subGridD) {
		if(subGridD>=0)
			this.subGridDiv = subGridD;
	}


	@Override
	public void setSubGridDots(final int subGridD) {
		if(subGridD>=0)
			this.subGridDots = subGridD;
	}


	@Override
	public void setSubGridWidth(final double subGridW) {
		if(subGridW>0 && GLibUtilities.isValidCoordinate(subGridW))
			this.subGridWidth = subGridW;
	}


	@Override
	public void setUnit(final double un) {
		if(un>0 && GLibUtilities.isValidCoordinate(un))//TODO unit may be lesser than 0.
			this.unit = un;
	}


	@Override
	public double getStep() {
		return unit*IShape.PPC;
	}
}
