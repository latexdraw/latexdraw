package net.sf.latexdraw.models.interfaces.prop;

/**
 * An interface for shapes having X/Y scale properties.
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 * @author Arnaud Blouin
 * @since 3.2
 */
public interface IScalable {
	/**
	 * Sets the X-scale of the shape.
	 * @param xscale The new X-scale in ]0;+inf[
	 */
	void setXScale(final double xscale);

	/**
	 * Sets the Y-scale of the shape.
	 * @param yscale The new Y-scale in ]0;+inf[
	 */
	void setYScale(final double yscale);

	/**
	 * Sets both Y and X scales of the shape.
	 * @param scale The new Y and X scale in ]0;+inf[
	 */
	void setScale(final double scale);

	/**
	 * @return The X-scale of the shape.
	 */
	double getXScale();

	/**
	 * @return The Y-scale of the shape.
	 */
	double getYScale();
}
