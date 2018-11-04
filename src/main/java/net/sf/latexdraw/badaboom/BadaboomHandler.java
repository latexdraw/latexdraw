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

/**
 * Defines a badaboom handler. An object that wants to be aware of the activities of a 'badaboom' manager must implement this interface.
 * @author Arnaud BLOUIN
 */
public interface BadaboomHandler {
	/**
	 * Actions to do when an exception is received by the manager.
	 * @param throwable The received exception.
	 */
	void notifyEvent(final Throwable throwable);

	/**
	 * Actions to do when exceptions are received by the manager.
	 */
	void notifyEvents();
}
