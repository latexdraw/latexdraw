package fr.eseo.malai.interaction.library;

import fr.eseo.malai.interaction.Interaction;
import fr.eseo.malai.interaction.ScrollTransition;
import fr.eseo.malai.interaction.TerminalState;
import fr.eseo.malai.picking.Pickable;
import fr.eseo.malai.stateMachine.SourceableState;
import fr.eseo.malai.stateMachine.TargetableState;

/**
 * Defines an interaction based on mouse scrolling.<br>
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
 * 10/28/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class Scrolling extends Interaction {
	/** The object on which the scroll is performed. */
	protected Pickable scrollTarget;

	/** The X-coordinate of the scroll position. */
 	protected double px;

	/** The Y-coordinate of the scroll position. */
 	protected double py;

 	/** The number of scroll up (i.e. moving the scroll from the user to the screen) that has been performed. */
 	protected int numberUp;

 	/** The total increment of the scrolling. */
 	protected int increment;


	/**
	 * Creates the interaction.
	 */
	public Scrolling() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		scrollTarget = null;
		px = 0.;
		py = 0.;
		numberUp  = 0;
		increment = 0;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		TerminalState wheeled = new TerminalState("scrolled"); //$NON-NLS-1$

		addState(wheeled);

		new ScrollingScrollTransition(initState, wheeled);
	}



	/**
	 * @return The object on which the scroll is performed.
	 * @since 0.2
	 */
	public Pickable getScrollTarget() {
		return scrollTarget;
	}


	/**
	 * @return The X-coordinate of the scroll position.
	 * @since 0.2
	 */
	public double getPx() {
		return px;
	}


	/**
	 * @return The Y-coordinate of the scroll position.
	 * @since 0.2
	 */
	public double getPy() {
		return py;
	}


	/**
	 * @return The number of scroll up (i.e. moving the scroll from the user to the screen) that has been performed.
	 * @since 0.2
	 */
	public int getNumberUp() {
		return numberUp;
	}


	/**
	 * @return The total increment of the scrolling.
	 * @since 0.2
	 */
	public int getIncrement() {
		return increment;
	}


	/**
	 * This scroll transition modifies the scrolling interaction.
	 */
	class ScrollingScrollTransition extends ScrollTransition {
		protected ScrollingScrollTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			Scrolling.this.setLastHIDUsed(this.hid);
			Scrolling.this.numberUp  	= Scrolling.this.numberUp + (this.direction>0 ? -1 : 1);
			Scrolling.this.increment  	= Scrolling.this.increment + (this.direction>0 ? -this.amount : this.amount);
			Scrolling.this.px			= this.x;
			Scrolling.this.py        	= this.y;
			Scrolling.this.scrollTarget = Interaction.getPickableAt(this.x, this.y, this.source);
		}
	}
}
