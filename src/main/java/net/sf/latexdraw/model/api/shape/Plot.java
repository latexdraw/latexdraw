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
package net.sf.latexdraw.model.api.shape;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import net.sf.latexdraw.model.api.property.PlotProp;
import org.jetbrains.annotations.NotNull;

/**
 * The API for plots.
 * @author Arnaud BLOUIN
 */
public interface Plot extends PositionShape, PlotProp {
	/**
	 * @param x The X coordinate.
	 * @return The corresponding Y coordinate or NaN if a problem occurs.
	 */
	double getY(final double x);

	@NotNull BooleanProperty polarProperty();

	@NotNull StringProperty plotEquationProperty();

	@NotNull DoubleProperty plotMinXProperty();

	@NotNull DoubleProperty plotMaxXProperty();

	@NotNull IntegerProperty nbPlottedPointsProperty();

	@NotNull ObjectProperty<PlotStyle> plotStyleProperty();

	@NotNull ObjectProperty<DotStyle> dotStyleProperty();

	@NotNull DoubleProperty dotDiametreProperty();

	@NotNull DoubleProperty xScaleProperty();

	@NotNull DoubleProperty yScaleProperty();

	@NotNull
	@Override
	Plot duplicate();
}
