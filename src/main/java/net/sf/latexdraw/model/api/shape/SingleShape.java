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
import javafx.beans.property.ReadOnlyBooleanProperty;
import org.jetbrains.annotations.NotNull;

/**
 * The API for shapes not composed of several shapes.
 * @author Arnaud Blouin
 */
public interface SingleShape extends Shape {
	/**
	 * @return The property of the thickness.
	 */
	@NotNull DoubleProperty thicknessProperty();

	/**
	 * @return The property of the line style.
	 */
	@NotNull ObjectProperty<LineStyle> linestyleProperty();

	/**
	 * @return The property of the border position.
	 */
	@NotNull ObjectProperty<BorderPos> borderPosProperty();

	/**
	 * @return The property of the line colour.
	 */
	@NotNull ObjectProperty<Color> lineColourProperty();

	/**
	 * @return The property of the filling.
	 */
	@NotNull ObjectProperty<FillingStyle> fillingProperty();

	@NotNull DoubleProperty dashSepWhiteProperty();

	@NotNull DoubleProperty dashSepBlackProperty();

	@NotNull DoubleProperty dotSepProperty();

	@NotNull BooleanProperty dbleBordProperty();

	@NotNull DoubleProperty dbleBordSepProperty();

	@NotNull ObjectProperty<Color> dbleBordColProperty();

	@NotNull ObjectProperty<Color> gradColStartProperty();

	@NotNull ObjectProperty<Color> gradColEndProperty();

	@NotNull ObjectProperty<Color> fillingColProperty();

	@NotNull DoubleProperty gradAngleProperty();

	@NotNull DoubleProperty gradMidPtProperty();

	@NotNull BooleanProperty shadowProperty();

	@NotNull ObjectProperty<Color> shadowColProperty();

	@NotNull DoubleProperty shadowAngleProperty();

	@NotNull DoubleProperty shadowSizeProperty();

	@NotNull DoubleProperty hatchingsAngleProperty();

	@NotNull DoubleProperty hatchingsSepProperty();

	@NotNull DoubleProperty hatchingsWidthProperty();

	@NotNull ObjectProperty<Color> hatchingsColProperty();

	@NotNull ReadOnlyBooleanProperty showPointProperty();
}
