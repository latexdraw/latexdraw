package org.malai.mapping;

/**
 * This interface defines the concept of mapping that link a source value (unary relation) to a target one.<br>
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
 * @param <S> The type of the source unary relation of the mapping.
 * @param <T> The type of the target unary relation of the mapping.
 */
public abstract class Unary2UnaryMapping<S, T> implements IMapping {
	/** The source unary relation of the mapping. */
	protected IUnary<S> sourceObject;

	/** The target unary relation of the mapping. */
	protected IUnary<T> targetObject;


	/**
	 * Creates a unary relation to unary relation mapping.
	 * @param source The source unary relation.
	 * @param target The target unary relation.
	 * @throw IllegalArgumentException If one of the given arguments is null or if they are equal.
	 * @since 0.2
	 */
	public Unary2UnaryMapping(final IUnary<S> source, final IUnary<T> target) {
		super();

		if(source==null || target==null || source==target)
			throw new IllegalArgumentException();

		sourceObject = source;
		targetObject = target;
	}



	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		// Cannot be applied for such a mapping.
	}



	@Override
	public void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex) {
		// Cannot be applied for such a mapping.
	}


	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		// Cannot be applied for such a mapping.
	}
	
	
	@Override
	public void init() {
		onObjectReplaced(sourceObject, sourceObject.getValue());
	}
	
	
	@Override
	public void onListCleaned(final Object list) {
		//
	}


	@Override
	public void clear() {
		sourceObject = null;
		targetObject = null;
	}



	@Override
	public Object getSource() {
		return sourceObject;
	}



	@Override
	public Object getTarget() {
		return targetObject;
	}
}
