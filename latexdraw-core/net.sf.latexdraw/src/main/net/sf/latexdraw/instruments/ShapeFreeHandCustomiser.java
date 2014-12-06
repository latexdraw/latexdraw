package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.jfx.ui.JFXWidgetCreator;

/**
 * This instrument modifies free hand properties of shapes or the pencil.<br>
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
 * 2012-04-15<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeFreeHandCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** The type of the freehand. */
	@FXML protected ComboBox<ImageView> freeHandType;

	/** The gap to consider between the points. */
	@FXML protected Spinner<Integer> gapPoints;

	/** Defines if the shape is open. */
	@FXML protected CheckBox open;

	@FXML protected TitledPane mainPane;
	

	/**
	 * Creates the instrument.
	 */
	public ShapeFreeHandCustomiser() {
		super();
	}

	
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		freeHandType.getItems().addAll(createItem(IFreeHandProp.FreeHandType.LINES, "/res/freehand/line.png"),
				createItem(IFreeHandProp.FreeHandType.CURVES, "/res/freehand/curve.png"));
	}
	

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IFreeHandProp.class)) {
			freeHandType.getSelectionModel().select(getItem(freeHandType, shape.getType()).orElseThrow(() -> new IllegalArgumentException()));
			gapPoints.getValueFactory().setValue(shape.getInterval());
			open.setSelected(shape.isOpen());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


	@Override
	protected void initialiseInteractors() {
//		try {
//			addInteractor(new Combobox2CustomPencilFH(this));
//			addInteractor(new Combobox2CustomSelectedFH(this));
//			addInteractor(new Spinner2PencilFreeHand(this));
//			addInteractor(new Spinner2SelectionFreeHand(this));
//			addInteractor(new Checkbox2PencilFreeHand(this));
//			addInteractor(new Checkbox2SelectionFreeHand(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}


//	/** Maps a checkbox to an action. */
//    private abstract static class CheckboxForShapeFreeHandCust<A extends ShapePropertyAction> extends CheckBoxForCustomiser<A, ShapeFreeHandCustomiser> {
//		protected CheckboxForShapeFreeHandCust(final ShapeFreeHandCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(instrument, clazzAction);
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.FREEHAND_OPEN);
//			action.setValue(interaction.getCheckBox().isSelected());
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getCheckBox()==instrument.open;
//		}
//	}
//
//	/** Maps a checkbox to an action that modifies the pencil. */
//	private static class Checkbox2PencilFreeHand extends CheckboxForShapeFreeHandCust<ModifyPencilParameter> {
//		protected Checkbox2PencilFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
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
//	/** This link maps a colour button to the selected shapes. */
//	private static class Checkbox2SelectionFreeHand extends CheckboxForShapeFreeHandCust<ModifyShapeProperty> {
//		protected Checkbox2SelectionFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
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
//	/** Maps a spinner to an action. */
//    private abstract static class SpinnerForShapeFreeHandCust<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeFreeHandCustomiser> {
//		protected SpinnerForShapeFreeHandCust(final ShapeFreeHandCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(instrument, clazzAction);
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.FREEHAND_INTERVAL);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return interaction.getSpinner()==instrument.gapPoints;
//		}
//
//		@Override
//		public void updateAction() {
//			action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
//		}
//	}
//
//
//	/** Maps a spinner to an action that modifies the pencil. */
//	private static class Spinner2PencilFreeHand extends SpinnerForShapeFreeHandCust<ModifyPencilParameter> {
//		protected Spinner2PencilFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyPencilParameter.class);
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
//	/**
//	 * This link maps a colour button to the selected shapes.
//	 */
//	private static class Spinner2SelectionFreeHand extends SpinnerForShapeFreeHandCust<ModifyShapeProperty> {
//		protected Spinner2SelectionFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
//			super(instrument, ModifyShapeProperty.class);
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
//	/** Maps a combobox to an action that modifies freehand's parameters. */
//    private abstract static class Combobox2CustomFH<A extends ShapePropertyAction> extends ListForCustomiser<A, ShapeFreeHandCustomiser> {
//		protected Combobox2CustomFH(final ShapeFreeHandCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(ins, clazzAction);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.freeHandType==interaction.getList();
//		}
//
//		@Override
//		public void initAction() {
//			action.setProperty(ShapeProperties.FREEHAND_STYLE);
//			if(getLabelText().equals(IFreeHandProp.FreeHandType.CURVES.toString()))
//				 action.setValue(IFreeHandProp.FreeHandType.CURVES);
//			else action.setValue(IFreeHandProp.FreeHandType.LINES);
//		}
//	}
//
//
//	/** Maps a combobox to an action that modifies freehand's parameters of the pencil. */
//	private static class Combobox2CustomPencilFH extends Combobox2CustomFH<ModifyPencilParameter> {
//		protected Combobox2CustomPencilFH(final ShapeFreeHandCustomiser ins) throws InstantiationException, IllegalAccessException {
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
//	/** Maps a combobox to an action that modifies the freehand's parameters of the selection. */
//	private static class Combobox2CustomSelectedFH extends Combobox2CustomFH<ModifyShapeProperty> {
//		protected Combobox2CustomSelectedFH(final ShapeFreeHandCustomiser ins) throws InstantiationException, IllegalAccessException {
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
