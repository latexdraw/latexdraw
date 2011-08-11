package org.malai.widget;

import javax.swing.JScrollPane;

/**
 * This interface is for widgets that can have a scroll pane.<br>
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
 * 05/15/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public interface Scrollable {
	/**
	 * @return True if the panel is in a scroll pane.
	 * @since 3.0
	 */
	boolean hasScrollPane();


	/**
	 * @return The scroll pane of the panel (may be null).
	 * @since 3.0
	 */
	JScrollPane getScrollpane();
}
