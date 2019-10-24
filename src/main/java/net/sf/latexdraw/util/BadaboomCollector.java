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
package net.sf.latexdraw.util;

import io.github.interacto.error.ErrorCatcher;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.jetbrains.annotations.NotNull;

/**
 * An error collector.
 * @author Arnaud BLOUIN
 */
public final class BadaboomCollector implements EventHandler<WorkerStateEvent> {
	/** The logger that will stores the errors. */
	private static final @NotNull Logger LOGGER = Logger.getAnonymousLogger();
	/** The singleton. */
	public static final @NotNull BadaboomCollector INSTANCE = new BadaboomCollector();

	static {
		Thread.setDefaultUncaughtExceptionHandler((t, throwable) -> INSTANCE.errors.add(throwable));
	}

	private final @NotNull ListProperty<Throwable> errors;

	/**
	 * Creates an empty collector.
	 */
	@SuppressWarnings("CheckReturnValue")
	private BadaboomCollector() {
		super();
		errors = new SimpleListProperty<>(FXCollections.observableArrayList());
		ErrorCatcher.getInstance().getErrors().subscribe(ex -> errors.add(ex));
	}

	public void add(final @NotNull Throwable ex) {
		synchronized(INSTANCE) {
			errors.add(ex);
		}
		LOGGER.log(Level.SEVERE, "An Exception occured.", ex); //NON-NLS
	}

	@Override
	public void handle(final @NotNull WorkerStateEvent evt) {
		add(evt.getSource().getException());
	}

	public void clear() {
		errors.clear();
	}

	public @NotNull ReadOnlyListProperty<Throwable> errorsProperty() {
		return errors;
	}
}
