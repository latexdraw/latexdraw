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
package net.sf.latexdraw.models.interfaces.shape;

/**
 * The API for single shapes that can have arrows.
 * @author Arnaud Blouin
 */
public interface IArrowableSingleShape extends ISingleShape, IArrowableShape {
	/**
	 * @param index The index of the arrow to use.
	 * @return The line that will be used to place the arrow or null.
	 * @since 3.0
	 */
	ILine getArrowLine(final int index);
}
