package fr.eseo.malai.mapping;

/**
 * This interface defines the concept of mapping that link a source value (singleton) to a target one.<br>
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
 * 05/15/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public abstract class Singleton2SingletonMapping<S, T> implements IMapping {
	/** The source singleton of the mapping. */
	protected ISingleton<S> sourceObject;

	/** The target singleton of the mapping. */
	protected ISingleton<T> targetObject;


	/**
	 * Creates a singleton to singleton mapping.
	 * @param source The source singleton.
	 * @param target The target singleton.
	 * @throw IllegalArgumentException If one of the given arguments is null or if they are equal.
	 * @since 0.2
	 */
	public Singleton2SingletonMapping(final ISingleton<S> source, final ISingleton<T> target) {
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
