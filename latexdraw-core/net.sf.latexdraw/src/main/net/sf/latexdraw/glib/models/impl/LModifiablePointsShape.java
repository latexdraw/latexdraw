package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * Defines a model of a shape that contains points that can be modified.<br>
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
 * 02/14/2008<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
abstract class LModifiablePointsShape extends LShape implements IModifiablePointsShape {
	/**
	 * Creates the shape.
	 * @param uniqueID True: the model will have a unique ID.
	 */
	public LModifiablePointsShape(final boolean uniqueID) {
		super(uniqueID);
	}


	@Override
	public void rotate(final IPoint point, final double angle) {
		setRotationAngle(rotationAngle+angle);
	}




	@Override
	public void setRotationAngle(double rotationAngle) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(rotationAngle)) {
			final double diff = rotationAngle-this.rotationAngle;
			super.setRotationAngle(rotationAngle);

			final IPoint gc = getGravityCentre();

			for(IPoint pt : points)
				pt.setPoint(pt.rotatePoint(gc, diff));
		}
	}


	@Override
	public boolean setPoint(final IPoint p, final int position) {
		if(p==null)
			return false;
		return setPoint(p.getX(), p.getY(), position);
	}



	@Override
	public boolean setPoint(final double x, final double y, final int position) {
		if(!GLibUtilities.INSTANCE.isValidPoint(x, y) || position<-1 || position>points.size() || points.isEmpty())
			return false;

		IPoint p = position==-1 ? points.get(points.size()-1) : points.get(position);
		p.setPoint(x, y);

		return true;
	}



	@Override
	public boolean removePoint(final IPoint pt) {
		if(pt==null) return false;
		final int ind = points.indexOf(pt);
		if(ind!=-1) return removePoint(ind)!=null;
		return false;
	}


	@Override
	public IPoint removePoint(final int position) {
		if(position>=-1 && position<points.size()) {
			if(isArrowable())
				arrows.remove(position==-1 ? points.size()-1 : position);
			return points.remove(position==-1 ? points.size()-1 : position);
		}

		return null;
	}


	@Override
	public IPoint replacePoint(final IPoint pt, final int position) {
		if(!GLibUtilities.INSTANCE.isValidPoint(pt) || points.contains(pt) || position<-1 || position>points.size())
			return null;

		IPoint pRemoved = points.remove(position==-1 ? points.size()-1 : position);

		if(position==-1 || points.isEmpty())
			points.add(pt);
		else
			points.add(position, pt);

		return pRemoved;
	}


	@Override
	public void addPoint(final IPoint pt) {
		addPoint(pt, -1);
	}


	@Override
	public void addPoint(final IPoint pt, final int position) {
		if(GLibUtilities.INSTANCE.isValidPoint(pt) && position>=-1 && position<=points.size())
			if(position==-1 || position==points.size())
				points.add(pt);
			else
				points.add(position, pt);
	}
}
