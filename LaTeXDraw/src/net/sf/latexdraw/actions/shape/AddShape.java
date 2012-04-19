package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;

import org.malai.undo.Undoable;

/**
 * This action adds a shape to a drawing.<br>
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
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class AddShape extends ShapeAction<IShape> implements Undoable, Modifying {
	/**
	 * Creates the action.
	 */
	public AddShape() {
		super();
	}


	@Override
	protected void doActionBody() {
		drawing.addShape(shape);
		drawing.setModified(true);
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getStringOthers("UndoRedoManager.create"); //$NON-NLS-1$
	}


	@Override
	public void redo() {
		doActionBody();
	}


	@Override
	public void undo() {
		drawing.removeShape(shape);
		drawing.setModified(true);
	}
}
