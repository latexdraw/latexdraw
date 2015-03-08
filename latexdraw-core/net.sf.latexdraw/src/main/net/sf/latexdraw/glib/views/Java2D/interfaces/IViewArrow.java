package net.sf.latexdraw.glib.views.Java2D.interfaces;

import net.sf.latexdraw.glib.models.interfaces.shape.Color;
import java.awt.Graphics2D;

/**
 * This interface defines a view of an arrow.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 11/18/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface IViewArrow {
	/**
	 * Paints the arrow.
	 * @param g The graphics into which the arrow will be painted.
	 * @param fColour The colour of the filling of the arrow.
	 * @param asShadow True: it is the shadow of a shape.
	 * @since 3.0
	 */
	void paint(final Graphics2D g, final Color fColour, final boolean asShadow);

	/**
	 * Updates the path of the arrow.
	 * @since 3.0
	 */
	void updatePath();
}
