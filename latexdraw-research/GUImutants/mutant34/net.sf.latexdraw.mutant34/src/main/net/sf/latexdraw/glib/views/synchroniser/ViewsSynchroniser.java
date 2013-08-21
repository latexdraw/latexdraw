package net.sf.latexdraw.glib.views.synchroniser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.AbstractCodeView;

/**
 * Defines an abstract synchroniser between a sets of Java2D views and other views.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/25/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public abstract class ViewsSynchroniser {
	/** The handler thats provides information to the synchroniser. */
	protected ViewsSynchroniserHandler handler;

	/** The shapes to synchronise. */
	protected IDrawing drawing;

	/** The code views to synchronise. */
	protected Map<IShape, AbstractCodeView<?>> viewsCode;



	/**
	 * Creates and initialises the synchroniser.
	 * @param h The handler.
	 * @param drawing The shapes to handle.
	 * @throws IllegalArgumentException If the given views or handler is null.
	 * @since 3.0
	 */
	public ViewsSynchroniser(final ViewsSynchroniserHandler h, final IDrawing drawing) {
		super();
		handler      = Objects.requireNonNull(h);
		this.drawing = Objects.requireNonNull(drawing);
		viewsCode    = new HashMap<>();
	}


	/**
	 * Synchronises the Java2D views with their PSTricks views.
	 * @since 3.0
	 */
	public abstract void synchronise();



	/**
	 * Updates the code corresponding to the given shape.
	 * @param shape The shape to updated.
	 * @return True if the code has been updated.
	 * @since 3.0
	 */
	public abstract boolean updateCode(final IShape shape);



	/**
	 * Removes the useless PSTricks views still in the synchroniser but
	 * removed from the drawing. This method can take some times,
	 * so it is advised to launch it occasionally.
	 * @since 3.0
	 */
	public void clean() {
		if(viewsCode.size()>drawing.size()) {
			final Iterator<IShape> shapes = viewsCode.keySet().iterator();
			IShape shape;
			int i;
			final int size = drawing.size();
			boolean again;

			while(shapes.hasNext()) {
				shape = shapes.next();
				i     = 0;
				again = true;

				while(i<size && again)
					if(drawing.getShapeAt(i)==shape)
						again = false;
					else
						i++;

				if(again)// We have to remove the code view
					viewsCode.remove(shape);
			}

			shapes.remove();
		}
	}


	/**
	 * @param shape The shape that the researched view contains.
	 * @return The corresponding code view or null.
	 * @since 3.0
	 */
	public AbstractCodeView<?> getView(final IShape shape) {
		return shape==null ? null : viewsCode.get(shape);
	}



	/**
	 * @param position The position of the researched views.
	 * @return The corresponding code view at the given position or null.
	 * @since 3.0
	 */
	public AbstractCodeView<?> getViewAt(final int position) {
		return position<0 && position>=drawing.size() ? null : getView(drawing.getShapeAt(position));
	}



	/**
	 * @return The handler thats provides information to the synchroniser.
	 * @since 3.0
	 */
	public ViewsSynchroniserHandler getHandler() {
		return handler;
	}


	/**
	 * @param handler The new handler thats provides information to the synchroniser.
	 * @since 3.0
	 */
	public void setHandler(final ViewsSynchroniserHandler handler) {
		if(handler!=null)
			this.handler = handler;
	}


	/**
	 * @return the drawing.
	 * @since 3.0
	 */
	public IDrawing getDrawing() {
		return drawing;
	}



	/**
	 * @param drawing The drawing used to generate the code. Must not be null.
	 * @since 3.0
	 */
	public void setViews2D(final IDrawing drawing) {
		if(drawing!=null)
			this.drawing = drawing;
	}
}
