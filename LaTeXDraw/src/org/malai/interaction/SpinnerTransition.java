package org.malai.interaction;

import javax.swing.JSpinner;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;



/**
 * This transition is mapped to a spinner that has been modified.<br>
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
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class SpinnerTransition extends Transition {
	/** The modified spinner. */
	protected JSpinner spinner;


	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 * @since 0.2
	 */
	public SpinnerTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The modified spinner.
	 * @since 0.2
	 */
	public JSpinner getSpinner() {
		return spinner;
	}


	/**
	 * Sets the modified spinner.
	 * @param spinner The modified spinner.
	 * @since 0.2
	 */
	public void setSpinner(final JSpinner spinner) {
		if(spinner!=null)
			this.spinner = spinner;
	}
}
