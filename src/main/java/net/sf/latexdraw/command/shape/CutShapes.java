/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command.shape;

import io.github.interacto.undo.Undoable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command cuts the selected shapes.
 * @author Arnaud Blouin
 */
public class CutShapes extends CopyShapes implements Undoable, Modifying {
	/** The index of the cut shapes. */
	private List<Integer> positionShapes;


	public CutShapes(final @NotNull Optional<SelectShapes> selection) {
		super(selection);
	}

	@Override
	public void doCmdBody() {
		selection.ifPresent(sel -> {
			// Removing the shapes.
			final List<Shape> drawingSh = sel.getDrawing().getShapes();

			copiedShapes = new ArrayList<>(sel.getShapes());
			positionShapes = sel.getShapes().stream().map(drawingSh::indexOf).collect(Collectors.toList());

			deleteShapes();
			sel.getShapes().clear();
		});
	}

	/**
	 * Delete the shapes from the drawing.
	 */
	private void deleteShapes() {
		selection.ifPresent(sel -> {
			copiedShapes.forEach(s -> sel.getDrawing().removeShape(s));
			sel.getDrawing().setModified(true);
		});
	}

	@Override
	public void redo() {
		deleteShapes();
	}

	@Override
	public void undo() {
		selection.ifPresent(sel -> {
			IntStream.range(0, positionShapes.size()).forEach(i -> sel.getDrawing().addShape(copiedShapes.get(i), positionShapes.get(i)));
			sel.getDrawing().setModified(true);
		});
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("LaTeXDrawFrame.44");
	}
}
