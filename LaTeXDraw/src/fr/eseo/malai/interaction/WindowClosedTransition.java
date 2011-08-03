package fr.eseo.malai.interaction;

import fr.eseo.malai.stateMachine.SourceableState;
import fr.eseo.malai.stateMachine.TargetableState;
import fr.eseo.malai.widget.MFrame;

/**
 * This transition corresponds to a press on the decorative close button of a frame.<br>
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
public class WindowClosedTransition extends Transition {
	/** The frame closed. */
	protected MFrame frame;

	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public WindowClosedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	/**
	 * @return The frame closed.
	 * @since 0.2
	 */
	public MFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame The frame closed. Must not be null.
	 * @since 0.2
	 */
	public void setFrame(final MFrame frame) {
		if(frame!=null)
			this.frame = frame;
	}
}
