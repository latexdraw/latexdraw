package net.sf.latexdraw.glib.models.impl;

import java.util.ArrayList;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of an arc.<br>
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
class LArc extends LEllipse implements IArc {
	/** The style of the arc. */
	protected ArcStyle style;

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

		startAngle 	= 0.;
		endAngle	= 3*Math.PI/2.;
		style		= ArcStyle.ARC;
		arrows 		= new ArrayList<>();
		arrows.add(new LArrow(this));
		arrows.add(new LArrow(this));
	}


	@Override
	public ILine getArrowLine(final IArrow arrow) {
		if(arrows.get(0)==arrow)
			return getTangenteAt(startAngle, startAngle<Math.PI);
		if(arrows.get(1)==arrow)
			return getTangenteAt(endAngle, endAngle>=Math.PI);
		return null;
	}


	@Override
	public boolean isShowPtsable() {
		return true;
	}


	@Override
	public IArc duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IArc ? (IArc)sh : null;
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IArc) {
			final IArc arc 	= (IArc)sh;
			startAngle 		= arc.getAngleStart();
			endAngle 		= arc.getAngleEnd();
			style			= arc.getArcStyle();
		}
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
		final IPoint gravityCentre = getGravityCentre();
		return new LPoint(gravityCentre.getX()+Math.cos(endAngle)*getRy(), gravityCentre.getY()-Math.sin(endAngle)*getRy());
	}


	@Override
	public IPoint getStartPoint() {
		final IPoint gravityCentre = getGravityCentre();
		return new LPoint(gravityCentre.getX()+Math.cos(startAngle)*getRx(), gravityCentre.getY()-Math.sin(startAngle)*getRx());
	}


	@Override
	public ArcStyle getArcStyle() {
		return style;
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
	public void setArcStyle(final ArcStyle type) {
		if(type!=null)
			this.style = type;
	}


	@Override
	public boolean isArrowable() {
		return style.supportArrow();
	}
}
