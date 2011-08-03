package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a model of a rounded arc.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
class LCircleArc extends LArc implements ICircleArc {//TODO add to factory.
	/**
	 * Creates a round arc.
	 * @param pt The centre of the arc.
	 * @param radius The radius.
	 * @param type The kind of arc.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the radius is not valid.
	 */
	protected LCircleArc(final IPoint pt, final double radius, final ArcType type, final boolean isUniqueID) {
		super(pt, radius, radius, type, isUniqueID);

		update();
	}


	/**
	 * Creates a circle arc with a 1 radius.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @since 3.0
	 */
	protected LCircleArc(final boolean isUniqueID) {
		this(new LPoint(), 1, null, isUniqueID);
	}


	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

}
