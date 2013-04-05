package net.sf.latexdraw.glib.models.impl;

import java.util.ArrayList;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of a Bezier curve.<br>
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
class LBezierCurve extends LAbstractCtrlPointShape implements IBezierCurve {
	/** Defines if the curve is closed. */
	protected boolean isClosed;


	/**
	 * Creates a model with no point.
	 * @param uniqueID True: the model will have a unique ID.
	 */
	protected LBezierCurve(final boolean uniqueID) {
		super(uniqueID);
		arrows = new ArrayList<>();
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
	}


	@Override
	public void addPoint(final IPoint pt, final int position) {
		if(GLibUtilities.INSTANCE.isValidPoint(pt) && position>=-1 && position<=points.size())
			if(position==-1 || position==points.size())
				arrows.add(new LArrow(this));
			else
				arrows.add(position, new LArrow(this));
		super.addPoint(pt, position);
	}


	@Override
	public IBezierCurve duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IBezierCurve ? (IBezierCurve)sh : null;
	}


	@Override
	public ILine getArrowLine(final IArrow arrow) {
		if(getNbPoints()<2) return null;
		final int index = arrows.indexOf(arrow);
		ILine line = null;

		if(index==0) line = new LLine(points.get(0), firstCtrlPts.get(0));
		else if(index==getNbPoints()-1) line = new LLine(points.get(points.size()-1), firstCtrlPts.get(points.size()-1));

		return line;
	}


	@Override
	public boolean isClosed() {
		return isClosed;
	}


	@Override
	public void setIsClosed(final boolean isClosed) {
		this.isClosed = isClosed;
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
			isClosed = ((IBezierCurve)sh).isClosed();
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


	@Override
	public boolean shadowFillsShape() {
		return false;
	}


	@Override
	public boolean isShowPtsable() {
		return true;
	}
}
