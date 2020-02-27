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
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command puts in background / foreground shapes.
 * @author Arnaud Blouin
 */
public class MoveBackForegroundShapes extends ShapeCmdImpl<Group> implements Undoable, Modifying {
	/** Defines whether the shapes must be placed in the foreground. */
	private final boolean foreground;
	/** The drawing that will be handled by the command. */
	private final @NotNull Drawing drawing;
	/** The former position of the shapes. */
	private int[] formerId;
	/** The shapes sorted by their position. */
	private List<Shape> sortedSh;
	private boolean mementoModified;

	public MoveBackForegroundShapes(final @NotNull Group gp, final boolean foreground, final @NotNull Drawing drawing) {
		super(gp);
		this.drawing = drawing;
		this.foreground = foreground;
	}

	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
	}

	@Override
	protected void doCmdBody() {
		if(foreground) {
			moveForeground();
		}else {
			moveBackground();
		}
	}

	private void prepareMove() {
		final int size = shape.size();
		formerId = new int[size];
		final List<Shape> drshapes = drawing.getShapes();
		sortedSh = shape.getShapes().stream().sorted(Comparator.comparingInt(sh -> drshapes.indexOf(sh))).collect(Collectors.toList());
	}

	/** Puts the shapes in the foreground. */
	private void moveForeground() {
		final int size = shape.size();
		prepareMove();

		for(int i = 0; i < size; i++) {
			final Shape sh = sortedSh.get(i);
			formerId[i] = drawing.getShapes().indexOf(sh);
			drawing.removeShape(sh);
			drawing.addShape(sh);
		}
		drawing.setModified(true);
	}


	/** Puts the shapes in the background. */
	private void moveBackground() {
		final int size = shape.size();
		prepareMove();

		for(int i = size - 1; i >= 0; i--) {
			final Shape sh = sortedSh.get(i);
			formerId[i] = drawing.getShapes().indexOf(sh);
			drawing.removeShape(sh);
			drawing.addShape(sh, 0);
		}
		drawing.setModified(true);
	}

	@Override
	public boolean canDo() {
		return !shape.isEmpty();
	}

	@Override
	public void undo() {
		if(foreground) {
			for(int i = formerId.length - 1; i >= 0; i--) {
				drawing.removeShape(sortedSh.get(i));
				drawing.addShape(sortedSh.get(i), formerId[i]);
			}
		}else {
			for(int i = 0, size = sortedSh.size(); i < size; i++) {
				drawing.removeShape(sortedSh.get(i));
				drawing.addShape(sortedSh.get(i), formerId[i]);
			}
		}

		drawing.setModified(mementoModified);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.8");
	}
}
