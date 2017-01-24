/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2017 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import net.sf.latexdraw.instruments.PreferencesSetter;
import org.malai.action.ActionImpl;

/**
 * This action writes the preferences.
 */
public class WritePreferences extends ActionImpl {
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
	 * @param val The setter that sets the preferences.
	 * @since 3.0
	 */
	public void setSetter(final PreferencesSetter val) {
		setter = val;
	}


	@Override
	public void flush() {
		super.flush();
		setter = null;
	}
}
