package org.malai.interaction;

import org.malai.stateMachine.MustAbortStateMachineException;

/**
 * Defines an interaction for objects that want to by notified when the state of an interaction changed.<br>
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
 * 10/10/2009<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public interface InteractionHandler {
	/**
	 * Happens when the interaction quits its initial state.
	 * @param interaction The concerned interaction.
	 * @throws MustAbortStateMachineException If the interaction must be aborted.
	 * @since 0.1
	 */
	void interactionStarts(final Interaction interaction) throws MustAbortStateMachineException;

	/**
	 * Happens when the interaction goes to standard state.
	 * @param interaction The concerned interaction.
	 * @throws MustAbortStateMachineException If the interaction must be aborted.
	 * @since 0.1
	 */
	void interactionUpdates(final Interaction interaction) throws MustAbortStateMachineException;

	/**
	 * Happens when the interaction goes to a terminal state.
	 * @param interaction The concerned interaction.
	 * @throws MustAbortStateMachineException If the interaction must be aborted.
	 * @since 0.1
	 */
	void interactionStops(final Interaction interaction) throws MustAbortStateMachineException;

	/**
	 * Happens when the interaction goes to an aborting state.
	 * @param interaction The concerned interaction.
	 * @since 0.1
	 */
	void interactionAborts(final Interaction interaction);
}
