package org.malai.action.library;

/**
 * This action shows or hides a JComponent.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 11/20/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class ShowWidget extends WidgetAction {
	/** Defines if the component must be shown or hidden. */
	protected boolean visible;


	/**
	 * Creates the action.
	 * @since 0.2
	 */
	public ShowWidget() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		component.setVisible(visible);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && component.isVisible()!=visible;
	}


	/**
	 * @param visible Defines if the component must be shown or hidden.
	 * @since 0.2
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
