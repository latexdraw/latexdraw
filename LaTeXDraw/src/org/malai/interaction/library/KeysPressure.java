package org.malai.interaction.library;

import java.util.ArrayList;
import java.util.List;

import org.malai.interaction.Interaction;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.KeyPressureTransition;
import org.malai.interaction.KeyReleaseTransition;
import org.malai.interaction.TerminalState;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This interaction permits to define combo a key pressed that can be used to define
 * shortcuts, etc.<br>
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
 * 06/06/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class KeysPressure extends Interaction {
	/** The list of the keys pressed. */
	protected List<Integer> keys;

	/** The object that produced the interaction. */
	protected Object object;


	/**
	 * Creates the interaction.
	 */
	public KeysPressure() {
		super();
		keys = new ArrayList<Integer>();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		if(keys!=null)
			keys.clear();
		object = null;
	}



	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		final TerminalState end			= new TerminalState("ended"); //$NON-NLS-1$

		addState(pressed);
		addState(end);

		new KeysPressureKeyPressedTransition(initState, pressed);
		new KeysPressureKeyPressedTransition(pressed, pressed) {
			@Override
			public boolean isGuardRespected() {
				return this.hid==KeysPressure.this.getLastHIDUsed();
			}
		};
		// The interaction stops once one of the key pressed is released. The other key pressed
		// events will be recycled.
		new KeyReleaseTransition(pressed, end) {
			@Override
			public boolean isGuardRespected() {
				return this.hid==KeysPressure.this.getLastHIDUsed() && KeysPressure.this.keys.contains(this.key);
			}
		};
	}



	/**
	 * Defines a transition modifying the keys attribute of the interaction.
	 */
	class KeysPressureKeyPressedTransition extends KeyPressureTransition {
		/**
		 * Creates the transition.
		 */
		public KeysPressureKeyPressedTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			KeysPressure.this.object = this.source;
			KeysPressure.this.keys.add(this.key);
			KeysPressure.this.setLastHIDUsed(this.hid);
		}
	}


	/**
	 * @return the keys pressed.
	 * @since 0.2
	 */
	public List<Integer> getKeys() {
		return keys;
	}


	/**
	 * @return The object that produced the interaction.
	 * @since 0.2
	 */
	public Object getObject() {
		return object;
	}
}

