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
package net.sf.latexdraw.ui;

import java.util.Arrays;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.Tuple;

/**
 * This widgets is a text area that automatically resizes its width and height according to its text.
 * @author Arnaud BLOUIN
 */
public class TextAreaAutoSize extends TextArea {
	/**
	 * States whether the text typed in the filed is valid. If not, the background of the filed is painted in red.
	 * That feature can be used when the text typed needed to be validated.
	 */
	protected boolean valid;
	protected final Text msg = new Text();

	/**
	 * Creates the widget.
	 */
	public TextAreaAutoSize() {
		super();
		valid = true;

		setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.ENTER) {
				if(evt.isShiftDown()) {
					final int caretPosition = getCaretPosition();
					setText(getText() + LSystem.EOL);
					positionCaret(caretPosition + 1);
				}else {
					evt.consume();
				}
			}
		});

		textProperty().addListener((observable, oldValue, newValue) -> updateDimension(newValue));

		msg.setFocusTraversable(false);
		msg.visibleProperty().bind(Bindings.createBooleanBinding(() -> isVisible() && !msg.getText().isEmpty(), visibleProperty(), msg.textProperty()));
		msg.setFill(Color.GRAY);

		visibleProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				updateDimension(getText());
				updateBackground();
			}
		});

		msg.layoutXProperty().bind(layoutXProperty());
		layoutYProperty().addListener((observable, oldValue, newValue) -> msg.setLayoutY(getLayoutY() + getPrefHeight() + 10));
		heightProperty().addListener((observable, oldValue, newValue) -> msg.setLayoutY(getLayoutY() + getPrefHeight() + 10));
		updateDimension(getText());
	}


	/**
	 * @return The text field that contains a message associated to the text area.
	 */
	public Text getMessageField() {
		return msg;
	}


	/**
	 * Defines whether the text typed in the filed is valid. If not, the background of the filed is painted in red.
	 * That feature can be used when the text typed needed to be validated.
	 * @param ok Valid or not and the message (that cannot be null).
	 * @since 3.2
	 */
	public void setValid(final Tuple<Boolean, String> ok) {
		valid = ok.a;
		msg.setText(ok.b);
		updateBackground();
	}


	/**
	 * @return Whether the text typed in the filed is valid.
	 * @since 3.1
	 */
	public boolean isValidText() {
		return valid;
	}


	private void updateBackground() {
		setBackground(new Background(new BackgroundFill(valid ? Color.WHITE : Color.RED, null, null)));
	}


	/**
	 * Updates the size of the widget according to its text.
	 */
	private void updateDimension(final String newText) {
		if(newText == null) {
			return;
		}

		final String[] lines = newText.split(LSystem.EOL);
		final int countEOL = newText.length() - newText.replace(LSystem.EOL, "").length();
		final String maxLine = Arrays.stream(lines).reduce((a, b) -> a.length() > b.length() ? a : b).orElse("");

		final Text txt = new Text(newText + " ");
		txt.setFont(getFont());
		final Bounds bounds = txt.getBoundsInLocal();
		final double width = bounds.getWidth() + 25;
		final double height = bounds.getHeight() + 15;

		setPrefRowCount(countEOL);
		setPrefColumnCount(maxLine.length() + 1);
		setPrefWidth(width);
		setMinWidth(width);
		setPrefHeight(height);
		setMinHeight(height);
	}
}
