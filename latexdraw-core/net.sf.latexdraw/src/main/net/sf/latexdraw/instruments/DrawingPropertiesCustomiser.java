package net.sf.latexdraw.instruments;

import java.util.Objects;

import javax.swing.JLabel;

import net.sf.latexdraw.actions.ModifyLatexProperties;
import net.sf.latexdraw.actions.ModifyLatexProperties.LatexProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator.VerticalPosition;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;

import org.malai.instrument.Link;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.interaction.library.CheckBoxModified;
import org.malai.swing.interaction.library.ListSelectionModified;
import org.malai.swing.interaction.library.SpinnerModified;
import org.malai.swing.interaction.library.TextChanged;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MCheckBox;
import org.malai.swing.widget.MComboBox;
import org.malai.swing.widget.MSpinner;
import org.malai.swing.widget.MTextField;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument modifies the properties of the drawing.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-01<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class DrawingPropertiesCustomiser extends WidgetInstrument {
	/** The field that changes the title of the drawing. */
	protected MTextField titleField;

	/** The field that changes the label of the drawing. */
	protected MTextField labelField;

	/** The LaTeX code generator. */
	protected LaTeXGenerator latexGen;

	/** Defines if the horizontal position of the drawing must be centred. */
	protected MCheckBox middleHorizPosCB;

	/** Defines the position of the drawing. */
	protected MComboBox<VerticalPosition> positionCB;

	/** Defines the scale of the drawing. */
	protected MSpinner scaleField;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param latexGen The LaTeX code generator.
	 * @since 3.0
	 */
	public DrawingPropertiesCustomiser(final UIComposer<?> composer, final LaTeXGenerator latexGen) {
		super(composer);

		this.latexGen = Objects.requireNonNull(latexGen);
		initialiseWidgets();
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
		latexGen.setCaption("");
		latexGen.setLabel("");
		latexGen.setPositionHoriCentre(false);
		latexGen.setPositionVertToken(VerticalPosition.NONE);
		updateWidgets();
	}



	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document==null || root==null)
			return ;

		Element elt;

		if(!generalPreferences) {
			final String ns = LPath.INSTANCE.getNormaliseNamespaceURI(nsURI);

			if(latexGen.getCaption().length()>0) {
				elt = document.createElement(ns + LNamespace.XML_CAPTION);
				elt.appendChild(document.createCDATASection(latexGen.getCaption()));
				root.appendChild(elt);
			}
			if(latexGen.getLabel().length()>0) {
				elt = document.createElement(ns + LNamespace.XML_LABEL);
				elt.appendChild(document.createCDATASection(latexGen.getLabel()));
				root.appendChild(elt);
			}
			if(latexGen.isPositionHoriCentre()) {
				elt = document.createElement(ns + LNamespace.XML_POSITION_HORIZ);
				elt.setTextContent(String.valueOf(latexGen.isPositionHoriCentre()));
				root.appendChild(elt);
			}
			if(latexGen.getPositionVertToken()!=VerticalPosition.NONE) {
				elt = document.createElement(ns + LNamespace.XML_POSITION_VERT);
				elt.setTextContent(latexGen.getPositionVertToken().toString());
				root.appendChild(elt);
			}
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



	@Override
	protected void initialiseWidgets() {
		titleField = new MTextField();
		labelField = new MTextField();
		middleHorizPosCB = new MCheckBox("Centred");
		positionCB = new MComboBox<>(VerticalPosition.values(), new JLabel("Position:"));
		scaleField = new MSpinner(new MSpinner.MSpinnerNumberModel(1., 0.1, 100., 0.1), new JLabel("Scale"));
	}


	protected void updateWidgets() {
		titleField.setText(latexGen.getCaption());
		labelField.setText(latexGen.getLabel());
		middleHorizPosCB.setSelected(latexGen.isPositionHoriCentre());
		positionCB.setSelectedItemSafely(latexGen.getPositionVertToken());
		scaleField.setValue(latexGen.getScale());
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		if(activated)
			updateWidgets();
	}


	/**
	 * @return The spinner that sets the scale of the drawing.
	 * @since 3.0
	 */
	public MSpinner getScaleSpinner() {
		return scaleField;
	}


	/**
	 * @return The combo-box that defines the position of the drawing.
	 * @since 3.0
	 */
	public final MComboBox<VerticalPosition> getPositionCB() {
		return positionCB;
	}


	/**
	 * @return  The field that define if the horizontal position of the drawing must be centred.
	 * @since 3.0
	 */
	public final MCheckBox getMiddleHorizPosCB() {
		return middleHorizPosCB;
	}



	/**
	 * @return The field that changes the title of the drawing.
	 * @since 3.0
	 */
	public final MTextField getTitleField() {
		return titleField;
	}


	/**
	 * @return The field that changes the label of the drawing.
	 * @since 3.0
	 */
	public final MTextField getLabelField() {
		return labelField;
	}


	@Override
	protected void initialiseLinks() {
		try {
			addLink(new TextField2CustDrawing(this));
			addLink(new CheckBox2CustDrawing(this));
			addLink(new ComboBox2CustDrawing(this));
			addLink(new Spinner2CustDrawing(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/** The link that maps a combo box to action that modifies the drawing's properties. */
	private static class Spinner2CustDrawing extends Link<ModifyLatexProperties, SpinnerModified, DrawingPropertiesCustomiser> {
		public Spinner2CustDrawing(final DrawingPropertiesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, ModifyLatexProperties.class, SpinnerModified.class);
		}

		@Override
		public void initAction() {
			action.setProperty(LatexProperties.SCALE);
			action.setGenerator(instrument.latexGen);
		}

		@Override
		public void updateAction() {
			action.setValue(instrument.scaleField.getValue());
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getSpinner()==instrument.scaleField;
		}
	}


	/** The link that maps a combo box to action that modifies the drawing's properties. */
	private static class ComboBox2CustDrawing extends Link<ModifyLatexProperties, ListSelectionModified, DrawingPropertiesCustomiser> {
		public ComboBox2CustDrawing(final DrawingPropertiesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, ModifyLatexProperties.class, ListSelectionModified.class);
		}

		@Override
		public void initAction() {
			action.setProperty(LatexProperties.POSITION_VERTICAL);
			action.setGenerator(instrument.latexGen);
			action.setValue(instrument.positionCB.getSelectedItem());
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getList()==instrument.positionCB;
		}
	}


	/** The link that maps a check box to action that modifies the drawing's properties. */
	private static class CheckBox2CustDrawing extends Link<ModifyLatexProperties, CheckBoxModified, DrawingPropertiesCustomiser> {
		public CheckBox2CustDrawing(final DrawingPropertiesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, ModifyLatexProperties.class, CheckBoxModified.class);
		}


		@Override
		public void initAction() {
			action.setProperty(LatexProperties.POSITION_HORIZONTAL);
			action.setGenerator(instrument.latexGen);
			action.setValue(instrument.middleHorizPosCB.isSelected());
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getCheckBox()==instrument.middleHorizPosCB;
		}
	}


	/** The link that maps text fields to action that modifies the drawing's properties. */
	private static class TextField2CustDrawing extends Link<ModifyLatexProperties, TextChanged, DrawingPropertiesCustomiser> {
		protected TextField2CustDrawing(final DrawingPropertiesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, ModifyLatexProperties.class, TextChanged.class);
		}

		@Override
		public void initAction() {
			if(interaction.getTextComp()==instrument.labelField)
				action.setProperty(LatexProperties.LABEL);
			else
				action.setProperty(LatexProperties.CAPTION);

			action.setGenerator(instrument.latexGen);
		}


		@Override
		public void updateAction() {
			super.updateAction();
			action.setValue(interaction.getText());
		}


		@Override
		public boolean isConditionRespected() {
			return interaction.getTextComp()==instrument.labelField || interaction.getTextComp()==instrument.titleField;
		}
	}
}
