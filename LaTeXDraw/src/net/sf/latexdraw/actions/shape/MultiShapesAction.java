package net.sf.latexdraw.actions.shape;

import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.actions.DrawingAction;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * This abstract action uses a set of shapes.<br>
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
public abstract class MultiShapesAction extends DrawingAction {
	/** The shapes to handle. */
	protected List<IShape> shapes;
	
	/**
	 * Creates the action.
	 */
	public MultiShapesAction() {
		super();
		shapes = new ArrayList<IShape>();
	}

	
	@Override
	public void flush() {
		super.flush();
		shapes.clear();
	}
	
	
	/**
	 * Add a shape to the list of shapes to handle.
	 * @param shape The shape to handle.
	 * @since 3.0
	 */
	public void addShape(final IShape shape) {
		if(shape!=null)
			shapes.add(shape);
	}


	/**
	 * Sets the shape to handle.
	 * @param shape The shape to handle. Can be null.
	 * @since 3.0
	 */
	public void setShape(final IShape shape) {
		shapes.clear();

		if(shape!=null)
			shapes.add(shape);
	}
	
	
	/**
	 * @return The shapes to handle.
	 * @since 3.0
	 */
	public List<IShape> getShapes() {
		return shapes;
	}
}
