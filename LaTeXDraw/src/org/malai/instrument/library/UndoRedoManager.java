package org.malai.instrument.library;

import javax.swing.ImageIcon;

import org.malai.action.library.Redo;
import org.malai.action.library.Undo;
import org.malai.error.ErrorCatcher;
import org.malai.instrument.Link;
import org.malai.instrument.WidgetInstrument;
import org.malai.interaction.library.ButtonPressed;
import org.malai.ui.UIComposer;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;
import org.malai.widget.MButton;

/**
 * This instrument allows to undo and redo saved actions.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 *
 * @author Arnaud BLOUIN
 * @date 05/24/10
 * @since 0.1
 * @version 0.2
 */
public class UndoRedoManager extends WidgetInstrument {
	/** The button used to undo actions. */
	protected MButton undoB;

	/** The button used to redo actions. */
	protected MButton redoB;


	/**
	 * Creates the instrument.
	 * @param composer The composer that compose the widgets provided by this instrument.
	 * @since 0.1
	 */
	public UndoRedoManager(final UIComposer<?> composer) {
		super(composer);

		initialiseWidgets();
		UndoCollector.INSTANCE.addHandler(this);
	}


	@Override
	protected void initialiseWidgets() {
		undoB = new MButton(new ImageIcon("src/org/malai/res/Undo.png")); //$NON-NLS-1$
		redoB = new MButton(new ImageIcon("src/org/malai/res/Redo.png")); //$NON-NLS-1$
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new ButtonPressed2Undo(this));
			addLink(new ButtonPressed2Redo(this));
		}catch(InstantiationException e){
			ErrorCatcher.INSTANCE.reportError(e);
		}catch(IllegalAccessException e){
			ErrorCatcher.INSTANCE.reportError(e);
		}
	}


	/**
	 * @return The button used to undo actions.
	 * @since 0.1
	 */
	public MButton getUndoB() {
		return undoB;
	}


	/**
	 * @return The button used to redo actions.
	 * @since 0.1
	 */
	public MButton getRedoB() {
		return redoB;
	}


	/**
	 * Updates the widgets of the instrument.
	 * @since 0.2
	 */
	public void updateWidgets() {
		if(activated) {
			Undoable undo = UndoCollector.INSTANCE.getLastUndo();
			Undoable redo = UndoCollector.INSTANCE.getLastRedo();

    		undoB.setEnabled(undo!=null);
    		redoB.setEnabled(redo!=null);
    		undoB.setToolTipText(undo==null ? null : undo.getUndoName());
    		redoB.setToolTipText(redo==null ? null : redo.getUndoName());
		}
	}



	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);

		undoB.setVisible(activated);
		redoB.setVisible(activated);

		if(activated)
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



/**
 * This link maps a button to a redo action.
 * @author Arnaud Blouin
 */
class ButtonPressed2Redo extends Link<Redo, ButtonPressed, UndoRedoManager> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains this link.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 */
	public ButtonPressed2Redo(final UndoRedoManager ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Redo.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		// Nothing to do.
	}


	@Override
	public boolean isConditionRespected() {
		return UndoCollector.INSTANCE.getLastRedo()!=null && getInteraction().getButton()==getInstrument().redoB;
	}
}



/**
 * This link maps a button to an undo action.
 * @author Arnaud Blouin
 */
class ButtonPressed2Undo extends Link<Undo, ButtonPressed, UndoRedoManager> {
	/**
	 * Creates the link.
	 * @param ins The instrument that contains this link.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 */
	public ButtonPressed2Undo(final UndoRedoManager ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Undo.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		// Nothing to do.
	}

	@Override
	public boolean isConditionRespected() {
		return UndoCollector.INSTANCE.getLastUndo()!=null && getInteraction().getButton()==getInstrument().undoB;
	}
}
