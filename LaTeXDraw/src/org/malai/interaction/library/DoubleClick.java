package org.malai.interaction.library;

import org.malai.interaction.AbortingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.MoveTransition;
import org.malai.interaction.PressureTransition;
import org.malai.interaction.ReleaseTransition;
import org.malai.interaction.TerminalState;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This interaction defines a double click. Between the two clicks the mouse must not
 * moved. A delay of 1 second at max is permitted between the two clicks.
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
 * 08/18/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class DoubleClick extends PointInteraction {
	/**
	 * Creates the interaction.
	 * @since 0.2
	 */
	public DoubleClick() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed1 = new IntermediaryState("pressed1");
		final AbortingState aborted = new AbortingState("aborted");
		final IntermediaryState released1 = new IntermediaryState("released1");
		final IntermediaryState pressed2 = new IntermediaryState("pressed2");
		final TerminalState released2 = new TerminalState("released2");

		addState(pressed1);
		addState(aborted);
		addState(released1);
		addState(pressed2);
		addState(released2);

		new PointPressureTransition(initState, pressed1);
		new EscapeKeyPressureTransition(pressed1, aborted);
		new MoveTransition(pressed1, aborted);
		new ReleaseTransition4DoubleClick(pressed1, released1);
		new MoveTransition(released1, aborted);
		new PressureTransition(released1, pressed2) {
			@Override
			public boolean isGuardRespected() {
				return this.hid==DoubleClick.this.getLastHIDUsed() && this.button==DoubleClick.this.button;
			}
		};
		new EscapeKeyPressureTransition(pressed2, aborted);
		new MoveTransition(pressed2, aborted);
		new ReleaseTransition4DoubleClick(pressed2, released2);
	}


	class ReleaseTransition4DoubleClick extends ReleaseTransition {
		protected ReleaseTransition4DoubleClick(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public boolean isGuardRespected() {
			return this.hid==DoubleClick.this.getLastHIDUsed() && this.button==DoubleClick.this.button;
		}
	}
}




