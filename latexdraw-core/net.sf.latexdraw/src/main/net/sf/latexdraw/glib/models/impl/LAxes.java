package net.sf.latexdraw.glib.models.impl;

import java.util.ArrayList;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

/**
 * Defines a model of axes.<br>
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
 * 02/13/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class LAxes extends LAbstractGrid implements IAxes {
	/** The increment of X axe (Dx in PST). */
	protected double incrementX;

	/** The increment of Y axe (Dy in PST). */
	protected double incrementY;

	/** The distance between each label of the X axe; if 0, the default value will be used (in cm). */
	protected double distLabelsX;

	/** The distance between each label of the Y axe; if 0, the default value will be used (in cm). */
	protected double distLabelsY;

	/** Define which labels must be displayed. */
	protected PlottingStyle labelsDisplayed;

	/** Define the origin must be shown. */
	protected boolean showOrigin;

	/** Define how the ticks must be shown. */
	protected PlottingStyle ticksDisplayed;

	/** Define the style of the ticks. */
	protected TicksStyle ticksStyle;

	/** The size of the ticks. */
	protected double ticksSize;

	/** The style of the axes. */
	protected AxesStyle axesStyle;


	/**
	 * Creates axes with default values.
	 * @param pt The bottom left position of the axes.
	 * @param isUniqueID True: the model will have a unique ID.
	 */
	protected LAxes(final boolean isUniqueID, final IPoint pt) {
		super(isUniqueID, pt);

		arrows			= new ArrayList<>();
		showOrigin		= true;
		ticksSize		= PSTricksConstants.DEFAULT_TICKS_SIZE*PPC;
		ticksDisplayed	= PlottingStyle.ALL;
		ticksStyle		= TicksStyle.FULL;
		labelsDisplayed = PlottingStyle.ALL;
		axesStyle		= AxesStyle.AXES;
		incrementX		= PSTricksConstants.DEFAULT_DX;
		incrementY		= PSTricksConstants.DEFAULT_DY;
		distLabelsX		= 1.;
		distLabelsY		= 1.;
		// The first arrow is for the bottom of the Y-axis.
		arrows.add(new LArrow(this));
		// The second arrow is for the top of the Y-axis.
		arrows.add(new LArrow(this));
		// The third arrow is for the left of the X-axis.
		arrows.add(new LArrow(this));
		// The fourth arrow is for the right of the X-axis.
		arrows.add(new LArrow(this));
	}


	@Override
	public void copy(final IShape s) {
		super.copy(s);

		if(s instanceof IAxes) {
			final IAxes axes = (IAxes) s;

			setTicksDisplayed(axes.getTicksDisplayed());
			setTicksSize(axes.getTicksSize());
			setTicksStyle(axes.getTicksStyle());
			setAxesStyle(axes.getAxesStyle());
			setShowOrigin(axes.isShowOrigin());
			setDistLabelsX(axes.getDistLabelsX());
			setDistLabelsY(axes.getDistLabelsY());
			setIncrementX(axes.getIncrementX());
			setIncrementY(axes.getIncrementY());
			setLabelsDisplayed(axes.getLabelsDisplayed());
		}
	}


	@Override
	public void setArrowStyle(final ArrowStyle style, final int position) {
		final IArrow arr1 = getArrowAt(position);

		if(style!=null && arr1!=null) {
			super.setArrowStyle(style, position);
			 arrows.get((position+2)%4).setArrowStyle(style);
		}
	}


	@Override
	public ILine getArrowLine(final IArrow arrow) {
		ILine line;

		// For the X-axis
		if(arrow==arrows.get(2) || arrow==arrows.get(3))
			line = getArrowLineX(arrow==arrows.get(2));
		else
			// For the Y-axis.
			if(arrow==arrows.get(0) || arrow==arrows.get(1))
				line = getArrowLineY(arrow==arrows.get(1));
			else
				// If the given arrow is null or is not an arrow of the shape.
				line = null;

		return line;
	}


	/**
	 * @return The line of the Y-axis.
	 */
	private ILine getArrowLineY(final boolean topY) {
		final IPoint pos = getPosition();
		final IPoint p2 = new LPoint(pos.getX(), pos.getY()-gridEndy*PPC);
		final IPoint p1 = new LPoint(pos.getX(), pos.getY()-gridStarty*PPC);

		return topY ? new LLine(p2, p1) : new LLine(p1, p2);
	}


	/**
	 * @return The line of the X-axis.
	 */
	private ILine getArrowLineX(final boolean leftX) {
		final IPoint pos = getPosition();
		final IPoint p2 = new LPoint(pos.getX()+gridEndx*PPC, pos.getY());
		final IPoint p1 = new LPoint(pos.getX()+gridStartx*PPC, pos.getY());

		return leftX ? new LLine(p1, p2) : new LLine(p2, p1);
	}


	@Override
	public IAxes duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IAxes ? (IAxes)sh : null;
	}


	@Override
	public AxesStyle getAxesStyle() {
		return axesStyle;
	}


	@Override
	public double getDistLabelsX() {
		return distLabelsX;
	}


	@Override
	public double getDistLabelsY() {
		return distLabelsY;
	}


	@Override
	public PlottingStyle getLabelsDisplayed() {
		return labelsDisplayed;
	}


	@Override
	public PlottingStyle getTicksDisplayed() {
		return ticksDisplayed;
	}


	@Override
	public double getTicksSize() {
		return ticksSize;
	}


	@Override
	public TicksStyle getTicksStyle() {
		return ticksStyle;
	}


	@Override
	public boolean isShowOrigin() {
		return showOrigin;
	}


	@Override
	public void setAxesStyle(final AxesStyle axesStyle) {
		if(axesStyle!=null)
			this.axesStyle = axesStyle;
	}


	@Override
	public void setDistLabelsX(final double distLabelsX) {
		if(distLabelsX>0 && GLibUtilities.INSTANCE.isValidCoordinate(distLabelsX))
				this.distLabelsX = distLabelsX;
	}


	@Override
	public void setDistLabelsY(final double distLabelsY) {
		if(distLabelsY>0 && GLibUtilities.INSTANCE.isValidCoordinate(distLabelsY))
			this.distLabelsY = distLabelsY;
	}


	@Override
	public void setIncrementX(final double increment) {
		if(increment>0 && GLibUtilities.INSTANCE.isValidCoordinate(increment))
			this.incrementX = increment;
	}


	@Override
	public void setIncrementY(final double increment) {
		if(increment>0 && GLibUtilities.INSTANCE.isValidCoordinate(increment))
			this.incrementY = increment;
	}


	@Override
	public void setLabelsDisplayed(final PlottingStyle labelsDisplayed) {
		if(labelsDisplayed!=null)
			this.labelsDisplayed = labelsDisplayed;
	}


	@Override
	public void setShowOrigin(final boolean showOrigin) {
		this.showOrigin = showOrigin;
	}


	@Override
	public void setTicksDisplayed(final PlottingStyle ticksDisplayed) {
		if(ticksDisplayed!=null)
			this.ticksDisplayed = ticksDisplayed;
	}


	@Override
	public void setTicksSize(final double ticksSize) {
		if(ticksSize>0 && GLibUtilities.INSTANCE.isValidCoordinate(ticksSize))
			this.ticksSize = ticksSize;
	}


	@Override
	public void setTicksStyle(final TicksStyle ticksStyle) {
		if(ticksStyle!=null)
			this.ticksStyle = ticksStyle;
	}


	@Override
	public double getStep() {
		return IShape.PPC;
	}


	@Override
	public boolean isArrowable() {
		return true;
	}


	@Override
	public boolean isLineStylable() {
		return true;
	}


	@Override
	public boolean isThicknessable() {
		return true;
	}


//	@Override
//	public void setLabelsSize(final int labelsSize) {
//		if(labelsSize==TestSize.FOOTNOTE.getSize() ||
//			labelsSize==TestSize.HUGE1.getSize() ||
//			labelsSize==TestSize.HUGE2.getSize() ||
//			labelsSize==TestSize.LARGE1.getSize() ||
//			labelsSize==TestSize.LARGE2.getSize() ||
//			labelsSize==TestSize.LARGE3.getSize() ||
//			labelsSize==TestSize.NORMAL.getSize() ||
//			labelsSize==TestSize.SCRIPT.getSize() ||
//			labelsSize==TestSize.SMALL.getSize() ||
//			labelsSize==TestSize.TINY.getSize())
//	}


	@Override
	public double getIncrementX() {
		return incrementX;
	}

	@Override
	public double getIncrementY() {
		return incrementY;
	}

	@Override
	public IPoint getIncrement() {
		return new LPoint(incrementX, incrementY);
	}

	@Override
	public void setIncrement(final IPoint increment) {
		if(increment!=null) {
			setIncrementX(increment.getX());
			setIncrementY(increment.getY());
		}
	}


	@Override
	public IPoint getDistLabels() {
		return new LPoint(distLabelsX, distLabelsY);
	}


	@Override
	public void setDistLabels(final IPoint distLabels) {
		if(distLabels!=null) {
			setDistLabelsX(distLabels.getX());
			setDistLabelsY(distLabels.getY());
		}
	}
}
