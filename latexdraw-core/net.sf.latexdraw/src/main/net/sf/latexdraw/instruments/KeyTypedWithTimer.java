package net.sf.latexdraw.instruments;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;
import org.malai.swing.interaction.KeyReleaseTransition;
import org.malai.swing.interaction.library.KeyInteraction;

/**
 * A KeyTyped interaction occurs when a key of a keyboard is pressed and then released.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public class KeyTypedWithTimer extends KeyInteraction {
	/**
	 * Creates the interaction.
	 * @since 0.2
	 */
	public KeyTypedWithTimer() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState timeout	= new TerminalState("timer"); //$NON-NLS-1$
		final IntermediaryState key = new IntermediaryState("released"); //$NON-NLS-1$

		addState(timeout);
		addState(key);

		new KeyInteractionKeyPressedTransition(initState, key);
		new KeyReleaseTransition(key, key) {
			@Override
			public boolean isGuardRespected() {
				return KeyTypedWithTimer.this.getLastHIDUsed()==this.hid;
			}
		};
		new KeyInteractionKeyPressedTransition(key, key) {
			@Override
			public boolean isGuardRespected() {
				return KeyTypedWithTimer.this.getLastHIDUsed()==this.hid;
			}
		};
		new TimeoutTransition(key, timeout, 200);
	}
}
