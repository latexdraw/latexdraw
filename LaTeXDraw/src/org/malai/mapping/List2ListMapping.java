package org.malai.mapping;

import java.util.List;

/**
 * Defines a mapping established between two lists.<br>
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
 * 05/15/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 * @param <E> The type of the source element of the mapping.
 * @param <F> The type of the target element of the mapping.
 */
public abstract class List2ListMapping<E, F> extends List2ObjectMapping<E, List<F>> {
	/**
	 * Creates the mapping.
	 * @param source The source list.
	 * @param target The target list.
	 * @throws IllegalArgumentException If one of the given lists is null or if they are the same object.
	 * @since 0.2
	 */
	public List2ListMapping(final List<E> source, final List<F> target) {
		super(source, target);
	}
}
