/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
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
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command separates joined shapes.
 * @author Arnaud Blouin
 */
public class SeparateShapes extends ShapeCmdImpl<Group> implements Modifying, Undoable {
	/** The drawing that will be handled by the command. */
	private final @NotNull Drawing drawing;
	private boolean mementoModified;


	public SeparateShapes(final @NotNull Drawing drawing, final @NotNull Group gp) {
		super(gp);
		this.drawing = drawing;
	}

	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
	}

	@Override
	public boolean canDo() {
		return !shape.isEmpty() && drawing.getShapes().contains(shape);
	}

	@Override
	protected void doCmdBody() {
		final int position = drawing.getShapes().indexOf(shape);
		final int insertPos = position >= drawing.size() - 1 ? -1 : position;
		drawing.removeShape(position);

		if(position >= drawing.size() - 1) {
			shape.getShapes().forEach(s -> drawing.addShape(s));
		}else {
			// Have to go through the list in the reverse order to insert the shapes in the good order
			final List<Shape> shapes = new ArrayList<>(shape.getShapes());
			Collections.reverse(shapes);
			shapes.forEach(s -> drawing.addShape(s, insertPos));
		}

		drawing.setModified(true);
	}

	@Override
	public void undo() {
		final int position = shape.getShapeAt(0).map(s -> drawing.getShapes().indexOf(s)).orElse(-1);
		IntStream.range(0, shape.getShapes().size()).forEach(i -> drawing.removeShape(position));
		drawing.addShape(shape, position);
		drawing.setModified(mementoModified);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("seperate");
	}
}
