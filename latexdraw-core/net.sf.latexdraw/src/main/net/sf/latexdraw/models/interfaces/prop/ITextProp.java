package net.sf.latexdraw.models.interfaces.prop;

import net.sf.latexdraw.models.interfaces.shape.TextPosition;

/**
 * Text shape properties.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
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
}
