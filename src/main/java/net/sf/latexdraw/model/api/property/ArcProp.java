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
package net.sf.latexdraw.model.api.property;

import net.sf.latexdraw.model.api.shape.ArcStyle;
import org.jetbrains.annotations.NotNull;

/**
 * Properties of arcs.
 * @author Arnaud Blouin
 */
public interface ArcProp {
	/**
	 * @return the style of the arc.
	 */
	@NotNull ArcStyle getArcStyle();

	/**
	 * @param style the arc style to set.
	 */
	void setArcStyle(final @NotNull ArcStyle style);

	/**
	 * @return the angleStart.
	 */
	double getAngleStart();

	/**
	 * @param angleStart the angleStart to set.
	 */
	void setAngleStart(final double angleStart);

	/**
	 * @return the angleEnd.
	 */
	double getAngleEnd();

	/**
	 * @param angleEnd the angleEnd to set.
	 */
	void setAngleEnd(final double angleEnd);
}
