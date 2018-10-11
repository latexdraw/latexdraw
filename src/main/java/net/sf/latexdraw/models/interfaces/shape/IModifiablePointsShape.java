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
 * The API for modifiable points.
 * @author Arnaud BLOUIN
 */
public interface IModifiablePointsShape extends ISingleShape {
	/**
	 * Sets the point at the given position to the given coordinate.
	 * @param x The new X-coordinate. The model is not updated!
	 * @param y The new Y-coordinate.
	 * @param position The position of the point to move in the points list (-1 corresponds to the last point).
	 * @return true if the operation is successful.
	 */
	boolean setPoint(final double x, final double y, final int position);

	@Override
	IModifiablePointsShape duplicate();

	/**
	 * Sets the rotation angle of the shape. The points of the shape are never transformed (rotated)
	 * when this method is called.
	 * @param rotationAngle The new rotation angle
	 */
	void setRotationAngleOnly(final double rotationAngle);
}
