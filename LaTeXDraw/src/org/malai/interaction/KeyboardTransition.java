package org.malai.interaction;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;


/**
 * This abstract transition is used a model for transition based on keyboard events.<br>
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
public abstract class KeyboardTransition extends Transition {
	/** The pressed key. */
	protected int key;

	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public KeyboardTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);

		key = -1;
	}


	/**
	 * @return The pressed key.
	 * @since 0.1
	 */
	public int getKey() {
		return key;
	}


	/**
	 * Sets the pressed key.
	 * @param key The pressed key.
	 * @since 0.1
	 */
	public void setKey(final int key) {
		this.key = key;
	}
}
