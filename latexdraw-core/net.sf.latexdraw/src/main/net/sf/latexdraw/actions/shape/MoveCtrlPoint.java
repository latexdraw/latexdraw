/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This action moves control points.
 * @author Arnaud Blouin
 */
public class MoveCtrlPoint extends MovePoint implements Undoable {
	/** The control point shape to modify. */
	protected IControlPointShape shape;

	/** True: it is a first control point which is moved. */
	protected boolean isFirstCtrlPt;


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
		return super.canDo() && shape != null && indexPt < shape.getNbPoints();
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
		final IPoint pt = ShapeFactory.INST.createPoint(getPoint());
		pt.translate(-tx, -ty);

		if(isFirstCtrlPt) move(pt, pt.centralSymmetry(shape.getPtAt(indexPt)));
		else move(pt.centralSymmetry(shape.getPtAt(indexPt)), pt);
	}


	@Override
	public void redo() {
		if(isFirstCtrlPt) move(newCoord, newCoord.centralSymmetry(shape.getPtAt(indexPt)));
		else move(newCoord.centralSymmetry(shape.getPtAt(indexPt)), newCoord);
	}


	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.9"); //$NON-NLS-1$
	}


	/**
	 * @param val True: it is a first control point which is moved.
	 * @since 3.0
	 */
	public void setIsFirstCtrlPt(final boolean val) {
		isFirstCtrlPt = val;
	}


	/**
	 * @param sh The shape to modify.
	 * @since 3.0
	 */
	public void setShape(final IControlPointShape sh) {
		shape = sh;
	}
}
