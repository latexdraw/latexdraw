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
package net.sf.latexdraw.models.impl;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * Implements the concept of drawing.
 * @author Arnaud Blouin
 */
class LDrawing implements IDrawing, LSetShapes {
	/** The set of shapes. */
	private final ObservableList<IShape> shapes;

	/** The selected shapes of the drawing. */
	private final IGroup selection;

	/** Defined if the shape has been modified. */
	private boolean modified;


	LDrawing() {
		super();
		shapes = FXCollections.observableArrayList();
		selection = ShapeFactory.INST.createGroup();
		modified = false;
	}


	@Override
	public IGroup getSelection() {
		return selection;
	}

	@Override
	public void setSelection(final List<IShape> newSelection) {
		selection.clear();
		newSelection.forEach(sh -> selection.addShape(sh));
	}

	@Override
	public void clear() {
		LSetShapes.super.clear();
		selection.clear();
	}

	@Override
	public ObservableList<IShape> getShapes() {
		return shapes;
	}

	@Override
	public boolean removeShape(final IShape sh) {
		selection.removeShape(sh);
		return LSetShapes.super.removeShape(sh);
	}

	@Override
	public IShape removeShape(final int i) {
		// Must be removed from the selection before removing from the main list (otherwise mapping selection2border will fail.
		if(i >= -1 && !shapes.isEmpty() && i < shapes.size()) {
			if(i == -1) {
				selection.removeShape(shapes.get(shapes.size() - 1));
			}else {
				selection.removeShape(shapes.get(i));
			}
		}

		return LSetShapes.super.removeShape(i);
	}

	@Override
	public void setModified(final boolean modified) {
		if(!modified) {
			shapes.forEach(sh -> sh.setModified(false));
		}
		this.modified = modified;
	}

	@Override
	public boolean isModified() {
		return modified || shapes.stream().anyMatch(sh -> sh.isModified());
	}

	@Override
	public void reinit() {
		clear();
	}
}
