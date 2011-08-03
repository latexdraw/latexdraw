package fr.eseo.malai.widget;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import fr.eseo.malai.interaction.Eventable;
import fr.eseo.malai.interaction.SwingEventManager;

/**
 * This widgets is based on a JEditorPane. It allows to be used in the Malai framework for picking.<br>
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
 * 31/05/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class MFrame extends JFrame implements Eventable {
	private static final long serialVersionUID = 1L;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;

	/**
	 * Creates the frame.
	 * @param withEvtManager True: the panel will have an event manager that gathers
	 * events produced using the editor pane (for picking).
	 * @since 0.2
	 */
	public MFrame(final boolean withEvtManager) {
		this("", withEvtManager); //$NON-NLS-1$
	}


	/**
	 * Creates the frame.
	 * @param title The title of the frame.
	 * @param withEvtManager True: the panel will have an event manager that gathers
	 * events produced using the editor pane (for picking).
	 * @since 0.2
	 */
	public MFrame(final String title, final boolean withEvtManager) {
		super(title);

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);

	     	addWindowListener(
                new WindowAdapter() {
    				@Override
    				public void windowClosing(final WindowEvent e) {
    					MFrame.this.eventManager.windowClosed(MFrame.this);
                    }
            });
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
