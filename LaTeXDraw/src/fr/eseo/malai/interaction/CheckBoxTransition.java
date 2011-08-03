package fr.eseo.malai.interaction;

import javax.swing.JCheckBox;

import fr.eseo.malai.stateMachine.SourceableState;
import fr.eseo.malai.stateMachine.TargetableState;


/**
 * This transition is mapped to a checkbox that has been modified.<br>
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
public class CheckBoxTransition extends Transition {
	/** The modified checkbox. */
	protected JCheckBox checkBox;

	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 * @since 0.2
	 */
	public CheckBoxTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The modified checkbox.
	 * @since 0.2
	 */
	public JCheckBox getCheckBox() {
		return checkBox;
	}


	/**
	 * Sets the checkbox that produced the transition.
	 * @param checkBox The checkbox.
	 * @since 0.2
	 */
	public void setCheckBox(final JCheckBox checkBox) {
		if(checkBox!=null)
			this.checkBox = checkBox;
	}
}
