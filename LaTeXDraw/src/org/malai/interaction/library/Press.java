package org.malai.interaction.library;

import org.malai.interaction.TerminalState;

/**
 * This interaction starts and ends when a button of a pointing device is pressed.<br>
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
 * @since 0.1
 */
public class Press extends PointInteraction {
	/**
	 * Creates the interaction.
	 */
	public Press() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new PointPressureTransition(initState, pressed);
	}
}
