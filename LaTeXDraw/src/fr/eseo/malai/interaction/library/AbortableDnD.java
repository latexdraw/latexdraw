package fr.eseo.malai.interaction.library;

import java.awt.event.KeyEvent;
import java.util.List;

import fr.eseo.malai.interaction.AbortingState;
import fr.eseo.malai.interaction.KeyPressureTransition;
import fr.eseo.malai.interaction.ReleaseTransition;
import fr.eseo.malai.stateMachine.ITransition;

/**
 * This interaction defines a drag-and-drop which can be aborted.
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
 * @since 0.2
 */
public class AbortableDnD extends DnD {
	/**
	 * Creates the interaction.
	 */
	public AbortableDnD() {
		super();
	}
	
	
	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		super.initStateMachine();
		
		AbortingState aborted = new AbortingState("aborted"); //$NON-NLS-1$
		addState(aborted);
		
		new KeyPressureTransition(pressed, aborted) {
			@Override
			public boolean isGuardRespected() {
				return key==KeyEvent.VK_ESCAPE;
			}
		};
		
		new KeyPressureTransition(dragged, aborted) {
			@Override
			public boolean isGuardRespected() {
				return key==KeyEvent.VK_ESCAPE;
			}
		};
		
		List<ITransition> ts = pressed.getTransitions();
		boolean ok = false;
		int i=0, size = ts.size();
		ITransition t;
		
		while(!ok && i<size) {
			t = ts.get(i);
			
			if(t instanceof ReleaseTransition && t.getOutputState()==released) {
				ok = true;
				ts.remove(t);
			}
			else i++;
		}
		
		new ReleaseTransition(pressed, aborted);
	}
}
