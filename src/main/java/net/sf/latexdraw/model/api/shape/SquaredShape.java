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
package net.sf.latexdraw.model.api.shape;

/**
 * The API for squared shapes.
 * @author Arnaud Blouin
 */
public interface SquaredShape extends PositionShape {
	/**
	 * Sets the width of the squared shape (the reference point is the bottom-left point of the shape).
	 * @param width The new width.
	 */
	void setWidth(final double width);
}
