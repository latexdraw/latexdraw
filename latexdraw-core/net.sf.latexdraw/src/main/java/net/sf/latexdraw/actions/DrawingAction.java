/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import java.util.Optional;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;

/**
 * This trait encapsulates a drawing attribute.
 * @author Arnaud Blouin
 */
public interface DrawingAction {
	/**
	 * @param dr The drawing that will be handled by the action
	 * @since 3.0
	 */
	void setDrawing(final IDrawing dr);

	/**
	 * @return The drawing that will be handled by the action
	 * @since 3.0
	 */
	Optional<IDrawing> getDrawing();
}
