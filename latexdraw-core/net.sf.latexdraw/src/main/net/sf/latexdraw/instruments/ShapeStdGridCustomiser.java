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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.malai.javafx.binding.SpinnerBinding;

/**
 * This instrument modifies the parameters of grids and axes.
 * @author Arnaud BLOUIN
 */
public class ShapeStdGridCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The field that sets the X-coordinate of the starting point of the grid. */
	@FXML private Spinner<Double> xStartS;
	/** The field that sets the Y-coordinate of the starting point of the grid. */
	@FXML private Spinner<Double> yStartS;
	/** The field that sets the X-coordinate of the ending point of the grid. */
	@FXML private Spinner<Double> xEndS;
	/** The field that sets the Y-coordinate of the ending point of the grid. */
	@FXML private Spinner<Double> yEndS;
	/** The field that sets the size of the labels of the grid. */
	@FXML private Spinner<Integer> labelsSizeS;
	/** The field that sets the X-coordinate of the origin point of the grid. */
	@FXML private Spinner<Double> xOriginS;
	/** The field that sets the Y-coordinate of the origin point of the grid. */
	@FXML private Spinner<Double> yOriginS;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	ShapeStdGridCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		((DoubleSpinnerValueFactory) xStartS.getValueFactory()).maxProperty().bind(xEndS.valueProperty());
		((DoubleSpinnerValueFactory) yStartS.getValueFactory()).maxProperty().bind(yEndS.valueProperty());
		((DoubleSpinnerValueFactory) xEndS.getValueFactory()).minProperty().bind(xStartS.valueProperty());
		((DoubleSpinnerValueFactory) yEndS.getValueFactory()).minProperty().bind(yStartS.valueProperty());

		scrollOnSpinner(yOriginS);
		scrollOnSpinner(xOriginS);
		scrollOnSpinner(labelsSizeS);
		scrollOnSpinner(yEndS);
		scrollOnSpinner(xEndS);
		scrollOnSpinner(yStartS);
		scrollOnSpinner(xStartS);
	}

	@Override
	protected void update(final IGroup gp) {
		if(gp.isTypeOf(IStdGridProp.class)) {
			xStartS.getValueFactory().setValue(gp.getGridStartX());
			yStartS.getValueFactory().setValue(gp.getGridStartY());
			xEndS.getValueFactory().setValue(gp.getGridEndX());
			yEndS.getValueFactory().setValue(gp.getGridEndY());
			xOriginS.getValueFactory().setValue(gp.getOriginX());
			yOriginS.getValueFactory().setValue(gp.getOriginY());
			labelsSizeS.getValueFactory().setValue(gp.getLabelsSize());
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
		addBinding(new Spinner4Pencil(this, labelsSizeS, ShapeProperties.GRID_SIZE_LABEL, false));
		addBinding(new Spinner4Selection(this, labelsSizeS, ShapeProperties.GRID_SIZE_LABEL, false));
		addBinding(new Spinner4StdGridPencil(xEndS, ShapeProperties.GRID_END));
		addBinding(new Spinner4StdGridPencil(yEndS, ShapeProperties.GRID_END));
		addBinding(new Spinner4StdGridHand(xEndS, ShapeProperties.GRID_END));
		addBinding(new Spinner4StdGridHand(yEndS, ShapeProperties.GRID_END));
		addBinding(new Spinner4StdGridPencil(xStartS, ShapeProperties.GRID_START));
		addBinding(new Spinner4StdGridPencil(yStartS, ShapeProperties.GRID_START));
		addBinding(new Spinner4StdGridHand(xStartS, ShapeProperties.GRID_START));
		addBinding(new Spinner4StdGridHand(yStartS, ShapeProperties.GRID_START));
		addBinding(new Spinner4StdGridPencil(xOriginS, ShapeProperties.GRID_ORIGIN));
		addBinding(new Spinner4StdGridPencil(yOriginS, ShapeProperties.GRID_ORIGIN));
		addBinding(new Spinner4StdGridHand(xOriginS, ShapeProperties.GRID_ORIGIN));
		addBinding(new Spinner4StdGridHand(yOriginS, ShapeProperties.GRID_ORIGIN));
	}

	class Spinner4StdGridPencil extends SpinnerBinding<ModifyPencilParameter, ShapeStdGridCustomiser> {
		final ShapeProperties prop;

		Spinner4StdGridPencil(final Spinner<?> widget, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ShapeStdGridCustomiser.this, true, ModifyPencilParameter.class, widget);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setPencil(instrument.pencil);
			if(prop == ShapeProperties.GRID_END) {
				action.setValue(ShapeFactory.INST.createPoint(xEndS.getValue(), yEndS.getValue()));
			}
			else {
				if(prop == ShapeProperties.GRID_START) {
					action.setValue(ShapeFactory.INST.createPoint(xStartS.getValue(), yStartS.getValue()));
				}
				else {
					action.setValue(ShapeFactory.INST.createPoint(xOriginS.getValue(), yOriginS.getValue()));
				}
			}
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	class Spinner4StdGridHand extends SpinnerBinding<ModifyShapeProperty, ShapeStdGridCustomiser> {
		final ShapeProperties prop;

		Spinner4StdGridHand(final Spinner<?> widget, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ShapeStdGridCustomiser.this, true, ModifyShapeProperty.class, widget);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setGroup(instrument.drawing.getSelection().duplicateDeep(false));
			if(prop == ShapeProperties.GRID_END) {
				action.setValue(ShapeFactory.INST.createPoint(xEndS.getValue(), yEndS.getValue()));
			}
			else {
				if(prop == ShapeProperties.GRID_START) {
					action.setValue(ShapeFactory.INST.createPoint(xStartS.getValue(), yStartS.getValue()));
				}
				else {
					action.setValue(ShapeFactory.INST.createPoint(xOriginS.getValue(), yOriginS.getValue()));
				}
			}
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}
}
