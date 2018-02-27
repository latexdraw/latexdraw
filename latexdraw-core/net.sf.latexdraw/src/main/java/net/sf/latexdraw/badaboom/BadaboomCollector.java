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
package net.sf.latexdraw.badaboom;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.malai.error.ErrorCatcher;
import org.malai.error.ErrorNotifier;

/**
 * Defines an error collector.
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public final class BadaboomCollector extends ArrayList<Throwable> implements UncaughtExceptionHandler, ErrorNotifier, EventHandler<WorkerStateEvent> {
	/** The logger that will stores the errors. */
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	/** The singleton. */
	public static final BadaboomCollector INSTANCE = new BadaboomCollector();

	/** Contains objects that want to be aware of the manager activities. */
	private final List<BadaboomHandler> handlers;


	/**
	 * Creates an empty collector.
	 */
	private BadaboomCollector() {
		super();
		handlers = new ArrayList<>();
		ErrorCatcher.INSTANCE.setNotifier(this);
	}


	/**
	 * Adds a handler to the manager.
	 * @param handler The handler to add. Must not be null.
	 * @since 3.0
	 */
	public void addHandler(final BadaboomHandler handler) {
		synchronized(handlers) {
			handlers.add(handler);
		}

		// If there is events, the handler is notified.
		synchronized(INSTANCE) {
			if(!INSTANCE.isEmpty()) {
				handler.notifyEvents();
			}
		}
	}

	/**
	 * Notifies the handlers that an event occurred.
	 * @since 3.0
	 */
	private void notifyHandlers(final Throwable error) {
		synchronized(handlers) {
			handlers.forEach(handler -> handler.notifyEvent(error));
		}
	}


	@Override
	public boolean add(final Throwable ex) {
		synchronized(INSTANCE) {
			if(ex == null || !super.add(ex)) {
				return false;
			}
		}
		LOGGER.log(Level.SEVERE, "An Exception occured.", ex); //NON-NLS
		notifyHandlers(ex);
		return true;
	}


	@Override
	public Throwable set(final int pos, final Throwable ex) {
		synchronized(INSTANCE) {
			if(ex == null || super.set(pos, ex) == null) {
				return null;
			}
		}
		LOGGER.log(Level.SEVERE, "An Exception occured.", ex); //NON-NLS
		notifyHandlers(ex);
		return ex;
	}

	@Override
	public void add(final int index, final Throwable ex) {
		synchronized(INSTANCE) {
			if(ex != null) {
				super.add(index, ex);
				LOGGER.log(Level.SEVERE, "An Exception occured.", ex); //NON-NLS
				notifyHandlers(ex);
			}
		}
	}


	@Override
	public void uncaughtException(final Thread thread, final Throwable trowable) {
		add(trowable);
	}


	@Override
	public void onException(final Exception exception) {
		add(exception);
	}


	@Override
	public void handle(final WorkerStateEvent evt) {
		add(evt.getSource().getException());
	}
}
