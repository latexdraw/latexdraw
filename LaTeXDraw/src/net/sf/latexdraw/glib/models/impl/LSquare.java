package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.ISquare;

/**
 * Defines a model of a square.<br>
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
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LSquare extends LRectangle implements ISquare {
	/**
	 * Creates a square at the position (0,0).
	 * @param uniqueID True: the square will have a unique ID.
	 */
	protected LSquare(final boolean uniqueID) {
		this(new LPoint(), 1, uniqueID);
	}


	/**
	 * Creates a square.
	 * @param pos The north-west point of the square.
	 * @param width The width of the square.
	 * @param uniqueID True: the square will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	protected LSquare(final IPoint pos, final double width, final boolean uniqueID) {
		super(pos, width, width, uniqueID);

		update();
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
	public boolean setLeft(final double x) {
		boolean ok = super.setLeft(x);

		if(ok)
			setHeight(getWidth());

		return ok;
	}


	@Override
	public boolean setRight(final double x) {
		boolean ok = super.setRight(x);

		if(ok)
			setHeight(getWidth());

		return ok;
	}


	@Override
	public void scale(final double sx, final double sy, final Position pos) {
		if(pos==null || sx<=0 || !GLibUtilities.INSTANCE.isValidCoordinate(sx))
			throw new IllegalArgumentException();
		scaleXY(getGravityCentre(), sx, sx);
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
	public ISquare duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof ISquare ? (ISquare)sh : null;
	}
}
