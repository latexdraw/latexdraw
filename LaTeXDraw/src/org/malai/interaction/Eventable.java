package org.malai.interaction;

/**
 * This interface corresponds to objects that may have an event manager.<br>
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
 * 05/13/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public interface Eventable {
	/**
	 * @return True: the Eventable object has an event manager that gathers events it produces.
	 * @since 0.2
	 */
	boolean hasEventManager();

	/**
	 * @return The event manager that gathers events the Eventable object produces.
	 * @since 0.2
	 */
	SwingEventManager getEventManager();
}
