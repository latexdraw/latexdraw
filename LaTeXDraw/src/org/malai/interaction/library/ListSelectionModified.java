package org.malai.interaction.library;

import java.awt.ItemSelectable;

import org.malai.interaction.Interaction;
import org.malai.interaction.ListTransition;
import org.malai.interaction.TerminalState;


/**
 * This interaction is based on a list which selection has been modified.<br>
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
public class ListSelectionModified extends Interaction {
	/** The list of objects which selection has been modified. */
	protected ItemSelectable list;

	/**
	 * Creates the interaction.
	 */
	public ListSelectionModified() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new ListTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ListSelectionModified.this.list = this.list;
			}
		};
	}


	/**
	 * @return The list concerned by the interaction.
	 * @since 0.2
	 */
	public ItemSelectable getList() {
		return list;
	}
}
