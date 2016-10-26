package net.sf.latexdraw.instruments;

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
import org.malai.javafx.instrument.library.SpinnerInteractor;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This instrument modifies the parameters of grids and axes.<br>
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
 * 12/23/2011<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeStdGridCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The field that sets the X-coordinate of the starting point of the grid. */
	@FXML protected Spinner<Double> xStartS;

	/** The field that sets the Y-coordinate of the starting point of the grid. */
	@FXML protected Spinner<Double> yStartS;

	/** The field that sets the X-coordinate of the ending point of the grid. */
	@FXML protected Spinner<Double> xEndS;

	/** The field that sets the Y-coordinate of the ending point of the grid. */
	@FXML protected Spinner<Double> yEndS;

	/** The field that sets the size of the labels of the grid. */
	@FXML protected Spinner<Integer> labelsSizeS;

	/** The field that sets the X-coordinate of the origin point of the grid. */
	@FXML protected Spinner<Double> xOriginS;

	/** The field that sets the Y-coordinate of the origin point of the grid. */
	@FXML protected Spinner<Double> yOriginS;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeStdGridCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
		((DoubleSpinnerValueFactory)xStartS.getValueFactory()).maxProperty().bind(xEndS.valueProperty());
		((DoubleSpinnerValueFactory)yStartS.getValueFactory()).maxProperty().bind(yEndS.valueProperty());
		((DoubleSpinnerValueFactory)xEndS.getValueFactory()).minProperty().bind(xStartS.valueProperty());
		((DoubleSpinnerValueFactory)yEndS.getValueFactory()).minProperty().bind(yStartS.valueProperty());
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
		}else
			setActivated(false);
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
		addInteractor(new Spinner4Pencil(this, labelsSizeS, ShapeProperties.GRID_SIZE_LABEL, false));
		addInteractor(new Spinner4Selection(this, labelsSizeS, ShapeProperties.GRID_SIZE_LABEL, false));
		addInteractor(new Spinner4StdGridPencil(xEndS, ShapeProperties.GRID_END));
		addInteractor(new Spinner4StdGridPencil(yEndS, ShapeProperties.GRID_END));
		addInteractor(new Spinner4StdGridHand(xEndS, ShapeProperties.GRID_END));
		addInteractor(new Spinner4StdGridHand(yEndS, ShapeProperties.GRID_END));
		addInteractor(new Spinner4StdGridPencil(xStartS, ShapeProperties.GRID_START));
		addInteractor(new Spinner4StdGridPencil(yStartS, ShapeProperties.GRID_START));
		addInteractor(new Spinner4StdGridHand(xStartS, ShapeProperties.GRID_START));
		addInteractor(new Spinner4StdGridHand(yStartS, ShapeProperties.GRID_START));
		addInteractor(new Spinner4StdGridPencil(xOriginS, ShapeProperties.GRID_ORIGIN));
		addInteractor(new Spinner4StdGridPencil(yOriginS, ShapeProperties.GRID_ORIGIN));
		addInteractor(new Spinner4StdGridHand(xOriginS, ShapeProperties.GRID_ORIGIN));
		addInteractor(new Spinner4StdGridHand(yOriginS, ShapeProperties.GRID_ORIGIN));
	}

	class Spinner4StdGridPencil extends SpinnerInteractor<ModifyPencilParameter, ShapeStdGridCustomiser> {
		final ShapeProperties prop;

		Spinner4StdGridPencil(final Spinner<?> widget, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ShapeStdGridCustomiser.this, ModifyPencilParameter.class, widget);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setPencil(instrument.pencil);
			if(prop == ShapeProperties.GRID_END)
				action.setValue(ShapeFactory.createPoint(xEndS.getValue(), yEndS.getValue()));
			else if(prop == ShapeProperties.GRID_START)
				action.setValue(ShapeFactory.createPoint(xStartS.getValue(), yStartS.getValue()));
			else
				action.setValue(ShapeFactory.createPoint(xOriginS.getValue(), yOriginS.getValue()));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	class Spinner4StdGridHand extends SpinnerInteractor<ModifyShapeProperty, ShapeStdGridCustomiser> {
		final ShapeProperties prop;

		Spinner4StdGridHand(final Spinner<?> widget, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ShapeStdGridCustomiser.this, ModifyShapeProperty.class, widget);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
			if(prop == ShapeProperties.GRID_END)
				action.setValue(ShapeFactory.createPoint(xEndS.getValue(), yEndS.getValue()));
			else if(prop == ShapeProperties.GRID_START)
				action.setValue(ShapeFactory.createPoint(xStartS.getValue(), yStartS.getValue()));
			else
				action.setValue(ShapeFactory.createPoint(xOriginS.getValue(), yOriginS.getValue()));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}
}
