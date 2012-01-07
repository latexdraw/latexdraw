package org.malai.error;

/**
 * The singleton ErrorCatcher permits to Malai code to send errors to a gatherer.
 * The ErrorCatcher then send the catched exception to an ErrorNotifier (if one is
 * defined). This is useful to gather Malai errors into a third-part application.
 * For instance, a develope can define his own error manager which implements
 * the ErrorNotifier interface and which is registered to the ErrorCatcher in order
 * to manage both the Malai exceptions and the third-part application errors.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/23/2011<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public final class ErrorCatcher {
	/** The singleton ErrorCatcher. */
	public static final ErrorCatcher INSTANCE = new ErrorCatcher();

	/** The notifier object. */
	private ErrorNotifier notifier;

	/**
	 * Creates the error catcher.
	 */
	private ErrorCatcher() {
		super();
	}

	/**
	 * Sets the notifier that will be notified about Malai errors.
	 * @param notifier The notifier that will be notified about errors. Can be null.
	 * @since 0.2
	 */
	public void setNotifier(final ErrorNotifier notifier) {
		this.notifier = notifier;
	}

	/**
	 * @return The notifier that is notified about errors.
	 * @since 0.2
	 */
	public ErrorNotifier getErrorNotifier() {
		return notifier;
	}


	/**
	 * Gathers malai exceptions.
	 * @param exception The errors to gather. Nothing is done if null or if no
	 * notifier is defined.
	 * @since 0.1
	 */
	public void reportError(final Exception exception) {
		if(exception!=null && notifier!=null)
			notifier.onMalaiException(exception);
	}
}
