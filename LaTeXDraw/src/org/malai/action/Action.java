package org.malai.action;

/**
 * An action is based on the command design pattern: it is an object that
 * encapsulates information to execute a task and to undo/redo it if
 * necessary.<br>
 * <br>
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
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class Action {
	/** Defines the different state of the action. @since 0.2 */
	public static enum ActionStatus { CREATED, EXECUTED, ABORTED, DONE, FLUSHED }

	/** Provides the state of the action. */
	protected ActionStatus status;


	/**
	 * The default constructor.
	 */
	public Action() {
		super();
		status = ActionStatus.CREATED;
	}


	/**
	 * When an action is no more useful it can be flushes to release the used data.
	 * Should be overridden.
	 * @since 0.2
	 */
	public void flush() {
		status = ActionStatus.FLUSHED;
	}


	/**
	 * Specifies if the action must be saved in the action register. For instance,
	 * some actions, such as a scroll of the scroll bars, should not be saved nor
	 * put in the undo/redo manager. Thus, they must not be registrable.
	 * @return True: the action is registrable.
	 * @since 0.1
	 */
	public abstract boolean isRegisterable();



	/**
	 * Executes the action. Should be overridden by sub-class to define stuffs to execute.
	 * @since 0.1
	 * @return True if the execution is successful. False otherwise.
	 */
	public boolean doIt() {
		boolean ok;

		if(canDo()) {
			ok = true;
			doActionBody();
			status = ActionStatus.EXECUTED;
			ActionsRegistry.INSTANCE.onActionExecuted(this);
		}
		else ok = false;

		return ok;
	}


	/**
	 * This method contains the core code to execute when the action is executed.
	 * @since 0.1
	 */
	protected abstract void doActionBody();



	/**
	 * @return True if the action can be executed.
	 * @since 0.1
	 */
	public abstract boolean canDo();


	/**
	 * @return True if the execution of the action had effects on the target.
	 * By default this function return the result of isDone. Should be overridden.
	 * @since 0.1
	 */
	public boolean hadEffect() {
		return isDone();
	}



	/**
	 * Defines if the given action can cancel the calling action. Should be overridden.
	 * @param action The action to test.
	 * @return True if the given action cancels the calling action.
	 * @since 0.1
	 */
	public boolean cancelledBy(final Action action) {
		return false;
	}



	/**
	 * Sets the action to "done".
	 * @since 0.1
	 */
	public void done() {
		status = ActionStatus.DONE;
		ActionsRegistry.INSTANCE.onActionDone(this);
	}



	/**
	 * @return True if the action is done.
	 * @since 0.1
	 */
	public boolean isDone() {
		return status==ActionStatus.DONE;
	}



	@Override
	public String toString() {
		return getClass().getSimpleName();
	}



	/**
	 * Aborts the action.
	 * @since 0.1
	 */
	public void abort() {
		status = ActionStatus.ABORTED;
	}


	/**
	 * @return True if the action has already started.
	 * @since 0.1
	 */
	public boolean isStarted() {
		return status!=ActionStatus.CREATED;
	}



	/**
	 * Provides the state of the action.
	 * @return The state of the action.
	 * @since 0.2
	 */
	public ActionStatus getStatus() {
		return status;
	}
}
