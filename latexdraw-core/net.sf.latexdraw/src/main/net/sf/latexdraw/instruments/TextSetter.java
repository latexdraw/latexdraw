package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.ui.TextAreaAutoSize;

import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;

import com.google.inject.Inject;

/**
 * This instrument allows to add and modify texts to the drawing.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 20/12/2010<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TextSetter extends JfxInstrument {
	/** The text field. */
	protected final TextAreaAutoSize textField;

	/**
	 * The point where texts are added. It may not corresponds with the location
	 * of the text field since the text field position is absolute (does not
	 * consider the zoom level).
	 */
	protected IPoint relativePoint;

	/**
	 * The text to modify throw this instrument. If it is not set, a new text
	 * will be created.
	 */
	protected IText text;

	protected IPlot plot;

	/** The pencil used to create shapes. */
	protected @Inject Pencil pencil;

	protected @Inject ShapeTextCustomiser custom;

	protected @Inject ShapePlotCustomiser plotCustom;

	/**
	 * Creates the instrument.
	 */
	public TextSetter() {
		super();
		textField = new TextAreaAutoSize();
		// overlayedPanel.add(textField, JLayeredPane.PALETTE_LAYER);
		// overlayedPanel.add(textField.getMessageField(),
		// JLayeredPane.PALETTE_LAYER);
		textField.setVisible(false);
//		addEventable(textField);
	}

	@Override
	public void onActionDone(final Action action) {
		super.onActionDone(action);
		custom.update();
	}

	/**
	 * Sets the text to modify throw this instrument.
	 * 
	 * @param sh
	 *            The plot to modify.
	 * @since 3.1
	 */
	public void setPlot(final IPlot sh) {
		plot = sh;

		if(sh!=null) {
			textField.setText(sh.getPlotEquation());
			setPlotMessage();
		}
	}

	/**
	 * Sets the text to modify throw this instrument.
	 * 
	 * @param sh
	 *            The text to modify.
	 * @since 3.0
	 */
	public void setText(final IText sh) {
		text = sh;

		if(sh!=null) {
			textField.setText(sh.getText());
			setTextMessage();
		}
	}

	/**
	 * @param pencil
	 *            The pencil to set to the text setter.
	 * @since 3.0
	 */
	public void setPencil(final Pencil pencil) {
		this.pencil = pencil;
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new Enter2SetText(this));
		// addInteractor(new Enter2SetEquation(this));
		// addInteractor(new Enter2AddText(this));
		// // addInteractor(new Enter2CheckPlot(this));
		// addInteractor(new KeyPress2Desactivate(this));
	}

	private void setTextMessage() {
		textField.getMessageField().setText("Write LaTeX text.");
	}

	private void setPlotMessage() {
		final String eqEx = " 2 x add sin"; //$NON-NLS-1$
		textField.getMessageField().setText("Write the equation, e.g.:"+eqEx);
	}

	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		if(activated&&pencil.isActivated()) {
			switch(pencil.getCurrentChoice()) {
				case TEXT:
					setTextMessage();
					break;
				case PLOT:
					setPlotMessage();
					break;
				default:
					break;
			}
		}
		textField.setValid(true);
		textField.setVisible(activated);
//		if(activated)
//			textField.requestFocusInWindow();
	}

	/**
	 * @return The text field used to set texts.
	 * @since 3.0
	 */
	public TextAreaAutoSize getTextField() {
		return textField;
	}

	/**
	 * @param relativePoint
	 *            The point where texts are added. It may not corresponds with
	 *            the location of the text field since the text field position
	 *            is absolute (does not consider the zoom level).
	 * @since 3.0
	 */
	public void setRelativePoint(final IPoint relativePoint) {
		this.relativePoint = relativePoint;
	}
}

/**
 * // * This links maps a key press interaction to an action that desactivates
 * the instrument. //
 */
// class KeyPress2Desactivate extends
// InteractorImpl<ActivateInactivateInstruments, KeyTyped, TextSetter> {
// /**
// * Creates the link.
// */
// protected KeyPress2Desactivate(final TextSetter ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, ActivateInactivateInstruments.class, KeyTyped.class);
// }
//
// @Override
// public void initAction() {
// action.addInstrumentToInactivate(instrument);
// }
//
// @Override
// public boolean isConditionRespected() {
// final int key = interaction.getKey();
// // It is useless to check if another key is pressed because if it is the
// case, the interaction
// // is in state keyPressed.
// return key==KeyEvent.VK_ENTER && instrument.textField.isValidText() &&
// !instrument.textField.getText().isEmpty() || key==KeyEvent.VK_ESCAPE;
// }
// }
//
//
// class Enter2SetText extends InteractorImpl<ModifyShapeProperty, KeyTyped,
// TextSetter> {
// protected Enter2SetText(final TextSetter ins) throws InstantiationException,
// IllegalAccessException {
// super(ins, false, ModifyShapeProperty.class, KeyTyped.class);
// }
//
// @Override
// public void initAction() {
// action.setGroup(ShapeFactory.createGroup(instrument.text));
// action.setProperty(ShapeProperties.TEXT);
// action.setValue(instrument.textField.getText());
// }
//
// @Override
// public boolean isConditionRespected() {
// return instrument.text!=null && !instrument.textField.getText().isEmpty() &&
// interaction.getKey()==KeyEvent.VK_ENTER;
// }
// }
//
//
// class Enter2SetEquation extends InteractorImpl<ModifyShapeProperty, KeyTyped,
// TextSetter> {
// protected Enter2SetEquation(final TextSetter ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, ModifyShapeProperty.class, KeyTyped.class);
// }
//
// @Override
// public void initAction() {
// action.setGroup(ShapeFactory.createGroup(instrument.plot));
// action.setProperty(ShapeProperties.PLOT_EQ);
// action.setValue(instrument.textField.getText());
// }
//
// @Override
// public boolean isConditionRespected() {
// return instrument.plot!=null && !instrument.textField.getText().isEmpty() &&
// interaction.getKey()==KeyEvent.VK_ENTER;
// }
// }
//
//
// /**
// * This links maps a key press interaction to an action that adds a text to
// the drawing.
// */
// class Enter2AddText extends InteractorImpl<AddShape, KeyTyped, TextSetter> {
// /**
// * Creates the link.
// */
// protected Enter2AddText(final TextSetter ins) throws InstantiationException,
// IllegalAccessException {
// super(ins, false, AddShape.class, KeyTyped.class);
// }
//
// @Override
// public void initAction() {
// final IPoint textPosition = instrument.relativePoint==null ?
// ShapeFactory.createPoint(instrument.textField.getX(),
// instrument.textField.getY()+instrument.textField.getHeight()) :
// instrument.relativePoint;
// final IShape sh = instrument.pencil==null ? null :
// instrument.pencil.createShapeInstance();
//
// if(sh instanceof IText) {
// final IText text = (IText)sh;
// text.setPosition(textPosition.getX(), textPosition.getY());
// text.setText(instrument.textField.getText());
// action.setShape(text);
// action.setDrawing(instrument.pencil.getCanvas().getDrawing());
// }
// }
//
// @Override
// public boolean isConditionRespected() {
// return instrument.pencil.getCurrentChoice()==EditionChoice.TEXT &&
// instrument.text==null &&
// !instrument.textField.getText().isEmpty() &&
// interaction.getKey()==KeyEvent.VK_ENTER;
// }
// }

// class Enter2CheckPlot extends InteractorImpl<AddShape, KeyTyped, TextSetter>
// {
// protected Enter2CheckPlot(final TextSetter ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, AddShape.class, KeyTyped.class);
// }
//
// @Override
// public void initAction() {
// instrument.textField.setValid(true);
// final IPoint textPosition = instrument.relativePoint==null ?
// ShapeFactory.createPoint(instrument.textField.getX(),
// instrument.textField.getY()+instrument.textField.getHeight()) :
// instrument.relativePoint;
// final IShape sh = instrument.pencil==null ? null :
// instrument.pencil.createShapeInstance();
//
// if(sh instanceof IPlot) {
// final IPlot plot = (IPlot)sh;
// plot.setPosition(textPosition.getX(), textPosition.getY());
// plot.setPlotEquation(instrument.textField.getText());
// action.setShape(plot);
// action.setDrawing(instrument.pencil.canvas().getDrawing());
// }
// }
//
// @Override
// public boolean isConditionRespected() {
// boolean ok = instrument.pencil.currentChoice()==EditionChoice.PLOT &&
// instrument.plot==null &&
// !instrument.textField.getText().isEmpty() &&
// interaction.getKey()==KeyEvent.VK_ENTER;
//
// if(ok)
// if(!PSFunctionParser.isValidPostFixEquation(instrument.textField.getText(),
// Double.valueOf(instrument.plotCustom.getMinXSpinner().getValue().toString()),
// Double.valueOf(instrument.plotCustom.getMaxXSpinner().getValue().toString()),
// Double.valueOf(instrument.plotCustom.getNbPtsSpinner().getValue().toString())))
// {
// instrument.textField.setValid(false);
// ok = false;
// }
// return ok;
// }
// }
