package org.malai.error;

/**
 * This interface must be used by error gatherer of third-part applications
 * that want to gather Malai's exceptions.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
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
public interface ErrorNotifier {
	/**
	 * Notifies that an exception has been thrown in the Malai code.
	 * @param exception The thrown exception.
	 * @since 0.2
	 */
	void onMalaiException(final Exception exception);
}
