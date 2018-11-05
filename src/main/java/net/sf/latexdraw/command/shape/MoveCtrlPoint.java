/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command.shape;

import java.util.ResourceBundle;
import net.sf.latexdraw.model.api.shape.ControlPointShape;
import net.sf.latexdraw.model.api.shape.Point;
import org.malai.undo.Undoable;

/**
 * This command moves control points.
 * @author Arnaud Blouin
 */
public class MoveCtrlPoint extends MovePoint implements Undoable {
	/** The control point shape to modify. */
	private ControlPointShape shape;

	/** True: it is a first control point which is moved. */
	private boolean isFirstCtrlPt;

	public MoveCtrlPoint() {
		super();
	}

	@Override
	protected void doCmdBody() {
		tx += newCoord.getX() - point.getX();
		ty += newCoord.getY() - point.getY();
		redo();
	}

	/**
	 * @return The index of the control point to move.
	 */
	private int getIndexCtrlPt() {
		return isFirstCtrlPt ? shape.getFirstCtrlPts().indexOf(point) : shape.getSecondCtrlPts().indexOf(point);
	}

	@Override
	public void flush() {
		super.flush();
		shape = null;
	}

	@Override
	public boolean canDo() {
		return shape != null && getIndexCtrlPt() != -1 && super.canDo();
	}

	private void move(final Point firstPt, final Point sndPt) {
		final int indexPt = getIndexCtrlPt();
		shape.setXFirstCtrlPt(firstPt.getX(), indexPt);
		shape.setYFirstCtrlPt(firstPt.getY(), indexPt);
		shape.setXSecondCtrlPt(sndPt.getX(), indexPt);
		shape.setYSecondCtrlPt(sndPt.getY(), indexPt);
		shape.setModified(true);
	}

	@Override
	public void undo() {
		point.translate(-tx, -ty);

		if(isFirstCtrlPt) {
			move(point, point.centralSymmetry(shape.getPtAt(getIndexCtrlPt())));
		}else {
			move(point.centralSymmetry(shape.getPtAt(getIndexCtrlPt())), point);
		}
	}

	@Override
	public void redo() {
		if(isFirstCtrlPt) {
			move(newCoord, newCoord.centralSymmetry(shape.getPtAt(getIndexCtrlPt())));
		}else {
			move(newCoord.centralSymmetry(shape.getPtAt(getIndexCtrlPt())), newCoord);
		}
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.9"); //NON-NLS
	}

	/**
	 * @param val True: it is a first control point which is moved.
	 */
	public void setIsFirstCtrlPt(final boolean val) {
		isFirstCtrlPt = val;
	}

	/**
	 * @param sh The shape to modify.
	 */
	public void setShape(final ControlPointShape sh) {
		shape = sh;
	}
}
