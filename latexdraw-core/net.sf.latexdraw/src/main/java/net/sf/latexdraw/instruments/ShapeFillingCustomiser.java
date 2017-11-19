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
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;

/**
 * This instrument modifies filling properties of shapes or the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeFillingCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Sets the colour of the interior of a shape. */
	@FXML private ColorPicker fillColButton;
	/** Sets the colour of the hatchings. */
	@FXML private ColorPicker hatchColButton;
	/** Changes the first colour of a gradient. */
	@FXML private ColorPicker gradStartColButton;
	/** Changes the second colour of a gradient. */
	@FXML private ColorPicker gradEndColButton;
	/** Changes the style of filling. */
	@FXML private ComboBox<FillingStyle> fillStyleCB;
	/** Changes the mid point of the gradient. */
	@FXML private Spinner<Double> gradMidPtField;
	/** Changes the angle of the gradient. */
	@FXML private Spinner<Double> gradAngleField;
	/** Changes the separation of the hatchings. */
	@FXML private Spinner<Double> hatchSepField;
	/** Changes the angle of the hatchings. */
	@FXML private Spinner<Double> hatchAngleField;
	/** Changes the width of the hatchings. */
	@FXML private Spinner<Double> hatchWidthField;
	@FXML private AnchorPane fillPane;
	@FXML private AnchorPane hatchingsPane;
	@FXML private AnchorPane gradientPane;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeFillingCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		fillPane.managedProperty().bind(fillPane.visibleProperty());
		hatchingsPane.managedProperty().bind(hatchingsPane.visibleProperty());
		gradientPane.managedProperty().bind(gradientPane.visibleProperty());

		final Map<FillingStyle, Image> cache = new EnumMap<>(FillingStyle.class);
		cache.put(FillingStyle.NONE, new Image("/res/hatch/hatch.none.png"));
		cache.put(FillingStyle.PLAIN, new Image("/res/hatch/hatch.solid.png"));
		cache.put(FillingStyle.CLINES, new Image("/res/hatch/hatch.cross.png"));
		cache.put(FillingStyle.CLINES_PLAIN, new Image("/res/hatch/hatchf.cross.png"));
		cache.put(FillingStyle.HLINES, new Image("/res/hatch/hatch.horiz.png"));
		cache.put(FillingStyle.HLINES_PLAIN, new Image("/res/hatch/hatchf.horiz.png"));
		cache.put(FillingStyle.VLINES, new Image("/res/hatch/hatch.vert.png"));
		cache.put(FillingStyle.VLINES_PLAIN, new Image("/res/hatch/hatchf.vert.png"));
		cache.put(FillingStyle.GRAD, new Image("/res/hatch/gradient.png"));
		initComboBox(fillStyleCB, cache, FillingStyle.values());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isInteriorStylable()) {
			setActivated(true);
			final FillingStyle style = shape.getFillingStyle();
			final boolean isFillable = style.isFilled();
			final boolean hatchings = style.isHatchings();
			final boolean gradient = style.isGradient();

			// Updating the visibility of the widgets.
			gradientPane.setVisible(gradient);
			hatchingsPane.setVisible(hatchings);
			fillPane.setVisible(isFillable);

			fillStyleCB.getSelectionModel().select(style);
			if(isFillable) {
				fillColButton.setValue(shape.getFillingCol().toJFX());
			}
			if(hatchings) {
				hatchColButton.setValue(shape.getHatchingsCol().toJFX());
				hatchAngleField.getValueFactory().setValue(Math.toDegrees(shape.getHatchingsAngle()));
				hatchSepField.getValueFactory().setValue(shape.getHatchingsSep());
				hatchWidthField.getValueFactory().setValue(shape.getHatchingsWidth());
			}else if(gradient) {
				gradStartColButton.setValue(shape.getGradColStart().toJFX());
				gradEndColButton.setValue(shape.getGradColEnd().toJFX());
				gradAngleField.getValueFactory().setValue(Math.toDegrees(shape.getGradAngle()));
				gradMidPtField.getValueFactory().setValue(shape.getGradMidPt());
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
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		addBinding(new List4Pencil(this, fillStyleCB, ShapeProperties.FILLING_STYLE));
		addBinding(new List4Selection(this, fillStyleCB, ShapeProperties.FILLING_STYLE));
		addBinding(new ColourPicker4Selection(this, fillColButton, ShapeProperties.COLOUR_FILLING));
		addBinding(new ColourPicker4Selection(this, gradStartColButton, ShapeProperties.COLOUR_GRADIENT_START));
		addBinding(new ColourPicker4Selection(this, gradEndColButton, ShapeProperties.COLOUR_GRADIENT_END));
		addBinding(new ColourPicker4Selection(this, hatchColButton, ShapeProperties.COLOUR_HATCHINGS));
		addBinding(new ColourPicker4Pencil(this, fillColButton, ShapeProperties.COLOUR_FILLING));
		addBinding(new ColourPicker4Pencil(this, gradStartColButton, ShapeProperties.COLOUR_GRADIENT_START));
		addBinding(new ColourPicker4Pencil(this, gradEndColButton, ShapeProperties.COLOUR_GRADIENT_END));
		addBinding(new ColourPicker4Pencil(this, hatchColButton, ShapeProperties.COLOUR_HATCHINGS));
		addBinding(new Spinner4Pencil(this, gradMidPtField, ShapeProperties.GRAD_MID_POINT, false));
		addBinding(new Spinner4Pencil(this, gradAngleField, ShapeProperties.GRAD_ANGLE, true));
		addBinding(new Spinner4Pencil(this, hatchAngleField, ShapeProperties.HATCHINGS_ANGLE, true));
		addBinding(new Spinner4Pencil(this, hatchWidthField, ShapeProperties.HATCHINGS_WIDTH, false));
		addBinding(new Spinner4Pencil(this, hatchSepField, ShapeProperties.HATCHINGS_SEP, false));
		addBinding(new Spinner4Selection(this, gradMidPtField, ShapeProperties.GRAD_MID_POINT, false));
		addBinding(new Spinner4Selection(this, gradAngleField, ShapeProperties.GRAD_ANGLE, true));
		addBinding(new Spinner4Selection(this, hatchAngleField, ShapeProperties.HATCHINGS_ANGLE, true));
		addBinding(new Spinner4Selection(this, hatchWidthField, ShapeProperties.HATCHINGS_WIDTH, false));
		addBinding(new Spinner4Selection(this, hatchSepField, ShapeProperties.HATCHINGS_SEP, false));
	}
}
