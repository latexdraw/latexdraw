package net.sf.latexdraw.actions;

import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import fr.eseo.malai.undo.Undoable;

/**
 * This action deletes shapes.<br>
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
 * 01/07/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class DeleteShape extends MultiShapesAction implements Undoable {
	/** The index of the deleted shapes into the original list. */
	protected int[] positionShapes;
	
	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public DeleteShape() {
		super();
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void doActionBody() {
		positionShapes = new int[shapes.size()];
		List<IShape> drawingSh = drawing.getShapes();
		
		for(int i=0, size=shapes.size(); i<size; i++)
			positionShapes[i] = drawingSh.indexOf(shapes.get(i));

		deleteShapes();
	}
	
	
	/**
	 * Delete the shapes from the drawing. 
	 * @since 3.0
	 */
	private void deleteShapes() {
		for(final IShape sh : shapes)
			drawing.removeShape(sh);
		drawing.setModified(true);
	}

	
	@Override
	public boolean canDo() {
		return super.canDo() && !shapes.isEmpty();
	}
	

	@Override
	public void undo() {
		for(int i=0; i<positionShapes.length; i++)
			drawing.addShape(shapes.get(i), positionShapes[i]);	
		drawing.setModified(true);
	}


	@Override
	public void redo() {
		deleteShapes();
	}


	@Override
	public String getUndoName() {
		return "delete";
	}
}
