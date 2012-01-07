package org.malai.widget;

import javax.swing.JEditorPane;

import org.malai.interaction.Eventable;
import org.malai.interaction.SwingEventManager;

/**
 * This widgets is based on a JEditorPane. It allows to be used in the Malai framework for picking.<br>
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
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MEditorPane extends JEditorPane implements Scrollable, Eventable {
	private static final long serialVersionUID = 1L;

	/** The possible scrollpane that contains the panel. */
	protected MScrollPane scrollpane;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;


	/**
	 * Creates a EditorPane.
	 * @param withScrollPane True: the panel will contain a scroll pane.
	 * @param withEvtManager True: the panel will have an event manager that gathers
	 * events produced using the editor pane (for picking).
	 * @since 0.2
	 */
	public MEditorPane(final boolean withScrollPane, final boolean withEvtManager) {
		super();

		if(withScrollPane) {
			scrollpane = new MScrollPane();
			scrollpane.getViewport().add(this);
		}

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}
	}


	@Override
	public boolean hasScrollPane() {
		return scrollpane!=null;
	}



	@Override
	public MScrollPane getScrollpane() {
		return scrollpane;
	}


	@Override
	public SwingEventManager getEventManager() {
		return eventManager;
	}


	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}
}
