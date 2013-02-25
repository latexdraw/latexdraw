package net.sf.latexdraw.glib.views;

/**
 * Defines a generic command mixed with a chain of responsibility.
 * The goal is to tame the cyclomatic complexity of factories by linking
 * the commands together. If a command cannot create the view using the given objects,
 * the next command is called.<br>
 * this class has three generics: the first one define the model of the view; the
 * second one the view; the third one is the type of the next element of the chain of
 * responsibility.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 11/25/11<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @version 3.0
 * @param <M> The type of the model used to create the view.
 * @param <V> The type of the view to create.
 * @param <F> The type of the next command.
 */
public abstract class CreateViewCmd<M, V, F extends CreateViewCmd<M,V,F>> {
	/** The next command. */
	protected F next;

	/** The type of the model supported by the command. */
	protected Class<? extends M> clazz;

	/**
	 * Creates the command.
	 * @param next The next command in the chain of responsibility. Can be null.
	 * @param clazz The class of the model.
	 * @since 3.0
	 */
	public CreateViewCmd(final F next, final Class<? extends M> clazz) {
		super();
		this.next = next;
		this.clazz = clazz;
	}

	/**
	 * Launches the creation process.
	 * @param model The model used to create the view.
	 * @return The created view or null.
	 * @since 3.0
	 */
	public V execute(final M model) {
		V view;

		if(clazz.isInstance(model))
			view = create(model);
		else
			view = next==null ? null : next.execute(model);

		return view;
	}

	/**
	 * Creates an instance of the view corresponding to the given shape.
	 * @param model The model used to create the view.
	 * @return The created view.
	 * @since 3.0
	 */
	protected abstract V create(final M model);
}
