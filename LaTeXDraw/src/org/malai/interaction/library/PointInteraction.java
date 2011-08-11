package org.malai.interaction.library;

import java.awt.Point;

import org.malai.interaction.Interaction;
import org.malai.interaction.PressureTransition;
import org.malai.picking.Pickable;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;


/**
 * This abstract interaction defines an interaction used by pointing devices.
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
 * 04/13/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class PointInteraction extends Interaction {
	/** The pressed position. */
	protected Point point;

	/** The button used for the pressure. */
	protected int button;

	/** The object picked at the pressed position. */
	protected Pickable target;


	/**
	 * Creates the interaction.
	 */
	public PointInteraction() {
		super();
	}


	@Override
	public void reinit() {
		super.reinit();

		point	 	= null;
		button		= -1;
		target		= null;
	}


	/**
	 * @return The pressed position.
	 * @since 0.1
	 */
	public Point getPoint() {
		return point;
	}


	/**
	 * @return The button used for the pressure.
	 * @since 0.1
	 */
	public int getButton() {
		return button;
	}


	/**
	 * @return The object picked at the pressed position.
	 * @since 0.1
	 */
	public Pickable getTarget() {
		return target;
	}


	/**
	 * A press transition.
	 */
	class PointPressureTransition extends PressureTransition {
		/**
		 * The constructor.
		 */
		public PointPressureTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			PointInteraction.this.point	= new Point(this.x, this.y);
			PointInteraction.this.button= this.button;
			PointInteraction.this.target= Interaction.getPickableAt(this.x, this.y, this.source);
			PointInteraction.this.setLastHIDUsed(this.hid);
		}
	}
}
