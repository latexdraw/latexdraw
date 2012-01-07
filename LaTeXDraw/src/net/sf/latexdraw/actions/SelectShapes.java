package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.action.Action;

/**
 * This action allows to (un-)select shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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

		if(shapes.isEmpty())
			selection.clear();
		else {
			int i=selection.size()-1;
			while(i>=0) {
				if(!shapes.contains(selection.getShapeAt(i)))
					selection.removeShape(i);
				i--;
			}

			for(IShape sh : shapes)
				if(!selection.contains(sh))
					selection.addShape(sh);
		}
	}


	@Override
	public boolean cancelledBy(final Action action) {
		return action instanceof SelectShapes || action instanceof CutShapes || action instanceof DeleteShapes;
	}
}
