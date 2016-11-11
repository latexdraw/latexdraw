/*
 * This file is part of latexdraw.<br>
 * Copyright (c) 2005-2016 Arnaud BLOUIN<br>
 * <br>
 * latexdraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * latexdraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.util.LSystem;
import org.malai.action.library.Redo;
import org.malai.action.library.Undo;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.ButtonPressed;
import org.malai.javafx.interaction.library.KeysPressure;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

/**
 * This instrument allows to undo and redo saved actions.
 * This instrument provides two buttons to interact with and two shortcuts
 * (ctrl/apple-Z, ctrl-apple-Y).
 */
public class UndoRedoManager extends CanvasInstrument implements Initializable {
	/** The button used to undo actions. */
	@FXML protected Button undoB;

	/** The button used to redo actions. */
	@FXML protected Button redoB;


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
	protected void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		addInteractor(new ButtonPressed2Undo());
		addInteractor(new ButtonPressed2Redo());
		addInteractor(new Keys2Undo());
		addInteractor(new Keys2Redo());
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

	class ButtonPressed2Redo extends JfxInteractor<Redo, ButtonPressed, UndoRedoManager> {
		ButtonPressed2Redo() throws InstantiationException, IllegalAccessException {
			super(UndoRedoManager.this, false, Redo.class, ButtonPressed.class, redoB);
		}

		@Override
		public void initAction() {
			// Nothing to do.
		}

		@Override
		public boolean isConditionRespected() {
			return UndoCollector.INSTANCE.getLastRedo() != null;
		}
	}

	class ButtonPressed2Undo extends JfxInteractor<Undo, ButtonPressed, UndoRedoManager> {
		ButtonPressed2Undo() throws InstantiationException, IllegalAccessException {
			super(UndoRedoManager.this, false, Undo.class, ButtonPressed.class, undoB);
		}

		@Override
		public void initAction() {
			// Nothing to do.
		}

		@Override
		public boolean isConditionRespected() {
			return UndoCollector.INSTANCE.getLastUndo() != null;
		}
	}

	class Keys2Redo extends JfxInteractor<Redo, KeysPressure, UndoRedoManager> {
		Keys2Redo() throws InstantiationException, IllegalAccessException {
			super(UndoRedoManager.this, false, Redo.class, KeysPressure.class, canvas);
		}

		@Override
		public void initAction() {
			// Nothing to do.
		}

		@Override
		public boolean isConditionRespected() {
			final List<KeyCode> keys = getInteraction().getKeyCodes();
			return UndoCollector.INSTANCE.getLastRedo() != null && keys.size() == 2 && keys.contains(KeyCode.Y) && keys.contains(LSystem.INSTANCE.getControlKey());
		}
	}

	class Keys2Undo extends JfxInteractor<Undo, KeysPressure, UndoRedoManager> {
		Keys2Undo() throws InstantiationException, IllegalAccessException {
			super(UndoRedoManager.this, false, Undo.class, KeysPressure.class, canvas);
		}

		@Override
		public void initAction() {
			// Nothing to do.
		}

		@Override
		public boolean isConditionRespected() {
			final List<KeyCode> keys = getInteraction().getKeyCodes();
			return UndoCollector.INSTANCE.getLastRedo() != null && keys.size() == 2 && keys.contains(KeyCode.Z) && keys.contains(LSystem.INSTANCE.getControlKey());
		}
	}
}
