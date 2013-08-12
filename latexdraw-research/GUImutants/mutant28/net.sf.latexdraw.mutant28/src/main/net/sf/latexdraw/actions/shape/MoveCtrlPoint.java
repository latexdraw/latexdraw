package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IControlPointShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.malai.undo.Undoable;

/**
 * This action moves control points.<br>
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
public class MoveCtrlPoint extends MovePoint implements Undoable {
	/** The control point shape to modify. */
	protected IControlPointShape shape;

	/** True: it is a first control point which is moved. */
	protected boolean isFirstCtrlPt;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public MoveCtrlPoint() {
		super();
	}


	@Override
	protected void doActionBody() {
		final IPoint pt = getPoint();
		tx += newCoord.getX() - pt.getX();
		ty += newCoord.getY() - pt.getY();
		redo();
	}


	/**
	 * @return The first or second control point moved.
	 */
	protected IPoint getPoint() {
		return isFirstCtrlPt ? shape.getFirstCtrlPtAt(indexPt) : shape.getSecondCtrlPtAt(indexPt);
	}


	@Override
	public void flush() {
		super.flush();
		shape = null;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && shape!=null && indexPt<shape.getNbPoints();
	}


	protected void move(final IPoint firstPt, final IPoint sndPt) {
		shape.setXFirstCtrlPt(firstPt.getX(), indexPt);
		shape.setYFirstCtrlPt(firstPt.getY(), indexPt);
		shape.setXSecondCtrlPt(sndPt.getX(), indexPt);
		shape.setYSecondCtrlPt(sndPt.getY(), indexPt);
		shape.setModified(true);
	}


	@Override
	public void undo() {
		final IPoint pt = DrawingTK.getFactory().createPoint(getPoint());
		pt.translate(-tx, -ty);

		if(isFirstCtrlPt)
			move(pt, pt.centralSymmetry(shape.getPtAt(indexPt)));
		else
			move(pt.centralSymmetry(shape.getPtAt(indexPt)), pt);
	}


	@Override
	public void redo() {
		if(isFirstCtrlPt)
			move(newCoord, newCoord.centralSymmetry(shape.getPtAt(indexPt)));
		else
			move(newCoord.centralSymmetry(shape.getPtAt(indexPt)), newCoord);
	}


	@Override
	public String getUndoName() {
		return "Move Control Point";
	}


	/**
	 * @param isFirstCtrlPt True: it is a first control point which is moved.
	 * @since 3.0
	 */
	public void setIsFirstCtrlPt(final boolean isFirstCtrlPt) {
		this.isFirstCtrlPt = isFirstCtrlPt;
	}


	/**
	 * @param shape The shape to modify.
	 * @since 3.0
	 */
	public void setShape(final IControlPointShape shape) {
		this.shape = shape;
	}
}
