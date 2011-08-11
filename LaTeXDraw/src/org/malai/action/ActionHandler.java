package org.malai.action;

/**
 * This interface allows to create a bridge between an action and an
 * object that want to be aware about events on actions (such as creation or
 * deletion of an action).<br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * @author Arnaud Blouin
 * @since 0.1
 */
public interface ActionHandler {
	/** Corresponds to events that happen to actions. */
	enum ActionEvent {
		EVT_CANCEL, EVT_ADD, EVT_ABORT;
	}


	/**
	 * This method is called when an event on action occurred.
	 * @param action The concerned action.
	 * @param evt The kind of event.
	 * @since 0.1
	 */
	void onAction(final Action action, final ActionEvent evt);

	/**
	 * Called when an action is executed.
	 * @param action The executed action.
	 * @since 0.5
	 */
	void onActionExecuted(final Action action);
}
