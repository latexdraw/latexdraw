/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.actions.shape;

import java.util.Optional;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapeActionImpl;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This action adds a shape to a drawing.
 * @author Arnaud Blouin
 */
public class AddShape extends ShapeActionImpl<IShape> implements Undoable, Modifying {
	/** The drawing that will be handled by the action. */
	protected final Optional<IDrawing> drawing;

	public AddShape(final IShape sh, final IDrawing dr) {
		super(sh);
		drawing = Optional.ofNullable(dr);
	}

	@Override
	protected void doActionBody() {
		drawing.ifPresent(dr -> shape.ifPresent(sh -> {
			dr.addShape(sh);
			dr.setModified(true);
		}));
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("UndoRedoManager.create"); //$NON-NLS-1$
	}

	@Override
	public void redo() {
		doActionBody();
	}

	@Override
	public void undo() {
		drawing.ifPresent(dr -> shape.ifPresent(sh -> {
			dr.removeShape(sh);
			dr.setModified(true);
		}));
	}

	@Override
	public boolean canDo() {
		return drawing.isPresent() && shape.isPresent();
	}
}
