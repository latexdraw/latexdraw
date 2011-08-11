package org.malai.action.library;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import org.malai.action.Action;


/**
 * This action opens an URI in the default browser.<br>
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
public class OpenWebPage extends Action {
	/** The URI to open. */
	protected URI uri;

	private boolean browsed;

	/**
	 * Creates the action.
	 * @since 0.2
	 */
	public OpenWebPage() {
		super();
		browsed = false;
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		try{
			Desktop.getDesktop().browse(uri);
			browsed = true;
		}catch(IOException exception){ browsed = false; }
	}


	@Override
	public boolean canDo() {
		return uri!=null && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
	}

	@Override
	public boolean hadEffect() {
		return super.hadEffect() && browsed;
	}

	/**
	 * @param uri The URI to open.
	 * @since 0.2
	 */
	public void setUri(final URI uri) {
		this.uri = uri;
	}
}
