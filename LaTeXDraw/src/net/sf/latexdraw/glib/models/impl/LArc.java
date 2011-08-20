package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
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
class LArc extends LEllipse implements IArc {
	/** The type of the arc. */
	protected ArcType type;

	/** The start angle of the arc. In radian. */
	protected double startAngle;

	/** The end angle of the arc. In radian. */
	protected double endAngle;


	/**
	 * Creates an arc with radius 1.
	 * @param isUniqueID True: the model will have a unique ID.
	 * @since 3.0
	 */
	protected LArc(final boolean isUniqueID) {
		this(new LPoint(10, 10), new LPoint(20, 20), isUniqueID);
	}


	/**
	 * Creates an arc.
	 * @param tl The top-left point of the arc.
	 * @param br The bottom-right point of the arc.
	 * @param isUniqueID True: the arc will have a unique ID.
	 * @throws IllegalArgumentException If a or b is not valid.
	 */
	protected LArc(final IPoint tl, final IPoint br, final boolean isUniqueID) {
		super(tl, br, isUniqueID);

		startAngle 	= 3*Math.PI/2.;
		endAngle	= 0.;
		type		= ArcType.ARC;

		update();
	}


	@Override
	public double getAngleEnd() {
		return endAngle;
	}


	@Override
	public double getAngleStart() {
		return startAngle;
	}


	@Override
	public IPoint getEndPoint() {
		return new LPoint(gravityCentre.getX()+Math.cos(endAngle)*getWidth(), gravityCentre.getY()-Math.sin(endAngle)*getHeight());
	}


	@Override
	public IPoint getStartPoint() {
		return new LPoint(gravityCentre.getX()+Math.cos(startAngle)*getWidth(), gravityCentre.getY()-Math.sin(startAngle)*getHeight());
	}


	@Override
	public ArcType getType() {
		return type;
	}


	@Override
	public void setAngleEnd(final double angleEnd) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(angleEnd))
			this.endAngle = angleEnd;
	}


	@Override
	public void setAngleStart(final double angleStart) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(angleStart))
			this.startAngle = angleStart;
	}


	@Override
	public void setType(final ArcType type) {
		if(type!=null)
			this.type = type;
	}


	@Override
	public boolean isArrowable() {
		return false; //TODO getType()==ArcType.ARC;
	}
}
