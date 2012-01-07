package net.sf.latexdraw.glib.models.impl;

import java.awt.geom.GeneralPath;

import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a model of a free hand shape.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
class LFreehand extends LModifiablePointsShape implements IFreehand {
	/**
	 * Creates and initialises a freehand model.
	 * @param pt The first point.
	 * @param uniqueID True: the model will have a unique ID.
	 * @throws IllegalArgumentException If the given point is not valid.
	 * @since 3.0
	 */
	protected LFreehand(final IPoint pt, final boolean uniqueID) {
		super(uniqueID);
//
//		if(!GLibUtilities.INSTANCE.isValidPoint(pt))
//			throw new IllegalArgumentException();
//
//		addPoint(pt);
//		type 		= FreeHandType.CURVES;
//		interval 	= 5;
//		open		= true;
	}


	@Override
	public int getInterval() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public GeneralPath getPath() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public FreeHandType getType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setInterval(final int interval) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setOpen(final boolean open) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setType(final FreeHandType type) {
		// TODO Auto-generated method stub

	}


	@Override
	public IFreehand duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IFreehand ? (IFreehand)sh : null;
	}


	@Override
	public boolean isArrowable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isBordersMovable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isDbleBorderable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isFillable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isInteriorStylable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isLineStylable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isShadowable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isShowPtsable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isThicknessable() {
		// TODO Auto-generated method stub
		return false;
	}

}
