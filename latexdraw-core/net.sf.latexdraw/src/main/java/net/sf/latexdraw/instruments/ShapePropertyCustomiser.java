/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.util.function.BooleanSupplier;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.commands.ModifyPencilParameter;
import net.sf.latexdraw.commands.shape.ModifyShapeProperty;
import net.sf.latexdraw.commands.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.malai.command.Command;
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
	protected final BooleanSupplier handActiv = () -> hand.isActivated();
	protected final BooleanSupplier pencilActiv = () -> pencil.isActivated();


	/**
	 * Creates the instrument.
	 */
	public ShapePropertyCustomiser() {
		super();
	}

	@Override
	public void onCmdDone(final Command cmd) {
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
		}else {
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

	protected final ModifyShapeProperty mapModShProp(final Object o, final ShapeProperties p) {
		return new ModifyShapeProperty(p, canvas.getDrawing().getSelection().duplicateDeep(false), o);
	}

	protected final ModifyPencilParameter firstPropPen(final Object o, final ShapeProperties p) {
		return new ModifyPencilParameter(p, pencil, o);
	}

	protected void addComboPropBinding(final ComboBox<?> combo, final ShapeProperties prop) {
		comboboxBinder(ModifyShapeProperty.class).on(combo).map(i -> mapModShProp(i.getWidget().getSelectionModel().getSelectedItem(), prop)).when(handActiv).bind();
		comboboxBinder(ModifyPencilParameter.class).on(combo).map(i -> firstPropPen(i.getWidget().getSelectionModel().getSelectedItem(), prop)).when(pencilActiv).bind();
	}

	protected void addSpinnerPropBinding(final Spinner<?> spinner, final ShapeProperties prop, final boolean angle) {
		spinnerBinder(ModifyShapeProperty.class).on(spinner).map(i -> mapModShProp(null, prop)).
			then((i, c) -> c.setValue(angle ? Math.toRadians(((Number) i.getWidget().getValue()).doubleValue()) : i.getWidget().getValue())).
			when(handActiv).bind();

		spinnerBinder(ModifyPencilParameter.class).on(spinner).map(i -> firstPropPen(null, prop)).
			then((i, c) -> c.setValue(angle ? Math.toRadians(((Number) i.getWidget().getValue()).doubleValue()) : i.getWidget().getValue())).
			when(pencilActiv).bind();
	}

	protected void addColorPropBinding(final ColorPicker picker, final ShapeProperties prop) {
		colorPickerBinder(ModifyShapeProperty.class).on(picker).map(i -> mapModShProp(ShapeFactory.INST.createColorFX(i.getWidget().getValue()), prop)).
			when(handActiv).bind();

		colorPickerBinder(ModifyPencilParameter.class).on(picker).map(i -> firstPropPen(ShapeFactory.INST.createColorFX(i.getWidget().getValue()), prop)).
			when(pencilActiv).bind();
	}

	protected void addCheckboxPropBinding(final CheckBox cb, final ShapeProperties prop) {
		checkboxBinder(ModifyShapeProperty.class).on(cb).map(i -> mapModShProp(i.getWidget().isSelected(), prop)).when(handActiv).bind();
		checkboxBinder(ModifyPencilParameter.class).on(cb).map(i -> firstPropPen(i.getWidget().isSelected(), prop)).when(pencilActiv).bind();
	}

	protected void addTogglePropBinding(final ToggleButton button, final ShapeProperties prop, final boolean invert) {
		toggleButtonBinder(ModifyShapeProperty.class).on(button).map(i -> mapModShProp(i.getWidget().isSelected() ^ invert, prop)).when(handActiv).bind();
		toggleButtonBinder(ModifyPencilParameter.class).on(button).map(i -> firstPropPen(i.getWidget().isSelected() ^ invert, prop)).when(pencilActiv).bind();
	}

	protected void addTogglePropBinding(final ToggleButton button, final ShapeProperties prop, final Object value) {
		toggleButtonBinder(ModifyShapeProperty.class).on(button).map(i -> mapModShProp(value, prop)).when(handActiv).bind();
		toggleButtonBinder(ModifyPencilParameter.class).on(button).map(i -> firstPropPen(value, prop)).when(pencilActiv).bind();
	}

	protected void addSpinnerXYPropBinding(final Spinner<Double> spinnerX, final Spinner<Double> spinnerY, final ShapeProperties property) {
		spinnerBinder(ModifyShapeProperty.class).on(spinnerX, spinnerY).
			map(i -> new ModifyShapeProperty(property, canvas.getDrawing().getSelection().duplicateDeep(false), null)).
			then(c -> c.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue()))).when(handActiv).bind();

		spinnerBinder(ModifyPencilParameter.class).on(spinnerX, spinnerY).
			map(i -> new ModifyPencilParameter(property, pencil, null)).
			then(c -> c.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue()))).
			when(pencilActiv).bind();
	}
}
