package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument modifies axes properties of shapes or the pencil.<br>
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
 * 2012-04-05<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeAxesCustomiser extends JfxInstrument implements Initializable { // extends ShapePropertyCustomiser {
	/** The widget that permits to select the style of the axes. */
	@FXML protected ComboBox<AxesStyle> shapeAxes;

	/** The widget that permits to select the style of the ticks. */
	@FXML protected ComboBox<TicksStyle> shapeTicks;

	/** The widget that permits to show/hide the ticks of the axes. */
	@FXML protected ComboBox<PlottingStyle> showTicks;

	/** The widget that permits to set the increment of X-labels. */
	@FXML protected Spinner<Double> incrLabelX;

	/** The widget that permits to set the increment of Y-labels. */
	@FXML protected Spinner<Double> incrLabelY;

	/** The widget that permits to set the visibility of the labels. */
	@FXML protected ComboBox<PlottingStyle> showLabels;

	/** The widget that permits to set the visibility of the origin point. */
	@FXML protected CheckBox showOrigin;

	/** The distance between the X-labels. */
	@FXML protected Spinner<Double> distLabelsX;

	/** The distance between the Y-labels. */
	@FXML protected Spinner<Double> distLabelsY;
	
	@FXML protected AnchorPane mainPane;


	/**
	 * Creates the instrument.
	 */
	public ShapeAxesCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		shapeAxes.getItems().addAll(IAxesProp.AxesStyle.values());
		shapeTicks.getItems().addAll(IAxesProp.TicksStyle.values());
		showTicks.getItems().addAll(IAxesProp.PlottingStyle.values());
		showLabels.getItems().addAll(IAxesProp.PlottingStyle.values());
	}

//	@Override
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
		}
		else setActivated(false);
	}


//	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


	@Override
	protected void initialiseInteractors() {
//		try {
//			addInteractor(new Combobox2CustomSelectedAxes(this));
//			addInteractor(new Combobox2CustomPencilAxes(this));
//			addInteractor(new Spinner2CustomPencilAxes(this));
//			addInteractor(new Spinner2CustomSelectedAxes(this));
//			addInteractor(new CheckBox2CustomPencilAxes(this));
//			addInteractor(new CheckBox2CustomSelectedAxes(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}


//	/** Maps a checkbox to an action that modifies several axes' parameters. */
//    private abstract static class CheckBox2CustomAxes<A extends ShapePropertyAction> extends CheckBoxForCustomiser<A, ShapeAxesCustomiser> {
//		protected CheckBox2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(ins, clazzAction);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.showOrigin==interaction.getCheckBox();
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.AXES_SHOW_ORIGIN);
//		}
//
//		@Override
//		public void updateAction() {
//			action.setValue(interaction.getCheckBox().isSelected());
//		}
//	}
//
//
//	/** Maps a spinner to an action that modifies the ticks size of the selected shapes. */
//	private static class CheckBox2CustomSelectedAxes extends CheckBox2CustomAxes<ModifyShapeProperty> {
//		protected CheckBox2CustomSelectedAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.hand.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//	/** Maps a spinner to an action that modifies the ticks size of the pencil. */
//	private static class CheckBox2CustomPencilAxes extends CheckBox2CustomAxes<ModifyPencilParameter> {
//		protected CheckBox2CustomPencilAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.pencil.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//	/** Maps a spinner to an action that modifies several axes' parameters. */
//    private abstract static class Spinner2CustomAxes<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeAxesCustomiser> {
//		protected Spinner2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(ins, clazzAction);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			final Object spinner = interaction.getSpinner();
//			return instrument.incrLabelX==spinner || instrument.incrLabelY==spinner ||
//				   instrument.distLabelsX==spinner || instrument.distLabelsY==spinner;
//		}
//
//		@Override
//		public void initAction() {
//			final Object spinner = interaction.getSpinner();
//
//			if(spinner==instrument.distLabelsX || spinner==instrument.distLabelsY)
//				action.setProperty(ShapeProperties.AXES_LABELS_DIST);
//			else
//				action.setProperty(ShapeProperties.AXES_LABELS_INCR);
//		}
//
//		@Override
//		public void updateAction() {
//			final Object spinner = interaction.getSpinner();
//
//			if(spinner==instrument.distLabelsX || spinner==instrument.distLabelsY)
//				action.setValue(ShapeFactory.createPoint(Double.valueOf(instrument.distLabelsX.getValue().toString()),
//								Double.valueOf(instrument.distLabelsY.getValue().toString())));
//			else
//				action.setValue(ShapeFactory.createPoint(Double.valueOf(instrument.incrLabelX.getValue().toString()),
//							Double.valueOf(instrument.incrLabelY.getValue().toString())));
//		}
//	}
//
//
//	/** Maps a spinner to an action that modifies the ticks size of the selected shapes. */
//	private static class Spinner2CustomSelectedAxes extends Spinner2CustomAxes<ModifyShapeProperty> {
//		protected Spinner2CustomSelectedAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.hand.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//	/** Maps a spinner to an action that modifies the ticks size of the pencil. */
//	private static class Spinner2CustomPencilAxes extends Spinner2CustomAxes<ModifyPencilParameter> {
//		protected Spinner2CustomPencilAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.pencil.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//
//	/** Maps a combobox to an action that modifies the axe's style. */
//    private abstract static class Combobox2CustomAxes<A extends ShapePropertyAction> extends ListForCustomiser<A, ShapeAxesCustomiser> {
//		protected Combobox2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(ins, clazzAction);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			final Object list = interaction.getList();
//			return instrument.shapeAxes==list || instrument.shapeTicks==list || instrument.showTicks==list || instrument.showLabels==list;
//		}
//
//		@Override
//		public void initAction() {
//			final Object list = interaction.getList();
//
//			if(instrument.shapeAxes==list)
//				action.setProperty(ShapeProperties.AXES_STYLE);
//			else
//				if(instrument.showTicks==list)
//					action.setProperty(ShapeProperties.AXES_TICKS_SHOW);
//				else
//					if(instrument.showLabels==list)
//						action.setProperty(ShapeProperties.AXES_LABELS_SHOW);
//					else
//						action.setProperty(ShapeProperties.AXES_TICKS_STYLE);
//
//			action.setValue(interaction.getList().getSelectedObjects()[0]);
//		}
//	}
//
//
//	/** Maps a combobox to an action that modifies the axe's style of the pencil. */
//	private static class Combobox2CustomPencilAxes extends Combobox2CustomAxes<ModifyPencilParameter> {
//		protected Combobox2CustomPencilAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.pencil.isActivated() && super.isConditionRespected();
//		}
//	}
//
//	/** Maps a combobox to an action that modifies the axe's style of the selection. */
//	private static class Combobox2CustomSelectedAxes extends Combobox2CustomAxes<ModifyShapeProperty> {
//		protected Combobox2CustomSelectedAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.hand.isActivated() && super.isConditionRespected();
//		}
//	}
}
