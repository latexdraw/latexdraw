package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

/**
 * This instrument modifies double border properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 11/02/2010<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeDoubleBorderCustomiser extends ShapePropertyCustomiser {
	/** Sets if the shape has double borders or not. */
	@FXML protected CheckBox dbleBoundCB;

	/** Allows to change the colour of the space between the double boundaries. */
	@FXML protected ColorPicker dbleBoundColB;

	/** This field modifies the double separation of the double line. */
	@FXML protected Spinner<Double> dbleSepField;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeDoubleBorderCustomiser() {
		super();
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isDbleBorderable()) {
			setActivated(true);
			final boolean dble = shape.hasDbleBord();

			dbleBoundCB.setSelected(dble);
			dbleBoundColB.setDisable(!dble);
			dbleSepField.setDisable(!dble);

			if(dble) {
				dbleBoundColB.setValue(shape.getDbleBordCol().toJFX());
				dbleSepField.getValueFactory().setValue(shape.getDbleBordSep());
			}
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
		// addInteractor(new CheckBox2PencilDoubleBorder(this));
		// addInteractor(new CheckBox2SelectionDoubleBorder(this));
		// addInteractor(new ColourButton2PencilDoubleBorder(this));
		// addInteractor(new ColourButton2SelectionDoubleBorder(this));
		// addInteractor(new Spinner2PencilDoubleBorder(this));
		// addInteractor(new Spinner2SelectionDoubleBorder(this));
		// }catch(InstantiationException | IllegalAccessException e){
		// BadaboomCollector.INSTANCE.add(e);
		// }
	}
}

//
// /**
// * This link uses a checkbox to modify the pencil.
// */
// class CheckBox2PencilDoubleBorder extends
// CheckBoxForCustomiser<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
// /**
// * Creates the link.
// * @param instrument The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected CheckBox2PencilDoubleBorder(final ShapeDoubleBorderCustomiser
// instrument) throws InstantiationException, IllegalAccessException {
// super(instrument, ModifyPencilParameter.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setProperty(ShapeProperties.DBLE_BORDERS);
// action.setPencil(instrument.pencil);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getCheckBox()==instrument.dbleBoundCB &&
// instrument.pencil.isActivated();
// }
// }
//
//
//
// /**
// * This link uses a checkbox to modify shapes.
// */
// class CheckBox2SelectionDoubleBorder extends
// CheckBoxForCustomiser<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
// /**
// * Creates the link.
// * @param instrument The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected CheckBox2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser
// instrument) throws InstantiationException, IllegalAccessException {
// super(instrument, ModifyShapeProperty.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
// action.setProperty(ShapeProperties.DBLE_BORDERS);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getCheckBox()==instrument.dbleBoundCB &&
// instrument.hand.isActivated();
// }
// }
//
//
// /**
// * This link maps a colour button to the pencil.
// */
// class ColourButton2PencilDoubleBorder extends
// ColourButtonForCustomiser<ModifyPencilParameter, ShapeDoubleBorderCustomiser>
// {
// /**
// * Creates the link.
// * @param instrument The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected ColourButton2PencilDoubleBorder(final ShapeDoubleBorderCustomiser
// instrument) throws InstantiationException, IllegalAccessException {
// super(instrument, ModifyPencilParameter.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
// action.setPencil(instrument.pencil);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getButton()==instrument.dbleBoundColB &&
// instrument.pencil.isActivated();
// }
// }
//
//
//
// /**
// * This link maps a colour button to the selected shapes.
// */
// class ColourButton2SelectionDoubleBorder extends
// ColourButtonForCustomiser<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
// /**
// * Creates the link.
// * @param instrument The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected ColourButton2SelectionDoubleBorder(final
// ShapeDoubleBorderCustomiser instrument) throws InstantiationException,
// IllegalAccessException {
// super(instrument, ModifyShapeProperty.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
// action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getButton()==instrument.dbleBoundColB &&
// instrument.hand.isActivated();
// }
// }
//
//
// /**
// * This link maps a spinner to a ModifyPencil action.
// */
// class Spinner2SelectionDoubleBorder extends
// SpinnerForCustomiser<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected Spinner2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser
// ins) throws InstantiationException, IllegalAccessException {
// super(ins, ModifyShapeProperty.class);
// }
//
// @Override
// public void initAction() {
// action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
// action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getSpinner()==instrument.dbleSepField &&
// instrument.hand.isActivated();
// }
// }
//
//
// /**
// * This link maps a spinner to a ModifyPencil action.
// */
// class Spinner2PencilDoubleBorder extends
// SpinnerForCustomiser<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
// /**
// * Creates the link.
// * @param ins The instrument that contains the link.
// * @throws InstantiationException If an error of instantiation (interaction,
// action) occurs.
// * @throws IllegalAccessException If no free-parameter constructor are
// provided.
// */
// protected Spinner2PencilDoubleBorder(final ShapeDoubleBorderCustomiser ins)
// throws InstantiationException, IllegalAccessException {
// super(ins, ModifyPencilParameter.class);
// }
//
//
// @Override
// public void initAction() {
// action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
// action.setPencil(instrument.pencil);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getSpinner()==instrument.dbleSepField &&
// instrument.pencil.isActivated();
// }
// }
