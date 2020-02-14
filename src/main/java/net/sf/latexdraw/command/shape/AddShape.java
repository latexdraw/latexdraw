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
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command adds a shape to a drawing.
 * @author Arnaud Blouin
 */
public class AddShape extends ShapeCmdImpl<Shape> implements Undoable, Modifying {
	/** The drawing that will be handled by the command. */
	protected final @NotNull Drawing drawing;
	private boolean mementoModified;

	public AddShape(final @NotNull Shape sh, final @NotNull Drawing dr) {
		super(sh);
		drawing  = dr;
	}

	@Override
	protected void doCmdBody() {
		mementoModified = drawing.isModified();
		redo();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("UndoRedoManager.create");
	}

	@Override
	public void redo() {
		drawing.addShape(shape);
		drawing.setModified(true);
	}

	@Override
	public void undo() {
		drawing.removeShape(shape);
		drawing.setModified(mementoModified);
	}
}
