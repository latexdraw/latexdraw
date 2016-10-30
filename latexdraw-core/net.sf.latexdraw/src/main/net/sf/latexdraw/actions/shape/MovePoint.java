/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.models.GLibUtilities;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;
import org.malai.action.ActionImpl;

/**
 * This abstract action moves any kind of points.
 */
public abstract class MovePoint extends ActionImpl {
	/** The index of the point to move. */
	protected int indexPt;

	/** The new coordinates of the point to move. */
	protected IPoint newCoord;

	/** The X-translation factor performed by the action. */
	protected double tx;

	/** The Y-translation factor performed by the action. */
	protected double ty;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	protected MovePoint() {
		super();
		tx = 0;
		ty = 0;
	}


	@Override
	public boolean canDo() {
		return indexPt >= 0 && GLibUtilities.isValidPoint(newCoord);
	}


	@Override
	public void flush() {
		super.flush();
		newCoord = null;
	}


	@Override
	public boolean hadEffect() {
		return super.hadEffect() && (!LNumber.equalsDouble(tx, 0.) || !LNumber.equalsDouble(ty, 0.));
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	/**
	 * @param index The index of the point to move.
	 * @since 3.0
	 */
	public void setIndexPt(final int index) {
		indexPt = index;
	}


	/**
	 * @param coord The new coordinates of the point to move.
	 * @since 3.0
	 */
	public void setNewCoord(final IPoint coord) {
		newCoord = coord;
	}
}
