package fr.eseo.malai.instrument;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.eseo.malai.action.Action;
import fr.eseo.malai.interaction.Eventable;
import fr.eseo.malai.preferences.Preferenciable;
import fr.eseo.malai.properties.Modifiable;

/**
 * Defines an abstract model of an instrument.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
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
public abstract class Instrument implements Preferenciable, Modifiable {
	/**  Defines if the instrument is activated or not. */
	protected boolean activated;

	/**  The links of the instrument. */
	protected List<Link<?,?,?>> links;

	/** Defined if the instrument has been modified. */
	protected boolean modified;


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
	 * Binds the interaction of the links of the instrument to a Eventable object that produces
	 * events used by the interactions.
	 * @param eventable The eventable object that gathers event used by the interactions.
	 * @since 0.2
	 */
	public void addEventable(final Eventable eventable) {
		for(Link<?,?,?> link : links)
			link.addEventable(eventable);
	}



	/**
	 * @return True if the instrument is activated.
	 */
	public boolean isActivated() {
		return activated;
	}


	/**
	 * Activates or desactivates the instrument.
	 * @param activated True = activation.
	 */
	public void setActivated(final boolean activated) {
		this.activated = activated;

		for(Link<?,?,?> link : links)
			link.setActivated(activated);

		interimFeedback();
	}



	/**
	 * This method is called when an action managed by a link of the instrument is aborted.
	 * This method can be used to provide users with information about the result of the action.
	 * @param link The link of the instrument that managed the aborted action.
	 * @param action The aborted action.
	 * @since 0.2
	 */
	public void onActionAborted(final Link<?,?,?> link, final Action action) {
		// Should be overridden.
	}


	/**
	 * This method is called when an action managed by a link of the instrument is done.
	 * This method can be used to provide users with information about the result of the action.
	 * @param link The link of the instrument that managed the done action.
	 * @param action The done action.
	 * @since 0.2
	 */
	public void onActionDone(final Link<?,?,?> link, final Action action) {
		// Should be overridden.
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
}
