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
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.parsers.ps.PSFunctionParser;
import net.sf.latexdraw.ui.TextAreaAutoSize;
import net.sf.latexdraw.util.Inject;
import org.malai.action.Action;
import org.malai.javafx.action.ActivateInactivateInstruments;

/**
 * An instrument for adding and modifying texts of the drawing.
 * @author Arnaud Blouin
 */
public class TextSetter extends CanvasInstrument implements Initializable {
	/** The text field. */
	private final TextAreaAutoSize textField;
	/**
	 * The position where texts are added. It may not corresponds with the location
	 * of the text field since the text field position is absolute (does not consider the zoom level).
	 */
	private IPoint position;
	/** The text to modify throw this instrument. If it is not set, a new text will be created. */
	private IText text;
	private IPlot plot;
	/** The pencil used to create shapes. */
	@Inject private Pencil pencil;
	@Inject private ShapeTextCustomiser custom;
	@Inject private ShapePlotCustomiser plotCustom;

	/**
	 * Creates the instrument.
	 */
	public TextSetter() {
		super();
		textField = new TextAreaAutoSize();
	}

	@Override
	public void onActionDone(final Action action) {
		super.onActionDone(action);
		custom.update();
		Platform.runLater(() -> canvas.requestFocus());
	}

	/**
	 * Sets the text to modify throw this instrument.
	 * @param sh The plot to modify.
	 * @since 3.1
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
	 * @since 3.0
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

	/*
private static class Enter2SetEquation extends JfXWidgetBinding<ModifyShapeProperty, KeyTyped, TextSetter> {
		Enter2SetEquation(final TextSetter ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, ModifyShapeProperty.class, KeyTyped.class, ins.textField);
		}

		@Override
		public void initAction() {
			action.setGroup(ShapeFactory.INST.createGroup(instrument.plot));
			action.setProperty(ShapeProperties.PLOT_EQ);
			action.setValue(instrument.textField.getText());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.plot != null && !instrument.textField.getText().isEmpty() && interaction.getKeyCode().orElse(null) == KeyCode.ENTER;
		}
	}
	 */

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		// Key Enter to validate the text.
		keyNodeBinder(ModifyShapeProperty.class).on(textField).with(KeyCode.ENTER).
			map(i -> new ModifyShapeProperty(ShapeProperties.TEXT, ShapeFactory.INST.createGroup(text), textField.getText())).
			when(i -> text != null && !textField.getText().isEmpty()).bind();

		// Key Enter to validate the equation of a plot shape.
		keyNodeBinder(ModifyShapeProperty.class).on(textField).with(KeyCode.ENTER).
			map(i -> new ModifyShapeProperty(ShapeProperties.PLOT_EQ, ShapeFactory.INST.createGroup(plot), ShapeProperties.PLOT_EQ)).
			when(i -> plot != null && !textField.getText().isEmpty()).bind();

		// Key Enter to add a text shape.
		keyNodeBinder(AddShape.class).on(textField).with(KeyCode.ENTER).
			map(i -> {
				final IText sh = (IText) pencil.createShapeInstance();
				text.setPosition(ShapeFactory.INST.createPoint(position.getX(), position.getY()));
				text.setText(textField.getText());
				return new AddShape(sh, canvas.getDrawing());
			}).
			when(i -> pencil.getCurrentChoice() == EditionChoice.TEXT && text == null && !textField.getText().isEmpty()).bind();

		// Key Enter to add a text shape.
		keyNodeBinder(AddShape.class).on(textField).with(KeyCode.ENTER).
			map(i -> {
				textField.setValid(true);
				plot = (IPlot) pencil.createShapeInstance();
				plot.setPosition(ShapeFactory.INST.createPoint(position.getX(), position.getY() + textField.getHeight()));
				plot.setPlotEquation(textField.getText());
				return new AddShape(plot, canvas.getDrawing());
			}).
			when(i -> pencil.getCurrentChoice() == EditionChoice.PLOT && plot == null && !textField.getText().isEmpty() && isValidPlotFct()).bind();

		keyNodeBinder(ActivateInactivateInstruments.class).
			map(i -> new ActivateInactivateInstruments(null, Collections.singletonList(this), false, false)).
			with(KeyCode.ENTER).when(i -> textField.isValidText() && !textField.getText().isEmpty()).bind();
		keyNodeBinder(ActivateInactivateInstruments.class).
			map(i -> new ActivateInactivateInstruments(null, Collections.singletonList(this), false, false)).
			with(KeyCode.ESCAPE).bind();
	}

	private boolean isValidPlotFct() {
		return PSFunctionParser.isValidPostFixEquation(textField.getText(),
			Double.valueOf(plotCustom.minXSpinner.getValue().toString()),
			Double.valueOf(plotCustom.maxXSpinner.getValue().toString()),
			Double.valueOf(plotCustom.nbPtsSpinner.getValue().toString()));
	}

	private void setTextMessage() {
		textField.getMessageField().setText("Write LaTeX text.");
	}

	private void setPlotMessage() {
		final String eqEx = " 2 x add sin"; //$NON-NLS-1$
		textField.getMessageField().setText("Write the equation, e.g.: " + eqEx);
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);
		if(act && pencil.isActivated()) {
			switch(pencil.getCurrentChoice()) {
				case TEXT:
					setTextMessage();
					break;
				case PLOT:
					setPlotMessage();
					break;
			}
		}

		textField.setValid(true);
		textField.setVisible(act);

		if(act) {
			Platform.runLater(() -> {
				textField.setLayoutX(position.getX());
				textField.setLayoutY(position.getY() - textField.getPrefHeight());
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
