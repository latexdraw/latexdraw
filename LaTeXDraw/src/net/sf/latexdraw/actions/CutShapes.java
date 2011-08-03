package net.sf.latexdraw.actions;

import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.undo.Undoable;

/**
 * This action cuts the selected shapes.<br>
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
 * 06/03/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class CutShapes extends CopyShapes implements Undoable {
	/** The index of the cut shapes. */
	protected int[] positionShapes;

	/** The drawing where the shapes are removed. Computed attribute from the selection action. */
	private IDrawing drawing;


	@Override
	protected void doActionBody() {
		// Removing the shapes.
		List<IShape> selectedShapes = selection.getShapes();
		List<IShape> drawingSh 		= selection.drawing.getShapes();
		IShape sh;
		drawing			= selection.drawing;
		copiedShapes 	= new ArrayList<IShape>();
		positionShapes 	= new int[selectedShapes.size()];

		for(int i=0, size=selectedShapes.size(); i<size; i++) {
			sh = selectedShapes.get(i);
			positionShapes[i] = drawingSh.indexOf(sh);
			copiedShapes.add(sh);
		}

		deleteShapes();
		selection.shapes.clear();
	}


	/**
	 * Delete the shapes from the drawing.
	 * @since 3.0
	 */
	private void deleteShapes() {
		for(final IShape sh : copiedShapes)
			drawing.removeShape(sh);

		drawing.setModified(true);
	}


	@Override
	public void redo() {
		deleteShapes();
	}


	@Override
	public void undo() {
		for(int i=0; i<positionShapes.length; i++)
			drawing.addShape(copiedShapes.get(i), positionShapes[i]);

		drawing.setModified(true);
	}

	@Override
	public String getUndoName() {
		return LResources.LABEL_CUT;
	}
}

