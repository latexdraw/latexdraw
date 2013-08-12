package net.sf.latexdraw.glib.models.impl;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;

/**
 * Defines a model of a rectangular shape.<br>
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
 * 02/16/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
abstract class LRectangularShape extends LPositionShape implements IRectangularShape {
	/**
	 * Creates a rectangular shape.
	 * @param tl The top left point of the shape.
	 * @param br The bottom right point of the shape.
	 * @param isUniqueID True: the shape will have a unique ID.
	 */
	protected LRectangularShape(final IPoint tl, final IPoint br, final boolean isUniqueID) {
		super(isUniqueID, new LPoint());

		if(!GLibUtilities.INSTANCE.isValidPoint(tl) || !GLibUtilities.INSTANCE.isValidPoint(br))
			throw new IllegalArgumentException("Invalid point(s)."); //$NON-NLS-1$

		if(tl.getX()>=br.getX() || tl.getY()>=br.getY())//TODO see if the width/height can be equal to 0.
			throw new IllegalArgumentException("The coordinates of the top left and the bottom-right points are not respected."); //$NON-NLS-1$


		points.get(0).setPoint(tl);// The first point already exists.
		points.add(new LPoint(br.getX(), tl.getY()));
		points.add(new LPoint(br));
		points.add(new LPoint(tl.getX(), br.getY()));
	}


	@Override
	public void mirrorHorizontal(final IPoint origin) {
		super.mirrorHorizontal(origin);
		if(getWidth()<0) {
			IPoint tmp = DrawingTK.getFactory().createPoint(points.get(0));
			points.get(0).setPoint(points.get(1));
			points.get(1).setPoint(tmp);
			tmp.setPoint(points.get(2));
			points.get(2).setPoint(points.get(3));
			points.get(3).setPoint(tmp);
		}
	}


	@Override
	public void mirrorVertical(final IPoint origin) {
		super.mirrorVertical(origin);
		if(getHeight()<0) {
			IPoint tmp = DrawingTK.getFactory().createPoint(points.get(0));
			points.get(0).setPoint(points.get(3));
			points.get(3).setPoint(tmp);
			tmp.setPoint(points.get(1));
			points.get(1).setPoint(points.get(2));
			points.get(2).setPoint(tmp);
		}
	}


	@Override
	public double getHeight() {
		return points.get(2).getY() - points.get(0).getY();
	}


	@Override
	public double getWidth() {
		return points.get(1).getX() - points.get(0).getX();
	}



	@Override
	public void setWidth(final double width) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(width) && width>0) {
			double xPos = points.get(points.size()-1).getX() + width;

			points.get(1).setX(xPos);
			points.get(2).setX(xPos);
		}
	}



	@Override
	public void setHeight(final double height) {
		if(GLibUtilities.INSTANCE.isValidCoordinate(height) && height>0) {
			double yPos = points.get(points.size()-1).getY() - height;

			points.get(0).setY(yPos);
			points.get(1).setY(yPos);
		}
	}


	@Override
	public boolean isBordersMovable() {
		return true;
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
