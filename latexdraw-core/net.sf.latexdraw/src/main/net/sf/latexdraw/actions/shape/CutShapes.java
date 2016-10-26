/*
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2016 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-20<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This action cuts the selected shapes.
 */
public class CutShapes extends CopyShapes implements Undoable, Modifying {
	/** The index of the cut shapes. */
	private List<Integer> _positionShapes;


	@Override
	public void doActionBody() {
		// Removing the shapes.
		final List<IShape> drawingSh = selection._drawing().get().getShapes();

		copiedShapes = selection.shapes().stream().map(sh -> sh).collect(Collectors.toList());
		_positionShapes = selection.shapes().stream().map(drawingSh::indexOf).collect(Collectors.toList());

		deleteShapes();
		selection.shapes().clear();
	}


	/**
	 * Delete the shapes from the drawing.
	 * @since 3.0
	 */
	private void deleteShapes() {
		final IDrawing dr = selection._drawing().get();
		copiedShapes.forEach(dr::removeShape);
		dr.setModified(true);
	}


	@Override
	public void redo() {
		deleteShapes();
	}


	@Override
	public void undo() {
		final IDrawing dr = selection.drawing().get();
		IntStream.range(0, _positionShapes.size()).forEach(i -> dr.addShape(copiedShapes.get(i), _positionShapes.get(i)));
		dr.setModified(true);
	}


	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.44");
	}
}
