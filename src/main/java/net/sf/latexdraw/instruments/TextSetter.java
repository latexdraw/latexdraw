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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.commands.shape.AddShape;
import net.sf.latexdraw.commands.shape.ModifyShapeProperty;
import net.sf.latexdraw.commands.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.parsers.ps.PSFunctionParser;
import net.sf.latexdraw.ui.TextAreaAutoSize;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.Tuple;
import org.malai.command.Command;
import org.malai.javafx.command.ActivateInactivateInstruments;

/**
 * An instrument for adding and modifying texts of the drawing.
 * @author Arnaud Blouin
 */
public class TextSetter extends CanvasInstrument implements Initializable {
	/** The text field. */
	private final TextAreaAutoSize textField;
	/** The position where texts are added. It may not corresponds with the location of the text field since the text field position is absolute
	 * (does not consider the zoom level). */
	private IPoint position;
	/** The text to modify throw this instrument. If it is not set, a new text will be created. */
	private IText text;
	private IPlot plot;
	/** The pencil used to create shapes. */
	@Inject private Pencil pencil;
	@Inject private ShapeTextCustomiser custom;
	@Inject private ShapePlotCustomiser plotCustom;
	@Inject private LangService lang;

	/**
	 * Creates the instrument.
	 */
	public TextSetter() {
		super();
		textField = new TextAreaAutoSize();
	}

	@Override
	public void onCmdDone(final Command cmd) {
		super.onCmdDone(cmd);
		custom.update();
		Platform.runLater(() -> canvas.requestFocus());
	}

	/**
	 * Sets the text to modify throw this instrument.
	 * @param sh The plot to modify.
	 */
	public void setPlot(final IPlot sh) {
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
	public void setText(final IText sh) {
		text = sh;

		if(sh != null) {
			textField.setText(sh.getText());
			setTextMessage();
		}
	}

	/**
	 * @param pen The pen to set to the text setter.
	 */
	public void setPencil(final Pencil pen) {
		pencil = pen;
	}

	@Override
	protected void configureBindings() {
		// Key Enter to validate the text.
		keyNodeBinder(i -> new ModifyShapeProperty<>(ShapeProperties.TEXT, ShapeFactory.INST.createGroup(text), textField.getText())).
			on(textField).with(KeyCode.ENTER).when(i -> text != null && !pencil.isActivated() && !textField.getText().isEmpty()).bind();

		// Key Enter to validate the equation of a plot shape.
		keyNodeBinder(i -> new ModifyShapeProperty<>(ShapeProperties.PLOT_EQ, ShapeFactory.INST.createGroup(plot), textField.getText())).
			on(textField).with(KeyCode.ENTER).when(i -> plot != null && !pencil.isActivated() && checkValidPlotFct()).bind();

		// Key Enter to add a text shape.
		keyNodeBinder(i -> {
			text = (IText) pencil.createShapeInstance();
			text.setPosition(ShapeFactory.INST.createPoint(position.getX(), position.getY()));
			text.setText(textField.getText());
			return new AddShape(text, canvas.getDrawing());
		}).on(textField).with(KeyCode.ENTER).
			when(i -> pencil.isActivated() && pencil.getCurrentChoice() == EditionChoice.TEXT && !textField.getText().isEmpty()).bind();

		// Key Enter to add a plot shape.
		keyNodeBinder(i -> {
			plot = (IPlot) pencil.createShapeInstance();
			plot.setPosition(ShapeFactory.INST.createPoint(position.getX(), position.getY() + textField.getHeight()));
			plot.setPlotEquation(textField.getText());
			return new AddShape(plot, canvas.getDrawing());
		}).on(textField).with(KeyCode.ENTER).when(i -> pencil.isActivated() && pencil.getCurrentChoice() == EditionChoice.PLOT && checkValidPlotFct()).bind();

		keyNodeBinder(i -> new ActivateInactivateInstruments(null, Collections.singletonList(this), false, false)).
			on(textField).with(KeyCode.ENTER).when(i -> textField.isValidText() && !textField.getText().isEmpty()).bind();

		keyNodeBinder(i -> new ActivateInactivateInstruments(null, Collections.singletonList(this), false, false)).
			on(textField).with(KeyCode.ESCAPE).bind();
	}

	private boolean checkValidPlotFct() {
		Tuple<Boolean, String> valid;
		try {
			valid = PSFunctionParser.isValidPostFixEquation(textField.getText(), Double.parseDouble(plotCustom.minXSpinner.getValue().toString()),
				Double.parseDouble(plotCustom.maxXSpinner.getValue().toString()), Double.parseDouble(plotCustom.nbPtsSpinner.getValue().toString()));
		}catch(final IllegalArgumentException ex) {
			valid = new Tuple<>(Boolean.FALSE, lang.getBundle().getString("invalid.function"));
		}
		textField.setValid(valid);
		return valid.a;
	}

	private void setTextMessage() {
		textField.getMessageField().setText(lang.getBundle().getString("write.latex.text"));
	}

	private void setPlotMessage() {
		final String eqEx = " 2 x add sin"; //NON-NLS
		textField.getMessageField().setText(lang.getBundle().getString("write.the.equation") + ' ' + eqEx);
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);
		if(act && pencil.isActivated()) {
			if(pencil.getCurrentChoice() == EditionChoice.TEXT) {
				setTextMessage();
			}else {
				if(pencil.getCurrentChoice() == EditionChoice.PLOT) {
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
	public TextAreaAutoSize getTextField() {
		return textField;
	}

	/**
	 * @param rel The point where texts are added.
	 */
	public void setPosition(final IPoint rel) {
		position = rel;
	}

	public IPoint getPosition() {
		return position;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(false);
		canvas.addToWidgetLayer(textField);
		canvas.addToWidgetLayer(textField.getMessageField());
	}
}
