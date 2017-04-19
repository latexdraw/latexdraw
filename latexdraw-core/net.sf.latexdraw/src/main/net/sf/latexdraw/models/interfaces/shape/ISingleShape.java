/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
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
import org.eclipse.jdt.annotation.NonNull;

/**
 * The API for shapes not composed of several shapes.
 * @author Arnaud Blouin
 */
public interface ISingleShape extends IShape {
	/**
	 * @return The property of the thickness.
	 */
	@NonNull DoubleProperty thicknessProperty();

	/**
	 * @return The property of the line style.
	 */
	@NonNull ObjectProperty<LineStyle> linestyleProperty();

	/**
	 * @return The property of the border position.
	 */
	@NonNull ObjectProperty<BorderPos> borderPosProperty();

	/**
	 * @return The property of the line colour.
	 */
	@NonNull ObjectProperty<Color> lineColourProperty();

	/**
	 * @return The property of the filling.
	 */
	@NonNull ObjectProperty<FillingStyle> fillingProperty();

	@NonNull DoubleProperty dashSepWhiteProperty();

	@NonNull DoubleProperty dashSepBlackProperty();

	@NonNull DoubleProperty dotSepProperty();

	@NonNull BooleanProperty dbleBordProperty();

	@NonNull DoubleProperty dbleBordSepProperty();

	@NonNull ObjectProperty<Color> dbleBordColProperty();

	@NonNull ObjectProperty<Color> gradColStartProperty();

	@NonNull ObjectProperty<Color> gradColEndProperty();

	@NonNull ObjectProperty<Color> fillingColProperty();

	@NonNull DoubleProperty gradAngleProperty();

	@NonNull DoubleProperty gradMidPtProperty();

	@NonNull BooleanProperty shadowProperty();

	@NonNull ObjectProperty<Color> shadowColProperty();

	@NonNull DoubleProperty shadowAngleProperty();

	@NonNull DoubleProperty shadowSizeProperty();

	@NonNull DoubleProperty hatchingsAngleProperty();

	@NonNull DoubleProperty hatchingsSepProperty();

	@NonNull DoubleProperty hatchingsWidthProperty();

	@NonNull ObjectProperty<Color> hatchingsColProperty();
}
