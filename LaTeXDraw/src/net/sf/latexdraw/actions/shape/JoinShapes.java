package net.sf.latexdraw.actions.shape;

import java.util.List;

import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;

import org.malai.undo.Undoable;

/**
 * This action joins shapes.<br>
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
 * 02/14/2012<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class JoinShapes extends MultiShapesAction implements Undoable, Modifying {
	/** The index of the joined shapes into the original list. */
	protected int[] positionShapes;

	/** The added group of shapes. */
	protected IGroup addedGroup;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public JoinShapes() {
		super();
	}


	@Override
	protected void doActionBody() {
		final List<IShape> drawingSh = drawing.getShapes();
		positionShapes = new int[shapes.size()];
		addedGroup = DrawingTK.getFactory().createGroup(true);

		for(int i=0, size=shapes.size(); i<size; i++)
			positionShapes[i] = drawingSh.indexOf(shapes.get(i));

		joinShapes();
	}


	private void joinShapes() {
		for(final IShape sh : shapes) {
			drawing.removeShape(sh);
			addedGroup.addShape(sh);
		}

		drawing.addShape(addedGroup);
		drawing.setModified(true);
	}


	@Override
	public void undo() {
		drawing.removeShape(addedGroup);
		addedGroup.clear();
		for(int i=0; i<positionShapes.length; i++)
			drawing.addShape(shapes.get(i), positionShapes[i]);
	}


	@Override
	public void flush() {
		super.flush();
		if(addedGroup!=null) {
			addedGroup.clear();
			addedGroup = null;
		}
	}


	@Override
	public void redo() {
		joinShapes();
	}


	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getStringOthers("UndoRedoManager.join");
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}
}
