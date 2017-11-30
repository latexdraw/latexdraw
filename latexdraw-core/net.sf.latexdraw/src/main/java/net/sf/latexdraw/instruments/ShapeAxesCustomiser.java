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
import javafx.scene.layout.Pane;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;

/**
 * This instrument modifies axes properties of shapes or the pencil.
 * @author Arnaud BLOUIN
 */
public class ShapeAxesCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The widget that permits to select the style of the axes. */
	@FXML private ComboBox<AxesStyle> shapeAxes;
	/** The widget that permits to select the style of the ticks. */
	@FXML private ComboBox<TicksStyle> shapeTicks;
	/** The widget that permits to show/hide the ticks of the axes. */
	@FXML private ComboBox<PlottingStyle> showTicks;
	/** The widget that permits to set the increment of X-labels. */
	@FXML private Spinner<Double> incrLabelX;
	/** The widget that permits to set the increment of Y-labels. */
	@FXML private Spinner<Double> incrLabelY;
	/** The widget that permits to set the visibility of the labels. */
	@FXML private ComboBox<PlottingStyle> showLabels;
	/** The widget that permits to set the visibility of the origin point. */
	@FXML private CheckBox showOrigin;
	/** The distance between the X-labels. */
	@FXML private Spinner<Double> distLabelsX;
	/** The distance between the Y-labels. */
	@FXML private Spinner<Double> distLabelsY;
	@FXML private Pane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeAxesCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());

		shapeAxes.getItems().addAll(AxesStyle.values());
		shapeTicks.getItems().addAll(TicksStyle.values());
		showTicks.getItems().addAll(PlottingStyle.values());
		showLabels.getItems().addAll(PlottingStyle.values());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IAxesProp.class)) {
			shapeAxes.getSelectionModel().select(shape.getAxesStyle());
			shapeTicks.getSelectionModel().select(shape.getTicksStyle());
			showTicks.getSelectionModel().select(shape.getTicksDisplayed());
			incrLabelX.getValueFactory().setValue(shape.getIncrementX());
			incrLabelY.getValueFactory().setValue(shape.getIncrementY());
			showLabels.getSelectionModel().select(shape.getLabelsDisplayed());
			showOrigin.setSelected(shape.isShowOrigin());
			distLabelsX.getValueFactory().setValue(shape.getDistLabelsX());
			distLabelsY.getValueFactory().setValue(shape.getDistLabelsY());
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
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		addComboPropBinding(shapeAxes, ShapeProperties.AXES_STYLE);
		addComboPropBinding(showTicks, ShapeProperties.AXES_TICKS_SHOW);
		addComboPropBinding(showLabels, ShapeProperties.AXES_LABELS_SHOW);
		addComboPropBinding(shapeTicks, ShapeProperties.AXES_TICKS_STYLE);

		addSpinnerAxesPropBinding(incrLabelX, incrLabelY, ShapeProperties.AXES_LABELS_INCR);
		addSpinnerAxesPropBinding(distLabelsX, distLabelsY, ShapeProperties.AXES_LABELS_DIST);

		addCheckboxPropBinding(showOrigin, ShapeProperties.AXES_SHOW_ORIGIN);
	}

	private void addSpinnerAxesPropBinding(final Spinner<Double> spinnerX, final Spinner<Double> spinnerY, final ShapeProperties property)
											throws InstantiationException, IllegalAccessException {
		spinnerBinder(ModifyShapeProperty.class).on(spinnerX, spinnerY).first(a -> {
			a.setGroup(canvas.getDrawing().getSelection().duplicateDeep(false));
			a.setProperty(property);
		}).then(a -> a.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue()))).
			when(handActiv).bind();

		spinnerBinder(ModifyPencilParameter.class).on(spinnerX, spinnerY).first(a -> {
			a.setPencil(pencil);
			a.setProperty(property);
		}).then(a -> a.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue()))).
			when(pencilActiv).bind();
	}
}
