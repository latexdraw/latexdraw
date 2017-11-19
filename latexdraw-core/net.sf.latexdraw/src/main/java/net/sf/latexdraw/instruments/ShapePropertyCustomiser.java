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

import javax.inject.Inject;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.action.Action;
import org.malai.javafx.binding.CheckboxBinding;
import org.malai.javafx.binding.ColorPickerBinding;
import org.malai.javafx.binding.ComboBoxBinding;
import org.malai.javafx.binding.SpinnerBinding;
import org.malai.javafx.binding.ToggleButtonBinding;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.undo.Undoable;

/**
 * This abstract instrument defines the base definition of instruments that customise shape properties.
 * @author Arnaud BLOUIN
 */
public abstract class ShapePropertyCustomiser extends JfxInstrument {
	/** The Hand instrument. */
	@Inject protected Hand hand;

	/** The Pencil instrument. */
	@Inject protected Pencil pencil;

	@Inject protected Canvas canvas;

	@Inject protected IDrawing drawing;


	/**
	 * Creates the instrument.
	 */
	public ShapePropertyCustomiser() {
		super();
	}

	@Override
	public void onActionDone(final Action action) {
		update();
	}

	@Override
	public void onUndoableUndo(final Undoable undoable) {
		update();
	}

	@Override
	public void onUndoableRedo(final Undoable undoable) {
		update();
	}

	/**
	 * Updates the instrument and its widgets
	 * @since 3.0
	 */
	public void update() {
		if(pencil.isActivated()) {
			update(ShapeFactory.INST.createGroup(pencil.createShapeInstance()));
		}
		else {
			update(drawing.getSelection());
		}
	}

	/**
	 * Updates the widgets using the given shape.
	 * @param shape The shape used to update the widgets. If null, nothing is performed.
	 * @since 3.0
	 */
	protected abstract void update(final IGroup shape);

	/**
	 * Sets the widgets of the instrument visible or not.
	 * @param visible True: they are visible.
	 * @since 3.0
	 */
	protected abstract void setWidgetsVisible(final boolean visible);

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);
		setWidgetsVisible(act);
	}

	static class List4Pencil extends ComboBoxBinding<ModifyPencilParameter, ShapePropertyCustomiser> {
		ShapeProperties prop;

		List4Pencil(final ShapePropertyCustomiser ins, final ComboBox<?> combo, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, combo);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setPencil(instrument.pencil);
			action.setProperty(prop);
			action.setValue(interaction.getWidget().getSelectionModel().getSelectedItem());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	static class List4Selection extends ComboBoxBinding<ModifyShapeProperty, ShapePropertyCustomiser> {
		ShapeProperties prop;

		List4Selection(final ShapePropertyCustomiser ins, final ComboBox<?> combo, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, combo);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setGroup(instrument.canvas.getDrawing().getSelection().duplicateDeep(false));
			action.setProperty(prop);
			action.setValue(interaction.getWidget().getSelectionModel().getSelectedItem());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	static class Spinner4Pencil extends SpinnerBinding<ModifyPencilParameter, ShapePropertyCustomiser> {
		ShapeProperties prop;
		boolean angle;

		Spinner4Pencil(final ShapePropertyCustomiser ins, final Spinner<?> widget, ShapeProperties property, boolean isAngle) throws InstantiationException, IllegalAccessException {
			super(ins, true, ModifyPencilParameter.class, widget);
			prop = property;
			angle = isAngle;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setPencil(instrument.pencil);
			updateAction();
		}

		@Override
		public void updateAction() {
			if(angle) {
				action.setValue(Math.toRadians(((Number) interaction.getWidget().getValue()).doubleValue()));
			}else {
				action.setValue(interaction.getWidget().getValue());
			}
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	static class Spinner4Selection extends SpinnerBinding<ModifyShapeProperty, ShapePropertyCustomiser> {
		ShapeProperties prop;
		boolean angle;

		Spinner4Selection(final ShapePropertyCustomiser ins, final Spinner<?> widget, ShapeProperties property, boolean isAngle) throws InstantiationException, IllegalAccessException {
			super(ins, true, ModifyShapeProperty.class, widget);
			prop = property;
			angle = isAngle;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setGroup(instrument.canvas.getDrawing().getSelection().duplicateDeep(false));
			updateAction();
		}

		@Override
		public void updateAction() {
			if(angle) {
				action.setValue(Math.toRadians(((Number) interaction.getWidget().getValue()).doubleValue()));
			}
			else {
				action.setValue(interaction.getWidget().getValue());
			}
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	static class ColourPicker4Pencil extends ColorPickerBinding<ModifyPencilParameter, ShapePropertyCustomiser> {
		ShapeProperties prop;

		ColourPicker4Pencil(final ShapePropertyCustomiser ins, ColorPicker picker, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, picker);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setValue(ShapeFactory.INST.createColorFX(interaction.getWidget().getValue()));
			action.setPencil(instrument.pencil);
			action.setProperty(prop);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	static class ColourPicker4Selection extends ColorPickerBinding<ModifyShapeProperty, ShapePropertyCustomiser> {
		ShapeProperties prop;

		ColourPicker4Selection(final ShapePropertyCustomiser ins, ColorPicker picker, ShapeProperties property) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, picker);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setGroup(instrument.canvas.getDrawing().getSelection().duplicateDeep(false));
			action.setValue(ShapeFactory.INST.createColorFX(interaction.getWidget().getValue()));
			action.setProperty(prop);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	static class Checkbox4Pencil extends CheckboxBinding<ModifyPencilParameter, ShapePropertyCustomiser> {
		ShapeProperties prop;

		Checkbox4Pencil(final ShapePropertyCustomiser ins, final CheckBox widget, final ShapeProperties property)
			throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, widget);
			prop = property;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setPencil(instrument.pencil);
			action.setValue(interaction.getWidget().isSelected());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	static class Checkbox4Selection extends CheckboxBinding<ModifyShapeProperty, ShapePropertyCustomiser> {
		ShapeProperties prop;

		Checkbox4Selection(final ShapePropertyCustomiser ins, final CheckBox widget, final ShapeProperties property)
			throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, widget);
			prop = property;
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setGroup(instrument.canvas.getDrawing().getSelection().duplicateDeep(false));
			action.setValue(interaction.getWidget().isSelected());
		}
	}

	static class ToggleButton4Pencil extends ToggleButtonBinding<ModifyPencilParameter, ShapePropertyCustomiser> {
		ShapeProperties prop;
		boolean invert;

		ToggleButton4Pencil(final ShapePropertyCustomiser ins, final ToggleButton widget, final ShapeProperties property, final boolean inverted)
			throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, widget);
			prop = property;
			invert = inverted;
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setPencil(instrument.pencil);
			action.setValue(interaction.getWidget().isSelected() ^ invert);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	static class ToggleButton4Selection extends ToggleButtonBinding<ModifyShapeProperty, ShapePropertyCustomiser> {
		ShapeProperties prop;
		boolean invert;

		ToggleButton4Selection(final ShapePropertyCustomiser ins, final ToggleButton widget, final ShapeProperties property, final boolean inverted)
			throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, widget);
			prop = property;
			invert = inverted;
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}

		@Override
		public void initAction() {
			action.setProperty(prop);
			action.setGroup(instrument.canvas.getDrawing().getSelection().duplicateDeep(false));
			action.setValue(interaction.getWidget().isSelected() ^ invert);
		}
	}
}
