package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of a rounded arc.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
class LCircleArc extends LArc implements ICircleArc {
	/**
	 * Creates a circled arc with radius 1.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @since 3.0
	 */
	protected LCircleArc(final boolean isUniqueID) {
		this(new LPoint(10, 10), new LPoint(20, 20), isUniqueID);
	}


	/**
	 * Creates a circled arc.
	 * @param tl The top-left point of the arc.
	 * @param br The bottom-right point of the arc.
	 * @param isUniqueID True: the circled arc will have a unique ID.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	protected LCircleArc(final IPoint tl, final IPoint br, final boolean isUniqueID) {
		super(tl, br, isUniqueID);
	}


	@Override
	public double getRadius() {
		return getWidth();
	}


	@Override
	public ICircleArc duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof ICircleArc ? (ICircleArc)sh : null;
	}


	@Override
	public boolean setRight(final double x) {
		boolean ok = super.setRight(x);

		if(ok)
			setHeight(getWidth());

		return ok;
	}


	@Override
	public boolean setLeft(final double x) {
		boolean ok = super.setLeft(x);

		if(ok)
			setHeight(getWidth());

		return ok;
	}


	@Override
	public boolean setTop(final double y) {
		boolean ok = super.setTop(y);

		if(ok)
			setWidth(getHeight());

		return ok;
	}


	@Override
	public boolean setBottom(final double y) {
		boolean ok = super.setBottom(y);

		if(ok)
			setWidth(getHeight());

		return ok;
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


	@Override
	public void scale(final double sx, final double sy, final Position pos) {
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

		super.scale(scale, scale, position);
	}
}
