/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.handler;

import javafx.scene.paint.Color;

/**
 * The API for the handlers that manipulate shape views.
 * @author Arnaud BLOUIN
 */
public interface Handler {
	/**
	 * The default size of a handler.
	 */
	int DEFAULT_SIZE = 16;

	/**
	 * The default colour of the handler.
	 */
	Color DEFAULT_COLOR = new Color(0d, 0d, 0d, 0.4);

	/**
	 * Flushes the handler.
	 */
	default void flush() {
		// Do nothing by default.
	}
}
