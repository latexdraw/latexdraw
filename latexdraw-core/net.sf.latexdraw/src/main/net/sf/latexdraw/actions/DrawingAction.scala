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

import net.sf.latexdraw.models.interfaces.shape.IDrawing

/**
  * This trait encapsulates a drawing attribute.
  */
trait DrawingAction {
	/** The drawing that will be handled by the action. */
	var _drawing: Optional[IDrawing] = Optional.empty()


	/**
	  * @param drawing The drawing that will be handled by the action
	  * @since 3.0
	  */
	def setDrawing(drawing: IDrawing) {
		_drawing = Optional.ofNullable(drawing)
	}


	/**
	  * @return The drawing that will be handled by the action
	  * @since 3.0
	  */
	def drawing = _drawing
}
