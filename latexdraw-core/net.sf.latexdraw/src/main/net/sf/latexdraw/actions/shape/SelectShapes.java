/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.actions.DrawingActionImpl;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapesAction;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * This action allows to (un-)select shapes.
 */
public class SelectShapes extends DrawingActionImpl implements ShapesAction, Modifying {
	/** The shapes to handle. */
	final List<IShape> shapes;

	public SelectShapes() {
		super();
		shapes = new ArrayList<>();
	}

	@Override
	public void doActionBody() {
		drawing.ifPresent(dr -> {
			final IGroup selection = dr.getSelection();

			if(shapes.isEmpty()) selection.clear();
			else {
				for(int i = selection.size() - 1; i >= 0; i--) {
					if(!shapes.contains(selection.getShapeAt(i))) selection.removeShape(i);
				}
				shapes.forEach(sh -> {
					if(!selection.contains(sh)) selection.addShape(sh);
				});
			}
		});
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	@Override
	public boolean cancelledBy(final Action action) {
		return action instanceof SelectShapes || action instanceof CutShapes || action instanceof DeleteShapes;
	}

	@Override
	public List<IShape> getShapes() {
		return shapes;
	}
}
