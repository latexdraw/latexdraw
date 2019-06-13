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
package net.sf.latexdraw.model.impl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.TextProp;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.TextPosition;
import org.jetbrains.annotations.NotNull;

/**
 * A model of a text.
 * @author Arnaud Blouin
 */
class TextImpl extends PositionShapeBase implements net.sf.latexdraw.model.api.shape.Text {
	/** The text */
	private final @NotNull StringProperty text;
	/** The text position of the text. */
	private @NotNull TextPosition textPosition;


	/**
	 * The constructor by default.
	 */
	TextImpl() {
		this(ShapeFactory.INST.createPoint(), "text"); //NON-NLS
	}

	/**
	 * @param pt The position of the text.
	 * @param text The text.
	 * @throws IllegalArgumentException If pt is not valid.
	 */
	TextImpl(final Point pt, final String text) {
		super(pt);

		this.text = new SimpleStringProperty(text == null || text.isEmpty() ? "text" : text); //NON-NLS
		textPosition = TextPosition.BOT_LEFT;
	}

	@Override
	public @NotNull Text duplicate() {
		final Text txt = ShapeFactory.INST.createText();
		txt.copy(this);
		return txt;
	}

	@Override
	public @NotNull Point getPosition() {
		return getPtAt(0);
	}

	@Override
	public @NotNull String getText() {
		return text.get();
	}


	@Override
	public void setText(final @NotNull String newTxt) {
		if(!newTxt.isEmpty()) {
			text.setValue(newTxt);
		}
	}

	@Override
	public @NotNull StringProperty textProperty() {
		return text;
	}


	@Override
	public void copy(final Shape s) {
		super.copy(s);

		if(s instanceof TextProp) {
			final TextProp textSh = (TextProp) s;
			text.setValue(textSh.getText());
			textPosition = textSh.getTextPosition();
		}
	}


	@Override
	public @NotNull TextPosition getTextPosition() {
		return textPosition;
	}


	@Override
	public void setTextPosition(final @NotNull TextPosition textPosition) {
		this.textPosition = textPosition;
	}

	@Override
	public boolean isThicknessable() {
		return false;
	}

	@Override
	public boolean isShadowable() {
		return false;
	}

	@Override
	public boolean isFillable() {
		return false;
	}

	@Override
	public boolean isInteriorStylable() {
		return false;
	}
}
