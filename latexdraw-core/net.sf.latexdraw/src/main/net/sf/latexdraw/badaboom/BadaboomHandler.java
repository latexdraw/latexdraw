package net.sf.latexdraw.badaboom;

/**
 * Defines a 'Bordel !' handler. A object that wants to be aware of the
 * Activities of a 'bordel' manager must implement this interface.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 03/19/2008<br>
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
	void notifyEvent(final Throwable e);

	/**
	 * Actions to do when exceptions are received by the manager.
	 * @since 3.0
	 */
	void notifyEvents();
}
