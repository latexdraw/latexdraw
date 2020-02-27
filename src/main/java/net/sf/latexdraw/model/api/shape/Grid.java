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
import net.sf.latexdraw.model.api.property.GridProp;
import org.jetbrains.annotations.NotNull;

/**
 * The API for LaTeX grids.
 * @author Arnaud BLOUIN
 */
public interface Grid extends StandardGrid, GridProp {
	@NotNull ObjectProperty<Color> gridLabelsColourProperty();

	@NotNull IntegerProperty gridDotsProperty();

	@NotNull DoubleProperty unitProperty();

	@NotNull DoubleProperty subGridWidthProperty();

	@NotNull IntegerProperty subGridDotsProperty();

	@NotNull IntegerProperty subGridDivProperty();

	@NotNull ObjectProperty<Color> subGridColourProperty();

	@NotNull DoubleProperty gridWidthProperty();

	@NotNull BooleanProperty yLabelWestProperty();

	@NotNull BooleanProperty xLabelSouthProperty();

	@NotNull
	@Override
	Grid duplicate();
}
