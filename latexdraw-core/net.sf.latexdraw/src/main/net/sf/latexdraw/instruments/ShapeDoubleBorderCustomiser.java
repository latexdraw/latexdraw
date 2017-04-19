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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.shape.IGroup;

/**
 * This instrument modifies double border properties of shapes or the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeDoubleBorderCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** Sets if the shape has double borders or not. */
	@FXML protected CheckBox dbleBoundCB;

	/** Allows to change the colour of the space between the double boundaries. */
	@FXML protected ColorPicker dbleBoundColB;

	/** This field modifies the double separation of the double line. */
	@FXML protected Spinner<Double> dbleSepField;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	ShapeDoubleBorderCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());

		scrollOnSpinner(dbleSepField);
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isDbleBorderable()) {
			setActivated(true);
			final boolean dble = shape.hasDbleBord();

			dbleBoundCB.setSelected(dble);
			dbleBoundColB.setDisable(!dble);
			dbleSepField.setDisable(!dble);

			if(dble) {
				dbleBoundColB.setValue(shape.getDbleBordCol().toJFX());
				dbleSepField.getValueFactory().setValue(shape.getDbleBordSep());
			}
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new Checkbox4Pencil(this, dbleBoundCB, ShapeProperties.DBLE_BORDERS));
		addInteractor(new Checkbox4Selection(this, dbleBoundCB, ShapeProperties.DBLE_BORDERS));
		addInteractor(new ColourPicker4Selection(this, dbleBoundColB, ShapeProperties.COLOUR_DBLE_BORD));
		addInteractor(new ColourPicker4Pencil(this, dbleBoundColB, ShapeProperties.COLOUR_DBLE_BORD));
		addInteractor(new Spinner4Pencil(this, dbleSepField, ShapeProperties.DBLE_BORDERS_SIZE, false));
		addInteractor(new Spinner4Selection(this, dbleSepField, ShapeProperties.DBLE_BORDERS_SIZE, false));
	}
}
