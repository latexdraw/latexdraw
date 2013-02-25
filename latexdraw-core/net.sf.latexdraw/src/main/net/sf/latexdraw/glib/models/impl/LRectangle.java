package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of a rectangle.<br>
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
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LRectangle extends LRectangularShape implements IRectangle {
	/** The radius of arcs drawn at the corners of lines. */
	protected double frameArc;


	/**
	 * Creates a rectangle at the position (0,0).
	 * @param uniqueID True: the rectangle will have a unique ID.
	 */
	protected LRectangle(final boolean uniqueID) {
		this(new LPoint(), new LPoint(1, 1), uniqueID);
	}


	/**
	 * Creates a rectangle.
	 * @param pos The north-west point of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param uniqueID True: the rectangle will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	protected LRectangle(final IPoint pos, final double width, final double height, final boolean uniqueID) {
		this(pos, pos==null ? null : new LPoint(pos.getX()+width, pos.getY()+height), uniqueID);
	}


	/**
	 * Creates a rectangle.
	 * @param tl The top left point of the rectangle.
	 * @param br The bottom right point of the rectangle.
	 * @param uniqueID True: the rectangle will have a unique ID.
	 */
	protected LRectangle(final IPoint tl, final IPoint br, final boolean uniqueID) {
		super(tl, br, uniqueID);
		frameArc = 0.;
	}


	@Override
	public IRectangle duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IRectangle ? (IRectangle)sh : null;
	}


	@Override
	public double getLineArc() {
		return frameArc;
	}



	@Override
	public boolean isRoundCorner() {
		return frameArc>0;
	}



	@Override
	public void setLineArc(final double arc) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(arc) && arc>=0 && arc<=1)
			frameArc = arc;
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof ILineArcShape)
			setLineArc(((ILineArcShape)sh).getLineArc());
	}
}
