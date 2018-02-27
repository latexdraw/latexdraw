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
package net.sf.latexdraw.parsers.svg.path;

import java.awt.geom.Point2D;

/**
 * An interface for segments that have a point.
 * @author Arnaud Blouin
 */
public interface PointSeg {
	/**
	 * @return The position of the segment.
	 * @throws IllegalArgumentException If the previous point is null while the segment is relative.
	 */
	Point2D getPoint(final Point2D prevPoint);
}
