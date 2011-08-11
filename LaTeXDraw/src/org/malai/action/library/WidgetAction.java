package org.malai.action.library;

import java.awt.Component;

import org.malai.action.Action;


/**
 * Defines an abstract action that concerns a widget.
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
 * 12/20/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public abstract class WidgetAction extends Action {
	/** The component concerned by the action. */
	protected Component component;

	/**
	 * Creates the action.
	 */
	public WidgetAction() {
		super();

		component = null;
	}


	@Override
	public boolean canDo() {
		return component!=null;
	}


	/**
	 * @param component The component concerned by the action.
	 * @since 0.2
	 */
	public void setComponent(final Component component) {
		this.component = component;
	}


	@Override
	public void flush() {
		super.flush();
		component = null;
	}
}
