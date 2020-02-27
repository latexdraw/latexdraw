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
package net.sf.latexdraw.model.api.property;

import net.sf.latexdraw.model.api.shape.TextPosition;
import org.jetbrains.annotations.NotNull;

/**
 * Properties of text shapes.
 * @author Arnaud Blouin
 */
public interface TextProp {
	/**
	 * @return The text position of the text (bottom-right, top-left, etc.).
	 */
	@NotNull TextPosition getTextPosition();

	/**
	 * Sets the text position of the text.
	 * @param textPosition The new text position of the text.
	 */
	void setTextPosition(final @NotNull TextPosition textPosition);

	/**
	 * @return the text.
	 */
	@NotNull String getText();

	/**
	 * @param text the text to set.
	 */
	void setText(final @NotNull String text);
}
