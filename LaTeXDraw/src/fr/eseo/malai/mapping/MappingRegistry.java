package fr.eseo.malai.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * This registry allows to map source objects to target objects using mappings.
 * When a source object is modified, its corresponding mappings are executed to
 * update the  corresponding target objects.<br>
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
public final class MappingRegistry implements IMappingRegistry {
	/** The singleton of the class. */
	public static final IMappingRegistry REGISTRY = new MappingRegistry();

	/** Contains objects and their unique mapping. */
	private Map<Object, IMapping> uniqueMappings;

	/**
	 * Contains objects and their mappings. The difference with uniqueMappings is that
	 * multiMappings is not used by default (i.e. when only one mapping is attributed to an object)
	 * to save memory.
	 */
	private Map<Object, List<IMapping>> multiMappings;

	private Map<Object, IMapping> invertedUniqueMappings;

	private Map<Object, List<IMapping>> invertedMultiMappings;


	/**
	 * Creates a mapping registry.
	 * @since 0.2
	 */
	private MappingRegistry() {
		super();

		uniqueMappings = new IdentityHashMap<Object, IMapping>();
		multiMappings  = new IdentityHashMap<Object, List<IMapping>>();
		invertedUniqueMappings = new IdentityHashMap<Object, IMapping>();
		invertedMultiMappings  = new IdentityHashMap<Object, List<IMapping>>();
	}



	@Override
	public void initMappings() {
		Collection<IMapping> coll = uniqueMappings.values();
		for(IMapping mapping : coll)
			mapping.init();

		Collection<List<IMapping>> collList = multiMappings.values();
		for(List<IMapping> list : collList)
			for(IMapping mapping : list)
				mapping.init();
	}



	@Override
	public <T> T getTargetFromSource(final Object source, final Class<T> targetType) {
		return getSrcOrTarFromTarOrSrc(true, source, targetType);
	}



	@Override
	public <T> T getSourceFromTarget(final Object target, final Class<T> sourceType) {
		return getSrcOrTarFromTarOrSrc(false, target, sourceType);
	}


	/**
	 * Allows to get the source or the target object of a mapping using the target or the source object.
	 * @param fromSource True: the target object while be searched. Otherwise, the source object.
	 * @param object The source or target object of the mapping used to get the target or source object.
	 * @param type The class of the object to return.
	 * @return The found object or null.
	 * @since 0.2
	 */
	public <T> T getSrcOrTarFromTarOrSrc(final boolean fromSource, final Object object, final Class<T> type) {
		final List<IMapping> mappings = fromSource ? getMappingFromSource(object) : getMappingFromTarget(object);
		T result = null;

		switch(mappings.size()) {
			case 0:
				break;
			case 1:
				Object obj = fromSource ? mappings.get(0).getTarget() : mappings.get(0).getSource();
				// We must check that the type of the mapping corresponds to the given type.
				result = type.isInstance(obj) ? type.cast(obj) : null;
				break;
			default:
				final int size = mappings.size();
				int i = 0;

				while(result==null && i<size) {
					obj = fromSource ? mappings.get(i).getTarget() : mappings.get(i).getSource();
					i++;

					if(type.isInstance(obj))
						result = type.cast(obj);
				}
				break;
		}

		return result;
	}


	/**
	 * @param source This object is used to find the mapping that uses is as a source object.
	 * @return The found mapping or null.
	 * @since 0.2
	 */
	public List<IMapping> getMappingFromSource(final Object source) {
		return getMapping(source, uniqueMappings, multiMappings);
	}

	/**
	 * @param target This object is used to find the mapping that uses is as a target object.
	 * @return The found mapping or null.
	 * @since 0.2
	 */
	public List<IMapping> getMappingFromTarget(final Object target) {
		return getMapping(target, invertedUniqueMappings, invertedMultiMappings);
	}



	private static List<IMapping> getMapping(final Object object, final Map<Object,IMapping> uniqueMap, final Map<Object,List<IMapping>> multiMap) {
		final List<IMapping> mappings = new ArrayList<IMapping>();

		if(object!=null) {
			IMapping mapping = uniqueMap.get(object);

			if(mapping==null) {
				List<IMapping> multi = multiMap.get(object);

				if(multi!=null)
					for(IMapping map : multi)
						mappings.add(map);
			}else
				mappings.add(mapping);
		}

		return mappings;
	}



	@Override
	public void addMapping(final IMapping mapping) {
		if(mapping==null)
			return ;

		//TODO manage mappings with several sources or targets.
		addMappings(mapping, mapping.getSource(), uniqueMappings, multiMappings);
		addMappings(mapping, mapping.getTarget(), invertedUniqueMappings, invertedMultiMappings);
	}


	/**
	 * Is used by method addMapping(mapping).
	 * @param mapping The mapping to add.
	 * @param object The object that will be used as key in the map.
	 * @param uniqueMap The map that contains unique mappings.
	 * @param multiMap The map that contains multiple mappings.
	 * @since 0.2
	 */
	private static void addMappings(final IMapping mapping, final Object object, final Map<Object, IMapping> uniqueMap,
							final Map<Object, List<IMapping>> multiMap) {
		final List<IMapping> mappings = multiMap.get(object);

		if(mappings!=null)
			mappings.add(mapping);
		else
			if(uniqueMap.get(object)==null)
				uniqueMap.put(object, mapping);
			else {
				final List<IMapping> list = new ArrayList<IMapping>();
				list.add(uniqueMap.remove(object));
				list.add(mapping);
				multiMap.put(object, list);
			}
	}



	@Override
	public <T> void onObjectAdded(final IActiveList<T> list, final T object, final int index) {
		final IMapping mapping = uniqueMappings.get(list);

		if(mapping==null) {
			final List<IMapping> mappings = multiMappings.get(list);

			if(mappings!=null)
				for(IMapping map : mappings)
					map.onObjectAdded(list, object, index);
		}
		else
			mapping.onObjectAdded(list, object, index);
	}



	@Override
	public void onObjectRemoved(final IActiveList<?> list, final Object object, final int index) {
		final IMapping mapping = uniqueMappings.get(list);

		if(mapping==null) {
			final List<IMapping> mappings = multiMappings.get(list);

			if(mappings!=null)
				for(IMapping map : mappings)
					map.onObjectRemoved(list, object, index);
		}
		else
			mapping.onObjectRemoved(list, object, index);
	}


	@Override
	public void onListCleaned(final Object list) {
		final IMapping mapping = uniqueMappings.get(list);

		if(mapping==null) {
			final List<IMapping> mappings = multiMappings.get(list);

			if(mappings!=null)
				for(IMapping map : mappings)
					map.onListCleaned(list);
		}
		else
			mapping.onListCleaned(list);
	}



	@Override
	public <T> void onObjectMoved(final IActiveList<T> list, final T object, final int srcIndex, final int targetIndex) {
		final IMapping mapping = uniqueMappings.get(list);

		if(mapping==null) {
			final List<IMapping> mappings = multiMappings.get(list);

			if(mappings!=null)
				for(IMapping map : mappings)
					map.onObjectMoved(list, object, srcIndex, targetIndex);
		}
		else
			mapping.onObjectMoved(list, object, srcIndex, targetIndex);
	}



	@Override
	public <T> void onObjectReplaced(final ISingleton<T> object, final T replacedObject) {
		final IMapping mapping = uniqueMappings.get(object);

		if(mapping==null) {
			final List<IMapping> mappings = multiMappings.get(object);

			if(mappings!=null)
				for(IMapping map : mappings)
					map.onObjectReplaced(object, replacedObject);
		}
		else
			mapping.onObjectReplaced(object, replacedObject);
	}



	@Override
	public void onObjectModified(final Object object) {
		final IMapping mapping = uniqueMappings.get(object);

		if(mapping==null) {
			final List<IMapping> mappings = multiMappings.get(object);

			if(mappings!=null)
				for(IMapping map : mappings)
					map.onObjectModified(object);
		}
		else
			mapping.onObjectModified(object);
	}



	@Override
	public void removeMappingsUsingSource(final Object source, final Class<? extends IMapping> clazz) {
		removeMappingsUsingSource(source, clazz, true);
	}


	/**
	 * Idem than removeMappingsUsingSource.
	 * @param removeTargetMappings True: the inverted hash maps will be cleaned too.
	 * @since 0.2
	 */
	protected void removeMappingsUsingSource(final Object source, final Class<? extends IMapping> clazz, final boolean removeTargetMappings) {
		removeMappings(source, clazz, uniqueMappings, multiMappings, false, removeTargetMappings);
	}



	@Override
	public void removeMappingsUsingTarget(final Object target, final Class<? extends IMapping> clazz) {
		removeMappingsUsingTarget(target, clazz, true);
	}


	/**
	 * Idem than removeMappingsUsingTarget.
	 * @param removeTargetMappings True: the standard hash maps will be cleaned too.
	 * @since 0.2
	 */
	protected void removeMappingsUsingTarget(final Object target, final Class<? extends IMapping> clazz, final boolean removeSourceMappings) {
		removeMappings(target, clazz, invertedUniqueMappings, invertedMultiMappings, true, removeSourceMappings);
	}


	/**
	 * Remove the mappings of the given type and using the given object from the hash maps.
	 * @param object The source or target object of the mappings to remove.
	 * @param clazz The type of the mappings to remove.
	 * @param uniqueMaps The hash map that contains the unique mappings.
	 * @param multiMaps The hash map that contains the mappings contained into a list.
	 * @param removeUsingTarget True: will remove the mappings by considering the given object as a target object of the mappings.
	 * @param removeOppositeMappings True: mappings contains is the opposite hash maps will be removed too.
	 * @since 0.2
	 */
	protected void removeMappings(final Object object, final Class<? extends IMapping> clazz,
							final Map<Object, IMapping> uniqueMaps, final Map<Object, List<IMapping>> multiMaps,
							final boolean removeUsingTarget, final boolean removeOppositeMappings) {
		if(object==null)
			return ;

		List<IMapping> mappings = multiMaps.get(object);
		IMapping mapping		= uniqueMaps.get(object);

		//TODO manage mappings with several sources or targets.
		if(mapping!=null && (clazz==null || clazz.isInstance(mapping))) {
			// Removing the mapping.
			uniqueMaps.remove(object);
			// Removing the opposite mappings if required.
			if(removeOppositeMappings)
				removeOppositeMapping(mapping);
			// Freeing the mapping.
			mapping.clear();
		}

		if(mappings!=null) {
			int i=0;
			while(i<mappings.size()) {
				mapping = mappings.get(i);
				// Each mapping is tested.
				if(clazz==null || clazz.isInstance(mapping)) {
					// Removing the mapping.
					mappings.remove(i);
					// Removing the opposite mappings if required.
					if(removeOppositeMappings)
						removeOppositeMapping(mapping);
					// Freeing the mapping.
					mapping.clear();
				}
				else i++;
			}

			// If the list is now empty, it is removed from the hash map.
			if(mappings.isEmpty())
				multiMaps.remove(object);
		}
	}



	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();

		printMappings(buf, uniqueMappings, multiMappings);
		buf.append("---\n"); //$NON-NLS-1$
		printMappings(buf, invertedUniqueMappings, invertedMultiMappings);

		return buf.toString();
	}


	/**
	 * This method is used by the toString function to print the register.
	 * @since 0.2
	 */
	private static void printMappings(final StringBuffer buf, final Map<Object, IMapping> uMappings,
								final Map<Object, List<IMapping>> mMappings) {
		for(IMapping mapping : uMappings.values())
			buf.append(mapping).append('\n');

		for(List<IMapping> mappings : mMappings.values())
			for(IMapping mapping : mappings)
				buf.append(mapping).append('\n');
	}


	private void removeOppositeMapping(final IMapping mapping) {
		if(mapping==null) return;

		invertedUniqueMappings.values().remove(mapping);

		List<IMapping> maps = invertedMultiMappings.get(mapping.getTarget());

		if(maps!=null) {
			maps.remove(mapping);

			if(maps.isEmpty())
				invertedMultiMappings.remove(mapping.getTarget());
		}
	}


	@Override
	public void removeMappingsUsingSource(final Object source) {
		removeMappingsUsingSource(source, null);
	}



	@Override
	public void removeMappingsUsingTarget(final Object target) {
		removeMappingsUsingTarget(target, null);
	}
}
