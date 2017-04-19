/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.interfaces.prop;

import net.sf.latexdraw.models.interfaces.shape.ArcStyle;

/**
 * Properties of arcs.
 * @author Arnaud Blouin
 */
public interface IArcProp {
	/**
	 * @return the style of the arc.
	 * @since 3.0.0
	 */
	ArcStyle getArcStyle();

	/**
	 * @param style the arc style to set.
	 * @since 3.0.0
	 */
	void setArcStyle(final ArcStyle style);

	/**
	 * @return the angleStart.
	 * @since 3.0.0
	 */
	double getAngleStart();

	/**
	 * @param angleStart the angleStart to set.
	 * @since 3.0.0
	 */
	void setAngleStart(final double angleStart);

	/**
	 * @return the angleEnd.
	 * @since 3.0.0
	 */
	double getAngleEnd();

	/**
	 * @param angleEnd the angleEnd to set.
	 * @since 3.0.0
	 */
	void setAngleEnd(final double angleEnd);
}
