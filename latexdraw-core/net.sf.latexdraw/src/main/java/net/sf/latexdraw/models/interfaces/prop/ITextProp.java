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
package net.sf.latexdraw.models.interfaces.prop;

import javafx.beans.property.StringProperty;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;

/**
 * Properties of text shapes.
 * @author Arnaud Blouin
 */
public interface ITextProp {
	/**
	 * @return The text position of the text (bottom-right, top-left, etc.).
	 * @since 3.0
	 */
	TextPosition getTextPosition();

	/**
	 * Sets the text position of the text.
	 * @param textPosition The new text position of the text.
	 * @since 3.0
	 */
	void setTextPosition(final TextPosition textPosition);

	/**
	 * @return the text.
	 * @since 3.0
	 */
	String getText();

	/**
	 * @param text the text to set.
	 * @since 3.0
	 */
	void setText(final String text);

	/**
	 * @return The text property.
	 */
	StringProperty textProperty();
}
