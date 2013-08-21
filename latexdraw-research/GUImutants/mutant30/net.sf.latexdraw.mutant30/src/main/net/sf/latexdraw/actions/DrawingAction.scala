package net.sf.latexdraw.actions

import net.sf.latexdraw.glib.models.interfaces.IDrawing

/**
 * This trait encapsulates a drawing attribute.<br>
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
 */
trait DrawingAction {
	/** The drawing that will be handled by the action. */
	protected var _drawing : Option[IDrawing] = None


	/**
	 * @param drawing The drawing that will be handled by the action
	 * @since 3.0
	 */
	def setDrawing(drawing : IDrawing) {
		_drawing = Some(drawing)
	}


	/**
	 * @return The drawing that will be handled by the action
	 * @since 3.0
	 */
	def drawing = _drawing
}
