package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

/**
 * This instrument modifies the parameters of grids and axes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
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
	protected void initialiseInteractors() {
		// try{
		// addInteractor(new Spinner2ModifySelectionGridCoords(this));
		// addInteractor(new Spinner2ModifyPencilGridCoords(this));
		// }catch(InstantiationException | IllegalAccessException e){
		// BadaboomCollector.INSTANCE.add(e);
		// }
	}

	// /** The link that maps a spinner to an action that modifies the selected
	// shapes. */
	// private static class Spinner2ModifySelectionGridCoords extends
	// Spinner2ModifyGridCoords<ModifyShapeProperty> {
	// protected Spinner2ModifySelectionGridCoords(final
	// ShapeStandardGridCustomiser ins) throws InstantiationException,
	// IllegalAccessException {
	// super(ins, ModifyShapeProperty.class);
	// }
	//
	// @Override
	// public void initAction() {
	// super.initAction();
	// action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
	// }
	//
	// @Override
	// public boolean isConditionRespected() {
	// return instrument.hand.isActivated() && super.isConditionRespected();
	// }
	// }
	//
	//
	// /** The link that maps a spinner to an action that modifies the pencil.
	// */
	// private static class Spinner2ModifyPencilGridCoords extends
	// Spinner2ModifyGridCoords<ModifyPencilParameter> {
	// protected Spinner2ModifyPencilGridCoords(final
	// ShapeStandardGridCustomiser ins) throws InstantiationException,
	// IllegalAccessException {
	// super(ins, ModifyPencilParameter.class);
	// }
	//
	// @Override
	// public void initAction() {
	// super.initAction();
	// action.setPencil(instrument.pencil);
	// }
	//
	// @Override
	// public boolean isConditionRespected() {
	// return instrument.pencil.isActivated() && super.isConditionRespected();
	// }
	// }
	//
	//
	// private abstract static class Spinner2ModifyGridCoords<A extends
	// ShapePropertyAction> extends SpinnerForCustomiser<A,
	// ShapeStandardGridCustomiser> {
	// protected Spinner2ModifyGridCoords(final ShapeStandardGridCustomiser ins,
	// final Class<A> clazzAction) throws InstantiationException,
	// IllegalAccessException {
	// super(ins, clazzAction);
	// }
	//
	// @Override
	// public void initAction() {
	// if(isOriginSpinner())
	// action.setProperty(ShapeProperties.GRID_ORIGIN);
	// else if(isLabelSizeSpinner())
	// action.setProperty(ShapeProperties.GRID_SIZE_LABEL);
	// else if(isStartGridSpinner())
	// action.setProperty(ShapeProperties.GRID_START);
	// else
	// action.setProperty(ShapeProperties.GRID_END);
	// }
	//
	// @Override
	// public void updateAction() {
	// if(isOriginSpinner())
	// action.setValue(ShapeFactory.createPoint(
	// Double.parseDouble(instrument.xOriginS.getValue().toString()),
	// Double.parseDouble(instrument.yOriginS.getValue().toString())));
	// else if(isLabelSizeSpinner())
	// action.setValue(Integer.parseInt(instrument.labelsSizeS.getValue().toString()));
	// else if(isStartGridSpinner())
	// action.setValue(ShapeFactory.createPoint(
	// Double.parseDouble(instrument.xStartS.getValue().toString()),
	// Double.parseDouble(instrument.yStartS.getValue().toString())));
	// else
	// action.setValue(ShapeFactory.createPoint(
	// Double.parseDouble(instrument.xEndS.getValue().toString()),
	// Double.parseDouble(instrument.yEndS.getValue().toString())));
	// }
	//
	//
	// private boolean isStartGridSpinner() {
	// return interaction.getSpinner()==instrument.xStartS ||
	// interaction.getSpinner()==instrument.yStartS;
	// }
	//
	// private boolean isEndGridSpinner() {
	// return interaction.getSpinner()==instrument.xEndS ||
	// interaction.getSpinner()==instrument.yEndS;
	// }
	//
	// private boolean isLabelSizeSpinner() {
	// return interaction.getSpinner()==instrument.labelsSizeS;
	// }
	//
	// private boolean isOriginSpinner() {
	// return interaction.getSpinner()==instrument.xOriginS ||
	// interaction.getSpinner()==instrument.yOriginS;
	// }
	//
	//
	// @Override
	// public boolean isConditionRespected() {
	// return isStartGridSpinner() || isEndGridSpinner() || isLabelSizeSpinner()
	// || isOriginSpinner();
	// }
	// }
}
