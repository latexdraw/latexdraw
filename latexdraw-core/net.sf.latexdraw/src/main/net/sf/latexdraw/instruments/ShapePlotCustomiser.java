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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;

/**
 * This instrument modifies plot parameters.
 * @author Arnaud BLOUIN
 */
public class ShapePlotCustomiser extends ShapePropertyCustomiser implements Initializable {
	@FXML Spinner<Integer> nbPtsSpinner;
	@FXML Spinner<Double> minXSpinner;
	@FXML Spinner<Double> maxXSpinner;
	@FXML Spinner<Double> xScaleSpinner;
	@FXML Spinner<Double> yScaleSpinner;
	@FXML CheckBox polarCB;
	@FXML ComboBox<PlotStyle> plotStyleCB;
	@FXML TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	ShapePlotCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		plotStyleCB.getItems().addAll(PlotStyle.values());
		((DoubleSpinnerValueFactory) minXSpinner.getValueFactory()).maxProperty().bind(maxXSpinner.valueProperty());
		((DoubleSpinnerValueFactory) maxXSpinner.getValueFactory()).minProperty().bind(minXSpinner.valueProperty());

		scrollOnSpinner(nbPtsSpinner);
		scrollOnSpinner(minXSpinner);
		scrollOnSpinner(maxXSpinner);
		scrollOnSpinner(xScaleSpinner);
		scrollOnSpinner(yScaleSpinner);
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IPlotProp.class)) {
			nbPtsSpinner.getValueFactory().setValue(shape.getNbPlottedPoints());
			minXSpinner.getValueFactory().setValue(shape.getPlotMinX());
			maxXSpinner.getValueFactory().setValue(shape.getPlotMaxX());
			xScaleSpinner.getValueFactory().setValue(shape.getXScale());
			yScaleSpinner.getValueFactory().setValue(shape.getYScale());
			polarCB.setSelected(shape.isPolar());
			plotStyleCB.getSelectionModel().select(shape.getPlotStyle());
			setActivated(true);
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
		addInteractor(new List4Pencil(this, plotStyleCB, ShapeProperties.PLOT_STYLE));
		addInteractor(new List4Selection(this, plotStyleCB, ShapeProperties.PLOT_STYLE));
		addInteractor(new Spinner4Pencil(this, nbPtsSpinner, ShapeProperties.PLOT_NB_PTS, false));
		addInteractor(new Spinner4Selection(this, nbPtsSpinner, ShapeProperties.PLOT_NB_PTS, false));
		addInteractor(new Spinner4Pencil(this, minXSpinner, ShapeProperties.PLOT_MIN_X, false));
		addInteractor(new Spinner4Selection(this, minXSpinner, ShapeProperties.PLOT_MIN_X, false));
		addInteractor(new Spinner4Pencil(this, maxXSpinner, ShapeProperties.PLOT_MAX_X, false));
		addInteractor(new Spinner4Selection(this, maxXSpinner, ShapeProperties.PLOT_MAX_X, false));
		addInteractor(new Spinner4Pencil(this, xScaleSpinner, ShapeProperties.X_SCALE, false));
		addInteractor(new Spinner4Selection(this, xScaleSpinner, ShapeProperties.X_SCALE, false));
		addInteractor(new Spinner4Pencil(this, yScaleSpinner, ShapeProperties.Y_SCALE, false));
		addInteractor(new Spinner4Selection(this, yScaleSpinner, ShapeProperties.Y_SCALE, false));
		addInteractor(new Checkbox4Pencil(this, polarCB, ShapeProperties.PLOT_POLAR));
		addInteractor(new Checkbox4Selection(this, polarCB, ShapeProperties.PLOT_POLAR));
	}
}
