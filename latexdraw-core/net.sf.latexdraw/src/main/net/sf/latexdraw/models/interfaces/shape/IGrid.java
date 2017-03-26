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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;

/**
 * The API for LaTeX grids.
 * @author Arnaud BLOUIN
 */
public interface IGrid extends IStandardGrid, IGridProp {
	ObjectProperty<Color> gridLabelsColourProperty();

	IntegerProperty gridDotsProperty();

	DoubleProperty unitProperty();

	DoubleProperty subGridWidthProperty();

	IntegerProperty subGridDotsProperty();

	IntegerProperty subGridDivProperty();

	ObjectProperty<Color> subGridColourProperty();

	DoubleProperty gridWidthProperty();

	BooleanProperty yLabelWestProperty();

	BooleanProperty xLabelSouthProperty();
}
