package net.sf.latexdraw.actions;

import java.util.ArrayList;
import java.util.List;

import org.malai.action.Action;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * This action copies the selected shapes.<br>
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
 * 06/03/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class CopyShapes extends Action {
	/** The copied shapes from the selection. */
	protected List<IShape> copiedShapes;

	/** The selection action. */
	protected SelectShapes selection;

	/** The number of times that the shapes have been copied. Use to compute the gap while pasting. */
	protected int nbTimeCopied;


	/**
	 * Creates the action.
	 */
	public CopyShapes() {
		super();
		nbTimeCopied = 0;
	}


	@Override
	protected void doActionBody() {
		copiedShapes = new ArrayList<IShape>();

		for(final IShape shape : selection.shapes)
			copiedShapes.add(DrawingTK.getFactory().duplicate(shape));
	}


	@Override
	public boolean cancelledBy(final Action action) {
		return action instanceof CopyShapes;
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	public boolean canDo() {
		return selection!=null && !selection.shapes.isEmpty();
	}


	/**
	 * @param selection The selected shapes to copy or cut.
	 * @since 3.0
	 */
	public void setSelection(final SelectShapes selection) {
		this.selection = selection;
	}


	@Override
	public void flush() {
		super.flush();

		if(copiedShapes!=null)
			copiedShapes.clear();

		selection 	 = null;
		copiedShapes = null;
	}
}

