package net.sf.latexdraw.glib.models.impl;

import java.util.ArrayList;

import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of a Bezier curve.<br>
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
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LBezierCurve extends LAbstractCtrlPointShape implements IBezierCurve {
	/** Define the shape of the closing path. */
	protected CloseType closeType;


	/**
	 * Creates a model with no point.
	 * @param uniqueID True: the model will have a unique ID.
	 */
	protected LBezierCurve(final boolean uniqueID) {
		super(uniqueID);

		closeType	= CloseType.CURVE;
		arrows		= new ArrayList<IArrow>();
		arrows.add(new LArrow(this));
		arrows.add(new LArrow(this));
		update();
	}


	/**
	 * Creates a bezier curve with two points.
	 * @param point The first point of the curve.
	 * @param point2 The second point of the curve.
	 * @param uniqueID uniqueID True: the model will have a unique ID.
	 */
	protected LBezierCurve(final IPoint point, final IPoint point2, final boolean uniqueID) {
		this(uniqueID);

		addPoint(point);
		addPoint(point2);
		update();
	}


	@Override
	public boolean isClosed() {
		return closeType!=CloseType.NONE;
	}


	@Override
	public void setClosed(final CloseType closeType) {
		if(closeType!=null)
			this.closeType = closeType;
	}


	@Override
	public boolean isArrowable() {
		return !isClosed();
	}


	@Override
	public boolean isDbleBorderable() {
		return true;
	}


	@Override
	public CloseType getCloseType() {
		return closeType;
	}


	@Override
	public boolean isFillable() {
		return true;
	}


	@Override
	public boolean isInteriorStylable() {
		return true;
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IBezierCurve)
			closeType = ((IBezierCurve)sh).getCloseType();
	}


	@Override
	public boolean isLineStylable() {
		return true;
	}


	@Override
	public boolean isShadowable() {
		return true;
	}


	@Override
	public boolean isThicknessable() {
		return true;
	}
}
