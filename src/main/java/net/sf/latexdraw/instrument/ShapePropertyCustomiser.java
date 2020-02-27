/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.undo.UndoCollector;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.BooleanSupplier;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.command.ModifyEditingParameter;
import net.sf.latexdraw.command.shape.ModifyShapeProperty;
import net.sf.latexdraw.command.shape.ShapeProperties;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * This abstract instrument defines the base definition of instruments that customise shape properties.
 * @author Arnaud BLOUIN
 */
public abstract class ShapePropertyCustomiser extends JfxInstrument implements Initializable {
	protected final @NotNull Hand hand;
	protected final @NotNull Pencil pencil;
	protected final @NotNull Canvas canvas;
	protected final @NotNull Drawing drawing;
	protected final @NotNull EditingService editing;
	protected final @NotNull BooleanSupplier handActiv;
	protected final @NotNull BooleanSupplier pencilActiv;

	@Inject
	public ShapePropertyCustomiser(final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super();
		this.hand = Objects.requireNonNull(hand);
		this.pencil = Objects.requireNonNull(pencil);
		this.canvas = Objects.requireNonNull(canvas);
		this.drawing = Objects.requireNonNull(drawing);
		this.editing = Objects.requireNonNull(editing);
		handActiv = () -> hand.isActivated();
		pencilActiv = () -> pencil.isActivated();
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resourceBundle) {
		addDisposable(UndoCollector.getInstance().redos().subscribe(undoable -> update()));
		addDisposable(UndoCollector.getInstance().undos().subscribe(undoable -> update()));
	}

	/**
	 * Updates the instrument and its widgets
	 */
	public void update() {
		if(pencil.isActivated()) {
			update(ShapeFactory.INST.createGroup(editing.createShapeInstance()));
		}else {
			update(drawing.getSelection());
		}
	}

	/**
	 * Updates the widgets using the given shape.
	 * @param shape The shape used to update the widgets. If null, nothing is performed.
	 */
	protected abstract void update(final Group shape);

	/**
	 * Sets the widgets of the instrument visible or not.
	 * @param visible True: they are visible.
	 */
	protected abstract void setWidgetsVisible(final boolean visible);

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);
		setWidgetsVisible(act);
	}

	protected final <T> ModifyShapeProperty<T> createModShProp(final T o, final @NotNull ShapeProperties<T> p) {
		return new ModifyShapeProperty<>(p, canvas.getDrawing().getSelection().duplicateDeep(false), o);
	}

	protected final <T> ModifyEditingParameter<T> firstPropPen(final T o, final @NotNull ShapeProperties<T> p) {
		return new ModifyEditingParameter<>(p, editing, o);
	}

	@SuppressWarnings("unchecked")
	protected <T> void addComboPropBinding(final @NotNull ComboBox<T> combo, final @NotNull ShapeProperties<T> prop) {
		final var spinnerBaseBinder = comboboxBinder()
			.on(combo)
			.end(() -> update());

		spinnerBaseBinder
			.toProduce(i -> createModShProp((T) i.getWidget().getSelectionModel().getSelectedItem(), prop))
			.when(handActiv)
			.bind();

		spinnerBaseBinder
			.toProduce(i -> firstPropPen((T) i.getWidget().getSelectionModel().getSelectedItem(), prop))
			.when(pencilActiv)
			.bind();
	}

	protected void addSpinnerAnglePropBinding(final @NotNull Spinner<Double> spinner, final @NotNull ShapeProperties<Double> prop) {
		final var spinnerBaseBinder = spinnerBinder()
			.on(spinner)
			.end(() -> update());

		spinnerBaseBinder
			.toProduce(() -> createModShProp(null, prop))
			.then((i, c) -> c.setValue(Math.toRadians((Double) i.getWidget().getValue())))
			.when(handActiv)
			.bind();

		spinnerBaseBinder
			.toProduce(() -> firstPropPen(null, prop))
			.then((i, c) -> c.setValue(Math.toRadians((Double) i.getWidget().getValue())))
			.when(pencilActiv)
			.bind();
	}

	@SuppressWarnings("unchecked")
	protected <T extends Number> void addSpinnerPropBinding(final @NotNull Spinner<T> spinner, final @NotNull ShapeProperties<T> prop) {
		final var spinnerBaseBinder = spinnerBinder()
			.on(spinner)
			.end(() -> update());

		spinnerBaseBinder
			.toProduce(() -> createModShProp(null, prop))
			.then((i, c) -> c.setValue((T) i.getWidget().getValue()))
			.when(handActiv)
			.bind();

		spinnerBaseBinder
			.toProduce(() -> firstPropPen(null, prop))
			.then((i, c) -> c.setValue((T) i.getWidget().getValue()))
			.when(pencilActiv)
			.bind();
	}

	protected void addColorPropBinding(final @NotNull ColorPicker picker, final @NotNull ShapeProperties<Color> prop) {
		final var basePickerBinder = colorPickerBinder()
			.on(picker)
			.end(() -> update());

		basePickerBinder
			.toProduce(i -> createModShProp(ShapeFactory.INST.createColorFX(i.getWidget().getValue()), prop))
			.when(handActiv)
			.bind();

		basePickerBinder
			.toProduce(i -> firstPropPen(ShapeFactory.INST.createColorFX(i.getWidget().getValue()), prop))
			.when(pencilActiv)
			.bind();
	}

	protected void addCheckboxPropBinding(final @NotNull CheckBox cb, final @NotNull ShapeProperties<Boolean> prop) {
		final var baseBoxBinder = checkboxBinder()
			.on(cb)
			.end(() -> update());

		baseBoxBinder
			.toProduce(i -> createModShProp(i.getWidget().isSelected(), prop))
			.when(handActiv)
			.bind();

		baseBoxBinder
			.toProduce(i -> firstPropPen(i.getWidget().isSelected(), prop))
			.when(pencilActiv)
			.bind();
	}

	protected void addTogglePropBinding(final @NotNull ToggleButton button, final @NotNull ShapeProperties<Boolean> prop, final boolean invert) {
		final var baseButtonBinder = toggleButtonBinder()
			.on(button)
			.end(() -> update());

		baseButtonBinder
			.toProduce(i -> createModShProp(i.getWidget().isSelected() ^ invert, prop))
			.when(handActiv)
			.bind();

		baseButtonBinder
			.toProduce(i -> firstPropPen(i.getWidget().isSelected() ^ invert, prop))
			.when(pencilActiv)
			.bind();
	}

	protected <T> void addTogglePropBinding(final @NotNull ToggleButton button, final @NotNull ShapeProperties<T> prop, final T value) {
		final var baseButtonBinder = toggleButtonBinder()
			.on(button)
			.end(() -> update());

		baseButtonBinder
			.toProduce(() -> createModShProp(value, prop))
			.when(handActiv)
			.bind();

		baseButtonBinder
			.toProduce(() -> firstPropPen(value, prop))
			.when(pencilActiv)
			.bind();
	}

	protected void addSpinnerXYPropBinding(final @NotNull Spinner<Double> spinnerX, final @NotNull Spinner<Double> spinnerY,
			final @NotNull ShapeProperties<Point> property) {
		final var baseBinder = spinnerBinder()
			.on(spinnerX, spinnerY)
			.end(() -> update());

		baseBinder
			.toProduce(() -> new ModifyShapeProperty<>(property, canvas.getDrawing().getSelection().duplicateDeep(false), null))
			.then(c -> c.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue())))
			.when(handActiv)
			.bind();

		baseBinder
			.toProduce(() -> new ModifyEditingParameter<>(property, editing, null))
			.then(c -> c.setValue(ShapeFactory.INST.createPoint(spinnerX.getValue(), spinnerY.getValue())))
			.when(pencilActiv)
			.bind();
	}
}
