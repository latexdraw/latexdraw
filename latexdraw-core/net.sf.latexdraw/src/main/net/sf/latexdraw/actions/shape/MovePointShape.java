package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.malai.undo.Undoable;

/**
 * This action moves points of IModifiablePointsShape.<br>
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
public class MovePointShape extends MovePoint implements Undoable {
	/** The shape to modify. */
	protected IModifiablePointsShape shape;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public MovePointShape() {
		super();
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
		return super.canDo() && shape!=null && indexPt<shape.getNbPoints();
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
		shape = null;
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
}
