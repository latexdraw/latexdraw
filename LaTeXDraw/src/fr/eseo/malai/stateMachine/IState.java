package fr.eseo.malai.stateMachine;

import java.util.List;

/**
 * This interface defines the notion of state that composes a state machine.<br>
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
 * 11/03/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public interface IState {
	/**
	 * Adds a transition to the state.
	 * @param trans The new transition. Must not be null.
	 * @since 0.1
	 */
	void addTransition(final ITransition trans);

	/**
	 * @return The name of the state.
	 * @since 0.1
	 */
	String getName();

	/**
	 * @return The transitions that leaves the state.
	 * @since 0.1
	 */
	List<ITransition> getTransitions();

	/**
	 * @param index The position of the transition to get.
	 * @return The corresponding transition or null if the given index is not valid.
	 * @since 0.2
	 */
	ITransition getTransition(final int index);

	/**
	 * Sets the state machine of the state.
	 * @param sm The new state machine. If null, nothing is done.
	 * @since 0.2
	 */
	void setStateMachine(final IStateMachine sm);

	/**
	 * @return The state machine that contains the state.
	 * @since 0.2
	 */
	IStateMachine getStateMachine();
}