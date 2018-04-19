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

import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.commands.DrawingCmdImpl;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.commands.ShapesCmd;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.command.Command;

/**
 * This command allows to (un-)select shapes.
 * @author Arnaud Blouin
 */
public class SelectShapes extends DrawingCmdImpl implements ShapesCmd, Modifying {
	/** The shapes to handle. */
	final List<IShape> shapes;

	public SelectShapes() {
		super();
		shapes = new ArrayList<>();
	}

	@Override
	public void doCmdBody() {
		final IGroup selection = drawing.getSelection();

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
	public List<IShape> getShapes() {
		return shapes;
	}
}
