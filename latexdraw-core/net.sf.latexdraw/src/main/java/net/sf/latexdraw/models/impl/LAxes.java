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

import java.util.Arrays;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;

/**
 * An implementation of axes.
 * @author Arnaud Blouin
 */
class LAxes extends LAbstractGrid implements IAxes, LArrowableShape {
	private final List<IArrow> arrows;
	/** The increment of X axe (Dx in PST). */
	private final DoubleProperty incrementX;
	/** The increment of Y axe (Dy in PST). */
	private final DoubleProperty incrementY;
	/** The distance between each label of the X axe; if 0, the default value will be used (in cm). */
	private final DoubleProperty distLabelsX;
	/** The distance between each label of the Y axe; if 0, the default value will be used (in cm). */
	private final DoubleProperty distLabelsY;
	/** Define which labels must be displayed. */
	private final ObjectProperty<PlottingStyle> labelsDisplayed;
	/** Define the origin must be shown. */
	private final BooleanProperty showOrigin;
	/** Define how the ticks must be shown. */
	private final ObjectProperty<PlottingStyle> ticksDisplayed;
	/** Define the style of the ticks. */
	private final ObjectProperty<TicksStyle> ticksStyle;
	/** The size of the ticks. */
	private final DoubleProperty ticksSize;
	/** The style of the axes. */
	private final ObjectProperty<AxesStyle> axesStyle;

	LAxes(final IPoint pt) {
		super(pt);
		arrows = Arrays.asList(
			// The left arrow of the X-axis.
			ShapeFactory.INST.createArrow(this),
			// The bottom arrow of the Y-axis.
			ShapeFactory.INST.createArrow(this),
			// The right arrow of the X-axis.
			ShapeFactory.INST.createArrow(this),
			// The top arrow of the Y-axis.
			ShapeFactory.INST.createArrow(this));
		incrementX = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_DX);
		incrementY = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_DY);
		distLabelsX = new SimpleDoubleProperty(1d);
		distLabelsY = new SimpleDoubleProperty(1d);
		labelsDisplayed = new SimpleObjectProperty<>(PlottingStyle.ALL);
		showOrigin = new SimpleBooleanProperty(true);
		ticksDisplayed = new SimpleObjectProperty<>(PlottingStyle.ALL);
		ticksStyle = new SimpleObjectProperty<>(TicksStyle.FULL);
		ticksSize = new SimpleDoubleProperty(PSTricksConstants.DEFAULT_TICKS_SIZE * IShape.PPC);
		axesStyle = new SimpleObjectProperty<>(AxesStyle.AXES);
	}


	@Override
	public void copy(final IShape s) {
		super.copy(s);
		LArrowableShape.super.copy(s);

		if(s instanceof IAxesProp) {
			final IAxesProp axes = (IAxesProp) s;
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
	public IAxes duplicate() {
		final IAxes axes = ShapeFactory.INST.createAxes(getPosition());
		axes.copy(this);
		return axes;
	}

	@Override
	public void setArrowStyle(final ArrowStyle style, final int position) {
		final int pos = position == -1 ? arrows.size() - 1 : position;

		if(style != null && pos < 4 && pos >=0) {
			LArrowableShape.super.setArrowStyle(style, position);
			switch(pos) {
				case 0:
					arrows.get(2).setArrowStyle(style);
					break;
				case 1:
					arrows.get(3).setArrowStyle(style);
					break;
				case 2:
					arrows.get(0).setArrowStyle(style);
					break;
				case 3:
					arrows.get(1).setArrowStyle(style);
					break;
			}
		}
	}

	@Override
	public IPoint getPosition() {
		return getPtAt(0);
	}

	@Override
	public ILine getArrowLine(final int index) {
		// For the X-axis
		if(index == 1 || index == 3) {
			return getArrowLineX(index == 1);
		}
		// For the Y-axis.
		if(index == 0 || index == 2) {
			return getArrowLineY(index == 2);
		}
		return null;
	}


	/**
	 * @return The line of the Y-axis.
	 */
	private ILine getArrowLineY(final boolean topY) {
		final IPoint pos = getPosition();
		final IPoint p2 = ShapeFactory.INST.createPoint(pos.getX(), pos.getY() - getGridEndY() * IShape.PPC);
		final IPoint p1 = ShapeFactory.INST.createPoint(pos.getX(), pos.getY() - getGridStartY() * IShape.PPC);

		if(topY) {
			return ShapeFactory.INST.createLine(p2, p1);
		}
		return ShapeFactory.INST.createLine(p1, p2);
	}


	/**
	 * @return The line of the X-axis.
	 */
	private ILine getArrowLineX(final boolean leftX) {
		final IPoint pos = getPosition();
		final IPoint p2 = ShapeFactory.INST.createPoint(pos.getX() + getGridEndX() * IShape.PPC, pos.getY());
		final IPoint p1 = ShapeFactory.INST.createPoint(pos.getX() + getGridStartX() * IShape.PPC, pos.getY());

		if(leftX) {
			return ShapeFactory.INST.createLine(p1, p2);
		}
		return ShapeFactory.INST.createLine(p2, p1);
	}

	@Override
	public AxesStyle getAxesStyle() {
		return axesStyle.getValue();
	}

	@Override
	public double getDistLabelsX() {
		return distLabelsX.get();
	}

	@Override
	public double getDistLabelsY() {
		return distLabelsY.get();
	}

	@Override
	public PlottingStyle getLabelsDisplayed() {
		return labelsDisplayed.getValue();
	}

	@Override
	public PlottingStyle getTicksDisplayed() {
		return ticksDisplayed.getValue();
	}

	@Override
	public double getTicksSize() {
		return ticksSize.get();
	}

	@Override
	public TicksStyle getTicksStyle() {
		return ticksStyle.getValue();
	}

	@Override
	public boolean isShowOrigin() {
		return showOrigin.get();
	}

	@Override
	public void setAxesStyle(final AxesStyle style) {
		if(style != null) {
			axesStyle.set(style);
		}
	}

	@Override
	public void setDistLabelsX(final double distX) {
		if(distX > 0d && MathUtils.INST.isValidCoord(distX)) {
			distLabelsX.set(distX);
		}
	}

	@Override
	public void setDistLabelsY(final double distY) {
		if(distY > 0d && MathUtils.INST.isValidCoord(distY)) {
			distLabelsY.set(distY);
		}
	}

	@Override
	public void setIncrementX(final double incr) {
		if(incr > 0d && MathUtils.INST.isValidCoord(incr)) {
			incrementX.set(incr);
		}
	}


	@Override
	public void setIncrementY(final double incr) {
		if(incr > 0d && MathUtils.INST.isValidCoord(incr)) {
			incrementY.set(incr);
		}
	}

	@Override
	public void setLabelsDisplayed(final PlottingStyle style) {
		if(style != null) {
			labelsDisplayed.set(style);
		}
	}

	@Override
	public void setShowOrigin(final boolean show) {
		showOrigin.set(show);
	}

	@Override
	public void setTicksDisplayed(final PlottingStyle style) {
		if(style != null) {
			ticksDisplayed.set(style);
		}
	}

	@Override
	public void setTicksSize(final double ticks) {
		if(ticks > 0d && MathUtils.INST.isValidCoord(ticks)) {
			ticksSize.set(ticks);
		}
	}

	@Override
	public void setTicksStyle(final TicksStyle style) {
		if(style != null) {
			ticksStyle.set(style);
		}
	}

	@Override
	public double getStep() {
		return IShape.PPC;
	}

	@Override
	public boolean isLineStylable() {
		return true;
	}

	@Override
	public boolean isThicknessable() {
		return true;
	}

	@Override
	public double getIncrementX() {
		return incrementX.get();
	}

	@Override
	public double getIncrementY() {
		return incrementY.get();
	}

	@Override
	public IPoint getIncrement() {
		return ShapeFactory.INST.createPoint(getIncrementX(), getIncrementY());
	}

	@Override
	public void setIncrement(final IPoint increment) {
		if(increment != null) {
			setIncrementX(increment.getX());
			setIncrementY(increment.getY());
		}
	}

	@Override
	public IPoint getDistLabels() {
		return ShapeFactory.INST.createPoint(getDistLabelsX(), getDistLabelsY());
	}

	@Override
	public void setDistLabels(final IPoint distLabels) {
		if(distLabels != null) {
			setDistLabelsX(distLabels.getX());
			setDistLabelsY(distLabels.getY());
		}
	}

	@Override
	public List<IArrow> getArrows() {
		return arrows;
	}

	@Override
	public DoubleProperty incrementXProperty() {
		return incrementX;
	}

	@Override
	public DoubleProperty incrementYProperty() {
		return incrementY;
	}

	@Override
	public DoubleProperty distLabelsXProperty() {
		return distLabelsX;
	}

	@Override
	public DoubleProperty distLabelsYProperty() {
		return distLabelsY;
	}

	@Override
	public ObjectProperty<PlottingStyle> labelsDisplayedProperty() {
		return labelsDisplayed;
	}

	@Override
	public BooleanProperty showOriginProperty() {
		return showOrigin;
	}

	@Override
	public ObjectProperty<PlottingStyle> ticksDisplayedProperty() {
		return ticksDisplayed;
	}

	@Override
	public ObjectProperty<TicksStyle> ticksStyleProperty() {
		return ticksStyle;
	}

	@Override
	public DoubleProperty ticksSizeProperty() {
		return ticksSize;
	}

	@Override
	public ObjectProperty<AxesStyle> axesStyleProperty() {
		return axesStyle;
	}
}
