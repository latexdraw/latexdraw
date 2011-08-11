package org.malai.mapping;

import java.util.List;

/**
 * This interface defines the concept of mapping that link source objects with target objects.<br>
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
public abstract class SymmetricList2ListMapping<E, F> extends List2ListMapping<E, F> {
	/**
	 * {@link List2ListMapping#List2ListMapping(List, List)}
	 */
	public SymmetricList2ListMapping(final List<E> source, final List<F> target) {
		super(source, target);
	}



	/**
	 * Creates an instance of the target type using the given source object.
	 * @param sourceObject The source object used to create the target object.
	 * @return The created target object.
	 * @since 0.2
	 */
	protected abstract F createTargetObject(final Object sourceObject);



	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		if(index==-1 || index>=target.size())
			target.add(createTargetObject(object));
		else
			target.add(index, createTargetObject(object));
	}


	@Override
	public void onObjectModified(final Object object) {
		// Nothing to do.
	}


	@Override
	public void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex) {
		final int srcPos	= srcIndex==-1 ? target.size()-1 : srcIndex;
		final F targetObj 	= target.remove(srcPos);

		if(targetIndex== target.size() || targetIndex==-1)
			target.add(targetObj);
		else
			target.add(targetIndex, targetObj);
	}


	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		if(index==-1)
			target.remove(target.size()-1);
		else
			target.remove(index);
	}
	
	
	@Override
	public void onListCleaned(final Object list) {
		target.clear();
	}

	
	@Override
	public void init() {
		onListCleaned(source);
	}


	@Override
	public void onObjectReplaced(final ISingleton<?> object, final Object replcaedObject) {
		// Nothing to do.
	}
}
