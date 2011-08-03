package fr.eseo.malai.mapping;

/**
 * This interface defines the concept of mapping that link a source object to a target object.<br>
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
 * 05/16/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public abstract class Object2ObjectMapping<S, T> implements IMapping {
	/** The source object. */
	protected S sourceObject;

	/** The target object. */
	protected T targetObject;


	/**
	 * Creates the mapping.
	 * @param source The source object.
	 * @param target The target object.
	 * @throws IllegalArgumentException If one of the given arguments is null or if they are the same object.
	 * @since 0.2
	 */
	public Object2ObjectMapping(final S source, final T target) {
		super();

		if(source==null || target==null || source==target)
			throw new IllegalArgumentException();

		sourceObject = source;
		targetObject = target;
	}

	@Override
	public S getSource() {
		return sourceObject;
	}



	@Override
	public T getTarget() {
		return targetObject;
	}

	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		//
	}



	@Override
	public void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex) {
		//
	}


	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		//
	}


	@Override
	public void onListCleaned(final Object list) {
		//
	}


	@Override
	public void onObjectReplaced(final ISingleton<?> object, final Object replacedObject) {
		//
	}


	@Override
	public void init() {
		onObjectModified(sourceObject);
	}


	@Override
	public void clear() {
		sourceObject = null;
		targetObject = null;
	}
}
