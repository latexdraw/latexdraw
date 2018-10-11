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
package net.sf.latexdraw.commands.shape;

import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This command moves points of IModifiablePointsShape.
 * @author Arnaud Blouin
 */
public class MovePointShape extends MovePoint implements Undoable {
	/** The shape to modify. */
	private IModifiablePointsShape shape;

	public MovePointShape() {
		super();
	}

	@Override
	protected void doCmdBody() {
		tx += newCoord.getX() - point.getX();
		ty += newCoord.getY() - point.getY();
		redo();
	}

	@Override
	public boolean canDo() {
		return super.canDo() && shape != null && shape.getPoints().indexOf(point) != -1;
	}

	@Override
	public void undo() {
		final int index = shape.getPoints().indexOf(point);
		if(index != -1) {
			// Must use setPoint since other attributes of the shape may depend on the point (e.g. control points).
			shape.setPoint(point.getX() - tx, point.getY() - ty, index);
			shape.setModified(true);
		}
	}

	@Override
	public void redo() {
		final int index = shape.getPoints().indexOf(point);
		if(index != -1) {
			shape.setPoint(newCoord.getX(), newCoord.getY(), index);
			shape.setModified(true);
		}
	}

	@Override
	public void flush() {
		super.flush();
		shape = null;
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.10"); //NON-NLS
	}

	/**
	 * @param sh The shape to modify.
	 */
	public void setShape(final IModifiablePointsShape sh) {
		shape = sh;
	}

	public IModifiablePointsShape getShape() {
		return shape;
	}
}
