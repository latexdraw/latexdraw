package org.malai.interaction.library;

import javax.swing.text.JTextComponent;

import org.malai.interaction.Interaction;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TextChangedTransition;


/**
 * This interaction is performed when the text of a text field is modified.<br>
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
public class TextChanged extends Interaction {
	/** The modified text field. */
	protected JTextComponent textComp;

	/** The new text of the field. */
	protected String text;


	/**
	 * Creates the interaction.
	 */
	public TextChanged() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		text 	 = null;
		textComp = null;
	}



	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		TerminalState changed = new TerminalState("changed"); //$NON-NLS-1$

		addState(changed);

		new TextChangedTransition(initState, changed) {
			@Override
			public void action() {
				super.action();

				TextChanged.this.textComp = this.textComp;
				TextChanged.this.text 	  = this.text;
			}
		};
	}


	/**
	 * @return The modified text field.
	 * @since 0.2
	 */
	public JTextComponent getTextComp() {
		return textComp;
	}


	/**
	 * @return The new text of the field.
	 * @since 0.2
	 */
	public String getText() {
		return text;
	}
}
