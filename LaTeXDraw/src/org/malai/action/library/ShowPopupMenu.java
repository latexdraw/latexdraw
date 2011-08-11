package org.malai.action.library;

import org.malai.widget.MPopupMenu;

/**
 * This action shows a popup menu.<br>
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
 * 11/13/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class ShowPopupMenu extends SetWidgetPosition {
	/** The popup menu to show. */
	protected MPopupMenu popupMenu;

	/**
	 * Creates the action.
	 * @since 0.2
	 */
	public ShowPopupMenu() {
		super();

		popupMenu = null;
	}


	@Override
	public void flush() {
		super.flush();
		popupMenu = null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		popupMenu.show(component, (int)px, (int)py);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && popupMenu!=null;
	}


	/**
	 * @param popupMenu the popup menu to show.
	 * @since 0.2
	 */
	public void setPopupMenu(final MPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}
}
