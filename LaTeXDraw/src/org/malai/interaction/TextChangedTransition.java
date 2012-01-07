package org.malai.interaction;

import javax.swing.text.JTextComponent;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to a change of the text of a text field.<br>
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
 * 06/01/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class TextChangedTransition extends Transition {
	/** The modified text field. */
	protected JTextComponent textComp;

	/** The modified text. */
	protected String text;


	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public TextChangedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);

		textComp = null;
		text	 = null;
	}


	/**
	 * @return The modified text field.
	 * @since 0.1
	 */
	public JTextComponent getTextComp() {
		return textComp;
	}


	/**
	 * @return The modified text.
	 * @since 0.1
	 */
	public String getText() {
		return text;
	}


	/**
	 * Sets the modified text field.
	 * @param textComp The modified text field. Must not be null.
	 * @since 0.1
	 */
	public void setTextComp(final JTextComponent textComp) {
		if(textComp!=null)
			this.textComp = textComp;
	}


	/**
	 * Sets the modified text.
	 * @param text The modified text.
	 * @since 0.1
	 */
	public void setText(final String text) {
		if(text!=null)
			this.text = text;
	}
}
