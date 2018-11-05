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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapesCmd;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import org.malai.undo.Undoable;

/**
 * This command removes shapes from a drawing.
 * @author Arnaud Blouin
 */
public class DeleteShapes extends DrawingCmdImpl implements ShapesCmd, Undoable, Modifying {
	/** The index of the deleted shapes into the original list. */
	List<Integer> positionShapes;

	/** The shapes to handle. */
	final List<Shape> shapes;


	public DeleteShapes(final Drawing drawing) {
		super(drawing);
		shapes = new ArrayList<>();
	}

	@Override
	protected void doCmdBody() {
		final List<Shape> drawingSh = drawing.getShapes();
		positionShapes = shapes.stream().mapToInt(sh -> {
			final int pos = drawingSh.indexOf(sh);
			drawing.removeShape(sh);
			return pos;
		}).boxed().collect(Collectors.toList());
		drawing.setModified(true);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && !shapes.isEmpty();
	}

	@Override
	public void undo() {
		for(int i = positionShapes.size() - 1; i >= 0; i--) {
			drawing.addShape(shapes.get(i), positionShapes.get(i));
		}
		drawing.setModified(true);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.5");
	}

	@Override
	public List<Shape> getShapes() {
		return shapes;
	}
}
