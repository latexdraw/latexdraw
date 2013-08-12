package net.sf.latexdraw.actions

import java.util.ArrayList

import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * This trait encapsulates a set of shapes attribute.<br>
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
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 * @param <T> The type of the shape that the action handles.
 */
trait ShapesAction {
	/** The shapes to handle. */
	protected var _shapes : java.util.List[IShape] = new ArrayList[IShape]()


	/**
	 * Add a shape to the list of shapes to handle.
	 * @param shape The shape to handle.
	 * @since 3.0
	 */
	def addShape(shape : IShape) {
		if(shape!=null)
			_shapes.add(shape)
	}


	/**
	 * Sets the shape to handle.
	 * @param shape The shape to handle. Can be null.
	 * @since 3.0
	 */
	def setShape(shape : IShape) {
		_shapes.clear

		if(shape!=null)
			_shapes.add(shape)
	}


	/**
	 * @return The shapes to handle.
	 * @since 3.0
	 */
	def shapes = _shapes
}
