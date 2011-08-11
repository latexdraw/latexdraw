package org.malai.presentation;

import org.malai.properties.Modifiable;
import org.malai.properties.Reinitialisable;

/**
 * A presentation contains an abstract presentation and a concrete presentation.
 * The goal of a presentation is to provide users with data (the abstract presentation) transformed
 * to be displayable (the concrete presentation).<br>
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
 * @date 05/24/10
 * @since 0.1
 * @version 0.1
 */
public class Presentation<A extends AbstractPresentation, C extends ConcretePresentation> implements Modifiable, Reinitialisable {
	/** The abstract presentation, i.e. the manipulated data model. */
	protected A abstractPresentation;

	/** The concrete presentation, i.e. the representation of the abstract presentation. */
	protected C concretePresentation;


	/**
	 * Creates a presentation.
	 * @param absPres The abstract presentation, i.e. the manipulated data model.
	 * @param concPres The concrete presentation, i.e. the representation of the abstract presentation.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 0.1
	 */
	public Presentation(final A absPres, final C concPres) {
		if(absPres==null || concPres==null)
			throw new IllegalArgumentException();

		abstractPresentation  = absPres;
		concretePresentation  = concPres;
	}


	@Override
	public void setModified(final boolean modified) {
		abstractPresentation.setModified(modified);
		concretePresentation.setModified(modified);
	}


	@Override
	public boolean isModified() {
		return abstractPresentation.isModified() || concretePresentation.isModified();
	}


	/**
	 * @return The abstract presentation, i.e. the manipulated data model.
	 * @since 0.1
	 */
	public A getAbstractPresentation() {
		return abstractPresentation;
	}


	/**
	 * @return The concrete presentation, i.e. the representation of the abstract presentation.
	 * @since 0.1
	 */
	public C getConcretePresentation() {
		return concretePresentation;
	}


	/**
	 * Updates the presentation.
	 * @since 0.2
	 */
	public void update() {
		concretePresentation.update();
	}


	/**
	 * Reinitialises the presentation (its concrete and abstract presentations).
	 * @since 0.2
	 */
	@Override
	public void reinit() {
		abstractPresentation.reinit();
		concretePresentation.reinit();
	}
}
