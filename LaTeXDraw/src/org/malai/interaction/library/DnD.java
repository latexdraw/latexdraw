package org.malai.interaction.library;

import java.awt.Point;

import org.malai.interaction.Interaction;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.MoveTransition;
import org.malai.interaction.PressureTransition;
import org.malai.interaction.ReleaseTransition;
import org.malai.interaction.TerminalState;
import org.malai.picking.Pickable;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;


/**
 * A DnD interaction is a Drag-And-Drop: press-drag-release.<br>
 * When key 'escape' is pressed, the interaction is aborted.<br>
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
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class DnD extends Interaction {
	/** The starting point of the dnd. */
	protected Point startPt;

	/** The ending point of the dnd. */
	protected Point endPt;

	/** The button of the device used to performed the dnd (-1 if no button). */
	protected int button;

	/** The object picked at the beginning of the dnd. */
	protected Pickable startObject;

	/** The object picked at the end of the dnd. */
	protected Pickable endObject;

	protected IntermediaryState pressed;

	protected IntermediaryState dragged;

	protected TerminalState released;

	/**
	 * Creates the interaction.
	 */
	public DnD() {
		super();

		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		dragged = new IntermediaryState("dragged"); //$NON-NLS-1$
		released= new TerminalState("released"); //$NON-NLS-1$

		addState(pressed);
		addState(dragged);
		addState(released);

		new PressureTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();

				setLastHIDUsed(this.hid);
				DnD.this.startPt 	 = new Point(this.x, this.y);
				DnD.this.endPt	 	 = new Point(this.x, this.y);
				DnD.this.button  	 = this.button;
				DnD.this.startObject = Interaction.getPickableAt(this.x, this.y, this.source);
				DnD.this.endObject 	 = DnD.this.startObject;
			}
		};

		new Move4DnD(pressed, dragged);
		new Move4DnD(dragged, dragged);
		new Release4DnD(dragged, released);
		new Release4DnD(pressed, released);
	}


	@Override
	public void reinit() {
		super.reinit();

		startPt 	= null;
		endPt 		= null;
		button		= -1;
		startObject = null;
		endObject 	= null;
	}


	/**
	 * @return The starting point of the dnd.
	 * @since 0.1
	 */
	public Point getStartPt() {
		return startPt;
	}


	/**
	 * @return The ending point of the dnd.
	 * @since 0.1
	 */
	public Point getEndPt() {
		return endPt;
	}


	/**
	 * @return The button of the device used to performed the dnd (-1 if no button).
	 * @since 0.1
	 */
	public int getButton() {
		return button;
	}


	/**
 	 * @return The object picked at the beginning of the dnd.
	 * @since 0.1
	 */
	public Pickable getStartObject() {
		return startObject;
	}


	/**
	 * @return The object picked at the end of the dnd.
	 * @since 0.1
	 */
	public Pickable getEndObjet() {
		return endObject;
	}


	class Release4DnD extends ReleaseTransition {
		protected Release4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}
		@Override
		public boolean isGuardRespected() {
			return DnD.this.button==this.button && DnD.this.getLastHIDUsed()==this.hid;
		}
	}


	class Move4DnD extends MoveTransition {
		protected Move4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}
		@Override
		public void action() {
			super.action();
			DnD.this.endPt.setLocation(x, y);
			DnD.this.endObject = Interaction.getPickableAt(this.x, this.y, this.source);
		}
		@Override
		public boolean isGuardRespected() {
			return DnD.this.getLastHIDUsed()==this.hid;
		}
	}
}
