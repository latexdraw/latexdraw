package org.malai.interaction;

import javax.swing.AbstractButton;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;


/**
 * This transition must be used to use a button pressed event within an interaction.<br>
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
 * 06/01/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class ButtonPressedTransition extends Transition {
	/** The pressed button. */
	protected AbstractButton button;

	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public ButtonPressedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The pressed button.
	 * @since 0.1
	 */
	public AbstractButton getButton() {
		return button;
	}


	/**
	 * Sets the pressed button.
	 * @param button The pressed button. Must not be null.
	 * @since 0.2
	 */
	public void setButton(final AbstractButton button) {
		if(button!=null)
			this.button = button;
	}
}
