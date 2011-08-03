package fr.eseo.malai.interaction.library;

import javax.swing.JMenuItem;

import fr.eseo.malai.interaction.Interaction;
import fr.eseo.malai.interaction.MenuItemTransition;
import fr.eseo.malai.interaction.TerminalState;

/**
 * This interaction is based on a menu item that has been modified.<br>
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
 * 05/21/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class MenuItemPressed extends Interaction {
	/** The pressed menu item. */
	protected JMenuItem menuItem;


	/**
	 * Creates the interaction.
	 */
	public MenuItemPressed() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new MenuItemTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();

				MenuItemPressed.this.menuItem = this.menuItem;
			}
		};
	}


	/**
	 * @return The pressed menu item.
	 * @since 0.2
	 */
	public JMenuItem getMenuItem() {
		return menuItem;
	}
}
