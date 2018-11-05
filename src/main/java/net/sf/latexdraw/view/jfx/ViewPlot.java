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

import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.view.PlotViewComputation;

/**
 * The JFX view of a plot.
 * @author Arnaud Blouin
 */
public class ViewPlot extends ViewPositionShape<Plot> implements PlotViewComputation {
	private ViewPolyline lineView;
	private ViewPolygon polygonView;
	private ViewBezierCurve curveView;
	private List<ViewDot> dotsView;
	private final PathElementProducer pathProducer;
	private final ChangeListener<Object> updatePath = (observable, oldValue, newValue) -> Platform.runLater(() -> updatePath());

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPlot(final Plot sh, final PathElementProducer pathProducer) {
		super(sh);

		this.pathProducer = pathProducer;
		model.plotEquationProperty().addListener(updatePath);
		model.dotDiametreProperty().addListener(updatePath);
		model.dotStyleProperty().addListener(updatePath);
		model.nbPlottedPointsProperty().addListener(updatePath);
		model.plotMaxXProperty().addListener(updatePath);
		model.plotMinXProperty().addListener(updatePath);
		model.plotStyleProperty().addListener(updatePath);
		model.polarProperty().addListener(updatePath);
		model.xScaleProperty().addListener(updatePath);
		model.yScaleProperty().addListener(updatePath);

		updatePath();
	}

	private final void updatePath() {
		final double minX = model.getPlotMinX();
		final double maxX = model.getPlotMaxX();
		final double step = model.getPlottingStep();

		getChildren().stream().filter(node -> node instanceof ViewShape<?>).forEach(vs -> ((ViewShape<?>) vs).flush());
		getChildren().clear();

		switch(model.getPlotStyle()) {
			case LINE:
				updateLine(minX, maxX, step);
				break;
			case CURVE:
				updateCurve(minX, maxX, step);
				break;
			case ECURVE:
				updateCurve(minX + step, maxX - step, step);
				break;
			case CCURVE:
				updateCurve(minX, maxX, step);
				break;
			case DOTS:
				updatePoints(minX, maxX, step);
				break;
			case POLYGON:
				updatePolygon(minX, maxX, step);
				break;
		}
	}


	private void updatePoints(final double minX, final double maxX, final double step) {
		flushDots();
		dotsView = updatePoints(model, 0d, 0d, minX, maxX, step).parallelStream().map(dot -> {
			final ViewDot viewDot = new ViewDot(dot, pathProducer);
			viewDot.setUserData(this);
			return viewDot;
		}).collect(Collectors.toList());
		getChildren().addAll(dotsView);
	}

	private void updatePolygon(final double minX, final double maxX, final double step) {
		flushPolygon();
		polygonView = new ViewPolygon(updatePolygon(model, 0d, 0d, minX, maxX, step), pathProducer);
		polygonView.setUserData(this);
		getChildren().add(polygonView);
	}


	private void updateLine(final double minX, final double maxX, final double step) {
		flushLine();
		lineView = new ViewPolyline(updateLine(model, 0d, 0d, minX, maxX, step), pathProducer);
		lineView.setUserData(this);
		getChildren().add(lineView);
	}


	private void updateCurve(final double minX, final double maxX, final double step) {
		flushCurve();
		curveView = new ViewBezierCurve(updateCurve(model, 0d, 0d, minX, maxX, step), pathProducer);
		curveView.setUserData(this);
		getChildren().add(curveView);
	}

	@Override
	public void flush() {
		super.flush();

		model.plotEquationProperty().removeListener(updatePath);
		model.dotDiametreProperty().removeListener(updatePath);
		model.dotStyleProperty().removeListener(updatePath);
		model.nbPlottedPointsProperty().removeListener(updatePath);
		model.plotMaxXProperty().removeListener(updatePath);
		model.plotMinXProperty().removeListener(updatePath);
		model.plotStyleProperty().removeListener(updatePath);
		model.polarProperty().removeListener(updatePath);
		model.xScaleProperty().removeListener(updatePath);
		model.yScaleProperty().removeListener(updatePath);

		flushLine();
		flushCurve();
		flushDots();
		flushPolygon();
	}

	private void flushLine() {
		if(lineView != null) {
			lineView.flush();
		}
	}

	private void flushCurve() {
		if(curveView != null) {
			curveView.flush();
		}
	}

	private void flushPolygon() {
		if(polygonView != null) {
			polygonView.flush();
		}
	}

	private void flushDots() {
		if(dotsView != null) {
			dotsView.forEach(v -> v.flush());
			dotsView.clear();
		}
	}
}
