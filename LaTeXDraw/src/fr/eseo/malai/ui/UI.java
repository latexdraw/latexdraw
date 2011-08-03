package fr.eseo.malai.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.WindowConstants;

import fr.eseo.malai.instrument.Instrument;
import fr.eseo.malai.presentation.AbstractPresentation;
import fr.eseo.malai.presentation.ConcretePresentation;
import fr.eseo.malai.presentation.Presentation;
import fr.eseo.malai.properties.Modifiable;
import fr.eseo.malai.widget.MFrame;

/**
 * Defines the concept of User Interface based on a JFrame.<br>
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
 * @author Arnaud BLOUIN
 * @since 0.1
 * @version 0.2
 */
public abstract class UI extends MFrame implements Modifiable {
	private static final long serialVersionUID = 1L;

	/** The presentations of the interactive system. */
	protected List<Presentation<?,?>> presentations;

	/** Defined if the UI has been modified. */
	protected boolean modified;


	/**
	 * Creates a user interface.
	 * @since 0.1
	 */
	public UI() {
		super(true);

		modified 		= false;
		presentations 	= new ArrayList<Presentation<?,?>>();
		initialisePresentations();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}


	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;

		for(final Presentation<?, ?> pres : presentations)
			pres.setModified(modified);

		for(final Instrument ins : getInstruments())
			ins.setModified(modified);
	}


	@Override
	public boolean isModified() {
		boolean ok = modified;
		int i=0;
		final int size = presentations.size();

		// Looking for a modified presentation.
		while(!ok && i<size)
			if(presentations.get(i).isModified())
				ok = true;
			else i++;

		final Instrument[] instruments = getInstruments();
		i=0;

		while(i<instruments.length && !ok)
			if(instruments[i].isModified())
				 ok = true;
			else i++;

		return ok;
	}


	/**
	 * @return The instruments of the interactive system.
	 * @since 0.2
	 */
	public abstract Instrument[] getInstruments();



	/**
	 * Initialises the presentations of the UI.
	 * @since 0.2
	 */
	public abstract void initialisePresentations();


	/**
	 * Updates the presentations.
	 * @since 0.2
	 */
	public void updatePresentations() {
		for(Presentation<?,?> presentation : presentations)
			presentation.update();
	}


	/**
	 * Allows to get the presentation which abstract and concrete presentations match the given classes.
	 * @param absPresClass The class of the abstract presentation to find.
	 * @param concPresClass The class of the concrete presentation to find.
	 * @return The found presentation or null.
	 * @since 0.2
	 */
	@SuppressWarnings("unchecked")
	public <A extends AbstractPresentation, C extends ConcretePresentation>
	Presentation<A, C> getPresentation(final Class<A> absPresClass, final Class<C> concPresClass) {
		Presentation<A, C> pres = null;
		Presentation<?, ?> tmp;

		for(int i=0, size=presentations.size(); i<size && pres==null; i++) {
			tmp = presentations.get(i);
			if(absPresClass.isInstance(tmp.getAbstractPresentation()) && concPresClass.isInstance(tmp.getConcretePresentation()))
				pres = (Presentation<A, C>)tmp;
		}

		return pres;
	}


	/**
	 * @return The presentations of the interactive system.
	 * @since 0.2
	 */
	public List<Presentation<?,?>> getPresentations() {
		return presentations;
	}
}
