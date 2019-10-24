/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.sf.latexdraw.util.Injector;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import io.github.interacto.instrument.Instrument;

public class InjectionExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver, BeforeAllCallback {
	Injector injector;
	Optional<Method> configureInjection;

	List<Method> getAllMethods(final Class<?> klass) {
		final List<Method> allMethods = new ArrayList<>(Arrays.asList(klass.getDeclaredMethods()));

		if(klass.getEnclosingClass() != null) {
			allMethods.addAll(getAllMethods(klass.getEnclosingClass()));
		}

		if(klass.getSuperclass() != null) {
			allMethods.addAll(getAllMethods(klass.getSuperclass()));
		}

		return allMethods;
	}

	@Override
	public void beforeAll(final ExtensionContext ctx) {
		configureInjection = getAllMethods(ctx.getTestClass().orElseThrow()).stream().filter(m -> m.isAnnotationPresent(ConfigureInjection.class)).findFirst();
	}

	@Override
	public void beforeEach(final ExtensionContext ctx) {
		injector = configureInjection.map(m -> {
			try {
				m.setAccessible(true);
				if(m.getDeclaringClass().isAssignableFrom(ctx.getTestClass().orElseThrow())) {
					return (Injector) m.invoke(ctx.getTestInstance().orElseThrow());
				}
				throw new IllegalArgumentException("Nested test class not supported yet");

			}catch(IllegalAccessException | InvocationTargetException ex) {
				ex.printStackTrace();
			}
			return createDefaultInjector();
		}).orElseGet(() -> createDefaultInjector());
		injector.initialise();
	}

	private Injector createDefaultInjector() {
		return new Injector() {
			@Override
			protected void configure() {
			}
		};
	}

	@Override
	public void afterEach(final ExtensionContext ctx) {
		injector.getInstances().stream().filter(obj -> obj instanceof Instrument<?>).forEach(ins -> ((Instrument<?>) ins).uninstallBindings());
		injector.clear();
	}

	@Override
	public boolean supportsParameter(final ParameterContext paramCtx, final ExtensionContext extCtx) throws ParameterResolutionException {
		return injector.getInstance(paramCtx.getParameter().getType()) != null;
	}

	@Override
	public Object resolveParameter(final ParameterContext paramCtx, final ExtensionContext extCtx) throws ParameterResolutionException {
		return injector.getInstance(paramCtx.getParameter().getType());
	}
}
