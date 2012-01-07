package org.malai.undo;

/**
 * This handler must help objet that want to be aware of
 * undone/redone event (for instance, to update some widgets).<br>
 * This file is part of libMalai.
 * Copyright (c) 2009-2012 Arnaud BLOUIN
 *
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public interface UndoHandler {
	/**
	 * Actions to do when an undoable object is added to the undo register.
	 * @param undoable The undoable object added to the undo register.
	 * @since 0.2
	 */
	void onUndoableAdded(final Undoable undoable);

	/**
	 * Actions to do when an undoable object is undone.
	 * @param undoable The undone object.
	 * @since 0.2
	 */
	void onUndoableUndo(final Undoable undoable);

	/**
	 * Actions to do when an undoable object is redone.
	 * @param undoable The redone object.
	 * @since 0.2
	 */
	void onUndoableRedo(final Undoable undoable);
}
