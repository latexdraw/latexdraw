package org.malai.mapping;

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
public interface IMapping {
	/**
	 * Returns the source object of the mapping. If there is several source objects, a collection is returned.
	 * @return The source object of the mapping.
	 * @since 0.2
	 */
	Object getSource();
	
	
	/**
	 * Returns the target object of the mapping. If there is several target objects, a collection is returned.
	 * @return The source object of the mapping.
	 * @since 0.2
	 */
	Object getTarget();
	
	
	/**
	 * Executes the mapping for the addition of an object.
	 * @param list The modified source list.
	 * @param object The added object.
	 * @param index The position where the object has been added. -1 = the last position.
	 * @since 0.2
	 */
	void onObjectAdded(final Object list, final Object object, final int index);


	/**
	 * Executes the mapping for the deletion of an object.
	 * @param list The modified source list.
	 * @param object The deleted object.
	 * @param index The position where the object has been deleted. -1 = the last position.
	 * @since 0.2
	 */
	void onObjectRemoved(final Object list, final Object object, final int index);

	
	/**
	 * Executes the mapping when all the elements of the source were removed.
	 * @param list The list before the cleaning, i.e. it still contains all the elements in
	 * order to know which elements have been removed.
	 * @since 3.0
	 */
	void onListCleaned(final Object list);
	

	/**
	 * Executes the mapping for the move of an object.
	 * @param list The modified source list.
	 * @param object The move object.
	 * @param srcIndex The source position of the object. -1 = the last position.
	 * @param targetIndex The target position of the object. -1 = the last position.
	 * @since 0.2
	 */
	void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex);


	/**
	 * Executes the mapping when an object has been replaced by an other one.
	 * @param object The singleton that contains the new object.
	 * @param replacedObject The old object contained in the singleton that has been replaced.
	 * @since 0.2
	 */
	void onObjectReplaced(final ISingleton<?> object, final Object replacedObject);


	/**
	 * Executes the mapping for the modification of an object.
	 * @param object The modified object.
	 * @since 0.2
	 */
	void onObjectModified(final Object object);


	/**
	 * Removes the references of the mapped objects of the mapping to avoid memory leaks.
	 * @since 0.2
	 */
	void clear();


	/**
	 * At start, the mapping must be executed one time to initialise the binding between
	 * source and target objects. 
	 * @since 3.0
	 */
	void init();
}
