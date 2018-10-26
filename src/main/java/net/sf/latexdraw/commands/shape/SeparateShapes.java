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
package net.sf.latexdraw.commands.shape;

import java.util.ResourceBundle;
import java.util.stream.IntStream;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.commands.ShapeCmdImpl;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.malai.undo.Undoable;

/**
 * This command separates joined shapes.
 * @author Arnaud Blouin
 */
public class SeparateShapes extends ShapeCmdImpl<IGroup> implements Modifying, Undoable {
	/** The drawing that will be handled by the command. */
	protected final IDrawing drawing;


	public SeparateShapes(final IDrawing drawing, final IGroup gp) {
		super(gp);
		this.drawing = drawing;
	}

	@Override
	public boolean canDo() {
		return drawing != null && shape.isPresent() && !shape.get().isEmpty() && super.canDo();
	}

	@Override
	protected void doCmdBody() {
		shape.ifPresent(sh -> {
			final int position = drawing.getShapes().indexOf(sh);
			final int insertPos = position >= drawing.size() - 1 ? -1 : position;
			drawing.removeShape(position);
			sh.getShapes().forEach(s -> drawing.addShape(s, insertPos));
			drawing.setModified(true);
		});
	}

	@Override
	public void undo() {
		shape.ifPresent(gp -> {
			final int position = drawing.getShapes().indexOf(gp.getShapeAt(0));
			final int addPosition = position >= drawing.size() ? -1 : position;
			IntStream.range(0, gp.getShapes().size()).forEach(i -> drawing.removeShape(position));
			drawing.addShape(gp, addPosition);
			drawing.setModified(true);
		});
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("UndoRedoManager.seperate");
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.LIMITED;
	}
}
