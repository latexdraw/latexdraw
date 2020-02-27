/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.undo.UndoCollector;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import net.sf.latexdraw.command.LatexProperties;
import net.sf.latexdraw.command.ModifyLatexProperties;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.latex.VerticalPosition;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument modifies the properties of the drawing.
 * @author Arnaud Blouin
 */
public class DrawingPropertiesCustomiser extends JfxInstrument implements Initializable {
	/** The field that changes the title of the drawing. */
	@FXML private TextField titleField;
	/** The field that changes the label of the drawing. */
	@FXML private TextField labelField;
	/** Defines if the horizontal position of the drawing must be centred. */
	@FXML private CheckBox middleHorizPosCB;
	/** Defines the position of the drawing. */
	@FXML private ComboBox<VerticalPosition> positionCB;
	/** Defines the scale of the drawing. */
	@FXML private Spinner<Double> scaleField;
	/** The LaTeX code generator. */
	private final @NotNull LaTeXDataService latexData;

	@Inject
	public DrawingPropertiesCustomiser(final LaTeXDataService data) {
		super();
		this.latexData = Objects.requireNonNull(data);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		positionCB.getItems().addAll(VerticalPosition.values());
		setActivated(true);
		addDisposable(UndoCollector.getInstance().redos().subscribe(undoable -> updateWidgets()));
		addDisposable(UndoCollector.getInstance().undos().subscribe(undoable -> updateWidgets()));
	}

	@Override
	public void reinit() {
		super.reinit();
		latexData.setCaption("");
		latexData.setLabel("");
		latexData.setPositionHoriCentre(false);
		latexData.setPositionVertToken(VerticalPosition.NONE);
		updateWidgets();
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document doc, final Element root) {
		super.save(generalPreferences, nsURI, doc, root);

		if(doc == null || root == null || generalPreferences) {
			return;
		}

		final String ns = SystemUtils.getInstance().getNormaliseNamespaceURI(nsURI);

		if(!latexData.getCaption().isEmpty()) {
			final Element elt = doc.createElement(ns + LNamespace.XML_CAPTION);
			elt.appendChild(doc.createTextNode(latexData.getCaption()));
			root.appendChild(elt);
		}
		if(!latexData.getLabel().isEmpty()) {
			final Element elt = doc.createElement(ns + LNamespace.XML_LABEL);
			elt.appendChild(doc.createTextNode(latexData.getLabel()));
			root.appendChild(elt);
		}
		SystemUtils.getInstance().createElement(doc, ns + LNamespace.XML_POSITION_HORIZ, String.valueOf(latexData.isPositionHoriCentre()), root);
		SystemUtils.getInstance().createElement(doc, ns + LNamespace.XML_POSITION_VERT, latexData.getPositionVertToken().toString(), root);
	}

	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_CAPTION)) {
			latexData.setCaption(root.getTextContent());
			return;
		}
		if(name.endsWith(LNamespace.XML_LABEL)) {
			latexData.setLabel(root.getTextContent());
			return;
		}
		if(name.endsWith(LNamespace.XML_POSITION_HORIZ)) {
			latexData.setPositionHoriCentre(Boolean.parseBoolean(root.getTextContent()));
			return;
		}
		if(name.endsWith(LNamespace.XML_POSITION_VERT)) {
			latexData.setPositionVertToken(VerticalPosition.getPosition(root.getTextContent()));
		}
	}

	protected void updateWidgets() {
		titleField.setText(latexData.getCaption());
		labelField.setText(latexData.getLabel());
		middleHorizPosCB.setSelected(latexData.isPositionHoriCentre());
		positionCB.setValue(latexData.getPositionVertToken());
		scaleField.getValueFactory().setValue(latexData.getScale());
	}

	@Override
	public void setActivated(final boolean isactivated) {
		super.setActivated(isactivated);
		if(isactivated) {
			updateWidgets();
		}
	}

	@Override
	protected void configureBindings() {
		textInputBinder()
			.toProduce(() -> new ModifyLatexProperties(latexData, LatexProperties.LABEL, null))
			.on(labelField)
			.then((i, c) -> c.setValue(i.getWidget().getText()))
			.bind();

		textInputBinder()
			.toProduce(() -> new ModifyLatexProperties(latexData, LatexProperties.CAPTION, null))
			.on(titleField)
			.then((i, c) -> c.setValue(i.getWidget().getText()))
			.bind();

		checkboxBinder()
			.toProduce(i -> new ModifyLatexProperties(latexData, LatexProperties.POSITION_HORIZONTAL, i.getWidget().isSelected()))
			.on(middleHorizPosCB)
			.bind();

		comboboxBinder()
			.toProduce(i -> new ModifyLatexProperties(latexData, LatexProperties.POSITION_VERTICAL, i.getWidget().getSelectionModel().getSelectedItem()))
			.on(positionCB)
			.bind();

		spinnerBinder()
			.toProduce(i -> new ModifyLatexProperties(latexData, LatexProperties.SCALE, i.getWidget().getValue()))
			.on(scaleField)
			.continuousExecution()
			.then(c -> c.setValue(scaleField.getValue()))
			.bind();
	}
}
