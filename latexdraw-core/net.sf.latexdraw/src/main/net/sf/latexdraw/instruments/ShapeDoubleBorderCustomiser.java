package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.javafx.instrument.library.CheckboxInteractor;
import org.malai.javafx.instrument.library.ColorPickerInteractor;
import org.malai.javafx.instrument.library.SpinnerInteractor;

/**
 * This instrument modifies double border properties of shapes or the pencil.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
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
		try {
			addInteractor(new CheckBox2PencilDoubleBorder(this));
			addInteractor(new CheckBox2SelectionDoubleBorder(this));
			addInteractor(new ColourButton2PencilDoubleBorder(this));
			addInteractor(new ColourButton2SelectionDoubleBorder(this));
			addInteractor(new Spinner2PencilDoubleBorder(this));
			addInteractor(new Spinner2SelectionDoubleBorder(this));
		}catch(InstantiationException | IllegalAccessException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	private static class CheckBox2PencilDoubleBorder extends CheckboxInteractor<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
		CheckBox2PencilDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, ins.dbleBoundCB);
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.DBLE_BORDERS);
			action.setPencil(instrument.pencil);
			action.setValue(interaction.getWidget().isSelected());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	private static class CheckBox2SelectionDoubleBorder extends CheckboxInteractor<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
		CheckBox2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, ins.dbleBoundCB);
		}

		@Override
		public void initAction() {
			action.setValue(interaction.getWidget().isSelected());
			action.setGroup(instrument.pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
			action.setProperty(ShapeProperties.DBLE_BORDERS);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	private static class Spinner2PencilDoubleBorder extends SpinnerInteractor<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
		Spinner2PencilDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, ins.dbleSepField);
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
			action.setPencil(instrument.pencil);
			action.setValue(getInteraction().getWidget().getValue());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	private static class Spinner2SelectionDoubleBorder extends SpinnerInteractor<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
		Spinner2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, ins.dbleSepField);
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
			action.setGroup(instrument.pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
			action.setValue(getInteraction().getWidget().getValue());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	private static class ColourButton2PencilDoubleBorder extends ColorPickerInteractor<ModifyPencilParameter, ShapeDoubleBorderCustomiser> {
		ColourButton2PencilDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, ins.dbleBoundColB);
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
			action.setPencil(instrument.pencil);
			action.setValue(ShapeFactory.createColorFX(interaction.getWidget().getValue()));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	class ColourButton2SelectionDoubleBorder extends ColorPickerInteractor<ModifyShapeProperty, ShapeDoubleBorderCustomiser> {
		ColourButton2SelectionDoubleBorder(final ShapeDoubleBorderCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, ins.dbleBoundColB);
		}

		@Override
		public void initAction() {
			action.setValue(ShapeFactory.createColorFX(interaction.getWidget().getValue()));
			action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
			action.setGroup(instrument.pencil.canvas.getDrawing().getSelection().duplicateDeep(false));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

}
