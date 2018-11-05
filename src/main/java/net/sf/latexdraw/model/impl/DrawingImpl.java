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
package net.sf.latexdraw.model.impl;

import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;

/**
 * Implements the concept of drawing.
 * @author Arnaud Blouin
 */
class DrawingImpl implements Drawing, SetShapesBase {
	/** The set of shapes. */
	private final ListProperty<Shape> shapes;

	/** The selected shapes of the drawing. */
	private final Group selection;

	/** Defined if the shape has been modified. */
	private boolean modified;


	DrawingImpl() {
		super();
		shapes = new SimpleListProperty<>(FXCollections.observableArrayList());
		selection = ShapeFactory.INST.createGroup();
		modified = false;
	}


	@Override
	public Group getSelection() {
		return selection;
	}

	@Override
	public void setSelection(final List<Shape> newSelection) {
		selection.clear();
		newSelection.forEach(sh -> selection.addShape(sh));
	}

	@Override
	public void clear() {
		SetShapesBase.super.clear();
		selection.clear();
	}

	@Override
	public ListProperty<Shape> getShapes() {
		return shapes;
	}

	@Override
	public boolean removeShape(final Shape sh) {
		selection.removeShape(sh);
		return SetShapesBase.super.removeShape(sh);
	}

	@Override
	public Shape removeShape(final int i) {
		// Must be removed from the selection before removing from the main list (otherwise mapping selection2border will fail.
		if(i >= -1 && !shapes.isEmpty() && i < shapes.size()) {
			if(i == -1) {
				selection.removeShape(shapes.get(shapes.size() - 1));
			}else {
				selection.removeShape(shapes.get(i));
			}
		}

		return SetShapesBase.super.removeShape(i);
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
