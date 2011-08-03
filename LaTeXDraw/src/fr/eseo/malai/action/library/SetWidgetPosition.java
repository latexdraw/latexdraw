package fr.eseo.malai.action.library;

import java.awt.Component;

/**
 * Defines an action that move a widget to the given position.<br>
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
public class SetWidgetPosition extends PositionAction {
	/** The component to move. */
	protected Component component;


	/**
	 * Creates the action.
	 */
	public SetWidgetPosition() {
		super();

		component = null;
	}


	@Override
	public void flush() {
		super.flush();
		component = null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		component.setLocation((int)px, (int)py);
	}


	@Override
	public boolean canDo() {
		return super.canDo() && component!=null;
	}


	/**
	 * @param component The component to move.
	 * @since 0.2
	 */
	public void setComponent(final Component component) {
		this.component = component;
	}
}
