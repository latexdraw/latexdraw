package org.malai.interaction.library;

import org.malai.interaction.Interaction;
import org.malai.interaction.TerminalState;
import org.malai.interaction.WindowClosedTransition;
import org.malai.widget.MFrame;

/**
 * This interaction is performed when a window is closed by pressing the decorating close button.<br>
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
 * 05/31/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class WindowClosed extends Interaction {
	/** The frame closed. */
	protected MFrame frame;


	/**
	 * Creates the interaction.
	 */
	public WindowClosed() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();
		frame = null;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState closed = new TerminalState("closed"); //$NON-NLS-1$

		addState(closed);

		new WindowClosedTransition(initState, closed) {
			@Override
			public void action() {
				super.action();
				WindowClosed.this.frame = this.frame;
			}
		};
	}


	/**
	 * @return the frame closed.
	 * @since 0.2
	 */
	public MFrame getFrame() {
		return frame;
	}
}

