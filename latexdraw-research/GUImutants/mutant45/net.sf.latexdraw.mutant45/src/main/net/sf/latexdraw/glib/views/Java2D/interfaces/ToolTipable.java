package net.sf.latexdraw.glib.views.Java2D.interfaces;

/**
 * Defines a type corresponding to shapes that support tooltips.<br>
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
 * 01/19/2012<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface ToolTipable {
	/**
	 * Tests if a tooltip can be displayed. The coordinates can be used to test
	 * if the current pointer position points onto the tooltipable object.
	 * @param x The X-coordinate to test.
	 * @param y The Y-coordinate to test.
	 * @return True if a tooltip can be displayed.
	 * @since 3.0
	 */
	boolean isToolTipVisible(final double x, final double y);

	/**
	 * @return The tooltip to display.
	 * @since 3.0
	 */
	String getToolTip();
}
