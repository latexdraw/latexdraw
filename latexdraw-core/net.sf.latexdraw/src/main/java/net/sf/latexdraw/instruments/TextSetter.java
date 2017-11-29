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
import java.util.Optional;
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
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.parsers.ps.PSFunctionParser;
import net.sf.latexdraw.ui.TextAreaAutoSize;
import net.sf.latexdraw.util.Inject;
import org.malai.action.Action;
import org.malai.javafx.action.ActivateInactivateInstruments;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.interaction.library.KeyTyped;

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
		keyNodeBinder(ModifyShapeProperty.class).on(textField).with(KeyCode.ENTER).first(action -> {
			action.setGroup(ShapeFactory.INST.createGroup(text));
			action.setProperty(ShapeProperties.TEXT);
			action.setValue(textField.getText());
		}).when(i -> text != null && !textField.getText().isEmpty()).bind();

		// Key Enter to validate the equation of a plot shape.
		keyNodeBinder(ModifyShapeProperty.class).on(textField).with(KeyCode.ENTER).first(action -> {
			action.setGroup(ShapeFactory.INST.createGroup(plot));
			action.setProperty(ShapeProperties.PLOT_EQ);
			action.setValue(textField.getText());
		}).when(i -> plot != null && !textField.getText().isEmpty()).bind();

		// Key Enter to add a text shape.
		keyNodeBinder(AddShape.class).on(textField).with(KeyCode.ENTER).first(action -> {
			final IPoint textPosition = ShapeFactory.INST.createPoint(position.getX(), position.getY());
			final IShape sh = pencil == null ? null : pencil.createShapeInstance();

			if(sh instanceof IText) {
				final IText text = (IText) sh;
				text.setPosition(textPosition.getX(), textPosition.getY());
				text.setText(textField.getText());
				action.setShape(text);
				action.setDrawing(pencil.canvas.getDrawing());
			}
		}).when(i -> pencil.getCurrentChoice() == EditionChoice.TEXT && text == null && !textField.getText().isEmpty()).bind();

		addBinding(new Enter2CheckPlot(this));
		addBinding(new KeyPress2Desactivate(this));
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
	 * @since 3.0
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


	private static class KeyPress2Desactivate extends JfXWidgetBinding<ActivateInactivateInstruments, KeyTyped, TextSetter> {
		KeyPress2Desactivate(TextSetter setter) throws InstantiationException, IllegalAccessException {
			super(setter, false, ActivateInactivateInstruments.class, new KeyTyped(), setter.textField);
		}

		@Override
		public void initAction() {
			action.addInstrumentToInactivate(instrument);
		}

		@Override
		public boolean isConditionRespected() {
			final Optional<KeyCode> key = interaction.getKeyCode();
			return key.isPresent() && (key.get() == KeyCode.ENTER && instrument.textField.isValidText() &&
				!instrument.textField.getText().isEmpty() || key.get() == KeyCode.ESCAPE);
		}
	}


	private static class Enter2CheckPlot extends JfXWidgetBinding<AddShape, KeyTyped, TextSetter> {
		Enter2CheckPlot(final TextSetter ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, AddShape.class, new KeyTyped(), ins.textField);
		}

		@Override
		public void initAction() {
			instrument.textField.setValid(true);
			final IPoint textPosition = ShapeFactory.INST.createPoint(instrument.position.getX(), instrument.position.getY() + instrument.textField.getHeight());
			final IShape sh = instrument.pencil == null ? null : instrument.pencil.createShapeInstance();

			if(sh instanceof IPlot) {
				final IPlot plot = (IPlot) sh;
				plot.setPosition(textPosition.getX(), textPosition.getY());
				plot.setPlotEquation(instrument.textField.getText());
				action.setShape(plot);
				action.setDrawing(instrument.pencil.canvas.getDrawing());
			}
		}

		@Override
		public boolean isConditionRespected() {
			boolean ok = instrument.pencil.getCurrentChoice() == EditionChoice.PLOT && instrument.plot == null && !
				instrument.textField.getText().isEmpty() && interaction.getKeyCode().orElse(null) == KeyCode.ENTER;

			if(ok)
				if(!PSFunctionParser.isValidPostFixEquation(instrument.textField.getText(),
					Double.valueOf(instrument.plotCustom.minXSpinner.getValue().toString()),
					Double.valueOf(instrument.plotCustom.maxXSpinner.getValue().toString()),
					Double.valueOf(instrument.plotCustom.nbPtsSpinner.getValue().toString()))) {
					instrument.textField.setValid(false);
					ok = false;
				}
			return ok;
		}
	}
}
