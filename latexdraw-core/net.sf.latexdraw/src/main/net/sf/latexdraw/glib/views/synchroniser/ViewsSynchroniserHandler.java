package net.sf.latexdraw.glib.views.synchroniser;

import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a handler that provides information to a views synchroniser.<br>
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
 * 05/29/2008<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public interface ViewsSynchroniserHandler {
	/**
	 * @return The top right point of the current drawing.
	 * @since 3.0
	 */
	IPoint getTopRightDrawingPoint();

	/**
	 * @return The bottom left point of the current drawing.
	 * @since 3.0
	 */
	IPoint getBottomLeftDrawingPoint();

	/**
	 * @return The origin point of the current drawing.
	 * @since 3.0
	 */
	IPoint getOriginDrawingPoint();

	/**
	 * @return The number of pixels per centimetre of the current drawing.
	 * @since 3.0
	 */
	int getPPCDrawing();
}
