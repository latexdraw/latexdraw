package org.malai.interaction.library;

import java.awt.event.KeyEvent;

import org.malai.interaction.KeyPressureTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition should be used to cancel an interaction using key ESCAPE.
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
 * 08/18/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class EscapeKeyPressureTransition extends KeyPressureTransition {
	/**
	 * Creates the transition.
	 * @param inputState The source state.
	 * @param outputState The target state.
	 * @since 0.2
	 */
	public EscapeKeyPressureTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	@Override
	public boolean isGuardRespected() {
		return key==KeyEvent.VK_ESCAPE;
	}
}
