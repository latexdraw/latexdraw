package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a model of an arc.<br>
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
class LArc extends LEllipse implements IArc {//TODO add to the factory.
	protected ArcType type;


	/**
	 * Creates an arc with radius 1.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @since 3.0
	 */
	protected LArc(final boolean isUniqueID) {
		this(new LPoint(), 1, 1, null, isUniqueID);
	}


	/**
	 * The main constructor.
	 * @param pt The centre of the elliptic arc.
	 * @param a The horizontal radius.
	 * @param b The vertical radius.
	 * @param type The kind of arc.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If a or b is not valid.
	 * @since 3.0
	 */
	protected LArc(final IPoint pt, final double a, final double b, final ArcType type, final boolean isUniqueID) {
		super(isUniqueID);
//		super(pt, a, b, isUniqueID);
//
//		angleStart	= 0;
//		angleEnd	= 3.*Math.PI/2.;
		this.type	= type==null ? ArcType.ARC : type;
//
//		update();
	}


	@Override
	public double getAngleEnd() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getAngleStart() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public IPoint getEndPoint() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IPoint getStartPoint() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArcType getType() {
		return type;
	}


	@Override
	public void setAngleEnd(final double angleEnd) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setAngleStart(final double angleStart) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setType(final ArcType type) {
		if(type!=null)
			this.type = type;
	}


	@Override
	public boolean isArrowable() {
		return getType()==ArcType.ARC;
	}
}
