package fr.eseo.malai.instrument.library;

import java.net.URI;

import fr.eseo.malai.action.library.OpenWebPage;
import fr.eseo.malai.instrument.Instrument;
import fr.eseo.malai.instrument.Link;
import fr.eseo.malai.interaction.library.MenuItemPressed;
import fr.eseo.malai.widget.MMenuItem;

/**
 * This link maps an action OpenWebPage to an interaction MenuItemPressed.<br>
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
 * 08/11/2011<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MenuItem2OpenWebPageLink extends Link<OpenWebPage, MenuItemPressed, Instrument> {
	/** The menu item that will be uses to create the action. */
	protected MMenuItem menuItem;

	/** The URI to open. */
	protected URI uri;

	/**
	 * Creates the link.
	 * @param ins The instrument that will contain the link.
	 * @param menuItem he menu item that will be uses to create the action.
	 * @param uri The URI to open.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 0.2
	 */
	public MenuItem2OpenWebPageLink(final Instrument ins, final MMenuItem menuItem,
									final URI uri) throws InstantiationException, IllegalAccessException {
		super(ins, false, OpenWebPage.class, MenuItemPressed.class);

		if(menuItem==null || uri==null)
			throw new IllegalArgumentException();

		this.uri		= uri;
		this.menuItem 	= menuItem;
	}


	@Override
	public void initAction() {
		action.setUri(uri);
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==menuItem;
	}
}
