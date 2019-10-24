/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.model.api.shape;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import net.sf.latexdraw.model.api.property.IStdGridProp;
import org.jetbrains.annotations.NotNull;

/**
 * The API for LaTeX grid shapes.
 * @author Arnaud BLOUIN
 */
public interface StandardGrid extends PositionShape, IStdGridProp {
	/**
	 * @return The property of the label size parameter.
	 */
	@NotNull IntegerProperty labelsSizeProperty();

	/**
	 * @return The property of the X-start parameter.
	 */
	@NotNull DoubleProperty gridStartXProperty();

	/**
	 * @return The property of the Y-start parameter.
	 */
	@NotNull DoubleProperty gridStartYProperty();

	/**
	 * @return The property of the X-end parameter.
	 */
	@NotNull DoubleProperty gridEndXProperty();

	/**
	 * @return The property of the Y-end parameter.
	 */
	@NotNull DoubleProperty gridEndYProperty();

	/**
	 * @return The property of the X-origin parameter.
	 */
	@NotNull DoubleProperty originXProperty();

	/**
	 * @return The property of the Y-origin parameter.
	 */
	@NotNull DoubleProperty originYProperty();

	@NotNull
	@Override
	StandardGrid duplicate();
}
