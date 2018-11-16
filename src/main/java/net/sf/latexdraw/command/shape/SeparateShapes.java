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
package net.sf.latexdraw.command.shape;

import java.util.ResourceBundle;
import java.util.stream.IntStream;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import org.jetbrains.annotations.NotNull;
import org.malai.undo.Undoable;

/**
 * This command separates joined shapes.
 * @author Arnaud Blouin
 */
public class SeparateShapes extends ShapeCmdImpl<Group> implements Modifying, Undoable {
	/** The drawing that will be handled by the command. */
	private final @NotNull Drawing drawing;


	public SeparateShapes(final @NotNull Drawing drawing, final @NotNull Group gp) {
		super(gp);
		this.drawing = drawing;
	}

	@Override
	public boolean canDo() {
		return !shape.isEmpty();
	}

	@Override
	protected void doCmdBody() {
		final int position = drawing.getShapes().indexOf(shape);
		final int insertPos = position >= drawing.size() - 1 ? -1 : position;
		drawing.removeShape(position);
		shape.getShapes().forEach(s -> drawing.addShape(s, insertPos));
		drawing.setModified(true);
	}

	@Override
	public void undo() {
		final int position = shape.getShapeAt(0).map(s -> drawing.getShapes().indexOf(s)).orElse(-1);
		final int addPosition = position >= drawing.size() ? -1 : position;
		IntStream.range(0, shape.getShapes().size()).forEach(i -> drawing.removeShape(position));
		drawing.addShape(shape, addPosition);
		drawing.setModified(true);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("UndoRedoManager.seperate");
	}
}
