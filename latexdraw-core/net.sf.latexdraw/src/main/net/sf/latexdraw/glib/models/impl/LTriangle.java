package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.ITriangle;

/**
 * Defines a model of a triangle.<br>
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
class LTriangle extends LRectangularShape implements ITriangle {
	/**
	 * Creates a triangle at the position (0,0).
	 * @param uniqueID True: the triangle will have a unique ID.
	 */
	protected LTriangle(final boolean uniqueID) {
		this(new LPoint(), 1, 1, uniqueID);
	}


	/**
	 * Creates a triangle.
	 * @param pos The north-west point of the triangle.
	 * @param width The width of the triangle.
	 * @param height The height of the triangle.
	 * @param uniqueID True: the triangle will have a unique ID.
	 * @throws IllegalArgumentException If the width or the height is not valid.
	 */
	protected LTriangle(final IPoint pos, final double width, final double height, final boolean uniqueID) {
		super(pos, pos==null ? null : new LPoint(pos.getX()+width, pos.getY()+height), uniqueID);
	}
	
	
	@Override
	public ITriangle duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof ITriangle ? (ITriangle)sh : null;
	}
}

