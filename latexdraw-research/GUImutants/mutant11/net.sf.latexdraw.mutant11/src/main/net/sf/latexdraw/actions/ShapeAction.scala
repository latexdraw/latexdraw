package net.sf.latexdraw.actions

import net.sf.latexdraw.glib.models.interfaces.IShape

/**
 * This trait encapsulates a shape attribute.<br>
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
trait ShapeAction[T <: IShape] {
	/** The shape to add. */
	protected var _shape : Option[T] = None

	/**
	 * Sets the shape to add.
	 * @param shape The shape to add.
	 * @since 3.0
	 */
	def setShape(shape : T) {
		_shape = Some(shape)
	}


	/**
	 * @return The shape to modify.
	 * @since 3.0
	 */
	def shape = _shape
}
