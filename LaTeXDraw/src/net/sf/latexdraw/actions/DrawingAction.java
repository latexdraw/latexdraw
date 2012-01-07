package net.sf.latexdraw.actions;

import org.malai.action.Action;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;

/**
 * This abstract action uses a drawing.<br>
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
 * 01/07/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class DrawingAction extends Action {
	/** The drawing that will be handled by the action. */
	protected IDrawing drawing;
	
	
	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public DrawingAction() {
		super();
	}


	@Override
	public boolean canDo() {
		return drawing!=null;
	}
	
	
	@Override
	public void flush() {
		super.flush();
		drawing = null;
	}
	
	
	/**
	 * @param drawing The drawing that will be handled by the action
	 * @since 3.0
	 */
	public void setDrawing(final IDrawing drawing) {
		this.drawing = drawing;
	}
	
	
	/**
	 * @return The drawing that will be handled by the action
	 * @since 3.0
	 */
	public IDrawing getDrawing() {
		return drawing;
	}
}
