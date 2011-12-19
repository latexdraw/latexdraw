package net.sf.latexdraw.glib.models.impl;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a model of a grid.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The position.
	 */
	protected LGrid(final boolean isUniqueID, final IPoint pt) {
		super(isUniqueID, pt);

		gridDots    		= PSTricksConstants.DEFAULT_GRIDDOTS;
		gridLabelsColour 	= PSTricksConstants.DEFAULT_LABELGRIDCOLOR;
		labelSize  			= (int)PSTricksConstants.DEFAULT_GRID_LABEL;
		gridWidth       	= PSTricksConstants.DEFAULT_GRID_WIDTH*PPC;
		subGridColour 		= PSTricksConstants.DEFAULT_SUB_GRID_COLOR;
		subGridDiv   		= PSTricksConstants.DEFAULT_SUBGRIDDIV;
		subGridDots  		= PSTricksConstants.DEFAULT_SUBGRIDDOTS;
		subGridWidth 		= PSTricksConstants.DEFAULT_SUB_GRID_WIDTH*PPC;
		unit 		 		= PSTricksConstants.DEFAULT_UNIT;

		update();
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IGrid) {
			IGrid grid = (IGrid) sh;

			gridDots 		= grid.getGridDots();
			subGridColour 	= grid.getSubGridColour();
			subGridDiv 		= grid.getSubGridDiv();
			subGridDots 	= grid.getSubGridDots();
			gridLabelsColour	= grid.getGridLabelsColour();
			unit			= grid.getUnit();
			gridWidth		= grid.getGridWidth();
			subGridWidth	= grid.getSubGridWidth();
		}
	}


	@Override
	public IGrid duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IGrid ? (IGrid)sh : null;
	}


	@Override
	public boolean isParametersEquals(final IShape sh, final boolean considerShadow) {
		boolean equal = super.isParametersEquals(sh, considerShadow);

		if(equal && sh instanceof IGrid) {
			IGrid grid = (IGrid) sh;

			// Comparing colours.
			equal = grid.getSubGridColour().equals(subGridColour) && grid.getGridLabelsColour().equals(gridLabelsColour);
			// Comparing div and unit.
			equal = equal && grid.getSubGridDiv()==subGridDiv && LNumber.INSTANCE.equals(unit, grid.getUnit());
			// Comparing dots.
			equal = equal && grid.getSubGridDots()==subGridDots && gridDots==grid.getGridDots();
			// Comparing widths.
			equal = equal && LNumber.INSTANCE.equals(grid.getSubGridWidth(), subGridWidth) && LNumber.INSTANCE.equals(grid.getGridWidth(), gridWidth);
		}

		return equal;
	}




	@Override
	public void scale(final double sx, final double sy, final Position pos) {
		if(pos==null || sx<=0 || sy<=0 || !GLibUtilities.INSTANCE.isValidPoint(sx, sy))
			return;

		final IPoint bl = points.get(0);

		switch(pos) {
			case WEST:
				setUnit(unit*sx);
				break;
			case SW:
				setUnit(unit*sx);
				break;
			case SOUTH:
				setUnit(unit*sy);
				break;
			case SE:
				IPoint br = getBottomRightPoint();
				setUnit(unit*sx);
				bl.setX(br.getX()-getStep()*(gridEndx-gridStartx));
				break;
			case NW:
				IPoint tl = getTopLeftPoint();
				setUnit(unit*sx);
				bl.setY(tl.getY()+getStep()*(gridEndy-gridStarty));
				break;
			case NORTH:
				tl = getTopLeftPoint();
				setUnit(unit*sy);
				bl.setY(tl.getY()+getStep()*(gridEndy-gridStarty));
				break;
			case NE:
				tl = getTopLeftPoint();
				br = getBottomRightPoint();
				setUnit(unit*sx);
				bl.setX(br.getX()-getStep()*(gridEndx-gridStartx));
				bl.setY(tl.getY()+getStep()*(gridEndy-gridStarty));
				break;
			case EAST:
				br = getBottomRightPoint();
				setUnit(unit*sx);
				bl.setX(br.getX()-getStep()*(gridEndx-gridStartx));
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
	public void setGridDots(final int gridDots) {
		if(gridDots>=0)
			this.gridDots = gridDots;
	}


	@Override
	public void setGridLabelsColour(final Color gridLabelsColour) {
		if(gridLabelsColour!=null)
			this.gridLabelsColour = gridLabelsColour;
	}



	@Override
	public void setGridWidth(final double gridWidth) {
		if(gridWidth>0 && GLibUtilities.INSTANCE.isValidCoordinate(gridWidth))
			this.gridWidth = gridWidth;
	}


	@Override
	public void setSubGridColour(final Color subGridColour) {
		if(subGridColour!=null)
			this.subGridColour = subGridColour;
	}


	@Override
	public void setSubGridDiv(final int subGridDiv) {
		if(subGridDiv>=0)
			this.subGridDiv = subGridDiv;
	}


	@Override
	public void setSubGridDots(final int subGridDots) {
		if(subGridDots>=0)
			this.subGridDots = subGridDots;
	}


	@Override
	public void setSubGridWidth(final double subGridWidth) {
		if(subGridWidth>0 && GLibUtilities.INSTANCE.isValidCoordinate(subGridWidth))
			this.subGridWidth = subGridWidth;
	}


	@Override
	public void setUnit(final double unit) {
		if(unit>0 && GLibUtilities.INSTANCE.isValidCoordinate(unit))//TODO unit may be lesser than 0.
			this.unit = unit;
	}


	@Override
	public double getStep() {
		return unit*IShape.PPC;
	}
}
