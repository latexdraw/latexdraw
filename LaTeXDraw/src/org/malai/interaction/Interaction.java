package org.malai.interaction;

import java.awt.ItemSelectable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.stateMachine.IState;
import org.malai.stateMachine.IStateMachine;
import org.malai.stateMachine.ITransition;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.widget.MFrame;

/**
 * Defines an interaction as defined in the Malai model.<br>
 * An interaction is a state machine and a class.<br>
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
 * 10/10/2009<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public abstract class Interaction implements IStateMachine, EventHandler {
	/** The states that compose the finite state machine. */
	protected List<IState> states;

	/** The initial state the starts the state machine. */
	protected InitState initState;

	/** The current state of the state machine when the state machine is executed. */
	protected IState currentState;

	/** Defines if the interaction is activated or not. If not, the interaction will not
	 * change on events.
	 * */
	protected boolean activated;

	/** The handlers that want to be notified when the state machine of the
	 * interaction changed.
	 */
	protected List<InteractionHandler> handlers;

	/** The events still in process. For example when the user press key ctrl and scroll one
	 * time using the wheel of the mouse, the interaction scrolling is finished but the event keyPressed
	 * 'ctrl' is still in process. At the end of the interaction, these events are re-introduced into the
	 * state machine of the interaction for processing.
	 */
	protected List<Event> stillProcessingEvents;

	/** The current timeout in progress. */
	protected TimeoutTransition currentTimeout;

	/** Defines the ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 */
	protected int lastHIDUsed;


	/**
	 * Creates the interaction with a init state.
	 */
	public Interaction() {
		this(new InitState());
	}


	/**
	 * Creates the state machine.
	 * @param initState The initial state of the state machine.
	 * @throws IllegalArgumentException If the given state is null.
	 * @since 0.1
	 */
	public Interaction(final InitState initState) {
		super();

		if(initState==null)
			throw new IllegalArgumentException();

		currentTimeout			= null;
		activated 				= true;
		states 					= new ArrayList<IState>();
		initState.stateMachine 	= this;
		this.initState 			= initState;
		addState(initState);
		reinit();
	}


	/**
	 * Initialises the interaction: creates the states and the transitions.
	 * @since 0.1
	 */
	protected abstract void initStateMachine();


	@Override
	public void setActivated(final boolean activated) {
		this.activated = activated;

		if(!activated) {
			reinit();
			clearEventsStillInProcess();
		}
	}


	@Override
	public void reinit() {
		if(currentTimeout!=null)
			currentTimeout.stopTimeout();

		currentTimeout	= null;
		currentState 	= initState;
		lastHIDUsed		= -1;
	}


	/**
	 * @return The handlers that listens to the interaction.
	 */
	public List<InteractionHandler> getHandlers() {
		return handlers;
	}


	/**
	 * Adds an interaction handler.
	 * @param handler The handler to add.
	 * @since 0.1
	 */
	public void addHandler(final InteractionHandler handler) {
		if(handler!=null) {
			if(handlers==null)
				handlers = new ArrayList<InteractionHandler>();

			handlers.add(handler);
		}
	}



	/**
	 * Notifies handlers that the interaction starts.
	 * @since 0.1
	 */
	protected void notifyHandlersOnStart() throws MustAbortStateMachineException {
		try {
			if(handlers!=null)
	    		for(final InteractionHandler handler : handlers)
	    			handler.interactionStarts(this);
		}catch(final MustAbortStateMachineException ex) {
			notifyHandlersOnAborting();
			throw ex;
		}
	}


	/**
	 * Notifies handlers that the interaction updates.
	 * @since 0.1
	 */
	protected void notifyHandlersOnUpdate() throws MustAbortStateMachineException {
		try {
			if(handlers!=null)
	    		for(final InteractionHandler handler : handlers)
	    			handler.interactionUpdates(this);
		}catch(final MustAbortStateMachineException ex) {
			notifyHandlersOnAborting();
			throw ex;
		}
	}



	/**
	 * Notifies handlers that the interaction stops.
	 * @since 0.1
	 */
	protected void notifyHandlersOnStop() throws MustAbortStateMachineException {
		try {
			if(handlers!=null)
	    		for(InteractionHandler handler : handlers)
	    			handler.interactionStops(this);
		}catch(MustAbortStateMachineException ex) {
			notifyHandlersOnAborting();
			throw ex;
		}
	}



	/**
	 * Notifies handlers that the interaction stops.
	 * @since 0.1
	 */
	protected void notifyHandlersOnAborting() {
		if(handlers!=null)
			for(final InteractionHandler handler : handlers)
				handler.interactionAborts(this);
	}


	/**
	 * Try to find a Pickable object at the given coordinate in the given source object.
	 * @param x The X-coordinate of the location to check.
	 * @param y The Y-coordinate of the location to check.
	 * @param source The source object in which the function will search.
	 * @return null if nothing is found. Otherwise a pickable object.
	 * @since 0.2
	 */
	public static Pickable getPickableAt(final double x, final double y, final Object source) {
		if(source==null)
			return null;

		if(source instanceof Picker)
			return ((Picker)source).getPickableAt(x, y);

		if(source instanceof Pickable) {
			Pickable srcPickable = (Pickable) source;

			if(srcPickable.contains(x, y))
				return srcPickable;

			return srcPickable.getPicker().getPickableAt(x, y);
		}

		return null;
	}


	@Override
	public void addState(final IState state) {
		if(state!=null) {
			states.add(state);
			state.setStateMachine(this);
		}
	}


	/**
	 * Links the interaction to an eventable object (e.g. a MPanel or a MButton).
	 * @param eventable The Eventable object.
	 * @since 0.2
	 */
	public void linkToEventable(final Eventable eventable) {
		if(eventable!=null && eventable.hasEventManager())
			eventable.getEventManager().addHandlers(this);
	}


	@Override
	public boolean isRunning() {
		return activated && currentState!=initState;
	}


	/**
	 * Executes the given transition. Only if the state machine is activated.
	 * @param transition The transition to execute.
	 * @since 0.1
	 */
	private void executeTransition(final ITransition transition) {
		if(activated)
    		try {
    			if(transition!=null) {
    				transition.action();
    				transition.getInputState().onOutgoing();
    				currentState = transition.getOutputState();
    				transition.getOutputState().onIngoing();
    			}
    		}catch(MustAbortStateMachineException ex) { reinit(); }
	}


	/**
	 * Stops the current timeout transition.
	 * @since 0.2
	 */
	protected void stopCurrentTimeout() {
		if(currentTimeout!=null) {
			currentTimeout.stopTimeout();
			currentTimeout = null;
		}
	}


	/**
	 * Checks if the transition can be executed and executes it if possible.
	 * @param transition The transition to check.
	 * @return True: the transition has been executed.
	 * @since 0.2
	 */
	private boolean checkTransition(final ITransition transition) {
		final boolean ok;

		if(transition.isGuardRespected()) {
			stopCurrentTimeout();
			executeTransition(transition);
			ok = true;
		}
		else ok = false;

		return ok;
	}


	@Override
	public void onTextChanged(final JTextComponent textComp) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof TextChangedTransition) {
				TextChangedTransition tct = (TextChangedTransition) t;

				tct.setTextComp(textComp);
				tct.setText(textComp.getText());
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onScroll(final int posX, final int posY, final int direction, final int amount,
							final int type, final int idHID, final Object src) {
		if(!activated) return ;

		boolean again = true;
		ITransition transition;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof ScrollTransition) {
				ScrollTransition st = (ScrollTransition)transition;

				st.setAmount(amount);
				st.setDirection(direction);
				st.setSource(src);
				st.setType(type);
				st.setX(posX);
				st.setY(posY);
				st.setHid(idHID);
				again = !checkTransition(transition);
			}
		}
	}



	@Override
	public void onButtonPressed(final AbstractButton button) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof ButtonPressedTransition) {
				((ButtonPressedTransition)t).setButton(button);
				again = !checkTransition(t);
			}
		}
	}



	@Override
	public void onItemSelected(final ItemSelectable itemSelectable) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof ListTransition) {
				((ListTransition)t).setList(itemSelectable);
				again = !checkTransition(t);
			}
		}
	}



	@Override
	public void onSpinnerChanged(final JSpinner spinner) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof SpinnerTransition) {
				((SpinnerTransition)t).setSpinner(spinner);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onCheckBoxModified(final JCheckBox checkbox) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof CheckBoxTransition) {
				((CheckBoxTransition)t).setCheckBox(checkbox);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onMenuItemPressed(final JMenuItem menuItem) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof MenuItemTransition) {
				((MenuItemTransition)t).setMenuItem(menuItem);
				again = !checkTransition(t);
			}
		}
	}



	@Override
	public void onKeyPressure(final int key, final int idHID, final Object object) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof KeyPressureTransition) {
				final KeyPressureTransition kpt = (KeyPressureTransition)t;
				kpt.setKey(key);
				kpt.setSource(object);
				kpt.setHid(idHID);

				again = !checkTransition(t);

				if(!again)
					// Adding an event 'still in process'
					addEvent(new KeyPressEvent(idHID, key, object));
			}
		}
	}



	@Override
	public void onKeyRelease(final int key, final int idHID, final Object object) {
		boolean again = true;

		if(activated) {
			ITransition t;

			for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
				t = currentState.getTransition(i);

				if(t instanceof KeyReleaseTransition) {
					final KeyReleaseTransition krt = (KeyReleaseTransition)t;
					krt.setKey(key);
					krt.setHid(idHID);
					krt.setSource(object);

					if(t.isGuardRespected()) {
						// Removing from the 'still in process' list
						removeKeyEvent(idHID, key);
						again = !checkTransition(t);
					}
				}
			}
		}

		// Removing from the 'still in process' list
		if(again)
			removeKeyEvent(idHID, key);
	}


	@Override
	public void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof MoveTransition) {
				MoveTransition mt = (MoveTransition)t;

				mt.setX(x);
				mt.setY(y);
				mt.setButton(button);
				mt.setSource(source);
				mt.setPressed(pressed);
				mt.setHid(idHID);
				again = !checkTransition(t);
			}
		}
	}


	@Override
	public void onPressure(final int button, final int x, final int y, final int idHID, final Object source) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof PressureTransition) {
				PressureTransition pt =  (PressureTransition)t;

				pt.setX(x);
				pt.setY(y);
				pt.setButton(button);
				pt.setSource(source);
				pt.setHid(idHID);
				again = !checkTransition(t);

				if(!again)
					// Adding an event 'still in process'
					addEvent(new MousePressEvent(idHID, x, y, button, source));
			}
		}
	}


	@Override
	public void onRelease(final int button, final int x, final int y, final int idHID, final Object source) {
		boolean again = true;

		if(activated) {
			ITransition t;

			for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
				t = currentState.getTransition(i);

				if(t instanceof ReleaseTransition) {
					ReleaseTransition rt = (ReleaseTransition)t;

					rt.setX(x);
					rt.setY(y);
					rt.setButton(button);
					rt.setSource(source);
					t.setHid(idHID);
					if(t.isGuardRespected()) {
						// Removing from the 'still in process' list
						removePressEvent(idHID);
						again = !checkTransition(t);
					}
				}
			}
		}

		// Removing from the 'still in process' list
		if(again)
			removePressEvent(idHID);
	}


	@Override
	public void onWindowClosed(final MFrame frame) {
		if(!activated) return ;

		ITransition transition;
		boolean again = true;

		for(int i=0, j=currentState.getTransitions().size(); again && i<j; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof WindowClosedTransition) {
				((WindowClosedTransition)transition).setFrame(frame);

				if(transition.isGuardRespected())
					again = !checkTransition(transition);
			}
		}
	}


	@Override
	public void onTabChanged(final JTabbedPane tabbedPanel) {
		if(!activated) return ;

		ITransition transition;
		boolean again = true;

		for(int i=0, j=currentState.getTransitions().size(); again && i<j; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof TabSelectedTransition) {
				((TabSelectedTransition)transition).setTabbedPane(tabbedPanel);

				if(transition.isGuardRespected())
					again = !checkTransition(transition);
			}
		}
	}


	@Override
	public void onTimeout(final TimeoutTransition timeoutTransition) {
		if(!activated || timeoutTransition==null) return ;
		executeTransition(timeoutTransition);
	}


	/**
	 * Adds the given event to the events 'still in process' list.
	 * @param event The event to add.
	 * @since 0.2
	 */
	protected void addEvent(final Event event) {
		if(stillProcessingEvents==null)
			stillProcessingEvents = new ArrayList<Event>();

		stillProcessingEvents.add(event);
	}



	/**
	 * Removes the given KeyPress event from the events 'still in process' list.
	 * @param idHID The identifier of the HID which produced the event.
	 * @param key The key code of the event to remove.
	 * @since 0.2
	 */
	protected void removeKeyEvent(final int idHID, final int key) {
		if(stillProcessingEvents==null) return ;

		boolean removed = false;
		Event event;

		for(int i=0, size=stillProcessingEvents.size(); i<size && !removed; i++) {
			event = stillProcessingEvents.get(i);

			if(event instanceof KeyPressEvent && event.idHID==idHID && ((KeyPressEvent)event).keyCode==key) {
				removed = true;
				stillProcessingEvents.remove(i);
			}
		}
	}


	/**
	 * Removes the given Press event from the events 'still in process' list.
	 * @param idHID The identifier of the HID which produced the event.
	 * @since 0.2
	 */
	protected void removePressEvent(final int idHID) {
		if(stillProcessingEvents==null) return ;

		boolean removed = false;
		Event event;

		for(int i=0, size=stillProcessingEvents.size(); i<size && !removed; i++) {
			event = stillProcessingEvents.get(i);

			if(event instanceof MousePressEvent && event.idHID==idHID) {
				removed = true;
				stillProcessingEvents.remove(i);
			}
		}
	}


	/**
	 * At the end of the interaction, the events still in process must be recycled
	 * to be reused in the interaction. For instance will the KeysScrolling interaction,
	 * if key 'ctrl' is pressed and the user scrolls the key event 'ctrl' is re-introduced
	 * into the state machine of the interaction to be processed.
	 * @since 0.2
	 */
	protected void processEvents() {
		if(stillProcessingEvents!=null) {
			Event event;
			// All the events must be processed but the list stillProcessingEvents can be modified
			// during the process. So, a clone of the list must be created.
			List<Event> list = new ArrayList<Event>(stillProcessingEvents);

			// All the events must be processed.
			while(!list.isEmpty()) {
				event = list.remove(0);
				// Do not forget to remove the event from its original list.
				stillProcessingEvents.remove(0);

				if(event instanceof MousePressEvent) {
					MousePressEvent press = (MousePressEvent)event;
					onPressure(press.button, press.x, press.y, press.idHID, press.source);
				} else if(event instanceof KeyPressEvent) {
					KeyPressEvent key = (KeyPressEvent)event;
					onKeyPressure(key.keyCode, key.idHID, key.source);
				}
			}
		}
	}


	@Override
	public void onTerminating() throws MustAbortStateMachineException {
		notifyHandlersOnStop();
		reinit();
		processEvents();
	}


	@Override
	public void onAborting() {
		notifyHandlersOnAborting();
		reinit();
		// When an interaction is aborted, the events in progress must not be reused.
		clearEventsStillInProcess();
	}


	@Override
	public void onStarting() throws MustAbortStateMachineException {
		notifyHandlersOnStart();
		checkTimeoutTransition();
	}


	@Override
	public void onUpdating() throws MustAbortStateMachineException {
		notifyHandlersOnUpdate();
		checkTimeoutTransition();
	}


	/**
	 * Checks if the current state has a timeout transition. If it is the case,
	 * the timeout transition is launched.
	 * @since 0.2
	 */
	protected void checkTimeoutTransition() {
		boolean again = true;
		ITransition transition;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			transition = currentState.getTransition(i);

			if(transition instanceof TimeoutTransition) {
				currentTimeout = (TimeoutTransition)transition;
				again = false;
				currentTimeout.startTimeout();
			}
		}
	}


	/**
	 * @return The ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 * @since 0.2
	 */
	public int getLastHIDUsed() {
		return lastHIDUsed;
	}

	/**
	 * @param hid The ID of last HID that has been used by the interaction. If the interaction has stopped or is
	 * aborted, the value of the attribute is -1.
	 * @since 0.2
	 */
	public void setLastHIDUsed(final int hid) {
		lastHIDUsed = hid;
	}


	/**
	 * Clears the events of the interaction still in process.
	 * @since 0.2
	 */
	public void clearEventsStillInProcess() {
		if(stillProcessingEvents!=null)
			stillProcessingEvents.clear();
	}
}


/**
 * This class defines an event corresponding to the pressure of a key.
 */
class KeyPressEvent extends Event {
	/** The code of the key pressed. */
	protected int keyCode;

	/** The object that produced the key event. */
	protected Object source;


	/**
	 * Creates the event.
	 * @param idHID The identifier of the HID.
	 * @param keyCode The key code.
	 * @param source The object that produced the event.
	 * @since 0.2
	 */
	public KeyPressEvent(final int idHID, final int keyCode, final Object source) {
		super(idHID);
		this.keyCode = keyCode;
		this.source  = source;
	}
}


/**
 * This class defines an event corresponding to the pressure of a button of a mouse.
 */
class MousePressEvent extends Event {
	/** The x coordinate of the pressure. */
	protected int x;

	/** The y coordinate of the pressure. */
	protected int y;

	/** The object targeted during the pressure. */
	protected Object source;

	/** The button used to perform the pressure. */
	protected int button;

	/**
	 * Creates the event.
	 * @param idHID The identifier of the HID.
	 * @param x The x coordinate of the pressure.
	 * @param y The y coordinate of the pressure.
	 * @param button The button used to perform the pressure.
	 * @param source The object targeted during the pressure.
	 * @since 0.2
	 */
	public MousePressEvent(final int idHID, final int x, final int y, final int button, final Object source) {
		super(idHID);
		this.x = x;
		this.y = y;
		this.button = button;
		this.source = source;
	}
}


/**
 * Defines the concept of event.
 */
abstract class Event {
	/** The identifier of the HID. */
	protected int idHID;

	/**
	 * Creates the event.
	 * @param idHID The identifier of the HID.
	 * @since 0.2
	 */
	public Event(final int idHID) {
		super();
		this.idHID = idHID;
	}
}
