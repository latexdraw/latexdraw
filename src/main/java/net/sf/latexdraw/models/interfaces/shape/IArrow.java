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
import javafx.beans.property.ObjectProperty;
import net.sf.latexdraw.models.interfaces.prop.IArrowable;

/**
 * The API for arrows.
 * @author Arnaud BLOUIN
 */
public interface IArrow extends IArrowable {
	/**
	 * Copies the parameters of the given arrow to the current arrow.
	 * The owner of the arrow to copy is not copied to the current arrow.
	 * @param model The arrow to copy. Cannot be null.
	 * @since 3.0
	 */
	void copy(final IArrow model);

	/**
	 * @return The full thickness of the line shape that has the arrow.
	 * @since 3.0
	 */
	double getLineThickness();

	/**
	 * @return True if the current arrow has a style.
	 * @since 3.0
	 */
	boolean hasStyle();

	/**
	 * @return The length of the arrow computed from the provided formula:
	 * length = arrowlength x (num x linewidth + dim)
	 * @since 3.0
	 */
	double getArrowShapeLength();

	/**
	 * @return The style of the arrow.
	 * @since 3.0
	 */
	ArrowStyle getArrowStyle();

	/**
	 * Defines the style of the arrow.
	 * @param arrowStyle The new style of the arrow.
	 * @since 3.0
	 */
	void setArrowStyle(final ArrowStyle arrowStyle);

	/**
	 * @return True if the arrow is the left arrow of its shape.
	 * @since 3.0
	 */
	boolean isLeftArrow();

	/**
	 * @return True if the arrow if inverted in its shape.
	 * @since 3.0
	 */
	boolean isInverted();

	/**
	 * @return The shape that contains the arrow.
	 * @since 3.0
	 */
	IArrowableSingleShape getShape();

	/**
	 * @return The line that can be used to locate the arrow.
	 * @since 3.0
	 */
	ILine getArrowLine();

	/**
	 * @return The radius of the rounded arrow styles.
	 * @since 3.0
	 */
	double getRoundShapedArrowRadius();

	/**
	 * @return The width of the bar arrow styles.
	 * @since 3.0
	 */
	double getBarShapedArrowWidth();

	/**
	 * @return The length of the brackets of bracket arrow styles.
	 * @since 3.0
	 */
	double getBracketShapedArrowLength();

	/**
	 * @return The width of arrow styles.
	 * @since 3.0
	 */
	double getArrowShapedWidth();

	ObjectProperty<ArrowStyle> styleProperty();

	DoubleProperty arrowSizeDimProperty();

	DoubleProperty arrowSizeNumProperty();

	DoubleProperty arrowLengthProperty();

	DoubleProperty arrowInsetProperty();

	DoubleProperty dotSizeDimProperty();

	DoubleProperty dotSizeNumProperty();

	DoubleProperty tBarSizeDimProperty();

	DoubleProperty tBarSizeNumProperty();

	DoubleProperty bracketNumProperty();

	DoubleProperty rBracketNumProperty();
}
