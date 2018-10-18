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
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
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

	protected final <T> ModifyShapeProperty<T> mapModShProp(final T o, final ShapeProperties<T> p) {
		return new ModifyShapeProperty<>(p, canvas.getDrawing().getSelection().duplicateDeep(false), o);
	}

	protected final <T> ModifyPencilParameter<T> firstPropPen(final T o, final ShapeProperties<T> p) {
		return new ModifyPencilParameter<>(p, pencil, o);
	}

	@SuppressWarnings("unchecked")
	protected <T> void addComboPropBinding(final ComboBox<T> combo, final ShapeProperties<T> prop) {
		comboboxBinder(i -> mapModShProp((T) i.getWidget().getSelectionModel().getSelectedItem(), prop)).on(combo).when(handActiv).bind();
		comboboxBinder(i -> firstPropPen((T) i.getWidget().getSelectionModel().getSelectedItem(), prop)).on(combo).when(pencilActiv).bind();
	}

	protected void addSpinnerAnglePropBinding(final Spinner<Double> spinner, final ShapeProperties<Double> prop) {
		spinnerBinder(i -> mapModShProp(null, prop)).on(spinner).
			then((i, c) -> c.setValue(Math.toRadians(((Number) i.getWidget().getValue()).doubleValue()))).
			when(handActiv).bind();

		spinnerBinder(i -> firstPropPen(null, prop)).on(spinner).
			then((i, c) -> c.setValue(Math.toRadians(((Number) i.getWidget().getValue()).doubleValue()))).
			when(pencilActiv).bind();
	}

	@SuppressWarnings("unchecked")
	protected <T extends Number> void addSpinnerPropBinding(final Spinner<T> spinner, final ShapeProperties<T> prop) {
		spinnerBinder(i -> mapModShProp(null, prop)).on(spinner).
			then((i, c) -> c.setValue((T) i.getWidget().getValue())).
			when(handActiv).bind();

		spinnerBinder(i -> firstPropPen(null, prop)).on(spinner).
			then((i, c) -> c.setValue((T) i.getWidget().getValue())).
			when(pencilActiv).bind();
	}

	protected void addColorPropBinding(final ColorPicker picker, final ShapeProperties<Color> prop) {
		colorPickerBinder(i -> mapModShProp(ShapeFactory.INST.createColorFX(i.getWidget().getValue()), prop)).on(picker).when(handActiv).bind();

		colorPickerBinder(i -> firstPropPen(ShapeFactory.INST.createColorFX(i.getWidget().getValue()), prop)).on(picker).when(pencilActiv).bind();
	}

	protected void addCheckboxPropBinding(final CheckBox cb, final ShapeProperties<Boolean> prop) {
		checkboxBinder(i -> mapModShProp(i.getWidget().isSelected(), prop)).on(cb).when(handActiv).bind();
		checkboxBinder(i -> firstPropPen(i.getWidget().isSelected(), prop)).on(cb).when(pencilActiv).bind();
	}

	protected void addTogglePropBinding(final ToggleButton button, final ShapeProperties<Boolean> prop, final boolean invert) {
		toggleButtonBinder(i -> mapModShProp(i.getWidget().isSelected() ^ invert, prop)).on(button).when(handActiv).bind();
		toggleButtonBinder(i -> firstPropPen(i.getWidget().isSelected() ^ invert, prop)).on(button).when(pencilActiv).bind();
	}

	protected <T> void addTogglePropBinding(final ToggleButton button, final ShapeProperties<T> prop, final T value) {
		toggleButtonBinder(i -> mapModShProp(value, prop)).on(button).when(handActiv).bind();
		toggleButtonBinder(i -> firstPropPen(value, prop)).on(button).when(pencilActiv).bind();
	}

	protected void addSpinnerXYPropBinding(final Spinner<Double> spinnerX, final Spinner<Double> spinnerY, final ShapeProperties<IPoint> property) {
		spinnerBinder(i -> new ModifyShapeProperty<>(property, canvas.getDrawing().getSelection().duplicateDeep(false), null)).
			on(spinnerX, spinnerY).then(c -> c.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue()))).when(handActiv).bind();

		spinnerBinder(i -> new ModifyPencilParameter<>(property, pencil, null)).on(spinnerX, spinnerY).
			then(c -> c.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue()))).
			when(pencilActiv).bind();
	}
}
