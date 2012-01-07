package org.malai.interaction.library;

import java.util.ArrayList;
import java.util.List;

import org.malai.interaction.Interaction;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.KeyPressureTransition;
import org.malai.interaction.KeyReleaseTransition;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;

/**
 * A KeyTyped interaction occurs when several keys are typed. It stops when the defined timeout expired.<br>
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
 * <br>
 * 08/13/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class KeysTyped extends Interaction {
	/** The keys pressed while scrolling. */
	protected List<Integer> keys;

	/** The timeout transition. Used to set the timeout value. */
	protected TimeoutTransition timeoutTransition;

	/** The object that produced the interaction. */
	protected Object object;


	/**
	 * Creates the interaction.
	 * @since 0.2
	 */
	public KeysTyped() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		if(keys==null)
			keys = new ArrayList<Integer>();
		keys.clear();
		object = null;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		final TerminalState ended 		= new TerminalState("ended"); //$NON-NLS-1$

		addState(pressed);
		addState(ended);

		new KeyPressureTransition(initState, pressed) {
			@Override
			public void action() {
				KeysTyped.this.object = this.source;
				KeysTyped.this.setLastHIDUsed(this.hid);
				KeysTyped.this.keys.add(this.key);
			}
		};

		new KeyPressureTransition(pressed, pressed) {
			@Override
			public void action() {
				KeysTyped.this.object = this.source;
				KeysTyped.this.keys.add(this.key);
			}

			@Override
			public boolean isGuardRespected() {
				return this.hid==KeysTyped.this.getLastHIDUsed();
			}
		};

		new KeyReleaseTransition(pressed, pressed) {
			@Override
			public void action() {
				KeysTyped.this.object = this.source;
				KeysTyped.this.keys.remove((Integer)this.key);
			}

			@Override
			public boolean isGuardRespected() {
				return this.hid==KeysTyped.this.getLastHIDUsed();
			}
		};

		timeoutTransition = new TimeoutTransition(pressed, ended, 1000);
	}


	/**
	 * @return the current key pressed.
	 * @since 0.2
	 */
	public List<Integer> getKeys() {
		return keys;
	}


	/**
	 * @return the current timeout.
	 * @since 0.2
	 */
	public int getTimeout() {
		return timeoutTransition.getTimeout();
	}


	/**
	 * @param timeout the timeout to set. Must be greater than 0.
	 * @since 0.2
	 */
	public void setTimeout(final int timeout) {
		if(timeout>0)
			timeoutTransition.setTimeout(timeout);
	}


	/**
	 * @return The object that produced the interaction.
	 * @since 0.2
	 */
	public Object getObject() {
		return object;
	}
}
