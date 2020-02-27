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
import javafx.beans.property.ObjectProperty;
import net.sf.latexdraw.model.api.property.AxesProp;
import org.jetbrains.annotations.NotNull;

/**
 * The API for LaTeX axes.
 * @author Arnaud BLOUIN
 */
public interface Axes extends StandardGrid, AxesProp, ArrowableSingleShape {
	@NotNull DoubleProperty incrementXProperty();

	@NotNull DoubleProperty incrementYProperty();

	@NotNull DoubleProperty distLabelsXProperty();

	@NotNull DoubleProperty distLabelsYProperty();

	@NotNull ObjectProperty<PlottingStyle> labelsDisplayedProperty();

	@NotNull BooleanProperty showOriginProperty();

	@NotNull ObjectProperty<PlottingStyle> ticksDisplayedProperty();

	@NotNull ObjectProperty<TicksStyle> ticksStyleProperty();

	@NotNull DoubleProperty ticksSizeProperty();

	@NotNull ObjectProperty<AxesStyle> axesStyleProperty();

	@NotNull
	@Override
	Axes duplicate();
}
