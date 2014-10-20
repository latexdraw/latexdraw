package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument modifies shadow properties of shapes or the pencil.<br>
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
 * 11/07/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeShadowCustomiser extends JfxInstrument { // extends ShapePropertyCustomiser {
	/** Sets if the a shape has a shadow or not. */
	@FXML protected CheckBox shadowCB;

	/** Sets the colour of the shadow of a figure. */
	@FXML protected ColorPicker shadowColB;

	/** Changes the size of the shadow. */
	@FXML protected TextField shadowSizeField;

	/** Changes the angle of the shadow. */
	@FXML protected TextField shadowAngleField;
	
	@FXML protected TitledPane mainPane;


	/**
	 * Creates the instrument.
	 */
	public ShapeShadowCustomiser() {
		super();
	}


//	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


//	@Override
	protected void update(final IGroup shape) {
		if(shape!=null && shape.isShadowable()) {
			final boolean hasShadow = shape.hasShadow();

			shadowCB.setSelected(hasShadow);
			shadowColB.setDisable(!hasShadow);
			shadowAngleField.setDisable(!hasShadow);
			shadowSizeField.setDisable(!hasShadow);

			if(hasShadow) {
				shadowColB.setValue(shape.getShadowCol().toJFX());
//				shadowAngleField.setValueSafely(Math.toDegrees(shape.getShadowAngle()));
//				shadowSizeField.setValueSafely(shape.getShadowSize());
			}
		}
		else setActivated(false);
	}

	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new CheckBox2PencilShadow(this));
//			addInteractor(new CheckBox2SelectionShadow(this));
//			addInteractor(new Spinner2SelectionShadow(this));
//			addInteractor(new Spinner2PencilShadow(this));
//			addInteractor(new ColourButton2SelectionShadow(this));
//			addInteractor(new ColourButton2PencilShadow(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}
}


///**
// * This link uses a checkbox to modify the pencil.
// */
//class CheckBox2PencilShadow extends CheckBoxForCustomiser<ModifyPencilParameter, ShapeShadowCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	CheckBox2PencilShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyPencilParameter.class);
//	}
//
//	@Override
//	public void initAction() {
//		super.initAction();
//		action.setProperty(ShapeProperties.SHADOW);
//		action.setPencil(instrument.pencil);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getCheckBox()==instrument.shadowCB && instrument.pencil.isActivated();
//	}
//}
//
//
///**
// * This link uses a checkbox to modify shapes.
// */
//class CheckBox2SelectionShadow extends CheckBoxForCustomiser<ModifyShapeProperty, ShapeShadowCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	CheckBox2SelectionShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public void initAction() {
//		super.initAction();
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		action.setProperty(ShapeProperties.SHADOW);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getCheckBox()==instrument.shadowCB && instrument.hand.isActivated();
//	}
//}
//
//
///**
// * This link maps a spinner to a ModifyPencil action.
// */
//class Spinner2SelectionShadow extends SpinnerForCustomiser<ModifyShapeProperty, ShapeShadowCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param ins The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	Spinner2SelectionShadow(final ShapeShadowCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public void initAction() {
//		final JSpinner spinner = interaction.getSpinner();
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//
//		if(spinner==instrument.shadowSizeField)
//			action.setProperty(ShapeProperties.SHADOW_SIZE);
//		else
//			action.setProperty(ShapeProperties.SHADOW_ANGLE);
//	}
//
//	@Override
//	public void updateAction() {
//		if(interaction.getSpinner()==instrument.shadowAngleField)
//			action.setValue(Math.toRadians(Double.valueOf(interaction.getSpinner().getValue().toString())));
//		else
//			super.updateAction();
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final JSpinner spinner = getInteraction().getSpinner();
//		return (spinner==instrument.shadowAngleField || spinner==instrument.shadowSizeField) && instrument.hand.isActivated();
//	}
//}
//
//
///**
// * This link maps a spinner to a ModifyPencil action.
// */
//class Spinner2PencilShadow extends SpinnerForCustomiser<ModifyPencilParameter, ShapeShadowCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param ins The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	Spinner2PencilShadow(final ShapeShadowCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, ModifyPencilParameter.class);
//	}
//
//
//	@Override
//	public void initAction() {
//		final JSpinner spinner = getInteraction().getSpinner();
//		action.setPencil(instrument.pencil);
//
//		if(spinner==instrument.shadowAngleField)
//			action.setProperty(ShapeProperties.SHADOW_ANGLE);
//		else
//			action.setProperty(ShapeProperties.SHADOW_SIZE);
//	}
//
//	@Override
//	public void updateAction() {
//		if(interaction.getSpinner()==instrument.shadowAngleField)
//			action.setValue(Math.toRadians(Double.valueOf(interaction.getSpinner().getValue().toString())));
//		else
//			super.updateAction();
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final JSpinner spinner = interaction.getSpinner();
//		return (spinner==instrument.shadowSizeField || spinner==instrument.shadowAngleField) && instrument.pencil.isActivated();
//	}
//}
//
//
///**
// * This link maps a colour button to the pencil.
// */
//class ColourButton2PencilShadow extends ColourButtonForCustomiser<ModifyPencilParameter, ShapeShadowCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	ColourButton2PencilShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyPencilParameter.class);
//	}
//
//	@Override
//	public void initAction() {
//		super.initAction();
//		action.setProperty(ShapeProperties.COLOUR_SHADOW);
//		action.setPencil(instrument.pencil);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getButton()==instrument.shadowColB && instrument.pencil.isActivated();
//	}
//}
//
//
///**
// * This link maps a colour button to the selected shapes.
// */
//class ColourButton2SelectionShadow extends ColourButtonForCustomiser<ModifyShapeProperty, ShapeShadowCustomiser> {
//	/**
//	 * Creates the link.
//	 * @param instrument The instrument that contains the link.
//	 * @throws InstantiationException If an error of instantiation (interaction, action) occurs.
//	 * @throws IllegalAccessException If no free-parameter constructor are provided.
//	 */
//	ColourButton2SelectionShadow(final ShapeShadowCustomiser instrument) throws InstantiationException, IllegalAccessException {
//		super(instrument, ModifyShapeProperty.class);
//	}
//
//	@Override
//	public void initAction() {
//		super.initAction();
//		action.setProperty(ShapeProperties.COLOUR_SHADOW);
//		action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getButton()==instrument.shadowColB && instrument.hand.isActivated();
//	}
//}
