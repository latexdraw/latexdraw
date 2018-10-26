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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.undo.Undoable;

/**
 * This command cuts the selected shapes.
 * @author Arnaud Blouin
 */
public class CutShapes extends CopyShapes implements Undoable, Modifying {
	/** The index of the cut shapes. */
	private List<Integer> positionShapes;


	public CutShapes(final SelectShapes selection) {
		super(selection);
	}

	@Override
	public void doCmdBody() {
		// Removing the shapes.
		selection.getDrawing().ifPresent(dr -> {
			final List<IShape> drawingSh = dr.getShapes();

			copiedShapes = new ArrayList<>(selection.getShapes());
			positionShapes = selection.getShapes().stream().map(drawingSh::indexOf).collect(Collectors.toList());

			deleteShapes();
			selection.getShapes().clear();
		});
	}

	/**
	 * Delete the shapes from the drawing.
	 */
	private void deleteShapes() {
		selection.getDrawing().ifPresent(dr -> {
			copiedShapes.forEach(dr::removeShape);
			dr.setModified(true);
		});
	}

	@Override
	public void redo() {
		deleteShapes();
	}

	@Override
	public void undo() {
		selection.getDrawing().ifPresent(dr -> {
			IntStream.range(0, positionShapes.size()).forEach(i -> dr.addShape(copiedShapes.get(i), positionShapes.get(i)));
			dr.setModified(true);
		});
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("LaTeXDrawFrame.44");
	}
}
