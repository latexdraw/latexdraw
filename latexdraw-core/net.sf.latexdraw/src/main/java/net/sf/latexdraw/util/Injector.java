/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	private final Map<Class<?>, Supplier<?>> bindingsBetweenTypes;

	/**
	 * Creates the base injector.
	 * The bindings are configured and attributes injected.
	 */
	public Injector() {
		super();
		instances = new HashMap<>();
		bindingsBetweenTypes = new HashMap<>();
		singletons = new HashSet<>();
		try {
			configure();
			singletons.forEach(cl -> injectFieldsOf(instances.get(cl)));
		}catch(final InstantiationException | IllegalAccessException ex) {
			LOGGER.log(Level.SEVERE, "Cannot create an instance of a class. Make sure this class has a public default constructor.", ex);
		}
	}

	/**
	 * Configures the bindings and create the singletons.
	 * The injection are not performed in this method.
	 * @throws InstantiationException On instantiation issue.
	 * @throws IllegalAccessException On instantiation issue.
	 */
	protected abstract void configure() throws InstantiationException, IllegalAccessException;

	/**
	 * Returns an instance of the given cl or null.
	 * @param cl The class to instantiate.
	 * @param <T> The type of the instance.
	 * @return The created instance of null when: the given class is or not registered.
	 */
	public <T> T getInstance(final Class<T> cl) {
		if(cl == null || cl.isPrimitive()) {
			return null;
		}

		LOGGER.info(() -> "Getting an instance of " + cl.getTypeName());

		final Supplier<?> supplier = bindingsBetweenTypes.get(cl);

		if(supplier != null) {
			return (T) supplier.get();
		}

		try {
			T instance = (T) instances.get(cl);

			if(instance == null) {
				instance = cl.newInstance();
			}

			injectFieldsOf(instance);
			return instance;
		}catch(final InstantiationException | IllegalAccessException ex) {
			LOGGER.log(Level.SEVERE, "Cannot create an instance of " + cl.getTypeName() + ". Make sure this class has a public default constructor.", ex);
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
				return cl != null && instances.containsKey(cl) || bindingsBetweenTypes.containsKey(cl);
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
				LOGGER.log(Level.SEVERE, "The field " + field.getName() + " in " + instance.getClass().getTypeName() +
					" has not been configured for injection yet. Aborting its injection.");
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

				// Injecting the value.
				final Object val = value;
				LOGGER.info(() -> "Injecting the field: " + field.getName() + " of " + instance.getClass().getTypeName() + " with: " + val);
				final boolean oldAccess = field.isAccessible();
				field.setAccessible(true);
				field.set(instance, value);
				field.setAccessible(oldAccess);
			}catch(final IllegalAccessException ex) {
				LOGGER.log(Level.SEVERE, "Cannot access a field to inject.", ex);
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
	public <T> void bindAsEagerSingleton(final Class<T> cl) throws IllegalAccessException, InstantiationException {
		if(cl != null) {
			final T instance = cl.newInstance();
			synchronized(instances) {
				instances.put(cl, instance);
			}
			synchronized(singletons) {
				singletons.add(cl);
			}
			bindToInstance(cl, instance);
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
	public <T, S> void bindWithCommand(final Class<T> out, final Class<S> src, Function<S, T> cmd) {
		if(out != null && src != null && cmd != null) {
			synchronized(instances) {
				if(instances.containsKey(out)) {
					LOGGER.severe("The type " + out.getTypeName() + " is already registered. Ignoring the binding.");
					return;
				}

				if(!instances.containsKey(src)) {
					LOGGER.severe("The type " + src.getTypeName() + " is not registered yet. Ignoring the binding.");
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

		while(currentClass != null && currentClass.getTypeName().startsWith("net.sf.latexdraw")) {
			fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
			currentClass = currentClass.getSuperclass();
		}

		return fields.stream().filter(field -> field.isAnnotationPresent(Inject.class)).collect(Collectors.toSet());
	}
}
