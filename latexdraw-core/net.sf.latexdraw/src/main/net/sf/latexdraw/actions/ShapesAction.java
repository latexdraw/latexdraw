/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import java.util.List;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.malai.action.Action;

/**
 * An action that handles a set of shapes attribute.
 * @author Arnaud Blouin
 */
public interface ShapesAction extends Action {
	/**
	 * Sets the shape to handle.
	 * @param shape The shape to handle. Can be null.
	 * @since 3.0
	 */
	default void setShape(final IShape shape) {
		getShapes().clear();

		if(shape != null) {
			getShapes().add(shape);
		}
	}

	/**
	 * Add a shape to the list of shapes to handle.
	 * @param shape The shape to handle.
	 * @since 3.0
	 */
	default void addShape(final IShape shape) {
		if(shape != null) {
			getShapes().add(shape);
		}
	}

	/**
	 * @return The shapes to handle.
	 * @since 3.0
	 */
	List<IShape> getShapes();
}
