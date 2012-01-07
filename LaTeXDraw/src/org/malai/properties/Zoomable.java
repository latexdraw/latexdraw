package org.malai.properties;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Defines an interface to zoomable objects.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/11/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.1
 * @since 0.1
 */
public interface Zoomable {
	/** The increment of a zooming ({@value}). */
	double ZOOM_INCREMENT = 0.25;

	/** The biggest zoom we can do ({@value}) */
	double MAX_ZOOM = 4.5;

	/** The smallest zoom we can do ({@value}) . */
	double MIN_ZOOM = 0.25;


	/**
	 * @return The zoom level.
	 * @since 0.1
	 */
	double getZoom();


	/**
	 * Zooms in the zoomable object.
	 * @param zoomingLevel The zooming level.
	 * @param x The X-coordinate of the location to zoom.
	 * @param y The Y-coordinate of the location to zoom.
	 * @since 0.1
	 */
	void setZoom(final double x, final double y, final double zoomingLevel);


	/**
	 * Transforms the given point in a point which coordinates have been modified to
	 * take account of the zoom level.
	 * @param x The X-coordinate of the point to modify.
	 * @param y The Y-coordinate of the point to modify.
	 * @return The transformed point.
	 * @since 0.2
	 */
	Point2D getZoomedPoint(final double x, final double y);


	/**
	 * Transforms the given point in a point which coordinates have been modified to
	 * take account of the zoom level.
	 * @param pt The point to transform.
	 * @return The transformed point. Returns (0,0) if the given point is null.
	 * @since 0.2
	 */
	Point2D getZoomedPoint(final Point pt);
}
