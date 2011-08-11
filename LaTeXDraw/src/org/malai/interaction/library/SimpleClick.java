package org.malai.interaction.library;

import java.awt.event.KeyEvent;

import org.malai.interaction.AbortingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.KeyPressureTransition;
import org.malai.interaction.MoveTransition;
import org.malai.interaction.ReleaseTransition;
import org.malai.interaction.TerminalState;


/**
 * This interaction defines a simple click. The interaction is aborted if the user move the pointing
 * device while pressing a button, or if key 'escape' is pressed.<br>
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
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class SimpleClick extends PointInteraction {
	/**
	 * Creates the interaction.
	 */
	public SimpleClick() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		IntermediaryState pressed 	= new IntermediaryState("pressed"); //$NON-NLS-1$
		AbortingState aborted		= new AbortingState("aborted"); //$NON-NLS-1$
		TerminalState ended			= new TerminalState("terminated"); //$NON-NLS-1$

		addState(pressed);
		addState(aborted);
		addState(ended);

		new PointPressureTransition(initState, pressed);
		new ReleaseTransition(pressed, ended) {
			@Override
			public boolean isGuardRespected() {
				return this.hid==SimpleClick.this.getLastHIDUsed() && this.button==SimpleClick.this.button;
			}
		};
		new MoveTransition(pressed, aborted);
		new KeyPressureTransition(pressed, aborted) {
			@Override
			public boolean isGuardRespected() {
				return this.key==KeyEvent.VK_ESCAPE;
			}
		};
	}
}
