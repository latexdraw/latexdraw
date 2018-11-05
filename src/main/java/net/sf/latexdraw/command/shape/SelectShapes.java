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
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapesCmd;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import org.malai.command.Command;

/**
 * This command allows to (un-)select shapes.
 * @author Arnaud Blouin
 */
public class SelectShapes extends DrawingCmdImpl implements ShapesCmd, Modifying {
	/** The shapes to handle. */
	final List<Shape> shapes;

	public SelectShapes(final Drawing theDrawing) {
		super(theDrawing);
		shapes = new ArrayList<>();
	}

	@Override
	public void doCmdBody() {
		final Group selection = drawing.getSelection();

		if(shapes.isEmpty()) {
			selection.clear();
		}else {
			for(int i = selection.size() - 1; i >= 0; i--) {
				if(!shapes.contains(selection.getShapeAt(i))) {
					selection.removeShape(i);
				}
			}
			shapes.forEach(sh -> {
				if(!selection.contains(sh)) {
					selection.addShape(sh);
				}
			});
		}
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.UNLIMITED;
	}

	@Override
	public boolean unregisteredBy(final Command cmd) {
		return cmd instanceof SelectShapes || cmd instanceof CutShapes || cmd instanceof DeleteShapes;
	}

	@Override
	public List<Shape> getShapes() {
		return shapes;
	}
}
