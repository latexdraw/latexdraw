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

import io.github.interacto.undo.Undoable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapesCmd;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command joins shapes.
 * @author Arnaud Blouin
 */
public class JoinShapes extends DrawingCmdImpl implements ShapesCmd, Undoable, Modifying {
	/** The added group of shapes. */
	private final @NotNull Group addedGroup;
	/** The shapes to handle. */
	private final @NotNull List<Shape> shapes;

	public JoinShapes(final @NotNull Drawing theDrawing) {
		super(theDrawing);
		addedGroup = ShapeFactory.INST.createGroup();
		shapes = new ArrayList<>();
	}

	@Override
	protected void doCmdBody() {
		joinShapes();
	}

	@Override
	public boolean canDo() {
		return !shapes.isEmpty();
	}

	private void joinShapes() {
		final List<Shape> drawingSh = drawing.getShapes();
		shapes.stream().sorted((s1, s2) -> drawingSh.indexOf(s1) < drawingSh.indexOf(s2) ? -1 : 1).forEach(sh -> {
			drawing.removeShape(sh);
			addedGroup.addShape(sh);
		});

		drawing.addShape(addedGroup);
		drawing.setModified(true);
	}

	@Override
	public void undo() {
		final List<Shape> drawingSh = drawing.getShapes();
		final Map<Shape, Integer> map = shapes.stream().collect(Collectors.toMap(sh -> sh, drawingSh::indexOf));

		drawing.removeShape(addedGroup);
		addedGroup.getShapes().forEach(sh -> drawing.addShape(sh, map.get(sh)));
		addedGroup.clear();
		drawing.setModified(true);
	}

	@Override
	public void redo() {
		joinShapes();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("UndoRedoManager.join");
	}

	@Override
	public @NotNull List<Shape> getShapes() {
		return shapes;
	}
}
