package net.sf.latexdraw.glib.models.impl;

import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of a circle.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/13/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LCircle extends LEllipse implements ICircle {
	/**
	 * Creates a square.
	 * @param isUniqueID isUniqueID True: the model will have a unique ID.
	 * @since 3.0
	 */
    protected LCircle(final boolean isUniqueID) {
		this(new LPoint(), 1., isUniqueID);
	}


	/**
	 * Creates a circle.
	 * @param pt The centre of the circle.
	 * @param radius The radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the radius is not valid.
	 * @throw NullPointerException If the given point pt is null.
	 */
    protected LCircle(final IPoint pt, final double radius, final boolean isUniqueID) {
		super(new LPoint(pt.getX()-radius, pt.getY()-radius), new LPoint(pt.getX()+radius, pt.getY()+radius), isUniqueID);
	}


	@Override
	public ICircle duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof ICircle ? (ICircle)sh : null;
	}


	@Override
	public void scale(final double sx, final double sy, final Position pos, final Rectangle2D bound) {
		final Position position;
		final double scale;

		switch(pos) {
			case EAST :
				position = Position.SE;
				scale = sx;
				break;
			case WEST:
				position = Position.SW;
				scale = sx;
				break;
			case NORTH:
				position = Position.NW;
				scale = sy;
				break;
			case SOUTH:
				position = Position.SW;
				scale = sy;
				break;
			default :
				position = pos;
				scale = sx;
		}

		super.scale(scale, scale, position, bound);
	}


	@Override
	public void setRx(final double rx) {
		super.setRx(rx);
		super.setRy(rx);
	}


	@Override
	public void setRy(final double rx) {
		setRx(rx);
	}


	@Override
	public double getA() {
		return getRx();
	}


	@Override
	public double getB() {
		return getRx();
	}


	@Override
	public void setWidth(final double width) {
		super.setWidth(width);
		super.setHeight(width);
	}



	@Override
	public void setHeight(final double height) {
		super.setHeight(height);
		super.setWidth(height);
	}
}
