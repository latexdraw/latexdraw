package net.sf.latexdraw.actions;

import java.util.List;

import org.malai.action.Action;


import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * This action allows to (un-)select shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 09/14/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class SelectShapes extends MultiShapesAction {
	/**
	 * Initialises the action.
	 */
	public SelectShapes() {
		super();
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void doActionBody() {
		final IGroup selection = drawing.getSelection();
		final List<IShape> sel = selection.getShapes();
		sel.clear();

		for(IShape sh : shapes)
			sel.add(sh);
	}


	@Override
	public boolean cancelledBy(final Action action) {
		return action instanceof SelectShapes || action instanceof CutShapes || action instanceof DeleteShapes;
	}
}
