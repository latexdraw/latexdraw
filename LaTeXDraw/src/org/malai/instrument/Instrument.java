package org.malai.instrument;

import java.util.ArrayList;
import java.util.List;

import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.interaction.Eventable;
import org.malai.preferences.Preferenciable;
import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Defines an abstract model of an instrument.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 *
 * @author Arnaud BLOUIN
 * @date 05/24/10
 * @since 0.1
 * @version 0.2
 */
public abstract class Instrument implements Preferenciable, Modifiable, Reinitialisable, ActionHandler {
	/**  Defines if the instrument is activated or not. */
	protected boolean activated;

	/**  The links of the instrument. */
	protected List<Link<?,?,?>> links;

	/** Defined if the instrument has been modified. */
	protected boolean modified;

	/** The eventable objects that the instrument uses. */
	protected List<Eventable> eventables;


	/**
	 * Creates and initialises the instrument.
	 * @since 0.1
	 */
	public Instrument() {
		activated = false;
		modified  = false;
		links	  = new ArrayList<Link<?,?,?>>();
	}


	/**
	 * @return The number of links that compose the instrument.
	 * @since 0.2
	 */
	public int getSizeLinks() {
		return links.size();
	}


	/**
	 * @return True: the instrument has at least one link. False otherwise.
	 * @since 0.2
	 */
	public boolean hasLinks() {
		return getSizeLinks()>0;
	}


	/**
	 * @return The links that compose the instrument. Cannot be null.
	 * @since 0.2
	 */
	public List<Link<?,?,?>> getLinks() {
		return links;
	}


	/**
	 * Initialises the links of the instrument.
	 * @since 0.2
	 */
	protected abstract void initialiseLinks();


	/**
	 * Adds the given link to the list of links of the instrument.
	 * Eventables object previously added to the instrument are added
	 * to the added link.
	 * @param link The link to add. If null, nothing is done.
	 * @since 0.2
	 */
	protected void addLink(final Link<?,?,?> link) {
		if(link!=null) {
			links.add(link);

			if(eventables!=null)
				for(final Eventable eventable : eventables)
					link.addEventable(eventable);
		}
	}


	/**
	 * Removes the given link from the list of links of the instrument.
	 * @param link The link to remove.
	 * @return True: the given link has been removed. False otherwise.
	 * @since 0.2
	 */
	protected boolean removeLink(final Link<?,?,?> link) {
		return link==null ? false : links.remove(link);
	}


	/**
	 * Binds the interaction of the links of the instrument to a Eventable object that produces
	 * events used by the interactions.
	 * @param eventable The eventable object that gathers event used by the interactions.
	 * @since 0.2
	 */
	public void addEventable(final Eventable eventable) {
		if(eventable!=null) {
			if(eventables==null)
				eventables = new ArrayList<Eventable>();

			eventables.add(eventable);

			for(Link<?,?,?> link : links)
				link.addEventable(eventable);
		}
	}


	/**
	 * Stops the interactions of the instrument and clears all its events waiting for a process.
	 * @since 0.2
	 */
	public void clearEvents() {
		for(final Link<?,?,?> link : links)
			link.clearEvents();
	}


	/**
	 * @return True if the instrument is activated.
	 */
	public boolean isActivated() {
		return activated;
	}


	/**
	 * Activates or deactivates the instrument.
	 * @param activated True = activation.
	 */
	public void setActivated(final boolean activated) {
		this.activated = activated;

		if(activated && !hasLinks())
			initialiseLinks();
		else
			for(final Link<?,?,?> link : links)
				link.setActivated(activated);

		interimFeedback();
	}



	/**
	 * Reinitialises the interim feedback of the instrument.
	 * Must be overridden.
	 */
	public void interimFeedback() {
		// Nothing to do
	}



	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		// Should be overridden.
	}



	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		// Should be overridden.
	}


	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;
	}


	@Override
	public boolean isModified() {
		return modified;
	}


	@Override
	public void reinit() {
		// Should be overridden.
	}


	@Override
	public void onUndoableAdded(final Undoable undoable) {
		// Should be overridden.
	}

	@Override
	public void onUndoableUndo(final Undoable undoable) {
		// Should be overridden.
	}

	@Override
	public void onUndoableRedo(final Undoable undoable) {
		// Should be overridden.
	}

	@Override
	public void onActionCancelled(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionAdded(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionAborted(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionExecuted(final Action action) {
		// Should be overridden.
	}

	@Override
	public void onActionDone(final Action action) {
		// Should be overridden.
	}
}
