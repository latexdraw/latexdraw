/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.command.ActivateInactivateInstruments;
import io.github.interacto.jfx.instrument.JfxInstrument;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.command.shape.AddShape;
import net.sf.latexdraw.command.shape.ModifyShapeProperty;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.parser.ps.PSFunctionParser;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.ui.TextAreaAutoSize;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.jfx.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * An instrument for adding and modifying texts of the drawing.
 * @author Arnaud Blouin
 */
public class TextSetter extends JfxInstrument implements Initializable {
	/** The text field. */
	private final @NotNull TextAreaAutoSize textField;
	/** The position where texts are added. It may not corresponds with the location of the text field since the text field position is absolute
	 * (does not consider the zoom level). */
	private Point position;
	/** The text to modify throw this instrument. If it is not set, a new text will be created. */
	private Text text;
	private Plot plot;
	private final @NotNull ResourceBundle lang;
	private final @NotNull EditingService editing;
	private final @NotNull Drawing drawing;
	private final @NotNull Canvas canvas;

	@Inject
	public TextSetter(final Canvas canvas, final ResourceBundle lang, final EditingService editing, final Drawing drawing) {
		super();
		this.editing = Objects.requireNonNull(editing);
		this.lang = Objects.requireNonNull(lang);
		this.drawing = Objects.requireNonNull(drawing);
		this.canvas = Objects.requireNonNull(canvas);
		textField = new TextAreaAutoSize();
	}

	/**
	 * Sets the text to modify throw this instrument.
	 * @param sh The plot to modify.
	 */
	public void setPlot(final Plot sh) {
		plot = sh;

		if(sh != null) {
			textField.setText(sh.getPlotEquation());
			setPlotMessage();
		}
	}

	/**
	 * Sets the text to modify throw this instrument.
	 * @param sh The text to modify.
	 */
	public void setText(final Text sh) {
		text = sh;

		if(sh != null) {
			textField.setText(sh.getText());
			setTextMessage();
		}
	}

	@Override
	protected void configureBindings() {
		final var bindingFragment = shortcutBinder()
			.on(textField)
			.end(() -> canvas.requestFocus());

		// Key Enter to validate the text.
		bindingFragment
			.toProduce(i -> new ModifyShapeProperty<>(ShapeProperties.TEXT, ShapeFactory.INST.createGroup(text), textField.getText()))
			.with(KeyCode.ENTER)
			.when(i -> text != null && editing.getCurrentChoice() == EditionChoice.HAND && !textField.getText().isEmpty())
			.bind();

		// Key Enter to validate the equation of a plot shape.
		bindingFragment
			.toProduce(i -> new ModifyShapeProperty<>(ShapeProperties.PLOT_EQ, ShapeFactory.INST.createGroup(plot), textField.getText()))
			.with(KeyCode.ENTER)
			.when(i -> plot != null && editing.getCurrentChoice() == EditionChoice.HAND && checkValidPlotFct())
			.bind();

		// Key Enter to add a text shape.
		bindingFragment
			.toProduce(i -> {
				text = (Text) editing.createShapeInstance();
				text.setPosition(ShapeFactory.INST.createPoint(position.getX(), position.getY()));
				text.setText(textField.getText());
				return new AddShape(text, drawing);
			})
			.with(KeyCode.ENTER)
			.when(i -> editing.getCurrentChoice() == EditionChoice.TEXT && !textField.getText().isEmpty())
			.bind();

		// Key Enter to add a plot shape.
		bindingFragment
			.toProduce(i -> {
				plot = (Plot) editing.createShapeInstance();
				plot.setPosition(ShapeFactory.INST.createPoint(position.getX(), position.getY() + textField.getHeight()));
				plot.setPlotEquation(textField.getText());
				return new AddShape(plot, drawing);
			})
			.with(KeyCode.ENTER)
			.when(i -> editing.getCurrentChoice() == EditionChoice.PLOT && checkValidPlotFct())
			.bind();

		bindingFragment
			.toProduce(i -> new ActivateInactivateInstruments(null, Collections.singletonList(this), false, false))
			.with(KeyCode.ENTER)
			.when(i -> textField.isValidText() && !textField.getText().isEmpty())
			.bind();

		bindingFragment
			.toProduce(i -> new ActivateInactivateInstruments(null, Collections.singletonList(this), false, false))
			.with(KeyCode.ESCAPE)
			.bind();
	}

	private boolean checkValidPlotFct() {
		Tuple<Boolean, String> valid;
		try {
			valid = PSFunctionParser.isValidPostFixEquation(textField.getText(), editing.getGroupParams().getPlotMinX(),
				editing.getGroupParams().getPlotMaxX(), editing.getGroupParams().getNbPlottedPoints());
		}catch(final IllegalArgumentException ex) {
			valid = new Tuple<>(Boolean.FALSE, lang.getString("invalid.function"));
		}
		textField.setValid(valid);
		return valid.a;
	}

	private void setTextMessage() {
		textField.getMessageField().setText(lang.getString("write.latex.text"));
	}

	private void setPlotMessage() {
		final String eqEx = " 2 x add sin"; //NON-NLS
		textField.getMessageField().setText(lang.getString("write.the.equation") + ' ' + eqEx);
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);
		if(act) {
			if(editing.getCurrentChoice() == EditionChoice.TEXT) {
				setTextMessage();
			}else {
				if(editing.getCurrentChoice() == EditionChoice.PLOT) {
					setPlotMessage();
				}
			}
		}

		textField.setValid(new Tuple<>(Boolean.TRUE, ""));
		textField.setVisible(act);

		if(act) {
			Platform.runLater(() -> {
				if(position != null) {
					textField.setLayoutX(position.getX());
					textField.setLayoutY(position.getY() - textField.getPrefHeight());
				}
				textField.requestFocus();

			});
		}
	}

	/**
	 * @return The text field used to set texts.
	 */
	public @NotNull TextAreaAutoSize getTextField() {
		return textField;
	}

	/**
	 * @param rel The point where texts are added.
	 */
	public void setPosition(final Point rel) {
		position = rel;
	}

	public Point getPosition() {
		return position;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(false);
		canvas.addToWidgetLayer(textField);
		canvas.addToWidgetLayer(textField.getMessageField());
	}
}
