package org.malai.undo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Defines a collector of undone/redone objects.<br>
 * This file is part of libMalai.
 * Copyright (c) 2009-2011 Arnaud BLOUIN
 *
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * @date 05/23/2010
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public final class UndoCollector {
	/** The default undo/redo collector. */
	public static final UndoCollector INSTANCE = new UndoCollector();

	/** The label display when there is no redo possible */
	public static final String EMPTY_REDO = "redo";

	/** The label display when there is no undo action */
	public static final String EMPTY_UNDO = "undo";


	/** Contains the undoable actions. */
	private Stack<Undoable> undo;

	/** Contains the redoable actions. */
	private Stack<Undoable> redo;

	/** The maximal number of undo. */
	private int sizeMax;

	/** The handlers that handles the collector. */
	private List<UndoHandler> handlers;



	/**
	 * The constructor by default.
	 */
	private UndoCollector() {
		super();

		handlers = new ArrayList<UndoHandler>();
		undo 	 = new Stack<Undoable>();
		redo 	 = new Stack<Undoable>();
		sizeMax  = 30;
	}


	/**
	 * Adds a handler to the collector.
	 * @param handler The handler to add. Must not be null.
	 */
	public void addHandler(final UndoHandler handler) {
		if(handler!=null)
			handlers.add(handler);
	}



	/**
	 * Removes the given handler from the collector.
	 * @param handler The handler to remove. Must not be null.
	 */
	public void removeHandler(final UndoHandler handler) {
		if(handler!=null)
			handlers.remove(handler);
	}



	/**
	 * Removes all the actions of the collector.
	 */
	public void clear() {
		undo.clear();
		redo.clear();
	}


	/**
	 * Adds an undoable object to the collector.
	 * @param undoable The undoable object to add.
	 */
	public void add(final Undoable undoable) {
		if(undoable!=null && sizeMax>0) {
			if(undo.size()==sizeMax)
				undo.remove(0);

			undo.push(undoable);
			redo.clear(); /* The redoable actions must be removed. */

			for(UndoHandler handler : handlers)
				handler.onUndoableAdded(undoable);
		}
	}


	/**
	 * Undoes the last action.
	 */
	public void undo() {
		if(!undo.isEmpty()) {
			final Undoable undoable = undo.pop();
			undoable.undo();
			redo.push(undoable);

			for(UndoHandler handler : handlers)
				handler.onUndoableUndo(undoable);
		}
	}


	/**
	 * Redoes the last action.
	 */
	public void redo() {
		if(!redo.isEmpty()) {
			final Undoable action = redo.pop();
			action.redo();
			undo.push(action);

			for(UndoHandler handler : handlers)
				handler.onUndoableRedo(action);
		}
	}


	/**
	 * @return The last undoable action name.
	 */
	public String getLastUndoMessage() {
		return undo.isEmpty() ? null : undo.peek().getUndoName();
	}


	/**
	 * @return The last redoable action name.
	 */
	public String getLastRedoMessage() {
		return redo.isEmpty() ? null : redo.peek().getUndoName();
	}


	/**
	 * @return The last undoable action.
	 */
	public Undoable getLastUndo() {
		return undo.isEmpty() ? null : undo.peek();
	}


	/**
	 * @return The last redoable action.
	 */
	public Undoable getLastRedo() {
		return redo.isEmpty() ? null : redo.peek();
	}


	/**
	 * @return The max number of saved undoable objects.
	 */
	public int getSizeMax() {
		return sizeMax;
	}


	/**
	 * @param max The max number of saved undoable objects. Must be great than 0.
	 */
	public void setSizeMax(final int max) {
		if(max>=0) {
			for(int i=0, nb=undo.size()-max; i<nb; i++)
				undo.remove(0);
			this.sizeMax = max;
		}
	}


	/**
	 * @return The stack of saved undoable objects.
	 * @since 3.0
	 */
	public Stack<Undoable> getUndo() {
		return undo;
	}


	/**
	 * @return The stack of saved redoable objects
	 * @since 3.0
	 */
	public Stack<Undoable> getRedo() {
		return redo;
	}
}
