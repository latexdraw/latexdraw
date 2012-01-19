package org.malai.mapping;

import java.util.List;

/**
 * Defines a mapping established between a list and an object.<br>
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
 * 01/19/2012<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 * @param <E> The type of the source element, contained in a list, of the mapping.
 * @param <F> The type of the target element of the mapping.
 */
public abstract class List2ObjectMapping<E, F> implements IMapping {
	/** The source list. */
	protected List<E> source;

	/** The target object. */
	protected F target;


	/**
	 * Creates the mapping.
	 * @param source The source list.
	 * @param target The target object.
	 * @throws IllegalArgumentException If one of the given elements is null or if they are the same object.
	 * @since 0.2
	 */
	public List2ObjectMapping(final List<E> source, final F target) {
		super();

		if(source==null || target==null || source==target)
			throw new IllegalArgumentException();

		this.source = source;
		this.target = target;
	}


	@Override
	public List<E> getSource() {
		return source;
	}


	@Override
	public F getTarget() {
		return target;
	}


	@Override
	public void clear() {
		source = null;
		target = null;
	}
}
