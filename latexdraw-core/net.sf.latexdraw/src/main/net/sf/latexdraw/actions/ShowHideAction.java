package net.sf.latexdraw.actions;

import org.malai.action.Action;

/**
 * This abstract action modifies the visibility of an object.<br>
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
 * 11/12/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class ShowHideAction extends Action {
	/** True: the code panel will be show. Otherwise, it will be hide. */
	protected boolean show;

	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public ShowHideAction() {
		super();
	}


	/**
	 * Defines if the code panel must be hide or shown.
	 * @param show True: the code panel will be show. Otherwise, it will be hide.
	 * @since 3.0
	 */
	public void setVisible(final boolean show) {
		this.show = show;
	}
}
