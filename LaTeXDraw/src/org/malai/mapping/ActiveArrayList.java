package org.malai.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Defines an active list that when an element is added/removed, then mapping registry
 * is then notified.<br>
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
public class ActiveArrayList<E> extends ArrayList<E> implements IActiveList<E> {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link ArrayList#ArrayList()}
	 */
	public ActiveArrayList() {
		super();
	}


	/**
 	 * {@link ArrayList#ArrayList(int)}
	 */
	public ActiveArrayList(final int initialCapacity) {
		super(initialCapacity);
	}


	/**
	 * {@link ArrayList#ArrayList(Collection)}
	 */
	public ActiveArrayList(final Collection<E> c) {
		super(c);
	}



	@Override
	public void move(final int srcIndex, final int targetIndex) {
		if(srcIndex<0 || targetIndex<0 || srcIndex>=size() || targetIndex>size())
			throw new IndexOutOfBoundsException();

		if(srcIndex==targetIndex)
			return ;

		final E elt = super.remove(srcIndex);

		if(targetIndex==size())
			super.add(elt);
		else
			super.add(targetIndex, elt);

		MappingRegistry.REGISTRY.onObjectMoved(this, elt, srcIndex, targetIndex);
	}



	@Override
	public boolean add(final E element) {
		final boolean ok = super.add(element);

		if(ok)
			MappingRegistry.REGISTRY.onObjectAdded(this, element, -1);

		return ok;
	}



	@Override
	public void add(final int index, final E element) {
		super.add(index, element);
		MappingRegistry.REGISTRY.onObjectAdded(this, element, index);
	}



	@Override
	public boolean addAll(final Collection<? extends E> collection) {
		final boolean ok = super.addAll(collection);

		if(ok)
			for(E obj : collection)
				MappingRegistry.REGISTRY.onObjectAdded(this, obj, -1);

		return ok;
	}



	@Override
	public boolean addAll(final int index, final Collection<? extends E> collection) {
		final boolean ok = super.addAll(index, collection);

		if(ok) {
			final List<E> array = new ArrayList<E>(collection);
			int i = array.size()-1;

			while(i>=0) {
				MappingRegistry.REGISTRY.onObjectAdded(this, array.get(i), index);
				i--;
			}
		}

		return ok;
	}



	@Override
	public void clear() {
		MappingRegistry.REGISTRY.onListCleaned(this);
		super.clear();
	}



	@Override
	public E remove(final int index) {
		final E elt = super.remove(index);

		if(elt!=null)
			MappingRegistry.REGISTRY.onObjectRemoved(this, elt, index);

		return elt;
	}



	@Override
	public boolean remove(final Object obj) {
		final int index = indexOf(obj);

		if(index==-1)
			return false;

		super.remove(index);
		MappingRegistry.REGISTRY.onObjectRemoved(this, obj, index);

		return true;
	}



	@Override
	public E set(final int index, final E element) {
		final E elt = get(index);

		super.set(index, element);
		MappingRegistry.REGISTRY.onObjectRemoved(this, elt, index);
		MappingRegistry.REGISTRY.onObjectAdded(this, element, index);

		return elt;
	}
}
