package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;

/**
 * Defines a model of an abstract latex grid.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
abstract class LAbstractGrid extends LPositionShape implements IStandardGrid {
	/** If true, the x label will be displayed at the south of the grid. Else at the north */
	protected boolean xLabelSouth;

	/** If true, the y label will be displayed at the west of the grid. Else at the east */
	protected boolean yLabelWest;

	/** The x-minimum values of the axes */
	protected double gridStartx;

	/** The y-minimum values of the axes */
	protected double gridStarty;

	/** The x-maximum values of the axes */
	protected double gridEndx;

	/** The y-maximum values of the axes */
	protected double gridEndy;

	/** The x-coordinate of the origin of the grid */
	protected double originx;

	/** The y-coordinate of the origin of the grid */
	protected double originy;

	/** The size of the labels. */
	protected int labelSize;


	/**
	 * Creates an abstract grid.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @param pt The position
	 */
	protected LAbstractGrid(final boolean isUniqueID, final IPoint pt) {
		super(isUniqueID, pt);

		xLabelSouth 	= true;
		yLabelWest  	= true;
		originx        	= 0;
		originy        	= 0;
		gridStartx		= 0;
		gridStarty		= 0;
		gridEndx		= 2;
		gridEndy		= 2;
		labelSize		= 10;
	}


	@Override
	public double getGridMinX() {
		return (gridEndx<gridStartx ? gridEndx : gridStartx)-originx;
	}


	@Override
	public double getGridMaxX() {
		return (gridEndx>=gridStartx ? gridEndx : gridStartx)-originx;
	}


	@Override
	public double getGridMinY() {
		return (gridEndy<gridStarty ? gridEndy : gridStarty)-originy;
	}


	@Override
	public double getGridMaxY() {
		return (gridEndy>=gridStarty ? gridEndy : gridStarty)-originy;
	}


	@Override
	public IPoint getBottomRightPoint() {
		final IPoint pos = points.get(0);
		return new LPoint(pos.getX()+getGridMaxX()*PPC, pos.getY()-getGridMinY()*PPC);
	}


	@Override
	public IPoint getTopLeftPoint() {
		final IPoint pos = points.get(0);
		return new LPoint(pos.getX()+getGridMinX()*PPC, pos.getY()-getGridMaxY()*PPC);
	}


	@Override
	public IPoint getTopRightPoint() {
		IPoint pos  = points.get(0);
		double step = getStep();

		return new LPoint(pos.getX()+step*(gridEndx-gridStartx), pos.getY()-step*(gridEndy-gridStarty));
	}



	@Override
	public void mirrorHorizontal(final IPoint origin) {
		if(origin==null) return ;

		IPoint bl = points.get(0).horizontalSymmetry(origin);
		IPoint br = getBottomRightPoint().horizontalSymmetry(origin);

		points.get(0).setPoint(br.getX()<bl.getX() ? br.getX() : bl.getX(), br.getY());
	}


	@Override
	public void mirrorVertical(final IPoint origin) {
		if(origin==null) return ;

		IPoint bl = points.get(0).verticalSymmetry(origin);
		IPoint tl = getTopLeftPoint().verticalSymmetry(origin);

		points.get(0).setPoint(bl.getX(), bl.getY()>tl.getY() ? bl.getY() : tl.getY());
	}



	@Override
	public void setLabelsSize(final int labelsSize) {
		if(labelsSize>0)
			labelSize = labelsSize;
	}


	@Override
	public double getGridEndX() {
		return gridEndx;
	}


	@Override
	public double getGridEndY() {
		return gridEndy;
	}

	@Override
	public double getGridStartX() {
		return gridStartx;
	}


	@Override
	public double getGridStartY() {
		return gridStarty;
	}



	@Override
	public int getLabelsSize() {
		return labelSize;
	}


	@Override
	public double getOriginX() {
		return originx;
	}

	@Override
	public double getOriginY() {
		return originy;
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
	public void setGridEnd(final double x, final double y) {
		setGridEndX(x);
		setGridEndY(y);
	}


	@Override
	public void setGridEndX(final double x) {
		if(x>=gridStartx && GLibUtilities.INSTANCE.isValidCoordinate(x))
			gridEndx = x;
	}


	@Override
	public void setGridEndY(final double y) {
		if(y>=gridStarty && GLibUtilities.INSTANCE.isValidCoordinate(y))
			gridEndy = y;
	}


	@Override
	public void setGridStart(final double x, final double y) {
		setGridStartX(x);
		setGridStartY(y);
	}


	@Override
	public void setGridStartX(final double x) {
		if(x<=gridEndx && GLibUtilities.INSTANCE.isValidCoordinate(x))
			gridStartx = x;
	}


	@Override
	public void setGridStartY(final double y) {
		if(y<=gridEndy && GLibUtilities.INSTANCE.isValidCoordinate(y))
			gridStarty = y;
	}


	@Override
	public void setOrigin(final double x, final double y) {
		setOriginX(x);
		setOriginY(y);
	}


	@Override
	public void setOriginX(final double x) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(x))
			originx = x;
	}


	@Override
	public void setOriginY(final double y) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(y))
			originy = y;
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
	public void copy(final IShape s) {
		super.copy(s);

		if(s instanceof IStandardGrid) {
			IStandardGrid grid = (IStandardGrid) s;

			gridEndx 	= grid.getGridEndX();
			gridEndy 	= grid.getGridEndY();
			gridStartx 	= grid.getGridStartX();
			gridStarty 	= grid.getGridStartY();
			xLabelSouth = grid.isXLabelSouth();
			yLabelWest 	= grid.isYLabelWest();
			originx 	= grid.getOriginX();
			originy 	= grid.getOriginY();
			setLabelsSize(grid.getLabelsSize());
		}
	}


	@Override
	public IPoint getGridStart() {
		return new LPoint(gridStartx, gridStarty);
	}


	@Override
	public IPoint getGridEnd() {
		return new LPoint(gridEndx, gridEndy);
	}
}
