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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.util.LSystem;
import org.malai.action.library.Redo;
import org.malai.action.library.Undo;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

/**
 * This instrument allows to undo and redo saved actions.
 * This instrument provides two buttons to interact with and two shortcuts (ctrl/apple-Z, ctrl-apple-Y).
 * @author Arnaud Blouin
 */
public class UndoRedoManager extends CanvasInstrument implements Initializable {
	/** The button used to undo actions. */
	@FXML private Button undoB;
	/** The button used to redo actions. */
	@FXML private Button redoB;

	/**
	 * Creates the instrument.
	 */
	public UndoRedoManager() {
		super();
		UndoCollector.INSTANCE.addHandler(this);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		buttonBinder(Undo.class).on(undoB).when(i -> UndoCollector.INSTANCE.getLastUndo() != null).bind();
		buttonBinder(Redo.class).on(redoB).when(i -> UndoCollector.INSTANCE.getLastRedo() != null).bind();
		keyNodeBinder(Undo.class).on(canvas).with(KeyCode.Z, LSystem.INSTANCE.getControlKey()).when(i -> UndoCollector.INSTANCE.getLastUndo() != null).bind();
		keyNodeBinder(Redo.class).on(canvas).with(KeyCode.Y, LSystem.INSTANCE.getControlKey()).when(i -> UndoCollector.INSTANCE.getLastRedo() != null).bind();
	}

	/**
	 * Updates the widgets of the instrument.
	 */
	public void updateWidgets() {
		if(activated) {
			final Undoable undo = UndoCollector.INSTANCE.getLastUndo();
			final Undoable redo = UndoCollector.INSTANCE.getLastRedo();

			undoB.setDisable(undo == null);
			redoB.setDisable(redo == null);
			undoB.setTooltip(undo == null ? null : new Tooltip(undo.getUndoName()));
			redoB.setTooltip(redo == null ? null : new Tooltip(redo.getUndoName()));
		}
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);

		undoB.setVisible(act);
		redoB.setVisible(act);

		if(act) updateWidgets();
	}


	@Override
	public void onUndoableCleared() {
		updateWidgets();
	}


	@Override
	public void onUndoableAdded(final Undoable undoable) {
		updateWidgets();
	}


	@Override
	public void onUndoableUndo(final Undoable undoable) {
		updateWidgets();
	}


	@Override
	public void onUndoableRedo(final Undoable undoable) {
		updateWidgets();
	}
}
