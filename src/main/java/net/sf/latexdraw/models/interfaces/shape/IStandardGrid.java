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
package net.sf.latexdraw.models.interfaces.shape;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;

/**
 * The API for LaTeX grid shapes.
 * @author Arnaud BLOUIN
 */
public interface IStandardGrid extends IPositionShape, IStdGridProp {
	/**
	 * @return The property of the label size parameter.
	 */
	IntegerProperty labelsSizeProperty();

	/**
	 * @return The property of the X-start parameter.
	 */
	DoubleProperty gridStartXProperty();

	/**
	 * @return The property of the Y-start parameter.
	 */
	DoubleProperty gridStartYProperty();

	/**
	 * @return The property of the X-end parameter.
	 */
	DoubleProperty gridEndXProperty();

	/**
	 * @return The property of the Y-end parameter.
	 */
	DoubleProperty gridEndYProperty();

	/**
	 * @return The property of the X-origin parameter.
	 */
	DoubleProperty originXProperty();

	/**
	 * @return The property of the Y-origin parameter.
	 */
	DoubleProperty originYProperty();

	@Override
	IStandardGrid duplicate();
}
