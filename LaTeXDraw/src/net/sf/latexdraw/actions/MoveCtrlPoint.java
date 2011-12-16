package net.sf.latexdraw.actions;

import org.malai.undo.Undoable;

import net.sf.latexdraw.glib.models.interfaces.IControlPointShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

/**
 * This action moves control points.<br>
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
public class MoveCtrlPoint extends MovePoint implements Undoable {
	/** The control point shape to modify. */
	protected IControlPointShape shape;

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


	@Override
	public void undo() {
		final IPoint pt = getPoint();
		if(isFirstCtrlPt) {
			shape.setXFirstCtrlPt(pt.getX()-tx, indexPt);
			shape.setYFirstCtrlPt(pt.getY()-ty, indexPt);
		}
		else {
			shape.setXSecondCtrlPt(pt.getX()-tx, indexPt);
			shape.setYSecondCtrlPt(pt.getY()-ty, indexPt);
		}
		shape.setModified(true);
	}


	@Override
	public void redo() {
		IPoint p1;
		IPoint p2;
		if(isFirstCtrlPt) {
			p1 = newCoord;
			p2 = newCoord.centralSymmetry(shape.getPtAt(indexPt));
		}
		else {
			p1 = newCoord.centralSymmetry(shape.getPtAt(indexPt));
			p2 = newCoord;
		}

		shape.setXFirstCtrlPt(p1.getX(), indexPt);
		shape.setYFirstCtrlPt(p1.getY(), indexPt);
		shape.setXSecondCtrlPt(p2.getX(), indexPt);
		shape.setYSecondCtrlPt(p2.getY(), indexPt);
		shape.setModified(true);
	}


	@Override
	public String getUndoName() {
		return "Move Control Point";
	}


	/**
	 * @param isFirstCtrlPt the isFirstCtrl to set.
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
