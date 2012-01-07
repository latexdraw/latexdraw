package org.malai.interaction;

import javax.swing.JTabbedPane;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to a change in the selection of a tabbed pane.<br>
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
public class TabSelectedTransition extends Transition {
	/** The tabbed pane that produced the event. */
	protected JTabbedPane tabbedPane;


	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public TabSelectedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The tabbed pane that produced the event.
	 * @since 0.2
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}


	/**
	 * @param tabbedPane The tabbed pane that produced the event.
	 * @since 0.2
	 */
	public void setTabbedPane(final JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
}
