package org.malai.instrument.library;

import java.awt.Component;

import org.malai.action.library.ShowWidget;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.widget.MMenuItem;


/**
 * This link links a menu item interaction to an action that shows a JComponent.<br>
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
 * 11/20/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class MenuItem2ShowComponentLink<N extends Instrument> extends Link<ShowWidget, MenuItemPressed, N> {
	/** The menu item used to shows the component. */
	protected MMenuItem menuItem;

	/** The component to show. */
	protected Component component;


	/**
	 * Creates the link.
	 * @param ins The instrument that contains the link.
	 * @param component The component to show/hide.
	 * @param menuItem The menu item used to show/hide to component.
	 * @since 0.2
	 */
	public MenuItem2ShowComponentLink(final N ins, final Component component, final MMenuItem menuItem) throws InstantiationException, IllegalAccessException {
		super(ins, false, ShowWidget.class, MenuItemPressed.class);

		this.component	= component;
		this.menuItem	= menuItem;
	}


	@Override
	public void initAction() {
		action.setComponent(component);
		action.setVisible(true);
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==menuItem;
	}
}
