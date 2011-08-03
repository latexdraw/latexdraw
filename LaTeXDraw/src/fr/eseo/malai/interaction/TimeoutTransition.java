package fr.eseo.malai.interaction;

import fr.eseo.malai.stateMachine.IStateMachine;
import fr.eseo.malai.stateMachine.SourceableState;
import fr.eseo.malai.stateMachine.TargetableState;

/**
 * This transition defines a timeout: when the user does nothing during a given duration,
 * the timeout transition is executed.<br>
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
 * 11/01/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class TimeoutTransition extends Transition {
	/** The timeout in ms. */
	protected int timeout;

	/** The current thread in progress. */
	private Thread timeoutThread;


	/**
	 * Creates the transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The target state of the transition.
	 * @param timeout The timeout in ms. Must be greater than 0.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 * @since 0.2
	 */
	public TimeoutTransition(final SourceableState inputState, final TargetableState outputState, final int timeout) {
		super(inputState, outputState);

		if(timeout<=0)
			throw new IllegalArgumentException();

		this.timeout = timeout;
	}


	/**
	 * Launches the chronometre (and its thread).
	 * @since 0.2
	 */
	public void startTimeout() {
		if(timeoutThread==null) {
			timeoutThread = new Thread(new TimeoutRunnable());
			timeoutThread.start();
		}
	}


	/**
	 * Stops the chronometre (and its thread).
	 * @since 0.2
	 */
	public void stopTimeout() {
		if(timeoutThread!=null) {
			timeoutThread.interrupt();
			timeoutThread = null;
		}
	}



	/**
	 * @return The timeout in ms.
	 * @since 0.2
	 */
	public int getTimeout() {
		return timeout;
	}


	/**
	 * A TimeoutRunnable defines the executable part of the thread which is
	 * thrown when the chronometre of the transition is launched.
	 */
	class TimeoutRunnable implements Runnable {
		@Override
		public void run() {
			try{
				// Sleeping the thread.
				Thread.sleep(TimeoutTransition.this.timeout);

				// There is a timeout and the interaction must be notified of that.
				final IStateMachine sm = TimeoutTransition.this.getInputState().getStateMachine();
				// Notifying the interaction of the timeout.
				if(sm instanceof Interaction)
					((Interaction)sm).onTimeout(TimeoutTransition.this);
			}catch(InterruptedException ex){
				// OK, thread stopped.
			}
		}
	}
}
