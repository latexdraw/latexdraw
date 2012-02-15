package net.sf.latexdraw.actions;

import org.malai.undo.Undoable;

import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.lang.LangTool;

/**
 * This action separates joined shapes.<br>
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
 * 02/15/2012<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class SeparateShapes extends ShapeAction<IGroup> implements Undoable {
	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public SeparateShapes() {
		super();
	}


	@Override
	public boolean canDo() {
		return super.canDo() && !shape.isEmpty();
	}



	@Override
	protected void doActionBody() {
		final int position = drawing.getShapes().indexOf(shape);
		final int insertPos = position>=(drawing.size()-1) ? -1 : position;
		drawing.removeShape(position);

		for(int i=0, size=shape.size(); i<size; i++)
			drawing.addShape(shape.getShapeAt(i), insertPos);

		drawing.setModified(true);
	}


	@Override
	public void undo() {
		final int position = drawing.getShapes().indexOf(shape.getShapeAt(0));

		for(int i=0, size=shape.size(); i<size; i++)
			drawing.removeShape(position);

		drawing.addShape(shape, position>=drawing.size() ? -1 : position);
		drawing.setModified(true);
	}


	@Override
	public void redo() {
		doActionBody();
	}


	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getStringOthers("UndoRedoManager.seperate");
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}
}
