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
package net.sf.latexdraw.view.jfx;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.GenericAxes;

/**
 * The JFX view of a axes.
 * @author Arnaud Blouin
 */
public class ViewAxes extends ViewStdGrid<Axes> implements GenericAxes<Text> {
	protected final Path framePath;
	protected final ViewPolyline axesHoriz;
	protected final ViewPolyline axesVert;
	protected final Path pathTicks;
	private final ChangeListener<Object> labelUpdate;
	private final ChangeListener<Object> labelTicksUpdate;
	private final ChangeListener<Object> fullAxesUpdate;
	private final ChangeListener<Object> ticksUpdate;
	private final ChangeListener<Object> axesUpdate;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewAxes(final Axes sh, final PathElementProducer pathProducer) {
		super(sh, pathProducer);
		labelUpdate = (o, formerv, newv) -> updatePath(false, false, true);
		labelTicksUpdate = (o, formerv, newv) -> updatePath(false, true, true);
		fullAxesUpdate = (o, formerv, newv) -> updatePath(true, true, true);
		ticksUpdate = (o, formerv, newv) -> updatePath(false, true, false);
		axesUpdate = (o, formerv, newv) -> updatePath(true, false, false);

		framePath = new Path();
		pathTicks = new Path();
		axesHoriz = new ViewPolyline(ShapeFactory.INST.createPolyline(List.of(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint())), pathProducer);
		axesVert = new ViewPolyline(ShapeFactory.INST.createPolyline(List.of(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint())), pathProducer);

		axesHoriz.getModel().getArrowAt(0).bindFrom(model.getArrowAt(0));
		axesHoriz.getModel().getArrowAt(1).bindFrom(model.getArrowAt(2));
		axesVert.getModel().getArrowAt(0).bindFrom(model.getArrowAt(1));
		axesVert.getModel().getArrowAt(1).bindFrom(model.getArrowAt(3));
		axesHoriz.getModel().getArrowAt(0).styleProperty().bind(model.getArrowAt(0).styleProperty());
		axesHoriz.getModel().getArrowAt(1).styleProperty().
			bind(Bindings.createObjectBinding(() -> model.getArrowAt(2).getArrowStyle().getOppositeArrowStyle(), model.getArrowAt(2).styleProperty()));
		axesVert.getModel().getArrowAt(0).styleProperty().
			bind(Bindings.createObjectBinding(() -> model.getArrowAt(1).getArrowStyle().getOppositeArrowStyle(), model.getArrowAt(1).styleProperty()));
		axesVert.getModel().getArrowAt(1).styleProperty().bind(model.getArrowAt(3).styleProperty());

		axesHoriz.getModel().getPtAt(0).xProperty().bind(model.gridStartXProperty().multiply(Shape.PPC));
		axesHoriz.getModel().getPtAt(1).xProperty().bind(model.gridEndXProperty().multiply(Shape.PPC));
		axesVert.getModel().getPtAt(0).yProperty().bind(model.gridStartYProperty().negate().multiply(Shape.PPC));
		axesVert.getModel().getPtAt(1).yProperty().bind(model.gridEndYProperty().negate().multiply(Shape.PPC));

		bindsAxesParametersToAxeLines(axesHoriz);
		bindsAxesParametersToAxeLines(axesVert);

		getChildren().add(framePath);
		getChildren().add(pathTicks);
		getChildren().add(axesHoriz);
		getChildren().add(axesVert);

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

		updatePath(true, true, true);
	}

	private final void bindsAxesParametersToAxeLines(final ViewPolyline line) {
		line.getModel().thicknessProperty().bind(model.thicknessProperty());
		line.getModel().lineColourProperty().bind(model.lineColourProperty());
		line.getModel().linestyleProperty().bind(model.linestyleProperty());
		line.visibleProperty().bind(model.axesStyleProperty().isEqualTo(AxesStyle.AXES));
		line.setUserData(this);
	}


	private final void updatePathFrame() {
		final double endx = model.getGridEndX();
		final double endy = model.getGridEndY();

		if(endx > 0d || endy > 0d) {
			final double y1 = endy > 0d ? -endy * Shape.PPC : 0d;
			final double x2 = endx > 0d ? +endx * Shape.PPC : 0d;

			framePath.getElements().add(pathProducer.createMoveTo(0d, y1));
			framePath.getElements().add(pathProducer.createLineTo(x2, y1));
			framePath.getElements().add(pathProducer.createLineTo(x2, 0d));
			framePath.getElements().add(pathProducer.createLineTo(0d, 0d));
			framePath.getElements().add(pathProducer.createClosePath());
		}
	}


	private final void updatePath(final boolean axes, final boolean ticks, final boolean texts) {
		if(axes) {
			framePath.getElements().clear();
			if(model.getAxesStyle() == AxesStyle.FRAME) {
				updatePathFrame();
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
		framePath.getElements().clear();
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

	@Override
	public void createPathTicksMoveTo(final double x, final double y) {
		pathTicks.getElements().add(pathProducer.createMoveTo(x, y));
	}

	@Override
	public void createPathTicksLineTo(final double x, final double y) {
		pathTicks.getElements().add(pathProducer.createLineTo(x, y));
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
