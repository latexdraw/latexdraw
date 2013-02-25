package net.sf.latexdraw.actions;

import net.sf.latexdraw.instruments.PreferencesSetter;

import org.malai.action.Action;

/**
 * This action writes the preferences.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 31/05/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class WritePreferences extends Action {
	/** The setter that sets the preferences. */
	protected PreferencesSetter setter;
	

	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		setter.writeXMLPreferences();
	}


	@Override
	public boolean canDo() {
		return setter!=null;
	}


	/**
	 * @param setter The setter that sets the preferences.
	 * @since 3.0
	 */
	public void setSetter(final PreferencesSetter setter) {
		this.setter = setter;
	}


	@Override
	public void flush() {
		super.flush();
		setter = null;
	}
}
