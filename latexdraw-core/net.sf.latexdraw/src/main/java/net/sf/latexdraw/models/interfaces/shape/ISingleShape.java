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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * The API for shapes not composed of several shapes.
 * @author Arnaud Blouin
 */
public interface ISingleShape extends IShape {
	/**
	 * @return The property of the thickness.
	 */
	DoubleProperty thicknessProperty();

	/**
	 * @return The property of the line style.
	 */
	ObjectProperty<LineStyle> linestyleProperty();

	/**
	 * @return The property of the border position.
	 */
	ObjectProperty<BorderPos> borderPosProperty();

	/**
	 * @return The property of the line colour.
	 */
	ObjectProperty<Color> lineColourProperty();

	/**
	 * @return The property of the filling.
	 */
	ObjectProperty<FillingStyle> fillingProperty();

	DoubleProperty dashSepWhiteProperty();

	DoubleProperty dashSepBlackProperty();

	DoubleProperty dotSepProperty();

	BooleanProperty dbleBordProperty();

	DoubleProperty dbleBordSepProperty();

	ObjectProperty<Color> dbleBordColProperty();

	ObjectProperty<Color> gradColStartProperty();

	ObjectProperty<Color> gradColEndProperty();

	ObjectProperty<Color> fillingColProperty();

	DoubleProperty gradAngleProperty();

	DoubleProperty gradMidPtProperty();

	BooleanProperty shadowProperty();

	ObjectProperty<Color> shadowColProperty();

	DoubleProperty shadowAngleProperty();

	DoubleProperty shadowSizeProperty();

	DoubleProperty hatchingsAngleProperty();

	DoubleProperty hatchingsSepProperty();

	DoubleProperty hatchingsWidthProperty();

	ObjectProperty<Color> hatchingsColProperty();

	ReadOnlyBooleanProperty showPointProperty();
}
