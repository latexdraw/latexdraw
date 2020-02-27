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

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.GridViewBase;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of a magnetic grid.
 * @author Arnaud Blouin
 */
public class MagneticGrid extends GridViewBase {
	private final @NotNull PreferencesService prefs;

	/**
	 * Creates the magnetic grid.
	 * @param canv The canvas in which the grid will work.
	 */
	public MagneticGrid(final @NotNull Canvas canv, final @NotNull PreferencesService prefs) {
		super(canv, prefs, new Color(0d, 0d, 1d, 0.3d), 0.4);
		this.prefs = prefs;
		prefWidthProperty().bind(canvas.prefWidthProperty());
		prefHeightProperty().bind(canvas.prefHeightProperty());
	}

	@Override
	protected double getUpdateWidth() {
		return getPrefWidth();
	}

	@Override
	protected double getUpdateHeight() {
		return getPrefHeight();
	}

	@Override
	protected double getLengthSub() {
		return getLengthMain();
	}

	@Override
	protected double getLengthMain() {
		return Math.max(canvas.getPrefHeight(), canvas.getPrefWidth());
	}

	/**
	 * Transform a point to another that sticks the magnetic grid.
	 * @param pt The point to transform.
	 * @return The transformed point or a clone of the given point if there is no magnetic grid.
	 */
	public @NotNull Point getTransformedPointToGrid(final @NotNull Point3D pt) {
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
