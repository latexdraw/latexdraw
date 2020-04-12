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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command joins shapes.
 * @author Arnaud Blouin
 */
public class JoinShapes extends DrawingCmdImpl implements Undoable, Modifying {
	/** The added group of shapes. */
	private final @NotNull Group addedGroup;
	/** The shapes to handle. */
	private final @NotNull List<Shape> shapes;
	private Map<Shape, Integer> mementoIndexes;
	private boolean mementoModified;

	public JoinShapes(final @NotNull Drawing theDrawing, final List<Shape> shapes) {
		super(theDrawing);
		addedGroup = ShapeFactory.INST.createGroup();
		this.shapes = List.copyOf(shapes);
	}

	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
		mementoIndexes = shapes.stream().collect(Collectors.toMap(sh -> sh, sh -> drawing.getShapes().indexOf(sh)));
	}

	@Override
	protected void doCmdBody() {
		redo();
	}

	@Override
	public boolean canDo() {
		return !shapes.isEmpty();
	}

	@Override
	public void undo() {
		drawing.removeShape(addedGroup);
		addedGroup.clear();

		mementoIndexes
			.entrySet()
			.stream()
			.sorted(Comparator.comparingInt(entry -> entry.getValue()))
			.forEach(sh -> drawing.addShape(sh.getKey(), sh.getValue()));

		drawing.setModified(mementoModified);
	}

	@Override
	public void redo() {
		shapes.stream().sorted(Comparator.comparingInt(sh -> drawing.getShapes().indexOf(sh))).forEach(sh -> {
			drawing.removeShape(sh);
			addedGroup.addShape(sh);
		});

		drawing.addShape(addedGroup);
		drawing.setModified(true);
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("join");
	}
}
