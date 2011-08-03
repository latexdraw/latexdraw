package fr.eseo.malai.interaction.library;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.malai.interaction.AbortingState;
import fr.eseo.malai.interaction.IntermediaryState;
import fr.eseo.malai.interaction.KeyPressureTransition;
import fr.eseo.malai.interaction.KeyReleaseTransition;
import fr.eseo.malai.interaction.TerminalState;

/**
 * Defines an interaction based on mouse scrolling and keyboard.<br>
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
 * 05/13/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class KeysScrolling extends Scrolling {
	/** The keys pressed while scrolling. */
	protected List<Integer> keys;


	/**
	 * Creates the interaction.
	 */
	public KeysScrolling() {
		super();
	}


	@Override
	public void reinit() {
		super.reinit();

		if(keys==null)
			keys = new ArrayList<Integer>();
		else
			keys.clear();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState keyPressed 	= new IntermediaryState("keyPressed"); //$NON-NLS-1$
		final AbortingState keyReleased		= new AbortingState("keyReleased"); //$NON-NLS-1$
		final TerminalState scrolled		= new TerminalState("scrolled"); //$NON-NLS-1$

		addState(keyPressed);
		addState(keyReleased);
		addState(scrolled);

		new ScrollingScrollTransition(initState, scrolled);

		new KeyPressureTransition(initState, keyPressed) {
			@Override
			public void action() {
				KeysScrolling.this.keys.add(getKey());
			}
		};

		new KeyPressureTransition(keyPressed, keyPressed) {
			@Override
			public void action() {
				KeysScrolling.this.keys.add(getKey());
			}
		};

		new KeyReleaseTransition(keyPressed, keyReleased) {
			@Override
			public boolean isGuardRespected() {
				return super.isGuardRespected() && KeysScrolling.this.keys.size()==1;
			}
		};

		new KeyReleaseTransition(keyPressed, keyPressed) {
			@Override
			public boolean isGuardRespected() {
				return super.isGuardRespected() && KeysScrolling.this.keys.size()>1;
			}

			@Override
			public void action() {
				KeysScrolling.this.keys.remove((Integer)getKey());
			}
		};

		new ScrollingScrollTransition(keyPressed, scrolled);
	}


	/**
	 * @return The keys pressed while scrolling.
	 * @since 0.2
	 */
	public List<Integer> getKeys() {
		return keys;
	}
}
