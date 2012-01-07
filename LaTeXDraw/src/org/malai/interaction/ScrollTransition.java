package org.malai.interaction;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;


/**
 * This transition corresponds to a scroll event.<br>
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
 * 28/10/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class ScrollTransition extends PointingDeviceTransition {
	/** Defines if the scrolling is up (positive value) or down (negative value). */
	protected int direction;
	
	/** The number of units to scroll by scroll. */
	protected int amount;
	
	/** The type of scrolling that should take place in response to this event (block or unit increment). */
	protected int type;
	
	
	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public ScrollTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	

	/**
	 * @return The direction of the scrolling.
	 * @since 0.2
	 */
	public int getDirection() {
		return direction;
	}


	
	/**
	 * @param direction Defines if the scrolling is up (positive value) or down (negative value).
	 * @since 0.2
	 */
	public void setDirection(final int direction) {
		this.direction = direction;
	}
	


	/**
	 * @return The number of units.
	 * @since 0.2
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * @param amount The number of units to scroll by scroll.
	 * @since 0.2
	 */
	public void setAmount(final int amount) {
		this.amount = amount;
	}


	/**
	 * @return the type of scrolling.
	 * @since 0.2
	 */
	public int getType() {
		return type;
	}


	/**
	 * @param type The type of scrolling that should take place in response to this event (block or unit increment).
	 * @since 0.2
	 */
	public void setType(final int type) {
		this.type = type;
	}

}
