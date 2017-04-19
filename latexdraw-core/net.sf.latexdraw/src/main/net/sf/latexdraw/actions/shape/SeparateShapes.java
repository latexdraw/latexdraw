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
import java.util.stream.IntStream;
import net.sf.latexdraw.actions.DrawingAction;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapeActionImpl;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This action separates joined shapes.
 * @author Arnaud Blouin
 */
public class SeparateShapes extends ShapeActionImpl<IGroup> implements DrawingAction, Modifying, Undoable {
	/** The drawing that will be handled by the action. */
	protected Optional<IDrawing> drawing;


	public SeparateShapes() {
		super();
		drawing = Optional.empty();
	}

	@Override
	public boolean canDo() {
		return super.canDo() && drawing.isPresent() && !shape.get().isEmpty();
	}

	@Override
	protected void doActionBody() {
		drawing.ifPresent(dr -> shape.ifPresent(sh -> {
			final int position = dr.getShapes().indexOf(sh);
			final int insertPos = position >= dr.size() - 1 ? -1 : position;
			dr.removeShape(position);
			sh.getShapes().forEach(s -> dr.addShape(s, insertPos));
			dr.setModified(true);
		}));
	}

	@Override
	public void undo() {
		drawing.ifPresent(dr -> shape.ifPresent(gp -> {
			final int position = dr.getShapes().indexOf(gp.getShapeAt(0));
			final int addPosition = position >= dr.size() ? -1 : position;
			IntStream.range(0, gp.getShapes().size()).forEach(i -> dr.removeShape(position));
			dr.addShape(gp, addPosition);
			dr.setModified(true);
		}));
	}

	@Override
	public void redo() {
		doActionBody();
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("UndoRedoManager.seperate");
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	@Override
	public void setDrawing(final IDrawing dr) {
		drawing = Optional.ofNullable(dr);
	}

	@Override
	public Optional<IDrawing> getDrawing() {
		return drawing;
	}
}
