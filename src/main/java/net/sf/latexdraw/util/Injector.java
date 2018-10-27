/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The base class for performing dependency injection.
 * @author Arnaud Blouin
 */
public abstract class Injector {
	static final Logger LOGGER = Logger.getAnonymousLogger();

	static {
		LOGGER.setLevel(Level.SEVERE);
	}

	private final Set<Class<?>> singletons;
	private final Map<Class<?>, Object> instances;
	private final Map<Class<?>, Supplier<Object>> instancesSuppliers;
	private final Map<Class<?>, Supplier<?>> bindingsBetweenTypes;

	/**
	 * Creates the base injector.
	 * The bindings are configured and attributes injected.
	 */
	public Injector() {
		super();
		instances = new HashMap<>();
		instancesSuppliers = new HashMap<>();
		bindingsBetweenTypes = new HashMap<>();
		singletons = new HashSet<>();
	}

	/**
	 * To be called to configure the injector.
	 */
	public void initialise() {
		try {
			configure();
			singletons.forEach(cl -> injectFieldsOf(instances.get(cl)));
		}catch(final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
			LOGGER.log(Level.SEVERE, "Cannot create an instance of a class. Make sure this class has a public default constructor.", ex); //NON-NLS
		}
	}

	/**
	 * Configures the bindings and create the singletons.
	 * The injection are not performed in this method.
	 * @throws InstantiationException On instantiation issue.
	 * @throws IllegalAccessException On instantiation issue.
	 */
	protected abstract void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

	/**
	 * Returns an instance of the given cl or null.
	 * @param cl The class to instantiate.
	 * @param <T> The type of the instance.
	 * @return The created instance of null when: the given class is or not registered.
	 */
	public <T> T getInstance(final Class<T> cl) {
		if(cl == null || cl.isPrimitive() || cl.isArray() || cl.isEnum() || !isConfigured(cl)) {
			return null;
		}

		LOGGER.info(() -> "Getting an instance of " + cl.getTypeName()); //NON-NLS

		final Supplier<?> supplier = bindingsBetweenTypes.get(cl);

		if(supplier != null) {
			return (T) supplier.get();
		}

		T instance = (T) instancesSuppliers.getOrDefault(cl, () -> null).get();

		if(instance == null) {
			instance = (T) instances.get(cl);
		}

		try {
			if(instance == null) {
				instance = cl.getDeclaredConstructor().newInstance();
			}

			injectFieldsOf(instance);
			return instance;
		}catch(final NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
			LOGGER.log(Level.SEVERE, "Cannot create an instance of " + cl.getTypeName() + ". Make sure this class has a public default constructor.", ex); //NON-NLS
			return null;
		}
	}

	/**
	 * Checks whether the given class is configured.
	 * @param cl The class to check.
	 * @return True: the class is configured. False otherwise.
	 */
	private boolean isConfigured(final Class<?> cl) {
		synchronized(instances) {
			synchronized(bindingsBetweenTypes) {
				return cl != null && (instances.containsKey(cl) || instancesSuppliers.containsKey(cl) || bindingsBetweenTypes.containsKey(cl));
			}
		}
	}

	/**
	 * Injects the fields (direct and inherited) of the instance.
	 * @param instance The instance to inject.
	 */
	private void injectFieldsOf(final Object instance) {
		// Getting the fields to inject.
		getAllFieldsToInject(instance.getClass()).stream().filter(field -> {
			// The field must be configured
			if(!isConfigured(field.getType())) {
				LOGGER.log(Level.SEVERE, "The field " + field.getName() + " in " + instance.getClass().getTypeName() + //NON-NLS
					" has not been configured for injection yet. Aborting its injection."); //NON-NLS
				return false;
			}
			return true;
		}).forEach(field -> {
			try {
				Object value;
				// First: checking whether a binding between types exists.
				synchronized(bindingsBetweenTypes) {
					value = bindingsBetweenTypes.getOrDefault(field.getType(), () -> null).get();
				}

				// Otherwise, checking whether a singleton value exists.
				if(value == null) {
					synchronized(instances) {
						value = instances.get(field.getType());
					}
				}

				if(value == null) {
					synchronized(instancesSuppliers) {
						value = instancesSuppliers.getOrDefault(field.getType(), () -> null).get();
					}
				}

				// Injecting the value.
				final Object val = value;
				LOGGER.info(() -> "Injecting the field: " + field.getName() + " of " + instance.getClass().getTypeName() + " with: " + val); //NON-NLS
				final boolean oldAccess = field.canAccess(instance);
				field.setAccessible(true);
				field.set(instance, value);
				field.setAccessible(oldAccess);
			}catch(final IllegalAccessException ex) {
				LOGGER.log(Level.SEVERE, "Cannot access a field to inject.", ex); //NON-NLS
			}
		});
	}

	/**
	 * Configures the given class as a eager singleton.
	 * The class is immediately instantiated (but not injected) and stored.
	 * @param cl The class to configure.
	 * @param <T> The type of the singleton.
	 * @throws IllegalAccessException On instantiation issues.
	 * @throws InstantiationException On instantiation issues.
	 */
	public <T> void bindAsEagerSingleton(final Class<T> cl) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		if(cl == null) {
			return;
		}
		// Getting all the constructors
		final Constructor<T>[] cons = (Constructor<T>[]) cl.getDeclaredConstructors();

		// Looking for the default constructor.
		// Do not use the getDeclaredConstructor() method to do that as it raises an exception when no default constructor is defined.
		final Optional<Constructor<T>> conDefault = Arrays.stream(cons).filter(con -> con.getParameterCount() == 0).findFirst();
		final T instance;

		if(conDefault.isPresent()) {
			// Simply calling the default constructor
			instance = conDefault.get().newInstance();
		}else {
			// Looking for a constructor that all its arguments are annotated with @Inject
			final Constructor<T> conInjection = Arrays.stream(cons).filter(con -> Arrays.stream(con.getParameterAnnotations()).
				flatMap(annots -> Arrays.stream(annots)).
				allMatch(annot -> annot.annotationType() == Inject.class)).
				findFirst().orElseThrow(NoSuchMethodException::new);

			// Calling the found constructor with as argument instances managed by the injector.
			instance = conInjection.newInstance(Arrays.stream(conInjection.getParameterTypes()).map(arg -> getInstance(arg)).toArray());
		}

		synchronized(singletons) {
			singletons.add(cl);
		}
		bindToInstance(cl, instance);
	}

	/**
	 * Binds the given class to the given instance. Nothing is done if one of the parameters is null.
	 * @param cl The class to bind.
	 * @param supplier The supplier that will return the instance.
	 * @param <T> The type of the instance.
	 */
	public <T> void bindToSupplier(final Class<T> cl, final Supplier<T> supplier) {
		if(cl != null && supplier != null) {
			synchronized(instances) {
				instancesSuppliers.put(cl, (Supplier<Object>) supplier);
			}
		}
	}

	/**
	 * Binds the given class to the given instance. Nothing is done if one of the parameters is null.
	 * @param cl The class to bind.
	 * @param instance The instance to use.
	 * @param <T> The type of the singleton.
	 */
	public <T> void bindToInstance(final Class<T> cl, final T instance) {
		if(cl != null && instance != null) {
			synchronized(instances) {
				instances.put(cl, instance);
			}
		}
	}

	/**
	 * Binds a source type to an output type through a given command.
	 * Nothing is done is one of the parameters is null.
	 * @param out The type of the instance to get.
	 * @param src The type of the source instance.
	 * @param cmd The command that do the bindings: that returns an output instance from an input one.
	 * @param <T> The output type.
	 * @param <S> The source type.
	 */
	public <T, S> void bindWithCommand(final Class<T> out, final Class<S> src, final Function<S, T> cmd) {
		if(out != null && src != null && cmd != null) {
			synchronized(instances) {
				if(instances.containsKey(out)) {
					LOGGER.severe("The type " + out.getTypeName() + " is already registered. Ignoring the binding."); //NON-NLS
					return;
				}

				if(!instances.containsKey(src)) {
					LOGGER.severe("The type " + src.getTypeName() + " is not registered yet. Ignoring the binding."); //NON-NLS
					return;
				}
			}

			synchronized(bindingsBetweenTypes) {
				bindingsBetweenTypes.put(out, () -> cmd.apply(getInstance(src)));
			}
		}
	}

	/**
	 * A helper method to get all (direct and inherited) attributes of a given class that need to be injected
	 * (annotated by @Inject from javax). Works only on the net.sf.latexdraw classes.
	 * @param cl The class to analyse.
	 * @return The fields to inject.
	 */
	private static Set<Field> getAllFieldsToInject(final Class<?> cl) {
		final Set<Field> fields = new HashSet<>(Arrays.asList(cl.getDeclaredFields()));
		Class<?> currentClass = cl.getSuperclass();

		while(currentClass != null && currentClass.getTypeName().startsWith("net.sf.latexdraw")) { //NON-NLS
			fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
			currentClass = currentClass.getSuperclass();
		}

		return fields.stream().filter(field -> field.isAnnotationPresent(Inject.class)).collect(Collectors.toSet());
	}

	/**
	 * @return The instances stored by the injector. Cannot be null.
	 */
	public Collection<Object> getInstances() {
		return Collections.unmodifiableCollection(instances.values());
	}

	public void clear() {
		singletons.clear();
		instances.clear();
		bindingsBetweenTypes.clear();
	}
}
