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

import org.jetbrains.annotations.NotNull;

/**
 * The API for ellipses.
 * @author Arnaud BLOUIN
 */
public interface Ellipse extends RectangularShape {
	/**
	 * @return The half of the biggest axe.
	 */
	double getA();

	/**
	 * @return The half of the smallest axe.
	 */
	double getB();

	/**
	 * Translates the shape to its new centre.
	 * @param centre The new centre.
	 */
	void setCentre(final @NotNull Point centre);

	/**
	 * @return The center of the ellipse. Cannot be null.
	 */
	@NotNull Point getCenter();
}
