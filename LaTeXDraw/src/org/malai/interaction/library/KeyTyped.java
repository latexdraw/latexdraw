package org.malai.interaction.library;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.KeyReleaseTransition;
import org.malai.interaction.TerminalState;

/**
 * A KeyTyped interaction occurs when a key of a keyboard is pressed and then released.<br>
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
 * 12/01/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class KeyTyped extends KeyInteraction {
	/**
	 * Creates the interaction.
	 * @since 0.2
	 */
	public KeyTyped() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState released 	= new TerminalState("released"); //$NON-NLS-1$
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$

		addState(pressed);
		addState(released);

		new KeyInteractionKeyPressedTransition(initState, pressed);
		new KeyReleaseTransition(pressed, released) {
			@Override
			public boolean isGuardRespected() {
				return super.isGuardRespected() && KeyTyped.this.key==this.key && KeyTyped.this.getLastHIDUsed()==this.hid;
			}
		};
	}
}
