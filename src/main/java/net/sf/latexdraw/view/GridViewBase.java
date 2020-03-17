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
package net.sf.latexdraw.view;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * The base class for computing background grids
 */
public abstract class GridViewBase extends Pane {
	protected final @NotNull Canvas canvas;
	private final @NotNull Color lineCol;
	private final double strokeWidth;
	private final @NotNull PreferencesService prefs;

	public GridViewBase(final @NotNull Canvas canvas, final @NotNull PreferencesService prefs, final @NotNull Color color, final double strokeWidth) {
		super();
		this.canvas = canvas;
		this.prefs = prefs;
		lineCol = color;
		this.strokeWidth = strokeWidth;
		setFocusTraversable(false);
		setMouseTransparent(true);

		this.prefs.gridGapProperty().addListener((observable, oldValue, newValue) -> update());
		this.prefs.gridStyleProperty().addListener((observable, oldValue, newValue) -> update());
		this.prefs.unitProperty().addListener((observable, oldValue, newValue) -> update());
		this.canvas.zoomProperty().addListener((observable, oldValue, newValue) -> update());
	}

	protected abstract double getUpdateWidth();

	protected abstract double getUpdateHeight();

	private double getZoomedPPC() {
		if(prefs.gridStyleProperty().get() == GridStyle.CUSTOMISED) {
			return prefs.gridGapProperty().get() * canvas.getZoom();
		}
		double zoomedPPC = Shape.PPC * canvas.getZoom();

		if(prefs.getUnit() == Unit.INCH) {
			zoomedPPC *= PSTricksConstants.INCH_VAL_CM;
		}

		return zoomedPPC;
	}

	protected double getMainStep() {
		final double zoomedPPC = getZoomedPPC();
		double step = 1d;

		while(zoomedPPC * step < canvas.getPPCDrawing() * 4) {
			step *= 2d;
		}
		return step;
	}

	protected double getSubStep() {
		return getMainStep() / 10d;
	}

	protected abstract double getLengthMain();

	protected abstract double getLengthSub();

	public void update() {
		getChildren().clear();

		if(prefs.getGridStyle() != GridStyle.NONE) {
			final double width = getUpdateWidth();
			final double height = getUpdateHeight();
			final double step = getMainStep() * canvas.getPPCDrawing();
			final double substep = getSubStep() * canvas.getPPCDrawing();
			final int incr = (int) (step / substep);
			final double lengthMain = getLengthMain();
			final double lengthSub = getLengthSub();

			produceXMainLines(width, step, lengthMain);
			produceXSubLines(width, step, substep, incr, lengthSub);
			produceYMainLines(height, step, lengthMain);
			produceYSubLines(height, step, substep, incr, lengthSub);
		}
	}

	private Line createLine(final double x1, final double y1, final double x2, final double y2) {
		final Line line = new Line(x1, y1, x2, y2);
		line.setStroke(lineCol);
		line.setStrokeWidth(strokeWidth);
		return line;
	}

	protected void produceXMainLines(final double width, final double step, final double length) {
		getChildren().addAll(IntStream
			.rangeClosed(-1, (int) (width / step))
			.parallel()
			.mapToObj(i -> createLine(i * step, 0d, i * step, length))
			.collect(Collectors.toList()));
	}

	protected void produceXSubLines(final double width, final double step, final double substep, final int incr, final double length) {
		getChildren().addAll(IntStream
			.rangeClosed(-1, (int) (width / step))
			.parallel()
			.mapToObj(j -> IntStream.range(1, incr)
				.parallel()
				.mapToObj(i -> createLine(j * step + i * substep, 0d, j * step + i * substep, length)))
			.flatMap(s -> s)
			.collect(Collectors.toList()));
	}

	protected void produceYMainLines(final double height, final double step, final double length) {
		getChildren().addAll(IntStream
			.rangeClosed(-1, (int) (height / step))
			.parallel()
			.mapToObj(i -> createLine(0d, i * step, length, i * step))
			.collect(Collectors.toList()));
	}

	protected void produceYSubLines(final double height, final double step, final double substep, final int incr, final double length) {
		getChildren().addAll(IntStream
			.rangeClosed(-1, (int) (height / step))
			.parallel()
			.mapToObj(j -> IntStream.range(1, incr)
				.parallel()
				.mapToObj(i -> createLine(0d, j * step + i * substep, length, j * step + i * substep)))
			.flatMap(s -> s)
			.collect(Collectors.toList()));
	}
}
