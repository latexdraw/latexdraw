package org.malai.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.HashSet;
import java.util.Set;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;


/**
 * A UI manager registers all the UIs of the applications in order to manage some technical
 * aspects such as cleaning pending events of a UI losing the focus.<br>
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
 * 07/22/2011<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public final class UIManager implements WindowFocusListener {
	/** The singleton instance. */
	public static final UIManager INSTANCE = new UIManager();

	/** The UI registered. */
	private Set<UI> uiRegistered;

	/**
	 * Creates the register.
	 * @since 0.2
	 */
	private UIManager() {
		super();
	}


	/**
	 * Registers the given UI.
	 * @param ui The UI to register. Nothing is done if null.
	 * @since 0.2
	 */
	public void registerUI(final UI ui) {
		if(ui!=null) {
			if(uiRegistered==null)
				uiRegistered = new HashSet<UI>();

			if(!uiRegistered.contains(ui)) {
				uiRegistered.add(ui);
				ui.addWindowFocusListener(this);
			}
		}
	}


	/**
	 * Unregisters the given UI.
	 * @param ui The UI to unregister.
	 * @since 0.2
	 */
	public void unregisterUI(final UI ui) {
		if(ui!=null && uiRegistered!=null) {
			uiRegistered.remove(ui);
			ui.removeWindowFocusListener(this);
		}
	}


	@Override
	public void windowGainedFocus(final WindowEvent e) {
		// Nothing to do.
	}


	@Override
	public void windowLostFocus(final WindowEvent e) {
		// When a registered UI loses its focus, its events are cleared.
		if(e!=null && e.getWindow() instanceof UI)
			clearUIEvents((UI)e.getWindow());
	}


	/**
	 * Clears the events in process of the instruments of the given UI.
	 * @param ui The UI to clear.
	 * @since 0.2
	 */
	private void clearUIEvents(final UI ui) {
		Interaction interaction;

		for(final Instrument instrument : ui.getInstruments())
			for(final Link<?,?,?> link : instrument.getLinks()) {
				interaction = link.getInteraction();
				interaction.reinit();
				interaction.clearEventsStillInProcess();
			}
	}
}
