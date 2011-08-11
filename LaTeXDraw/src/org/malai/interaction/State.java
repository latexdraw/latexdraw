package org.malai.interaction;

import java.util.ArrayList;
import java.util.List;

import org.malai.stateMachine.IState;
import org.malai.stateMachine.IStateMachine;
import org.malai.stateMachine.ITransition;


/**
 * A state is a component of a state machine.<br>
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
 * 10/10/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public abstract class State implements IState {
	/** The name of the state. */
	protected String name;

	/** The list of the transitions that leave the state. */
	protected List<ITransition> transitions;

	/** The state machine that contains the state. */
	protected Interaction stateMachine;


	/**
	 * Creates the state.
	 * @param name The name of the state.
	 * @throws IllegalArgumentException If the given name is null.
	 * @since 0.1
	 */
	public State(final String name) {
		super();

		if(name==null)
			throw new IllegalArgumentException();

		this.name 	 = name;
		stateMachine = null;
		transitions  = new ArrayList<ITransition>();
	}


	@Override
	public void setStateMachine(final IStateMachine sm) {
		if(sm instanceof Interaction)
			stateMachine = (Interaction)sm;
	}


	@Override
	public Interaction getStateMachine() {
		return stateMachine;
	}


	@Override
	public void addTransition(final ITransition trans) {
		if(trans!=null)
			transitions.add(trans);
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public List<ITransition> getTransitions() {
		return transitions;
	}


	@Override
	public ITransition getTransition(final int i) {
		return i<0 || i>=transitions.size() ? null : transitions.get(i);
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(getClass().getCanonicalName()).append('[').append(name).append(", "); //$NON-NLS-1$

		for(final ITransition t : transitions)
			sb.append(t).append(", "); //$NON-NLS-1$

		sb.append(']');

		return sb.toString();
	}
}
