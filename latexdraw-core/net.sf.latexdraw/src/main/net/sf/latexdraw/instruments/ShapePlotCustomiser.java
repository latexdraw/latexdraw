package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument modifies plot parameters.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-10-05<br>
 * @author Arnaud BLOUIN
 * @since 3.1
 */
public class ShapePlotCustomiser extends JfxInstrument implements Initializable {// extends ShapePropertyCustomiser {
	@FXML Spinner<Integer> nbPtsSpinner;
	@FXML Spinner<Double> minXSpinner;
	@FXML Spinner<Double> maxXSpinner;
	@FXML Spinner<Double> xScaleSpinner;
	@FXML Spinner<Double> yScaleSpinner;
	@FXML CheckBox polarCB;
	@FXML ComboBox<IPlotProp.PlotStyle> plotStyleCB;
	@FXML TitledPane mainPane;

	
	/**
	 * Creates the instrument.
	 */
	public ShapePlotCustomiser() {
		super();
	}

	
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		plotStyleCB.getItems().addAll(IPlotProp.PlotStyle.values());
		((DoubleSpinnerValueFactory)minXSpinner.getValueFactory()).maxProperty().bind(maxXSpinner.valueProperty());
		((DoubleSpinnerValueFactory)maxXSpinner.getValueFactory()).minProperty().bind(minXSpinner.valueProperty());
	}
	
	
//	@Override
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
		}
		else setActivated(false);
	}

//	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new Spinner2PencilPlot(this));
//			addInteractor(new Spinner2SelectionPlot(this));
//			addInteractor(new CheckBox2PencilPlot(this));
//			addInteractor(new CheckBox2SelectionPlot(this));
//			addInteractor(new Combobox2CustomPencilPlot(this));
//			addInteractor(new Combobox2CustomSelectionPlot(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}

//	private abstract static class SpinnerForPlotCust<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapePlotCustomiser> {
//		protected SpinnerForPlotCust(final ShapePlotCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(instrument, clazzAction);
//		}
//
//		@Override
//		public void initAction() {
//			final Object spinner = interaction.getSpinner();
//			if(spinner==instrument.nbPtsSpinner)
//				action.setProperty(ShapeProperties.PLOT_NB_PTS);
//			else if(spinner==instrument.minXSpinner)
//				action.setProperty(ShapeProperties.PLOT_MIN_X);
//			else if(spinner==instrument.maxXSpinner)
//				action.setProperty(ShapeProperties.PLOT_MAX_X);
//			else if(spinner==instrument.xScaleSpinner)
//				action.setProperty(ShapeProperties.X_SCALE);
//			else if(spinner==instrument.yScaleSpinner)
//				action.setProperty(ShapeProperties.Y_SCALE);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			final Object spinner = interaction.getSpinner();
//			return spinner==instrument.nbPtsSpinner || spinner==instrument.maxXSpinner || spinner==instrument.minXSpinner ||
//					spinner==instrument.xScaleSpinner || spinner==instrument.yScaleSpinner;
//		}
//
//		@Override
//		public void updateAction() {
//			if(interaction.getSpinner()==instrument.nbPtsSpinner)
//				action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
//			else
//				super.updateAction();
//		}
//	}
//
//
//	private static class Spinner2PencilPlot extends SpinnerForPlotCust<ModifyPencilParameter> {
//		protected Spinner2PencilPlot(final ShapePlotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override public boolean isConditionRespected() { return instrument.pencil.isActivated() && super.isConditionRespected();}
//	}
//
//
//	private static class Spinner2SelectionPlot extends SpinnerForPlotCust<ModifyShapeProperty> {
//		protected Spinner2SelectionPlot(final ShapePlotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override public boolean isConditionRespected() { 
//			return instrument.hand.isActivated() && super.isConditionRespected() &&
//					PSFunctionParser.isValidPostFixEquation(instrument.pencil.canvas().getDrawing().getSelection().getPlotEquation(), 
//							Double.valueOf(instrument.getMinXSpinner().getValue().toString()), 
//							Double.valueOf(instrument.getMaxXSpinner().getValue().toString()), 
//							Double.valueOf(instrument.getNbPtsSpinner().getValue().toString()));
//		}
//	}
//
//	private static class CheckBox2PencilPlot extends CheckBoxForCustomiser<ModifyPencilParameter, ShapePlotCustomiser> {
//		CheckBox2PencilPlot(final ShapePlotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setProperty(ShapeProperties.PLOT_POLAR);
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getCheckBox()==instrument.polarCB && instrument.pencil.isActivated();
//		}
//	}
//
//	private static class CheckBox2SelectionPlot extends CheckBoxForCustomiser<ModifyShapeProperty, ShapePlotCustomiser> {
//		CheckBox2SelectionPlot(final ShapePlotCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//			action.setProperty(ShapeProperties.PLOT_POLAR);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getCheckBox()==instrument.polarCB && instrument.hand.isActivated();
//		}
//	}
//
//	private static class Combobox2CustomPencilPlot extends ListForCustomiser<ModifyPencilParameter, ShapePlotCustomiser> {
//		Combobox2CustomPencilPlot(final ShapePlotCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.PLOT_STYLE);
//			action.setValue(interaction.getList().getSelectedObjects()[0]);
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.pencil.isActivated() && instrument.plotStyleCB==interaction.getList();
//		}
//	}
//
//	private static class Combobox2CustomSelectionPlot extends ListForCustomiser<ModifyShapeProperty, ShapePlotCustomiser> {
//		Combobox2CustomSelectionPlot(final ShapePlotCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.PLOT_STYLE);
//			action.setValue(interaction.getList().getSelectedObjects()[0]);
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.hand.isActivated() && instrument.plotStyleCB==interaction.getList();
//		}
//	}
}
