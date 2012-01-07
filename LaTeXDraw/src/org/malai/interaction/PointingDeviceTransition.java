package org.malai.interaction;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;


/**
 * This abstract transition defines a model for transitions based on pointing device events.<br>
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
 * 06/01/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public abstract class PointingDeviceTransition extends Transition {
	/** The X-coordinate of the pointing device. */
	protected int x;

	/** The Y-coordinate of the pointing device. */
	protected int y;

	/** The button used. */
	protected int button;

	/** The object picked at the position (x, y). */
	protected Object source;


	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public PointingDeviceTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);

		button = -1;
		source = null;
	}


	/**
	 * @return The button used.
	 * @since 0.1
	 */
	public int getButton() {
		return button;
	}


	/**
	 * @return The object picked at the position (x, y).
	 * @since 0.1
	 */
	public Object getSource() {
		return source;
	}


	/**
	 * Sets the object picked at the position (x, y).
	 * @param source The object picked at the position (x, y).
	 * @since 0.1
	 */
	public void setSource(final Object source) {
		this.source = source;
	}


	/**
	 * Sets the button of the pointing device used by the event.
	 * @param button The button used.
	 * @since 0.1
	 */
	public void setButton(final int button) {
		this.button = button;
	}


	/**
	 * @return The X-coordinate of the pointing device.
	 * @since 0.1
	 */
	public int getX() {
		return x;
	}


	/**
	 * Sets the X-coordinate of the pointing device.
	 * @param x The X-coordinate of the pointing device.
	 * @since 0.1
	 */
	public void setX(final int x) {
		this.x = x;
	}


	/**
	 * @return The Y-coordinate of the pointing device.
	 * @since 0.1
	 */
	public int getY() {
		return y;
	}


	/**
	 * Sets the Y-coordinate of the pointing device.
	 * @param y The Y-coordinate of the pointing device.
	 * @since 0.1
	 */
	public void setY(final int y) {
		this.y = y;
	}
}
