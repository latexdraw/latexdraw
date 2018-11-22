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
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of a magnetic grid.
 * @author Arnaud Blouin
 */
public class MagneticGrid extends Path {
	/** The canvas that paints the grid. */
	private final @NotNull Canvas canvas;
	private final @NotNull PreferencesService prefs;

	/**
	 * Creates the magnetic grid.
	 * @param canv The canvas in which the grid will work.
	 */
	public MagneticGrid(final @NotNull Canvas canv, final @NotNull PreferencesService prefs) {
		super();
		this.prefs = prefs;
		canvas = canv;
		setStroke(new Color(0d, 0d, 1d, 0.3d));
		setStrokeWidth(1);
		canvas.zoomProperty().addListener((observable, oldValue, newValue) -> update());
		this.prefs.gridGapProperty().addListener((observable, oldValue, newValue) -> update());
		this.prefs.gridStyleProperty().addListener((observable, oldValue, newValue) -> update());
	}


	private void update() {
		getElements().clear();

		switch(prefs.gridStyleProperty().get()) {
			case STANDARD:
				double ppc = canvas.getPPCDrawing();
				if(prefs.getUnit() == Unit.INCH) {
					ppc *= PSTricksConstants.INCH_VAL_CM;
				}

//				paintSubLines(getElements(), canvas.getLayoutBounds());
				paintMainLines(getElements(), ppc);
				break;
			case CUSTOMISED:
				paintMainLines(getElements(), prefs.gridGapProperty().get());
				break;
			case NONE:
				break;
		}
	}


	private void createLine(final List<PathElement> elts, final double x1, final double y1, final double x2, final double y2) {
		elts.add(new MoveTo(x1, y1));
		elts.add(new LineTo(x2, y2));
	}


//	private void paintSubLines(final List<PathElement> elts) {
//		double pixPerCm10 = canvas.getPPCDrawing() / 10d;
//
//		if(ScaleRuler.getUnit() == Unit.INCH) {
//			pixPerCm10 *= PSTricksConstants.INCH_VAL_CM;
//		}
//
//		if(Double.compare(pixPerCm10, 4d) > 0d) {
//			final double height = canvas.getPrefHeight();
//			final double width = canvas.getPrefWidth();
//
//			for(double i = 0d; i < width; i += pixPerCm10) {
//				createLine(elts, i, 0d, i, height);
//			}
//
//			for(double j = 0d; j < height; j += pixPerCm10) {
//				createLine(elts, 0d, j, width, j);
//			}
//		}
//	}


	private void paintMainLines(final List<PathElement> elts, final double gap) {
		final double height = canvas.getPrefHeight();
		final double width = canvas.getPrefWidth();

		for(double i = 0d; i < width; i += gap) {
			createLine(elts, i, 0d, i, height);
		}

		for(double j = 0d; j < height; j += gap) {
			createLine(elts, 0d, j, width, j);
		}
	}


	/**
	 * Transform a point to another that sticks the magnetic grid.
	 * @param pt The point to transform.
	 * @return The transformed point or a clone of the given point if there is no magnetic grid.
	 */
	public Point getTransformedPointToGrid(final @NotNull Point3D pt) {
		if(!prefs.isMagneticGrid() || prefs.gridStyleProperty().get() == GridStyle.NONE) {
			return ShapeFactory.INST.createPoint(pt.getX(), pt.getY());
		}

		final double modulo = getMagneticGridGap();
		return ShapeFactory.INST.createPoint(MathUtils.INST.getClosestModuloValue(pt.getX(), modulo), MathUtils.INST.getClosestModuloValue(pt.getY(), modulo));
	}


	/**
	 * @return The gap between the lines of the magnetic grid.
	 */
	public double getMagneticGridGap() {
		if(prefs.gridStyleProperty().get() == GridStyle.CUSTOMISED) {
			return prefs.gridGapProperty().get();
		}
		return Math.round(prefs.getUnit() == Unit.CM ? canvas.getPPCDrawing() / 10d : canvas.getPPCDrawing() * PSTricksConstants.INCH_VAL_CM / 10d);
	}
}
