package net.sf.latexdraw.badaboom;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Defines a badaboom handler. A object that wants to be aware of the
 * Activities of a 'bordel' manager must implement this interface.
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 03/19/2008
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface BadaboomHandler {
	/**
	 * Actions to do when an exception is received by the manager.
	 * @param e The received exception.
	 * @since 3.0
	 */
	void notifyEvent(@NonNull final Throwable e);

	/**
	 * Actions to do when exceptions are received by the manager.
	 * @since 3.0
	 */
	void notifyEvents();
}
