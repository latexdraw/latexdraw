/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2015 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.glib.views;

import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;

/**
 * Defines a handler that provides information to a views synchroniser.
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
