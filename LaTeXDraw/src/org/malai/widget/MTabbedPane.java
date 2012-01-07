package org.malai.widget;

import javax.swing.JTabbedPane;

import org.malai.interaction.Eventable;
import org.malai.interaction.SwingEventManager;

/**
 * Extends the Java JTabbedPane to conform malai requirements.<br>
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
 * 12/09/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class MTabbedPane extends JTabbedPane implements Eventable {
	private static final long serialVersionUID = 1L;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;
	

	/**
	 * {@link JTabbedPane#JTabbedPane()}
	 *  @param withEvtManager True: the tabbed panel will have an event manager that gathers events produced using the editor pane (for picking).
	 */
	public MTabbedPane(final boolean withEvtManager) {
		this(TOP, WRAP_TAB_LAYOUT, withEvtManager);
	}


	/**
	 * {@link JTabbedPane#JTabbedPane(int)}
	 * @param withEvtManager True: the tabbed panel will have an event manager that gathers events produced using the editor pane (for picking).
	 * @param tabPlacement the placement for the tabs relative to the content
	 */
	public MTabbedPane(final int tabPlacement, final boolean withEvtManager) {
		this(tabPlacement, WRAP_TAB_LAYOUT, withEvtManager);
	}


	/**
	 * {@link JTabbedPane#JTabbedPane(int, int)}
	 * @param withEvtManager True: the tabbed panel will have an event manager that gathers events produced using the editor pane (for picking).
	 * @param tabPlacement the placement for the tabs relative to the content
     * @param tabLayoutPolicy the policy for laying out tabs when all tabs will not fit on one run
	 */
	public MTabbedPane(final int tabPlacement, final int tabLayoutPolicy, final boolean withEvtManager) {
		super(tabPlacement, tabLayoutPolicy);
		
		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}
	}


	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}


	@Override
	public SwingEventManager getEventManager() {
		return eventManager;
	}
}
