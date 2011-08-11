package org.malai.interaction.library;

import javax.swing.JSpinner;

import org.malai.interaction.Interaction;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.SpinnerTransition;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;


/**
 * This interaction is based on a spinner that has been modified.<br>
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
 * @since 0.2
 */
public class SpinnerModified extends Interaction {
	/** The modified spinner. */
	protected JSpinner spinner;


	/**
	 * Creates the interaction.
	 */
	public SpinnerModified() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		IntermediaryState pressed 	= new IntermediaryState("pressed"); //$NON-NLS-1$
		TerminalState timeout 		= new TerminalState("timeout"); //$NON-NLS-1$

		addState(timeout);
		addState(pressed);

		new SpinnerModifiedSpinnerTransition(initState, pressed);
		new SpinnerModifiedSpinnerTransition(pressed, pressed);
		new TimeoutTransition(pressed, timeout, 1000);
	}



	/**
	 * @return The spinner concerned by the interaction.
	 * @since 0.2
	 */
	public JSpinner getSpinner() {
		return spinner;
	}


	/**
	 * The kind of transition of the SpinnerModified corresponding to a spinner event.
	 */
	class SpinnerModifiedSpinnerTransition extends SpinnerTransition {
		/**
		 * Creates the transition.
		 */
		public SpinnerModifiedSpinnerTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			super.action();
			SpinnerModified.this.spinner = this.spinner;
		}
	}
}
