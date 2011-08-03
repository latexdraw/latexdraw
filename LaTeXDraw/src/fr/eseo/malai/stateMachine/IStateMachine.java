package fr.eseo.malai.stateMachine;

/**
 * This interface defines the notion of state machine.<br>
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
 * 01/09/2011<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public interface IStateMachine {
	/** Terminates the state machine. */
	void onTerminating() throws MustAbortStateMachineException;

	/** Aborts the state machine. */
	void onAborting();

	/** Starts the state machine. */
	void onStarting() throws MustAbortStateMachineException;

	/** Updates the state machine. */
	void onUpdating() throws MustAbortStateMachineException;

	/**
	 * Adds a state to the state machine.
	 * @param state The state to add. Must not be null.
	 * @since 0.2
	 */
	void addState(final IState state);

	/**
	 * Reinits the state machine.
	 * @since 0.2
	 */
	void reinit();

	/**
	 * Defines if the state machine is activated.
	 * @param activated True: the state machine will be activated.
	 * @since 0.2
	 */
	void setActivated(final boolean activated);

	/**
	* @return True: the state machine is running.
	* @since 0.2
	*/
	boolean isRunning();
}
