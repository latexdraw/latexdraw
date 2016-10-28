/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions

import java.util.Optional

import net.sf.latexdraw.models.interfaces.shape.IShape

/**
  * This trait encapsulates a shape attribute.
  */
trait ShapeAction[T <: IShape] {
	/** The shape to add. */
	var _shape: Optional[T] = Optional.empty()

	/**
	  * Sets the shape to add.
	  *
	  * @param shape The shape to add.
	  * @since 3.0
	  */
	def setShape(shape: T) {
		_shape = Optional.ofNullable(shape)
	}

	/**
	  * @return The shape to modify.
	  * @since 3.0
	  */
	def shape = _shape
}
