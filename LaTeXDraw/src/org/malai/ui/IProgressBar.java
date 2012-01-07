package org.malai.ui;

/**
 * This interface defines a progress bar which progression can be modified.<br>
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
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface IProgressBar {
	/**
	 * Increments the progress bar.
	 * @param increment The value to add to the progress bar.
	 */
	void addToProgressBar(final int increment);
}
