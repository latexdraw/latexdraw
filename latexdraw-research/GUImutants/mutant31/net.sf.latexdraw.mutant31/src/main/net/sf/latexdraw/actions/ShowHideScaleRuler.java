package net.sf.latexdraw.actions;

import net.sf.latexdraw.ui.ScaleRuler;

/**
 * This action shows/hides scale rulers.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 11/12/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ShowHideScaleRuler extends ShowHideAction {
	/** The scale ruler to show/hide. */
	protected ScaleRuler ruler;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public ShowHideScaleRuler() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		ruler = null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		ruler.setVisible(show);
	}


	@Override
	public boolean canDo() {
		return ruler!=null;
	}


	/**
	 * @param ruler The scale ruler to show/hide.
	 * @since 3.0
	 */
	public void setRuler(final ScaleRuler ruler) {
		this.ruler = ruler;
	}
}
