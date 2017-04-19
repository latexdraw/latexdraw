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

import org.malai.action.ActionImpl;

/**
 * This abstract action modifies the visibility of an object.
 * @author Arnaud Blouin
 */
public abstract class ShowHideAction extends ActionImpl {
	/** True: the code panel will be show. Otherwise, it will be hide. */
	protected boolean show;

	/**
	 * Creates the action.
	 * @since 3.0
	 */
    protected ShowHideAction() {
		super();
	}


	/**
	 * Defines if the code panel must be hide or shown.
	 * @param val True: the code panel will be show. Otherwise, it will be hide.
	 * @since 3.0
	 */
	public void setVisible(final boolean val) {
		show = val;
	}
}
