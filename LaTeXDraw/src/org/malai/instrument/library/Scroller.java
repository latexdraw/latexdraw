package org.malai.instrument.library;

import org.malai.action.library.Scroll;
import org.malai.error.ErrorCatcher;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.KeysScrolling;
import org.malai.widget.MPanel;

/**
 * Defines a scroller that scrolls on a scrollable object.<br>
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
 * 05/11/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class Scroller extends Instrument {
	/** The panel to scroll. */
	protected MPanel panel;


	/**
	 * Initialises a scroller.
	 * @param panel The panel to scroll.
	 * @throw IllegalArgumentException If panel is null or is not scrollable.
	 * @since 0.2
	 */
	public Scroller(final MPanel panel) {
		super();

		if(panel==null || !panel.hasScrollPane())
			throw new IllegalArgumentException();

		this.panel = panel;
	}



	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Scrolling2Scroll(this));
		}catch(InstantiationException e){
			ErrorCatcher.INSTANCE.reportError(e);
		}catch(IllegalAccessException e){
			ErrorCatcher.INSTANCE.reportError(e);
		}
	}
}


/**
 * ScrollInteraction -> ScrollAction
 */
class Scrolling2Scroll extends Link<Scroll, KeysScrolling, Scroller> {
	/**
	 * Creates a link ScrollInteraction -> ScrollAction
	 * @param scroller The instrument.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @since 0.2
	 */
	public Scrolling2Scroll(final Scroller scroller) throws InstantiationException, IllegalAccessException {
		super(scroller, false, Scroll.class, KeysScrolling.class);
	}

	@Override
	public void initAction() {
		Scroll scroll = getAction();

		scroll.setPanel(getInstrument().panel);
		scroll.setIncrement(getInteraction().getIncrement());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getKeys().isEmpty();
	}
}
