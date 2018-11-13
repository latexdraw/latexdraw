/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.api.property.PlotProp;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.parser.ps.PSFunctionParser;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.Tuple;

/**
 * This instrument modifies plot parameters.
 * @author Arnaud BLOUIN
 */
public class ShapePlotCustomiser extends ShapePropertyCustomiser implements Initializable {
	@FXML Spinner<Integer> nbPtsSpinner;
	@FXML Spinner<Double> minXSpinner;
	@FXML Spinner<Double> maxXSpinner;
	@FXML private Spinner<Double> xScaleSpinner;
	@FXML private Spinner<Double> yScaleSpinner;
	@FXML private CheckBox polarCB;
	@FXML private ComboBox<PlotStyle> plotStyleCB;
	@FXML private TitledPane mainPane;
	@Inject private ResourceBundle lang;

	/**
	 * Creates the instrument.
	 */
	public ShapePlotCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		plotStyleCB.getItems().addAll(PlotStyle.values());
		((DoubleSpinnerValueFactory) minXSpinner.getValueFactory()).maxProperty().bind(maxXSpinner.valueProperty());
		((DoubleSpinnerValueFactory) maxXSpinner.getValueFactory()).minProperty().bind(minXSpinner.valueProperty());
	}

	@Override
	protected void update(final Group shape) {
		if(shape.isTypeOf(PlotProp.class)) {
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

	private boolean checkValidPlotFct() {
		Tuple<Boolean, String> valid;
		try {
			valid = PSFunctionParser.isValidPostFixEquation(canvas.getDrawing().getSelection().getPlotEquation(),
				Double.parseDouble(minXSpinner.getValue().toString()), Double.parseDouble(maxXSpinner.getValue().toString()),
				Double.parseDouble(nbPtsSpinner.getValue().toString()));
		}catch(final IllegalArgumentException ex) {
			valid = new Tuple<>(Boolean.FALSE, lang.getString("invalid.function"));
		}
		return valid.a;
	}

	@Override
	protected void configureBindings() {
		addComboPropBinding(plotStyleCB, ShapeProperties.PLOT_STYLE);
		addSpinnerPropBinding(nbPtsSpinner, ShapeProperties.PLOT_NB_PTS);

		spinnerBinder(i -> mapModShProp(null, ShapeProperties.PLOT_MIN_X)).on(minXSpinner).
			then((i, c) -> c.setValue((Double) i.getWidget().getValue())).
			when(i -> hand.isActivated() && checkValidPlotFct()).bind();

		spinnerBinder(i -> firstPropPen(null, ShapeProperties.PLOT_MIN_X)).on(minXSpinner).
			then((i, c) -> c.setValue((Double) i.getWidget().getValue())).
			when(pencilActiv).bind();

		spinnerBinder(i -> mapModShProp(null, ShapeProperties.PLOT_MAX_X)).on(maxXSpinner).
			then((i, c) -> c.setValue((Double) i.getWidget().getValue())).
			when(i -> hand.isActivated() && checkValidPlotFct()).bind();

		spinnerBinder(i -> firstPropPen(null, ShapeProperties.PLOT_MAX_X)).on(maxXSpinner).
			then((i, c) -> c.setValue((Double) i.getWidget().getValue())).
			when(pencilActiv).bind();

		addSpinnerPropBinding(xScaleSpinner, ShapeProperties.X_SCALE);
		addSpinnerPropBinding(yScaleSpinner, ShapeProperties.Y_SCALE);
		addCheckboxPropBinding(polarCB, ShapeProperties.PLOT_POLAR);
	}
}
