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
package net.sf.latexdraw.models.impl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.ITextProp;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;

/**
 * A model of a text.
 * @author Arnaud Blouin
 */
class LText extends LPositionShape implements IText {
	/** The text */
	private final StringProperty text;
	/** The text position of the text. */
	private TextPosition textPosition;


	/**
	 * The constructor by default.
	 * @since 3.0
	 */
	LText() {
		this(ShapeFactory.INST.createPoint(), "text"); //NON-NLS
	}

	/**
	 * @param pt The position of the text.
	 * @param text The text.
	 * @throws IllegalArgumentException If pt is not valid.
	 * @since 3.0
	 */
	LText(final IPoint pt, final String text) {
		super(pt);

		this.text = new SimpleStringProperty(text == null || text.isEmpty() ? "text" : text); //NON-NLS
		textPosition = TextPosition.BOT_LEFT;
	}

	@Override
	public IText duplicate() {
		final IText text = ShapeFactory.INST.createText();
		text.copy(this);
		return text;
	}

	@Override
	public IPoint getPosition() {
		return getPtAt(0);
	}

	@Override
	public String getText() {
		return text.get();
	}


	@Override
	public void setText(final String newTxt) {
		if(newTxt != null && !newTxt.isEmpty()) {
			text.setValue(newTxt);
		}
	}

	@Override
	public StringProperty textProperty() {
		return text;
	}


	@Override
	public void copy(final IShape s) {
		super.copy(s);

		if(s instanceof ITextProp) {
			final ITextProp textSh = (ITextProp) s;
			text.setValue(textSh.getText());
			textPosition = textSh.getTextPosition();
		}
	}


	@Override
	public TextPosition getTextPosition() {
		return textPosition;
	}


	@Override
	public void setTextPosition(final TextPosition textPosition) {
		if(textPosition != null) {
			this.textPosition = textPosition;
		}
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
