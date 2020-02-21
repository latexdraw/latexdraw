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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command removes shapes from a drawing.
 * @author Arnaud Blouin
 */
public class DeleteShapes extends DrawingCmdImpl implements Undoable, Modifying {
	/** The index of the deleted shapes into the original list. */
	private @NotNull List<Integer> positionShapes;
	/** The shapes to handle. */
	private final @NotNull List<Shape> shapes;
	private boolean mementoModified;


	public DeleteShapes(final @NotNull Drawing drawing, final @NotNull List<Shape> toDelete) {
		super(drawing);
		shapes = toDelete
			.stream()
			.sorted(Comparator.comparingInt(sh -> drawing.getShapes().indexOf(sh)).reversed())
			.collect(Collectors.toList());
		positionShapes = List.of();
	}

	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
		positionShapes = shapes
			.stream()
			.mapToInt(sh -> drawing.getShapes().indexOf(sh))
			.boxed()
			.collect(Collectors.toList());
	}

	@Override
	protected void doCmdBody() {
		shapes.forEach(sh -> drawing.removeShape(sh));
		drawing.setModified(true);
	}

	@Override
	public boolean canDo() {
		return !shapes.isEmpty();
	}

	@Override
	public void undo() {
		for(int i = positionShapes.size() - 1; i >= 0; i--) {
			drawing.addShape(shapes.get(i), positionShapes.get(i));
		}
		drawing.setModified(mementoModified);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.5");
	}
}
