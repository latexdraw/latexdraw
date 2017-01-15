/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
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
 * This instrument modifies shadow properties of shapes or the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeShadowCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** Sets if the a shape has a shadow or not. */
	@FXML protected CheckBox shadowCB;

	/** Sets the colour of the shadow of a figure. */
	@FXML protected ColorPicker shadowColB;

	/** Changes the size of the shadow. */
	@FXML protected Spinner<Double> shadowSizeField;

	/** Changes the angle of the shadow. */
	@FXML protected Spinner<Double> shadowAngleField;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeShadowCustomiser() {
		super();
	}


	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());

		scrollOnSpinner(shadowSizeField);
		scrollOnSpinner(shadowAngleField);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isShadowable()) {
			final boolean hasShadow = shape.hasShadow();

			shadowCB.setSelected(hasShadow);
			shadowColB.setDisable(!hasShadow);
			shadowAngleField.setDisable(!hasShadow);
			shadowSizeField.setDisable(!hasShadow);

			if(hasShadow) {
				shadowColB.setValue(shape.getShadowCol().toJFX());
				shadowAngleField.getValueFactory().setValue(Math.toDegrees(shape.getShadowAngle()));
				shadowSizeField.getValueFactory().setValue(shape.getShadowSize());
			}
			setActivated(true);
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new Checkbox4Pencil(this, shadowCB, ShapeProperties.SHADOW));
		addInteractor(new Checkbox4Selection(this, shadowCB, ShapeProperties.SHADOW));
		addInteractor(new ColourPicker4Pencil(this, shadowColB, ShapeProperties.SHADOW_COLOUR));
		addInteractor(new ColourPicker4Selection(this, shadowColB, ShapeProperties.SHADOW_COLOUR));
		addInteractor(new Spinner4Pencil(this, shadowSizeField, ShapeProperties.SHADOW_SIZE, false));
		addInteractor(new Spinner4Selection(this, shadowSizeField, ShapeProperties.SHADOW_SIZE, false));
		addInteractor(new Spinner4Pencil(this, shadowAngleField, ShapeProperties.SHADOW_ANGLE, true));
		addInteractor(new Spinner4Selection(this, shadowAngleField, ShapeProperties.SHADOW_ANGLE, true));
	}
}
