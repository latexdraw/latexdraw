package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.latex.VerticalPosition;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;

import org.malai.javafx.instrument.JfxInstrument;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument modifies the properties of the drawing.<br>
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
 * 2012-04-01<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class DrawingPropertiesCustomiser extends JfxInstrument {
	/** The field that changes the title of the drawing. */
	@FXML protected TextField titleField;

	/** The field that changes the label of the drawing. */
	@FXML protected TextField labelField;

	/** Defines if the horizontal position of the drawing must be centred. */
	@FXML protected CheckBox middleHorizPosCB;

	/** Defines the position of the drawing. */
	@FXML protected ComboBox<VerticalPosition> positionCB;

	/** Defines the scale of the drawing. */
	@FXML protected Spinner<Double> scaleField;

	/** The LaTeX code generator. */
	protected LaTeXGenerator latexGen;

	/**
	 * Creates the instrument.
	 */
	public DrawingPropertiesCustomiser() {
		super();
	}

	@Override
	public void onUndoableUndo(final Undoable undoable) {
		super.onUndoableUndo(undoable);
		updateWidgets();
	}

	@Override
	public void onUndoableRedo(final Undoable undoable) {
		super.onUndoableRedo(undoable);
		updateWidgets();
	}

	@Override
	public void reinit() {
		super.reinit();
		latexGen.setCaption(""); //$NON-NLS-1$
		latexGen.setLabel(""); //$NON-NLS-1$
		latexGen.setPositionHoriCentre(false);
		latexGen.setPositionVertToken(VerticalPosition.NONE);
		updateWidgets();
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document==null||root==null||generalPreferences)
			return;

		final String ns = LPath.INSTANCE.getNormaliseNamespaceURI(nsURI);

		if(!latexGen.getCaption().isEmpty()) {
			Element elt = document.createElement(ns+LNamespace.XML_CAPTION);
			elt.appendChild(document.createTextNode(latexGen.getCaption()));
			root.appendChild(elt);
		}
		if(!latexGen.getLabel().isEmpty()) {
			Element elt = document.createElement(ns+LNamespace.XML_LABEL);
			elt.appendChild(document.createTextNode(latexGen.getLabel()));
			root.appendChild(elt);
		}
		if(latexGen.isPositionHoriCentre()) {
			Element elt = document.createElement(ns+LNamespace.XML_POSITION_HORIZ);
			elt.setTextContent(String.valueOf(latexGen.isPositionHoriCentre()));
			root.appendChild(elt);
		}
		if(latexGen.getPositionVertToken()!=VerticalPosition.NONE) {
			Element elt = document.createElement(ns+LNamespace.XML_POSITION_VERT);
			elt.setTextContent(latexGen.getPositionVertToken().toString());
			root.appendChild(elt);
		}
	}

	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_CAPTION))
			latexGen.setCaption(root.getTextContent());
		else if(name.endsWith(LNamespace.XML_LABEL))
			latexGen.setLabel(root.getTextContent());
		else if(name.endsWith(LNamespace.XML_POSITION_HORIZ))
			latexGen.setPositionHoriCentre(Boolean.parseBoolean(root.getTextContent()));
		else if(name.endsWith(LNamespace.XML_POSITION_VERT))
			latexGen.setPositionVertToken(VerticalPosition.getPosition(root.getTextContent()));
	}

	protected void updateWidgets() {
		titleField.setText(latexGen.getCaption());
		labelField.setText(latexGen.getLabel());
		middleHorizPosCB.setSelected(latexGen.isPositionHoriCentre());
		positionCB.setValue(latexGen.getPositionVertToken());
		scaleField.getValueFactory().setValue(latexGen.getScale());
	}

	@Override
	public void setActivated(final boolean isactivated) {
		super.setActivated(isactivated);

		if(isactivated)
			updateWidgets();
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new TextField2CustDrawing(this));
		// addInteractor(new CheckBox2CustDrawing(this));
		// addInteractor(new ComboBox2CustDrawing(this));
		// addInteractor(new Spinner2CustDrawing(this));
	}

	// /** The link that maps a combo box to action that modifies the drawing's
	// properties. */
	// protected static class Spinner2CustDrawing extends
	// InteractorImpl<ModifyLatexProperties, SpinnerModified,
	// DrawingPropertiesCustomiser> {
	// protected Spinner2CustDrawing(final DrawingPropertiesCustomiser ins)
	// throws InstantiationException, IllegalAccessException {
	// super(ins, false, ModifyLatexProperties.class, SpinnerModified.class);
	// }
	//
	// @Override
	// public void initAction() {
	// action.setProperty(LatexProperties.SCALE);
	// action.setGenerator(instrument.latexGen);
	// }
	//
	// @Override
	// public void updateAction() {
	// action.setValue(instrument.scaleField.getValue());
	// }
	//
	// @Override
	// public boolean isConditionRespected() {
	// return interaction.getSpinner()==instrument.scaleField;
	// }
	// }
	//
	//
	// /** The link that maps a combo box to action that modifies the drawing's
	// properties. */
	// protected static class ComboBox2CustDrawing extends
	// InteractorImpl<ModifyLatexProperties, ListSelectionModified,
	// DrawingPropertiesCustomiser> {
	// protected ComboBox2CustDrawing(final DrawingPropertiesCustomiser ins)
	// throws InstantiationException, IllegalAccessException {
	// super(ins, false, ModifyLatexProperties.class,
	// ListSelectionModified.class);
	// }
	//
	// @Override
	// public void initAction() {
	// action.setProperty(LatexProperties.POSITION_VERTICAL);
	// action.setGenerator(instrument.latexGen);
	// action.setValue(instrument.positionCB.getSelectedItem());
	// }
	//
	// @Override
	// public boolean isConditionRespected() {
	// return interaction.getList()==instrument.positionCB;
	// }
	// }
	//
	//
	// /** The link that maps a check box to action that modifies the drawing's
	// properties. */
	// protected static class CheckBox2CustDrawing extends
	// InteractorImpl<ModifyLatexProperties, CheckBoxModified,
	// DrawingPropertiesCustomiser> {
	// protected CheckBox2CustDrawing(final DrawingPropertiesCustomiser ins)
	// throws InstantiationException, IllegalAccessException {
	// super(ins, false, ModifyLatexProperties.class, CheckBoxModified.class);
	// }
	//
	//
	// @Override
	// public void initAction() {
	// action.setProperty(LatexProperties.POSITION_HORIZONTAL);
	// action.setGenerator(instrument.latexGen);
	// action.setValue(instrument.middleHorizPosCB.isSelected());
	// }
	//
	// @Override
	// public boolean isConditionRespected() {
	// return interaction.getCheckBox()==instrument.middleHorizPosCB;
	// }
	// }
	//
	//
	// /** The link that maps text fields to action that modifies the drawing's
	// properties. */
	// protected static class TextField2CustDrawing extends
	// InteractorImpl<ModifyLatexProperties, TextChanged,
	// DrawingPropertiesCustomiser> {
	// protected TextField2CustDrawing(final DrawingPropertiesCustomiser ins)
	// throws InstantiationException, IllegalAccessException {
	// super(ins, false, ModifyLatexProperties.class, TextChanged.class);
	// }
	//
	// @Override
	// public void initAction() {
	// if(interaction.getTextComp()==instrument.labelField)
	// action.setProperty(LatexProperties.LABEL);
	// else
	// action.setProperty(LatexProperties.CAPTION);
	//
	// action.setGenerator(instrument.latexGen);
	// }
	//
	//
	// @Override
	// public void updateAction() {
	// super.updateAction();
	// action.setValue(interaction.getText());
	// }
	//
	//
	// @Override
	// public boolean isConditionRespected() {
	// return interaction.getTextComp()==instrument.labelField ||
	// interaction.getTextComp()==instrument.titleField;
	// }
	// }
}
