package org.malai.mapping;

/**
 * Defines an interface for a registry that gathers modifications made to a listened objects.<br>
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
public interface IMappingRegistry {
	/**
	 * At start, mappings must be executed one time to initialise the mapping.
	 * @since 0.2
	 */
	void initMappings();


	/**
	 * Adds a mapping to the registry.
	 * @param mapping The mapping to add.
	 * @since 0.2
	 */
	void addMapping(final IMapping mapping);


	/**
	 * Executes mappings related to the given list for the addition of an object.
	 * @param list The modified source list.
	 * @param object The added object.
	 * @param index The position where the object has been added.
	 * @since 0.2
	 */
	<T> void onObjectAdded(final IActiveList<T> list, final T object, final int index);


	/**
	 * Executes mappings related to the given list for the deletion of an object.
	 * @param list The modified source list.
	 * @param object The deleted object.
	 * @param index The position where the object has been deleted.
	 * @since 0.2
	 */
	void onObjectRemoved(final IActiveList<?> list, final Object object, final int index);


	/**
	 * Executes the mapping when all the elements of the source were removed.
	 * @param list The list before the cleaning, i.e. it still contains all the elements in
	 * order to know which elements have been removed.
	 * @since 3.0
	 */
	void onListCleaned(final Object list);

	/**
	 * Executes mappings related to the given list for the move of an object.
	 * @param list The modified source list.
	 * @param object The move object.
	 * @param srcIndex The source position of the object.
	 * @param targetIndex The target position of the object.
	 * @since 0.2
	 */
	<T> void onObjectMoved(final IActiveList<T> list, final T object, final int srcIndex, final int targetIndex);


	/**
	 * Executes mappings related to the given list for the modification of an object.
	 * @param object The modified object.
	 * @since 0.2
	 */
	void onObjectModified(final Object object);



	/**
	 * Executes the mapping when an object has been replaced by an other one.
	 * @param object The singleton that contains the new object.
	 * @param replacedObject The old object contained in the singleton that has been replaced.
	 * @since 0.2
	 */
	<T> void onObjectReplaced(final ISingleton<T> object, final T replacedObject);


	/**
	 * Removes the mappings that use the given object as source.
	 * @param source A source object of the mappings to remove.
	 * @since 0.2
	 */
	void removeMappingsUsingSource(final Object source);


	/**
	 * Removes the mappings that use the given object as target.
	 * @param target A target object of the mappings to remove.
	 * @param clazz The type of the mappings to remove.
	 * @since 0.2
	 */
	void removeMappingsUsingTarget(final Object target, final Class<? extends IMapping> clazz);


	/**
	 * Removes the mappings that use the given object as target.
	 * @param target A target object of the mappings to remove.
	 * @since 0.2
	 */
	void removeMappingsUsingTarget(final Object target);


	/**
	 * Removes the mappings of the given type that use the given object as source.
	 * @param source A source object of the mappings to remove.
	 * @param clazz The type of the mappings to remove.
	 * @since 0.2
	 */
	void removeMappingsUsingSource(final Object source, final Class<? extends IMapping> clazz);


	/**
	 * @param source The source object used to find the target object matching the given class.
	 * @param targetType The class of the target object to find.
	 * @return The target object found using the given source object, or null.
	 * @since 0.2
	 */
	<T> T getTargetFromSource(final Object source, final Class<T> targetType);

	/**
	 * @param target The target object used to find the source object matching the given class.
	 * @param sourceType The class of the source object to find.
	 * @return The source object found using the given target object, or null.
	 * @since 0.2
	 */
	<T> T getSourceFromTarget(final Object target, final Class<T> sourceType);
}
