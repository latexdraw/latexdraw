package org.malai.interaction.library;

import javax.swing.JTabbedPane;

import org.malai.interaction.Interaction;
import org.malai.interaction.TabSelectedTransition;
import org.malai.interaction.TerminalState;

/**
 * A TabSelected interaction occurs when the selected tab of a tabbed changed.<br>
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
 * 12/09/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class TabSelected extends Interaction {
	/** The tabbed panel that changed. */
	protected JTabbedPane tabbedPane;

	/**
	 * Creates the interaction.
	 * @since 0.2
	 */
	public TabSelected() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();
		tabbedPane = null;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState tabSelected = new TerminalState("tabSelected"); //$NON-NLS-1$
		addState(tabSelected);

		new TabSelectedTransition(initState, tabSelected) {
			@Override
			public void action() {
				super.action();
				TabSelected.this.tabbedPane = this.tabbedPane;
			}
		};
	}


	/**
	 * @return The tabbed panel that changed.
	 * @since 0.2
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}
