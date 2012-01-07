package org.malai.interaction.library;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.malai.interaction.AbortingState;
import org.malai.interaction.Interaction;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.MoveTransition;
import org.malai.interaction.PressureTransition;
import org.malai.interaction.ReleaseTransition;
import org.malai.interaction.TerminalState;

/**
 * This interaction allows to performed several clicks using button 1. If a click
 * occurs with another button, the interaction ends. THe interaction is aborted is key 'escape'
 * is pressed.
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
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class MultiClick extends Interaction {
	/** The list of pressed position. */
	protected List<Point> points;

	/** The current position of the pointing device. */
	protected Point currentPosition;

	/** The minimum number of points that the interaction must gather. */
	protected int minPoints;


	/**
	 * Creates the interaction.
	 */
	public MultiClick() {
		super();
		minPoints = 2;
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		if(points==null)
			points = new ArrayList<Point>();
		else
			points.clear();

		currentPosition = null;
	}


	/**
	 * @return True if the last point gathered by the interaction is a point created by the right
	 * click that ends the interaction. This method is useful to make the difference between
	 * points created using left clicks and the last one created using a right click.
	 * @since 0.2
	 */
	public boolean isLastPointFinalPoint() {
		return currentState instanceof TerminalState;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		IntermediaryState pressed 	= new IntermediaryState("pressed"); //$NON-NLS-1$
		IntermediaryState released 	= new IntermediaryState("released"); //$NON-NLS-1$
		TerminalState ended 		= new TerminalState("ended"); //$NON-NLS-1$
		AbortingState aborted 		= new AbortingState("aborted"); //$NON-NLS-1$

		addState(pressed);
		addState(released);
		addState(ended);
		addState(aborted);

		new PressureTransition(initState, pressed) {
			@Override
			public void action() {
				MultiClick.this.setLastHIDUsed(this.hid);
				MultiClick.this.points.add(new Point(this.x, this.y));
				currentPosition = new Point(this.x, this.y);
			}

			@Override
			public boolean isGuardRespected() {
				return button==MouseEvent.BUTTON1;
			}
		};

		new ReleaseTransition(pressed, released) {
			@Override
			public boolean isGuardRespected() {
				return button==MouseEvent.BUTTON1 && this.hid==MultiClick.this.getLastHIDUsed();
			}
		};

		new PressureTransition(released, pressed) {
			@Override
			public void action() {
				MultiClick.this.points.add(new Point(this.x, this.y));
				currentPosition = new Point(this.x, this.y);
			}

			@Override
			public boolean isGuardRespected() {
				return button==MouseEvent.BUTTON1 && this.hid==MultiClick.this.getLastHIDUsed();
			}
		};

		new EscapeKeyPressureTransition(pressed, aborted);

		new MoveTransition(released, released) {
			@Override
			public void action() {
				currentPosition.setLocation(this.x, this.y);
			}

			@Override
			public boolean isGuardRespected() {
				return this.hid==MultiClick.this.getLastHIDUsed();
			}
		};

		new EscapeKeyPressureTransition(released, aborted);

		new PressureTransition(released, ended) {
			@Override
			public void action() {
				MultiClick.this.points.add(new Point(this.x, this.y));
			}

			@Override
			public boolean isGuardRespected() {
				return button!=MouseEvent.BUTTON1 && this.hid==MultiClick.this.getLastHIDUsed() && (MultiClick.this.points.size()+1)>=MultiClick.this.minPoints;
			}
		};

		new PressureTransition(released, aborted) {
			@Override
			public boolean isGuardRespected() {
				return button!=MouseEvent.BUTTON1 && this.hid==MultiClick.this.getLastHIDUsed() && MultiClick.this.points.size()<MultiClick.this.minPoints;
			}
		};
	}


	/**
	 * @return The list of pressed position.
	 * @since 0.2
	 */
	public List<Point> getPoints() {
		return points;
	}


	/**
	 * @return The current position of the pointing device.
	 * @since 0.2
	 */
	public Point getCurrentPosition() {
		return currentPosition;
	}


	/**
	 * @return The minimum number of points that the interaction must gather.
	 * @since 0.2
	 */
	public int getMinPoints() {
		return minPoints;
	}


	/**
	 * @param minPoints The minimum number of points that the interaction must gather. Must be greater than 0.
	 * @since 0.2
	 */
	public void setMinPoints(final int minPoints) {
		if(minPoints>0)
			this.minPoints = minPoints;
	}
}
