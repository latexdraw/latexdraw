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
package net.sf.latexdraw.view.jfx;

import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.view.GenericAxes;

/**
 * The JFX view of a axes.
 * @author Arnaud Blouin
 */
public class ViewAxes extends ViewStdGrid<IAxes> implements GenericAxes<Text> {
	private final Path mainAxes;
	private final Path pathTicks;
//	private final ViewArrowableTrait viewArrows;
	private final ChangeListener<Object> labelUpdate;
	private final ChangeListener<Object> labelTicksUpdate;
	private final ChangeListener<Object> fullAxesUpdate;
	private final ChangeListener<Object> ticksUpdate;
	private final ChangeListener<Object> axesUpdate;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewAxes(final IAxes sh) {
		super(sh);
		labelUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(false, false, true, false));
		labelTicksUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(false, true, true, false));
		fullAxesUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(true, true, true, false));
		ticksUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(false, true, false, false));
		axesUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(true, false, false, false));

		mainAxes = new Path();
		pathTicks = new Path();
//		viewArrows = new ViewArrowableTrait(this);

		getChildren().add(mainAxes);
		getChildren().add(pathTicks);
//		getChildren().add(viewArrows);

		model.labelsSizeProperty().addListener(labelUpdate);
		model.gridEndXProperty().addListener(fullAxesUpdate);
		model.gridEndYProperty().addListener(fullAxesUpdate);
		model.gridStartXProperty().addListener(fullAxesUpdate);
		model.gridStartYProperty().addListener(fullAxesUpdate);
		model.originXProperty().addListener(labelUpdate);
		model.originYProperty().addListener(labelUpdate);
		model.incrementXProperty().addListener(labelUpdate);
		model.incrementYProperty().addListener(labelUpdate);
		model.distLabelsXProperty().addListener(labelTicksUpdate);
		model.distLabelsYProperty().addListener(labelTicksUpdate);
		model.labelsDisplayedProperty().addListener(labelUpdate);
		model.showOriginProperty().addListener(labelUpdate);
		model.ticksDisplayedProperty().addListener(ticksUpdate);
		model.ticksStyleProperty().addListener(ticksUpdate);
		model.ticksSizeProperty().addListener(ticksUpdate);
		model.axesStyleProperty().addListener(axesUpdate);

		updatePath(true, true, true, true);
	}


	private void updatePathFrame() {
		final double endx = model.getGridEndX();
		final double endy = model.getGridEndY();

		if(endx > 0d || endy > 0d) {
			final double y1 = endy > 0d ? -endy * IShape.PPC : 0d;
			final double x2 = endx > 0d ? +endx * IShape.PPC : 0d;

			mainAxes.getElements().add(ViewFactory.INSTANCE.createMoveTo(0d, y1));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(x2, y1));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(x2, 0d));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(0d, 0d));
			mainAxes.getElements().add(ViewFactory.INSTANCE.createClosePath());
		}
	}


	private void updatePathAxes() {
		final IArrow arr0 = model.getArrowAt(1);
		final IArrow arr1 = model.getArrowAt(3);
		final double arr0Reduction = arr0.getArrowStyle().needsLineReduction() ? arr0.getArrowShapedWidth() : 0d;
		final double arr1Reduction = arr1.getArrowStyle().needsLineReduction() ? arr1.getArrowShapedWidth() : 0d;

		mainAxes.getElements().add(ViewFactory.INSTANCE.createMoveTo(model.getGridStartX() * IShape.PPC + arr0Reduction, 0d));
		mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(model.getGridEndX() * IShape.PPC - arr1Reduction, 0d));
		mainAxes.getElements().add(ViewFactory.INSTANCE.createMoveTo(0d, -model.getGridStartY() * IShape.PPC - arr0Reduction));
		mainAxes.getElements().add(ViewFactory.INSTANCE.createLineTo(0d, -model.getGridEndY() * IShape.PPC + arr1Reduction));
	}


	public void updatePath(final boolean axes, final boolean ticks, final boolean texts, final boolean arrows) {
		final AxesStyle axesStyle = model.getAxesStyle();

		if(arrows || axes) {
//			viewArrows.update(model.getAxesStyle().supportsArrows());
		}

		if(axes) {
			mainAxes.getElements().clear();
			switch(axesStyle) {
				case AXES:
					updatePathAxes();
					break;
				case FRAME:
					updatePathFrame();
					break;
				case NONE:
					break;
			}
		}

		if(ticks) {
			pathTicks.getElements().clear();
			updatePathTicks();
		}

		if(texts) {
			cleanLabels();
			if(model.getLabelsDisplayed() != PlottingStyle.NONE) {
				labels.setDisable(false);
				updatePathLabels();
			}else {
				labels.setDisable(true);
			}
		}
	}


	@Override
	public void flush() {
		mainAxes.getElements().clear();
		pathTicks.getElements().clear();
		model.labelsSizeProperty().removeListener(labelUpdate);
		model.gridEndXProperty().removeListener(fullAxesUpdate);
		model.gridEndYProperty().removeListener(fullAxesUpdate);
		model.gridStartXProperty().removeListener(fullAxesUpdate);
		model.gridStartYProperty().removeListener(fullAxesUpdate);
		model.originXProperty().removeListener(labelUpdate);
		model.originYProperty().removeListener(labelUpdate);
		model.incrementXProperty().removeListener(labelUpdate);
		model.incrementYProperty().removeListener(labelUpdate);
		model.distLabelsXProperty().removeListener(labelTicksUpdate);
		model.distLabelsYProperty().removeListener(labelTicksUpdate);
		model.labelsDisplayedProperty().removeListener(labelUpdate);
		model.showOriginProperty().removeListener(labelUpdate);
		model.ticksDisplayedProperty().removeListener(ticksUpdate);
		model.ticksStyleProperty().removeListener(ticksUpdate);
		model.ticksSizeProperty().removeListener(ticksUpdate);
		model.axesStyleProperty().removeListener(axesUpdate);
		super.flush();
	}

	/**
	 * @return The main axes path.
	 */
	public Path getMainAxes() {
		return mainAxes;
	}

	/**
	 * @return The ticks path.
	 */
	public Path getPathTicks() {
		return pathTicks;
	}

	@Override
	public void createPathTicksMoveTo(final double x, final double y) {
		pathTicks.getElements().add(ViewFactory.INSTANCE.createMoveTo(x, y));
	}

	@Override
	public void createPathTicksLineTo(final double x, final double y) {
		pathTicks.getElements().add(ViewFactory.INSTANCE.createLineTo(x, y));
	}

	@Override
	public void disablePathTicks(final boolean disable) {
		pathTicks.setDisable(disable);
	}

	@Override
	public void setPathTicksFill(final Color color) {
		pathTicks.setFill(color);
	}
}
