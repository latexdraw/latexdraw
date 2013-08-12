package net.sf.latexdraw.glib.models.impl;

import java.util.ArrayList;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a view of a polyline.<br>
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
class LPolyline extends LPolygon implements IPolyline {
	/**
	 * Creates a model with no point.
	 * @param uniqueID True: the shape will have a unique ID.
	 */
	protected LPolyline(final boolean uniqueID) {
		super(uniqueID);
		arrows = new ArrayList<>();
	}



	/**
	 * Creates a model with two points.
	 * @param uniqueID True: the shape will have a unique ID.
	 */
	protected LPolyline(final IPoint point, final IPoint point2, final boolean uniqueID) {
		this(uniqueID);

		if(point==null || point2==null)
			throw new IllegalArgumentException();

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
	public IPolyline duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IPolyline ? (IPolyline)sh : null;
	}


	@Override
	public ILine getArrowLine(final IArrow arrow) {
		if(getNbPoints()<2) return null;
		final int index = arrows.indexOf(arrow);
		ILine line = null;

		if(index==0) line = new LLine(points.get(0), points.get(1));
		else if(index==getNbPoints()-1) line = new LLine(points.get(points.size()-1), points.get(points.size()-2));

		return line;
	}


	@Override
	public boolean isFillable() {
		return getNbPoints()>2;
	}



	@Override
	public boolean isArrowable() {
		return true;
	}


	@Override
	public boolean shadowFillsShape() {
		return false;
	}
}
