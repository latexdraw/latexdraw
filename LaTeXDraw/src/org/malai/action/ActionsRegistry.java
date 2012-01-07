package org.malai.action;

import java.util.ArrayList;
import java.util.List;

import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

/**
 * A register of actions.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud Blouin
 * @since 0.1
 */
public final class ActionsRegistry {
	/** The saved actions. */
	private List<Action> actions;

	/** The actions handlers. */
	private List<ActionHandler> handlers;

	/** The max number of actions that can contains the register. */
	private int sizeMax;

	/** The register of actions. */
	public static final ActionsRegistry INSTANCE = new ActionsRegistry();



	/**
	 * Creates and initialises a register.
	 * @since 0.1
	 */
	private ActionsRegistry() {
		super();
		actions 	= new ArrayList<Action>();
		handlers 	= new ArrayList<ActionHandler>();
		sizeMax		= 30;
	}



	/**
	 * Notifies handlers that an action has been executed.
	 * @param action The executed action.
	 * @since 0.2
	 */
	public void onActionExecuted(final Action action) {
		if(action!=null)
			for(ActionHandler handler : handlers)
				handler.onActionExecuted(action);
	}



	/**
	 * Notifies handlers that an action ends.
	 * @param action The action that ends.
	 * @since 0.2
	 */
	public void onActionDone(final Action action) {
		if(action!=null)
			for(ActionHandler handler : handlers)
				handler.onActionDone(action);
	}



	/**
	 * @return The stored actions. Cannot be null.
	 * @since 0.1
	 */
	public List<Action> getActions() {
		return actions;
	}



	/**
	 * Removes and flushes actions from the register using a given action.
	 * @param action The action that may cancels others.
	 * @since 0.1
	 */
	public void cancelActions(final Action action) {
		if(action==null)
			return;

		int i=0;

		while(i<actions.size())
			if(actions.get(i).cancelledBy(action)) {
				Action act = actions.remove(i);

				for(ActionHandler handler : handlers)
					handler.onActionCancelled(act);

				act.flush();
			}
			else
				i++;
	}



	/**
	 * Adds an action to the register. Before being added, the given action is used to cancel actions
	 * already added. Handlers are notified of the add of the given action. If Undoable, the action is
	 * added to the undo collector as well.
	 * @param action The action to add. Cannot be null.
	 * @param actionHanndler The handler that produced or is associated to the action. Cannot be null.
	 * @since 0.2
	 */
	public void addAction(final Action action, final ActionHandler actionHanndler) {
		if(action!=null && actionHanndler!=null && !actions.contains(action) && sizeMax>0) {
			cancelActions(action);

			// If there is too many actions in the register, the oldest action is removed and flushed.
			if(actions.size()==sizeMax)
				actions.remove(0).flush();

			actions.add(action);

			for(ActionHandler handler : handlers)
				handler.onActionAdded(action);

			if(action instanceof Undoable)
				UndoCollector.INSTANCE.add((Undoable)action, actionHanndler);
		}
	}



	/**
	 * Removes the action from the register. The action is then flushes.
	 * @param action The action to remove.
	 * @since 0.1
	 */
	public void removeAction(final Action action) {
		if(action==null)
			return;

		actions.remove(action);
		action.flush();
	}



	/**
	 * Adds an action handler.
	 * @param handler The handler to add.
	 * @since 0.1
	 */
	public void addHandler(final ActionHandler handler) {
		if(handler!=null)
			handlers.add(handler);
	}


	/**
	 * Removes the given handler.
	 * @param handler The handler to remove.
	 * @since 0.2
	 */
	public void removeHandler(final ActionHandler handler) {
		if(handler!=null)
			handlers.remove(handler);
	}


	/**
	 * Removes all the action handlers.
	 * @since 0.2
	 */
	public void removeAllHandlers() {
		handlers.clear();
	}


	/**
	 * @param clazz The reference class.
	 * @return The first action of the exact same class of the given class.
	 * @since 0.1
	 */
	public <T extends Action> T getAction(final Class<T> clazz) {
		T action = null;

		if(clazz!=null)
			for(int i=0, size=actions.size(); i<size && action==null; i++)
				if(actions.get(i).getClass()==clazz)
					action = clazz.cast(actions.get(i));

		return action;
	}



	/**
	 * Aborts the given action, i.e. the action is aborted then remove from
	 * the register. Handlers are then notified. The action is finally flushes.
	 * @param action The action to abort.
	 * @since 0.1
	 */
	public void abortAction(final Action action) {
		if(action!=null) {
			action.abort();
			actions.remove(action);

			for(ActionHandler handler : handlers)
				handler.onActionAborted(action);

			action.flush();
		}
	}


	/**
	 * @return The max number of actions that can contains the register.
	 * @since 0.2
	 */
	public int getSizeMax() {
		return sizeMax;
	}


	/**
	 * @param sizeMax The max number of actions that can contains the register. Must be equal or greater than 0.
	 * @since 0.2
	 */
	public void setSizeMax(final int sizeMax) {
		if(sizeMax>=0) {
			// If there is too many actions in the register, they are removed.
			for(int i=0, nb=actions.size()-sizeMax; i<nb; i++)
				actions.remove(0).flush();

			this.sizeMax = sizeMax;
		}
	}
}
