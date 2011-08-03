package fr.eseo.malai.interaction.library;

import javax.swing.AbstractButton;

import fr.eseo.malai.interaction.ButtonPressedTransition;
import fr.eseo.malai.interaction.Interaction;
import fr.eseo.malai.interaction.TerminalState;

/**
 * A ButtonPressed interaction occurs when a button is pressed.<br>
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
public class ButtonPressed extends Interaction {
	/** The pressed button. */
	protected AbstractButton button;

	/**
	 * Creates the interaction.
	 */
	public ButtonPressed() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		button = null;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new ButtonPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();

				ButtonPressed.this.button = this.button;
			}
		};
	}


	/**
	 * @return The pressed button.
	 * @since 0.1
	 */
	public AbstractButton getButton() {
		return button;
	}
}
