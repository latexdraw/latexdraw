package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

import org.malai.action.Action;

/**
 * This abstract action moves any kind of points.<br>
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
 * 12/15/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class MovePoint extends Action {
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
	public MovePoint() {
		super();
		tx = 0;
		ty = 0;
	}


	@Override
	public boolean canDo() {
		return indexPt>=0 && GLibUtilities.INSTANCE.isValidPoint(newCoord);
	}


	@Override
	public void flush() {
		super.flush();
		newCoord 	= null;
	}


	@Override
	public boolean hadEffect() {
		return super.hadEffect() && (!LNumber.INSTANCE.equals(tx, 0.) || !LNumber.INSTANCE.equals(ty, 0.));
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	/**
	 * @param indexPt The index of the point to move.
	 * @since 3.0
	 */
	public void setIndexPt(final int indexPt) {
		this.indexPt = indexPt;
	}


	/**
	 * @param newCoord The new coordinates of the point to move.
	 * @since 3.0
	 */
	public void setNewCoord(final IPoint newCoord) {
		this.newCoord = newCoord;
	}
}
