package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

import org.malai.action.Action;
import org.malai.undo.Undoable;

/**
 * This action moves points of IModifiablePointsShape.<br>
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
 * 12/15/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class MovePoint extends Action implements Undoable {
	/** The shape to modify. */
	protected IModifiablePointsShape shape;

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
	protected void doActionBody() {
		final IPoint pt = shape.getPtAt(indexPt);
		tx += newCoord.getX() - pt.getX();
		ty += newCoord.getY() - pt.getY();
		redo();
	}


	@Override
	public boolean canDo() {
		return shape!=null && indexPt>=0 && indexPt<shape.getNbPoints() && GLibUtilities.INSTANCE.isValidPoint(newCoord);
	}


	@Override
	public void undo() {
		IPoint pt = shape.getPtAt(indexPt);
		shape.setPoint(pt.getX()-tx, pt.getY()-ty, indexPt);
		shape.setModified(true);
	}


	@Override
	public void redo() {
		shape.setPoint(newCoord, indexPt);
		shape.setModified(true);
	}


	@Override
	public void flush() {
		super.flush();
		newCoord 	= null;
		shape 		= null;
	}



	@Override
	public boolean hadEffect() {
		return super.hadEffect() && (!LNumber.INSTANCE.equals(tx, 0.) || !LNumber.INSTANCE.equals(ty, 0.));
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	public String getUndoName() {
		return "Move Point";
	}


	/**
	 * @param shape The shape to modify.
	 * @since 3.0
	 */
	public void setShape(final IModifiablePointsShape shape) {
		this.shape = shape;
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
