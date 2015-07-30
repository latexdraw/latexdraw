package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

/**
 * This instrument modifies shadow properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 * <br>
 * 11/07/2010<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeShadowCustomiser extends ShapePropertyCustomiser {
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
		}else
			setActivated(false);
	}

	@Override
	protected void initialiseInteractors() {
		try {
			addInteractor(new Checkbox4Pencil(this, shadowCB, ShapeProperties.SHADOW));
			addInteractor(new Checkbox4Selection(this, shadowCB, ShapeProperties.SHADOW));
			addInteractor(new ColourPicker4Pencil(this, shadowColB, ShapeProperties.SHADOW_COLOUR));
			addInteractor(new ColourPicker4Selection(this, shadowColB, ShapeProperties.SHADOW_COLOUR));
			addInteractor(new Spinner4Pencil(this, shadowSizeField, ShapeProperties.SHADOW_SIZE, false));
			addInteractor(new Spinner4Selection(this, shadowSizeField, ShapeProperties.SHADOW_SIZE, false));
			addInteractor(new Spinner4Pencil(this, shadowAngleField, ShapeProperties.SHADOW_ANGLE, true));
			addInteractor(new Spinner4Selection(this, shadowAngleField, ShapeProperties.SHADOW_ANGLE, true));
		}catch(InstantiationException | IllegalAccessException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}
}
