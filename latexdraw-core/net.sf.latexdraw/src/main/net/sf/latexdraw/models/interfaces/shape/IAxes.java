/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.interfaces.shape;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;

/**
 * The API for LaTeX axes.
 * @author Arnaud BLOUIN
 */
public interface IAxes extends IStandardGrid, IAxesProp, IArrowableShape {
	DoubleProperty incrementXProperty();

	DoubleProperty incrementYProperty();

	DoubleProperty distLabelsXProperty();

	DoubleProperty distLabelsYProperty();

	ObjectProperty<PlottingStyle> labelsDisplayedProperty();

	BooleanProperty showOriginProperty();

	ObjectProperty<PlottingStyle> ticksDisplayedProperty();

	ObjectProperty<TicksStyle> ticksStyleProperty();

	DoubleProperty ticksSizeProperty();

	ObjectProperty<AxesStyle> axesStyleProperty();
}
