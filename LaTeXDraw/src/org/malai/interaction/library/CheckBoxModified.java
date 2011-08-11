package org.malai.interaction.library;

import javax.swing.JCheckBox;

import org.malai.interaction.CheckBoxTransition;
import org.malai.interaction.Interaction;
import org.malai.interaction.TerminalState;


/**
 * This interaction is based on a checkbox that has been selected or unselected.<br>
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
public class CheckBoxModified extends Interaction {
	/** The modified checkbox. */
	protected JCheckBox checkBox;


	/**
	 * Creates the interaction.
	 */
	public CheckBoxModified() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new CheckBoxTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();

				CheckBoxModified.this.checkBox = this.checkBox;
			}
		};
	}


	/**
	 * @return The checkbox concerned by the interaction.
	 * @since 0.1
	 */
	public JCheckBox getCheckBox() {
		return checkBox;
	}
}
