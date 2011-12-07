package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;

/**
 * This abstract action factorises code needed to manipulate the selection of a drawing.<br>
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
 * 29/11/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class DrawingSelectionAction extends DrawingAction {
	/** The selection to modify. */
	protected IGroup selection;

	/**
	 * Creates the action.
	 */
	public DrawingSelectionAction() {
		super();
		selection = null;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && selection!=null;
	}


	@Override
	public void setDrawing(final IDrawing drawing) {
		super.setDrawing(drawing);

		if(drawing!=null)
			selection = drawing.getSelection().duplicate();
	}
}
