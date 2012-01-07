package org.malai.action.library;

import org.malai.action.Action;
import org.malai.widget.MPanel;

/**
 * Defines an action that scrolls a panel.<br>
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
 * 05/11/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class Scroll extends Action {
	/** The panel that contains the scroller. */
	protected MPanel panel;

	/** The scrolling increment. */
	protected int increment;


	/**
	 * Creates and initialises a Scroll action.
	 * @since 0.2
	 */
	public Scroll() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		panel = null;
	}


	@Override
	public boolean canDo() {
		return panel!=null && panel.hasScrollPane();
	}



	@Override
	protected void doActionBody() {
		if(panel.isVerticalScrollbarVisible())
			panel.scrollVertically(increment*5);
		else
			panel.scrollHorizontally(increment*5);

		done();
	}



	@Override
	public boolean isRegisterable() {
		return false;
	}


	/**
	 * @return The panel to scroll.
	 * @since 0.2
	 */
	public MPanel getPanel() {
		return panel;
	}


	/**
	 * Sets the panel that contains the scroller.
	 * @param panel The new panel.
	 * @since 0.2
	 */
	public void setPanel(final MPanel panel) {
		this.panel = panel;
	}


	/**
	 * @return The increment.
	 * @since 0.2
	 */
	public int getIncrement() {
		return increment;
	}


	/**
	 * Sets the increment.
	 * @param increment The new value of the increment.
	 * @since 0.2
	 */
	public void setIncrement(final int increment) {
		this.increment = increment;
	}
}
